package kattis.first;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

//https://open.kattis.com/problems/cookieselection
public class CookieSelection {
    public static void main(String[] args) {
        OnlineStreamParser p = new OnlineStreamParser(System.in, 9);
        MedianDS m = new MedianDS();
        m.add(p.getInt());
        while(true) {
            String t = p.getToken();
            if(t==null) {
                return;
            }

            if(t.charAt(0)=='#') {
                System.out.println(m.poll());
            } else {
                m.add(Integer.parseInt(t));
            }
        }
    }

    private static class MedianDS {
        PriorityQueue<Integer> f = new PriorityQueue<>(Comparator.reverseOrder());
        PriorityQueue<Integer> s = new PriorityQueue<>();
        int size=0;

        void add(int i) {
            size++;
            if(f.isEmpty()&&s.isEmpty()) {
                f.add(i);
                return;
            }

            if(f.size()==s.size()) {
                if(f.peek()>=i) {
                    f.add(i);
                } else {
                    s.add(i);
                }
                return;
            }

            if(f.size()<s.size()) {
                if(f.size()==0||i<=s.peek()) {
                    f.add(i);
                } else {
                    f.add(s.poll());
                    s.add(i);
                }
                return;
            }

            if (s.size() == 0 || i >= f.peek()) {
                s.add(i);
            } else {
                s.add(f.poll());
                f.add(i);
            }
        }

        int poll() {
            if(size==0) {
                throw new RuntimeException();
            }

            size--;

            if(f.size()==s.size()) {
                return s.poll();
            }

            if(f.size()<s.size()) {
                return s.poll();
            }

            return f.poll();
        }

        int size() {
            return size;
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
            int ch;
            try {
                if((ch=s.read())==-1) {
                    return null;
                }
                int i = 0;
                while (ch != ' ' && ch != '\n') {
                    b[i++] = (char)ch;
                    ch = s.read();
                }

                return new String(Arrays.copyOf(b, i));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
