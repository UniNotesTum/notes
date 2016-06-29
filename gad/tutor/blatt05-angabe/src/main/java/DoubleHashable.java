
public interface DoubleHashable<K> {
  /**
   * Diese Methode implementiert h(key).
   * 
   * @param key der Schlüssel, der gehasht werden soll
   * @return der generierte Hash
   */
  public long hash (K key);

  /**
   * Diese Methode implementiert h'(key).
   * 
   * @param key der Schlüssel, der gehasht werden soll
   * @return der generierte Hash
   */
  public long hashTick (K key);
}
