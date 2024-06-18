package gameBase.elements;

import gameBase.GameLogic;

public class EmptyNode extends Node {
    protected EmptyNode(int x, int y,boolean isPublic) {
        super( NodeTypes.Empty, isPublic, x, y );
    }

    public EmptyNode ( int x, int y ){
        this(x,y,false);
    }

    @Override
    public void leftClickEvent ( GameLogic gameBase) {
        gameBase.publicNode( super.POSITION, super.hashCode() );
        super.setPublic( true,gameBase );
        // auto spread
        gameBase.clickNode( super.POSITION.X - 1, super.POSITION.Y - 1 );
        gameBase.clickNode( super.POSITION.X - 1, super.POSITION.Y );
        gameBase.clickNode( super.POSITION.X - 1, super.POSITION.Y + 1 );
        gameBase.clickNode( super.POSITION.X, super.POSITION.Y - 1 );
        gameBase.clickNode( super.POSITION.X, super.POSITION.Y + 1 );
        gameBase.clickNode( super.POSITION.X + 1, super.POSITION.Y - 1 );
        gameBase.clickNode( super.POSITION.X + 1, super.POSITION.Y );
        gameBase.clickNode( super.POSITION.X + 1, super.POSITION.Y + 1 );
    }
}
