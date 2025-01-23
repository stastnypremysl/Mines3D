package cos.premy.mines.generator;

import java.util.Random;

import cos.premy.mines.MyHappyException;
import cos.premy.mines.data.MinesContainer;

/**
 * Created by premy on 07.11.2017.
 */

public class RandomMinesGenerator implements MinesGenerator {
    @Override
    public MinesContainer getNewProblem(int N, int M, int numLevels, int minesNumber){
        try {
            MinesContainer ret = new MinesContainer(N, M, numLevels, minesNumber);
            if (numLevels >= 3) {
                // Place 6 mines around position (1,1,1) for easy test
                ret.getMine(0, 1, 1).setIsReal(true);
                ret.getMine(2, 1, 1).setIsReal(true);
                ret.getMine(1, 0, 1).setIsReal(true);
                ret.getMine(1, 2, 1).setIsReal(true);
                ret.getMine(1, 1, 0).setIsReal(true);
                ret.getMine(1, 1, 2).setIsReal(true);
            }
            ret.setFactorized();
            return ret;
        }
        catch (MyHappyException ex){
            ex.printStackTrace();
            System.exit(1);
            return null;
        }
    }
}
