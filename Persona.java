//Juan Jose Henriquez Vergara

package modelo;

import utilidades.*;

public class Persona implements Serializable{

    private IdPersona idPersona;
    private Nombre nombreCompleto;
    private String telefono;


    public Persona(IdPersona idPersona, Nombre nombreCompleto) {
        this.idPersona = idPersona;
        this.nombreCompleto = nombreCompleto;
    }

    public IdPersona getIdPersona() {
        return idPersona;
    }
    public Nombre getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto (Nombre nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }


    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Persona (idPersona: " + idPersona + ", nombre Completo: " + nombreCompleto + ", telefono: " + telefono + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Persona persona = (Persona) obj;
        return idPersona.equals(persona.idPersona);
    }

}
