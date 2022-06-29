package kattis.forth;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;

public class InvFactorialBinarySearch {
    HashMap<Interval, BigInteger> tree = new HashMap<>();

    public static void main(String[] args) {
        new InvFactorialBinarySearch().run();
//        System.out.println(new Third().aproxFN(205023).precision()-30);
//        System.out.println(BigDecimal.valueOf(205023)
//                .divide(new BigDecimal("2.71923"), 100, RoundingMode.CEILING));
//        System.out.println(d);
//        System.out.println(d.scale());
//        System.out.println(new Third().stirlingFormula(100));
//        System.out.println(BigDecimal.valueOf(100)
//                .divide(BigDecimal.valueOf(Math.exp(1)), 20, RoundingMode.CEILING));
    }

    int aproxFN(int n) {
        return BigDecimal.valueOf(n)
                .divide(new BigDecimal("2.71923"), RoundingMode.CEILING)
                .pow(n).multiply(BigDecimal.valueOf(Math.sqrt(Math.PI*2*n))).multiply(BigDecimal.valueOf((double)1/(12*n+1))).precision()-30;
    }

    int aproxInvF(int n) {
        int s = 1;
        int e = 205023;
        int m = e;
        while(s<=e) {
            int v = aproxFN(m);
            if(v==n) {
                return m;
            }

            if(v>n) {
                e=m-1;
            } else {
                s=m+1;
            }
            m=(s+e)/2;
        }

        return 205023;
    }

    public void run() {
        OnlineStreamParser parser = new OnlineStreamParser(System.in, 1000000);

        String str = parser.getToken();
        BigInteger value = new BigInteger(str);
        int s = 1;
        int e = aproxInvF(str.length());

        BigInteger prev = BigInteger.ONE;
        while (s <= e) {
            int m = (s + e) / 2;
            BigInteger nv;
            Interval i = new Interval(s, m);
            if (tree.containsKey(i)) {
                nv = tree.get(i);
            } else {
                nv = multiply(s, m);
            }
            BigInteger cv = prev.multiply(nv);
            int c = cv.compareTo(value);
            if (c == 0) {
                System.out.println(m);
                return;
            }
            if (c > 0) {
                e = m - 1;
            } else {
                prev = cv;
                s = m + 1;
            }
        }
    }

    BigInteger multiply(int start, int end) {
        BigInteger[] a = new BigInteger[(end-start)/2];
        if (end - start <= 64) {
            Interval i = new Interval(start, end);
            BigInteger b = mulBInt(start, end);
            tree.put(i, b);
            return b;
        }

        Interval b = new Interval(start, (end + start) >>> 1);
        Interval e = new Interval(((end + start) >>> 1) + 1, end);
        BigInteger bN = multiply(start, (end + start) >>> 1);
        BigInteger eN = multiply(((end + start) >>> 1) + 1, end);
        BigInteger fN = bN.multiply(eN);
        Interval f = new Interval(start, end);
        tree.put(b, bN);
        tree.put(e, eN);
        tree.put(f, fN);
        return fN;
    }

    BigInteger mulBInt(int start, int end) {
        BigInteger b = BigInteger.valueOf(start);
        for (int i = start + 1; i <= end; i++) {
            b = b.multiply(BigInteger.valueOf(i));
        }

        return b;
    }

    int[] mul(int i, int j, int[] b) {
        Arrays.fill(b, 0);
        long res = (long) i * j;
        return numberToArray(res, b);
    }

    int[] numberToArray(long l, int[] b) {
        int i = 0;
        while (l != 0) {
            b[i] = (int) (l % 10);
            l /= 10;
            i++;
        }
        int[] res = new int[i];
        for (int k = 0; k < res.length; k++) {
            res[k] = b[i - k - 1];

        }

        return res;
    }


    private static class Interval implements Comparable<Interval> {
        int s;
        int e;

        public Interval(int s, int e) {
            this.s = s;
            this.e = e;
        }

        @Override
        public int compareTo(Interval o) {
            return Integer.compare(s, o.s);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Interval interval = (Interval) o;

            if (s != interval.s) return false;
            return e == interval.e;
        }

        @Override
        public int hashCode() {
            int result = s;
            result = 31 * result + e;
            return result;
        }
    }

    private static class OnlineStreamParser {
        private InputStream s;
        private byte[] b;

        public OnlineStreamParser(InputStream s, int bs) {
            this.s = new BufferedInputStream(s);
            b = new byte[bs];
        }

        public int getInt() {
            char ch;
            try {
                ch = (char) s.read();
                boolean isPositive = true;
                if (ch == '-') {
                    isPositive = false;
                    ch = (char) s.read();
                }
                int i = 0;
                while ('0' <= ch && ch <= '9') {
                    i *= 10;
                    i += ch - '0';
                    ch = (char) s.read();
                }
                return isPositive ? i : -i;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public String getToken() {
            int ch;
            try {
                int i = s.read(b);
//                while ((ch = s.read(b)) != ' ' && ch != '\n' && ch!=-1) {
//                    b[i++] = (char)ch;
//                }
                return new String(Arrays.copyOf(b, i - 1));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
