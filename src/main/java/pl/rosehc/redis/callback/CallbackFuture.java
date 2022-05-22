package pl.rosehc.redis.callback;

import java.util.concurrent.CompletableFuture;

public final class CallbackFuture<T> implements Callback {

  private final CompletableFuture<T> future;

  public CallbackFuture(CompletableFuture<T> future) {
    this.future = future;
  }

  @Override
  @SuppressWarnings("unchecked")
  public void done(CallbackPacket packet) {
    future.complete((T) packet);
  }

  @Override
  public void error(String message) {
    future.completeExceptionally(new CallbackException(message));
  }

  static class CallbackException extends Exception {

    public CallbackException(String message) {
      super(message);
    }
  }
}
