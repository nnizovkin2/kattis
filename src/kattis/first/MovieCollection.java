package kattis.first;

import java.io.*;
import java.util.Arrays;

//https://open.kattis.com/problems/moviecollection
public class MovieCollection {
    public static void main(String[] args) throws IOException {
        OnlineStreamParser p = new OnlineStreamParser(System.in, 5);
        int tn = p.getInt();
        while(tn-->0){
            StringBuilder builder = new StringBuilder();
            int n=p.getInt(),k=p.getInt();
            int[] a = new int[n];
            int ind = n;
            Stat tree = new Stat(200000);
            for (int i = 0; i < a.length; i++) {
                a[i]=a.length-i;
            }
            for (int i = 0; i < k; i++) {
                int d=p.getInt()-1;
                int dInd=a[d];
                int up = n-dInd+tree.findRank(dInd);
                a[d]=++ind;
                tree.insertElement(dInd);
                builder.append(up);
                if(i<k-1) {
                    builder.append(' ');
                }
            }
            System.out.println(builder);
        }
    }

    //take from here https://www.geeksforgeeks.org/order-statistic-tree-using-fenwick-tree-bit/
    static class Stat {
        int[] BIT;
        int size;

        public Stat(int size) {
            this.size=size;
            BIT = new int[size];
        }

        public void clear() {
            BIT=new int[size];
        }

        void update(int i, int add) {
            while (i > 0 && i < BIT.length) {
                BIT[i] += add;
                i = i + (i & (-i));
            }
        }

        int sum(int i) {
            int ans = 0;
            while (i > 0) {
                ans += BIT[i];
                i = i - (i & (-i));
            }
            return ans;
        }

        void insertElement(int x) {
            update(x, 1);
        }

        void deleteElement(int x) {
            update(x, -1);
        }

        int findRank(int x) {
            return sum(x);
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