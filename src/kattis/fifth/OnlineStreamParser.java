package kattis.fifth;

import java.io.*;
import java.util.Arrays;

public class OnlineStreamParser {
    private InputStream s;
    private char[] b;

    OutputStream st=new BufferedOutputStream(System.out);

    public OnlineStreamParser(InputStream s) {
        this(s,50);
    }

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

    public void println(Object o) throws IOException {
        st.write((o.toString()+'\n').getBytes());
    }

    public void flush(Object o) throws IOException {
        st.flush();
    }

    public void close() throws IOException {
        st.close();
    }
}
