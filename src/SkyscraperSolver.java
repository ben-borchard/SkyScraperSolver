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

        this.guesses = new Stack();

        s = top.length;

        this.grid = new Grid(s, new int[][]{top, right, bottom, left});

    }

    public void solve() {

        // pre-guess logic
        grid.init();
        System.out.println(grid);

        int r = 0;


        // start guessing
        for (int i=0;i<5;i++){
//        while(r == 0){
            r = guess();

        }

        System.out.println(grid);
    }

    private int guess(){
        for (int i = s; i > 0; i--) {
            for (int j = 0; j < s; j++) {
                for (int k = 0; k < s; k++) {
                    if (grid.possible(j, k, i)) {
                        guesses.push(grid);
                        int r = grid.setCellVal(j, k, i);
                        System.out.println("guessing row: "+j+" col: "+k+" is "+i );
                        System.out.println(grid);
                        if (r == 1) {
                            System.out.println("solved");
                            return 1;
                        } else if (r == -1) {

                            if (guesses.isEmpty())
                                return -1;
                            else {
                                System.out.println("bad guess");
                                grid = guesses.pop();
                                grid.setCellNot(j, k, i);
                                return 0;
                            }
                        } else {
                            System.out.println("not enough info");
                            return 0;
                        }
                    }
                }
            }
        }
        return -1;
    }

    private boolean puzzleSquare(int[] top, int[] right, int[] bottom, int[] left) {
        // Transitive property :)
        boolean square = top.length == right.length;
        square &= right.length == bottom.length;
        square &= bottom.length == left.length;
        return square;
    }
}