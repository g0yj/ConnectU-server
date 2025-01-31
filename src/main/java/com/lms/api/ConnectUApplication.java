package com.lms.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ConnectUApplication {

  public static void main(String[] args) {
    SpringApplication.run(ConnectUApplication.class, args);
  }

}
