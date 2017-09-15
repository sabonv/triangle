package Main;

/**
 * Created by v.usov on 06.09.2017.
 */
public class MainTest {


    public static void main(String[] args) throws Exception {

        try {


// тест сканирования директории

            //ScanDir testScan = new ScanDir("C:\\Users\\v.usov\\IdeaProjects\\triangle\\source_file1\\");
            ScanDir testScan = new ScanDir("/Users/v.usov/IdeaProjects/triangle/source_file");
            System.out.println(testScan.getFilesn());





        }
        catch (Exception e) {
            System.out.println("Error main: " + e);
            System.out.println(e.getMessage());
        }
    }


}
