package Carpoolear;


import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resources;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
public class ConductorControlador {


    private final ConductorRepositorio repositorio;
    private final ConductorEnsambladorRecursos ensamblador;

    ConductorControlador(ConductorRepositorio repositorio, ConductorEnsambladorRecursos ensamblador){
        this.repositorio=repositorio;
        this.ensamblador=ensamblador;

    }

    //Agregando raiz
    @GetMapping("/conductor")
    Resources<Resource<Conductor>> todo(){

        List<Resource<Conductor>> conductores = repositorio.findAll().stream()
                .map(ensamblador::toResource)
                .collect(Collectors.toList());

        return new Resources<>(conductores,
                linkTo(methodOn(ConductorControlador.class).todo()).withSelfRel());
    }

    @PostMapping("/conductor")
    Conductor nnuevoConductor(@RequestBody Conductor nuevoConductor){
        return repositorio.save(nuevoConductor);
    }

    //un solo item
    @GetMapping("/conductor/{id}")
    Resource<Conductor> uno(@PathVariable Long id){

        Conductor conductor=repositorio.findById(id)
                .orElseThrow(() ->new ConductorExcepciónNoEncontrada(id));
        return ensamblador.toResource(conductor);
    }

    @PutMapping("/conductor/{id}")
    Conductor reemplazarConductor(@RequestBody Conductor nuevoConductor,@PathVariable Long id){
        return repositorio.findById(id)
                .map(conductor ->{
                    conductor.setName(nuevoConductor.getName());
                    conductor.setName(nuevoConductor.getName());
                    return repositorio.save(conductor);
                })
                .orElseGet(() ->{
                    nuevoConductor.setId(id);
                    return repositorio.save(nuevoConductor);
                });
    }

    @DeleteMapping("/conductor/{id}")
            void BorrarConductor(@PathVariable Long id){
                repositorio.deleteById(id);
            }
}
