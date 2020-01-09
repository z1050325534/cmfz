package com.baizhi.zw.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
@RestController
public class PhoneUtil {
    //生成验证码
    public static String getCode(int n) {
        char[] code = "0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(code[new Random().nextInt(code.length)]);
        }
        return sb.toString();
    }

    //发送验证码
    public static CommonResponse senCode(String phone, String code) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FhnZN1nTA3zKZ4pYzuG", "DgtGmhtIRCWpfYCMtV1g1qNfRQVUBb");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "剑客无情");
        request.putQueryParameter("TemplateCode", "SMS_181858203");
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            return response;
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }
    //验证
    public static Map getPhone(String phone, String random) {
        HashMap hashMap = new HashMap();
        String message = null;
       try {
           CommonResponse commonResponse = senCode(phone, random);
           if(commonResponse.getData().split("Code\":\"")[1].equals("OK\"}")){
               message = "发送成功";
               hashMap.put("message", message);
               hashMap.put("random", random);
               hashMap.put("status", "200");
           }else {
               message = "发送失败";
               hashMap.put("message", message);
               hashMap.put("status", "-200");
           }
       }catch (Exception e){
           e.printStackTrace();
           message = "发送失败";
           hashMap.put("message", message);
           hashMap.put("status", "-200");
       }
        return hashMap;
    }
}
