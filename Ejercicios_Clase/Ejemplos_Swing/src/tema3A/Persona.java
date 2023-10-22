package tema3A;

import java.time.LocalDate;

public class Persona {

    private String nombre;
    private String apellido;
    private LocalDate nacimiento;
    private String pais;

    public Persona(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nacimiento = null;
        this.pais = null;
    }

    public Persona(String nombre, String apellido, LocalDate nacimiento) {
        this(nombre, apellido);
        this.nacimiento = nacimiento;
    }

    public Persona(String nombre, String apellido, LocalDate nacimiento, String pais) {
        this(nombre, apellido, nacimiento);
        this.pais = pais;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public LocalDate getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(LocalDate nacimiento) {
        this.nacimiento = nacimiento;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    @Override
    public String toString() {
        return String.format("%s %s", nombre, apellido);
    }
}