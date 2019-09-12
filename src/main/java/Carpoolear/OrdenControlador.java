package Carpoolear;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
class OrdenControlador {


    private final OrdenRespositorio ordenRespositorio;
    private final OrdenEnsambladorRecursos ensamblador;

    public OrdenControlador(OrdenRespositorio ordenRespositorio, OrdenEnsambladorRecursos ensamblador) {
        this.ordenRespositorio = ordenRespositorio;
        this.ensamblador = ensamblador;
    }

    @GetMapping("/pedidos")
    Resources<Resource<Orden>> todo(){
        List<Resource<Orden>> pedidos = ordenRespositorio.findAll().stream()
                .map(ensamblador::toResource)
                .collect(Collectors.toList());
        return new Resources<>(pedidos,
                linkTo(methodOn(OrdenControlador.class).todo()).withSelfRel() );

    }


    @GetMapping("/pedidos/{id}")
    Resource<Orden> uno(@PathVariable Long id) {
        return ensamblador.toResource(
                ordenRespositorio.findById(id)
                        .orElseThrow(() -> new OrdenNoEncontradoExcepcion(id)));
    }

    @PostMapping("/pedidos")
    ResponseEntity<Resource<Orden>> nuevoOrden(@RequestBody Orden orden){

        orden.setEstado(Estado.EN_PROCESOS);
        Orden nuevoOrden = ordenRespositorio.save(orden);

        return ResponseEntity
                .created(linkTo(methodOn(OrdenControlador.class).uno(nuevoOrden.getId())).toUri())
                .body(ensamblador.toResource(nuevoOrden));
    }


    @DeleteMapping("/pedidos/{id}/cancelar")
    ResponseEntity<ResourceSupport> cancelar(@PathVariable Long id){

        Orden orden = ordenRespositorio.findById(id).orElseThrow(() -> new OrdenNoEncontradoExcepcion(id));

        if (orden.getEstado() == Estado.EN_PROCESOS){
            orden.setEstado(Estado.CAMCELADO);
            return ResponseEntity.ok(ensamblador.toResource(ordenRespositorio.save(orden)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new VndErrors.VndError("Metodo nho permitido","No puede cancelar un pedido que está en el " + orden.getEstado() + "estado"));
    }

    @PutMapping("/pedidos/{id}/completar")
    ResponseEntity<ResourceSupport> completar(@PathVariable Long id) {

        Orden orden = ordenRespositorio.findById(id).orElseThrow(() -> new OrdenNoEncontradoExcepcion(id));

        if (orden.getEstado() == Estado.EN_PROCESOS) {
            orden.setEstado(Estado.TERMINADO);
            return ResponseEntity.ok(ensamblador.toResource(ordenRespositorio.save(orden)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new VndErrors.VndError("Método no permitido", "No puede completar un pedido que está en " + orden.getEstado() + " estado"));
    }








}
