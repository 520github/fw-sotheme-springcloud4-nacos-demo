package org.sunso.sotheme.springcloud4.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.sunso.sotheme.springcloud4.common.dto.data.DataDTO;
import org.sunso.sotheme.springcloud4.data.service.DataService;

@RestController
@RequestMapping("/data")
public class DataController {

    @Autowired
    private DataService dataService;

    @GetMapping("/check/{message}")
    public String checkOk(@PathVariable String message) {
        return dataService.checkOk(message);
    }

    @PostMapping("/save")
    public DataDTO save(@RequestBody DataDTO dto) {
        return dataService.saveData(dto);
    }
}
