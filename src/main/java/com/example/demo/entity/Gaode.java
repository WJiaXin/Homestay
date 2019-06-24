package com.example.demo.entity;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Gaode {                          //高德计算两点距离web API
    private static String key = "c963250c4ab40d20631dc0f1a8a72094";

    public Gaode() {
    }

    public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            return null;
        } finally { // 使用finally块来关闭输入流
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

    /**
     * 距离测量API服务地址
     *
     * @param origins     出发点
     * @param destination 目的地
     * @return
     */
    public static String getDistanceUrl(String origins, String destination) {
        String urlString = "http://restapi.amap.com/v3/distance?type=0&origins=" + origins + "&destination=" + destination + "&key=" + key;
        return urlString;
    }

    /**
     * 获取二个地点之间的直线距离
     * @param origins 出发地
     * @param destination 目的地
     * @return
     */
    public  String getDistance(String origins, String destination) {
        String jsonString = sendGet(getDistanceUrl(origins,destination));
System.out.println("distance:"+jsonString);
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        String distance = jsonObject.getJSONArray("results").getJSONObject(0).get("distance").toString();
        return distance;
    }
}
