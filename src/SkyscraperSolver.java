import java.util.Stack;

/**
 * Created by bborchard on 7/20/2015.
 */
public class SkyscraperSolver {

    private int s;

    private Grid grid;

    private Stack<Grid> grids;
    private Stack<int[]> guesses;

    public SkyscraperSolver(int[][] initialCellsVals, int[] top, int[] right, int[] bottom, int[] left) {
        if (!puzzleSquare(top, right, bottom, left)) {
            System.err.println("The input puzzle is not a square, please check your input again");
            System.exit(1);
        }

        this.grids = new Stack();
        this.guesses = new Stack();

        s = top.length;

        this.grid = new Grid(s, new int[][]{top, right, bottom, left}, initialCellsVals);
    }

    public SkyscraperSolver(int[] top, int[] right, int[] bottom, int[] left) {

        if (!puzzleSquare(top, right, bottom, left)) {
            System.err.println("The input puzzle is not a square, please check your input again");
            System.exit(1);
        }

        this.grids = new Stack();
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
//        for (int i=0;i<10;i++){
        while(r == 0){
            r = guess();
        }

        System.out.println(grid);
    }

    private int guess(){
        for (int i = s; i > 0; i--) {
            for (int j = 0; j < s; j++) {
                for (int k = 0; k < s; k++) {

                    if (!grid.cellFilled(j,k) && grid.possible(j, k, i)) {
                        grids.push(grid.clone());
                        guesses.push(new int[]{j, k, i});
//                        System.out.println("guessing row: "+j+" col: "+k+" is "+i );
//                        System.out.println(grids.size() + "");
                        int r = grid.setCellVal(j, k, i);
//                        System.out.println(grid);
                        if (r == 1) {
//                            System.out.println("solved");
                            return 1;
                        } else if (r == -1) {
                            while(r == -1) {
                                if (grids.isEmpty()) {
//                                    System.out.println("unsolvable");
                                    return -1;
                                }
                                int[] lastGuess = guesses.pop();
//                                System.out.println("bad guess, removing possibility of row: " + lastGuess[0] + " col: " + lastGuess[1] + " being " + lastGuess[2]);
//                                System.out.println(grid);
                                grid = grids.pop();
//                                System.out.println(grids.size() + "");
                                r = grid.setCellNot(lastGuess[0], lastGuess[1], lastGuess[2]);
                            }
                            return r;
                        } else {
//                            System.out.println("not enough info");
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