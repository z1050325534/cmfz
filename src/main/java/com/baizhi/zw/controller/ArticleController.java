package com.baizhi.zw.controller;

import com.baizhi.zw.entity.Article;
import com.baizhi.zw.service.ArticleService;
import com.baizhi.zw.service.GuruService;
import com.baizhi.zw.util.HttpUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @Autowired
    GuruService guruService;

    @RequestMapping("/queryByPage")
    //分页展示所有文章信息
    public Map queryByPage(Integer page, Integer rows) {
        return articleService.queryByPage(page, rows);
    }

    @RequestMapping("add")
    //添加/修改文章
    public String add(Article article,MultipartFile cover,HttpServletRequest request) {
        HashMap hashMap = new HashMap();
        //判断id是否为空  为空添加   不为空根据id修改
        if(article.getId()==null||"".equals(article.getId())){
            String url = HttpUtil.getHttp(cover, request, "/upload/article/");
            String articleId = UUID.randomUUID().toString();
            hashMap.put("urlId", articleId);
            article.setId(articleId);
            article.setImage(url);
            article.setFilename(((String) request.getSession().getAttribute("originalFilename")));
            article.setCreateDate(new Date());
            article.setPublishDate(new Date());
            articleService.insertOne(article);
        }else{
            //修改
            if(!"".equals(cover.getOriginalFilename())){
                //修改图片  删除原文件
                String url = HttpUtil.getHttp(cover, request, "/upload/article/");
                String realPath = request.getSession().getServletContext().getRealPath("/upload/article/");
                String filename = articleService.queryOne(article.getId()).getFilename();
                File file = new File(realPath, filename);
                if (file != null) {
                    if (file.exists()) {
                        file.delete();
                    }
                }
                //判断是否修改了内容
                if(article.getContent().equals("")){
                    article.setContent(articleService.queryOne(article.getId()).getContent());
                }
                article.setImage(url);
                article.setFilename(((String) request.getSession().getAttribute("originalFilename")));
                article.setCreateDate(new Date());
                article.setPublishDate(new Date());
                articleService.updateOne(article);
            }else{
                //不修改图片  只修改数据
                //判断是否修改了内容
                if(article.getContent().equals("")){
                    article.setContent(articleService.queryOne(article.getId()).getContent());
                }
                article.setCreateDate(new Date());
                article.setPublishDate(new Date());
                article.setImage(article.getImage());
                articleService.updateOne(article);
            }
        }
        return "ok";
    }
    @RequestMapping("delete")
    //根据id删除文章信息
    public Map delete(String id) {
        HashMap hashMap = new HashMap();
        articleService.delete(id);
        return hashMap;
    }
    @RequestMapping("uploadArticleImg")//上传文章图片
    public Map uploadArticleImg(MultipartFile image, HttpServletRequest request){
        HashMap hashMap = new HashMap();
        try {
            String url = HttpUtil.getHttp(image, request, "/upload/articleImg/");
            hashMap.put("error", 0);
            hashMap.put("url",url);
        }catch (Exception e){
            hashMap.put("error", 1);
            e.printStackTrace();
            hashMap.put("message", "上传失败");
        }
        return hashMap;
    }
    //查询图片空间的图片
    @RequestMapping("/queryArticleImg")
    public Map queryArticleImg(HttpServletRequest request, HttpSession session) {
        HashMap hashMap = new HashMap();
        hashMap.put("current_url",request.getContextPath()+"/upload/articleImg/");
        String realPath = session.getServletContext().getRealPath("/upload/articleImg/");
        File file = new File(realPath);
        File[] files = file.listFiles();
        hashMap.put("total_count",files.length);
        ArrayList arrayList = new ArrayList();
        for (File file1 : files) {
            HashMap fileMap = new HashMap();
            fileMap.put("is_dir",false);
            fileMap.put("has_file",false);
            fileMap.put("filesize",file1.length());
            fileMap.put("is_photo",true);
            String name = file1.getName();
            String extension = FilenameUtils.getExtension(name);
            fileMap.put("filetype",extension);
            fileMap.put("filename",name);
            //字符串拆分
            String time = name.split("_")[0];
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format = simpleDateFormat.format(new Date(Long.valueOf(time)));
            fileMap.put("datetime",format);
            arrayList.add(fileMap);
        }
        hashMap.put("file_list",arrayList);
        return hashMap;
    }
    //前台文章详情展示
    @RequestMapping("queryOneArticle")
    public Map queryOneArticle(String uid,String id){
        HashMap hashMap = new HashMap();
        Article article = articleService.queryOne(id);
        hashMap.put("status","200");
        hashMap.put("article",article);
        return hashMap;
    }
}
