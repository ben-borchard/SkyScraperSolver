/**
 * Created by bborchard on 7/20/2015.
 */
public class SkyscraperSolver {

    private int s, index, r;

    private int[] top, bottom, left, right, p;
    private Group[] cols, rows;

    public SkyscraperSolver(int s){
        this.s = s;
        resetPossibilities();
    }

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
            nextPossibility();
            cols[i].setContent(p);
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

    private void nextPossibility(){

        while (true) {
            if (r >= 0) {
                if (index-2 <= r) {
                    p[r] = index;
                    if (r == s-1) {
                        index++;
                        System.out.print("----------->");
                        return;
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
            } else
                return;
            printp();
        }
    }

    public void nextPossibilityTest(){
        for(int i=0;i<24;i++){
            nextPossibility();
            printp();
        }
    }

    private void printp(){
        System.out.print(" r: "+r+" | index: "+index+" | ");
        for (int n : p){
            System.out.print( n+" " );
        }
        System.out.println("");
    }

    public static void main(String[] args){
//        SkyscraperSolver ss = new SkyscraperSolver(4);
//        ss.nextPossibilityTest();
//        combination(new Integer[5], 5);
        int[] a = new int[] {1,2,3,4,5};
//        printar(a);
//        swap(a,0,0);
//        printar(a);
        allCombos(a, 0);
    }

    public static void allCombos(int[] a, int in){
        if (in == a.length-1)
            printar(a);
        else
            for(int i=in;i<a.length;i++) {
//                allCombos(swapr(a, in, i),in+1);
                swap(a, in, i);
                allCombos(a, in + 1);
            }
    }

    public static int[] swapr(int[] a, int in, int i){
        int[] r = a.clone();
        int temp = r[in];
        r[in] = r[i];
        r[i] = temp;
        return r;
    }

    public static void swap(int[] a, int in, int i) {
        int temp = a[in];
        a[in] = a[i];
        a[i] = temp;
    }

    public static void combination(Object[]  elements, int K){

        // get the length of the array
        // e.g. for {'A','B','C','D'} => N = 4
        int N = elements.length;

        if(K > N){
            System.out.println("Invalid input, K > N");
            return;
        }

        // get the combination by index
        // e.g. 01 --> AB , 23 --> CD
        int combination[] = new int[K];

        // position of current index
        //  if (r = 1)              r*
        //  index ==>        0   |   1   |   2
        //  element ==>      A   |   B   |   C
        int r = 0;
        int index = 0;

        while(r >= 0){
            // possible indexes for 1st position "r=0" are "0,1,2" --> "A,B,C"
            // possible indexes for 2nd position "r=1" are "1,2,3" --> "B,C,D"

            // for r = 0 ==> index < (4+ (0 - 2)) = 2
            if(index <= (N + (r - K))){
                combination[r] = index;

                // if we are at the last position print and increase the index
                if(r == K-1){

                    //do something with the combination e.g. add to list or print
                    printar(combination);
                    index++;
                }
                else{
                    // select index for next position
                    index = combination[r]+1;
                    r++;
                }
            }
            else{
                r--;
                if(r > 0)
                    index = combination[r]+1;
                else
                    index = combination[0]+1;
            }
//            System.out.print("r: "+r+" index: "+index+"\n");
        }
    }
    private static void printar(int[] arr){
        for (int n : arr){
            System.out.print( n+" " );
        }
        System.out.println("");
    }

}
