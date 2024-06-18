package GameBase.Util;


public final class LinearRandom {
    public final long SEED;
    private int NEXT = 1;

    private long value;

    private static final long[] ADDITIONS = new long[]{
            375251, 719717, 559901, 433229, 329111, 226669, 484301, 630589,
            662681, 790327, 178781, 333757, 811511, 958543, 399587, 148061,
            379501, 619139, 737203, 169019, 781681, 810307, 169319, 480287,
            236323, 831829, 230213, 214541, 294289, 144271, 710971, 218417,
            717011, 575557, 941609, 619009, 594709, 901717, 286289, 141221,
            139169, 417457, 258157, 531287, 253703, 644141, 311137, 622997,
            762973, 867463, 345547, 108461, 626489, 530797, 344231, 515843
    };

    private static final long[] MULTIPLIERS = new long[]{
            55147, 45989, 64693, 69019, 54577, 49393, 76777, 65269,
            64091, 44647, 73277, 67979, 85093, 78797, 36011, 97283,
            35753, 37321, 61031, 18253, 97771, 89899, 67021, 33289,
            11411, 87691, 72739, 16573, 29741, 17881, 86371, 39019
    };

    private static final long[] XOR_NUMBERS = new long[]{
            107839, 902849, 533053, 831751, 460451, 506899, 526681, 675823,
            636829, 442283, 346439, 352123, 296159, 373757, 534059, 758111,
            792257, 232853, 113093, 930287, 492659, 450799, 947351, 369361,
            720877, 451541, 581473, 956051, 346553, 496439, 940127, 830111
    };

    private static final long MOD = 65536;

    private int addition_index = 0;
    private int multiplier_index = 0;
    private int xor_index = 0;

    public LinearRandom ( long SEED ) {
        this.SEED = SEED;
        initializeSeed( SEED );
    }

    private void initializeSeed( long SEED ){
        if (SEED < 0){
            SEED = -SEED;
            NEXT = -1;
        }
        this.value = SEED % MOD;
        SEED %= (ADDITIONS.length * MULTIPLIERS.length * XOR_NUMBERS.length);
        addition_index = (int)(SEED % ADDITIONS.length);
        SEED /= ADDITIONS.length;
        multiplier_index = (int)(SEED % MULTIPLIERS.length);
        SEED /= MULTIPLIERS.length;
        xor_index = (int)(SEED % XOR_NUMBERS.length);
    }

    private static final long TIME_XOR_VALUE = 733989247218263L;
    private static final long TIME_SIGNED_XOR_VALUE = 232051339277467L;

    public LinearRandom (){
        LinearRandom linearRandom = new LinearRandom( ( System.currentTimeMillis() ^ TIME_XOR_VALUE ) * ( ( TIME_SIGNED_XOR_VALUE ^ System.currentTimeMillis() ) % 16 > 7 ? - 1 : 1 ) );
        long seed = linearRandom.next();
        seed <<= 16;
        seed |= linearRandom.next();
        seed <<= 16;
        seed |= linearRandom.next();
        seed <<= 10;
        seed |= linearRandom.next(0,1024);
        seed *= linearRandom.next(0,2) == 0 ? 1 : -1;
        this.SEED = seed;
        initializeSeed( seed );
    }

    /**
     * Check Variation Indexes
     */

    private void checkIndexes(){
        addition_index = (addition_index + ADDITIONS.length) % ADDITIONS.length;
        multiplier_index = (multiplier_index + MULTIPLIERS.length) % MULTIPLIERS.length;
        xor_index = (xor_index + XOR_NUMBERS.length) % XOR_NUMBERS.length;
    }

    /**
     * get random number range [0,65536)
     * @return 随机数
     */
    public int next(){
        value ^= XOR_NUMBERS[ xor_index ];
        value *= MULTIPLIERS[ multiplier_index ];
        value += ADDITIONS[ addition_index ];
        xor_index += NEXT;
        multiplier_index += NEXT;
        addition_index += NEXT;
        checkIndexes();
        value %= MOD;
        return (int)value;
    }

    /**
     * get random number range [minNumber,maxNumber) (MAX 65536)
     * @param minNumber 最小值
     * @param maxNumber 最大值(不包含)
     * @return 随机数
     */
    public int next(int minNumber,int maxNumber){
        return next()%(maxNumber-minNumber)+minNumber;
    }

    /**
     * get random number range [0,maxNumber) (MAX 65536)
     * @param maxNumber 最大值(不包含)
     * @return 随机数
     */
    public int next(int maxNumber){
        return next(0,maxNumber);
    }
}
