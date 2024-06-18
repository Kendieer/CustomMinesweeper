package gameBase;

import gameBase.util.GameDefaultSettings;
import gameBase.util.LinearRandom;
import ui.WindowFrame;
import ui.WindowSeedLabel;

public final class MineSweeper {
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
