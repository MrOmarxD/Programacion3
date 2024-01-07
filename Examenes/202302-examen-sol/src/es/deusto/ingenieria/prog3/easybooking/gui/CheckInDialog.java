package es.deusto.ingenieria.prog3.easybooking.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellRenderer;

import es.deusto.ingenieria.prog3.easybooking.domain.Flight;
import es.deusto.ingenieria.prog3.easybooking.domain.Reservation;

public class CheckInDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JTable jTablePassengers = new JTable();	
	private JButton jButtonConfirm = new JButton("Aceptar");
			
	public CheckInDialog(MainWindow mainWindow, Flight flight) {
		final CheckInDialog dialog = this;
		jButtonConfirm.addActionListener(e -> dialog.dispose());
		
		jTablePassengers = new JTable(new CheckInTableModel(flight));
		jTablePassengers.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		jTablePassengers.setFillsViewportHeight(true);
		
		//T5.A Crear un renderer personalizado para la tabla de personas
		//Crea un Rederer personalizado para la tabla de personas:
		//- Poner en color RGB(255, 113, 91) el texto y fondo blanco de las filas sin asiento asignado.
		//- Poner en color RGB(23, 137, 252) el fondo y texto blanco la fila seleccionada
		//  (tanto si tiene asiento asignado como si no lo tiene.
		//La columna LOCALIZADOR debe contener el localizador de la reserva y estar centrada.
		//El número de asiento debe aparecer centrado y con 3 dígitos (001 ó 045).
		
		TableCellRenderer passengerRenderer = (table, value, isSelected, hasFocus, row, column) -> {
			JLabel label = new JLabel();
			
			//El número de asiento se rendereiza como un entero de 3 posicones centrado.
			if (column == 2) {
				if (!value.toString().contains("-")) {
					label.setText(String.format("%03d", Integer.valueOf(value.toString())));
				} else {
					label.setText(value.toString());
				}
				
				label.setHorizontalAlignment(JLabel.CENTER);					
			//El localizado se rendereiza centrado
			} else if (column == 0) {
				label.setText(((Reservation) value).getLocator());
				label.setHorizontalAlignment(JLabel.CENTER);
			//El nombre se renderiza por defecto
			} else {
				label.setText(value.toString());
			}
			
			//Se establecen los colores de letra y fondo en caso de que la fila esté seleccionada
			if (isSelected) {
				label.setBackground(new Color(23, 137, 252));
				label.setForeground(Color.WHITE);
			} else {
				String seat = table.getValueAt(row, 2).toString();
				
				if (seat.contains("-")) {
					label.setForeground(new Color(255, 113, 91));
					label.setBackground(Color.WHITE);
				} else {
					label.setForeground(table.getForeground());
					label.setBackground(table.getBackground());
				}				
			}			
			
			label.setOpaque(true);
			return label;
		};
		
		//Se asigna el Rederer personalizado a la tabla de pasajeros
		jTablePassengers.setDefaultRenderer(Object.class, passengerRenderer);
		
		//T5.B Pulsa F10 para asignar el asiento a una persona
		// Añade la funcionalidad necesaria para que cuando esté releccionada una persona
		//y se pulse la tecla F10 se abra un cuadro de diálogo para asignar el número de 
		//asiento. La lista desplegable de asientos sólo contendrá los asientos libres.
		//Además, al pulsar el botón aceptar del nuevo cuadro de diálogo, se invocará 
		//al método createBoardingPass(Reservation reservation, String passenger, int seat)
		//que se encuentra en la clase MainWindow.java.
		
		//Se añade a la tabla el evento de la tecla F10
		jTablePassengers.addKeyListener((KeyListener) new KeyAdapter() {
			@Override			
			public void keyReleased(KeyEvent e) {				
				//Se estaba pulsado ALT y la tecla F1 y la persona no tiene asiento asignado
				if (e.getKeyCode() == KeyEvent.VK_F10 && 
					jTablePassengers.getValueAt(jTablePassengers.getSelectedRow(), 2).toString().contains("-")) {
					//Se crea una lista con los números de asiento del avión
					List<Integer> freeSeats = IntStream.range(1, flight.getPlane().getSeats()).boxed().collect(Collectors.toList());
					//Se recorren las tarjetas de embarque y se eliminan los asientos ocupados					
					flight.getReservations().forEach(reservation -> {
						reservation.getBoardingPasses().forEach(pass -> {
							freeSeats.remove(Integer.valueOf(pass.getSeat()));
						});
					});

					//Se recupera el nombre del pasajero
					String name = jTablePassengers.getValueAt(jTablePassengers.getSelectedRow(), 1).toString();
					//Se crea el JComboBox con los asientos libres
					JComboBox<Integer> jComboBoxSeats = new JComboBox<>(new Vector<>(freeSeats));
					//Se iniciliza el array de componentes para el ciadro de diálogo
					JComponent[] inputs = new JComponent[] {
						new JLabel(String.format("Número de asiento para '%s':", name)),
						jComboBoxSeats,
					};					
					
					//Se muestra un cuadro de diálogo para introducir nombre y apellidos
					int result = JOptionPane.showConfirmDialog(dialog, inputs, 
										String.format("Selección del asiento para - %s", name), 
										JOptionPane.OK_CANCEL_OPTION,
										JOptionPane.PLAIN_MESSAGE, new ImageIcon("resources/images/passenger.png"));
					
					//Si se pulsa aceptar, se crea la tarjeta de embarque
					if (result == JOptionPane.OK_OPTION) {
						int seat = Integer.valueOf(jComboBoxSeats.getSelectedItem().toString());
						Reservation reservation = (Reservation) jTablePassengers.getValueAt(jTablePassengers.getSelectedRow(), 0);
						mainWindow.createBoardingPass(reservation, name, seat);
						
						//Se actualiza la información de la tabla de personas
						jTablePassengers.setModel(new CheckInTableModel(flight));
						
					}
				}
			}			
		});
		
		JPanel jPanelFlight = new JPanel();
		jPanelFlight.setBorder(new TitledBorder("Datos del vuelo"));
		jPanelFlight.setLayout(new GridLayout(3, 1));

		JLabel jLabelFlight = new JLabel(String.format("- %s", flight.getCode()));
		jLabelFlight.setIcon(new ImageIcon(String.format("resources/images/%s.png", flight.getAirline().getCode())));
		
		jPanelFlight.add(jLabelFlight);
		jPanelFlight.add(new JLabel(String.format("Origen: %s - %s", flight.getOrigin().getCode(), flight.getOrigin().getName())));
		jPanelFlight.add(new JLabel(String.format("Destino: %s - %s", flight.getDestination().getCode(), flight.getDestination().getName())));
				
		JPanel jPanelButtons = new JPanel();
		jPanelButtons.add(jButtonConfirm);
		
		add(jPanelFlight, BorderLayout.NORTH);
		add(new JScrollPane(jTablePassengers), BorderLayout.CENTER);
		add(jPanelButtons, BorderLayout.SOUTH);
		
		setTitle(String.format("Check-In del vuelo '%s'", flight.getCode()));		
		setIconImage(new ImageIcon("resources/images/tickets.png").getImage());		
		setSize(500, 700);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}