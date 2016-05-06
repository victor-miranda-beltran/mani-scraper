package com.victormiranda.mani.scraper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    private App() {}

    public static void main(String[] args) {
        SpringApplication.run(App.class, args).close();
    }
}
