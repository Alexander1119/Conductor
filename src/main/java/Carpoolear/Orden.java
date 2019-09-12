package Carpoolear;


import jdk.jshell.Snippet;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "CUSTOMER_ORDER")
class Orden {
    private @Id @GeneratedValue Long id;

    private String descripcion;
    private Estado estado;


    Orden(){}

    public Orden(String descripcion, Estado estado) {
        this.descripcion = descripcion;
        this.estado = estado;
    }
}
