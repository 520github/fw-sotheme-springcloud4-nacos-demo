package org.sunso.sotheme.springcloud4.data.service;

import org.sunso.sotheme.springcloud4.common.dto.data.DataDTO;

public interface DataService {
   String checkOk(String message);

   DataDTO saveData(DataDTO dto);
}
