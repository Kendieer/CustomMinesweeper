package game.saves;

import java.io.Serializable;

public final class GameSave implements Serializable {
    final int MINES_COUNT;
    final int WIDTH,HEIGHT;
    final long RANDOM_MAP_SEED;
    final boolean[] IS_PUBLIC_NODE;
    final int FIRST_CLICK_X,FIRST_CLICK_Y;

    public GameSave ( int MINES_COUNT, int WIDTH, int HEIGHT, long RANDOM_MAP_SEED, boolean[] IS_PUBLIC_NODE , int FIRST_CLICK_X, int FIRST_CLICK_Y ) {
        this.MINES_COUNT = MINES_COUNT;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.RANDOM_MAP_SEED = RANDOM_MAP_SEED;
        this.IS_PUBLIC_NODE = IS_PUBLIC_NODE;
        this.FIRST_CLICK_X = FIRST_CLICK_X;
        this.FIRST_CLICK_Y = FIRST_CLICK_Y;
    }

    @Override
    public String toString () {
        return "GameSave{" +
                "MINES_COUNT=" + MINES_COUNT +
                ", WIDTH=" + WIDTH +
                ", HEIGHT=" + HEIGHT +
                ", SEED=" + RANDOM_MAP_SEED +
                ", NODES=" + nodesToString() +
                '}';
    }

    private String nodesToString(){
        if (IS_PUBLIC_NODE == null)
            return "null";
        int iMax = IS_PUBLIC_NODE.length - 1;
        if (iMax == -1)
            return "";

        StringBuilder b = new StringBuilder();
        for (int i = 0; ; i++) {
            b.append(IS_PUBLIC_NODE[i]?"1":"0");
            if (i == iMax)
                return b.toString();
            else
                b.append( "," );
        }
    }
}
