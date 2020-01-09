package com.baizhi.zw.service;

import com.baizhi.zw.annotation.LogAnnotation;
import com.baizhi.zw.dao.GuruDao;
import com.baizhi.zw.entity.Banner;
import com.baizhi.zw.entity.Guru;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GuruServiceImpl implements GuruService {
    @Autowired
    GuruDao guruDao;
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @LogAnnotation(value = "查询所有上师信息")
    public List<Guru> queryAll() {
        return  guruDao.selectAll();
    }

    @Override
    @LogAnnotation(value = "添加上师信息")
    public void insertOne(Guru guru) {
        guruDao.insert(guru);
    }

    @Override
    @LogAnnotation(value = "删除上师信息")
    public void delete(String[] ids) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String realPath = requestAttributes.getRequest().getSession().getServletContext().getRealPath("/upload/guru/");
        for (int i = 0; i < ids.length; i++) {
            String id = ids[i];
            String filename = guruDao.selectByPrimaryKey(id).getFilename();
            File file = new File(realPath, filename);
            if (file != null) {
                if (file.exists()) {
                    file.delete();
                }
            }
        }
        guruDao.deleteByIdList(Arrays.asList(ids));
    }

    @Override
    public Guru queryOne(String id) {
        return guruDao.selectByPrimaryKey(id);
    }

    @Override
    @LogAnnotation(value = "修改上师信息")
    //修改上师信息
    public String updateOne(Guru guru) {
        Guru guru1 = guruDao.selectByPrimaryKey(guru);
        if (guru1.getPhoto() != "" && guru.getPhoto() != "") {
            //修改了图片
            guruDao.updateByPrimaryKeySelective(guru);
            return "需要修改http";
        } else {
            //不需要修改图片
            guru.setPhoto(guru1.getPhoto());
            guruDao.updateByPrimaryKeySelective(guru);
            return null;
        }
    }
}
