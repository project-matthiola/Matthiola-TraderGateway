package com.cts.trader.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class HttpUtil {
    public static String sendGet (String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = (param != null) ? url + "?" + param : url;
            URL readUrl = new URL(urlNameString);
            URLConnection connection = readUrl.openConnection();
            connection.setConnectTimeout(2000);
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36");
            connection.setRequestProperty("authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJDVFMiLCJzdWIiOiJ0cmFkZXIyIiwiZXhwIjoxNTI2NDc3OTEyLCJpYXQiOjE1MjY0NzYxMTI5MDl9.Gb9XZj8byBTPyq1Vfk6m_fJjoIdK9Ng9f9n1pKEj-g4p4GC2EEIDJ_Cc2MxM3lRmSqweAf9p8BsSfAiy9Re4RA");
            connection.connect();

            Map<String, List<String>> map = connection.getHeaderFields();
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("get error(most probably manually timeout)" + e);
            return result;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static String sendPost (String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            out = new PrintWriter(connection.getOutputStream());
            out.print(param);
            out.flush();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        //String s = HttpUtil.sendGet("http://202.120.40.87:22471/Entity/Ubfbd4152866263/iCampus/Member/", null);
        //System.out.println(s);
        String s = HttpUtil.sendGet("http://localhost:8888/future/getAllFutures", null);
        System.out.println(s);
    }
}
