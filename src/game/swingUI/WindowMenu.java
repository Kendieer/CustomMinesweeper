package game.swingUI;

import game.base.MinesweeperSystem;
import game.base.util.GameDefaultSettings;
import game.saves.SavesProcessor;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public final class WindowMenu {
    private final JMenuBar MENU_BAR = new JMenuBar();

    private static final WindowMenu STATIC_MENU = new WindowMenu();

    private WindowMenu(){
        initializeMenu();
    }

    private void initializeMenu(){
        // Create Menu
        JMenu gameMenu = new JMenu("Game");
        JMenu optionMenu = new JMenu("Option");
        JMenu saveMenu = new JMenu("Saves");

        // init
        initializeGameMenu( gameMenu );
        initializeOptionMenu( optionMenu );
        initializeSaveMenu( saveMenu );

        // Add to Menubar
        MENU_BAR.add( gameMenu );
        MENU_BAR.add( optionMenu );
        MENU_BAR.add( saveMenu );
    }

    private void initializeSaveMenu ( JMenu saveMenu ) {
        // new
        JMenuItem saveGame = new JMenuItem("Save Game");
        JMenuItem loadGame = new JMenuItem("Load Game");

        saveGame.addActionListener( e -> SavesProcessor.saveGame( MinesweeperSystem.getMinesweeper() ) );
        loadGame.addActionListener( e -> {
            JFileChooser fileChooser = new JFileChooser("Saves");
            fileChooser.addChoosableFileFilter( new FileNameExtensionFilter( "Minesweeper data", "data" ) );
            int result = fileChooser.showOpenDialog(WindowFrame.getStaticFrame().getFrame());
            if (result == JFileChooser.APPROVE_OPTION){
                File selectedFile = fileChooser.getSelectedFile();
                SavesProcessor.loadGame( selectedFile );
            }
        } );

        // add
        saveMenu.add( saveGame );
        saveMenu.add( loadGame );
    }

    private void initializeGameMenu(JMenu gameMenu){
        // restart
        JMenuItem restart = new JMenuItem("Restart");
        restart.addActionListener( e -> MinesweeperSystem.newMinesweeper() );

        // restart with
        JMenu restartWith = new JMenu("Restart With");

            // difficulty
            JMenuItem easy = new JMenuItem("Easy");
            easy.addActionListener( e -> {
                MinesweeperSystem.width = GameDefaultSettings.Presets.EASY.GAME_WIDTH;
                MinesweeperSystem.height = GameDefaultSettings.Presets.EASY.GAME_HEIGHT;
                MinesweeperSystem.minesCount = GameDefaultSettings.Presets.EASY.MINES_COUNT;
                MinesweeperSystem.newMinesweeper();
            } );
            restartWith.add( easy );
            JMenuItem normal = new JMenuItem("Normal");
            normal.addActionListener( e -> {
                MinesweeperSystem.width = GameDefaultSettings.Presets.NORMAL.GAME_WIDTH;
                MinesweeperSystem.height = GameDefaultSettings.Presets.NORMAL.GAME_HEIGHT;
                MinesweeperSystem.minesCount = GameDefaultSettings.Presets.NORMAL.MINES_COUNT;
                MinesweeperSystem.newMinesweeper();
            } );
            restartWith.add( normal );
            JMenuItem hard = new JMenuItem("Hard");
            hard.addActionListener( e -> {
                MinesweeperSystem.width = GameDefaultSettings.Presets.HARD.GAME_WIDTH;
                MinesweeperSystem.height = GameDefaultSettings.Presets.HARD.GAME_HEIGHT;
                MinesweeperSystem.minesCount = GameDefaultSettings.Presets.HARD.MINES_COUNT;
                MinesweeperSystem.newMinesweeper();
            } );
            restartWith.add( hard );

        // add
        gameMenu.add( restart );
        gameMenu.add( restartWith );
    }

    private static void initializeOptionMenu( JMenu optionMenu ){
        JMenuItem customGameSize = new JMenuItem("Game Settings");

        customGameSize.addActionListener( e -> {
            // create dialog
            JDialog dialog = new JDialog( WindowFrame.getStaticFrame().getFrame(), "Game Settings" );
            dialog.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
            dialog.setLocationRelativeTo( WindowFrame.getStaticFrame().getFrame() );
            dialog.setVisible( true );
            dialog.setLayout( new GridLayout(4,2) );
            dialog.setSize( 200, 140 );

            // add
            dialog.add( new JLabel( "     Width:" ) );
            JTextField width = new JTextField( 2 );
            dialog.add( width );

            dialog.add( new JLabel( "     Height:" ) );
            JTextField height = new JTextField( 2 );
            dialog.add( height );

            dialog.add( new JLabel( "     Mines:" ) );
            JTextField minesCount = new JTextField( 4 );
            dialog.add( minesCount );

            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener( evt-> dialog.dispose() );
            dialog.add( cancelButton );
            JButton confirmButton = new JButton("Confirm");
            confirmButton.addActionListener( evt -> {
                    String widthText = width.getText();
                    String heightText = height.getText();
                    String minesCountText = minesCount.getText();
                    if (
                            !widthText.matches( "[1-9]([0-9])*" ) ||
                            !heightText.matches( "[1-9]([0-9])*" ) ||
                            !minesCountText.matches( "[0-9]+" ) ||
                            widthText.isEmpty() ||
                            heightText.isEmpty() ||
                            minesCountText.isEmpty()
                    ){
                        if (!widthText.matches( "[1-9]([0-9])*" ) || widthText.isEmpty()){
                            width.setBackground( new Color( 0xdf3f3f ) );
                        } else {
                            width.setBackground( Color.WHITE );
                        }
                        if (!heightText.matches( "[1-9]([0-9])*" ) || heightText.isEmpty()){
                            height.setBackground( new Color( 0xdf3f3f ) );
                        }
                        if (!minesCountText.matches( "([0-9])+" ) || minesCountText.isEmpty()){
                            minesCount.setBackground( new Color( 0xdf3f3f ) );
                        } else {
                            minesCount.setBackground( Color.WHITE );
                        }
                    } else {
                        MinesweeperSystem.width = Integer.parseInt( widthText );
                        MinesweeperSystem.height = Integer.parseInt( heightText );
                        MinesweeperSystem.minesCount = Integer.parseInt( minesCountText );
                        MinesweeperSystem.newMinesweeper();
                        dialog.dispose(); // exit dialog
                    }
                }
            );
            dialog.add(confirmButton);

        } );

        // add
        optionMenu.add( customGameSize );
    }



    public static WindowMenu getStaticMenu (){
        return STATIC_MENU;
    }

    public JMenuBar getMenuBar () {
        return MENU_BAR;
    }
}
