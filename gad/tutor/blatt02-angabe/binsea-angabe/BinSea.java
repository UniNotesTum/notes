
/**
 * Die Klasse BinSea stellt Methoden bereit, in sortierten Feldern binär
 * nach Wertebereichen zu suchen.
 */
class BinSea {
  /**
   * Diese Methode sucht nach einem Wert in einem einem sortierten Feld. Sie
   * soll dabei dazu verwendet werden können, den ersten bzw. letzten Index in
   * einem Intervall zu finden. Wird kein passender Index gefunden, wird
   * -1 zurückgegeben.
   * 
   * Beispiel:
   * 
   *             0 <-----------------------> 4
   * sortedData: [-10, 33, 50, 99, 123, 4242 ]
   * value: 80             ^   ^
   *                       |   |
   *                       |   |- Ergebnis (3) für ersten Index im Intervall, da 99
   *                       |      als erster Wert im Feld größer als 80 ist
   *                       |
   *                       |- Ergebnis (2) für letzten Index im Intervall, da 50
   *                          als letzter Wert kleiner als 80 ist
   * 
   * @param sortedData das sortierte Feld, in dem gesucht wird
   * @param value der Wert der Intervallbegrenzung, der dem gesucht wird
   * @param lower true für untere Intervallbegrenzung, false für obere Intervallbegrenzung
   * @return der passende Index, -1 wenn dieser nicht gefunden werden kann
   */
  private static int search (int[] sortedData, int value, boolean lower) {

		int left = 0, right = sortedData.length - 1;

		int middle = (left + right)/2;

		while (left <= right) {
			if (sortedData[middle] < value) {
				left = middle + 1;
			} else if (sortedData[middle] > value) {
				right = middle - 1;
			} else return middle;
			middle = (left + right)/2;
		}

		if ((right < 0 && lower) || (left >= sortedData.length && !lower)) return -1;
		
		return lower ? right : left;
		
  }

  /**
   * Diese Methode sucht ein Intervall von Indices eines sortierten Feldes, deren Werte
   * in einem Wertebereich liegen. Es soll dabei binäre Suche verwendet werden.
   * 
   * Beispiel: 0 <-----------------------> 4 sortedData: [-10, 33, 50, 99, 123,
   *                     4242 ] valueRange: [80;700] ^ ^ | |- Ergebnis (4) für
   *                     obere Intervallbegrenzung, | da 123 als letzter Wert
   *                     kleiner als 700 als | 700 ist | | |- Ergebnis (3) für
   *                     untere Intervallbegrenzung, da 99 als erster Wert
   *                     größer als 80 ist
   * 
   * @param sortedData das sortierte Feld, in dem gesucht wird
   * @param valueRange der Wertebereich, zu dem Indices gesucht werden
   */
  public static Interval search (int[] sortedData, Interval valueRange) {
    int start = search(sortedData, valueRange.getFrom(), false);
		int end   = search(sortedData, valueRange.getTo(), true);
		if (end <= start) return new EmptyInterval();
		return new NonEmptyInterval(start, end);
  }

}
