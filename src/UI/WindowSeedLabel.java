package UI;

import javax.swing.*;
import java.awt.*;

public final class WindowSeedLabel {
    private static final WindowSeedLabel STATIC_SEED_LABEL = new WindowSeedLabel();
    private final JTextField SEED_LABEL = new JTextField();
    private final JCheckBox SEED_CONFIRM_BUTTON = new JCheckBox( "Fixed Seed" );

    private WindowSeedLabel(){
        initializeSettings();
    }

    private void initializeSettings(){
        // seed label
        SEED_LABEL.setMargin( new Insets( 0,0,0,0 ) );
        SEED_LABEL.setFont( new Font( "Minecraft AE", Font.BOLD, 10 ) );
        SEED_LABEL.setEnabled( true );
        SEED_LABEL.setBounds( 80,10,170,20 );
        SEED_LABEL.setVisible( true );

        // confirm button
        SEED_CONFIRM_BUTTON.setMargin( new Insets( 0,0,0,0 ) );
        SEED_CONFIRM_BUTTON.setFont( new Font( "Console", Font.PLAIN, 14 ) );
        SEED_CONFIRM_BUTTON.setEnabled( true );
        SEED_CONFIRM_BUTTON.setBounds( 250,10,100,20 );
        SEED_CONFIRM_BUTTON.setVisible( true );
    }

    public static WindowSeedLabel getStaticSeedLabel(){
        return STATIC_SEED_LABEL;
    }

    public JTextField getSeedLabel(){
        return this.SEED_LABEL;
    }

    public JCheckBox getSeedConfirmButton(){
        return this.SEED_CONFIRM_BUTTON;
    }

    public void setEnabled(boolean value){
        SEED_LABEL.setEnabled( value );
        SEED_CONFIRM_BUTTON.setEnabled( value );
    }
}
