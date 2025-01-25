package cos.premy.mines.generator;

import java.util.Random;

import cos.premy.mines.MyHappyException;
import cos.premy.mines.data.MinesContainer;

/**
 * Created by premy on 07.11.2017.
 */

public class RandomMinesGenerator implements MinesGenerator {
    @Override
    public void populateNewProblem(MinesContainer container, int level, int n, int m) {
        try {
            MinesContainer con = container;
            Random rand = new Random();
            int minesAdded = 0;
            while (minesAdded < con.getMinesNumber()) {
                int z = rand.nextInt(con.getNumLevels());
                int y = rand.nextInt(con.getHeight());
                int x = rand.nextInt(con.getWidth());
                if (!con.isRealMine(z, y, x)
                        && !(level == z && n == y && m == x)) {
                    minesAdded++;
                    con.getMine(z, y, x).setIsReal(true);
                }
            }
            container.setFactorized();
        }
        catch (MyHappyException ex){
            ex.printStackTrace();
            System.exit(1);
        }
    }
}
