package com.finapp.tests.configs.jwtoauth;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Class name: ControllerScanner
 * Creater: wgicheru
 * Date:6/21/2019
 */
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {"com.finapp.tests.controllers"})
public class ControllerScanner implements WebMvcConfigurer {
}
