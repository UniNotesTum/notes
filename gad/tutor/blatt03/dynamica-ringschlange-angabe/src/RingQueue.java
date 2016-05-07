/**
 * Die Klasse RingQueue soll eine zirkuläre Warteschlange auf
 * Basis der Klasse {@link DynamicArray} implementieren.
 */
public class RingQueue {
  private DynamicArray dynArr;
  
  private int size;
  
  private int from;
  
  private int to;
  
  public int getSize() {
    return size;
  }
  
  public boolean isEmpty() {
    return getSize() > 0;
  }
  
  /**
   * Dieser Konstruktor erzeugt eine neue Ringschlange. Ein leere
   * Ringschlange habe stets eine Größe von 0, sowie auf 0
   * gesetzte Objektvariablen to und from. 
   * 
   * @param growthFactor der Wachstumsfaktor des zugrundeliegenden
   * dynamischen Feldes
   * @param maxOverhead der maximale Overhead des zugrundeliegenden
   * dynamischen Feldes
   */
  public RingQueue (int growthFactor, int maxOverhead) {
    dynArr = new DynamicArray(growthFactor, maxOverhead);
    size = 0;
    from = 0;
    to = 0;
  }
  
  /**
   * Diese Methode reiht ein Element in die Schlange ein.
   * 
   * @param value der einzufügende Wert
   */
  public void enqueue(int value) {
      Interval interval = dynArr.reportUsage(new NonEmptyInterval(from, to), ++size);
      from = interval.getFrom();
      to   = (interval.getTo() + 1)%dynArr.getInnerLength();
      dynArr.set(to, value);
  }
  
  /**
   * Diese Methode entfernt ein Element aus der Warteschlange.
   * 
   * @return das entfernte Element
   */
  public int dequeue() {
    int element = dynArr.get(from);
    Interval interval = dynArr.reportUsage(new NonEmptyInterval(from, to), --size);
    from = (interval.getFrom()+1)%dynArr.getInnerLength();
    to   = interval.getTo();
    return element;
  }

  public static void main(String[] args) {
      RingQueue queue = new RingQueue(2, 4);
      queue.enqueue(1);
      queue.enqueue(2);
      queue.enqueue(3);
      System.out.println(queue.dynArr);
  }
}
