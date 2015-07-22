import java.util.Stack;

/**
 * Created by bborchard on 7/20/2015.
 */
public class SkyscraperSolver {

    private int s;

    private Grid grid;

    private Stack<Grid> guesses;

    public SkyscraperSolver(int s) {
        this.s = s;
    }

    public SkyscraperSolver(int[] top, int[] right, int[] bottom, int[] left) {

        if (!puzzleSquare(top, right, bottom, left)) {
            System.err.println("The input puzzle is not a square, please check your input again");
            System.exit(1);
        }

        s = top.length;

        this.grid = new Grid(s, new int[][]{top, right, bottom, left});

    }

    public void solve(){
        // pre-guess logic
        grid.init();

        System.out.println(grid);
        System.out.println(grid.pToString());
    }

    private boolean puzzleSquare(int[] top, int[] right, int[] bottom, int[] left) {
        // Transitive property :)
        boolean square = top.length == right.length;
        square &= right.length == bottom.length;
        square &= bottom.length == left.length;
        return square;
    }
}