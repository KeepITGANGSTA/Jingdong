package cacheUtils;

import java.io.File;
import java.text.DecimalFormat;

/**
 * Created by 李英杰 on 2017/9/17.
 */

public class FileSize {
    /**
     *  获取内部数据库大小
     * @param file
     * @return
     */
    public static long getFileSize(File file){
        if (file==null){
            return 0;
        }
        if (!file.isDirectory()){
            return 0;
        }
        long dirSize=0;
        File[] files = file.listFiles();
        for (File file1 : files) {
            if (file1.isFile()){
                dirSize+=file1.length();
            }else if (file1.isDirectory()){
                dirSize+=getFileSize(file1);
            }
        }
        return dirSize;
    }

    /**
     * 将文件大小转换
     * @param fileSize
     * @return
     */
    public static String getFileM(long fileSize){
        DecimalFormat decimalFormat=new DecimalFormat("#.00");
        String size="";
        if (fileSize<1024){
            size=decimalFormat.format((double) fileSize)+"B";
        }else if (fileSize<1048576){
            size=decimalFormat.format((double) fileSize/1024)+"KB";
        }else if (fileSize<1073741824){
            size=decimalFormat.format((double) fileSize/1048576)+"MB";
        }else {
            size=decimalFormat.format((double) fileSize/ 1073741824)+"G";
        }
        return size;
    }


    public static void clearCache(File file){
        if (file!=null && file.isDirectory()){
            File[] files = file.listFiles();
            for (File file1 : files) {
                if (file1.isFile()){
                    file1.delete();
                }
            }
        }

    }


}
