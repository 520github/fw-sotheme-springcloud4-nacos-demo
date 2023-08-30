package org.sunso.sotheme.springcloud4.data.service.impl;

import org.springframework.stereotype.Service;
import org.sunso.sotheme.springcloud4.data.service.DataService;

@Service
public class DataServiceImpl implements DataService {
    @Override
    public String checkOk(String message) {
        return "checkOk:" + message;
    }
}
