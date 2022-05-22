package pl.rosehc.redis.callback;

import java.util.function.Consumer;

public class CallbackBuilder<T extends CallbackPacket> implements Callback {

  private Consumer<T> packetConsumer = x -> {
  };
  private Consumer<String> errorConsumer = x -> {
    throw new RuntimeException("Unhandled error: " + x);
  };

  public CallbackBuilder<T> handleDone(Consumer<T> packetConsumer) {
    this.packetConsumer =
        this.packetConsumer != null ? this.packetConsumer.andThen(packetConsumer) : packetConsumer;
    return this;
  }

  public CallbackBuilder<T> handleError(Consumer<String> errorConsumer) {
    this.errorConsumer =
        this.errorConsumer != null ? this.errorConsumer.andThen(errorConsumer) : errorConsumer;
    return this;
  }

  @Override
  @SuppressWarnings("unchecked")
  public void done(CallbackPacket packet) {
    packetConsumer.accept((T) packet);
  }

  @Override
  public void error(String message) {
    errorConsumer.accept(message);
  }
}
