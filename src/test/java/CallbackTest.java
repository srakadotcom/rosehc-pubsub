import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import packet.StanKoniaRequestPacket;
import packet.StanKoniaResponsePacket;
import pl.rosehc.redis.RedisAdapter;
import pl.rosehc.redis.callback.CallbackFactory;

public class CallbackTest {
  @Test
  public void test() {
    RedisAdapter adapter = new RedisAdapter(new CallbackFactory(), "localhost", 6379, "");
    adapter.subscribe("kon_z_valony", Arrays.asList(StanKoniaRequestPacket.class, StanKoniaResponsePacket.class));
    adapter.<StanKoniaResponsePacket>sendFuturePacket(new StanKoniaRequestPacket(), "kon_z_valony")
        .thenAccept(packet -> System.out.println("Stan konia: " + packet.getStanKonia()))
        .orTimeout(10, TimeUnit.SECONDS)
        .join();
  }
}
