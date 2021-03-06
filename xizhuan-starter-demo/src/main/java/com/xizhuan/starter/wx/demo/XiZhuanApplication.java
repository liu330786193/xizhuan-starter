package com.xizhuan.starter.wx.demo;

import com.xizhuan.prevent.duplicate.form.annotation.XZPreventDuplicateForm;
import com.xizhuan.wx.chatbot.SendResult;
import com.xizhuan.wx.chatbot.WxChatbotClient;
import com.xizhuan.wx.chatbot.message.TextMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @ProjectName: xizhuan-starter
 * @Package: com.xizhuan.starter.wx.demo
 * @ClassName: XiZhuanApplication
 * @Author: lyl
 * @Description: 启动程序
 * @Date: 2020/10/15 10:32
 */
@RestController
@SpringBootApplication
@ComponentScan("com.xizhuan")
public class XiZhuanApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(XiZhuanApplication.class, args);
    }

    @RequestMapping("/test")
    private void test() throws IOException {
        TextMessage message = new TextMessage("微信API");
        SendResult send = WxChatbotClient.send("https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=21c5110d-4a20-4144-a401-f4c5373ebaa4", message);
        System.out.println("拿提交");
    }



    @XZPreventDuplicateForm
    @GetMapping("/test/prevent/dup")
    public void preventDup() throws InterruptedException {
        System.out.println("测试防重");
        Thread.sleep(3000);
    }

}