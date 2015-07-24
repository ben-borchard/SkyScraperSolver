/**
 * Created by bborchard on 7/20/2015.
 */
public class Main {


    public static void main(String[] args){

        int[] top = new int[] {0,2,2,3,4,0};
        int[] right = new int[] {4,0,2,2,0,1};
        int[] bottom = new int[] {0,0,0,2,0,0};
        int[] left = new int[] {0,0,0,5,2,2};

//        int[] top = new int[] {3,2,2,1};
//        int[] right = new int[] {1,2,2,2};
//        int[] bottom = new int[] {1,3,2,2};
//        int[] left = new int[] {4,2,3,1};

//        int[] top = new int[] {2,2,4,1};
//        int[] right = new int[] {1,2,3,2};
//        int[] bottom = new int[] {2,2,1,4};
//        int[] left = new int[] {3,2,1,2};

//        int[] top = new int[] {0,2,3,0};
//        int[] right = new int[] {3,0,3,0};
//        int[] bottom = new int[] {0,0,0,0};
//        int[] left = new int[] {0,3,0,0};

//        int[] top = new int[] {0,0,5,2,0};
//        int[] right = new int[] {0,3,0,1,0};
//        int[] bottom = new int[] {0,4,0,0,0};
//        int[] left = new int[] {0,2,0,0,0};

        SkyscraperSolver ss = new SkyscraperSolver(top,right,bottom,left);
        ss.solve();
    }
}
