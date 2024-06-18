package gameBase.util;

import gameBase.elements.EmptyNode;
import gameBase.elements.MineNode;
import gameBase.elements.Node;
import gameBase.elements.NumberNode;
import gameBase.elements.Position;

import java.util.Arrays;

public final class MapCreator {
    private MapCreator(){}

    /**
     * 获取随机序列
     * @return 随机序列[ true = mines ]
     */
    private static byte[] getRandomSequence( int width, int height, int minesCount, LinearRandom randomDataGenerator , Position firstStepPosition){
        byte[] sequence = new byte[ width * height ];
        try {
            int emptyCount = getEmptyNodesCount( firstStepPosition, width, height );
            try {
                Arrays.fill( sequence, emptyCount , emptyCount + minesCount , (byte)-1 );
            } catch ( ArrayIndexOutOfBoundsException e ) {
                // Escape the Array Index out
            }
            // generate sequence
            for ( int i = emptyCount; i < sequence.length; i++ ){
                swapNode( sequence, i , randomDataGenerator.next(emptyCount, sequence.length) );
            }
            // swap 1st step
            importFirstClickNode( sequence , firstStepPosition , width, height);
            // get Numbers
            for ( int i = 0; i < sequence.length; i++ ) {
                if (sequence[i] == -1){
                    continue;
                }
                sequence[i] = getMinesCountAroundNode( i, sequence, width, height );
            }
        } catch ( NullPointerException e ){
            // 这种情况下只有调用时没有，所以直接抛出即可
            throw new RuntimeException(e);
        }
        return sequence;
    }

    /**
     * 固定种子
     * @param randomDataGenerator 随机数生成器
     */
    public static void spawnMap ( Node[][] nodes, int minesCount, LinearRandom randomDataGenerator, Position firstStepPosition){
        int height = nodes.length;
        int width = nodes[0].length;
        byte[] result = getRandomSequence(width, height, minesCount,randomDataGenerator, firstStepPosition);
        // spawn
        for ( int i = 0; i < height; i++ ) {
            for ( int j = 0; j < width; j++ ) {
                nodes[i][j] = (switch ( result[i*width+j] ){
                    case -1 -> new MineNode( i,j );
                    case 0 -> new EmptyNode( i,j );
                    default -> new NumberNode( i,j,result[i*width+j] );
                });
//                System.out.print( ( result[ i * width + j ] == - 1 ) ? "*" : result[ i * width + j ] );
            }
//            System.out.println();
        }
    }

    private static void swapNode( byte[] array, int index1, int index2){
        try {
            byte temp = array[ index1 ];
            array[ index1 ] = array[ index2 ];
            array[ index2 ] = temp;
        } catch ( ArrayIndexOutOfBoundsException e ) {
            return;
        }
    }

    private static void importFirstClickNode ( byte[] sequence , Position firstStepPosition, int width, int height ) {
        int p = 0;
        swapNode( sequence, (firstStepPosition.X + 1) * width + firstStepPosition.Y + 1, p++);
        swapNode( sequence, (firstStepPosition.X + 1) * width + firstStepPosition.Y , p++);
        swapNode( sequence, (firstStepPosition.X + 1) * width + firstStepPosition.Y - 1 , p++);
        swapNode( sequence, ( firstStepPosition.X ) * width + firstStepPosition.Y + 1, p++ );
        swapNode( sequence, (firstStepPosition.X) * width + firstStepPosition.Y , p++);
        swapNode( sequence, (firstStepPosition.X) * width + firstStepPosition.Y - 1 , p++);
        swapNode( sequence, (firstStepPosition.X - 1) * width + firstStepPosition.Y + 1, p++);
        swapNode( sequence, (firstStepPosition.X - 1) * width + firstStepPosition.Y , p++);
        swapNode( sequence, (firstStepPosition.X - 1) * width + firstStepPosition.Y - 1 , p++);
    }

    /**
     * 返回某一位置附近地雷的数量
     * @param pos 位置[一维索引]
     * @param sequence [序列数组]
     * @param width [宽]
     * @param height [长]
     * @return 数值
     */
    private static byte getMinesCountAroundNode ( int pos, byte[] sequence, int width, int height ) {
        byte cnt = 0;
        cnt += judgeMinesNode( sequence, pos / width - 1,pos % width - 1, width, height );
        cnt += judgeMinesNode( sequence, pos / width - 1,pos % width, width, height );
        cnt += judgeMinesNode( sequence, pos / width - 1,pos % width + 1, width, height );
        cnt += judgeMinesNode( sequence, pos / width,pos % width - 1, width, height );
        cnt += judgeMinesNode( sequence, pos / width,pos % width + 1, width, height );
        cnt += judgeMinesNode( sequence, pos / width + 1,pos % width - 1, width, height );
        cnt += judgeMinesNode( sequence, pos / width + 1,pos % width, width, height );
        cnt += judgeMinesNode( sequence, pos / width + 1,pos % width + 1, width, height );
        return cnt;
    }

    private static byte judgeMinesNode ( byte[] sequence, int x, int y , int width, int height) {
        if (x < 0 || y < 0 || x >= height || y >= width){
            return 0;
        }
        try {
            if ( sequence[ x * width + y ] == -1 ) {
                return 1;
            }
        } catch ( ArrayIndexOutOfBoundsException e ) {
            return 0;
        }
        return 0;
    }

    /**
     * 获取初始节点附近数量
     * @return 初始节点附近的数量
     */
    private static int getEmptyNodesCount( Position firstStepPosition, int width, int height ){
        if (firstStepPosition == null){
            return 0;
        }
        int count = 1;
        if (firstStepPosition.X > 0 && firstStepPosition.Y > 0){
            count++;
        }
        if (firstStepPosition.X > 0){
            count++;
        }
        if (firstStepPosition.X > 0 && firstStepPosition.Y < width - 1){
            count++;
        }
        if (firstStepPosition.Y > 0){
            count++;
        }
        if (firstStepPosition.Y < width - 1){
            count++;
        }
        if (firstStepPosition.X < height - 1 && firstStepPosition.Y > 0){
            count++;
        }
        if (firstStepPosition.X < height - 1){
            count++;
        }
        if (firstStepPosition.X < height - 1 && firstStepPosition.Y < width - 1){
            count++;
        }
        return count;
    }
}
