package com.example.utils;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.example.Entity.Song;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Created by luzhiyuan on 16/6/15.
 */
public class DownloadUtils {
    private static String sdcardPath;

    static {
        File sdcard = Environment.getExternalStorageDirectory();
        sdcardPath = sdcard.getPath() + "/mp3player";
    }
    public static String download(String urlStr) {
        String result = "";
        String read = "";
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while((read=reader.readLine()) != null) {
                result = result + read;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean checkExistFile(String fileName) {
        String filePath = sdcardPath + "/" + fileName;
        File file = new File(filePath);
        return file.exists();
    }

    //1表示下载成功,0表示下载失败
    public static int downloadSongAndLrc2SDcard(String urlStr, String fileName) {
        int flag = 1;
        try {
            makeRootDirectory(sdcardPath);
            String filePath = sdcardPath + "/" + fileName;
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream in = conn.getInputStream();
            FileOutputStream out = new FileOutputStream(new File(filePath));
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = in.read(bytes)) != -1) {
                out.write(bytes, 0, len);
            }
            out.flush();
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            flag = 0;
        }
        return flag;
    }

    public static boolean createFile() {
        File file = new File(sdcardPath);
        boolean flag = false;
        try {
            flag = file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static void makeRootDirectory(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public static File[] getFileFromDir() {
        File file = new File(sdcardPath);
        File[] files = null;
        if (file.exists()) {
            files = file.listFiles();
        }
        return files;
    }
}
