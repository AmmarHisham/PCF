package webapp;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import webapp.configuration.ElasticsearchConfiguration;


@SpringBootApplication
@Configuration
@EnableAutoConfiguration(exclude = {ElasticsearchConfiguration.class})
//@EnableElasticsearchRepositories("webapp.indexRepo,webapp.testRepo")
public class Application  extends SpringBootServletInitializer {
	public static void main(String args[]) {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}

	private static Class<Application> applicationClass = Application.class;
}