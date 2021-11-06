package todoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class TodosApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodosApplication.class, args);
	}
	
	// @ConfigurationPropertiesScan으로 자동 클래스 탐지로 빈 등록하기
	// @Bean
	// public SiteProperties siteProperties() {
	//     return new SiteProperties();
	// }	

}
