package kattis.first;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

//https://open.kattis.com/problems/bing
public class BingItOn {
    public static void main(String[] args) {
        OnlineStreamParser parser = new OnlineStreamParser(System.in, 32);
        int n = parser.getInt();
        L root = new L();
        String s;
        for (int i = 0; i < n; i++) {
            L pos = root;
            char[] a = parser.getToken().toCharArray();
            for (char c: a) {
                pos.t++;
                if(pos.childs==null) {
                    pos.childs=new L[26];
                }
                if(pos.childs[c-'a']==null) {
                    pos.childs[c-'a'] = new L();
                }
                pos=pos.childs[c-'a'];
            }
            System.out.println(pos.t);
            pos.t++;
        }
    }

    private static class L {
        L[] childs;
        int t = 0;
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
