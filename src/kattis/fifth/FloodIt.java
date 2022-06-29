package kattis.fifth;

import java.io.*;
import java.util.*;

//https://open.kattis.com/problems/floodit
public class FloodIt {
    public static void main(String[] args) throws IOException {
//        char[][] a={{'1','2','3'},
//                    {'2','3','1'},
//                    {'3','3','3'}};
//        processOneGame(3, a);
        outputFloodIt();
    }

    static void outputFloodIt() throws EOFException {
        OnlineStreamParser parser = new OnlineStreamParser(System.in, 20);
        int tc=parser.getInt();
        while(--tc>=0) {
            processOneGame(parser);
        }
    }

    //    private static void processOneGame(OnlineStreamParser parser) throws EOFException {
    private static void processOneGame(OnlineStreamParser parser) throws EOFException {
        int n=parser.getInt();
        DisjointUnionSets sets=new DisjointUnionSets(n*n);
        char[][] ct=new char[n][];
        for (int i=0;i<n;i++) {
            ct[i]=parser.getToken().toCharArray();
        }


        for (int i=0;i<n;i++) {
            for (int j=0;j<n;j++) {
                sets.setColor(i*n+j, ct[i][j]-'1');
                if(j+1<n&&ct[i][j+1]==ct[i][j]) {
                    sets.union(i*n+j, i*n+j+1,ct[i][j]-'1');
                }
                if(i+1<n&&ct[i][j]==ct[i+1][j]) {
                    sets.union(i*n+j, (i+1)*n+j,ct[i][j]-'1');
                }
            }
        }

        int[] colors = new int[6];
        int changeN = 0;
        while(true) {
            int root = sets.find(0);
            int[] cr = {-1,-1,-1,-1,-1,-1};

            for (int i = 0; i < n; i++) {
                for (int j=0;j<n;j++) {
                    int p = sets.find(i*n+j);
                    int c=sets.color[p];
                    boolean isNear=i-1>=0&&sets.find((i-1)*n+j)==root;
                    isNear|=i+1<n&&sets.find((i+1)*n+j)==root;
                    isNear|=j-1>=0&&sets.find(i*n+j-1)==root;
                    isNear|=j+1<n&&sets.find(i*n+j+1)==root;
                    if(p==root||!isNear){
                        continue;
                    }
                    if(cr[c]==-1){
                        cr[c]=p;
                    } else {
                        cr[c]=sets.union(p,cr[c],c);
                    }
                }
            }

            int size=-1;
            int v=-1;
            int c=-1;
            for (int i = 0; i < cr.length; i++) {
                if(cr[i]!=-1&&size<sets.size[cr[i]]) {
                    v=cr[i];
                    c=sets.color[v];
                    size=sets.size[cr[i]];
                }
            }
            if (v==-1) {
                System.out.println(changeN);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < colors.length; i++) {
                    sb.append(colors[i]);
                    if (i < colors.length - 1) {
                        sb.append(' ');
                    }
                }
                System.out.println(sb);
                break;
            }
            colors[c]++;
            sets.union(root,v,c);
            changeN++;
        }
    }

    //take from here https://www.geeksforgeeks.org/disjoint-set-data-structures/
    static class DisjointUnionSets {
        int[] rank, parent;
        int n;
        int[] size, color;

        // Constructor
        public DisjointUnionSets(int n)
        {
            rank = new int[n];
            parent = new int[n];
            size=new int[n];
            color=new int[n];
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
                size[i]=1;
            }
        }

        void setColor(int i, int c) {
            color[i]=c;
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

        int maxSizeI() {
            int i=0;
            for (int j = 1; j < size.length; j++) {
                if(size[i]<size[j]) {
                    i=j;
                }

            }
            return i;
        }

        int union(int x, int y, int c) {
            int xRoot = find(x), yRoot = find(y);
            if(xRoot==yRoot) {
                return xRoot;
            }
            int newSize=size[xRoot]+size[yRoot];
            size[xRoot]=newSize;
            size[yRoot]=newSize;
            color[xRoot]=c;
            color[yRoot]=c;
            return union(xRoot, yRoot);
        }

        // Unites the set that includes x and the set
        // that includes x
        int union(int x, int y)
        {
            // Find representatives of two sets
            int xRoot = find(x), yRoot = find(y);

            // Elements are in the same set, no need
            // to unite anything.
            if (xRoot == yRoot)
                return xRoot;

            // If x's rank is less than y's rank
            if (rank[xRoot] < rank[yRoot]) {

                // Then move x under y  so that depth
                // of tree remains less
                parent[xRoot] = yRoot;
                return yRoot;
            }

            // Else if y's rank is less than x's rank
            else if (rank[yRoot] < rank[xRoot]) {

                // Then move y under x so that depth of
                // tree remains less
                parent[yRoot] = xRoot;
                return xRoot;
            }

            else // if ranks are the same
            {
                // Then move y under x (doesn't matter
                // which one goes where)
                parent[yRoot] = xRoot;

                // And increment the result tree's
                // rank by 1
                rank[xRoot] = rank[xRoot] + 1;
                return xRoot;
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
