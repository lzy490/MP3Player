package com.example.utils;

import android.net.Uri;

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
}
