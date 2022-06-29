package kattis;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class OnlineStreamParser {
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
