package com.baizhi.zw.controller;

import com.baizhi.zw.entity.Album;
import com.baizhi.zw.entity.Chapter;
import com.baizhi.zw.service.AlbumService;
import com.baizhi.zw.service.ChapterService;
import com.baizhi.zw.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    AlbumService albumService;
    @Autowired
    ChapterService chapterService;
    //后台查询所有专辑
    @RequestMapping("/queryByPage")
    public Map queryByPage(Integer page, Integer rows) {
        return albumService.queryByPage(page, rows);
    }
    //增删改（专辑）
    @RequestMapping("/save")
    public Map save(String oper, Album album, String id) {
        HashMap hashMap = new HashMap();
        if ("add".equals(oper)) {
            String albumId = UUID.randomUUID().toString();
            System.out.println(album);
            hashMap.put("urlId", albumId);
            album.setId(albumId);
            albumService.insertOne(album);
        } else if ("edit".equals(oper)) {
            hashMap.put("urlId", album.getId());
            String editId = albumService.updateOne(album);
            hashMap.put("editId",editId);
        } else {
            albumService.delete(id);
        }
        return hashMap;
    }
    //上传图片
    @RequestMapping("uploadAlbum")
    public Map uploadBanner(MultipartFile url, String urlId, HttpServletRequest request){
        HashMap hashMap = new HashMap();
        String realPath = request.getSession().getServletContext().getRealPath("/upload/album/");
        //更新删除原图片
        if(albumService.queryOne(urlId).getFilename()!=null){
                File file = new File(realPath, albumService.queryOne(urlId).getFilename());
                if (file != null) {
                    if (file.exists()) {
                        boolean delete = file.delete();
                        System.out.println(delete);
                    }
                }
        }
        String http = HttpUtil.getHttp(url, request, "/upload/album/");
        //更新数据库信息
        Album album = new Album();
        //网络路径
        album.setId(urlId);
        album.setUrl(http);
        album.setFilename((String) request.getSession().getAttribute("originalFilename"));
        albumService.updateOne(album);
        hashMap.put("status",200);
        return hashMap;
    }
    //前台展示专辑详情
    @RequestMapping("queryOneAlbum")
    public Map queryOneAlbum(String uid,String id){
        HashMap hashMap = new HashMap();
        Album albums = albumService.queryOne(id);
        List<Chapter> chapters = chapterService.queryByAlbumId(id);
        albums.setChapters(chapters);
        hashMap.put("albums",albums);
        return hashMap;
    }
}
