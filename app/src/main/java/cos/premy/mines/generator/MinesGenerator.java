package cos.premy.mines.generator;

import cos.premy.mines.data.MinesContainer;

/**
 * Created by premy on 07.11.2017.
 */

public interface MinesGenerator {
    public MinesContainer getNewProblem(int N, int M, int minesNumber);
}
