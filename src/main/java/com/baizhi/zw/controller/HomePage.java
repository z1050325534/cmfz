package com.baizhi.zw.controller;

import com.baizhi.zw.entity.Album;
import com.baizhi.zw.entity.Article;
import com.baizhi.zw.entity.Banner;
import com.baizhi.zw.service.AlbumService;
import com.baizhi.zw.service.ArticleService;
import com.baizhi.zw.service.BannerService;
import com.baizhi.zw.service.GuruService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("HomePage")
public class HomePage {
    @Autowired
    BannerService bannerService;
    @Autowired
    AlbumService albumService;
    @Autowired
    ArticleService articleService;
    @Autowired
    GuruService guruService;

    @RequestMapping("firstPage")
    //前台一级页面展示
    public Map firstPage(String uid,String type,String sub_type){
        HashMap hashMap = new HashMap();
        try{
            if(type.equals("all")){
                //轮播图信息
                List<Banner> banners = bannerService.queryByTime();
                //专辑信息
                List<Album> albums = albumService.queryByRowBounds();
                //文章信息
                List<Article> articles = articleService.queryAll();
                hashMap.put("status",200);
                hashMap.put("head",banners);
                hashMap.put("albums",albums);
                hashMap.put("articles",articles);
            }else if(type.equals("wen")){
                List<Album> albums = albumService.queryByRowBounds();
                hashMap.put("status",200);
                hashMap.put("albums",albums);
            }else {
                if (sub_type.equals("ssyj")){
                    List<Article> articles = articleService.queryAll();
                    hashMap.put("status",200);
                    hashMap.put("articles",articles);
                }else {
                    List<Article> articles = articleService.queryAll();
                    hashMap.put("status",200);
                    hashMap.put("articles",articles);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            hashMap.put("status","-200");
            hashMap.put("message","error");
        }
        return hashMap;
    }

}
