package triangulina;

public class Triangulina {

    public static int leftP(int n) {
        if (n == 0) return Integer.MIN_VALUE;

        for (int i = 0; ; i++) {

            int k = i * (i + 1) / 2;

            if (n == k) return Integer.MIN_VALUE;

            if (n - k < 0) {  // we are in the right row now

                return n - i;

            }

        }

    }

    public static int rightP(int n) {
        if (n == 0) return Integer.MIN_VALUE;

        for (int i = 0; ; i++) {

            int k = i * (i + 1) / 2;

            if (n == (k-1)) return Integer.MIN_VALUE;

            if (n - k < 0) {  // we are in the right row now

                return n - i + 1;

            }

        }
    }

    public static int getLine(int n) {
        for (int i = 0; ; i++) {
            int k = i * (i + 1) / 2;
            if (n - k < 0) {  // we are in the right row now
                return i;
            }
        }
    }

  public static TriangulinaResult findMaxSumPath(int[] a) {

      int[] triangle = new int[a.length];

      for(int i = 0; i < a.length; i++) triangle[i] = a[i];

      for (int i = 1; i < triangle.length; i++) {

            int l = leftP(i), r = rightP(i);

            if (l == Integer.MIN_VALUE) l = r;
            if (r == Integer.MIN_VALUE) r = l;

            triangle[i] = triangle[l] > triangle[r] ? triangle[l] + triangle[i] : triangle[r] + triangle[i];

      }

      int max = triangle.length - 1;

      int lastLine = getLine(triangle.length - 1);

      for (int i = triangle.length - lastLine; i < triangle.length; i++) {
          if (triangle[max] < triangle[i]) max = i;
      }

      int[] path = new int[lastLine];

      int res = triangle[max];

      path[--lastLine] = max;

      while (lastLine > 0) {

          int l = leftP(max), r = rightP(max);

          if (l == Integer.MIN_VALUE) l = r;
          if (r == Integer.MIN_VALUE) r = l;

          max = triangle[l] > triangle[r] ? l : r;

          path[--lastLine] = max;

      }

      return new TriangulinaResult(res, path);

  }

  public static void main (String[] args) {
    int[] triangle = new int[] {5, 6, 9, 7, 8, 2, 1, 4, 3, 5};
    TriangulinaResult tr = findMaxSumPath(triangle);
    System.out.print("Path: ");
    for (int i = 0; i < tr.getPath().length; i++) {
      if(i > 0)
        System.out.print(" -> ");
      System.out.print(tr.getPath()[i]);
    }
    System.out.println();
    System.out.println("Revenue: " + tr.getRevenue());
  }

}
