package de.cd.user.inbound.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBack {

    @GetMapping("/fallback")
    private String fallack() {
        return "Leider ist der Service gerade nicht erreichbar";
    }
}
