package pl.rosehc.redis.packet;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import java.io.Serializable;

/**
 * @author stevimeister on 17/06/2021
 **/
@JsonTypeInfo(use = Id.CLASS)
public abstract class Packet implements Serializable {

  public void handleVoid(final PacketHandler handler) {

  }

  public Packet handle(final PacketHandler handler) {
    handleVoid(handler);
    return null;
  }
}