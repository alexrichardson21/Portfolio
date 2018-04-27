public class Fibonacci {
    public static BigInteger Fibonacci ( int nth )  {
        BigInteger num = new BigInteger("1");
        BigInteger prevnum = new BigInteger("0");
        for (int i = 2; i < nth; i++) {
            BigInteger tempnum = new BigInteger(num.toString());
            num = num.plus(prevnum);
            prevnum = tempnum;
        }
        return num;
    }
    public static void main(String[] args) {
        try {
            int nth = Integer.parseInt(args[0]);
            BigInteger result = Fibonacci(nth);
            System.out.println(result.toString());
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            System.out.println("Usage instructions: java Fibonacci <degree>");
        } catch (NumberFormatException nfe) {
            System.out.println("Usage instructions: java Fibonacci <degree>");
        } catch (Exception e) {
            System.out.println("Usage instructions: java Fibonacci <degree>");
            e.printStackTrace();
        }
    }
}
