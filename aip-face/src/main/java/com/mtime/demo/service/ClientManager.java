package com.mtime.demo.service;

import com.baidu.aip.face.AipFace;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ClientManager {

    @Value("${baidu.app.id}")
    private  String APP_ID;

    @Value("${baidu.api.key}")
    private  String API_KEY;

    @Value("${baidu.secret.key}")
    private  String SECRET_KEY;



    public static AipFace client=null;

    public  AipFace getAipFaceClient() {
        if (client != null) {
            return client;
        }

        AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        //client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
        // client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理
        return client;
    }
}
