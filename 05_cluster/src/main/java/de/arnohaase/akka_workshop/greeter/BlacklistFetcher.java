package de.arnohaase.akka_workshop.greeter;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.cluster.Cluster;
import de.arnohaase.akka_workshop.util.AbstractActorWithTimeout;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


class BlacklistFetcher extends AbstractActorWithTimeout {
    static Props props(ActorRef replyTo) {
        return Props.create (BlacklistFetcher.class, () -> new BlacklistFetcher(replyTo)).withDispatcher("fs-dispatcher");
    }

    private BlacklistFetcher (ActorRef replyTo) throws IOException {
//        Cluster cluster = Cluster.get(context().system());
//        System.out.println("cluster: ");
//        System.out.println("  members: " + cluster.state().getMembers());
//        System.out.println("  leader: " + cluster.state().getLeader());

        try (BufferedReader in = new BufferedReader(new FileReader("blacklist.txt"))) {
            List<String> names = new ArrayList<>();
            String line = null;
            while ((line = in.readLine()) != null) {
                line = line.trim();
                if (! line.isEmpty()) {
                    names.add(line);
                }
            }

            replyTo.tell(ImmutableBlacklist.of(names), self());
            context().stop(self());
        }
    }

    @Override public Receive createReceive () {
        return emptyBehavior();
    }
}
