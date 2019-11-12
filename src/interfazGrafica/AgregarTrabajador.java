package interfazGrafica;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import observer.ObjetoObservable;
import observer.Observador;
import persona.Trabajador;
import reaccionEventos.ReaccionEventos;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class AgregarTrabajador extends JFrame implements ObjetoObservable{
	private static final long serialVersionUID = 1L;
	private List<Observador> observadores;
	private JPanel panel;
	private JTextField nombre, dni, edad;
	private JLabel lblNombre, lblDni, lblEdad;
	private Trabajador Trabajador;
	private JButton btnAceptar;
	private String puesto;

	public AgregarTrabajador(String p) {
		observadores = new ArrayList<Observador>();
		puesto = p;
		crearVentana();
		crearPanel();
		crearLabels();
		crearJTextFields();
		crearBoton();
		reaccionarAClick();
	}

	private void crearBoton() {
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(192, 116, 130, 23);
		panel.add(btnAceptar);
	}

	private void crearJTextFields() {
		nombre = new JTextField();
		nombre.setBounds(64, 11, 226, 20);
		nombre.setColumns(10);
		panel.add(nombre);

		nombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent tecla) {
				if (nombre.getText().length() == 8)
					ReaccionEventos.evitarTecla(tecla);
				else if (nombre.getText().length() == 0)
					ReaccionEventos.ignorarSiNoEsLetraMayuscula(tecla);
				else
					ReaccionEventos.ignorarSiNoEsLetraMinuscula(tecla);
			}
		});

		dni = new JTextField();
		dni.setBounds(64, 42, 226, 20);
		dni.setColumns(10);
		panel.add(dni);

		dni.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent tecla) {
				if (dni.getText().length() == 8)
					ReaccionEventos.evitarTecla(tecla);
				else
					ReaccionEventos.ignorarSiNoEsNumero(tecla);
			}
		});

		edad = new JTextField();
		edad.setBounds(64, 73, 226, 20);
		edad.setColumns(10);
		panel.add(edad);

		edad.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent tecla) {
				if (edad.getText().length() == 2)
					ReaccionEventos.evitarTecla(tecla);
				else
					ReaccionEventos.ignorarSiNoEsNumero(tecla);
			}
		});
	}

	private void crearLabels() {
		lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 11, 85, 20);
		panel.add(lblNombre);

		lblDni = new JLabel("DNI:");
		lblDni.setBounds(10, 42, 85, 20);
		panel.add(lblDni);

		lblEdad = new JLabel("Edad:");
		lblEdad.setBounds(10, 73, 85, 20);
		panel.add(lblEdad);
	}

	private void crearPanel() {
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(null);
		setContentPane(panel);
	}

	private void crearVentana() {
		setAutoRequestFocus(false);
		setTitle("Info");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 342, 200);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				cerrar();
				notificar();
			}
		});
	}

	public void cerrar() {
		dispose();
	}

	public void setCargo(String otro) {
		puesto = otro;
	}

	public Trabajador getTrabajador() {
		return Trabajador != null ? new Trabajador(Trabajador.getNombre(), Trabajador.getEdad(), Trabajador.getDNI(), puesto)
				: null;
	}

	private void reaccionarAClick() {
		btnAceptar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (verificarCampos()) {
					Trabajador = new Trabajador(nombre.getText(), Integer.valueOf(edad.getText()),
							Integer.valueOf(dni.getText()), puesto);
					notificar();
				} else
					JOptionPane.showMessageDialog(null, "Campos ingresados no validos");
			}
		});
	}

	private boolean verificarCampos() {
		return largoNombreValido() && rangoEdadValido() && rangoDNIValido();
	}

	private boolean rangoDNIValido() {
		try {
			return Integer.valueOf(dni.getText()) >= 9999999;
		} catch (Exception e) {
			return false;
		}
	}

	private boolean rangoEdadValido() {
		try {
			return Integer.valueOf(edad.getText()) >= 18 && Integer.valueOf(edad.getText()) <= 70;
		} catch (Exception e) {
			return false;
		}
	}

	private boolean largoNombreValido() {
		try {
			return nombre.getText().length() >= 3;
		} catch (Exception e) {
			return false;
		}
	}

	public void enlazar(Observador o) {
		observadores.add(o);
	}

	public void reiniciar() {
		dni.setText("");
		nombre.setText("");
		edad.setText("");
		Trabajador = null;
	}

	public void notificar() {
		for (Observador o : observadores)
			o.update();
	}

}
