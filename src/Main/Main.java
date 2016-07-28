package Main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
    try {
        long starttime = System.currentTimeMillis();

        String sourcedir = argstest(args[0]);

        ArrayList<FileInputStream> files = new ArrayList<>(ScanDir(sourcedir));

        List<ArrayList<Integer>> alltr = new ArrayList<>(ReadFiles(files));

        List<ArrayList<Integer>> sorttr = new ArrayList<>(SortA(alltr));

        outresult(sorttr, sourcedir);

        OutHeapSize();

        System.out.println("Time of work(ms) " + (System.currentTimeMillis() - starttime));
    }

    catch (Exception e){
        System.out.println("Error main ");
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

    public static ArrayList<FileInputStream> ScanDir(String sourcedir)throws IOException{

            ArrayList<FileInputStream> result = new ArrayList<>();
            File dir = new File(sourcedir);

            String[] names = dir.list(new FilenameFilter() {

                public boolean accept(File dir, String name) {
                    String f = new File(name).getName();
                    return f.endsWith(".blob");

                }
            });

            for (String file : names) {
                result.add(new FileInputStream(sourcedir + file));
            }

        return result;
    }

    public static List<ArrayList<Integer>> ReadFiles(ArrayList<FileInputStream> inarr) throws IOException{

        List<ArrayList<Integer>> result = new ArrayList<>();

        for (FileInputStream in : inarr){

            try {
                int tempread;
                ArrayList<Integer> temp = new ArrayList<>();

                in.skip(1);
                while ((tempread = in.read()) != -1) {
                    if(tempread==3&&temp.size()==3){
                        result.add(new ArrayList<Integer>(temp));
                        temp.clear();
                    }
                    else {

                        temp.add(tempread);

                    }
                }
            }
            catch (Exception e){
                System.out.println("ReadFiles error " + e.getMessage());
            }
            finally {
                if(in != null) in.close();
            }

        }
        return result;
    }

    public static List<ArrayList<Integer>> SortA(List<ArrayList<Integer>> X) throws IOException {

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
