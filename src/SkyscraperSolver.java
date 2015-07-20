/**
 * Created by bborchard on 7/20/2015.
 */
public class SkyscraperSolver {

    private int s, index, r;

    private int[] top, bottom, left, right, p;
    private Group[] cols, rows;


    public SkyscraperSolver(int[] top, int[] right, int[] bottom, int[] left){
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.left = left;

        if (!puzzleSquare()){
            System.err.println("The input puzzle is not a square, please check your input again");
            System.exit(1);
        }

        s = top.length;

        for (int i=0;i<s;i++){
            cols[i] = new Group(top[i], bottom[i], s);
            rows[i] = new Group(left[i], right[i], s);
        }

        resetPossibilities();
    }

    public int[][] solve(){

        for (int i=0;i<s;i++) {
            cols[i].setContent(nextPossibility());
        }

        return null;
    }

    private boolean puzzleSquare(){
        // Transitive property :)
        boolean square = top.length == right.length;
        square &= right.length == bottom.length;
        square &= bottom.length == left.length;
        return square;
    }

    private void resetPossibilities(){
        r = 0;
        index = 1;
        p = new int[s];
    }

    private int[] nextPossibility(){

        while (true) {
            if (r <= 0) {
                if (index-1 <= r) {
                    p[r] = index;
                    if (r == s-1) {
                        index++;
                        return p;
                    } else {
                        index = p[r] + 1;
                        r++;
                    }
                } else {
                    r--;
                    if (r > 0)
                        index = p[r] + 1;
                    else
                        index = p[0] + 1;
                }
            }
        }
    }

}
