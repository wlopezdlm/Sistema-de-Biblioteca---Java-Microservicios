package pe.codigo.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Servidor de configuracion centralizada - Clase 3.
 *
 * "La pizarra central": aqui vive la config de TODOS los servicios y este
 * servidor la reparte por HTTP, segun el nombre del servicio que la pida.
 *
 * La magia, otra vez, es una anotacion: @EnableConfigServer.
 *
 * En este proyecto usamos el backend NATIVE (lee de src/main/resources/config)
 * para que arranque sin depender de un repositorio Git remoto. En produccion
 * se usa Git (ver application.properties).
 */
@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
