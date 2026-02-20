package com.mringupta.springbootlearn.controller;

import com.mringupta.springbootlearn.NameService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/name")
@AllArgsConstructor
public class NameController {

    private final NameService nameService;

    @GetMapping("/")
    public ResponseEntity<String> getName() {
        String name = nameService.sendName();
        return ResponseEntity.ok(name);
    }
}
