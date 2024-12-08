package com.khu.yaong.test;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name= "테스트 API", description = "상태 체크")
@RestController
@RequestMapping("/api")
public class TestController {
    @Operation(summary = "테스트 생성")
    @GetMapping("/test")
    public String hello(){
        return "hello Yaong!";
    }
}
