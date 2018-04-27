public class DynamicChangemakerTestHarness {

    private static int attempts = 0;
    private static int successes = 0;

    public static void main(String[] args) {
        attempts = 0;
        successes = 0;

        test_Simple(); // 1, 4, 3, 6
        test_USA();
        test_SwissFrancs(); // 5,10,20,50 (NO 1 CENT COIN!)
        test_Keckels(); // 7,3,29,15,53 (Made-up currency with non-standard denominations)

        System.out.println(successes + "/" + attempts + " tests passed.");
    }

    private static void displaySuccessIfTrue(boolean value) {
        attempts++;
        successes += value ? 1 : 0;

        System.out.println(value ? "success" : "failure");
    }

    private static void displayFailure() {
        displaySuccessIfTrue(false);
    }
    public static void test_Simple() {
        System.out.println("Testing simple denominations ...");
        int[] simpleDenominations = new int[] { 1, 4, 3, 6 };
        Tuple result = DynamicChangemaker.makeChangeWithDynamicProgramming(simpleDenominations, 13);
        try {
            displaySuccessIfTrue(1 == result.getElement(0));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }

        try {
            displaySuccessIfTrue(0 == result.getElement(1));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }

        try {
            displaySuccessIfTrue(0 == result.getElement(2));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }

        try {
            displaySuccessIfTrue(2 == result.getElement(3));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }

        Tuple result2 = DynamicChangemaker.makeChangeWithDynamicProgramming(simpleDenominations, 17);
        try {
            displaySuccessIfTrue(1 == result2.getElement(0));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }

        try {
            displaySuccessIfTrue(1 == result2.getElement(1));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }

        try {
            displaySuccessIfTrue(0 == result2.getElement(2));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }

        try {
            displaySuccessIfTrue(2 == result2.getElement(3));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }
    }

    public static void test_USA() {
        System.out.println("Testing USA denominations ...");
        int[] usaDenominations = new int[] { 25, 10, 5, 1 };

        Tuple result = DynamicChangemaker.makeChangeWithDynamicProgramming(usaDenominations, 99);


        try {
            displaySuccessIfTrue(3 == result.getElement(0));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }

        try {
            displaySuccessIfTrue(2 == result.getElement(1));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }

        try {
            displaySuccessIfTrue(0 == result.getElement(2));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }

        try {
            displaySuccessIfTrue(4 == result.getElement(3));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }


        Tuple result2 = DynamicChangemaker.makeChangeWithDynamicProgramming(usaDenominations, 101);


        try {
            displaySuccessIfTrue(4 == result2.getElement(0));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }

        try {
            displaySuccessIfTrue(0 == result2.getElement(1));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }

        try {
            displaySuccessIfTrue(0 == result2.getElement(2));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }

        try {
            displaySuccessIfTrue(1 == result2.getElement(3));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }
    }

    public static void test_SwissFrancs() {
        System.out.println("Testing Swiss denominations ...");
        int[] swissDenominations = new int[] { 5, 10, 20, 50 };

        Tuple result = DynamicChangemaker.makeChangeWithDynamicProgramming(swissDenominations, 115);
        try {
            displaySuccessIfTrue(1 == result.getElement(0));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }

        try {
            displaySuccessIfTrue(1 == result.getElement(1));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }

        try {
            displaySuccessIfTrue(0 == result.getElement(2));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }

        try {
            displaySuccessIfTrue(2 == result.getElement(3));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }


        Tuple result2 = DynamicChangemaker.makeChangeWithDynamicProgramming(swissDenominations, 4);
        try {
            displaySuccessIfTrue(result2.isImpossible());
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }

    }
    public static void test_Keckels() {
        System.out.println("Testing Keck denominations ...");
        int[] keckDenominations = new int[] { 7, 3, 29, 15, 53 };

        Tuple result = DynamicChangemaker.makeChangeWithDynamicProgramming(keckDenominations, 35);
        try {
            displaySuccessIfTrue(0 == result.getElement(0));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }

        try {
            displaySuccessIfTrue(2 == result.getElement(1));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }

        try {
            displaySuccessIfTrue(1 == result.getElement(2));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }

        try {
            displaySuccessIfTrue(0 == result.getElement(3));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }

        try {
            displaySuccessIfTrue(0 == result.getElement(4));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }

        Tuple result2 = DynamicChangemaker.makeChangeWithDynamicProgramming(keckDenominations, 36);
        try {
            displaySuccessIfTrue(1 == result2.getElement(0));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }

        try {
            displaySuccessIfTrue(0 == result2.getElement(1));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }

        try {
            displaySuccessIfTrue(1 == result2.getElement(2));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }

        try {
            displaySuccessIfTrue(0 == result2.getElement(3));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }

        try {
            displaySuccessIfTrue(0 == result2.getElement(4));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }

        Tuple result3 = DynamicChangemaker.makeChangeWithDynamicProgramming(keckDenominations, 74);
        try {
            displaySuccessIfTrue(0 == result3.getElement(0));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }

        try {
            displaySuccessIfTrue(2 == result3.getElement(1));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }

        try {
            displaySuccessIfTrue(0 == result3.getElement(2));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }

        try {
            displaySuccessIfTrue(1 == result3.getElement(3));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }

        try {
            displaySuccessIfTrue(1 == result3.getElement(4));
        } catch (Exception e) {
            e.printStackTrace();
            displayFailure();
        }
    }
}
