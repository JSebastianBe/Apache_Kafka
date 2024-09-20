package co.vinni.kafka.SBProveedor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@SpringBootApplication
@Service
public class SbProveedorApplication {

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	public static void main(String[] args) {
		SpringApplication.run(SbProveedorApplication.class, args);
	}

	public void sendMessage(Object mensaje) {
		String topico = "EquipoKafka-topic";
		kafkaTemplate.send(topico, mensaje);
		System.out.println("Mensaje: " + mensaje + " enviado al tópico: " + topico);
	}
}
