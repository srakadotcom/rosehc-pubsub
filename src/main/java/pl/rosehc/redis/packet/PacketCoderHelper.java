package pl.rosehc.redis.packet;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.IOException;

public final class PacketCoderHelper {

  private static final ObjectMapper MAPPER = JsonMapper.builder()
      .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
      .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
      .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
      .enable(Feature.WRITE_BIGDECIMAL_AS_PLAIN)
      .build()
      .setSerializationInclusion(Include.NON_NULL)
      .setVisibility(VisibilityChecker.Std.defaultInstance()
          .withFieldVisibility(Visibility.ANY).withGetterVisibility(Visibility.NONE)
          .withSetterVisibility(Visibility.NONE).withCreatorVisibility(Visibility.NONE));

  private PacketCoderHelper() {
  }

  public static Packet readValue(final byte[] data) {
    try {
      return MAPPER.readValue(data, Packet.class);
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static byte[] writeValue(final Packet packet) {
    try {
      return MAPPER.writeValueAsBytes(packet);
    } catch (final JsonProcessingException e) {
      throw new UnsupportedOperationException(e);
    }
  }
}
