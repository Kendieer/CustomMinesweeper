package game.saves;

import game.base.GameLogic;
import game.base.MinesweeperSystem;
import game.swingUI.WindowFrame;
import game.swingUI.WindowPanel;
import game.swingUI.WindowSeedLabel;

import java.io.*;

public class SavesProcessor {
    private SavesProcessor(){}

    /**
     * 获取从游戏中捕获存档
     * @param game 游戏
     * @return 存档对象
     */
    private static GameSave getSave( GameLogic game ){
        try {
            return game.exportSave();
        } catch ( NullPointerException e ) {
            throw new NullPointerException();
        }
    }

    public static void saveGame( GameLogic game ){
        GameSave save = getSave( game );
        if (save == null){
            WindowFrame.getStaticFrame().showWarningDialog( "Please Start Game!" );
            return;
        }
        new File( "Saves" ).mkdir();
        try ( ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream( "Saves\\" + game.getGameSeed() + "HC" + game.hashCode() + ".data" ) ) ) {
            oos.writeObject( save );
        } catch ( Exception e ){
            System.out.println("ERROR");
            return;
        }
    }

    public static void loadGame( File file ){
        GameLogic game;
        GameSave save = null;
        try ( ObjectInputStream ois = new ObjectInputStream( new FileInputStream( file ) ) ) {
            save = ( GameSave ) ois.readObject();
        } catch ( Exception e ){
            System.out.println("Load Failed");
            WindowFrame.getStaticFrame().showWarningDialog( "Failed to load this Save!" );
            return;
        }
        try {
            game = new GameLogic( save.HEIGHT, save.WIDTH, save.MINES_COUNT );
            game.setSeed( save.RANDOM_MAP_SEED );
            WindowSeedLabel.getStaticSeedLabel().getSeedConfirmButton().setSelected( true );
            WindowSeedLabel.getStaticSeedLabel().getSeedLabel().setText( Long.toString( save.RANDOM_MAP_SEED ) );
            MinesweeperSystem.loadGame( game );
            game.clickNode( save.FIRST_CLICK_X, save.FIRST_CLICK_Y );
            for ( int i = 0; i < save.IS_PUBLIC_NODE.length; i++ ) {
                if (save.IS_PUBLIC_NODE[i]){
                    WindowPanel.getStaticPanel().updateButton( i / save.WIDTH, i % save.WIDTH );
                }
                if (save.IS_MARKED_NODE[i]){
                    WindowPanel.getStaticPanel().markButton( i / save.WIDTH, i % save.WIDTH );
                }
            }
        } catch ( Exception e ) {
            System.out.println("Generate Map Failed");
            return;
        }
    }
}
