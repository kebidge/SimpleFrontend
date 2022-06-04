package com.kebidge.SimpleFrontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import reactor.core.publisher.Mono;



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

        return "simple-page";
    }

    @PostMapping("/")
    public String addItem(@RequestParam String newitem, @RequestParam int amount, Model model) {
    
        SimpleItem item = new SimpleItem();
        item.item = newitem;
    
        WebClient
            .create(simpleItemEndpoint + "add")
            .post()
            .body(Mono.just(item), SimpleItem.class)
            .retrieve()
            .bodyToMono(SimpleItem.class)
            .block();
    
        return "redirect:/";
    }
    
}
