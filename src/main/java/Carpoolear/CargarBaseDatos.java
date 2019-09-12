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
    CommandLineRunner iniciarBaseDatos(ConductorRepositorio repositorio,OrdenRespositorio ordenRespositorio) {
        ordenRespositorio.save(new Orden("Llevar al centro",Estado.EN_PROCESOS));
        ordenRespositorio.save(new Orden("Dejar en la plaza",Estado.CAMCELADO));

        ordenRespositorio.findAll().forEach(orden -> {
            log.info("Cargando datos" + orden);
        });

        return args -> {
            log.info("Cargando datos" + repositorio.save(new Conductor("Miguel" ,"Rollano","1458955")));
            log.info("Cargando datos" + repositorio.save(new Conductor("Wilson"," Gironda","1168466")));



        };
    }
}
