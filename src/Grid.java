/**
 * Created by bborchard on 7/21/2015.
 */
public class Grid {

    private Cell[][] cells;

    private int[][] puzzleStatus;
    private int[][] initialValues;

    private int filled;


    public Grid(int s, int[][] puzzle, int[][] initialValues){
        this(s, puzzle);
        this.initialValues = initialValues;
    }

    public Grid(int s, int[][] puzzle){
        cells = new Cell[s][s];
        for (int i=0;i<s;i++)
            for(int j=0;j<s;j++)
                cells[i][j] = new Cell(i,j,s);

        this.filled = 0;

        this.puzzleStatus = puzzle;
    }

    public Grid(Cell[][] cells, int filled, int[][] puzzle){
        this.cells = cells;
        this.filled = filled;
        this.puzzleStatus = puzzle;
    }

    public int setCellNot(int row, int col, int val){
        return cells[row][col].not(val, this);
    }

    public boolean cellFilled(int row, int col){
        return cells[row][col].filled();
    }

    public int setCellVal(int row, int col, int val){



        int r = cells[row][col].setVal(val);
        if (r == -1)
            return r;
        else if (r == 1)
            return 0;

        filled++;

        for(int i=0;i<cells.length;i++) {

            r = cells[row][i].not(val, this);

            if (r != 0) { return r; }
        }
        Cell[] yarray = new Cell[cells.length];
        for(int i=0;i<cells.length;i++) {
            r = cells[i][col].not(val, this);
            yarray[i] = cells[i][col];
            if (r != 0) { return r; }
        }

        if (checkCellArray(cells[row], puzzleStatus[3][row], puzzleStatus[1][row]) == -1)
            return -1;
        if (checkCellArray(yarray, puzzleStatus[0][col], puzzleStatus[2][col]) == -1)
            return -1;

        if (filled == cells.length*cells.length){
            return solved() ? 1 : -1;
        }
        return 0;
    }

    public int checkRowCol(int row, int col, int val){
        // Col
        int cWithVal = -1;
        boolean onePossibility = true;
        for (int i=0;i<cells.length;i++){
            if (cells[i][col].possible(val)){
                if (cWithVal != -1) {
                    onePossibility = false;
                    break;
                } else {
                    cWithVal = i;
                }
            }
        }
        if (onePossibility && cWithVal != -1) {
            int r = setCellVal(cWithVal, col, val);
            if ( r != 0 ) { return r; }
        }


        // Row
        cWithVal = -1;
        onePossibility = true;
        for (int i=0;i<cells.length;i++){
            if (cells[row][i].possible(val)){
                if (cWithVal != -1) {
                    onePossibility = false;
                    break;
                } else {
                    cWithVal = i;
                }
            }
        }
        if (onePossibility && cWithVal != -1) {
            int r = setCellVal(row, cWithVal, val);
            if ( r != 0 ) { return r; }
        }
        return 0;
    }

    public void init(){

        // Initial Cells
        if (initialValues != null){
            insertInitialValues();
        }

        // Top
        for (int i=0;i<cells.length;i++){
            if(puzzleStatus[0][i] == 1)
                setCellVal(0, i, cells.length);
            for(int j=puzzleStatus[0][i]-2;j>=0;j--){
                for(int k=cells.length;k>=cells.length-(puzzleStatus[0][i]-2-j);k--) {
                    cells[j][i].not(k, this);
                }
            }
        }

        // Right
        for (int i=0;i<cells.length;i++){
            if (puzzleStatus[1][i] == 1)
                setCellVal(i, cells.length - 1, cells.length);
            for(int j=cells.length-(puzzleStatus[1][i]-1);j<cells.length;j++){
                for(int k=cells.length;k>=cells.length-(j-(cells.length-(puzzleStatus[1][i]-1)));k--) {
                    cells[i][j].not(k, this);
                }
            }
        }


        // Bottom
        for (int i=0;i<cells.length;i++){
            if (puzzleStatus[2][i] == 1)
                setCellVal(cells.length - 1, i, cells.length);
            for(int j=cells.length-(puzzleStatus[2][i]-1);j<cells.length;j++){
                for(int k=cells.length;k>=cells.length-(j-(cells.length-(puzzleStatus[2][i]-1)));k--) {
                    cells[j][i].not(k, this);
                }
            }
        }



        // Left
        for (int i=0;i<cells.length;i++){
            if (puzzleStatus[3][i] == 1)
                setCellVal(i, 0, cells.length);
            for(int j=puzzleStatus[3][i]-2;j>=0;j--){
                for(int k=cells.length;k>=cells.length-(puzzleStatus[3][i]-2-j);k--) {
                    cells[i][j].not(k, this);
                }
            }
        }
    }

    private void insertInitialValues(){
        for(int i=0;i<cells.length;i++)
            for(int j=0;j<cells.length;j++)
                if (initialValues[i][j] != 0)
                    setCellVal(i,j,initialValues[i][j]);
    }

    private int checkCellArray(Cell[] group, int ltSkyscrapers, int rbSkyscrapers){
        int[] dupArray = new int[group.length];
        int ssFromLeftOrTop = 0;
        int ssFromRightOrBottom = 0;
        int ltMax = 0;
        int rbMax = 0;
        boolean filled = true;


        for (int i=0;i<cells.length;i++){

            if (group[i].getVal() != 0) {
                dupArray[group[i].getVal() - 1]++;

                if (group[i].getVal() > ltMax) {
                    ssFromLeftOrTop++;
                    ltMax = group[i].getVal();
                }

                int s = group.length;
                if (group[s-i-1].getVal() > rbMax) {
                    ssFromRightOrBottom++;
                    rbMax = group[s-i-1].getVal();
                }
            } else
                filled = false;
        }

        // Check for duplicate numbers
        for (int n : dupArray)
            if (n > 1)
                return 0;

        if (filled && (ssFromLeftOrTop == ltSkyscrapers || ltSkyscrapers == 0) && (ssFromRightOrBottom == rbSkyscrapers || rbSkyscrapers == 0))
            return 1; // Good
        else if (filled && ((ssFromLeftOrTop != ltSkyscrapers && ltSkyscrapers != 0) || (ssFromRightOrBottom != rbSkyscrapers && rbSkyscrapers != 0))) {
//            System.out.println("filled is bad, lt val: "+ltSkyscrapers+" lt actual: "+ssFromLeftOrTop+" | rb val: "+rbSkyscrapers+" rb actual: "+ssFromRightOrBottom);
            return -1; // Bad
        }
        else if ((ssFromLeftOrTop > ltSkyscrapers && ltSkyscrapers != 0) || (ssFromRightOrBottom > rbSkyscrapers && rbSkyscrapers != 0)) {
//            System.out.println("too many skyscrapers, lt val: "+ltSkyscrapers+" lt actual: "+ssFromLeftOrTop+" | rb val: "+rbSkyscrapers+" rb actual: "+ssFromRightOrBottom);
            return -1; // Bad
        }
        else
            return 0; // Unsure
    }

    private boolean solved(){

        for (int i=0;i<cells.length;i++)
            if (checkCellArray(cells[i], puzzleStatus[3][i], puzzleStatus[1][i]) == -1)
                return false;

        for (int i=0;i<cells.length;i++) {
            Cell[] yarray = new Cell[cells.length];
            for (int j = 0; j < cells.length; j++)
                yarray[j] = cells[j][i];
            if (checkCellArray(yarray, puzzleStatus[0][i], puzzleStatus[2][i]) == -1)
                return false;
        }

        return true;
    }

    public boolean possible(int row, int col, int val){
        return cells[row][col].possible(val);
    }

    public String toString(){
        String str = "";
        for(Cell[] cs : cells) {
            for (Cell c : cs)
                str += c + " ";
            str += "\n";
        }
        return str;
    }

    public Grid clone(){
        Cell[][] newCells = new Cell[cells.length][cells.length];
        for (int i=0;i<cells.length;i++){
            for (int j=0;j<cells.length;j++){
                newCells[i][j] = cells[i][j].clone();
            }
        }
        return new Grid(newCells, this.filled, this.puzzleStatus);
    }
}
