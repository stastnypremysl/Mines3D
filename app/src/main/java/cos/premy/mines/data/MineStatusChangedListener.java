package cos.premy.mines.data;

import cos.premy.mines.GameStatus;

/**
 * Created by premy on 07.11.2017.
 */

public interface MineStatusChangedListener {
    public void onOpen(GameStatus status, Mine mine);
    public void onBlock(GameStatus status, Mine mine);
    public void onUnblock(GameStatus status, Mine mine);
}
