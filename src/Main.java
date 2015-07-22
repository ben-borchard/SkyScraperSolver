/**
 * Created by bborchard on 7/20/2015.
 */
public class Main {


    public static void main(String[] args){

        int[] top = new int[] {0,2,2,3,4,0};
        int[] right = new int[] {4,0,2,2,0,1};
        int[] bottom = new int[] {0,0,0,2,0,0};
        int[] left = new int[] {0,0,0,5,2,2};

        SkyscraperSolver ss = new SkyscraperSolver(top,right,bottom,left);
        ss.solve();
    }
}
