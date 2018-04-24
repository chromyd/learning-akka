package de.arnohaase.akka_workshop.greeter;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Status;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import de.arnohaase.akka_workshop.vouchers.ImmutableGetVoucher;
import de.arnohaase.akka_workshop.vouchers.VoucherMessages;
import scala.concurrent.duration.FiniteDuration;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletionStage;

import static akka.pattern.PatternsCS.pipe;


class GreeterWorker extends AbstractActor {
    private static class TimedOut{}

    private final String name;
    private final List<String> blacklist;
    private final ActorRef replyTo;

    private boolean hasGoogle = false;
    private boolean hasFacebook = false;

    private String voucher;
    private boolean hasVoucherResponse = false;

    static Props props(String name, List<String> blacklist, ActorRef replyTo, ActorRef vouchers) {
        return Props.create(GreeterWorker.class, () -> new GreeterWorker(name, blacklist, replyTo, vouchers));
    }

    private GreeterWorker (String name, List<String> blacklist, ActorRef replyTo, ActorRef vouchers) {
        this.name = name;
        this.blacklist = blacklist;
        this.replyTo = replyTo;

        vouchers.tell(ImmutableGetVoucher.builder().build(), self());

        final Http http = Http.get(context().system());
        final Materializer mat = ActorMaterializer.create(context().system());

        CompletionStage<String> fGoogle = http.singleRequest(HttpRequest.create("http://google.com"), mat)
                .thenApply(response -> {
                    response.entity().discardBytes(mat);
                    return "google";
                });
        CompletionStage<String> fFacebook = http.singleRequest(HttpRequest.create("http://facebook.com"), mat)
                .thenApply(response -> {
                    response.entity().discardBytes(mat);
                    return "facebook";
                });


        pipe (fGoogle, context().dispatcher()).to(self());
        pipe (fFacebook, context().dispatcher()).to(self());

        context().system().scheduler().scheduleOnce(FiniteDuration.create(20, "ms"), self(), new TimedOut(), context().dispatcher(), self());
    }

    @Override public Receive createReceive () {
        return receiveBuilder()
                .match(String.class, this::onHttpResponse)
                .match(TimedOut.class, this::onTimedOut)
                .match(VoucherMessages.GetVoucherResponse.class, this::onVoucher)
                .match(VoucherMessages.NoVoucherResponse.class, this::onNoVoucher)
                .build();
    }

    private void onVoucher(VoucherMessages.GetVoucherResponse msg) {
        voucher = msg.voucher();
        hasVoucherResponse = true;
        checkComplete();
    }

    private void onNoVoucher(VoucherMessages.NoVoucherResponse msg) {
        voucher = null;
        hasVoucherResponse = true;
        checkComplete();
    }

    private void onTimedOut(TimedOut msg) {
        doReply();
    }

    private void onHttpResponse(String msg) {
        switch(msg) {
            case "google": hasGoogle = true; break;
            case "facebook": hasFacebook = true; break;
        }
    }

    private void checkComplete() {
        if (hasGoogle && hasFacebook && hasVoucherResponse) {
            doReply();
        }
    }

    private void doReply() {
        if (blacklist.contains(name)) {
            replyTo.tell(new Status.Failure(new NoSuchElementException()), self());
        } else {
            String fb = hasFacebook ? "with Facebook" : "without Facebook";
            String go = hasGoogle ? "with Google" : "without Google";

            String v = (voucher != null) ? (", voucherCode: " + voucher) : "";

            replyTo.tell(ImmutableGreetingResponse.of("Hello, " + name + " (" + fb + " " + go + ")" + v + "!"), self());
        }
        context().stop(self());
    }
}
