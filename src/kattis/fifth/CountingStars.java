package kattis.fifth;

import java.io.*;
import java.util.*;

//https://open.kattis.com/problems/countingstars
public class CountingStars {
    public static void main(String[] args) throws IOException {
        outputStarNumbers();
    }

    static void outputStarNumbers() {
        OnlineStreamParser parser = new OnlineStreamParser(System.in, 100);

        int n=1;
        while(!parser.isEOF) {
            outputAmebaNumber(parser, (boolean[][] v, DisjointUnionSets sets, int i, int j)->{
                int sn=v[0].length;
                if(j+1<sn&&v[i][j +1]) {
                    sets.union(i * sn + j, i * sn + j +1);
                }
                if(i+1<v.length&&v[i+1][j]) {
                    sets.union(i * sn + j, (i+1)* sn + j);
                }
            }, n++);
        }
    }

    static void outputAmebaNumber(OnlineStreamParser parser, Method m, int n) {
        try {
            int ln = parser.getInt(),sn = parser.getInt();
            boolean[][] v = new boolean[ln][];
            for (int i = 0; i < v.length; i++) {
                String s=parser.getToken();
                v[i]=new boolean[sn];
                char[] str=s.toCharArray();
                for (int j = 0; j < str.length; j++) {
                    if(str[j]=='-'){
                        v[i][j]=true;
                    }
                }
            }
            DisjointUnionSets sets = new DisjointUnionSets(ln*sn);
            for (int i = 0; i < v.length; i++) {
                boolean[] row=v[i];
                for (int j = 0; j < row.length; j++) {
                    if(!v[i][j]) {
                        continue;
                    }

                    m.invoke(v, sets, i, j);
                }
            }

            Set<Integer> s = new HashSet<>();
            for (int i = 0; i < ln*sn; i++) {
                if(v[i/sn][i%sn]) {
                    s.add(sets.find(i));
                }
            }
            //Case 1: 4
            System.out.format("Case %d: %d\n",n, s.size());
        } catch (Exception e) {
        }
    }

    private interface Method{
        void invoke(boolean[][] v, DisjointUnionSets sets, int i, int j);
    }

    //take from here https://www.geeksforgeeks.org/disjoint-set-data-structures/
    static class DisjointUnionSets {
        int[] rank, parent;
        int n;

        // Constructor
        public DisjointUnionSets(int n)
        {
            rank = new int[n];
            parent = new int[n];
            this.n = n;
            makeSet();
        }

        // Creates n sets with single item in each
        void makeSet()
        {
            for (int i = 0; i < n; i++) {
                // Initially, all elements are in
                // their own set.
                parent[i] = i;
            }
        }

        // Returns representative of x's set
        int find(int x)
        {
            // Finds the representative of the set
            // that x is an element of
            if (parent[x] != x) {
                // if x is not the parent of itself
                // Then x is not the representative of
                // his set,
                parent[x] = find(parent[x]);

                // so we recursively call Find on its parent
                // and move i's node directly under the
                // representative of this set
            }

            return parent[x];
        }

        // Unites the set that includes x and the set
        // that includes x
        void union(int x, int y)
        {
            // Find representatives of two sets
            int xRoot = find(x), yRoot = find(y);

            // Elements are in the same set, no need
            // to unite anything.
            if (xRoot == yRoot)
                return;

            // If x's rank is less than y's rank
            if (rank[xRoot] < rank[yRoot])

                // Then move x under y  so that depth
                // of tree remains less
                parent[xRoot] = yRoot;

                // Else if y's rank is less than x's rank
            else if (rank[yRoot] < rank[xRoot])

                // Then move y under x so that depth of
                // tree remains less
                parent[yRoot] = xRoot;

            else // if ranks are the same
            {
                // Then move y under x (doesn't matter
                // which one goes where)
                parent[yRoot] = xRoot;

                // And increment the result tree's
                // rank by 1
                rank[xRoot] = rank[xRoot] + 1;
            }
        }
    }

    private static class OnlineStreamParser {
        private InputStream s;
        private char[] b;
        private boolean isEOF=false;

        OutputStream st=new BufferedOutputStream(System.out);

        public OnlineStreamParser(InputStream s) {
            this(s,50);
        }

        public OnlineStreamParser(InputStream s, int bs) {
            this.s = new BufferedInputStream(s);
            b = new char[bs];
        }

        boolean isEOF() {
            return isEOF;
        }

        public int getInt() throws EOFException {
            if(isEOF) {
                throw new EOFException();
            }
            int ch;
            try {
                ch = s.read();
                boolean isPositive = true;
                if(ch==-1) {
                    isEOF=true;
                    throw new EOFException();
                }
                if (ch == '-') {
                    isPositive = false;
                    ch = (char) s.read();
                }
                int i = 0;
                while ('0' <= ch && ch <= '9') {
                    i *= 10;
                    i += ch - '0';
                    ch = s.read();
                }
                if(ch==-1) {
                    isEOF=true;
                }
                return isPositive ? i : -i;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public String getToken() throws EOFException {
            if(isEOF) {
                throw new EOFException();
            }
            int ch;
            try {
                int i = 0;
                while ((ch=s.read())!=' '&&ch!='\n'&&ch!=-1) {
                    b[i++] = (char) ch;
                }

                if(ch==-1) {
                    isEOF=true;
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
}
