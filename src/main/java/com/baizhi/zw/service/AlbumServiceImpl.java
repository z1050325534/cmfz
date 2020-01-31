package com.baizhi.zw.service;

import com.baizhi.zw.dao.AlbumDao;
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

import java.io.File;
import java.util.*;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    AlbumDao albumDao;
    @Autowired
    ChapterService chapterService;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @LogAnnotation(value = "查询专辑信息")
    //后台展示所有专辑信息
    public Map queryByPage(Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        //分页处理
        int records = albumDao.selectCount(null);
        hashMap.put("page", page);
        hashMap.put("total", records % rows == 0 ? records / rows : records / rows + 1);
        hashMap.put("records", records);
        hashMap.put("rows", albumDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows)));
        return hashMap;
    }

    @Override
    @LogAnnotation(value = "修改专辑信息")
    //后台修改专辑信息
    public String updateOne(Album album) {
        Album album1 = albumDao.selectByPrimaryKey(album);
        //判断是否需要修改图片
        if (album1.getUrl() != "" && album.getUrl() != "") {
            //需要修改图片
            albumDao.updateByPrimaryKeySelective(album);
            return "需要修改图片";
        } else {
            //不需要修改图片
            album.setUrl(album1.getUrl());
            albumDao.updateByPrimaryKeySelective(album);
            return null;
        }
    }

    @Override
    @LogAnnotation(value = "删除专辑信息")
    //根据id删除专辑信息
    public void delete(String id) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //删除一级类别，清空所有子级
        List<Chapter> chapters = chapterService.queryByAlbumId(id);
        for (Chapter c : chapters) {
            String realPath1 = requestAttributes.getRequest().getSession().getServletContext().getRealPath("/upload/chapter/");
            //查出所有音频路径
            String filename = c.getFilename();
            File file = new File(realPath1, filename);
            if (file != null) {
                if (file.exists()) {
                    boolean delete = file.delete();
                    System.out.println(delete);
                    //删除文件成功
                }
            }
        }
        chapterService.deleteAll(chapters);
        //删除专辑文件
        String realPath = requestAttributes.getRequest().getSession().getServletContext().getRealPath("/upload/album/");
        String filename = albumDao.selectByPrimaryKey(id).getFilename();
        File file = new File(realPath, filename);
        if (file != null) {
            if (file.exists()) {
                file.delete();
            }
        }
        albumDao.deleteByIdList(Arrays.asList(id));
    }

    @Override
    @LogAnnotation(value = "添加专辑信息")
    //添加专辑信息
    public void insertOne(Album album) {
        //存入音频数量
        album.setCounts(0);
        //存入创建时间
        album.setCreateDate(new Date());
        albumDao.insert(album);
    }

    @Override
    @LogAnnotation(value = "查询一个专辑信息")
    public Album queryOne(String id) {
        return albumDao.selectByPrimaryKey(id);
    }

    @Override
    //前台展示5条数据
    public List<Album> queryByRowBounds() {
        return albumDao.selectByRowBounds(null, new RowBounds(0, 5));
    }
}
