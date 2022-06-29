package kattis.second;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CryptographicKeys {
    public static void main(String[] args) {
        OnlineStreamParser parser = new OnlineStreamParser(System.in, 20);
        long v = parser.getLong();
        System.out.println(new CryptographicKeys().pow(v));
    }

    long pow(long n) {
        List<Long> l = primesTill((int)Math.sqrt(n));
        l.add(n);
        long[] a = l.stream().mapToLong(i->i).toArray();
        int i = 0;
        long[] num = new long[40];
        int pos = 0;
        while(i<a.length&&n!=0) {
            if(n%a[i]==0) {
                num[pos++]=a[i];
                n/=a[i];
                continue;
            }

            i++;
        }

        int max = 0;
        int c = 0;
        long prev = -1;
        long v = 0;
        for (int j = 0; j < pos; j++) {
            if(num[j]==prev) c++;
            else {c=1;prev=num[j];}
            if(c>max) {
                v=num[j];
                max=c;
            }
        }

        return v;
    }
    public static ArrayList<Long> primesTill(int n) {
        ArrayList<Long> res = new ArrayList<>();
        long i = 2;
        while (i <= n) {
//            if(i % 10000000 == 0) {
//                System.out.println(System.currentTimeMillis());
//            }
            boolean isPrime = true;
            int lim = (int)Math.sqrt(i) + 1;
            for (Long p : res) {
                if (i % p == 0) {
                    isPrime = false;
                    break;
                }
                if(p > lim) {
                    break;
                }
            }
            if (isPrime) {
                res.add(i);
            }
            i++;
        }

        return res;
    }

    private static class OnlineStreamParser {
        private InputStream s;
        private char[] b;

        public OnlineStreamParser(InputStream s, int bs) {
            this.s = new BufferedInputStream(s);
            b = new char[bs];
        }

        public long getLong() {
            char ch;
            try {
                ch = (char) s.read();
                boolean isPositive = true;
                if (ch == '-') {
                    isPositive = false;
                    ch = (char) s.read();
                }
                long i = 0;
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
