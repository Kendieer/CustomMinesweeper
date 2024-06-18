package game.base.elements;

import game.base.GameLogic;

public class MineNode extends Node {
    public MineNode ( int x, int y , boolean isPublic ) {
        super( NodeTypes.Mine, isPublic , x , y);
    }
    public MineNode ( int x, int y ){
        this( x, y, false );
    }

    @Override
    public void leftClickEvent ( GameLogic gameBase) {
        gameBase.stopGame( super.POSITION ,super.hashCode() );
        super.setPublic( true,gameBase );
    }
}
