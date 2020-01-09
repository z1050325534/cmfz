package com.baizhi.zw.cmfz;

import com.baizhi.zw.dao.*;
import com.baizhi.zw.entity.*;
import com.baizhi.zw.service.BannerService;
import com.baizhi.zw.service.BannerServiceImpl;
import io.goeasy.GoEasy;
import lombok.AllArgsConstructor;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AdminTest {
    @Autowired
    AdminDao adminDao;
    @Autowired
    BannerDao bannerDao;
    @Autowired
    ChapterDao chapterDao;
    @Autowired
    AlbumDao albumDao;
    @Autowired
    UserDao userDao;
    @Autowired
    GuanXiDao guanXiDao;
    @Autowired
    CourseDao courseDao;

    @Test
    public void queryAll() {
        Admin admins = adminDao.selectOneByExample(new Admin(null,"admin","admin"));
        System.out.println(admins);
    }

    @Test
    public void bannerTexst() {
        List<Banner> banners = bannerDao.selectByRowBounds(new Banner(), new RowBounds(1, 5));
        System.out.println(banners);
    }

    @Test
    public void name() {
        Banner banner = bannerDao.selectOne(new Banner("1", null, null, null, null, null,null,null));
        System.out.println(banner);
    }

    @Test
    public void Test1() {
        // 创建 Example对象 负责id查询条件查询 list
        Example e = new Example(Chapter.class);
        e.createCriteria().andEqualTo("albumId","2");
        List<Chapter> chapters = chapterDao.selectByExample(e);
        System.out.println(chapters.size());

    }

    @Test
    public void names() {
        Album album = new Album();
        album.setId("1");
        Album album1 = albumDao.selectOne(album);
        System.out.println(album1);
    }

    @Test
    public void tetschapter() {
        chapterDao.updateByPrimaryKeySelective(new Chapter("1",null,"2",null,null,null,null,null));
    }

    @Test
    public void haha() {
        Banner banner = new Banner();
        banner.setId("1");
        banner.setCreateDate(new Date());
        banner.setStatus("2");
        banner.setHref("2");
        banner.setTitle("2");
        banner.setStatus("2");
        banner.setUrl(null);
        bannerDao.updateByPrimaryKeySelective(banner);

    }

    @Test
    public void heiheih() {
        Chapter chapter = new Chapter();
        chapter.setAlbumId("7c6b5019-d119-41a0-aced-a6d98e243229");
        Example e = new Example(Chapter.class);
        e.createCriteria().andEqualTo("albumId", "7c6b5019-d119-41a0-aced-a6d98e243229");
        List<Chapter> chapters = chapterDao.selectByExampleAndRowBounds(e, new RowBounds(0, 1));
        System.out.println(chapters);
    }

    @Test
    public void heihei() {
        List<City> man = userDao.selectUserByLocation("1");
        System.out.println(man);
        List<City> woman = userDao.selectUserByLocation("0");
        System.out.println(woman);
        HashMap hashMap = new HashMap();
        hashMap.put("man",man);
        hashMap.put("woman",woman);
        System.out.println(hashMap);
    }

    @Test
    public void testLogin() {
        User user = userDao.selectByPhoneAndPassword("1", "1");
        System.out.println(user);
    }

    @Test
    public void testBanner() {
        List<Banner> list = bannerDao.selectBannersByTime();
        System.out.println(list);
    }

    @Test
    public void test9() {
        Example e = new Example(Course.class);
        e.createCriteria().andEqualTo("userId", "1");
        List<Course> courses = courseDao.selectByExample(e);
        System.out.println(courses);

    }

    @Test
    public void haha1() {
        Example e = new Example(Guanxi.class);
        e.createCriteria().andEqualTo("uid", "1");
        List<Guanxi> guanxis = guanXiDao.selectByExample(e);
        System.out.println(guanxis);
    }

    @Test
    public void goesay() {
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-47e47f95448848df966df1209ab41af7");
                goEasy.publish("cmfz", "测试发送数据！！");
    }
}

