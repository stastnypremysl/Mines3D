package cos.premy.mines.data;

import cos.premy.mines.GameStatus;

/**
 * Created by premy on 07.11.2017.
 */

public class MinesContainer {
    private final int height;
    private final int width;
    private final int numLevels;
    private final int minesNumber;
    private int minesBlocked;
    /**
     * Increased only if user block correct mine. Not the false one.
     */
    private int minesOkBlocked;
    private int minesOpened;
    private final Mine[][][] mines;

    public MinesContainer(int N, int M, int numLevels, int minesNumber, int minesBlocked, int minesOkBlocked, int minesOpened){
        this.numLevels = numLevels;
        this.height = N;
        this.width = M;
        this.minesNumber = minesNumber;
        this.minesBlocked = minesBlocked;
        this.minesOkBlocked = minesOkBlocked;
        this.minesOpened = minesOpened;

        mines = new Mine[numLevels][N][M];
        for(int i = 0; i != numLevels; i++){
            for(int ii = 0; ii != N; ii++){
                for(int iii = 0; iii != M; iii++){
                    mines[i][ii][iii] = new Mine();
                    //DEBUG
                    /*try {
                        mines[i][ii][iii].setStatus(MineStatus.OPENED);
                    }
                    catch (MyHappyException ex){

                    }*/
                    //
                }
            }
        }
        initListeners();
    }

    public MinesContainer(int N, int M, int numLevels, int minesNumber){
        this(N, M, numLevels, minesNumber, 0, 0, 0);
    }

    public int getOkBlockedMines(){
        return minesOkBlocked;
    }

    public int getMinesOpened(){
        return minesOpened;
    }

    public int getMinesBlocked(){
        return minesBlocked;
    }

    public int getMinesNumber(){
        return minesNumber;
    }

    private void initListeners() {
        for (int i = 0; i != numLevels; i++) {
            for (int ii = 0; ii != height; ii++) {
                for (int iii = 0; iii != width; iii++) {
                    mines[i][ii][iii].addMineStatusChangedListener(new MineStatusChangedListener() {
                        @Override
                        public void onOpen(GameStatus status, Mine mine) {
                            if(mine.getIsReal()){
                                status.endGame(false);
                            } else{
                                minesOpened++;
                            }
                        }

                        @Override
                        public void onBlock(GameStatus status, Mine mine) {
                            if(mine.getIsReal()){
                                minesOkBlocked++;
                            }
                            minesBlocked++;
                            if(minesOkBlocked == minesBlocked && minesOkBlocked == minesNumber){
                                status.endGame(true);
                            }
                        }

                        @Override
                        public void onUnblock(GameStatus status, Mine mine) {
                            if(mine.getIsReal()){
                                minesOkBlocked--;
                            }
                            minesBlocked--;
                            if(minesOkBlocked == minesBlocked && minesOkBlocked == minesNumber){
                                status.endGame(true);
                            }
                        }
                    });
                }
            }
        }
    }

    private void setMineNeighbors(int z, int y, int x){
        Mine thisMine = mines[z][y][x];

        if (z > 0) {
            thisMine.setNeighborCoord(new MineCoord(x, y, z - 1));
            if (mines[z - 1][y][x].getIsReal()) {
                thisMine.setNeighborMineCoord(new MineCoord(x, y, z - 1));
            }
        }

        if (z != numLevels - 1) {
            thisMine.setNeighborCoord(new MineCoord(x, y, z + 1));
            if (mines[z + 1][y][x].getIsReal()) {
                thisMine.setNeighborMineCoord(new MineCoord(x, y, z + 1));
            }
        }

        if(y > 0){
            thisMine.setNeighborCoord(new MineCoord(x, y-1, z));
            if(mines[z][y-1][x].getIsReal()){
                thisMine.setNeighborMineCoord(new MineCoord(x, y-1, z));
            }
        }

        if(y != height - 1){
            thisMine.setNeighborCoord(new MineCoord(x, y+1, z));
            if(mines[z][y+1][x].getIsReal()){
                thisMine.setNeighborMineCoord(new MineCoord(x, y+1, z));
            }
        }

        if(x > 0){
            thisMine.setNeighborCoord(new MineCoord(x-1, y, z));
            if(mines[z][y][x-1].getIsReal()){
                thisMine.setNeighborMineCoord(new MineCoord(x-1, y, z));
            }
        }

        if(x != width - 1){
            thisMine.setNeighborCoord(new MineCoord(x+1, y, z));
            if(mines[z][y][x+1].getIsReal()){
                thisMine.setNeighborMineCoord(new MineCoord(x+1, y, z));
            }
        }
    }


    public boolean isRealMine(int z, int y, int x){
        return mines[z][y][x].getIsReal();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth(){
        return width;
    }

    public Mine getMine(int z, int y, int x){
        return mines[z][y][x];
    }

    public void setFactorized(){
        for(int i = 0; i != numLevels; i++){
            for(int ii = 0; ii != height; ii++){
                for(int iii = 0; iii != width; iii++){
                    setMineNeighbors(i,ii,iii);
                    mines[i][ii][iii].setFactorized();
                }
            }
        }
    }


}
