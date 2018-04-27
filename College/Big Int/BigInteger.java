public class BigInteger {
    private int[] bigIntArray; // bigIntArray[0] contains either 1 or -1 to determine positive / negative
    private int negative;

    public BigInteger ( String val ) {

        String intString = val.trim();


        if (intString.charAt(0) == '-'){
            this.negative = -1;
            intString = intString.substring(1);
        } else if (intString.charAt(0) == '+'){
            this.negative = 1;
            intString = intString.substring(1);

        } else {
            this.negative = 1;
        }


        while (intString.charAt(0) == '0' && intString.length() > 1) {
            intString = intString.substring(1);
        }

        this.bigIntArray = new int[intString.length()];

        for (int i = 0; i < bigIntArray.length; i++) {
            bigIntArray[i] = Integer.parseInt("" + intString.charAt(i));
        }

    }
    // one of several java.math.BigInteger constructors
/*
    public BigInteger plus ( BigInteger val ){

        for (int i = val.toString().length(); i >= 1; i--) {
            bigIntArray[i] += val.getDigitAt(i);
        }
        //resize( '+', val );
        int count = 0;
        for (int i = val.toString().length(); i >= 0; i--) {
            if (val.getDigitAt(i) + bigIntArray[i] >= 10)
                count++;
        }
        int[] tempIntArray = new int[ bigIntArray.length + count ];

        for (int i = val.toString().length(); i >= 0; i--) {
            tempIntArray[i] = bigIntArray[i];
            tempIntArray[i] += digitAt(i + 1, 0);
            tempIntArray[i] = digitAt(i, 0);
        }


    } // returns a BigInteger whose value is this + val

    public int digitAt (int index, int place) {
        if (place > (bigIntArray[index].toString().length())) {
            return 0;
        } else {
            return Integer.parseInt(bigIntArray[index].toString().charAt(bigIntArray[index].toString().length() - place));
        }
    }
*/


    public int getDigitAt ( int index ){
        if ( index >= 0 && index < bigIntArray.length ) {
            return bigIntArray[index];
        } else {
            return 0;
        }
    }
    public int length () {
        return bigIntArray.length;
    }
    public int[] leadingZeros ( int[] val, int zeros ) {
        int[] tempIntArray = new int[ val.length + zeros ];
        for (int i = 0; i < val.length; i++) {
            tempIntArray[i + zeros] = val[i];
        }
        for (int i = 0; i < zeros; i++) {
            tempIntArray[i] = 0;
        }
        return tempIntArray;
    } // resizes with leading zeros



    public BigInteger plus ( BigInteger val ){
        int sizeDiff = Math.abs(val.length() - bigIntArray.length);


        if (this.negative == -1 && val.isNegative() == -1){
            negative = -1;
        } else if (this.negative == 1 && val.isNegative() == 1){
            negative = 1;
        } else if (this.negative == -1 && val.isNegative() == 1){
            this.opposite();
            return val.minus(this);
        } else {
            val.opposite();
            return minus(val);
        }
        // SAME LENGTH
        if (sizeDiff == 0) {

            for (int i = bigIntArray.length - 1; i >= 0; i--) {
                //combine int array
                if (bigIntArray[i] + val.getDigitAt(i) < 10) {
                    bigIntArray[i] += val.getDigitAt(i);
                } else {
                    bigIntArray[i] += val.getDigitAt(i);
                    bigIntArray[i] -= 10;
                    if (i > 0){
                        bigIntArray[i - 1] += 1;
                    } else {

                        bigIntArray = leadingZeros(bigIntArray, 1);
                        bigIntArray[i] += 1;
                    }

                }

            }

            /*if (bigIntArray[0] > 10) {
                bigIntArray = leadingZeros(bigIntArray, 1);
                bigIntArray[1] -= 10;
                bigIntArray[0] = 1;
            }*/

        // VAL LARGER
        } else if (val.length() > bigIntArray.length) {
            bigIntArray = leadingZeros(bigIntArray,  sizeDiff);

            for (int i = val.length() - 1; i >= 0; i--) {
                //combine int array
                if (bigIntArray[i] + val.getDigitAt(i) < 10) {
                    bigIntArray[i] += val.getDigitAt(i);
                } else {
                    bigIntArray[i] += val.getDigitAt(i);
                    bigIntArray[i] -= 10;
                    if ((i - 1) >= 0){
                        bigIntArray[i - 1] += 1;
                    } else {
                        bigIntArray = leadingZeros(bigIntArray, 1);
                        bigIntArray[i] += 1;
                    }

                }
            }

        // Val Smaller
        } else {
            for (int i = bigIntArray.length - 1; i >= 0; i--) {
                //combine int array
                if (bigIntArray[i] + val.getDigitAt(i - sizeDiff) < 10) {
                    bigIntArray[i] += val.getDigitAt(i - sizeDiff);
                } else {
                    bigIntArray[i] += val.getDigitAt(i - sizeDiff);
                    bigIntArray[i] -= 10;
                    if ((i - 1) >= 0){
                        bigIntArray[i - 1] += 1;
                    } else {
                        bigIntArray = leadingZeros(bigIntArray, 1);
                        bigIntArray[i] += 1;
                    }
                }

            }
        }


        //System.out.println(this);

        /*if (bigIntArray[0] + val.getDigitAt(0) < 10) {

            bigIntArray = new int[ bigIntArray.length + 1 ];
            bigIntArray[1] -= 10;
            bigIntArray[0] += 1;
        }*/
        return this;
    }
    public BigInteger minus ( BigInteger val ) {
        int sizeDiff = val.length() - bigIntArray.length;

        if (val.length() > bigIntArray.length) {
            bigIntArray = leadingZeros(bigIntArray, sizeDiff);
            sizeDiff = 0;
        }

        if (val.isNegative() == -1 && this.negative == 1){
            this.opposite();
            return val.plus(this);
        } else if (this.negative == -1 && val.isNegative() == 1){
            val.opposite();
            return val.plus(this);
        } else if (val.isNegative() == 1 && this.negative == 1){
            for (int i = bigIntArray.length - 1; i >= 0; i--) {
                    //combine int array

                if (bigIntArray[i] - val.getDigitAt(i - sizeDiff) >= 0) {
                    bigIntArray[i] -= val.getDigitAt(i - sizeDiff);
                } else if (bigIntArray[i] - val.getDigitAt(i - sizeDiff) < 0){
                    //System.out.println(this);
                    if (i > 0){
                        bigIntArray[i] += 10;
                        bigIntArray[i - 1] -= 1;
                        bigIntArray[i] -= val.getDigitAt(i - sizeDiff);
                    } else {
                        negative = -1;
                        bigIntArray[i] -= val.getDigitAt(i - sizeDiff);
                        bigIntArray[i] *= -1;
                    }
                }
            }
        } else {
            val.opposite();
            this.opposite();
            return val.minus(this);
        }

        //REMOVES LEADING ZEROS


        return this;
    } // returns a BigInteger whose value is this - val
    public BigInteger times ( BigInteger val ) {
        throw new UnsupportedOperationException();
    } // returns a BigInteger whose value is this * val
    public BigInteger divideBy ( BigInteger val ) {
        throw new UnsupportedOperationException();
    } // returns a BigInteger whose value is this / val
    public BigInteger remainder ( BigInteger val ) {
        throw new UnsupportedOperationException();
    } // returns a BigInteger whose value is this % val

    public String toString () {
        String string = "";
        if (negative == -1){
            string = "-";
        }
        for (int i = 0; i < bigIntArray.length; i++) {
            string += bigIntArray[i];
        }
        return string;
    } // returns the decimal string represention of this BigInteger

    public int isNegative () {
        return negative;
    }

    public boolean greaterThan ( BigInteger val ){
        if (val.isNegative() == 1 && negative == -1) {
            return false;
        } else if (val.isNegative() == -1 && negative == 1){
            return true;
        } else if (val.isNegative() == -1 && negative == -1) {
            if (val.length() > bigIntArray.length) {
                return true;
            }
            if (val.length() < bigIntArray.length) {
                return false;
            }
            for (int i = 0; i < bigIntArray.length; i++) {
                if (bigIntArray[i] < val.getDigitAt(i)){
                    return true;
                } else if (bigIntArray[i] > val.getDigitAt(i)) {
                    return false;
                }
            }
        } else {
            if (val.length() > bigIntArray.length) {
                return false;
            }
            if (val.length() < bigIntArray.length) {
                return true;
            }
            for (int i = 0; i < bigIntArray.length; i++) {
                if (bigIntArray[i] > val.getDigitAt(i)){
                    return true;
                } else if (bigIntArray[i] < val.getDigitAt(i)) {
                    return false;
                }
            }
        }
        return false;
    } // returns true if this BigInteger is numerically greater than val

    public boolean lessThan ( BigInteger val ) {
        if (this.equals(val)) {
            return false;
        } else if (this.greaterThan(val)) {
            return false;
        } else {
            return true;
        }
    } // returns true if this BigInteger is numerically less than val

    public boolean equals ( Object x ) {
        BigInteger val = (BigInteger) x;
        if (val.isNegative() == -1 * negative) {
            return false;
        }
        for (int i = 0; i < bigIntArray.length; i++) {
            if (bigIntArray[i] != val.getDigitAt(i)){
                return false;
            }
        }
        return true;
    } // returns true iff x is a BigInteger whose value is numerically equal to this BigInteger

    public void opposite ( ) {
        negative = -1 * negative;
    }

    public static final BigInteger ZERO = new BigInteger("0");// a classwide constant for zero
    public static final BigInteger ONE = new BigInteger("1"); // a classwide constant for one
    public static final BigInteger TEN = new BigInteger("10"); // a classwide constant for ten
    public static BigInteger valueOf ( long val ) {
        return new BigInteger("" + val);
    } // "static factory" for constructing BigIntegers out of longs
}
