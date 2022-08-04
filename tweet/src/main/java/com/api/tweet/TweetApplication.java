package com.api.tweet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.api.tweet.swagger.SwaggerFilter;

import lombok.extern.slf4j.Slf4j;


@SpringBootApplication
@EnableFeignClients
@Slf4j
public class TweetApplication {

	public static void main(String[] args) {
		SpringApplication.run(TweetApplication.class, args);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		log.debug("Entering filterRegisterationBean Method !!!");
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		SwaggerFilter myFilter = new SwaggerFilter();
		filterRegistrationBean.setFilter(myFilter);
		log.debug("Exiting filterRegisterationBean Method !!!");
		return filterRegistrationBean;
	}

}
