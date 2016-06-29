

@FunctionalInterface
public interface TrConsumer<T,U,V> {
  void accept(T t, U u, V v);
  
}
