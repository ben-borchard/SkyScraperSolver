/**
 * Created by bborchard on 7/21/2015.
 */
public class Cell {

    int[] possibilities;
    int numpos;
    int val;
    int row, col;

    public Cell(int row, int col, int s){

        this.possibilities = new int[s];
        this.row = row;
        this.col = col;

        for (int i=0;i<s;i++)
            this.possibilities[i] = i+1;
        this.numpos = s;
        this.val = 0;
    }

    public void setVal(int val){
        System.out.println("Setting row "+ row +", col "+ col +" as "+val);
        this.val = val;
    }

    public int getVal(){
        return this.val;
    }

    public void not(int possibility, Grid g){

        if (val == 0 && possibilities[possibility - 1] != 0) {
            possibilities[possibility - 1] = 0;
            numpos--;



            if (numpos == 1) {
                for (int p : possibilities)
                    if (p != 0)
                        g.setCell(row, col,p);
            }

            g.checkRowCol(row, col, possibility);
        }
    }

    public boolean filled(){
        return val != 0;
    }

    public String toString(){
        return val+"";
    }

    public String pToString(){
        String str =  "| ";
        for (int i : possibilities){
            str += i+" ";
        }
        return str+"|";
    }


}
