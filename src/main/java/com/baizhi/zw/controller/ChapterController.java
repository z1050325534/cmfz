package com.baizhi.zw.controller;

import com.baizhi.zw.entity.Chapter;
import com.baizhi.zw.service.ChapterService;
import com.baizhi.zw.util.HttpUtil;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    ChapterService chapterService;

    @RequestMapping("queryByPage")
    //后台分页展示所有音频信息
    public Map queryByPage(Integer page, Integer rows, String album) {
        return chapterService.queryByPage(page, rows, album);
    }

    @RequestMapping("save")
    //增删改操作
    public Map save(String oper, Chapter chapter, String[] id, String album) throws IOException {
        HashMap hashMap = new HashMap();
        if ("add".equals(oper)) {
            String chapterId = UUID.randomUUID().toString();
            chapter.setId(chapterId);
            hashMap.put("urlId", chapterId);
            chapterService.insertOne(chapter, album);
        } else if ("edit".equals(oper)) {
            hashMap.put("urlId", chapter.getId());
            String editId = chapterService.updateOne(chapter);
            hashMap.put("editId", editId);
        } else {
            chapterService.delete(id, album);
        }
        return hashMap;
    }

    @RequestMapping("uploadChapter")
    //上传音频
    public Map uploadChapter(MultipartFile url, String urlId, HttpServletRequest request) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        HashMap hashMap = new HashMap();
        Chapter chapter = new Chapter();
        String uri = HttpUtil.getHttp(url, request, "/upload/chapter/");
        String realPath = request.getSession().getServletContext().getRealPath("/upload/chapter/");
        //更新删除原音频
        if (chapterService.queryOne(urlId).getFilename() != null) {
            File file = new File(realPath, chapterService.queryOne(urlId).getFilename());
            if (file != null) {
                if (file.exists()) {
                    file.delete();
                }
            }
        }
        //计算文件大小
        File file = new File(realPath, uri.split("/")[6]);
        AudioFile audioFile = AudioFileIO.read(file);
        AudioHeader audioHeader = audioFile.getAudioHeader();
        int length = audioHeader.getTrackLength();
        chapter.setTime(length / 60 + "分" + length % 60 + "秒"); //转换为分钟:秒
        //file.length可以获取文件字节大小,保留两位小数
        double size = file.length() / 1024.0 / 1024;
        size = (double) Math.round(size * 100) / 100;
        //更新数据库信息
        //网络路径
        chapter.setId(urlId);
        chapter.setSize(size);
        chapter.setUrl(uri);
        chapter.setFilename((String) request.getSession().getAttribute("originalFilename"));
        chapterService.updateOne(chapter);
        hashMap.put("status", 200);
        return hashMap;
    }


}
