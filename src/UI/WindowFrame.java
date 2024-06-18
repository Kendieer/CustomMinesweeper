package ui;

import gameBase.GameLogic;
import gameBase.MineSweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public final class WindowFrame {

    private static final WindowFrame STATIC_FRAME = new WindowFrame("Minesweeper");

    private JFrame frame = null;


    private WindowFrame (){}

    public static final int DEFAULT_LENGTH = 400;
    public static final int DEFAULT_HEIGHT = 400;

    private WindowFrame ( String name){
        this.frame = new JFrame( name );
        frame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        frame.addKeyListener( new KeyAdapter() {
            @Override
            public void keyPressed ( KeyEvent e ) {
                super.keyPressed( e );
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    System.exit( 0 );
                }
            }
        } );
        frame.setJMenuBar( WindowMenu.getStaticMenu().getMenuBar() );
        frame.add( WindowPanel.getStaticPanel().getPanel() );
        newGame( MineSweeper.getMinesweeper() );
        frame.setLocation( 240,240 );
    }

    public void newGame( GameLogic game ){
        if (game == null){
            game = new GameLogic();
        }
        updateFrame( Math.max(game.WIDTH * 20 + 20, 300), game.HEIGHT*20 + 80 );
    }

    public void updateFrame(){
        updateFrame( WindowFrame.DEFAULT_LENGTH, WindowFrame.DEFAULT_HEIGHT );
    }

    public void updateFrame(int width,int height){
        this.frame.setMinimumSize( new Dimension(width,height) );
        this.frame.setSize( new Dimension(width+40,height+40) );
        WindowPanel.getStaticPanel().newGame( MineSweeper.getMinesweeper() );
        frame.setVisible( true );
    }

    public static WindowFrame getStaticFrame (){
        return STATIC_FRAME;
    }

    public JFrame getFrame(){
        return frame;
    }

    @Override
    public int hashCode () {
        return Runtime.getRuntime().hashCode();
    }
}
