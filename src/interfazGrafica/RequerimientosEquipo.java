package interfazGrafica;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javafx.util.Pair;
import persona.Trabajador;
import restriccion.Restriccion;
import selectorGrupo.Incompatibilidad;
import selectorGrupo.SelectorGrupo;
import java.awt.List;

public class RequerimientosEquipo extends JFrame {
	private List _integrantes, _incompatibilidades, _restricciones;
	private static final long serialVersionUID = 1L;
	private JPanel panel;

	private SelectorGrupo _selector;

	public RequerimientosEquipo(SelectorGrupo selector) {
		_selector = selector;

		crearVentana();
		crearPanel();
		crearListas();
	}

	private void crearListas() {
		_integrantes = new List();
		_integrantes.add("INTEGRANTES DEL EQUIPO:");
		_integrantes.setEnabled(false);
		_integrantes.setBounds(10, 10, 436, 128);

		for (Trabajador trabajador : _selector.getIntegrantes())
			_integrantes.add(trabajador.toString());

		panel.add(_integrantes);

		_incompatibilidades = new List();
		_incompatibilidades.add("INCOMPATIBLIDADES: ");
		_incompatibilidades.setEnabled(false);
		_incompatibilidades.setBounds(10, 144, 154, 87);
		for (Incompatibilidad incompatibilidad : _selector.getIncompatibilidades())
			_incompatibilidades.add("*" + incompatibilidad.primerTrabajador().getNombre() + " y "
					+ incompatibilidad.segundoTrabajador().getNombre() + " \n");

		panel.add(_incompatibilidades);

		_restricciones = new List();
		_restricciones.setEnabled(false);
		_restricciones.setBounds(170, 144, 276, 87);
		_restricciones.add("RESTRICCIONES POR PUESTO:");

		for (Pair<String, Restriccion> limite : _selector.restriccionesPorPuesto())
			_restricciones.add("Puesto: " + limite.getKey() + ", Minimo: " + limite.getValue().getMinimo()
					+ ", Maximo: " + limite.getValue().getMaximo() + "\n");

		panel.add(_restricciones);
	}

	private void crearPanel() {
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panel);
		panel.setLayout(null);
	}

	private void crearVentana() {
		setTitle("Requerimientos");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 468, 277);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				cerrar();
			}
		});
	}

	private void cerrar() {
		dispose();
	}
}
