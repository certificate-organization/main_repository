package com.start.st.domain.mbti.controller;

import com.start.st.domain.mbti.entity.Mbti;
import com.start.st.domain.mbti.service.MbtiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/api/mbti")
public class MbtiApiController {

    @Autowired
    private MbtiService mbtiService;

    @GetMapping("/{name}")
    public ResponseEntity<Mbti> getMbtiByName(@PathVariable(value = "name") String name) {
        Mbti mbti = mbtiService.findMbtiByName(name);

        if (mbti != null) {
            return new ResponseEntity<>(mbti, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}