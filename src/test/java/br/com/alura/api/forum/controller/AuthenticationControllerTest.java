package br.com.alura.api.forum.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

//@WebMvcTest -> just for controller simple tests do not call of all process of creation controllers
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnFORBIDDENofInvalidAuthenticationData() throws Exception {
        var uri = new URI("/auth");
        var bodyRequest = "{ \"email\": \"xxx@email.com\", \"password\": \"xxxxx\"}";
        mockMvc.perform(MockMvcRequestBuilders.post(uri).content(bodyRequest).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(HttpStatus.FORBIDDEN.value()));
    }

}
