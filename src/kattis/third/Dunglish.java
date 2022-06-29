package kattis.third;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Dunglish {
    public static void main(String[] args) {
        Map<String, Integer> mapC = new HashMap<>();
        Map<String, Integer> mapU = new HashMap<>();
        Map<String, String> map = new HashMap<>();

        OnlineStreamParser parser = new OnlineStreamParser(System.in, 20);
        int n = parser.getInt();

        String[] s = new String[n];
        int i = 0;
        while(i<n) {
            s[i++]=parser.getToken();
        }

        int k = parser.getInt();
        while(k-->0) {
            String s1=parser.getToken();
            String s2=parser.getToken();
            if(parser.getToken().equals("correct")) {
                mapC.put(s1, mapC.getOrDefault(s1, 0)+1);
            } else {
                mapU.put(s1, mapU.getOrDefault(s1, 0)+1);
            }

            map.put(s1, s2);
        }

        long c = 1;
        long inc = 1;

        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < s.length; j++) {
            String word = s[j];
            c*=mapC.getOrDefault(word, 0);
            inc*=mapC.getOrDefault(word, 0)+mapU.getOrDefault(word, 0);
            sb.append(map.getOrDefault(word, ""));
            if(j<s.length-1) {
                sb.append(' ');
            }
        }

        inc-=c;

        if(c==1&&inc==0) {
            System.out.println(sb);
            System.out.println("correct");
        } else if(c==0&&inc==1){
            System.out.println(sb);
            System.out.println("incorrect");
        } else {
            System.out.println(c + " correct");
            System.out.println(inc + " incorrect");
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
