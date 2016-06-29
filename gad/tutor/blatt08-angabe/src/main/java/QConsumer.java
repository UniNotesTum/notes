

@FunctionalInterface
public interface QConsumer<T,U,V,W> {
  void accept(T t, U u, V v, W w);
  
}
