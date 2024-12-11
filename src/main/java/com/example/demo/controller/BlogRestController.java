package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RequiredArgsConstructor
@RestController // @Controller + @ResponseBody
public class BlogRestController {
    
    @GetMapping("/favicon.ico")
        public void favicon() {
        // 아무 작업도 하지 않음
    }
}