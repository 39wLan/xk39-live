package xk.FileUtils;

import ws.schild.jave.MultimediaObject;
import ws.schild.jave.info.MultimediaInfo;

import java.io.File;

import static xk.FileUtils.DirList.getList;

public class ReadFile {
    public static void main(String[] args) {
        String folder="D:\\BaiduYunDownload\\iHRM";
        String partten=".*(avi|wmv|mp4)$";
        long sumTime=0;
        for (File f:getList(folder,partten)){
            long temp=getVideoInfo(f);
            long h=temp/(60*60*1000);
            long m=(temp%(60*60*1000))/(60*1000);
            long s=(temp%(60*1000))/1000;
            sumTime+=temp;
            System.out.format("%-60s%02dh%02dm%02ds%n",f.toString(),h,m,s);
        }
        long h=sumTime/(60*60*1000);
        long m=(sumTime%(60*60*1000))/(60*1000);
        long s=(sumTime%(60*1000))/1000;
        
        System.out.format("%dh%2dm%2ds",h,m,s);
    }
    public static long getVideoInfo(File file){
        long time=0;
        try{
            MultimediaObject multimediaObject = new MultimediaObject(file);
            MultimediaInfo info = multimediaObject.getInfo();
            time= info.getDuration();
        }catch (Exception e){
            System.out.println(e);
        }
        return time;
    }
}
