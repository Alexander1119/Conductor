package Carpoolear;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;


@Component
public class ConductorEnsambladorRecursos implements ResourceAssembler<Conductor, Resource<Conductor>> {
    @Override
    public Resource<Conductor> toResource(Conductor conductor) {
        return new Resource<>(conductor,
                linkTo(methodOn(ControladorConductor.class).uno(conductor.getId())).withSelfRel(),
                linkTo(methodOn(ControladorConductor.class).todo()).withRel("conductor"));
    }


}
