package org.sunso.sotheme.springcloud4.data.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.sunso.sotheme.springcloud4.common.dto.data.DataDTO;
import org.sunso.sotheme.springcloud4.data.service.DataService;

@Slf4j
@Service
public class DataServiceImpl implements DataService {
    @Override
    public String checkOk(String message) {
        return "checkOk:" + message;
    }

    @Override
    public DataDTO saveData(DataDTO dto) {
        log.info("saveData data[{}]", dto);
        return dto;
    }
}
