import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import packet.StanKoniaRequestPacket;
import packet.StanKoniaResponsePacket;
import pl.rosehc.redis.RedisAdapter;
import pl.rosehc.redis.callback.CallbackFactory;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class CallbackTest {
  @Test
  public void test() {
    RedisAdapter adapter = new RedisAdapter(new CallbackFactory(), new JedisPool(createConnectionPoolConfig(), "localhost",
        6379, (int) TimeUnit.SECONDS.toMillis(10L)));
    adapter.subscribe("kon_z_valony", Arrays.asList(StanKoniaRequestPacket.class, StanKoniaResponsePacket.class));
    adapter.<StanKoniaResponsePacket>sendFuturePacket(new StanKoniaRequestPacket(), "kon_z_valony")
        .thenAccept(packet -> System.out.println("Stan konia: " + packet.getStanKonia()))
        .orTimeout(10, TimeUnit.SECONDS)
        .join();
  }


  private static JedisPoolConfig createConnectionPoolConfig() {
    JedisPoolConfig poolConfig = new JedisPoolConfig();
    poolConfig.setMinEvictableIdleTime(Duration.ofMinutes(1L));
    poolConfig.setTimeBetweenEvictionRuns(Duration.ofSeconds(30L));
    poolConfig.setMinIdle(2);
    poolConfig.setNumTestsPerEvictionRun(3);
    poolConfig.setTestOnBorrow(true);
    poolConfig.setTestOnReturn(true);
    poolConfig.setBlockWhenExhausted(true);
    return poolConfig;
  }

}
