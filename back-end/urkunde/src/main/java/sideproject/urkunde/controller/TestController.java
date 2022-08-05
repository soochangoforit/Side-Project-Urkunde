package sideproject.urkunde.controller;

import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;

@RestController
public class TestController {

    @GetMapping("/test")
    public ResponseEntity<Hello> test() {

        Hello hello = new Hello();
        hello.setName("Hello World");

        return ResponseEntity.ok(hello);
    }

    @Data
    static class Hello{
        private String name;
    }
}
