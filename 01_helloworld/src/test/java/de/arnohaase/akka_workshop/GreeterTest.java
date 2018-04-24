package de.arnohaase.akka_workshop;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.TestKit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import static de.arnohaase.akka_workshop.GreeterMessages.GreetingResponse;
import static org.junit.Assert.assertEquals;

public class GreeterTest {
    private static ActorSystem system;

    @BeforeClass
    public static void setUp() {
        system = ActorSystem.create();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        Await.result (system.terminate(), Duration.Inf());
        system = null;
    }

    @Test
    public void testGreeting() {
        final TestKit client = new TestKit(system);

        final ActorRef greeter = system.actorOf(Greeter.props(), "greeter");

        greeter.tell(ImmutableGreetingRequest.of("me"), client.testActor());
        final GreetingResponse response = client.expectMsgClass(GreetingResponse.class);
        assertEquals("Hello, me!", response.greeting());

        greeter.tell(ImmutableGreetingRequest.of("you"), client.testActor());
        final GreetingResponse response2 = client.expectMsgClass(GreetingResponse.class);
        assertEquals("Hello, you!", response2.greeting());
    }
}
