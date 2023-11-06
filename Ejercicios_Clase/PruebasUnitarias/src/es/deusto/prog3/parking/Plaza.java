package es.deusto.prog3.parking;

/**
 * Esta clase representa una plaza del parking.
 * Cada plaza tiene un identificador único y un
 * precio por hora asociado.
 */


public class Plaza {

    private String id; // el identificador de la plaza
    private float precioHora; // el precio por hora
    private boolean ocupada; // indica si la plaza está ocupada

    /**
     * Constructor de la clase. 
     * @param id identificador de la plaza.
     * @param precioHora precio por hora de la plaza.
     */
    public Plaza(String id, float precioHora) {
        this.id = id;
        this.precioHora = precioHora;
        this.ocupada = false;
    }

    /**
     * Obtiene el identificador de la plaza
     * @return el identificador de la plaza
    */
    public String getId() {
        return id;
    }

    /**
     * Obtiene el precio por hora de la plaza
     * @return el precio por hora
     */
    public float getPrecioHora() {
        return precioHora;
    }

    /**
     * Indica si la plaza está ocupada
     * @return true si la plaza está ocupada, false en caso contrario
     */
    public boolean getOcupada() {
        return ocupada;
    }

    /**
     * Permite cambiar el estado de la plaza.
     * @param ocupada nuevo estado de la plaza.
     */
    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    /**
     * Permite cambiar el precio por hora de la plaza.
     * @param precioHora el nuevo precio por hora de la plaza.
     */
    public void setPrecioHora(float precioHora) {
        this.precioHora = precioHora;
    }

    @Override
    public String toString() {
        return String.format("%s: %.2f", id, precioHora);
    }
}