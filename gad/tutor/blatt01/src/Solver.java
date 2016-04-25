/**
 * Created by yuriyarabskyy on 21/04/16.
 */
public class Solver {

    //directions: 0: down, 1: right, 2: up, 3: left

    private final static int width  = 20;
    private final static int height = 20;

    private int x = 1, y = 0, direction = 1;

    private boolean[][] field, solution;

    public int getX() { return x; }
    public int getY() { return y; }
    public int getDirection() { return direction; }
    public boolean[][] getField() { return field; }
    public boolean[][] getSolution() { return solution; }

    public void setField(boolean[][] field) { this.field = field; }
    public void setSolution(boolean[][] solution) { this.solution = solution; }

    private void setX(int x) { this.x = x; }
    private void setY(int y) { this.y = y; }
    private void setDirection(int direction) { this.direction = direction;}

    public void walk(int x, int y, int direction) {

        solution[x][y] = true;

        setDirection(controlDirection(direction));
        direction = getDirection();

        switch (direction) {
            case 0:
                setX(++x);
                break;
            case 1:
                setY(++y);
                break;
            case 2:
                setX(--x);
                break;
            case 3:
                setY(--y);
                break;
            default:
                break;
        }

        Maze.draw(x, y, field, solution);

        if (backToStart()) {
            System.out.println("Exit could not be reached");
            System.exit(0);
        }

        if (finished()) {
            System.out.println("Successfully got out!");
            System.exit(0);
        }

    }

    private boolean backToStart() {
        return x == 1 && y == 0;
    }

    private boolean finished() {
        return x == width - 1 && y == height - 2;
    }

    //changes the direction of the dot, so that it would follow the wall
    private int controlDirection(int direction) {

        switch (direction) {
            case 0:
                if (!field[x][y - 1]) return 3; //left

                if (field[x][y - 1] && !field[x + 1][y]) return direction; //down

                if (field[x + 1][y] && !field[x][y + 1]) return 1; //right

                if (field[x + 1][y] && field[x][y + 1]) return 2; //up

                break;

            case 1:
                if (!field[x + 1][y]) return 0; //down

                if (field[x + 1][y] && !field[x][y + 1]) return direction; //right

                if (field[x][y + 1] && !field[x - 1][y]) return 2; //up

                if (field[x][y + 1] && field[x - 1][y]) return 3; //left

                break;

            case 2:
                if (!field[x][y + 1]) return 1; //right

                if (field[x][y + 1] && !field[x - 1][y]) return direction; //up

                if (field[x - 1][y] && !field[x][y - 1]) return 3; //left

                if (field[x - 1][y] && field[x][y - 1]) return 0; //down

                break;

            case 3:
                if (!field[x - 1][y]) return 2; //up

                if (field[x - 1][y] && !field[x][y - 1]) return direction; //left

                if (field[x][y - 1] && !field[x + 1][y]) return 0; //down

                if (field[x][y - 1] && field[x + 1][y]) return 1; //right

                break;

            default:
                break;
        }

        return direction;

    }

    public static void main(String[] args) {

        Solver solver = new Solver();

        solver.setField(Maze.generateMaze(width, height));

        solver.setSolution(new boolean[width][height]);

        Maze.draw(solver.getX(), solver.getY(), solver.getField(), solver.getSolution());

        while (true) {

            try {

                Thread.sleep(100);

                solver.walk(solver.getX(), solver.getY(), solver.getDirection());

            } catch (InterruptedException e) { e.printStackTrace(); }

        }

    }

}
