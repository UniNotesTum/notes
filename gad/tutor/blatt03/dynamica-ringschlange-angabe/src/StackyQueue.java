/**
 * Die Klasse StackyQueue soll eine Warteschlange auf
 * Basis der Klasse {@link DynamicStack} implementieren. Es
 * soll ausschließlich die Klasse {@link DynamicStack} zur
 * Datenspeicherung verwendet werden.
 */
public class StackyQueue {

    DynamicStack stack;

    DynamicStack tempStack;

  /**
   * Diese Methode ermittelt die Länge der Warteschlange.
   * @return die Länge der Warteschlange
   */
  public int getLength() {
    return stack.getLength();
  }
  
  /**
   * Dieser Kontruktor initialisiert eine neue Schlange.
   * 
   * @param growthFactor
   * @param maxOverhead
   */
  public StackyQueue (int growthFactor, int maxOverhead) {
      stack = new DynamicStack(growthFactor, maxOverhead);
      tempStack = new DynamicStack(growthFactor, maxOverhead);
  }
  
  /**
   * Diese Methode reiht ein Element in die Schlange ein.
   * 
   * @param value der einzufügende Wert
   */
  public void enqueue (int value) {

      while (stack.getLength() > 0) {
          tempStack.pushBack(stack.popBack());
      }

      stack.pushBack(value);

      while (tempStack.getLength() > 0) {
          stack.pushBack(tempStack.popBack());
      }

  }
  
  /**
   * Diese Methode entfernt ein Element aus der Warteschlange.
   * 
   * @return das entfernte Element
   */
  public int dequeue () {
    return stack.popBack();
  }

}
