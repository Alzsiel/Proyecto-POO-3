//Marisol Yañez Borquez
package modelo;
import java.util.ArrayList;
import utilidades.*;
import java.io.Serializable;
public class Auxiliar extends Tripulante implements Serializable{
    private ArrayList<Viaje> viajes;

    public Auxiliar(IdPersona idPersona, Nombre nombre, Direccion direccion) {
        super(idPersona, nombre, direccion);
        viajes = new ArrayList<>();
    }
    @Override
    public void addViaje(Viaje viaje) {
        if (viaje != null) {
            viajes.add(viaje);
        }
    }

    public int getNroViajes() {
        return viajes.size();
    }
}
