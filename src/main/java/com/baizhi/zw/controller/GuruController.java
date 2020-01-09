package com.baizhi.zw.controller;

import com.baizhi.zw.entity.Guru;
import com.baizhi.zw.service.GuruService;
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
@RequestMapping("guru")
public class GuruController {
    @Autowired
    GuruService guruService;

    @RequestMapping("selectAllGuru")
    //查询所有上师
    public List<Guru> selectAllGuru() {
        return guruService.queryAll();
    }

    @RequestMapping("save")
    //增删改操作
    public Map save(String oper, Guru guru, String[] id) {
        HashMap hashMap = new HashMap();
        if ("add".equals(oper)) {
            String guruId = UUID.randomUUID().toString();
            hashMap.put("urlId", guruId);
            guru.setId(guruId);
            guruService.insertOne(guru);
        } else if ("edit".equals(oper)) {
            hashMap.put("urlId", guru.getId());
            //返回是否需要修改图片
            String editId = guruService.updateOne(guru);
            hashMap.put("editId",editId);
        } else {
            //假删
            //guruService.deleteOne(guru);//修改轮播图状态
            //真删
            guruService.delete(id);
        }
        return hashMap;
    }
    @RequestMapping("uploadGuru")
    //上传上师photo
    public Map uploadGuru(MultipartFile photo, String urlId, HttpServletRequest request){
        HashMap hashMap = new HashMap();
        String http = HttpUtil.getHttp(photo, request, "/upload/guru/");
        //更新删除原图片
        String realPath = request.getSession().getServletContext().getRealPath("/upload/guru/");
        if(guruService.queryOne(urlId).getFilename()!=null){
            File file = new File(realPath, guruService.queryOne(urlId).getFilename());
            if (file != null) {
                if (file.exists()) {
                    file.delete();
                }
            }
        }
        //更新数据库信息
        Guru guru = new Guru();
        guru.setId(urlId);
        guru.setFilename((String) request.getSession().getAttribute("originalFilename"));
        //相对路径
        //guru.setPhoto("upload/img/"+name);
        //网络路径
        guru.setPhoto(http);
        System.out.println(guru);
        guruService.updateOne(guru);
        hashMap.put("status",200);
        return hashMap;
    }
}
