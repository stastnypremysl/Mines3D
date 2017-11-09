package cos.premy.mines;

/**
 * Created by premy on 07.11.2017.
 */

public class MyHappyException extends Exception{
    public enum EX_TYPES{
        MINE_IS_FACTORIZED,
        ANIMATION_HASNT_BEEN_CONFIGURED
    }

    public static String getMessage(EX_TYPES type){
        switch (type){
            case MINE_IS_FACTORIZED:
                return "Mine has been already factorized and is read only.";
            case ANIMATION_HASNT_BEEN_CONFIGURED:
                return "Animation hasn't been configured yet.";
            default:
                return "Unknown error.";
        }
    }

    public MyHappyException(EX_TYPES type){
        super(getMessage(type));
    }

    /**
     * Created by premy on 08.11.2017.
     */

    public static class LoadedGame {
    }
}
