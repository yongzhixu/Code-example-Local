package com.convertlab.common.beta.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.*;

/**
 * Http 链接客户端工具
 *
 * @author LIUJUN
 * @date 2021-03-20 17:07:00
 */
public class HttpClientUtil {

    /**
     * post 请求
     *
     * @param strUrl  接受地址
     * @param content 参数内容: a=1&b=2
     * @return 返回体
     */
    public static String doPost(String strUrl, String content) {
        return doPost(null, 0, strUrl, content);
    }

    /**
     * post 请求
     *
     * @param proxyHost 代理地址
     * @param proxyPort 代理端口
     * @param strUrl    接受地址
     * @param content   参数内容: a=1&b=2
     * @return 返回体
     */
    public static String doPost(String proxyHost, int proxyPort, String strUrl, String content) {
        String result = "";

        try {
            URL url = new URL(strUrl);
            HttpURLConnection urlConnection = null;
            if (!StringUtils.isEmpty(proxyHost) && proxyPort > 0) {
                SocketAddress sa = new InetSocketAddress(proxyHost, proxyPort);
                Proxy proxy = new Proxy(Proxy.Type.HTTP, sa);
                //通过调用url.openConnection()来获得一个新的URLConnection对象，并且将其结果强制转换为HttpURLConnection.
                urlConnection = (HttpURLConnection) url.openConnection(proxy);

            } else {
                urlConnection = (HttpURLConnection) url.openConnection();
            }
            urlConnection.setRequestMethod("POST");
            //设置连接的超时值为30000毫秒，超时将抛出SocketTimeoutException异常
            urlConnection.setConnectTimeout(30000);
            //设置读取的超时值为30000毫秒，超时将抛出SocketTimeoutException异常
            urlConnection.setReadTimeout(30000);
            //将url连接用于输出，这样才能使用getOutputStream()。getOutputStream()返回的输出流用于传输数据
            urlConnection.setDoOutput(true);
            //设置通用请求属性为默认浏览器编码类型
            urlConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            //getOutputStream()返回的输出流，用于写入参数数据。
            OutputStream outputStream = urlConnection.getOutputStream();
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();
            //此时将调用接口方法。getInputStream()返回的输入流可以读取返回的数据。
            InputStream inputStream = urlConnection.getInputStream();
            byte[] data = new byte[1024];
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            //inputStream每次就会将读取1024个byte到data中，当inputSteam中没有数据时，inputStream.read(data)值为-1
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            result = sb.toString();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * Http Get 请求 请求
     *
     * @param strUrl  接受地址
     * @param content 参数内容: a=1&b=2
     * @return 返回体
     */
    public static String httpGet(String strUrl, String content) {
        return httpGet(null, 0, strUrl);
    }

    /**
     * post 请求
     *
     * @param proxyHost 代理地址
     * @param proxyPort 代理端口
     * @param strUrl    接受地址
     * @return 返回体
     */
    public static String httpGet(String proxyHost, int proxyPort, String strUrl) {
        String feedback = null;
        if (StringUtils.isBlank(strUrl)) {
            System.out.println("http 请求连接的ip为空,放弃连接");
            return null;
        }
        // 创建CloseableHttpClient对象 * 官方推荐的创建HttpClient实例的方式
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(strUrl);

        // 设置超时时间、请求时间、socket时间都为5秒，允许重定向
        RequestConfig.Builder builder = RequestConfig.custom().setConnectTimeout(5000);
        if (!StringUtils.isEmpty(proxyHost) && proxyPort > 0) {
            //设置代理IP，设置连接超时时间 、 设置 请求读取数据的超时时间 、 设置从connect Manager获取Connection超时时间、
            HttpHost proxy = new HttpHost(proxyHost, proxyPort);
            builder.setProxy(proxy);
        }
        RequestConfig requestConfig = builder
                .setConnectionRequestTimeout(30000)
                .setSocketTimeout(30000)
                .setRedirectsEnabled(true)
                .build();
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpGet);
            // 获取返回的Http状态
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            System.out.println("statusCode：" + statusCode);
            // 200(表示连接成功)
            if (statusCode == 200) {
                //获取服务器返回的文本消息
                HttpEntity httpEntity = response.getEntity();
                feedback = EntityUtils.toString(httpEntity, "UTF-8");
                System.out.println("feedback：" + feedback);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭输入流
            try {
                if (response != null) {
                    EntityUtils.consume(response.getEntity());
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            // * 无论成功与否,都要释放连接，终止连接
            if (!httpGet.isAborted()) {
                httpGet.releaseConnection();
                httpGet.abort();
            }
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return feedback;
    }

}
