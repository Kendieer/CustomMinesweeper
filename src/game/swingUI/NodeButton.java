package game.swingUI;

import game.base.elements.NodeTypes;
import game.base.MineSweeper;

import javax.swing.*;
import java.awt.*;

public class NodeButton {
    private final JButton button = new JButton();

    private final int X,Y;

    public static final int BUTTON_WIDTH = 20,BUTTON_HEIGHT = 20;

    public NodeButton ( int x, int y ) {
        X = x;
        Y = y;
        button.setBackground( new Color( 255,255,255 ) );
        button.setBounds( BUTTON_WIDTH * y + 10,BUTTON_HEIGHT * x + 40 ,BUTTON_WIDTH,BUTTON_HEIGHT);
        button.setMargin( new Insets( 0,0,0,0 ) );
        button.addActionListener( e -> {
                updateButton();
//                System.out.println(X+" " +Y);
        } );
        button.updateUI();
    }

    public void setButtonLocation ( int posX, int posY){
        button.setBounds( posX, posY ,button.getWidth(),button.getHeight());
        button.updateUI();
    }

    public void updateButton(){
        MineSweeper.getMinesweeper().clickNode( X,Y );
        button.setFont( new Font( "Console", Font.BOLD, 18 ) );
        switch ( MineSweeper.getMinesweeper().getNodeStatus(X,Y) ){
            case NodeTypes.Empty -> button.setBackground( new Color( 128,128,128 ) );
            case NodeTypes.Number -> {
//                button.setBackground( new Color( 201,201,201 ) );
                button.setText( Byte.toString( MineSweeper.getMinesweeper().getNodeValue( X,Y ) ) );
                button.setBackground(
                    (switch ( MineSweeper.getMinesweeper().getNodeValue( X,Y ) ){
                        case 1 -> new Color(0xAFDF0F);
                        case 2 -> new Color(0x3F3FFF);
                        case 3 -> new Color(0xFF3F3F);
                        case 4 -> new Color(0x0F0F8F);
                        case 5 -> new Color(0xFFFF3F);
                        case 6 -> new Color(0x0F7F0F);
                        case 7 -> new Color(0xFF00FF);
                        default -> new Color( 0xAF0F7F );
                    })
                );
            }
            case NodeTypes.Mine -> {
                button.setBackground( new Color( 255,0,0 ) );
                button.setText( "*" );
                button.setForeground( new Color( 0xFFFFFF ) );
            }
            case null -> {
                return;
            }
            default -> button.setBackground( new Color(255,255,255) );
        }
        button.setEnabled( false );
        button.updateUI();
    }

    public JButton getButton(){
        return button;
    }

    public void disEnabled(){
        button.setEnabled( false );
        try {
            button.removeMouseListener(button.getMouseListeners()[0]);
        } catch ( ArrayIndexOutOfBoundsException e ) {
            return;
        }
    }

}
