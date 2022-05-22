package packet;

import pl.rosehc.redis.callback.CallbackPacket;
import pl.rosehc.redis.packet.Packet;
import pl.rosehc.redis.packet.PacketHandler;

public class StanKoniaRequestPacket extends CallbackPacket {
  @Override
  public Packet handle(PacketHandler handler) {
    return new StanKoniaResponsePacket("ciekły");
  }

}
