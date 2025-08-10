package com.sky.test;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HttpClientTest {

    /**
     * 测试通过httpclient发送Get方式请求
     */
    @Test
    public void testGet() throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //创建请求对象
        HttpGet httpGet = new HttpGet("http://localhost:8080/user/shop/status");

        //发送请求
        CloseableHttpResponse response = httpClient.execute(httpGet);

        //获取响应结果(相应状态码)
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("服务器返回的状态码为" + statusCode);

        if(statusCode == 200){
            //获取响应结果
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity,"UTF-8");
            System.out.println("服务器返回的结果为" + result);
        }

        //关闭资源
        response.close();
        httpClient.close();
    }

    /**
     * 测试通过httpclient发送Post方式请求
     */
    @Test
    public void testPost() throws Exception {
        //创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建请求对象
        HttpPost httpPost = new HttpPost("http://localhost:8080/admin/employee/login");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username","admin");
        jsonObject.put("password","123456");

        StringEntity entity = new StringEntity(jsonObject.toString());
        entity.setContentEncoding("utf-8");
        //数据格式（固定）
        entity.setContentType("application/json");
        httpPost.setEntity(entity);

        //发送请求
        CloseableHttpResponse response = httpClient.execute(httpPost);

        //获取响应结果(响应状态码)
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("服务器返回的状态码为" + statusCode);

        if(statusCode == 200){
            //获取响应结果
            HttpEntity entityResponse = response.getEntity();
            String result = EntityUtils.toString(entityResponse,"UTF-8");
            System.out.println("服务器返回的结果为" + result);
        }

        response.close();
        httpClient.close();
    }
}
