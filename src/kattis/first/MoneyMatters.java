package kattis.first;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

//https://open.kattis.com/problems/moneymatters
public class MoneyMatters {
    public static void main(String[] args) {
        OnlineStreamParser p = new OnlineStreamParser(System.in, 5);
        int n=p.getInt(),k=p.getInt();
        int[] w = new int[n];
        int i = 0;
        while(i<n) {
            w[i++]=p.getInt();
        }

        List<Integer>[] edges = new ArrayList[n];
        i=0;
        int s,e;
        while(i<k) {
            i++;
            s=p.getInt();
            e=p.getInt();
            if(edges[s]==null) {
                edges[s]=new ArrayList<>();
            }
            if(edges[e]==null) {
                edges[e]=new ArrayList<>();
            }
            edges[s].add(e);
            edges[e].add(s);
        }
        boolean[] isVisited=new boolean[n];
        i=0;
        while(i<n) {
            if(isVisited[i]) {
                i++;
                continue;
            }
            int sum=0;
            Queue<Integer> q = new LinkedList<>();
            q.add(i);
            isVisited[i]=true;
            sum+=w[i];
            while(!q.isEmpty()) {
                int v = q.poll();
                for (int j = 0; j < edges[v].size(); j++) {
                    int vc = edges[v].get(j);
                    if(!isVisited[vc]) {
                        sum+=w[vc];
                        isVisited[vc]=true;
                        q.add(vc);
                    }
                }
            }
            if(sum!=0) {
                System.out.println("IMPOSSIBLE");
                return;
            }
        }

        System.out.println("POSSIBLE");
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
