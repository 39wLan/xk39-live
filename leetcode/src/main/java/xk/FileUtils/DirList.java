package xk.FileUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class DirList {
    static class FilterByMatches implements FileFilter {
        private String partten;
        
        public FilterByMatches(String partten){
            this.partten=partten;
        }
        @Override
        public boolean accept(File name) {
            return  name.isDirectory()||name.toString().matches(partten);
        }
    }
    
    public static List<File> getList(String _folder){
        return getList(new File(_folder));
    }
    public static List<File> getList(File _folder){
        File folder=_folder;
        ArrayList<File> files = new ArrayList<>();
        if(!folder.exists()){
            return files;
        }
        if(folder.isFile()){
            files.add(folder);
            return files;
        }
        if (folder.isDirectory()){
            try{
                for (File f:folder.listFiles()){
                    if(f.isDirectory()){
                        files.addAll(getList(f));
                    }else if(f.isFile()){
                        files.add(f);
                    }
                }
            }catch (Exception e){
                System.out.println(e);
            }
        }
        return files;
    }
    

    public static List<File> getList(String _folder,String partten){
        return getList(new File(_folder),new FilterByMatches(partten));
    }
    public static List<File> getList(String _folder,FilterByMatches filterByMatches){
        return getList(new File(_folder),filterByMatches);
    }
    public static List<File> getList(File _folder,String partten){
        return getList(_folder,new FilterByMatches(partten));
    }
    public static List<File> getList(File _folder,FilterByMatches filterByMatches){
        File folder=_folder;
        System.out.println("try-get****"+folder);
        ArrayList<File> files = new ArrayList<>();
        if(!folder.exists()){
            return files;
        }
        if(folder.isFile()){
            files.add(folder);
            return files;
        }
        if (folder.isDirectory()){
            try{
                for (File f:folder.listFiles(filterByMatches)){
                    if(f.isDirectory()){
                        files.addAll(getList(f,filterByMatches));
                    }else if(f.isFile()){
                        files.add(f);
                    }
                }
            }catch (Exception e){
                System.out.println(e);
            }
        }
        return files;
    }
    

}
