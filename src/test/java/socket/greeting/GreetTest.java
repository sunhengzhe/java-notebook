package socket.greeting;

import basic.network.socket.GreetClient;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;

public class GreetTest {

    @Test
    public void should_server_return_hi() throws IOException {
        GreetClient greetClient = new GreetClient();
        greetClient.startConnection("127.0.0.1", 6666);
        String hi = greetClient.sendMessage("hi");
        assertEquals("hi", hi);
    }
}
