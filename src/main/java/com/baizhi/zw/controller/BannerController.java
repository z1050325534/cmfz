package com.baizhi.zw.controller;

import com.baizhi.zw.entity.Banner;
import com.baizhi.zw.service.BannerService;
import com.baizhi.zw.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("banner")
public class BannerController {
    @Autowired
    BannerService bannerService;

    @RequestMapping("/queryByPage")
    //分页展示所有轮播图信息
    public Map queryByPage(Integer page, Integer rows) {
        return bannerService.queryByPage(page, rows);
    }

    @RequestMapping("save")
    //增删改操作
    public Map save(String oper, Banner banner, String[] id) {
        HashMap hashMap = new HashMap();
        if ("add".equals(oper)) {
            String bannerId = UUID.randomUUID().toString();
            hashMap.put("urlId", bannerId);
            banner.setId(bannerId);
            bannerService.insertOne(banner);
        } else if ("edit".equals(oper)) {
            hashMap.put("urlId", banner.getId());
            //返回是否需要修改图片
            String editId = bannerService.updateOne(banner);
            hashMap.put("editId",editId);
        } else {
            //假删
            //bannerService.deleteOne(banner);//修改轮播图状态
            //真删
            bannerService.delete(id);
        }
        return hashMap;
    }

    @RequestMapping("uploadBanner")
    //上传轮播图
    public Map uploadBanner(MultipartFile url, String urlId, HttpServletRequest request){
        HashMap hashMap = new HashMap();
        String http = HttpUtil.getHttp(url, request, "/upload/banner/");
        //更新删除原图片
        String realPath = request.getSession().getServletContext().getRealPath("/upload/banner/");
        if(bannerService.queryOne(urlId).getFilename()!=null){
            File file = new File(realPath, bannerService.queryOne(urlId).getFilename());
            if (file != null) {
                if (file.exists()) {
                    file.delete();
                }
            }
        }
        //更新数据库信息
        Banner banner = new Banner();
        banner.setId(urlId);
        banner.setFilename((String) request.getSession().getAttribute("originalFilename"));
        //相对路径
        //banner.setUrl("upload/img/"+name);
        //网络路径
        banner.setUrl(http);
        bannerService.updateOne(banner);
        hashMap.put("status",200);
        return hashMap;
    }
}
