package Carpoolear;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class OrdenEnsambladorRecursos implements ResourceAssembler<Orden, Resource<Orden>> {

    @Override
    public Resource<Orden> toResource(Orden orden) {

        Resource<Orden> ordenRecursos = new Resource<>(orden,
                linkTo(methodOn(OrdenControlador.class).uno(orden.getId())).withSelfRel(),
                linkTo(methodOn(OrdenControlador.class).todo()).withRel("pedidos")
                );


                if (orden.getEstado() == Estado.EN_PROCESOS){
                    ordenRecursos.add(
                            linkTo(methodOn(OrdenControlador.class)
                                    .cancelar(orden.getId())).withRel("cancelar"));
                    ordenRecursos.add(
                            linkTo(methodOn(OrdenControlador.class)
                            .completar(orden.getId())).withRel("completar"));
                }

                return ordenRecursos;
    }
}
