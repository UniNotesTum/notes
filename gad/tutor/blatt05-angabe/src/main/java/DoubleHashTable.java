import java.lang.reflect.Array;
import java.util.*;

/**
 * Die Klasse DoubleHashTable implementiert eine Hashtabelle, die doppeltes
 * Hashing verwendet.
 *
 * @param <K> der Typ der Schlüssel, die in der Hashtabelle gespeichert werden
 * @param <V> der Typ der Werte, die in der Hashtabelle gespeichert werden
 */
public class DoubleHashTable<K, V> {

    private Pair<K, V>[] a;

    private int size;
    private int count = 0;

    private DoubleHashable<K> hashable;

  /**
   * Diese Methode implementiert h(x, i).
   * 
   * @param key der Schlüssel, der gehasht werden soll
   * @param i der Index, der angibt, der wievielte Hash für den gegebenen Schlüssel
   * berechnet werden soll
   * @return der generierte Hash
   */
  private int hash(K key, int i) {

      return ((int) (hashable.hash(key) + i * hashable.hashTick(key))) % size;

  }

  /**
   * Dieser Konstruktor initialisiert die Hashtabelle.
   * 
   * @param primeSize die Größe 'm' der Hashtabelle; es kann davon ausgegangen
   * werden, dass es sich um eine Primzahl handelt.
   * @param hashableFactory Fabrik, die aus einer Größe ein DoubleHashable<K>-Objekt erzeugt.
   */
  @SuppressWarnings("unchecked")
  public DoubleHashTable(int primeSize, HashableFactory<K> hashableFactory) {

      size = primeSize;

      hashable = hashableFactory.create(primeSize);

      a = (Pair<K, V>[]) new Pair[primeSize];

  }

  /**
   * Diese Methode fügt entsprechend des doppelten Hashens ein Element
   * in die Hashtabelle ein.
   * 
   * @param k der Schlüssel des Elements, das eingefügt wird
   * @param v der Wert des Elements, das eingefügt wird
   * @return 'true' falls das einfügen erfolgreich war, 'false' falls die
   * Hashtabelle voll ist.
   */
  public boolean insert(K k, V v) {

      if (count >= size) return false;

      int i = 0;

      count++;

      int ind = hash(k, i);

      while (a[ind] != null && !a[ind]._1.equals(k)) {

          i++;

          ind  = hash(k, i);

      }

      a[ind] = new Pair<>(k, v);

      return true;

  }

  /**
   * Diese Methode sucht ein Element anhand seines Schlüssels in der Hashtabelle
   * und gibt den zugehörigen Wert zurück, falls der Schlüssel gefunden wurde.
   * 
   * @param k der Schlüssel des Elements, nach dem gesucht wird
   * @return der Wert des zugehörigen Elements, sonfern es gefunden wurde
   */
  public Optional<V> find(K k) {

      for (int i = 0; i < size; i++) {

          int ind = hash(k, i);

          Pair<K, V> pair = a[ind];

          if (pair != null && pair._1.equals(k)) return Optional.of(pair._2);

      }

      return Optional.empty();

  }

  /**
   * Diese Methode ermittelt die Anzahl der Kollisionen, also die Anzahl
   * der Elemente, nicht an der 'optimalen' Position in die Hashtabelle eingefügt
   * werden konnten. Die optimale Position ist diejenige Position, die der
   * erste Aufruf der Hashfunktion (i = 0) bestimmt.
   * 
   * @return die Anzahl der Kollisionen
   */
  public int collisions() {

      int k = 0;

      for (int i = 0; i < a.length; i++) {

          Pair<K, V> pair = a[i];

          if (pair == null) continue;

          if (hash(pair._1, 0) != i) k++;

      }

      return k;

  }
 
  /**
   * Diese Methode berechnet die maximale Anzahl von Aufrufen der Hashfunktion,
   * die nötig waren, um ein einziges Element in die Hashtabelle einzufügen.
   * 
   * @return die berechnete Maximalzahl von Aufrufen
   */
  public int maxRehashes() {

      int max = 0;

      for (int i = 0; i < size; i++) {

          Pair<K, V> pair = a[i];

          if (pair == null) continue;

          int j = 0;

          while (i != hash(pair._1, j)) j++;

          if (j > max) max = j;

      }

      return max;

  }

}
