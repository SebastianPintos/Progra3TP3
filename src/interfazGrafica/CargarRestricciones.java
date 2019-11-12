package interfazGrafica;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javafx.util.Pair;
import observer.ObjetoObservable;
import observer.Observador;
import reaccionEventos.ReaccionEventos;
import restriccion.Restriccion;

import javax.swing.JLabel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JButton;

public class CargarRestricciones extends JFrame implements ObjetoObservable{
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JTextField[] campos;
	private JLabel[] _puestos;
	private List<Pair<String, Integer>> limitesPorPuesto;
	private List<Pair<String, Restriccion>> restricciones;
	private List<Observador> observadores;

	public CargarRestricciones(List<Pair<String, Integer>> valoresLimitePorPuesto) {
		limitesPorPuesto = valoresLimitePorPuesto;
		observadores = new ArrayList<Observador>();
		restricciones = new ArrayList<Pair<String, Restriccion>>();
		campos = new JTextField[limitesPorPuesto.size() * 2];
		_puestos = new JLabel[limitesPorPuesto.size()];
		caracteristicasFrame();
		crearPanel();
		crearLabelsMinimoYMaximo();
		agregarCamposAlFrame();
		limitarIngresoTeclasInvalidas();
		agregarReaccionBotonAceptar();
	}

	private void agregarReaccionBotonAceptar() {
		JButton aceptar = new JButton("Aceptar");
		aceptar.setBounds(10, 182, 363, 23);
		panel.add(aceptar);
		aceptar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				actualizarLista();
				notificar();
			}
		});
	}

	private void limitarIngresoTeclasInvalidas() {
		for (JTextField campo : campos) {
			campo.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent l) {
					ReaccionEventos.ignorarSiNoEsNumero(l);
				}
			});
		}
	}
	
	public void reiniciarLista() {
		restricciones.clear();
	}
	
	private void crearLabelsMinimoYMaximo() {
		JLabel minimo = new JLabel("Minimo");
		minimo.setBounds(143, 20, 42, 19);
		panel.add(minimo);

		JLabel maximo = new JLabel("Maximo");
		maximo.setBounds(261, 20, 49, 19);
		panel.add(maximo);
	}

	private void agregarCamposAlFrame() {
		int x = 10;
		int y = 40;
		int ancho = 80;
		int alto = 30;
		int auxX = x;
		int j = 0;

		for (int i = 0; i < limitesPorPuesto.size(); i++) {
			_puestos[i] = new JLabel(limitesPorPuesto.get(i).getKey() + ":");
			_puestos[i].setBounds(x, y, ancho, alto);
			auxX = x;
			auxX += 100;
			campos[j] = new JTextField("0");
			panel.add(campos[j]);
			campos[j].setBounds(auxX, y, 110, 25);
			auxX += 120;
			j++;
			campos[j] = new JTextField("0");
			panel.add(campos[j]);
			campos[j].setBounds(auxX, y, 110, 25);
			j++;
			y += 30;
			panel.add(_puestos[i]);
		}
	}

	private void crearPanel() {
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(null);
		setContentPane(panel);
	}

	private void caracteristicasFrame() {
		setTitle("Restricciones");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 394, 255);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				cerrar();
			}
		});
	}

	public void enlazar(Observador observador) {
		observadores.add(observador);
	}

	public void notificar() {
		for (Observador o : observadores)
			o.update();
	}

	public void cerrar() {
		dispose();
	}

	public List<Pair<String, Restriccion>> getRestricciones() {
		return clonarRestricciones();
	}

	private List<Pair<String, Restriccion>> clonarRestricciones() {
		List<Pair<String, Restriccion>> ret = new ArrayList<Pair<String, Restriccion>>();
		for (Pair<String, Restriccion> restriccion : restricciones)
			ret.add(restriccion);
		return ret;
	}

	private void actualizarLista() {
		int z = 0;
		for (int i = 0; i < limitesPorPuesto.size(); i++) {
			
			int minimo = Integer.valueOf(campos[z++].getText());
			int maximo = Integer.valueOf(campos[z++].getText());

			Restriccion r = new Restriccion(limitesPorPuesto.get(i).getValue());
				if (r.esValida(minimo, maximo)) {
				r.set(minimo, maximo);
				restricciones.add(new Pair<String, Restriccion>(limitesPorPuesto.get(i).getKey(), r));
			}
		}
	}
}
