package radixchen;

public interface KeyDescriptor<K> {
  /**
   * Diese Methode gibt die Anzahl der Buckets zurück, die zum Sortieren
   * in RadixSort verwendet werden sollen (vgl. die Konstante K in den Vorlesungsfolien).
   * @return die Anzahl an Buckets
   */
  int buckets();
  
  /**
   * Diese Methode gibt die Anzahl an Stellen zurück, die jeder Schlüssel
   * beim Sortieren mit RadixSort hat (vgl. die Konstante d in den Vorlesungsfolien).
   * @return die Anzahl an Stellen
   */
  int digits();
  
  /**
   * Diese Methode berechnet den Schlüssel an Stelle 'digit' des
   * Elements 'element'.
   * 
   * @param element das Element, von dem der Schlüssel berechnet werden soll
   * @param digit die Stelle, mit der der Schlüssel berechnet werden soll
   * @return der Schlüssel
   */
  int key(K element, int digit);
}
