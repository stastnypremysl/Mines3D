package cos.premy.mines.generator;

import cos.premy.mines.data.MinesContainer;

/**
 * Created by premy on 07.11.2017.
 */

public interface MinesGenerator {
    /**
     * Given a container with no real mines, place real mines
     * on it. It avoids placing a mine on user's first tap
     * @param container MinesContainer
     * @param level User's first tap level
     * @param y User's first tap y tile
     * @param x User's first tap x tile
     */
    public void populateNewProblem(MinesContainer container, int level, int y, int x);
}
