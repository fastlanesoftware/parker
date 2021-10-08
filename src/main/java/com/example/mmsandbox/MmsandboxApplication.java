package com.example.mmsandbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({
		"com.example.mmsandbox.kafka",
})
@SpringBootApplication
public class MmsandboxApplication {

	public static void main(String[] args) {
		SpringApplication.run(MmsandboxApplication.class, args);
	}

}
