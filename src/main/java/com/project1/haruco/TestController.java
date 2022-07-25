package com.project1.haruco;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @ResponseBody
    @RequestMapping("/test")
    public String test() {

        return "테스트";

    }

    @RequestMapping("/index")
    @ResponseBody
    public String index() {
        return "index";
    }
}
