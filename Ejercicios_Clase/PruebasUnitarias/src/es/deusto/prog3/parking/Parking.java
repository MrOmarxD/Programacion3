package es.deusto.prog3.parking;

/**
 * Esta clase representa un parking
 * Cada parking tiene un nombre identificativo.
 */
public class Parking {

    private final static int NUM_PLAZAS = 20;

    private String nombre; // nombre del parking
    private Plaza[] plazas; // plazas del parking
    
    /**
     * Constructor de la clase
     * @param nombre nombre del parking
     */
    public Parking(String nombre) {
    	this.nombre = nombre;
        plazas = new Plaza[NUM_PLAZAS];

        for (int i = 0; i < NUM_PLAZAS; i++) {
            plazas[i] = new Plaza(String.format("%02d", i), 2.50f);
        }
    }

    /**
     * Obtiene el nombre del parking
     * @return el nombre del parking
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el número de plazas libres en el parking
     * @return número de plazas libres
     */
    public int getPlazasLibres() {
        int numLibres = 0;
        for (Plaza p : plazas) {
            if (!p.getOcupada()) {
                numLibres += 1;
            }
        }
        return numLibres;
    }
    
    /**
     * Reserva la plaza cuyo identificador se ha pasado
     * como parámetro. Si la plaza se ha podido reservar
     * devuelve true, si la plaza no se ha podido reservar
     * porque está ocupada devuelve false. Si la plaza a reservar
     * no existe, se lanza una excepción.
     */
    public boolean reservar(String id) throws PlazaNoEncontrada {
        for (Plaza p :  plazas) {
            if (p.getId().equals(id)) {
                if (!p.getOcupada()) {
                    p.setOcupada(true);
                    return true;
                } else {
                    return false;
                }
            }
        }

        throw new PlazaNoEncontrada();
    }

    /**
     * Libera la plaza indicada si estaba ocupada y
     * devuelve el coste total de acuerdo al número de horas
     * que la plaza ha estado ocupada.
     * Si la plaza no estaba ocupada, o no existe, el método retorna 0.0
     * @param id el identificador de la plaza a liberar
     * @param horas el número de horas que la plaza ha estado ocupada
     * @return el coste total (horas * precioHora)
     */
    public float liberar(String id, int horas) {
        for (Plaza p :  plazas) {
            if (p.getId().equals(id)) {
                if (p.getOcupada()) {
                    p.setOcupada(false);
                    return p.getPrecioHora() * horas;
                } else {
                    return 0.0f;
                }
            }
        }

        return 0.0f;
    }
}
