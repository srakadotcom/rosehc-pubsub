package packet;

import pl.rosehc.redis.callback.CallbackPacket;

public class StanKoniaResponsePacket extends CallbackPacket {
  private final String stanKonia;

  public StanKoniaResponsePacket(String stanKonia) {
    this.stanKonia = stanKonia;
  }

  private StanKoniaResponsePacket() {
    this.stanKonia = null;
  }

  public String getStanKonia() {
    return stanKonia;
  }
}
