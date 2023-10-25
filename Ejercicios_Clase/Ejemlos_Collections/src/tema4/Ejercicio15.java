package tema4;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Ejercicio15 {
	//Recibe una lista de Productos y devuelve una lista de Envios, que agrupa en cada fecha 
	//todos los Productos que tienen que ser enviados en esa misma fecha.
	//La lista de envios resultado debe estar ordenada por fecha de entrega, de más lejana
	//a más reciente en el tiempo.
	public static List<Envio> agruparEnvios(List<Producto> productos) {
		Map<LocalDate,List<Producto>> mapa = new TreeMap<>();
		
		for(Producto p : productos) {
			//Forma convencional
			/*if(!mapa.containsKey(p.getEntrega()))
				mapa.put(p.getEntrega(), new ArrayList<>());
				
			mapa.get(p.getEntrega()).add(p);
			*/
			//Forma mas eficiente
			mapa.putIfAbsent(p.getEntrega(), new ArrayList<>());
			mapa.get(p.getEntrega()).add(p);
		}
		
		List<Envio> listEnvios = new ArrayList<>();
		for (Entry<LocalDate, List<Producto>> entry : mapa.entrySet()){
			listEnvios.add(new Envio(entry.getKey(), entry.getValue()));
		}
		
		return listEnvios;
	}
}
