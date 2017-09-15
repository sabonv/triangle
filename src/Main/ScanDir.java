package Main;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by v.usov on 06.09.2017.
 */
public class ScanDir {

    private String sourcedir;
    private ArrayList<String> filesn;

// конструетор требует на входе принемает пармаметры передаваемые в программу(директория)

    public ScanDir(String inparam) throws Exception {

        testSource(inparam);

    }

// проверяет сущетвует ли адрес и при необходимости дописывает его для дальнейшего использования
    private void testSource (String inparam)throws Exception{

        String result = inparam;

        // проверить способ дополнения адреса
        if(result.substring(0,1).equals("\"")) result = result.substring(1,result.length());
        if(result.substring(result.length()-1, result.length()).equals("\"")) result = result.substring(0,result.length()-1);
        if(!result.endsWith("\\")) result = result + "\\";

        File dir = new File(result);
        if (dir.exists()) this.sourcedir = result;
        else throw new Exception("Directory not found: " + result);
    }

// метод сканирует директорию на наличие необходимых файлов и формирует список имён

    private void scanSourcedir(){

        File dir = new File(this.sourcedir);

        String[] names = dir.list(new FilenameFilter() {

            public boolean accept(File dir, String name) {
                String f = new File(name).getName();
                return f.endsWith(".blob");
            }
        });
        this.filesn = new ArrayList<>(Arrays.asList(names));

    }

// формирует и возвращает список файлов

    public ArrayList<String> getFilesn() {
        scanSourcedir();
        return filesn;
    }

// возвращает дополненый адрес директории

    public String getSourcedir() {
        return sourcedir;
    }

//
    public void setSourcedir(String inparam) throws Exception {

        testSource(inparam);

    }
}
