//Juan Jose Henriquez Vergara

package modelo;

import utilidades.*;
public class Conductor implements Serializable, extends Tripulante {

    public Conductor(IdPersona idPersona, Nombre nombre, Direccion direccion) {
        super(idPersona, nombre, direccion);
    }
}
