package de.arnohaase.akka_workshop.vouchers;

import akka.Done;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.persistence.AbstractPersistentActor;
import akka.persistence.SnapshotOffer;

import java.util.HashSet;
import java.util.Set;


public class Vouchers extends AbstractPersistentActor {
    public static Props props() {
        return Props.create(Vouchers.class, Vouchers::new);
    }

    private final LoggingAdapter log = Logging.getLogger(this);

    private Set<String> vouchers = new HashSet<>();

    @Override public String persistenceId () {
        return "voucher-codes";
    }

    @Override public Receive createReceive () {
        return receiveBuilder()
                .match(VoucherMessages.AddVouchers.class, this::onAddVouchers)
                .match(VoucherMessages.GetVoucher.class, this::onGetVoucher)
                .build();
    }

    private void onAddVouchers(VoucherMessages.AddVouchers msg) {
        persist(ImmutableVoucherCodesAdded.of(msg.vouchers()), evt -> {
            vouchers.addAll(msg.vouchers());
            if (lastSequenceNr() % 1000 == 0) saveSnapshot(ImmutableSnapshot.of(vouchers));
            sender().tell(Done.getInstance(), self());
        });
    }

    private void onGetVoucher(VoucherMessages.GetVoucher msg) {
        if (vouchers.isEmpty()) {
            log.info("no voucher");
            sender().tell (ImmutableNoVoucherResponse.of(), self());
        }
        else {
            final String voucher = vouchers.iterator().next();
            persist(ImmutableVoucherAssigned.of(voucher), evt -> {
                vouchers.remove(voucher);
                sender().tell(ImmutableGetVoucherResponse.of(voucher), self());
                if (lastSequenceNr() % 1000 == 0) saveSnapshot(ImmutableSnapshot.of(vouchers));
            });
            
        }
    }

    @Override public Receive createReceiveRecover () {
        return receiveBuilder()
                .match(VoucherMessages.VoucherCodesAdded.class, evt -> vouchers.addAll(evt.vouchers()))
                .match(VoucherMessages.VoucherAssigned.class, evt -> vouchers.remove(evt.voucher()))
                .match(SnapshotOffer.class, so -> {
                    vouchers.clear();
                    vouchers.addAll (((ImmutableSnapshot) so.snapshot()).vouchers());
                })
                .build();
    }
}
