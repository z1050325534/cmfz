package com.baizhi.zw.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class HttpUtil {
    public static String getHttp(MultipartFile file,HttpServletRequest request,String path){
        String realPath = request.getSession().getServletContext().getRealPath(path);
        // 判断该文件夹是否存在
        File file1 = new File(realPath);
        if (!file1.exists()) {
            // mkdirs() 多级创建
            file1.mkdirs();
        }
        // 防止重名
        String originalFilename = new Date().getTime()+"_"+file.getOriginalFilename();
        request.getSession().setAttribute("originalFilename",originalFilename);
        try {
            file.transferTo(new File(realPath,originalFilename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 获取网络路径
        // 获取协议头
        String scheme = request.getScheme();
        // 获取IP地址
        String localHost = null;
        try {
            localHost = InetAddress.getLocalHost().toString().split("/")[1];
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        // 获取端口号
        int serverPort = request.getServerPort();
        // 获取项目名
        String contextPath = request.getContextPath();
        String uri = scheme +"://"+ localHost + ":" + serverPort + contextPath + path + originalFilename;
        return uri;
    }
}
