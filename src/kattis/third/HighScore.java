package kattis.third;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

//https://open.kattis.com/problems/highscore2
public class HighScore {
    public static void main(String[] args) {
//        test0();
        OnlineStreamParser parser = new OnlineStreamParser(System.in, 10);
        int n = parser.getInt();
        while(n-->0) {
            int[] a = new int[3];
            a[0] = parser.getInt();
            a[1] = parser.getInt();
            a[2] = parser.getInt();
            int b = parser.getInt();
            System.out.println(count(a,b));
        }
    }

    static void test0() {
        count(new int[]{0,1,2},2);
        for (int i = 0; i < 5; i++) {
            for (int j = i; j < 5; j++) {
                for (int k = j; k < 5; k++) {
                    for (int l = k; l < 5; l++) {
                        long v1 = test(new int[]{i,j,k}, l);
                        long v2 = count(new int[]{i,j,k},l);
                        if(v1!=v2) {
                            System.out.println(i + " " + j + " " + k + " " + l + " " + v1 + " " + v2);
                        }
                    }
                }
            }
        }
    }
    static long test(int[] a, int b) {
        if(b==0) {
            return count(a);
        }

        a[0]++;
        long r = test(a, b-1);
        a[0]--;
        a[1]++;
        r = Math.max(r, test(a, b-1));
        a[1]--;
        a[2]++;
        r = Math.max(r, test(a, b-1));
        a[2]--;
        return r;
    }

    static long count(int[] a, int b) {
        int[] c = Arrays.copyOf(a, 3);
        add(a,b);
        addToMax(c,b);
        return Math.max(count(a), count(c));
    }

    static long count(int[] a) {
        return (long) a[0]*a[0]+ (long) a[1]*a[1]+ (long) a[2]*a[2] + 7L*Math.min(a[0], Math.min(a[1], a[2]));
    }
    static void addToMax(int[] a, int b) {
        Arrays.sort(a);
        a[2]+=b;
    }

    static void add(int[] a, int b) {
        Arrays.sort(a);
        int d = Math.min(b, a[1]-a[0]);
        a[0]+=d;
        b-=d;
        if(b==0) {
            return;
        }

        if(a[0]==a[1]) {
            d = Math.min(b/2, a[2]-a[0]);
            a[0]+=d;
            a[1]+=d;
            b-=d*2;
        }

        if(b!=0) {
            if(a[0]!=a[2]) {
                a[2]+=b;
            } else {
                a[0]=a[1]=a[2]=a[2]+b/3;
                b=b%3;
                a[2]+=b;
            }
        }
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