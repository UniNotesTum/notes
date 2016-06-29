package triangulina;

public class Pair implements Comparable {
  public final int _1;
  public final int _2;

  public Pair(int _1, int _2) {
    this._1 = _1;
    this._2 = _2;
  }

  @Override public boolean equals (Object o) {
    return o instanceof Pair && _1 == _1 && _2 == _2;
  }


  @Override public int compareTo(Object o) {

    if (!(o instanceof Pair)) return 0;

    return _2 - ((Pair) o)._2;

  }



}
