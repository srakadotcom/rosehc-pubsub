package pl.rosehc.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import pl.rosehc.redis.packet.Packet;
import pl.rosehc.redis.packet.PacketCoderHelper;
import redis.clients.jedis.BinaryJedisPubSub;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class RedisPubSubAdapter extends BinaryJedisPubSub {

  private final Map<Class<? extends Packet>, Consumer> handlerMap = new ConcurrentHashMap<>();

  @Override
  public void onMessage(final byte[] channel, final byte[] message) {
    final Packet packet = PacketCoderHelper.readValue(message);
    if (packet != null) {
      this.handlerMap.entrySet().stream()
          .filter(entry -> entry.getKey().isAssignableFrom(packet.getClass())).findFirst()
          .ifPresent(entry -> entry.getValue().accept(packet));
    }
  }

  <T extends Packet> void registerHandler(final Class<T> type, final Consumer<T> handler) {
    this.handlerMap.put(type, handler);
  }
}
