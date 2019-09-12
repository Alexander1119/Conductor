package Carpoolear;

public class ConductorExcepciónNoEncontrada extends RuntimeException{
    ConductorExcepciónNoEncontrada(Long id){
        super("No se pudo encontrar empleado" + id);
    }

}
