package tema4;

import java.time.LocalDate;
import java.util.List;

public class Envio {
    
    private LocalDate entrega; // fecha de entrega
    private List<Producto> productos; // lista con productos a entregar en la fecha

    public Envio(LocalDate entrega, List<Producto> productos) {
        this.entrega = entrega;
        this.productos = productos;
    }

    public LocalDate getEntrega() {
        return entrega;
    }

    public List<Producto> getProductos() {
        return this.productos;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Envio)) 
            return false;

        Envio e = (Envio) o;
        return this.entrega.equals(e.entrega)
            && this.productos.equals(e.productos);
    }

    @Override
    public int hashCode() {
        int result = this.entrega.hashCode();
        return 31 * result + productos.hashCode();
    }
}
