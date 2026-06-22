//Juan Jose Henriquez Vergara

package modelo;

import utilidades.*;
import java.io.Serializable;

public class Conductor extends Tripulante implements Serializable{

    public Conductor(IdPersona idPersona, Nombre nombre, Direccion direccion) {
        super(idPersona, nombre, direccion);
    }
}