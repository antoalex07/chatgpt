package com.antoalex.chatgpt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.antoalex.chatgpt.dto.ChatGPTRequest;
import com.antoalex.chatgpt.dto.ChatGPTResponse;

@RestController
@RequestMapping("/openapi")
public class ChatGPTController {
    
    @Value("${chatgpt.model}")
    private String model;

    @Value("${chatgpt.url}")
    private String url;

    @Autowired
    private RestTemplate temp;

    @GetMapping("/chat")
    public String fetch(@RequestParam(value="value", required = false, defaultValue = "defaultValue") String value){
        ChatGPTRequest request = new ChatGPTRequest(model, value);
        ChatGPTResponse response = temp.postForObject(url, request, ChatGPTResponse.class);
        if(response == null){
            return "System couldn't identify the input";
        }
        return response.getChoices().get(0).getMessage().getContent();

    }

}
