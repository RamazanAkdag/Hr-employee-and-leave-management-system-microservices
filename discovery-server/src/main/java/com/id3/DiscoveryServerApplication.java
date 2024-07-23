package com.id3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(DiscoveryServerApplication.class,args);
    }
}
