package ru.knastnt.springjmsibmmq;

import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.spring.SpringFxWeaver;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import javafx.application.Application;
import ru.knastnt.springjmsibmmq.javafx.JavaFxApplication;

@SpringBootApplication
public class SpringJmsIbmMqApplication {

	public static void main(String[] args) {
//		SpringApplication.run(SpringJmsIbmMqApplication.class, args);
		// This is how normal Spring Boot app would be launched
		// SpringApplication.run(SpringBootExampleApplication.class, args);

		// JavaFxApplication doesn't exist yet,
		// we'll create it in the next step
		Application.launch(JavaFxApplication.class, args);
	}

	@Bean
	public FxWeaver fxWeaver(ConfigurableApplicationContext applicationContext) {
		// Would also work with javafx-weaver-core only:
		// return new FxWeaver(applicationContext::getBean, applicationContext::close);
		return new SpringFxWeaver(applicationContext);
	}
}
