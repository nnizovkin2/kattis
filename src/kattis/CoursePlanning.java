package kattis;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

//https://open.kattis.com/problems/courseplanning
public class CoursePlanning {
    public static void main(String[] args) throws IOException {
        OnlineStreamParser parser = new OnlineStreamParser(System.in, 20);
        int n = parser.getInt(),k= parser.getInt();
        HashMap<String, Integer> lc = new HashMap<>();
        Pair[] p = new Pair[n];
        int l = 0;
        String rep;
        int score;
        int lastChar;
        while(n>0) {
            n--;
            rep=parser.getString();
            lastChar=rep.charAt(rep.length()-1);
            score=parser.getInt();
            if (lastChar != '1' && lastChar != '2') {
                p[l++] = new Pair(score, 0);
                continue;
            }
            rep=rep.substring(0, rep.length()-1);
            Integer s = lc.get(rep);
            if (s == null) {
                lc.put(rep, score);
                continue;
            }

            if (lastChar == '1' && score > s) {
                p[l++] = new Pair(score, s);
                continue;
            }

            if (lastChar == '2' && s > score) {
                p[l++] = new Pair(s, score);
                continue;
            }

            p[l++] = new Pair(s, 0);
            p[l++] = new Pair(score, 0);
        }

        p = Arrays.copyOf(p, l);
        Arrays.sort(p);
        long res = 0;
        int c = 0;
        int max = 0;
        int i;
        for (i = 0; ; i++) {
            c += p[i].s == 0 ? 1 : 2;
            if (c > k) {
                break;
            }
            max = Math.max(max, p[i].s == 0 ? p[i].f : p[i].s);
            res += p[i].f + p[i].s;
            if (c == k) {
                System.out.println(res);
                return;
            }
        }
        int sc = p[i].f + p[i].s;
        int min = Integer.MAX_VALUE;
        while (i < l) {
            min = Math.min(min, p[i].f);
            if (p[i].s == 0) {
                break;
            }
            i++;
        }

        if (min + max < sc) {
            System.out.println(res + min);
        } else {
            System.out.println(res - max + sc);
        }
    }

    private static class OnlineStreamParser {
        InputStream s;
        char[] b;

        public OnlineStreamParser(InputStream s, int bs) {
            this.s = new BufferedInputStream(s);
            b = new char[bs];
        }

        int getInt() {
            char ch = 0;
            try {
                int i = 0;
                while ('0' <= (ch = (char) s.read()) && ch <= '9') {
                    i *= 10;
                    i += ch-'0';
                }
                return i;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        String getString() {
            char ch;
            try {
                int i = 0;
                while ((ch = (char) s.read())!=' '&&ch != '\n') {
                    b[i++]=ch;
                }
                return new String(Arrays.copyOf(b, i));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static boolean genNotEqual() {
        char[] a1 = genStr();
        char[] a2 = genStr();

        if(!Arrays.equals(a1,a2)&&genRep(a1).equals(genRep(a2))) {
            System.out.println(new String(a1) + " " + new String(a2));
            return true;
        }

        return false;
    }

    static StrRep genRep(char[] a) {
        StrRep rep = new StrRep();
        for (int i = 0; i < a.length; i++) {
            char c = a[i];
            if(i<10) {
                rep.f*=26;
                rep.f+=1+c-'a';
            } else {
                rep.s*=26;
                rep.s+=1+c-'a';
            }

        }

        return rep;
    }

    static char[] genStr() {
        int s = 1 + (int) (20 * Math.random());
        char[] a = new char[s];

        for (int i = 0; i < a.length; i++) {
            a[i] = (char) ('a' + Math.random()*26);
        }

        return a;
    }

    private static class StrRep {
        long f;
        long s;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            StrRep strRep = (StrRep) o;

            if (f != strRep.f) return false;
            return s == strRep.s;
        }

        @Override
        public int hashCode() {
            int result = (int) (f ^ (f >>> 32));
            result = 31 * result + (int) (s ^ (s >>> 32));
            return result;
        }
    }

    private static class Pair implements Comparable<Pair> {
        int f;
        int s;

        public Pair(int f, int s) {
            this.f = f;
            this.s = s;
        }

        @Override
        public int compareTo(Pair o) {
            int dav = s == 0 ? 2 * f : f + s;
            int oDav = o.s == 0 ? 2 * o.f : o.f + o.s;
            int c = Integer.compare(dav, oDav);
            return c != 0 ? c : -Integer.compare(f, o.f);
        }
    }
}