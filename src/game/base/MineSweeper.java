package game.base;

import game.base.util.GameDefaultSettings;
import game.base.util.LinearRandom;
import game.swingUI.WindowFrame;
import game.swingUI.WindowSeedLabel;

public final class MineSweeper {

    public static final String Version = "V1.1";
    private MineSweeper(){}

    private static GameLogic minesweeper = new GameLogic();

    public static GameLogic getMinesweeper(){
        return minesweeper;
    }

    public static int height = GameDefaultSettings.Presets.EASY.GAME_HEIGHT, width = GameDefaultSettings.Presets.EASY.GAME_WIDTH ,minesCount = GameDefaultSettings.Presets.EASY.MINES_COUNT;

    private static void newGame(GameLogic newGame){
        minesweeper = newGame;
        WindowFrame.getStaticFrame().newGame( minesweeper );
    }


    public static void newMinesweeper(long seed){
        GameLogic game = new GameLogic(height,width, minesCount);
        game.setSeed( seed );
        newGame(game);
    }
    public static void newMinesweeper(String seed){
        GameLogic game = new GameLogic(height,width, minesCount);
        game.setSeed( seed );
        newGame(game);
    }

    public static void loadGame( GameLogic game ){
        newGame( game );
    }

    public static void newMinesweeper(){
        if ( WindowSeedLabel.getStaticSeedLabel().getSeedConfirmButton().isSelected() ){
            newMinesweeper( WindowSeedLabel.getStaticSeedLabel().getSeedLabel().getText() );
        } else {
            newMinesweeper( new LinearRandom().SEED );
        }
    }

    public static void SystemRunner(){
        WindowFrame windowFrame = WindowFrame.getStaticFrame();
    }
}
