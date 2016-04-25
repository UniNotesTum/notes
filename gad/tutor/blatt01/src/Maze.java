import java.awt.GridLayout;
import javax.swing.*;
import java.awt.*;
import java.applet.*;
import java.util.*;

public class Maze extends Applet {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  private class Field extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    Point p;
    int x, y;

    public Field (int x, int y) {
      this.x = x;
      this.y = y;
      p = getLocation();
    }

    public void paint (Graphics g) {
      super.paint(g);
      if (spielFeld[x][y]) {
        GradientPaint gradient = new GradientPaint(10, 50, Color.DARK_GRAY, getWidth(), 0, Color.DARK_GRAY);
        ((Graphics2D) g).setPaint(gradient);
      } else {
        GradientPaint gradient = new GradientPaint(10, 50, Color.WHITE, getWidth(), 0, Color.WHITE);
        ((Graphics2D) g).setPaint(gradient);
      }
      g.fillRect(p.getLocation().x, p.getLocation().y, getWidth() * 2, getHeight());
      if (sol != null && sol[x][y]) {
        GradientPaint gradient = new GradientPaint(15, 0, Color.GREEN, getWidth(), 0, Color.LIGHT_GRAY);
        ((Graphics2D) g).setPaint(gradient);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

        g.fillOval((int) (getWidth() * .3), (int) (getHeight() * .3), (int) (getWidth() * .5), (int) (getHeight() * .5));
      }
      if (x == posx && y == posy) {
        GradientPaint gradient = new GradientPaint(15, 0, Color.RED, getWidth(), 0, Color.LIGHT_GRAY);
        ((Graphics2D) g).setPaint(gradient);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

        g.fillOval((int) (getWidth() * .3), (int) (getHeight() * .3), (int) (getWidth() * .5), (int) (getHeight() * .5));
      }
    }
  }

//  public static void setSolution (int[] solution) {
//    r.solution = solution;
//  }

  public static void setSolution (boolean[][] sol) {
    r.sol = sol;
  }

  private JFrame myFrame = new JFrame("Spielfeld");
  private JPanel pan = new JPanel();
  private boolean[][] spielFeld;
  private int posx, posy;

//  private int[] solution;
  private boolean[][] sol = null;

  public Maze () {
  }

  private Maze (int px, int py, boolean[][] feld) {
    spielFeld = new boolean[feld.length][feld[0].length];
    pan.setLayout(new GridLayout(spielFeld.length, spielFeld[0].length));
    Field[][] field = new Field[spielFeld.length][spielFeld[0].length];
    for (int x = 0; x < spielFeld.length; x++) {
      for (int y = 0; y < spielFeld[x].length; y++) {
        field[x][y] = new Field(x, y);
        pan.add(field[x][y]);
        spielFeld[x][y] = feld[x][y];
      }
    }
    myFrame.getContentPane().add(pan);
    myFrame.setSize(400, 400 * (spielFeld.length) / (spielFeld[0].length));
    myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    myFrame.setVisible(true);
    update(px, py, feld);
  }

  private void update (int px, int py, boolean[][] feld) {
    if (feld.length != spielFeld.length) {
      System.err.println("Spielfeld darf sich nicht vergroessern/verkleinern...");
    }
    if (feld[0].length != spielFeld[0].length) {
      System.err.println("Spielfeld darf sich nicht vergroessern/verkleinern...");
    }

    for (int x = 0; x < spielFeld.length; x++) {
      for (int y = 0; y < spielFeld[0].length; y++) {
        spielFeld[x][y] = feld[x][y];
      }
    }
    this.posx = px;
    this.posy = py;
    pan.repaint();
  }

  private static Maze r;

  public static void draw (int x, int y, boolean[][] feld, boolean[][] solution) {
    if (r == null) {
      r = new Maze(x, y, feld);
      r.sol = solution;
      r.update(x, y, feld);
    } else {
      r.sol = solution;
      r.update(x, y, feld);
    }
    try {
      Thread.sleep(0);
    } catch (InterruptedException ie) {
    }
  }

  public static void main (String[] args) {
    boolean[][] spielfeld = generateMaze(25, 25);
    draw(1, 0, spielfeld, null);
    draw(1, 1, spielfeld, null);
  }

  public static boolean[][] generateMaze () {
    return generateMaze(11, 11);
  }

  public static boolean[][] generateMaze (int width, int height) {
    if (width < 3) {
      width = 3;
    }
    if (height < 3) {
      height = 3;
    }
    boolean[][] maze = new boolean[width][height];

    // borders
    for (int i = 0; i < width; i++) {
      maze[i][0] = true;
      maze[i][height - 1] = true;
    }
    for (int i = 0; i < height; i++) {
      maze[0][i] = true;
      maze[width - 1][i] = true;
    }

    // create random obstacles
    Random r = new Random();

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (r.nextInt(4) == 0) {
          maze[x][y] = true;
        }
      }
    }

    // entrance and exit
    maze[1][0] = false;
    maze[1][1] = false;
    maze[width - 1][height - 2] = false;
    maze[width - 2][height - 2] = false;

    return maze;
  }

  public static boolean[][] generateStandardMaze () {
    return generateStandardMaze(11, 11);
  }

  public static boolean[][] generateStandardMaze (int width, int height) {
    if (width < 3) {
      width = 3;
    }
    if (height < 3) {
      height = 3;
    }
    boolean[][] maze = new boolean[width][height];

    // borders
    for (int i = 0; i < width; i++) {
      maze[i][0] = true;
      maze[i][height - 1] = true;
    }
    for (int i = 0; i < height; i++) {
      maze[0][i] = true;
      maze[width - 1][i] = true;
    }

    // create random obstacles
    Random r = new Random();
    r.setSeed(0);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (r.nextInt(4) == 0) {
          maze[x][y] = true;
        }

      }
    }

    // entrance and exit
    maze[1][0] = false;
    maze[1][1] = false;
    maze[width - 1][height - 2] = false;
    maze[width - 2][height - 2] = false;

    return maze;
  }

}
