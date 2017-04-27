package Main;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {


    try {

//        Properties p = System.getProperties();
//        System.out.println("Separator: " +
//                p.getProperty("file.separator"));

        long starttime = System.currentTimeMillis();

        ArrayList<Reader> arrread = new ArrayList<Reader>();

        List<ArrayList<Integer>> alltr = new ArrayList<>();

//берём директорию с файлами из параметров запуска

        String sourcedir = argstest(args[0]);

//сканим директорию на наличие нужных файлов

        ArrayList<String> filesn = new ArrayList<>(ScanDir(sourcedir));

// "читаем файлы" создаём массив с объектами паралельными потоками

        for (int i = 0; i < filesn.size(); i++) {
            arrread.add(new Reader("file"+i, sourcedir + filesn.get(i)));
        }

// джойним потоки к основному потоку

        for (int i = 0; i < arrread.size(); i++) {
            arrread.get(i).getP().join();
        }
        System.out.println("Time(ms) of work (Thread end) = " + (System.currentTimeMillis() - starttime));
//сливаем результаты в один массив

        for (int i = 0; i < arrread.size(); i++) {
            List<ArrayList<Integer>> tempsors = arrread.get(i).getResult();
            alltr.addAll(tempsors.subList(0,tempsors.size()));
        }
        System.out.println("Time(ms) of work (All in one array) = " + (System.currentTimeMillis() - starttime));
//сортируем общий массив

        List<ArrayList<Integer>> sorttr = new ArrayList<>(SortA(alltr));

///сохраняем результаты сортировки в файл

        outresult(sorttr, sourcedir);

        OutHeapSize();

        System.out.println("Time(ms) of work (Main) = " + (System.currentTimeMillis() - starttime));


    }

    catch (Exception e){
        System.out.println("Error main: " + e);
        System.out.println(e.getMessage());

    }

    }

    public static String argstest(String args) throws Exception{

        String result = args;

        if(result.substring(0,1).equals("\"")) result = result.substring(1,result.length());
        if(result.substring(result.length()-1, result.length()).equals("\"")) result = result.substring(0,result.length()-1);
        if(!result.endsWith("\\")) result = result + "\\";

        File dir = new File(result);
        if (dir.exists()) return result;
        else throw new Exception("Directory not found: " + result);

    }

    public static ArrayList<String> ScanDir(String sourcedir)throws IOException{

        //ArrayList<FileInputStream> result = new ArrayList<>();
        //ArrayList<String> result = new ArrayList<>();
        File dir = new File(sourcedir);

        String[] names = dir.list(new FilenameFilter() {

            public boolean accept(File dir, String name) {
                String f = new File(name).getName();
                return f.endsWith(".blob");
                }
        });
        ArrayList<String> result = new ArrayList<>(Arrays.asList(names));

//            for (String file : names) {
//                result.add(new FileInputStream(sourcedir + file));
//            }

        return result;
    }

    public static List<ArrayList<Integer>> SortA(List<ArrayList<Integer>> X) throws IOException {
        long starttime = System.currentTimeMillis();

        for (int n = 0; n < 3; n++) {


            for (int i = X.size() - 1; i > 0; i--) {
                for (int j = 0; j < i; j++) {


                    ArrayList<Integer> a = X.get(j);
                    ArrayList<Integer> b = X.get(j+1);

                    if(n==0 || n==2&&a.get(n-1)==b.get(n-1)&&a.get(n-2)==b.get(n-2)||n==1&&a.get(n-1)==b.get(n-1)) {

                        if (a.get(n) > b.get(n)) {
                            ArrayList<Integer> temp = X.get(j + 1);
                            X.set(j+1, X.get(j));
                            X.set(j, temp);
                        }

                    }


                }
            }
            System.out.println("Time(ms) sort step " + (n+1) + " = " + (System.currentTimeMillis() - starttime));
            starttime = System.currentTimeMillis();
        }

        return X;

    }

    public static void outresult(List<ArrayList<Integer>> X, String dis)throws IOException{

        PrintWriter out = new PrintWriter(new FileWriter(dis +  "Out_result.txt"));
        try {

            for(ArrayList<Integer> Line : X) {

                out.print("Triangle: " + Line.get(0) + ", " + Line.get(1) + ", " + Line.get(2));
                out.println("");

            }
        }
        catch (Exception e){
            System.out.println("outresult error "+ e.getMessage());
        }
        finally {
            if(out != null) out.close();
        }
    }

    public static void OutHeapSize(){

        long heapSize = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.println("Heap Size(KB) = " + heapSize/1024);

    }
}
