package com.example.androidfinalwork.Service;

import com.example.androidfinalwork.entity.Account;
import com.example.androidfinalwork.entity.SendKey;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class UrlService  {



    public UrlService() {

    }

    private HttpURLConnection initPostRequest(String url,String postData) throws IOException {

        //设置字符集为UTF-8
        URL urlObject = new URL(url);
        HttpURLConnection connection = null;
        connection=(HttpURLConnection) urlObject.openConnection();
        // 设置请求方法为POST
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        // 设置请求体的内容类型为x-www-form-urlencoded,编码为UTF-8
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        // 写入请求体数据
        //将postdata转为中文可以识别的格式
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.write(postData.getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();

//        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
//        outputStream.writeBytes(postData);
//        outputStream.flush();
//        outputStream.close();
        return connection;
    }

    public static UrlService getOKhttpService(){
        return new UrlService();
    }

   public boolean PostLogin(Account account) {
        //不使用任何框架封装post请求
       String url = "http://170.106.178.146:8080/login";
       String postData = "username=" + account.username + "&password=" + account.password;

       try {

           HttpURLConnection connection = initPostRequest(url, postData);
           // 获取响应状态码
           int responseCode = connection.getResponseCode();

           if (responseCode == HttpURLConnection.HTTP_OK) {
               // 读取响应数据
               BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
               String line;
               StringBuilder response = new StringBuilder();

               while ((line = reader.readLine()) != null) {
                   response.append(line);
               }
               reader.close();
               // 处理响应数据
                //提取response中的key{key:"",message:""}
                String responseStr = response.toString();
                String[] responseArr = responseStr.split(",");
                String[] keyArr = responseArr[0].split(":");
                SendKey.key= keyArr[1].substring(1, keyArr[1].length()-1);
               connection.disconnect();
               return true;
           } else {
               // 处理请求失败的情况
               System.out.println("POST请求失败，响应码：" + responseCode);
           }

           // 断开连接
           connection.disconnect();
       } catch (IOException e) {
           e.printStackTrace();
       }



        return false;
   }



    public String PostResponse(String key, String question) {
        String url = "http://170.106.178.146:8080/chat";
        String postData = "key=" + key + "&question=" + question;
        //将postData序列化成json数据

        try {
            HttpURLConnection connection = initPostRequest(url, postData);
            // 获取响应状态码
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 读取响应数据
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                // 处理响应数据
                //提取response中的key{message:"",status:"" }
                String responseStr = response.toString();
                String[] responseArr = responseStr.split(",");
                String[] MessageArr = responseArr[0].split(":");
                String[] StatusArr = responseArr[1].split(":");
                String message = MessageArr[1].substring(1, MessageArr[1].length()-1);
                String status = StatusArr[1].substring(1, StatusArr[1].length()-2);
                connection.disconnect();
                if(status.equals("false"))
                    return "error";
                else
                    return message;


            } else {
                connection.disconnect();
                return "error";
            }

            // 断开连接

        } catch (ProtocolException ex) {
            throw new RuntimeException(ex);
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}