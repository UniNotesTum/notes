/**
 * Die Klasse DynamicStack soll einen Stapel auf
 * Basis der Klasse {@link DynamicArray} implementieren.
 */
public class DynamicStack {
  private DynamicArray dynArr;
  
  /**
   * Dieses Feld speichert die Anzahl der Elemente auf dem Stapel.
   */
  private int length;
  
  public int getLength() {
    return length;
  }
  
  public DynamicStack (int growthFactor, int maxOverhead) {
    dynArr = new DynamicArray(growthFactor, maxOverhead);
    length = 0;
  }
  
  /**
   * Diese Methode legt ein Element auf den Stapel.
   * 
   * @param value das Element, das auf den Stapel gelegt werden soll
   */
  public void pushBack (int value) {
        dynArr.reportUsage(new NonEmptyInterval(0, length), ++length);
		dynArr.set(length - 1, value);
  }

  /**
   * Diese Methode nimmt ein Element vom Stapel.
   * @return das entfernte Element
   */
  public int popBack () {
      length--;
      int element = dynArr.get(length);
      dynArr.reportUsage(new NonEmptyInterval(0, length), length);
      return element;
  }

	public static void main(String[] args) {
		DynamicStack stack = new DynamicStack(2, 4);
		for (int i = 0; i < 10; i++)
			stack.pushBack(i);
		System.out.println(stack.dynArr);
        System.out.println(stack.popBack());
        System.out.println(stack.popBack());
        System.out.println(stack.popBack());
        System.out.println(stack.popBack());
        System.out.println(stack.dynArr);
        System.out.println(stack.popBack());
        System.out.println(stack.popBack());
        System.out.println(stack.popBack());
        System.out.println(stack.dynArr);
        stack.pushBack(10);
        stack.pushBack(20);
        System.out.println(stack.dynArr);
	}
}
