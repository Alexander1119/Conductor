package Carpoolear;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Conductor  {

    private @Id @GeneratedValue Long id;
    private String name;
    private String nlicensia;

    public Conductor(String name, String nlicensia) {
        this.name = name;
        this.nlicensia = nlicensia;
    }

    public Conductor(){}
}
