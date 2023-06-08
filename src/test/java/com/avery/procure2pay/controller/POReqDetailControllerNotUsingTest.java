package com.avery.procure2pay.controller;


import com.avery.procure2pay.model.POReqDetail;
import com.avery.procure2pay.service.POReqDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(POReqDetailController_NotUsing.class)
class POReqDetailControllerNotUsingTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    POReqDetailService poReqDetailService;

    @Test
    void getPOReqDetails_success() throws Exception {
        List<POReqDetail> poReqDetailLists = new ArrayList<>(Arrays.asList(new POReqDetail(), new POReqDetail(), new POReqDetail()));

        when(poReqDetailService.getPOReqDetails()).thenReturn(poReqDetailLists);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/po-req-details/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
    }
}