package kattis.fifth;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//https://open.kattis.com/problems/wheresmyinternet
public class  WhereMyInternet {
    public static void main(String[] args) throws IOException {
        OnlineStreamParser parser = new OnlineStreamParser(System.in, 10);
        int v=parser.getInt(), en=parser.getInt();;

        int[][]e=new int[en][];
        for (int i = 0; i < en; i++) {
            e[i]=new int[]{parser.getInt(), parser.getInt()};
        }

        List<Integer> res=new WhereMyInternet().notConnected(v, e);
        if(res.size()==0) {
            System.out.println("Connected");
        } else {
            for (Integer vertex: res) {
                parser.println(vertex);
            }
        }
        parser.close();
    }

    public List<Integer> notConnected(int v, int[][] edges) {
        OnlineStreamParser parser = new OnlineStreamParser(System.in, 10);
        DisjointUnionSets sets = new DisjointUnionSets(v);
        for(int[] edge: edges) {
            sets.union(edge[0]-1, edge[1]-1);
        }

        int cr = sets.find(0);

        ArrayList<Integer> l = new ArrayList<>();
        for(int i=0; i<v; i++) {
            if(sets.find(i)!=cr) {
                l.add(i+1);
            }
        }

        return l;
    }
 }
