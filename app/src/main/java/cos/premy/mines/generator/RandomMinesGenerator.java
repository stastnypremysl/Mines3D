package cos.premy.mines.generator;

import java.util.Random;

import cos.premy.mines.MyHappyException;
import cos.premy.mines.data.MinesContainer;

/**
 * Created by premy on 07.11.2017.
 */

public class RandomMinesGenerator implements MinesGenerator {
    @Override
    public MinesContainer getNewProblem(int N, int M, int minesNumber){
        try {
            MinesContainer ret = new MinesContainer(N, M, minesNumber);
            Random rand = new Random();
            int minesAdded = 0;
            while (minesAdded != minesNumber) {
                int z = rand.nextInt(2);
                int y = rand.nextInt(N);
                int x = rand.nextInt(M);
                if (!ret.isRealMine(z, y, x)) {
                    minesAdded++;
                    ret.getMine(z, y, x).setIsReal(true);
                }
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
