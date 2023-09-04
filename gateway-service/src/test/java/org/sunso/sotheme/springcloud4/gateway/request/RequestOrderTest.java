package org.sunso.sotheme.springcloud4.gateway.request;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.sunso.sotheme.springcloud4.gateway.SpringTest;

public class RequestOrderTest extends SpringTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getOrderTest() throws Exception {
        RequestBuilder builder = MockMvcRequestBuilders.get("/order/get/105?id=9999&name=dkkjdkk");
        mockMvc.perform(builder);
    }
}
