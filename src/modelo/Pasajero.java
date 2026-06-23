//Marisol Yañez Borquez
package modelo;
import utilidades.*;
import java.io.Serializable;
public class Pasajero extends Persona implements Serializable {
    private Nombre nomContacto;
    private String fonoContacto;

    public Pasajero(IdPersona idPersona, Nombre nombre) {
        super(idPersona, nombre);
    }

    public Nombre getNomContacto() {
        return nomContacto;
    }

    public void setNomContacto(Nombre nomContacto) {
        this.nomContacto = nomContacto;
    }

    public String getFonoContacto() {
        return fonoContacto;
    }

    public void setFonoContacto(String fonoContacto) {
        this.fonoContacto = fonoContacto;
    }
}