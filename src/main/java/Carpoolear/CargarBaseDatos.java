package Carpoolear;


import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class CargarBaseDatos  {
    @Bean
    CommandLineRunner iniciarBaseDatos(ConductorRepositorio repositorio) {
        return args -> {
            log.info("Cargando datos" + repositorio.save(new Conductor("Miguel Rollano","1458955")));
            log.info("Cargando datos" + repositorio.save(new Conductor("Wilson Gironda","1168466")));

        };
    }
}
