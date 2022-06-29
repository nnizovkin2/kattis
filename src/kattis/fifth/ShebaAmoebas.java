package kattis.fifth;

//https://open.kattis.com/problems/amoebas

import java.io.*;
import java.util.*;

public class ShebaAmoebas {
    public static void main(String[] args) throws IOException {
        outputAmebaNumber();
    }

    static void outputAmebaNumber() {
        OnlineStreamParser parser = new OnlineStreamParser(System.in, 100);
        int ln = parser.getInt(), sn = parser.getInt();
        boolean[][] v = new boolean[ln][];
        for (int i = 0; i < v.length; i++) {
            String s = parser.getToken();
            v[i] = new boolean[sn];
            char[] str = s.toCharArray();
            for (int j = 0; j < str.length; j++) {
                if (str[j] == '#') {
                    v[i][j] = true;
                }
            }
        }
        DisjointUnionSets sets = new DisjointUnionSets(ln * sn);
        for (int i = 0; i < v.length; i++) {
            boolean[] row = v[i];
            for (int j = 0; j < row.length; j++) {
                if (!v[i][j]) {
                    continue;
                }

                if (j + 1 < row.length && v[i][j + 1]) {
                    sets.union(i * sn + j, i * sn + j + 1);
                }
                if (i + 1 < v.length && v[i + 1][j]) {
                    sets.union(i * sn + j, (i + 1) * sn + j);
                }
                if (i + 1 < v.length && j + 1 < row.length && v[i + 1][j + 1]) {
                    sets.union(i * sn + j, (i + 1) * sn + j + 1);
                }
                if (i - 1 >= 0 && j + 1 < row.length && v[i - 1][j + 1]) {
                    sets.union(i * sn + j, (i - 1) * sn + j + 1);
                }
            }
        }

        Set<Integer> s = new HashSet<>();
        for (int i = 0; i < ln * sn; i++) {
            if (v[i / sn][i % sn]) {
                s.add(sets.find(i));
            }
        }
        System.out.println(s.size());
    }

}
