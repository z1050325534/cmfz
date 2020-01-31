package com.baizhi.zw.service;

import com.baizhi.zw.dao.ChapterDao;
import com.baizhi.zw.annotation.LogAnnotation;
import com.baizhi.zw.entity.Album;
import com.baizhi.zw.entity.Chapter;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.util.*;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ChapterDao chapterDao;
    @Autowired
    AlbumService albumService;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @LogAnnotation(value = "查询专辑的音频信息")
    //分页展示所有音频信息
    public Map queryByPage(Integer page, Integer rows, String albumId) {
        HashMap hashMap = new HashMap();
        Chapter chapter = new Chapter();
        chapter.setAlbumId(albumId);
        Example e = new Example(Chapter.class);
        e.createCriteria().andEqualTo("albumId", albumId);
        int records = chapterDao.selectCount(chapter);//条件查条数
        int offset = (page - 1) * rows;
        hashMap.put("page", page);
        hashMap.put("total", records % rows == 0 ? records / rows : records / rows + 1);
        hashMap.put("records", records);
        hashMap.put("rows", chapterDao.selectByExampleAndRowBounds(e, new RowBounds(offset, rows)));
        return hashMap;
    }

    @Override
    @LogAnnotation(value = "修改音频信息")
    //修改音频信息
    public String updateOne(Chapter chapter) {
        Chapter chapter1 = chapterDao.selectByPrimaryKey(chapter);
        //判断是否需要修改音频
        if (chapter1.getUrl() != "" && chapter.getUrl() != "") {
            chapterDao.updateByPrimaryKeySelective(chapter);
            return "yes";
        } else {
            chapter.setUrl(chapter1.getUrl());
            chapterDao.updateByPrimaryKeySelective(chapter);
            return null;
        }
    }

    @Override
    @LogAnnotation(value = "删除音频信息")
    //根据id批量删除
    public void delete(String[] id, String albumId) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String realPath = requestAttributes.getRequest().getSession().getServletContext().getRealPath("/upload/chapter/");
        Album album = albumService.queryOne(albumId);
        albumService.updateOne(album);
        for (int i = 0; i < id.length; i++) {
            String s = id[i];
            String filename = chapterDao.selectByPrimaryKey(s).getFilename();
            File file = new File(realPath, filename);
            if (file != null) {
                if (file.exists()) {
                    boolean delete = file.delete();
                    System.out.println(delete);
                }
            }
        }
        for (int i = 0; i < id.length; i++) {
            //修改父级count属性
            album.setCounts(album.getCounts() - 1);
        }
        chapterDao.deleteByIdList(Arrays.asList(id));
    }

    @Override
    @LogAnnotation(value = "添加音频信息")
    //添加音频信息
    public void insertOne(Chapter chapter, String albumId) {
        chapter.setAlbumId(albumId);
        chapter.setCreateTime(new Date());
        Album album = albumService.queryOne(albumId);
        //给父级专辑count属性+1操作
        album.setCounts(album.getCounts() + 1);
        albumService.updateOne(album);
        chapterDao.insertSelective(chapter);
    }

    @Override
    public List<Chapter> queryByAlbumId(String albumId) {
        //根据父级id查询章节的信息
        Example e = new Example(Chapter.class);
        e.createCriteria().andEqualTo("albumId", albumId);
        return chapterDao.selectByExample(e);
    }

    @Override
    //批量删除音频信息
    public void deleteAll(List<Chapter> chapters) {
        for (Chapter c : chapters) {
            chapterDao.delete(c);
        }
    }

    @Override
    //根据id查一个
    public Chapter queryOne(String id) {
        return chapterDao.selectByPrimaryKey(id);
    }
}
