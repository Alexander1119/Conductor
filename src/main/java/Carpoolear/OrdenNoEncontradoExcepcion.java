package Carpoolear;

public class OrdenNoEncontradoExcepcion extends RuntimeException {

    OrdenNoEncontradoExcepcion(Long id){
        super("No se pudo encontrar pedido" + id);
    }

}
