package utilidades;
public class Direccion {
    private String calle;
    private int numero;
    private String comuna;

    public Direccion (String calle, int numero, String comuna){
        this.calle=calle;
        this.numero=numero;
        this.comuna=comuna;
    }

    public String getCalle() {
        return calle;
    }

    public int getNumero() {
        return numero;
    }

    public String getComuna() {
        return comuna;
    }

    public String toString() {
        return "Calle: " + calle + "Número:" + numero + "Comuna: " + comuna;
    }

    @Override
    public boolean equals(Object otro) {
        if (this == otro) {
            return true;
        }
        if (otro == null || getClass() != otro.getClass()) {
            return false;
        }
        Direccion d = (Direccion) otro;
        return numero == d.numero &&
                calle.equals(d.calle) &&
                comuna.equals(d.comuna);
    }
}