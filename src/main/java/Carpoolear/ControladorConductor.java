package Carpoolear;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ControladorConductor {


    private final ConductorRepositorio repositorio;

    ControladorConductor(ConductorRepositorio repositorio){
        this.repositorio=repositorio;
    }

    //Agregando raiz
    @GetMapping("/conductor")
    List<Conductor> Todo(){
        return repositorio.findAll();
    }

    @PostMapping("/conductor")
    Conductor nnuevoConductor(@RequestBody Conductor nuevoConductor){
        return repositorio.save(nuevoConductor);
    }

    //un solo item
    @GetMapping("/conductor/{id}")
    Conductor uno(@PathVariable Long id){
        return repositorio.findById(id)
                .orElseThrow(() -> new ConductorExcepciónNoEncontrada(id));
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
