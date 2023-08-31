package org.sunso.sotheme.springcloud4.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.sunso.sotheme.springcloud4.common.dto.data.DataDTO;

@FeignClient("dataService4")
public interface DataFeignClient {

    @PostMapping("/data/save")
    DataDTO save(@RequestBody DataDTO dto);
}
