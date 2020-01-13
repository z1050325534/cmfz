package com.baizhi.zw.cmfz;

import com.baizhi.zw.dao.ArticleDao;
import com.baizhi.zw.entity.Article;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.omg.CORBA.InitializerSeqHelper;
import org.apache.poi.hssf.usermodel.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PoiTest {
    @Autowired
    ArticleDao articleDao;
    @Test
    public void test01() {
        HSSFWorkbook workbook = new HSSFWorkbook();//创建文档
        HSSFSheet sheet = workbook.createSheet();//创建工作簿
        HSSFRow row = sheet.createRow(0);//创建行
        HSSFCell cell = row.createCell(0);//创建单元格
        cell.setCellValue("嘿嘿嘿嘿");//为单元格赋值
        try {
            workbook.write(new File("D:\\"+new Date().getTime()+".xls"));//将Excle文档做本地输出
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test02() {
        List<Article> articles = articleDao.selectAll();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow row = sheet.createRow(0);//创建行
        String[] str={"Id","标题","图片","内容","创建时间","出版时间","状态","上师Id"};
        for (int i = 0; i < str.length; i++) {
            String s = str[i];
            row.createCell(i).setCellValue(s);
        }
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        short format = dataFormat.getFormat("yyyy年MM月dd日");
        cellStyle.setDataFormat(format);
        for (int i = 0; i < articles.size(); i++) {
            Article article = articles.get(i);
            HSSFRow row1 = sheet.createRow(i + 1);
            row1.createCell(0).setCellValue(article.getId());

        }
    }

    @Test
    public void testShiro() {
        IniSecurityManagerFactory iniSecurityManagerFactory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = iniSecurityManagerFactory.createInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("admin", "admin");
        subject.login(usernamePasswordToken);
        if(subject.isAuthenticated()){
            System.out.println("hello World");
        }
    }

    @Test
    public void testMD5() {
        String salt = "abcd";//9898247bfac3a524680145b3b5e203d3
        Md5Hash md5Hash = new Md5Hash("zw",salt,1024);
        System.out.println(md5Hash);
    }
}
