package kattis.first;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

//https://open.kattis.com/problems/goblingardenguards
public class GoblinGardenGuards {
    public static void main(String[] args) {
        OnlineStreamParser p = new OnlineStreamParser(System.in, 6);
        int[][] pp = new int[10001][];
        int np = p.getInt();
        int i = np;
        while(i>0) {
            i--;
            int x=p.getInt(),y=p.getInt();
            if(pp[x]==null) {
                pp[x]=new int[10001];
            }
            pp[x][y]++;
        }
        int cn = p.getInt();
        while(cn>0) {
            cn--;
            int x=p.getInt(),y=p.getInt(),r=p.getInt();
            int xb = Math.min(x+r,10000);
            int rs = r*r;
            for (i=Math.max(x-r,0); i <= xb; i++) {
                int yb = Math.min(y+r,10000);
                int fs=(x-i)*(x-i);
                for (int j=Math.max(y-r,0); j <= yb; j++) {
                    if(fs+(y-j)*(y-j)<=rs&&pp[i]!=null) {
                        np-=pp[i][j];
                        pp[i][j]=0;
                    }
                }
            }
        }

        System.out.println(np);
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