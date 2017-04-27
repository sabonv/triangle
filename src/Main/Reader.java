package Main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by v.usov on 26.04.2017.
 */
public class Reader implements Runnable  {

    private Thread p;
    private List<ArrayList<Integer>> result = new ArrayList<>();
    private FileInputStream in;


    Reader (String name, String filesors){
        super();
        long starttime = System.currentTimeMillis();
        try {
            this.in = new FileInputStream(filesors);
        }catch (Exception e){
            System.out.println("Error find file in thread" + e);
        }
        p = new Thread(Thread.currentThread().getThreadGroup(), this, name);
        p.start();
//        System.out.println("Thread " + p.getName() + " started");
        System.out.println("Time(ms) of work (" + p.getName() + ") = " + (System.currentTimeMillis() - starttime));
    }

    public Thread getP() {
        return p;
    }

    public List<ArrayList<Integer>> getResult() {
        return result;
    }

    public void run()  {

        try {
            int tempread;
            ArrayList<Integer> temp = new ArrayList<>();

            int sum = 0;
            //int count = 0, err = 0;

            while ((tempread = this.in.read()) != -1) {

                if(tempread==3){

                    for (int i = 0; i < 3; i++) {
                        if((tempread = this.in.read()) != -1) temp.add(tempread);

                    }
                    for(Integer x: temp) sum = sum+x;
                    if (sum==180 && temp.size()==3) {
                        this.result.add(new ArrayList<Integer>(temp));

                        sum = 0;
                        temp.clear();
                        // count++;
                    }
                    else {
                        sum = 0;
                        temp.clear();
                    }
                }

            }

        }
        catch (Exception e){
            System.out.println("ReadFiles error " + e);
        }
        finally {

            try {
                if (this.in != null) this.in.close();
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }


    }
}
