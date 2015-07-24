/**
 * Created by bborchard on 7/21/2015.
 */
public class Cell {

    private int[] possibilities;
    private int numpos;
    private int val;
    private int row, col;

    public Cell(int row, int col, int[] possibilities, int val, int numpos){
        this.row = row;
        this.col = col;
        this.numpos = numpos;
        this.val = val;
        this.possibilities = possibilities;
    }

    public Cell(int row, int col, int s){

        this.possibilities = new int[s];
        this.row = row;
        this.col = col;

        for (int i=0;i<s;i++)
            this.possibilities[i] = i+1;
        this.numpos = s;
        this.val = 0;
    }

    public int setVal(int val){

        if (possibilities[val-1] == 0)
            return -1;
        if (this.val == val){
            return 1;
        }
        possibilities = new int[possibilities.length];
        possibilities[val-1] = val;
        numpos = 1;
        this.val = val;
        return 0;
    }

    public int getVal(){
        return this.val;
    }

    public int not(int possibility, Grid g){

        if (val == 0 && possibilities[possibility - 1] != 0) {
            possibilities[possibility - 1] = 0;
            numpos--;



            if (numpos == 1) {
                for (int p : possibilities)
                    if (p != 0)
                        return g.setCellVal(row, col, p);
            }

            return g.checkRowCol(row, col, possibility);
        }
        return 0;
    }

    public boolean filled(){
        return val != 0;
    }

    public String toString() {
        String str = "| ";
        for (int i : possibilities) {
            str += i + " ";
        }
        return str + "|";
//        return val+"";
    }

    public boolean possible(int val){
        return possibilities[val-1] != 0;
    }

    public Cell clone(){
        return new Cell(row, col, possibilities.clone(), val, numpos);
    }


}
