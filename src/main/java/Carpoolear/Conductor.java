package Carpoolear;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Conductor  {

    private @Id @GeneratedValue Long id;
    private String primerNombre;
    private String apellido;
    private String numeroLiensia;

    public Conductor(){}

    public Conductor(String primerNombre, String apellido, String nlicensia) {
        this.primerNombre = primerNombre;
        this.apellido = apellido;
        this.numeroLiensia = nlicensia;
    }

    public String getNombre(){
        return this.primerNombre + " " + this.apellido;
    }

    public void setNombre(String nombre){
        String[] partes= nombre.split( " ");
        this.primerNombre = partes[0];
        this.apellido = partes[1];
    }
}
