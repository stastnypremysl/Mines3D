package cos.premy.mines.graphics;

import static java.lang.Math.*;

/**
 * Created by premy on 07.11.2017.
 */

class MinesLayoutComputor {
    MinesLayoutComputor(int height, int width){
        this.height = height;
        this.width = width;
    }

    final int height;
    final int width;

    static final int MARGIN_TOP = 40;
    static final int MARGIN_BOTTOM = 20;
    static final int MARGIN_LEFT = 20;
    static final int MARGIN_RIGHT = 20;

    static final int BUTTON_MARGIN_RIGHT = 30;
    static final int BUTTON_MARGIN_BOTTOM = 30;


    private int buttonWidth = -1;
    int getButtonWidth(){
        if(buttonWidth != -1){
            return buttonWidth;
        }

        if(width > height){
            buttonWidth = (width - MARGIN_LEFT - MARGIN_RIGHT) /7;
        }
        else {
            buttonWidth = width - MARGIN_LEFT - MARGIN_RIGHT;
        }
        return getButtonWidth();
    }

    private int buttonHeight = -1;
    int getButtonHeight(){
        if(buttonHeight != -1){
            return buttonHeight;
        }

        if(width > height){
            buttonHeight = height - MARGIN_TOP - MARGIN_BOTTOM;
        }
        else {
            buttonHeight = (height - MARGIN_TOP - MARGIN_BOTTOM)/7;
        }
        return getButtonHeight();
    }

    private int gridSize = -1;
    int getGridWidth(){
        if(gridSize != -1){
            return gridSize;
        }
        gridSize = min(width - (MARGIN_RIGHT + MARGIN_LEFT), height - (MARGIN_TOP + MARGIN_BOTTOM));
        return getGridWidth();
    }

    int getGridHeight(){
        return getGridWidth();
    }

    private int buttonX = -1;
    int getButtonX(){
        if(buttonX != -1){
            return buttonX;
        }
        getGridWidth();

        if(width > height){
            buttonX = (width - gridSize - getButtonWidth() - MARGIN_LEFT - MARGIN_RIGHT - BUTTON_MARGIN_RIGHT)/2;
        } else{
            buttonX = MARGIN_LEFT;
        }
        return getButtonX();
    }

    private int buttonY = -1;
    int getButtonY(){
        if(buttonY != -1){
            return buttonY;
        }
        getGridWidth();

        if(width > height){
            buttonY = MARGIN_TOP;
        } else{
            buttonY = (height - gridSize - getButtonHeight() - MARGIN_TOP - MARGIN_BOTTOM - BUTTON_MARGIN_BOTTOM)/2;
        }
        return getButtonY();
    }

    private int gridX = -1;
    int getGridX(){
        if(gridX != -1){
            return gridX;
        }
        if(width > height) {
            gridX = getButtonX() + getButtonWidth() + BUTTON_MARGIN_RIGHT;
        }
        else {
            gridX = getButtonX();
        }
        return getGridX();
    }

    private int gridY = -1;
    int getGridY(){
        if(gridY != -1){
            return gridY;
        }
        if(width > height) {
            gridY = getButtonY();
        } else {
            gridY = getButtonY() + getButtonHeight() + BUTTON_MARGIN_BOTTOM;
        }
        return getGridY();
    }

    int getStatusLabelX(){
        return MARGIN_LEFT;
    }

    int getStatusLabelY(){
        return MARGIN_TOP;
    }

    int getStatusHeight() {
        return (min(width, height) * 30) / 1100;
    }

}
