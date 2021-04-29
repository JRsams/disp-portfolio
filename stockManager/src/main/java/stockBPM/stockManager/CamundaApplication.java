package stockBPM.stockManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication("stockManager")
public class CamundaApplication {

	public static void main(String... args) throws SQLException {
		SpringApplication.run(CamundaApplication.class, args);
	}

}