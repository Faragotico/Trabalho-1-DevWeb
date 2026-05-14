package pagamentos.futebol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages= "pagamentos.futebol")
@EnableJpaRepositories("pagamentos.futebol.repository")
@EntityScan("pagamentos.futebol.model")
public class FutebolApplication {

	public static void main(String[] args){
		SpringApplication.run(FutebolApplication.class, args);
	}
}
