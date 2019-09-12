package Carpoolear;


import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resources;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
public class ConductorControlador {


    private final ConductorRepositorio repositorio;
    private final ConductorEnsambladorRecursos ensamblador;

    ConductorControlador(ConductorRepositorio repositorio, ConductorEnsambladorRecursos ensamblador) {
        this.repositorio = repositorio;
        this.ensamblador = ensamblador;

    }

    //Agregando raiz
    @GetMapping("/conductor")
    Resources<Resource<Conductor>> todo() {

        List<Resource<Conductor>> conductores = repositorio.findAll().stream()
                .map(ensamblador::toResource)
                .collect(Collectors.toList());

        return new Resources<>(conductores,
                linkTo(methodOn(ConductorControlador.class).todo()).withSelfRel());
    }

    @PostMapping("/conductor")
    ResponseEntity<?> nuevoConductor(@RequestBody Conductor nuevoConductor) throws URISyntaxException {
        Resource<Conductor> recurso = ensamblador.toResource(repositorio.save(nuevoConductor));

        return ResponseEntity
                .created(new URI(recurso.getId().expand().getHref()))
                .body(recurso);
    }


    //un solo item
    @GetMapping("/conductor/{id}")
    Resource<Conductor> uno(@PathVariable Long id) {

        Conductor conductor = repositorio.findById(id)
                .orElseThrow(() -> new ConductorExcepciónNoEncontrada(id));
        return ensamblador.toResource(conductor);
    }

    @PutMapping("/conductor/{id}")
    ResponseEntity<?> reemplazarConductor(@RequestBody Conductor nuevoConductor, @PathVariable Long id) throws URISyntaxException {

        Conductor actualizarConductor = repositorio.findById(id)
                .map(conductor -> {
                    conductor.setNombre(nuevoConductor.getNombre());
                    conductor.setNumeroLiensia(nuevoConductor.getNumeroLiensia());
                    return repositorio.save(conductor);
                })
                .orElseGet(() -> {
                    nuevoConductor.setId(id);
                    return repositorio.save(nuevoConductor);
                });

        Resource<Conductor> recurso = ensamblador.toResource(actualizarConductor);

        return ResponseEntity
                .created(new URI(recurso.getId().expand().getHref()))
                .body(recurso);

    }

    @DeleteMapping("/conductor/{id}")
    ResponseEntity<?> borrarConductor(@PathVariable Long id) {
        repositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}