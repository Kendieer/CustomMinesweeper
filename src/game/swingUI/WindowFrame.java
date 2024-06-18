package game.swingUI;

import game.base.GameLogic;
import game.base.MinesweeperSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public final class WindowFrame {

    private static final WindowFrame STATIC_FRAME = new WindowFrame("Minesweeper " + MinesweeperSystem.Version);

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
        newGame( MinesweeperSystem.getMinesweeper() );
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
        WindowPanel.getStaticPanel().newGame( MinesweeperSystem.getMinesweeper() );
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

    public void showWarningDialog(String message){
        JDialog dialog = new JDialog(this.frame, "Warning");
        dialog.setMinimumSize( new Dimension(220,180) );
        dialog.setLocationRelativeTo( this.frame );
        dialog.setLayout( null );
        dialog.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
        // label
        Label label = new Label( message );
        label.setBounds( 10,10,200,30 );
        label.setFont( new Font("Console", Font.BOLD, 16) );
        label.setAlignment( Label.CENTER );
        dialog.add( label );
        // button
        JButton confirmButton = new JButton("OK");
        confirmButton.setBounds( 75,85 ,50,20 );
        confirmButton.setMargin( new Insets( 0,0,0,0 ) );
        confirmButton.addActionListener( e -> dialog.dispose() );
        dialog.add( confirmButton );
        dialog.setVisible( true );
    }
}
