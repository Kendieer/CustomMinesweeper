package gameBase.elements;

import gameBase.GameLogic;

public class NumberNode extends Node {
    public final int VALUE;
    protected NumberNode ( int x, int y, int value, boolean isPublic) {
        super( NodeTypes.Number, isPublic, x, y );
        VALUE = value;
    }

    public NumberNode ( int x, int y, int value ){
        this(x,y,value,false);
    }

    @Override
    public void leftClickEvent ( GameLogic gameBase) {
        gameBase.publicNode( super.POSITION, super.hashCode() );
        super.setPublic( true, gameBase );
    }

}
