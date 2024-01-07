package es.deusto.ingenieria.prog3.easybooking.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import es.deusto.ingenieria.prog3.easybooking.domain.Airline;
import es.deusto.ingenieria.prog3.easybooking.domain.Airport;
import es.deusto.ingenieria.prog3.easybooking.domain.BoardingPass;
import es.deusto.ingenieria.prog3.easybooking.domain.Flight;
import es.deusto.ingenieria.prog3.easybooking.domain.Reservation;
import es.deusto.ingenieria.prog3.easybooking.domain.Statistics;
import es.deusto.ingenieria.prog3.easybooking.service.AirAllianceService;
import es.deusto.ingenieria.prog3.easybooking.service.OneWorldService;
import es.deusto.ingenieria.prog3.easybooking.service.SkyTimeService;
import es.deusto.ingenieria.prog3.easybooking.service.StarAllianceService;


public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	
	//Servicios de aerolíneas
	private List<AirAllianceService> airAllianceServices = new ArrayList<>();
	
	//Lista de vuelos que se está visualizando en la ventana
	private List<Flight> flights = new ArrayList<>();
	
	//JTable de vuelos
	private JTable jTableFlights = new JTable();	
	//JLabel para mensajes de información
	private JLabel jLabelInfo = new JLabel("Selecciona un aeropuerto origen");
	//JCombos de Origen y Destino
	private JComboBox<String> jComboOrigin = new JComboBox<>();
	private JComboBox<String> jComboDestination = new JComboBox<>();
	private JButton jBtnRecursiveSearch = new JButton("Búsqueda máx. 2 escalas");
	private JButton jBtnRecursiveSearch2 = new JButton("Búsqueda IDA y VUELTA");
	private JButton jBtnAirportRanking = new JButton("Ranking Aeropuertos");
	private JButton jBtnAirlineOperations = new JButton("Operaciones Aerolíneas");
	
	public MainWindow() {
		//Se inicializan los servicios de las aerolíneas
		airAllianceServices.add(new OneWorldService());
		airAllianceServices.add(new SkyTimeService());
		airAllianceServices.add(new StarAllianceService());
		
		//Ajuste el JComboBox de aeropuertos origen
		jComboOrigin.setPrototypeDisplayValue("Seleccione el nombre del aeropuerto origen");		
		jComboOrigin.addActionListener((e) -> {
			Object fromItem = ((JComboBox<?>) e.getSource()).getSelectedItem();
			flights = new ArrayList<>();
			
			if (fromItem != null && !fromItem.toString().isEmpty()) {
				final String origin = fromItem.toString().substring(0, fromItem.toString().indexOf(" - "));
				
				if (!origin.isEmpty()) {
					Set<Airport> destinations = new HashSet<>();					
					airAllianceServices.forEach(aA -> {
						aA.getDestinations(origin).forEach(a -> destinations.add(a));
					});
					updateDestinations(new ArrayList<Airport>(destinations));
					jBtnRecursiveSearch2.setEnabled(true);
				} else {
					jComboDestination.removeAllItems();
					jBtnRecursiveSearch2.setEnabled(false);
				}								
			} else {
				jBtnRecursiveSearch2.setEnabled(false);
			}
			
			updateFlights();
			jLabelInfo.setText("Selecciona un aeropuerto origen");
		});
		
		//Recuperación de todos los aeropuertos origen de las alianzas de aerolíneas
		Set<Airport> origins = new HashSet<>();
		airAllianceServices.forEach(aA -> aA.getOrigins().forEach(a -> origins.add(a)));				
		updateOrigins(new ArrayList<Airport>(origins));

		//Inicialización el JComboBox de aeropuertos destino
		jComboDestination.setPrototypeDisplayValue("Seleccione el nombre del aeropuerto destino");
		jComboDestination.addActionListener((e) -> {
			Object toItem = ((JComboBox<?>) e.getSource()).getSelectedItem();
			flights = new ArrayList<>();
			
			if (toItem != null && !toItem.toString().isEmpty()) {
				final String destination = toItem.toString().substring(0, toItem.toString().indexOf(" - "));
				
				if (!destination.isEmpty() && jComboOrigin.getSelectedIndex() > 0) {
					Object fromItem = jComboOrigin.getSelectedItem();
					final String origin = fromItem.toString().substring(0, fromItem.toString().indexOf(" - "));
					airAllianceServices.forEach(aA -> {
						aA.search(origin, destination).forEach(f -> flights.add(f));
					});
				}
			}
			
			updateFlights();
		});		

		//Inicialización del botón de búsqueda recursiva máx. escalas 2
		jBtnRecursiveSearch.setEnabled(false);
		jBtnRecursiveSearch.addActionListener((e) -> {
			Object item = jComboOrigin.getSelectedItem();
			String origin = item.toString().substring(0, item.toString().indexOf(" - "));
			item = jComboDestination.getSelectedItem();
			String destination = item.toString().substring(0, item.toString().indexOf(" - "));

			//Se realiza la búsqueda recursiva de vuelos en un hilo independiente
			new Thread(() -> {
				//Se invica al método recursivo
				List<List<Flight>> flightsList = recursiveSearch(origin, destination);
				//Se muestra el resultado de la búsqueda en un cuadro de diálogo
				showItinerariesDialog(origin, destination, flightsList);				
			}).start();
		});
		
		//Inicialización del botón de búsqueda recursiva ida y vuelta
		jBtnRecursiveSearch2.setEnabled(false);
		jBtnRecursiveSearch2.addActionListener((e) -> {
			Object item = jComboOrigin.getSelectedItem();
			String origin = item.toString().substring(0, item.toString().indexOf(" - "));
			
			//Se realiza la búsqueda recursiva de vuelos en un hilo independiente
			new Thread(() -> {
				try {
					String input = JOptionPane.showInputDialog(this, "Introduce dinero disponible para oferta de vacaciones");
					int credit = Integer.parseInt(input);
					
					//recursiveSearchVacation(origin, destination, credit);
					List<List<Flight>> flightsList = recursiveRoundtrip(origin, credit);
					
					//Se muestra el resultado de la búsqueda en un cuadro de diálogo
					showItinerariesDialog(origin, origin, flightsList);
				} catch (NumberFormatException ex) {
					return;
				}
			}).start();
		});		
		
		//Inicialización de los botones de las estadísticas	
		jBtnAirportRanking.addActionListener((e) -> showAirportTransitRanking());
		jBtnAirlineOperations.addActionListener((e) -> showAirlineOperationsData());
		
		//Inicialización de la tabla de vuelos
		jTableFlights.setRowHeight(30);
		jTableFlights.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		((DefaultTableCellRenderer) jTableFlights.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		
		//Inicialización del label de información
		jLabelInfo.setHorizontalAlignment(JLabel.RIGHT);
		
		//Distribución de los elementos en el JFrame
		JPanel pOrigin = new JPanel();		
		pOrigin.add(new JLabel("Origen: "));		
		pOrigin.add(this.jComboOrigin);

		JPanel pDestination = new JPanel();
		pDestination.add(new JLabel("Destino: "));
		pDestination.add(jComboDestination);
		
		JPanel pButtons = new JPanel();
		pButtons.add(jBtnRecursiveSearch);
		pButtons.add(jBtnRecursiveSearch2);
		pButtons.add(jBtnAirportRanking);
		pButtons.add(jBtnAirlineOperations);
		
		JPanel pSearch = new JPanel();
		pSearch.setBorder(new TitledBorder("Búsqueda de vuelos"));
		pSearch.setLayout(new GridLayout(3, 1));
		pSearch.add(pOrigin);
		pSearch.add(pDestination);		
		pSearch.add(pButtons);
						
		add(pSearch, BorderLayout.NORTH);
		add(new JScrollPane(jTableFlights), BorderLayout.CENTER);
		add(jLabelInfo, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Easy Booking");
		setIconImage(new ImageIcon("resources/images/logo.png").getImage());		
		setSize(1200, 600);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Genera una lista de itinerarios de ida y vuelta desde un origen y con un presupueto máximo.
	 * El proceso se implementa de manera recursiva.
	 * @param origin String con el código del aeropuerto origen.
	 * @param credit float con el crédito máximo.
	 * @return List<List<Flight>> con la lista de itinerarios.
	 */
	private List<List<Flight>> recursiveRoundtrip(String origin, float credit) {
		//Se recuperan todos los vuelos 
		List<Flight> allFlights = new ArrayList<>();

		airAllianceServices.forEach(aA -> {
			aA.getAllFlights().forEach(f -> allFlights.add(f));
		});
		
		//Se realiza la búsqueda recursiva de ida y vuelta.
        List<List<Flight>> result = new ArrayList<>();		
        recursiveRoundtripAux(result, new ArrayList<>(), origin, credit, allFlights);
        
		return result;
	}
	
	//T6. Recursividad: La nueva versión de la aplicación incorpora el botón “Búsqueda IDA y VUELTA”.
	//Este nuevo botón se activa al seleccionar el aeropuerto de origen.
	//Cuando se pulsa este botón se abre un cuadro de diálogo para introducir una
	//cantidad económica y a partir de ese importe realiza una búsqueda recursiva
	//para obtener todos los itinerarios (secuencias de vuelos encadenadas) que 
	//permiten regresar al aeropuerto origen. Es decir, para cada itinerario, 
	//el aeropuerto origen del primer y último vuelo se corresponden con el vuelo 
	//seleccionado como origen. El coste de todos los vuelos de un itinerario debe
	//ser siempre menor o igual a la cantidad introducida en el cuadro de diálogo.
	//Puedes implementar este método de dos maneras. Cada una de ellas lleva asociada una puntuación diferente:
	//- Imprimir por pantalla cada itinerario (1 punto)
	//- Generar un cuadro de diálogo similar al de la búsqueda recursiva del examen de convocatoria ordinaria (2 puntos)	
	
	/**
	 * Genera una lista de itinerarios de ida y vuelta desde un origen y con un presupueto máximo.
	 * El proceso se implementa de manera recursiva.
	 * @param result List<List<Flight>> con la lista de itinerarios.
	 * @param aux List<Flight> con una lista auxiliar para crear cada itinerario.
	 * @param origin String con el código del aeropuerto origen.
	 * @param credit float con el crédito máximo.
	 * @param allFlights List<Flight> con la lista de vuelos usados como referencia.
	 */
	private void recursiveRoundtripAux(List<List<Flight>> result, List<Flight> aux, String origin, float credit, List<Flight> allFlights) {
		//CASO BASE 1: Se ha superado el dinero disponible
		if (credit < 0) {
			return;
		}
		
		//CASO BASE 2: aux no esta vacío AND
		if (!aux.isEmpty() &&
			//el destino del último vuelo en aux es 'origin' AND
			aux.get(aux.size()-1).getDestination().getCode().equals(origin) &&
			//itinerario no repetido
			!result.contains(aux)) {
			//Se añade el nuevo itinerario a la lista de resultados
			result.add(new ArrayList<>(aux));			
		//CASO RECURSIVO: Se van añadiendo vuelos al itinerario aux  
		} else {
			//Se recorren los vuelos disponbles
			for (Flight flight : allFlights) {
				//Si aux está vacío y el vuelo actual sale de 'origin' OR
				if ((aux.isEmpty() && flight.getOrigin().getCode().equals(origin)) ||
					//el origen del vuelo actual es el destino del último vuelo de aux y el vuelo no está en aux
					(!aux.isEmpty() && flight.getOrigin().getCode().equals(aux.get(aux.size()-1).getDestination().getCode()) && !aux.contains(flight))) {
					//Se añade el vuelo a aux
					aux.add(flight);
					//Se realiza la invocación recursiva: se reduce credit
					recursiveRoundtripAux(result, 
									   	  aux,
									      origin,
									      credit - flight.getPrice(),
									      allFlights);
					//Se elimina de aux el último vuelo añadido
					aux.remove(aux.size()-1);
				}
			}
		}
	}
	
	/**
	 * Devuelve una lista de itinerario (lista de vuelos) entre un aeropuerto origen y
	 * un aeropuerto destino con un máximo de 2 escalas.
	 * @param origin String con el código del aeropuerto origen.
	 * @param destination String con el código de aeropuerto destino.
	 * @return List<List<Flight>> lista con los itinearios entre origen y destino.
	 */
	private List<List<Flight>> recursiveSearch(String origin, String destination) {
		//Se recuperan todos los vuelos 
		List<Flight> allFlights = new ArrayList<>();

		airAllianceServices.forEach(aA -> {
			aA.getAllFlights().forEach(f -> allFlights.add(f));
		});
		
		//Se invoca al método que realiza la búsqueda recursiva
		return recursiveSearch(origin, destination, allFlights, 2);
	}
		
	/**
	 * Devuelve una lista de itinerarios entre un origen y un destino. La generación
	 * de la lista se realiza con un método recursivo.
	 * @param origin String con el código del aeropuerto origen.
	 * @param destination String con el código del aeropuerto destino.
	 * @param flights List<Flight> con la lista de vuelos usados como referencia.
	 * @param max int con el número máximo de escalas.
	 * @return List<List<Flight>> con la lista de itinerarios de vuelos.
	 */
	private List<List<Flight>> recursiveSearch(String origin, String destination,
											   List<Flight> flights, int max) {                
        List<List<Flight>> result = new ArrayList<>();
		
        recursiveSearchAux(result, new ArrayList<>(), origin, destination, flights, max);
        
		return result;
    }

	/**
	 * Genera una lista de itinerarios entre un origen y un destino. La generación
	 * de la lista se realiza con un método recursivo.
	 * @param result List<List<Flight>> con la lista de itinerarios de vuelos.
	 * @param aux List<Flight> con una lista auxiliar para crear cada itinerario.
	 * @param origin String con el código del aeropuerto origen.
	 * @param destination String con el código del aeropuerto destino.
	 * @param allFlights List<Flight> con la lista de vuelos usados como referencia.
	 * @param max int con el número máximo de escalas.
	 */	
	private void recursiveSearchAux(List<List<Flight>> result, List<Flight> aux, 
									String origin, String destination,
									List<Flight> allFlights, int max) {				
		//CASO BASE 1: Se ha superado el número máximo de escalas
		if (aux.size() > max + 1) {
			return;
		}
		
		//CASO BASE 2: Destino del último vuelo en aux es 'destination' e itinerario no repetido
		if (!aux.isEmpty() && 
			aux.get(aux.size()-1).getDestination().getCode().equals(destination) && 
			!result.contains(aux)) {
			//Se añade el nuevo itinerario a la lista de resultados
			result.add(new ArrayList<>(aux));
			
		//CASO RECURSIVO: Se van añadiendo vuelos al itinerario aux  
		} else {
			//Se recorren los vuelos disponbles
			for (Flight flight : allFlights) {
				//Si el origen del vuelo actual es 'origin' y el vuelo no está en aux. 
				if (flight.getOrigin().getCode().equals(origin) && !aux.contains(flight)) {
					//Se añade el vuelo a aux
					aux.add(flight);
					//Se realiza la invocación recursiva: se modifica 'origin'
					recursiveSearchAux(result, 
									   aux,
									   flight.getDestination().getCode(),
									   destination,
									   allFlights,
									   max);
					//Se elimina de aux el último vuelo añadido
					aux.remove(aux.size()-1);
				}
			}
		}
	}
		
	/**
	 * Actualiza el JComboBos de aeropuertos origen.
	 * @param airports List<Airport> con los aeropuertos.
	 */
	private void updateOrigins(List<Airport> airports) {		
		this.jComboOrigin.removeAllItems();
		this.jComboOrigin.addItem("");		
		Collections.sort(airports);
		airports.forEach(a -> jComboOrigin.addItem(String.format("%s - %s (%s)", 
				a.getCode(), a.getName(), a.getCountry().getName())));		
	}

	/**
	 * Actualiza el JComboBos de aeropuertos destino.
	 * @param airports List<Airport> con los aeropuertos.
	 */
	private void updateDestinations(List<Airport> airports) {		
		this.jComboDestination.removeAllItems();
		this.jComboDestination.addItem("");
		Collections.sort(airports);
		airports.forEach(a -> jComboDestination.addItem(String.format("%s - %s (%s)", 
				a.getCode(), a.getName(), a.getCountry().getName())));		
	}
		
	/**
	 * Actualiza la JTable de vuelos.
	 */
	public void updateFlights() {
		//Se crea un comparador para ordenar los itinerarios por número de vuelos				
		Comparator<Flight> priceComparator = (f1, f2) -> {
			return Float.compare(f1.getPrice(), f2.getPrice());
		};
		
		//Se ordenan los vuelos por precio
		Collections.sort(flights, priceComparator);
		
		jTableFlights.setModel(new FlightsTableModel(flights));	
		
		//Se define el render para todas las columnas de la tabla excepto la dos últimas
		FlightRenderer defaultRenderer = new FlightRenderer();
		
		for (int i=0; i<jTableFlights.getColumnModel().getColumnCount()-2; i++) {
			jTableFlights.getColumnModel().getColumn(i).setCellRenderer(defaultRenderer);
		}

		//Se define el render y editor para las columnas de los botones		
		jTableFlights.getColumnModel().getColumn(9).setCellRenderer(new CheckInRendererEditor(this));
		jTableFlights.getColumnModel().getColumn(9).setCellEditor(new CheckInRendererEditor(this));
		jTableFlights.getColumnModel().getColumn(10).setCellRenderer(new BookRendererEditor(this));		
		jTableFlights.getColumnModel().getColumn(10).setCellEditor(new BookRendererEditor(this));		
		
		jLabelInfo.setText(String.format("%d vuelos", flights.size()));
		
		if (flights.isEmpty()) {
			jBtnRecursiveSearch.setEnabled(false);
		} else {
			jBtnRecursiveSearch.setEnabled(true);
		}
	}
	
	private void showAirportTransitRanking() {		
		//Se recuperan los vuelos de todos los servicios de las alianzas
		List<Flight> flights = new ArrayList<>();		
		airAllianceServices.forEach(alliance ->  
			alliance.getAllFlights().forEach(flight -> flights.add(flight))
		);
		
		//Se genera el Ranking de tránsito de pasajeros por aeropuerto
		List<Airport> ranking = Statistics.getAirportTransitRanking(flights);
		
		//Se muestra el resultado en cuadro de diálogo
		DefaultListModel<Airport> model = new DefaultListModel<>();		
		ranking.forEach(a -> model.addElement(a));		
		
		JList<Airport> jlistAirports = new JList<>(model);
		jlistAirports.setEnabled(false);
		
		JScrollPane scrollPane = new JScrollPane(jlistAirports);
		scrollPane.setPreferredSize(new Dimension(600, 350));
		
		JOptionPane.showMessageDialog(this, 
				scrollPane, 
				"Ranking de aeropuertos por tránsito de personas", 
				JOptionPane.PLAIN_MESSAGE, 
				new ImageIcon("resources/images/passenger.png"));	    
	}
	
	private void showAirlineOperationsData() {
		//Se recuperan los vuelos de todos los servicios de las alianzas
		List<Flight> flights = new ArrayList<>();
		
		airAllianceServices.forEach(aA -> {
			aA.getAllFlights().forEach(f -> flights.add(f));
		});
		
		//Se genera el mapa de operaciones de aerolíneas
		Map<Airline, List<Float>> operations = Statistics.getAirlineOperationsData(flights);

		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		Vector<String> header = new Vector<String>(Arrays.asList( "AEROLÍNEA", "INGRESOS", "PERSONAS", "ASIENTOS TOTALES"));
		
		operations.entrySet().forEach(entry -> {
			data.add(new Vector<Object>(Arrays.asList(entry.getKey().getCode(), entry.getValue().get(0), entry.getValue().get(1), entry.getValue().get(2))));
		});
		
		DefaultTableModel operationsModel = new DefaultTableModel(data, header);
		JTable jTableOperations = new JTable(operationsModel);
		jTableOperations.setEnabled(false);
		jTableOperations.setRowHeight(26);
		jTableOperations.setDefaultRenderer(Object.class, (table, value, isSelected, hasFocus, row, column) -> {
			JLabel label = new JLabel();
			label.setHorizontalAlignment(JLabel.CENTER);
			
			if (column == 0) {
				label.setIcon(new ImageIcon(String.format("resources/images/%s.png", (String) value)));
			} else if (column == 1) {
				label.setText(String.format("%,.2f €", (Float) value));
				label.setHorizontalAlignment(JLabel.RIGHT);
			} else if (column == 2 || column == 3) {
				label.setText(String.format("%,.0f", (Float) value));
			}
			
			label.setForeground(table.getForeground());
			label.setBackground(table.getBackground());
			
			label.setOpaque(true);
			
			
			return label;
		});
		
		JScrollPane scrollPane = new JScrollPane(jTableOperations);
		scrollPane.setPreferredSize(new Dimension(600, 350));
		
		JOptionPane.showMessageDialog(this, 
				scrollPane, 
				"Datos de operaciones de las aerolíneas", 
				JOptionPane.PLAIN_MESSAGE, 
				new ImageIcon("resources/images/passenger.png"));	    
	}
	
	/**
	 * Muestra un cuadro de diálogo con itinerarios de vuelos.
	 * @param origin String con el nombre del aeropuerto origen.
	 * @param destination String con el nombre del aeropuerto destino.
	 * @param itineraries List<List<Flight>> con los itinerarios.
	 */
	private void showItinerariesDialog(String origin, String destination, List<List<Flight>> itineraries) {
		//Se crea un comparador para ordenar los itinerarios por número de vuelos				
		Comparator<List<Flight>> itineraryLength = (list1, list2) -> {
			return Integer.compare(list1.size(), list2.size());
		};
		
		//Se crea un comparador para ordenar los itinerarios por precio				
		Comparator<List<Flight>> itineraryPrice = (list1, list2) -> {
			double price1 = 0;
			double price2 = 0;
			
			for (Flight f : list1) {
				price1 += f.getPrice();
			}

			for (Flight f : list2) {
				price2 += f.getPrice();
			}
			
			return Double.compare(price1, price2);
		};
			
		Collections.sort(itineraries, itineraryLength.thenComparing(itineraryPrice));
			
		//Los itinerarios se muestran en un JTree dento de un cuadro de diálogo
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(String.format("%02d Itinerarios", itineraries.size()));
		DefaultMutableTreeNode itineraryNode;
		
		float price;
		int duration;
		
		for (int i=0; i<itineraries.size(); i++) {
			price = 0;
			duration = 0;
			itineraryNode = new DefaultMutableTreeNode();
			rootNode.add(itineraryNode);
			
			for (Flight f : itineraries.get(i)) {
				itineraryNode.add(new DefaultMutableTreeNode(f));
				duration += f.getDuration();
				price += f.getPrice();						
			}					
			
			itineraryNode.setUserObject(String.format("%2d vuelos, %2d min., %.2f €", 
					itineraries.get(i).size(),
					duration,
					price));
		}

		JTree jTreeItineraies = new JTree(rootNode);
		
		jTreeItineraies.setCellRenderer(new DefaultTreeCellRenderer() {
			private static final long serialVersionUID = 1L;

			public Component getTreeCellRendererComponent(JTree tree, Object value, 
					boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
				super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
				
				if (leaf && value instanceof DefaultMutableTreeNode) {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
					
					if (node.getUserObject() instanceof Flight) { 
						Flight flight = (Flight) node.getUserObject();
						this.setIcon(new ImageIcon(String.format("resources/images/%s.png", flight.getAirline().getCode())));
					}
				}
								
				return this;
			}
		});
		
		JScrollPane scrollPane = new JScrollPane(jTreeItineraies);
		scrollPane.setPreferredSize(new Dimension(600, 300));
		
		JOptionPane.showMessageDialog(this, 
				scrollPane, 
				String.format("Itinerarios entre %s y %s", origin, destination), 
				JOptionPane.PLAIN_MESSAGE, 
				new ImageIcon("resources/images/confirm.png"));		    
	}
	
	/**
	 * Devuelve el servicio de la alianaza de aerolíneas que gestiona el vuelo.
	 * @param flight Flight con el vuelo.
	 * @return AirAllianceService con el servicio de la alianza de aerolíneas.
	 */
	protected AirAllianceService getService(Flight flight) {
		for (AirAllianceService service : airAllianceServices) {
			if (service.getAirAlliance().equals(flight.getAirline().getAlliance())) {
				return service;
			}
		}
		
		return null;
	}
	
	/**
	 * Crea una nueva tarjeta de embarque.
	 * @param reservation Reserva a la que se asocia la tarjeta de embarque.
	 * @param passenger String con el nombre de la persona
	 * @param seat int con el número de asiento
	 */
	protected void createBoardingPass(Reservation reservation, String passenger, int seat) {
		if (reservation != null && passenger != null && seat > 0) {	
			//Se crea una nueva tarjeta de embarque y se asocia a la Reserva
			BoardingPass boardingPass = new BoardingPass(reservation.getLocator(), passenger, seat);					
			reservation.addBoardingPass(boardingPass);
			//Se recupera el servicio de la alianza de aerolíneas del vuelo
			AirAllianceService service = getService(reservation.getFlight());
			//Se almacena la tarjeta de embarque
			service.storeBoardingPass(boardingPass);
			//Se imprime la tarjeta de embarque
			service.printBoardingPass(reservation, boardingPass);
		}
	}
	
	/**
	 * Método main
	 * @param args String[] con los argumentos.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new MainWindow());
	}
}