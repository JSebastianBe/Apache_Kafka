package co.vinni.kafka.SBProveedor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Scanner;

@SpringBootApplication
public class SbProveedorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbProveedorApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(KafkaTemplate<String, String> kafkaTemplate){
		while(true){
			String topico = "EquipoKafka-topic";
			Scanner lectura = new Scanner (System.in);

			System.out.println("Ingrese mensaje: ");

			String mensaje = lectura.next();

			kafkaTemplate.send(topico, "{tipo:1,msg:"+mensaje+"}");
		}
	}
}
