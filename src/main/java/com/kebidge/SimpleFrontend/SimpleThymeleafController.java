package com.kebidge.SimpleFrontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Value;



@Controller
public class SimpleThymeleafController {

    @Value("${simpleitem.endpoint}")
    private String simpleItemEndpoint;

    @GetMapping("/")
    public String displayPage(Model model) {

        SimpleItem[] items = WebClient
            .create(simpleItemEndpoint)
            .get()
            .retrieve()
            .bodyToMono(SimpleItem[].class)
            .block();

        model.addAttribute("items", items);

        return "page";
    }
    
}
