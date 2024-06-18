package game.base.util;

public final class GameDefaultSettings {


    public static final class Presets{
        public final int GAME_WIDTH;
        public final int GAME_HEIGHT;
        public final int MINES_COUNT;

        // presets
        public static final Presets EASY = new Presets(20,20,40);
        public static final Presets NORMAL = new Presets( 30,30,100 );
        public static final Presets HARD = new Presets( 40,40,400 );

        private Presets ( int GAME_WIDTH, int GAME_HEIGHT, int MINES_COUNT ) {
            this.GAME_WIDTH = GAME_WIDTH;
            this.GAME_HEIGHT = GAME_HEIGHT;
            this.MINES_COUNT = MINES_COUNT;
        }
    }

    private GameDefaultSettings(){}
}
