package com.project1.haruco;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @ResponseBody
    @RequestMapping("/test")
    public String test() {
        System.out.println("테스트임");
        return "테스트합니다!!!";
    }
    @RequestMapping("/index")
    @ResponseBody
    public String index() {
        return "index";
    }

    @RequestMapping("/SeonSo")
    @ResponseBody
    public String SeonSoHomeTest() {
        return "소선호 HomeTest 확인";
    }

    @RequestMapping("/yeonji")
    @ResponseBody
    public String yeonJiTest() {
        return "연지 HomeTest 확인";
    }

    @RequestMapping("/yoya")
    @ResponseBody
    public String YoYaTest() {
        return "용규 HomeTest 확인";
    }

    @RequestMapping("/daehan")
    @ResponseBody
    public String daehan(){
        return "대한 HoneTest 확인";
    }
}
