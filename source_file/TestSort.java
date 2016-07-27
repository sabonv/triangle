package com.company;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by v.usov on 27.07.2016.
 */
public class TestSort {
    public static void main(String[]ars) throws IOException
    {
//        int B[][] = {{9,7,2},{9,7,2},{2,6,3},{9,6,1},{9,5,3},{1,9,9},{9,6,7}};

        FileInputStream in = new FileInputStream("c:\\Users\\sabo\\IdeaProjects\\triangle\\source_file\\test_file_1.blob");

        List<ArrayList<Integer>> A = new ArrayList<>();

        try {
            int tempread;
            ArrayList<Integer> temp = new ArrayList<>();

            in.skip(1);
            while ((tempread = in.read()) != -1) {
                if(tempread==3&&temp.size()==3){
                        A.add(new ArrayList<Integer>(temp));
                        temp.clear();
                }
                else {

                    temp.add(tempread);

                }
            }
        }
        finally {
            if(in != null) in.close();
        }

//        SortB(B);
        SortA(A);

        try {
//подсчет занятой памяти и вывод
            long heapSize = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            System.out.println("Heap Size(KB) = " + heapSize/1024);
        }
        catch(Exception e) {
            System.out.printf(e.getMessage());
        }

    }

/*    public static void SortB(int[][] X) {

        for (int n = 0; n < X[0].length; n++) {


            for (int i = X.length - 1; i > 0; i--) {
                for (int j = 0; j < i; j++) {
                   if(n==0 || n==2&&X[j][n-1]==X[j+1][n-1]&&X[j][n-2]==X[j+1][n-2]) {

                       if (X[j][n] < X[j + 1][n]) {
                           int[] temp = X[j + 1];
                           X[j + 1] = X[j];
                           X[j] = temp;
                       }

                   }
                    if(n==1&&X[j][n-1]==X[j+1][n-1]) {

                        if (X[j][n] > X[j + 1][n]) {
                            int[] temp = X[j + 1];
                            X[j + 1] = X[j];
                            X[j] = temp;
                        }

                    }

                }
            }

        }

        for(int[] Line : X) {
            for(int out : Line) System.out.print(out);
            System.out.println("");
        }

    }*/

    public static void SortA(List<ArrayList<Integer>> X) throws IOException {

        for (int n = 0; n < 3; n++) {


            for (int i = X.size() - 1; i > 0; i--) {
                for (int j = 0; j < i; j++) {

                    ArrayList<Integer> a = X.get(j);
                    ArrayList<Integer> b = X.get(j+1);

                    if(n==0 || n==2&&a.get(n-1)==b.get(n-1)&&a.get(n-2)==b.get(n-2)) {

                        if (a.get(n) < b.get(n)) {
                            ArrayList<Integer> temp = X.get(j + 1);
                            X.set(j+1, X.get(j));
                            X.set(j, temp);
                        }

                    }
                    if(n==1&&a.get(n-1)==b.get(n-1)) {

                        if (a.get(n) > b.get(n)) {
                            ArrayList<Integer> temp = X.get(j + 1);
                            X.set(j+1, X.get(j));
                            X.set(j, temp);
                        }

                    }

                }
            }

        }

        PrintWriter out = new PrintWriter(new FileWriter("c:\\Users\\sabo\\IdeaProjects\\triangle\\source_file\\List.txt"));
        try {

            for(ArrayList<Integer> Line : X) {
                for(int outi : Line) out.print(outi+" ");;
                out.println("");
            }
        }
        finally {
            if(out != null) out.close();
        }
/*        for(ArrayList<Integer> Line : X) {
            for(int out : Line) System.out.print(out +" " );
            System.out.println("");
        }*/

    }



}

