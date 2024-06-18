package game.base;

import game.base.elements.*;
import game.base.util.*;
import game.saves.GameSave;
import game.swingUI.*;

import java.awt.*;

public final class GameLogic {
    private Node[][] nodes = null;
    public final int WIDTH,HEIGHT,MINES_COUNT;
    private LinearRandom randomDataGenerator = null;
    private Position firstStepPosition = null;
    private GameStatus status = GameStatus.NoCache;
    private int restSaveNodeCount = 0;
    public GameLogic(int height,int width,int minesCount){
        nodes = new Node[height][width];
        this.WIDTH = width;
        this.HEIGHT = height;
        this.MINES_COUNT = minesCount;
    }

    /**
     * 空参构造[按照简单难度]
     */
    public GameLogic(){
        this(
                GameDefaultSettings.Presets.EASY.GAME_HEIGHT,
                GameDefaultSettings.Presets.EASY.GAME_WIDTH,
                GameDefaultSettings.Presets.EASY.MINES_COUNT
        );
    }

    /**
     * 设置随机数生成器种子
     * @param seed 种子
     */
    public void setSeed(long seed){
        if (status == GameStatus.Running){
            return;
        }
        randomDataGenerator = new LinearRandom(seed);
    }

    public void setSeed(String str){
        if (status == GameStatus.Running){
            return;
        }
        if ( str.isEmpty() ){
            randomDataGenerator = new LinearRandom();
            return;
        }
        if (str.matches( "-?[0-9]+" )){
            setSeed( Long.parseLong( str ) );
        } else {
            setSeed( str.hashCode() );
        }
    }

    public void removeSeed(){
        if (firstStepPosition != null){
            return;
        }
        randomDataGenerator = null;
    }

    public void clickNode(int idx){
        clickNode( idx / WIDTH, idx % WIDTH );
    }

    public void clickNode(int x,int y){
        if ( !checkCache() ) {
            if (WindowSeedLabel.getStaticSeedLabel().getSeedConfirmButton().isSelected()){
                setSeed( WindowSeedLabel.getStaticSeedLabel().getSeedLabel().getText() );
            }

            firstStepPosition = new Position( x, y );
            if ( randomDataGenerator == null ) {
                randomDataGenerator = new LinearRandom();
            }
            MapCreator.spawnMap(nodes,MINES_COUNT,randomDataGenerator,firstStepPosition);
            status = GameStatus.Running;
            restSaveNodeCount = WIDTH * HEIGHT - MINES_COUNT;
            WindowSeedLabel.getStaticSeedLabel().getSeedLabel().setText( Long.toString(getGameSeed()) );
            WindowSeedLabel.getStaticSeedLabel().setEnabled( false );
        }
        try {
            if (nodes[x][y].isPublic()){
                return;
            }
            nodes[x][y].leftClickEvent(this);
            if (restSaveNodeCount == 0){
                stopGame( nodes[ x ][ y ].getPosition(), nodes[ x ][ y ].hashCode() );
            }
        } catch ( ArrayIndexOutOfBoundsException e ) {
            return;
        }
    }

    /**
     * 检测是否有缓存地图
     */
    private boolean checkCache () {
        return (firstStepPosition != null);
    }

    /**
     * 获取当前种子
     * @return 随机生成器种子
     */
    public long getGameSeed(){
        // 防止空指针
        try {
            return randomDataGenerator.SEED;
        } catch ( NullPointerException e ) {
            randomDataGenerator = new LinearRandom();
            return randomDataGenerator.SEED;
        }
    }

    public GameStatus getStatus(){
        return this.status;
    }

    /**
     * 终止游戏
     * @return 是否终止成功
     */
    public boolean stopGame(Position pos,int nodeHashCode){
        try {
            if ( nodes[ pos.X ][ pos.Y ].hashCode() == nodeHashCode) {
//                System.out.println(pos.X + " " + pos.Y);
                if ( nodes[ pos.X ][ pos.Y ].getType() == NodeTypes.Mine ){
                    status = GameStatus.Failed;
                } else {
                    status = GameStatus.Won;
                }
                displayAllMines();
                WindowSeedLabel.getStaticSeedLabel().setEnabled( true );
                return true;
            } else {
                return false;
            }
        } catch ( ArrayIndexOutOfBoundsException | NullPointerException e ) {
            return false;
        }
    }

    public boolean publicNode(Position pos,int hashCode){
        try {
            if ( nodes[ pos.X ][ pos.Y ].hashCode() == hashCode && !nodes[ pos.X ][ pos.Y ].isPublic()) {
                if (restSaveNodeCount > 0){
                    restSaveNodeCount--;
                } else {
                    return false;
                }
                return true;
            } else {
                return false;
            }
        } catch ( ArrayIndexOutOfBoundsException | NullPointerException e ) {
            return false;
        }
    }

    private void displayAllMines(){
        for ( int i = 0; i < nodes.length; i++ ) {
            for ( int j = 0; j < nodes[ i ].length; j++ ) {
                if (nodes[i][j].getType() == NodeTypes.Mine ){
                    nodes[i][j].setPublic( true,this );
                    if (status == GameStatus.Won){
                        WindowPanel.getStaticPanel().getButton( i,j ).setBackground( new Color( 127,255,127 ) );
                        WindowPanel.getStaticPanel().getButton( i,j ).setText( "#" );
                        WindowPanel.getStaticPanel().disEnabledButton( i,j );
                    }
                } else {
                    WindowPanel.getStaticPanel().disEnabledButton( i,j );
                }
            }
        }
    }

    public GameSave exportSave(){
        boolean[] isPublicNodes = new boolean[WIDTH * HEIGHT];
        for ( int i = 0; i < WIDTH * HEIGHT; i++ ) {
            isPublicNodes[i] = nodes[i/WIDTH][i%WIDTH].isPublic();
        }
        return new GameSave( MINES_COUNT, WIDTH, HEIGHT, getGameSeed(), isPublicNodes, firstStepPosition.X, firstStepPosition.Y );
    }

    public NodeTypes getNodeStatus(int x,int y){
        try {
            return nodes[x][y].getType();
        } catch ( ArrayIndexOutOfBoundsException e ) {
            return null;
        }
    }

    public NodeTypes getNodeStatus(int idx){
        return getNodeStatus( idx / WIDTH, idx % WIDTH );
    }

    public byte getNodeValue(int x,int y){
        return ( byte ) ((nodes[x][y] instanceof NumberNode ) ? (( ( NumberNode ) nodes[x][y] ).VALUE) : 0);
    }
}
