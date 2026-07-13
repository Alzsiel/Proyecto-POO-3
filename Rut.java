//Juan Jose Henriquez Vergara

package utilidades;

import java.io.Serializable;

public class Rut implements IdPersona, Serializable {
    private int numero;
    private char dv;

    public Rut(int numero, char dv) {
        this.numero = numero;
        this.dv = dv;
    }

    public Rut(String rut) {
    }

    public int getNumero() {
        return numero;
    }

    public char getDv() {
        return dv;
    }

    public static Rut of(String rutConDv) {
        String[] partes = rutConDv.split("-");
        int numero = Integer.parseInt(partes[0]);
        char dv = partes[1].charAt(0);
        return new Rut(numero, dv);
    }

    @Override
    public String toString() {
        return numero + "-" + dv;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Rut)) {
            return false;
        }
        Rut otro = (Rut) obj;
        return numero == otro.numero && dv == otro.dv;
    }
}