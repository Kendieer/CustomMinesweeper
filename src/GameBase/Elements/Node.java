package gameBase.elements;


import gameBase.GameLogic;
import ui.WindowPanel;

import java.util.Objects;

public abstract class Node implements ClickEvent {
    protected final NodeTypes type;

    final Position POSITION;

    protected boolean isPublic = false;

    protected Node ( NodeTypes type, boolean isPublic, int x, int y){
        this.type = type;
        this.isPublic = isPublic;
        this.POSITION = new Position( x, y );
    }

    public Node ( NodeTypes type, Position pos, boolean isPublic ) {
        this.type = type;
        POSITION = pos;
        this.isPublic = isPublic;
    }

    @Override
    public boolean equals ( Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( ! ( o instanceof Node node ) ) {
            return false;
        }
        return type == node.type && Objects.equals( POSITION, node.POSITION );
    }

    @Override
    public int hashCode () {
        return Objects.hash( type, POSITION );
    }

    public NodeTypes getType () {
        return type;
    }

    public boolean isPublic () {
        return isPublic;
    }

    public Position getPosition () {
        return POSITION;
    }

    public void setPublic( boolean isPublic, GameLogic key ){
        if (key == null || isPublic == this.isPublic){
            return;
        }
        this.isPublic = isPublic;
        WindowPanel.getStaticPanel().updateButton( POSITION.X, POSITION.Y );
    }
}
