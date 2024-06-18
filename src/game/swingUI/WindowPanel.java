package game.swingUI;

import game.base.GameLogic;
import game.base.MineSweeper;

import javax.swing.*;
import java.awt.*;

public final class WindowPanel {
    private final JPanel PANEL = new JPanel();

    private static final WindowPanel STATIC_PANEL = new WindowPanel();

    private WindowPanel(){
        initializePanel();
    }

    private void initializePanel(){
        PANEL.setLayout( null );
        JLabel seedLabel = new JLabel( "Seed : " );
        seedLabel.setBounds( 10,10,100,20 );
        seedLabel.setFont( new Font( "Minecraft AE", Font.PLAIN, 14 ) );
        seedLabel.getInsets( new Insets( 0, 0, 0, 0 ) );
        // add seed
        PANEL.add( seedLabel );
        PANEL.add( WindowSeedLabel.getStaticSeedLabel().getSeedLabel() );
        PANEL.add( WindowSeedLabel.getStaticSeedLabel().getSeedConfirmButton() );
        newGame( MineSweeper.getMinesweeper() );
    }

    public void clearOldButtons(){
        if (buttons == null){
            return;
        }
        for ( int i = 0; i < buttons.length; i++ ) {
            for ( int j = 0; j < buttons[i].length; j++ ) {
                PANEL.remove( buttons[i][j].getButton() );
            }
        }
        PANEL.repaint();
    }

    private NodeButton[][] buttons = null;
    public void newGame( GameLogic game ){
        clearOldButtons();
        buttons = new NodeButton[game.HEIGHT][game.WIDTH];
        for ( int i = 0; i < buttons.length; i++ ) {
            for ( int j = 0; j < buttons[i].length; j++ ) {
                buttons[i][j] = new NodeButton( i,j );
                PANEL.add( buttons[i][j].getButton() );
            }
        }
        WindowSeedLabel.getStaticSeedLabel().setEnabled( true );
        PANEL.repaint();
    }

    public void updateButton(int x,int y){
        buttons[x][y].updateButton();
    }

    public JButton getButton(int x,int y){
        return buttons[x][y].getButton();
    }

    public void disEnabledButton(int x,int y){
        buttons[x][y].disEnabled();
    }

    public static WindowPanel getStaticPanel(){
        return STATIC_PANEL;
    }

    public JPanel getPanel(){
        return PANEL;
    }

}
