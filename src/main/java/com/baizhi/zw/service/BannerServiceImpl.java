package com.baizhi.zw.service;

import com.baizhi.zw.annotation.LogAnnotation;
import com.baizhi.zw.dao.BannerDao;
import com.baizhi.zw.entity.Banner;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    BannerDao bannerDao;

    @Override
    // 分页查
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @LogAnnotation(value = "查询轮播图信息")
    //后台展示所有轮播图信息
    public HashMap queryByPage(Integer page, Integer rows) {
        int records = bannerDao.selectCount(null);
        int offset = (page - 1) * rows;
        HashMap hashMap = new HashMap();
        hashMap.put("page", page);
        hashMap.put("total", records % rows == 0 ? records / rows : records / rows + 1);
        hashMap.put("records", records);
        hashMap.put("rows", bannerDao.selectByRowBounds(null, new RowBounds(offset, rows)));
        return hashMap;
    }

    @Override
    //获取轮播图数量
    public int getCount() {
        return bannerDao.selectCount(new Banner());
    }

    @Override
    @LogAnnotation(value = "添加轮播图信息")
    //后台添加轮播图信息
    public void insertOne(Banner banner) {
        banner.setCreateDate(new Date());
        bannerDao.insert(banner);
    }

    @Override
    @LogAnnotation(value = "修改轮播图信息")
    //后台修改轮播图信息
    public String updateOne(Banner banner) {
        Banner banner1 = bannerDao.selectByPrimaryKey(banner);
        if (banner1.getUrl() != "" && banner.getUrl() != "") {
            //修改了图片
            bannerDao.updateByPrimaryKeySelective(banner);
            return "需要修改http";
        } else {
            //不需要修改图片
            banner.setUrl(banner1.getUrl());
            bannerDao.updateByPrimaryKeySelective(banner);
            return null;
        }
    }

    @Override
    @LogAnnotation(value = "修改轮播图状态信息")
    //假删----修改状态
    public void deleteOne(Banner banner) {
        //假删
        banner.setStatus("0");
        bannerDao.updateByPrimaryKeySelective(banner);
    }

    @Override
    @LogAnnotation(value = "删除轮播图信息")
    //真删---删除库及本地文件
    public void delete(String[] ids) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String realPath = requestAttributes.getRequest().getSession().getServletContext().getRealPath("/upload/banner/");
        for (int i = 0; i < ids.length; i++) {
            String id = ids[i];
            String filename = bannerDao.selectByPrimaryKey(id).getFilename();
            File file = new File(realPath, filename);
            if (file != null) {
                if (file.exists()) {
                    file.delete();
                }
            }
        }
        bannerDao.deleteByIdList(Arrays.asList(ids));
    }

    @Override
    //根据id查一个
    public Banner queryOne(String id) {
        return bannerDao.selectByPrimaryKey(id);
    }

    @Override
    //根据时间倒序排列展示5条
    public List<Banner> queryByTime(){ return bannerDao.selectBannersByTime();}
}
