package kattis.fifth;

import java.io.*;
import java.util.*;

//https://open.kattis.com/problems/reachableroads
public class ReachableRoads {
    public static void main(String[] args) throws IOException {
        outputReachableRoads();
    }

    private static void outputReachableRoads() {
        OnlineStreamParser parser = new OnlineStreamParser(System.in, 10);
        ReachableRoads graphProblems = new ReachableRoads();
        int c=parser.getInt();

        for (int i = 0; i < c; i++) {
            int v=parser.getInt();
            int en= parser.getInt();
            int[][]e=new int[en][];
            for (int j = 0; j < en; j++) {
                e[j]=new int[]{parser.getInt(), parser.getInt()};
            }

            System.out.println(graphProblems.roadNum(v,e));
        }
    }

    public int roadNum(int v, int[][] edges) {
        DisjointUnionSets sets=connect(v,edges, 0);

        Set<Integer> s = new HashSet<>();
        for (int i = 0; i < v; i++) {
            s.add(sets.find(i));
        }

        return s.size()-1;
    }

    private DisjointUnionSets connect(int v, int[][] edges, int d) {
        DisjointUnionSets sets = new DisjointUnionSets(v);
        for(int[] edge: edges) {
            sets.union(edge[0]-d, edge[1]-d);
        }
        return sets;
    }
}
