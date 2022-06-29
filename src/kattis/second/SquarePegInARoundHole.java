package kattis.second;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

//https://open.kattis.com/problems/squarepegs
public class SquarePegInARoundHole {
    public static void main(String[] args) {
        OnlineStreamParser p = new OnlineStreamParser(System.in, 3);
        int m = p.getInt(), n = p.getInt(), k = p.getInt();
        int[] ma = new int[m];
        int[] na = new int[n + k];

        for (int i = 0; i < ma.length; i++) {
            ma[i] = p.getInt();
        }

        int i = 0;
        for (i = 0; i < n; i++) {
            na[i] = p.getInt();
        }

        double sqrt2 = Math.sqrt(2);
        for (; i < n + k; i++) {
            na[i] = (int) (p.getInt() / sqrt2);
        }

        Arrays.sort(ma);
        Arrays.sort(na);

        int res = 0;

        int mi = ma.length - 1;
        int ni = na.length - 1;

        while (mi >= 0 && ni >= 0) {
            if (na[ni] < ma[mi]) {
                res++;
                ni--;
                mi--;
            } else {
                ni--;
            }
        }

        System.out.println(res);
    }

    private static class OnlineStreamParser {
        private InputStream s;
        private char[] b;

        public OnlineStreamParser(InputStream s, int bs) {
            this.s = new BufferedInputStream(s);
            b = new char[bs];
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
            char ch;
            try {
                int i = 0;
                while ((ch = (char) s.read()) != ' ' && ch != '\n') {
                    b[i++] = ch;
                }
                return new String(Arrays.copyOf(b, i));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
