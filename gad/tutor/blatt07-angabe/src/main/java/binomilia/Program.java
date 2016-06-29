package binomilia;

public class Program {

  public static void main(String[] args) {
    BinomialHeap bh = new BinomialHeap();
    for (int i = 0; i < 10; i++) {
      bh.insert(i);
    }
    for (int i = 0; i < 10; i++) {
      bh.insert(20 - i);
    }
    for (int i = 0; i < 10; i++) {
      bh.insert(20 + i);
    }

    for (int i = 0; i < 30; i++)
      System.out.println(bh.deleteMin());

  }

}
