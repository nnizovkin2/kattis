package kattis.first;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

//https://open.kattis.com/problems/conquestcampaign
public class ConquestCampaign {
    public static void main(String[] args) {
        OnlineStreamParser p = new OnlineStreamParser(System.in, 5);
        int r=p.getInt(),c=p.getInt(),n=p.getInt();
        int i = 0;
        int[] a=new int[r*c];
        int[] t=new int[r*c];
        int tl;
        int l = n;
        boolean[][] occupy = new boolean[r][c];
        for (int j = 0; j < occupy.length; j++) {
            occupy[j]=new boolean[c];
        }
        while(i<n) {
            int x=p.getInt(),y=p.getInt();
            occupy[x-1][y-1]=true;
            i++;
        }

        i=0;
        for (int j = 0; j < occupy.length; j++) {
            for (int k = 0; k < occupy[j].length; k++) {
                if(occupy[j][k]) {
                    a[i++]=(j<<16)+k;
                }
            }
        }
        l=i;
        i = 0;
        int rest = c*r;
        while(rest>0) {
            i++;
            tl=0;
            for (int j = 0; j < l; j++) {
                int x=a[j]>>16,y=(a[j]<<16)>>16;
                rest--;
                occupy[x][y]=true;
                if(x-1>=0&&!occupy[x-1][y]){ occupy[x-1][y]=true;t[tl++]=((x-1)<<16)+y;}
                if(x+1<r&&!occupy[x+1][y]){ occupy[x+1][y]=true;t[tl++]=((x+1)<<16)+y;}
                if(y-1>=0&&!occupy[x][y-1]){ occupy[x][y-1]=true;t[tl++]=(x<<16)+y-1;}
                if(y+1<c&&!occupy[x][y+1]){ occupy[x][y+1]=true;t[tl++]=(x<<16)+y+1;}
            }
            System.arraycopy(t,0, a,0, tl);
            l=tl;
        }

        System.out.println(i);
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