public class DynamicChangemaker {

    public static void main(String[] args) {
        if (args.length != 2) {
            printUsage();
            return;
        }

        try {

            String[] denominationStrings = args[0].split(",");
            int[] denominations = new int[denominationStrings.length];

            for (int i = 0; i < denominations.length; i++) {
                denominations[i] = Integer.parseInt(denominationStrings[i]);
                if (denominations[i] <= 0) {
                    System.out.println("Denominations must all be greater than zero.\n");
                    printUsage();
                    return;
                }

                for (int j = 0; j < i; j++) {
                    if (denominations[j] == denominations[i]) {
                        System.out.println("Duplicate denominations are not allowed.\n");
                        printUsage();
                        return;
                    }
                }
            }

            int amount = Integer.parseInt(args[1]);
            if (amount < 0) {
                System.out.println("Change cannot be made for negative amounts.\n");
                printUsage();
                return;
            }



            Tuple change = makeChangeWithDynamicProgramming(denominations, amount);
            if (change.isImpossible()) {
                System.out.println("It is impossible to make " + amount + " cents with those denominations.");
            } else {
                int coinTotal = change.total();
                System.out.println(amount + " cents can be made with " + coinTotal + " coin" +
                        getSimplePluralSuffix(coinTotal) + " as follows:");

                for (int i = 0; i < denominations.length; i++) {
                    int coinCount = change.getElement(i);
                    System.out.println("- "  + coinCount + " " + denominations[i] + "-cent coin" +
                            getSimplePluralSuffix(coinCount));
                }
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Denominations and amount must all be integers.\n");
            printUsage();
        }
    }

    public static Tuple makeChangeWithDynamicProgramming(int[] denominations, int amount) {
        // inits
        Tuple[][] tuples = new Tuple[ denominations.length ][ amount + 1 ];

        // init zeros
        int count = 0;
        while (count < denominations.length) {
            tuples[count][0] = new Tuple( denominations.length );
            count++;
        }

        // i is denomination / row
        // j is amount / col
        for (int i = 0; i < denominations.length; i++){
            for (int j = 1; j <= amount; j++) {
                // init
                tuples[i][j] = new Tuple( denominations.length );
                // if they can't be subtracted it is IMPOSSIBLE
                if (j - denominations[i] < 0) {
                    tuples[i][j] = Tuple.IMPOSSIBLE;
                // else if they can be subtracted
                } else {
                    // sets element at denomination to 1
                    tuples[i][j].setElement( i, 1 );
                    // looks horizontal to add another tuple if it is not IMPOSSIBLE
                    if ( tuples[i][ j - denominations[i] ].isImpossible() || tuples[i][ j - denominations[i] ] == null) {
                        tuples[i][j] = Tuple.IMPOSSIBLE;
                    } else {
                        tuples[i][j] = tuples[i][j].add( tuples[i][ j - denominations[i] ] );
                    }
                }



                // Compares with a cell above on the table
                if (i - 1 >= 0 && tuples[i - 1][j].isImpossible() == false) {
                    if (tuples[i][j].isImpossible()) {
                        tuples[i][j] = tuples[i - 1][j];
                    } else {
                        if (tuples[i - 1][j].total() < tuples[i][j].total()) {
                            tuples[i][j] = tuples[i - 1][j];
                        }
                    }
                }
            }
        }

        // returns bottom right corner element
        return tuples[ denominations.length - 1 ][ amount ];
    }

    private static void printUsage() {
        System.out.println("Usage: java DynamicChangemaker <denominations> <amount>");
        System.out.println("  - <denominations> is a comma-separated list of denominations (no spaces)");
        System.out.println("  - <amount> is the amount for which to make change");
    }

    private static String getSimplePluralSuffix(int count) {
        return count == 1 ? "" : "s";
    }

}
