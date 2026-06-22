//Tomás Meza

package utilidades;

import java.io.Serializable;

public class Pasaporte implements IdPersona, Serializable {
    private String numero;
    private String nacionalidad;

    public Pasaporte(String numero, String nacionalidad) {
        this.numero = numero;
        this.nacionalidad = nacionalidad;
    }

    public String getNumero() {
        return numero;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    @Override
    public String toString() {
        return numero + "-" + nacionalidad;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pasaporte)) {
            return false;
        }
        Pasaporte otro = (Pasaporte) obj;
        return toString().equals(otro.toString());
    }
}