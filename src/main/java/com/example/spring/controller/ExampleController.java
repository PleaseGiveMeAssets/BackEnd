package com.example.spring.controller;

import com.example.spring.dto.ExampleDTO;
import com.example.spring.service.ExampleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/example")
@Slf4j
public class ExampleController {
    private ExampleService exampleService;

    @Autowired
    public ExampleController(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<ExampleDTO>> getListExample() {
        return ResponseEntity.ok(exampleService.getListExample());
    }
}
