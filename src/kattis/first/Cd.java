package kattis.first;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

//https://open.kattis.com/problems/cd
public class Cd {
    public static void main(String[] args) {
        OnlineStreamParser parser = new OnlineStreamParser(System.in, 10);
        int s1,s2;
        int[] a1,a2;
        while(true) {
            s1 = parser.getInt();
            s2 = parser.getInt();
            if(s1==0&&s2==0) {
                return;
            }
            if(s1==0){
                System.out.println(0);
                continue;
            }
            if(s2==0){
                System.out.println(0);
                continue;
            }
            a1=new int[s1];
            int i = s1;
            while(i>0) {
                a1[s1-i]=parser.getInt();
                i--;
            }
            a2=new int[s2];
            i = s2;
            while(i>0) {
                a2[s2-i]=parser.getInt();
                i--;
            }
            i=0;
            int j=0;
            int res=0;
            while(i<s1&&j<s2) {
                if(a1[i]==a2[j]) {
                    res++;
                    i++;
                    j++;
                    continue;
                }
                if(a1[i]<a2[j]) {
                    i++;
                    continue;
                }
                j++;
            }
            System.out.println(res);
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
