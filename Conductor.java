//Juan Jose Henriquez Vergara

package modelo;

import utilidades.*;
public class Conductor extends Tripulante implements Serializable{

    public Conductor(IdPersona idPersona, Nombre nombre, Direccion direccion) {
        super(idPersona, nombre, direccion);
    }
}
