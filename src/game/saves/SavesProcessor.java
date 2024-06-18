package game.saves;

import game.base.GameLogic;
import game.base.MineSweeper;
import game.swingUI.WindowPanel;
import game.swingUI.WindowSeedLabel;

import java.io.*;

public class SavesProcessor {
    private SavesProcessor(){}

    private static GameSave getSave( GameLogic game ){
        try {
            return game.exportSave();
        } catch ( NullPointerException e ) {
            throw new NullPointerException();
        }
    }

    public static void saveGame( GameLogic game ){
        try ( ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream( "Saves\\" + game.getGameSeed() + "HC" + game.hashCode() + ".data" ) ) ) {
            oos.writeObject( getSave( game ) );
        } catch ( Exception e ){
            System.out.println("ERROR");
            return;
        }
    }

    public static void loadGame( File file ){
        GameLogic game;
        try ( ObjectInputStream ois = new ObjectInputStream( new FileInputStream( file ) ) ) {
            GameSave save = (GameSave) ois.readObject();
            game = new GameLogic( save.HEIGHT, save.WIDTH, save.MINES_COUNT );
            game.setSeed( save.RANDOM_MAP_SEED );
            WindowSeedLabel.getStaticSeedLabel().getSeedConfirmButton().setSelected( true );
            WindowSeedLabel.getStaticSeedLabel().getSeedLabel().setText( Long.toString( save.RANDOM_MAP_SEED ) );
            MineSweeper.loadGame( game );
            game.clickNode( save.FIRST_CLICK_X, save.FIRST_CLICK_Y );
            for ( int i = 0; i < save.IS_PUBLIC_NODE.length; i++ ) {
                if (save.IS_PUBLIC_NODE[i]){
                    WindowPanel.getStaticPanel().updateButton( i / save.WIDTH, i % save.WIDTH );
                }
            }
        } catch ( Exception e ){
            System.out.println("Load Failed");
            return;
        }
    }
}
