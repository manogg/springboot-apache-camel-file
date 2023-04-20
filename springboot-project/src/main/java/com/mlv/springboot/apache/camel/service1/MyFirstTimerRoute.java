package com.mlv.springboot.apache.camel.service1;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class MyFirstTimerRoute extends RouteBuilder {


	@Autowired
	private GetCurrentTimeBean getCurrentTimeBean;

	@Autowired
	private SimpleLog simpleLog;

	@Override
	public void configure() throws Exception {

		// consume end points
		// if you want to make any changes on message body then go for Transform()
		// if you don't want to make any changes on message body then go for
		// Processing()
		// publish end points

		// transform

		from("timer:first-timer")
		.log("${body}")
		.bean("getCurrentTimeBean","getCurrentTime")
		.log("${body}")
		//.bean("simpleLog","process")
		.bean(new SimpleLogProcessor())
		.to("log:first-timer");

	

	}

}

@Component
class GetCurrentTimeBean {

	public String getCurrentTime() {
		return "Time is :" + LocalDateTime.now();
	}

}

@Component
class SimpleLog {

	private Logger log = LoggerFactory.getLogger(SimpleLog.class);

	public void process(String message) {

		log.info("SimpleLog >>> {}", message);
	}
}

class SimpleLogProcessor implements Processor {
	
	private Logger log= LoggerFactory.getLogger(SimpleLogProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		
		log.info("SimpleLogProcessor >>> {}", exchange.getMessage().getBody());
		
		
	}

}
