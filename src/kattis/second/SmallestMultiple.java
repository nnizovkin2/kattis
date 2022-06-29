package kattis.second;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

//https://open.kattis.com/problems/smallestmultiple
public class SmallestMultiple {
    public static void main(String[] args) throws IOException {
        SmallestMultiple problemH = new SmallestMultiple();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Kattio kattio = new Kattio(System.in);
        String s;
        List<Integer> l = primesTill((int)Math.sqrt(Integer.MAX_VALUE)+1);
        int[] ta = new int[32];
        while((s=reader.readLine())!=null) {
            String[] str = s.split(" ");
            System.out.println(problemH.calc(Arrays.stream(str).mapToInt(Integer::parseInt).toArray(), l, ta));
        }
    }
    BigInteger calc(int[] a, List<Integer> l, int[] ta) {
        BigInteger res = BigInteger.ONE;
        Map<Integer, Integer> map = new HashMap<>();
        for (int v : a) {
            if(v==1) continue;
            l.add(v);
            pow(v, l, map, ta);
            l.remove(l.size()-1);
        }

        for (Map.Entry<Integer, Integer> e : map.entrySet()) {
            res=res.multiply(BigInteger.valueOf((long)Math.pow(e.getKey(), e.getValue())));
        }
        return res;
    }
    void pow(int n, List<Integer> primes, Map<Integer, Integer> res, int[] num) {
        int pos = 0;
        int i=0;
        while(i< primes.size()&&n!=0) {
            int p = primes.get(i);
            if(n%p==0) {
                num[pos++]=p;
                n/=p;
                continue;
            }

            i++;
        }

        if(n>1) {
            num[pos++]=n;
        }

        int c = 0;
        int prev = -1;
        for (int j = 0; j < pos; j++) {
            if(num[j]==prev) c++;
            else {
                int val = res.getOrDefault(prev,0);
                if(val<c) {
                    res.put(prev, c);
                }
                c=1;
                prev=num[j];
            }
        }
        int val = res.getOrDefault(prev,0);
        if(val<c) {
            res.put(prev, c);
        }
    }

    public static ArrayList<Integer> primesTill(int n) {
        ArrayList<Integer> res = new ArrayList<>();
        int i = 2;
        while (i <= n) {
//            if(i % 10000000 == 0) {
//                System.out.println(System.currentTimeMillis());
//            }
            boolean isPrime = true;
            int lim = (int)Math.sqrt(i) + 1;
            for (Integer p : res) {
                if (i % p == 0) {
                    isPrime = false;
                    break;
                }
                if(p > lim) {
                    break;
                }
            }
            if (isPrime) {
                res.add(i);
            }
            i++;
        }


        return res;
    }

    private static class Kattio extends PrintWriter {
        private BufferedReader r;
        private String line;
        private StringTokenizer st;
        private String token;

        public Kattio(InputStream i) {
            super(new BufferedOutputStream(System.out));
            r = new BufferedReader(new InputStreamReader(i));
        }

        public Kattio(InputStream i, OutputStream o) {
            super(new BufferedOutputStream(o));
            r = new BufferedReader(new InputStreamReader(i));
        }

        public boolean hasMoreTokens() {
            return peekToken() != null;
        }

        public int getInt() {
            return Integer.parseInt(nextToken());
        }

        public double getDouble() {
            return Double.parseDouble(nextToken());
        }

        public long getLong() {
            return Long.parseLong(nextToken());
        }

        public String getWord() {
            return nextToken();
        }

        private String peekToken() {
            if (token == null)
                try {
                    while (st == null || !st.hasMoreTokens()) {
                        line = r.readLine();
                        if (line == null) return null;
                        st = new StringTokenizer(line);
                    }
                    token = st.nextToken();
                } catch (IOException e) {
                }
            return token;
        }

        private String nextToken() {
            String ans = peekToken();
            token = null;
            return ans;
        }
    }
}
