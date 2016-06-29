package triangulina;

public class TriangulinaResult {
  private int revenue;
  
  public int getRevenue () {
    return revenue;
  }
  
  int[] path;
  
  public int[] getPath () {
    return path;
  }
  
  public TriangulinaResult (int revenue, int[] path) {
    this.revenue = revenue;
    this.path = path;
  }
}
