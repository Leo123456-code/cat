package com.stylefeng.guns;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stylefeng.guns.rest.OrderApplication;
import com.stylefeng.guns.rest.common.util.FTPUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderApplication.class)
@Slf4j
public class GunsRestApplicationTests {

    @Autowired
    private FTPUtil ftpUtil;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();


    @Test
    public void contextLoads() {

        String fileStrByAddress = ftpUtil.getFileStrByAddress("seats/cgs.json");

        System.out.println(fileStrByAddress);


    }

    @Test
	public void test02(){

		String fileStrByAddress = ftpUtil.getFileStrByAddress("seats/123214.json");

		System.out.println(fileStrByAddress);
		log.info("fileStrByAddress={}",gson.toJson(fileStrByAddress));

	}

}
