package interfazGrafica;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import grupo.Grupo;
import memento.CareTaker;
import memento.Originator;
import observer.Observador;
import persona.Trabajador;
import selectorGrupo.SelectorGrupo;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import java.awt.List;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Font;

public class VentanaPrincipal extends JFrame implements Observador {
	private static final long serialVersionUID = 1L;
	private List TrabajadoresACargar, incompatibles;
	private JPanel panel;
	private Grupo grupo;
	private SelectorGrupo selector;
	private AgregarTrabajador ventanaAgregarTrabajador;
	private CargarRestricciones ventanaCargarRestricciones;
	private Trabajador[] trabajadoresSeleccionados;
	private JMenuItem[] puestosPosibles;
	private CareTaker careTaker;
	private Originator originator;

	VentanaPrincipal() throws FileNotFoundException {
		grupo = new Grupo();
		selector = new SelectorGrupo(grupo);
		careTaker = new CareTaker();
		careTaker = careTaker.traerCareDArch();

		trabajadoresSeleccionados = new Trabajador[2];
		crearVentanaActual();
		crearVentanaEmergente();
		crearPanel();
		crearLista();
		crearMenu();
		agregarComponentes();
	}
	
	private void crearVentanaActual() {
		setTitle("Equipo Ideal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 506, 401);
		getContentPane().setLayout(null);
	}
	
	private void crearVentanaEmergente() {
		ventanaAgregarTrabajador = new AgregarTrabajador("");
		ventanaAgregarTrabajador.enlazar(this);
	}
	
	private void crearPanel() {
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(null);
		setContentPane(panel);
	}
	
	private void crearLista() {
		TrabajadoresACargar = new List();
		TrabajadoresACargar.setBounds(10, 24, 274, 278);
		panel.add(TrabajadoresACargar);

		incompatibles = new List();
		incompatibles.setBounds(290, 75, 188, 53);
		panel.add(incompatibles);
	}
	
	private void crearMenu() {
		JMenuBar menubar = new JMenuBar();
		setJMenuBar(menubar);

		JMenu menu = new JMenu("Nuevo Trabajador");
		menubar.add(menu);

		puestosPosibles = agregarItemsPuestosRequeridos();

		for (JMenuItem puesto : puestosPosibles) {
			menu.add(puesto);
			puesto.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					mostrarVentana(puesto.getText());
				}
			});
		}
	}
	
	private void mostrarVentana(String texto) {
		ventanaAgregarTrabajador.setCargo(texto);
		ventanaAgregarTrabajador.setVisible(true);
	}
	
	private void agregarComponentes() {
		JLabel listaTrabajadores = new JLabel("Trabajadores Ingresadas:");
		listaTrabajadores.setFont(new Font("Tahoma", Font.BOLD, 13));
		listaTrabajadores.setBounds(64, 0, 220, 18);
		panel.add(listaTrabajadores);

		crearBotonResolver();
		crearBotonNuevaIncopatibilidad();
		crearBotonCargarIncompatibles();
		crearBotonEliminarIncopatibilidad();
		crearBotonReiniciarLista();
		crearBotonCargarRestricciones();
		crearBotonesHistorial();
		crearBotonVerRequerimientos();
	}
	
	private void crearBotonResolver() {
		JButton agregarLista = new JButton("Resolver");
		agregarLista.setFont(new Font("Tahoma", Font.BOLD, 12));
		agregarLista.setBounds(10, 307, 274, 23);
		panel.add(agregarLista);

		agregarLista.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (selector.tamano() >= 2) {
					selector.start();
					try {
						selector.join();
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
					Grupo resultante = selector.getGrupoResultante();
					originator = new Originator(resultante);
					originator.setEstado(resultante);
					careTaker.addMemento(originator.guardarAMemento());
					careTaker.guardar();
					if (resultante.tamano() > 0)
						JOptionPane.showMessageDialog(null, "Los integrantes del grupo son: \n" + resultante);
					else
						JOptionPane.showMessageDialog(null, "El grupo obtenido no contiene integrantes");
				} else
					JOptionPane.showMessageDialog(null, "Largo de la lista actual no admitido");
			}
		});
	}
	
	private void crearBotonNuevaIncopatibilidad() {
		JButton nuevaIncompatibilidad = new JButton("Seleccione Incompatible");

		nuevaIncompatibilidad.setBounds(290, 46, 188, 23);
		panel.add(nuevaIncompatibilidad);

		nuevaIncompatibilidad.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (selector.tamano() > 0)
					agregarSeleccionado();
				else
					JOptionPane.showMessageDialog(null, "No hay Trabajadores para seleccionar");
			}
		});
	}
	
	private void crearBotonCargarIncompatibles() {
		selector.setGrupo(grupo);
		JButton cargar = new JButton("Cargar Incompatibles");
		cargar.setBounds(290, 134, 188, 23);
		panel.add(cargar);

		cargar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (trabajadoresSeleccionados[0] != null && trabajadoresSeleccionados[1] != null) {
					String mostrar = trabajadoresSeleccionados[0].getNombre() + " con "
							+ trabajadoresSeleccionados[1].getNombre() + "?";

					int opcion = JOptionPane.showConfirmDialog(null, "Desea agregar a: " + mostrar);

					if (opcion == 0) {
						selector.agregarIncompatibilidad(trabajadoresSeleccionados[0], trabajadoresSeleccionados[1]);
						reiniciarIncompatibles();
						JOptionPane.showMessageDialog(null, "La incompatibilidad se ha agregado correctamente");
					}
				} else {
					if (selector != null)
						JOptionPane.showMessageDialog(null, "La cantidad de elementos ingresados debe ser 2");
					else
						JOptionPane.showMessageDialog(null, "Aun no ha agregado la lista!");
				}
			}
		});
	}
	
	private JMenuItem[] agregarItemsPuestosRequeridos() {
		puestosPosibles = new JMenuItem[SelectorGrupo.obtenerPosiblesPuestos().size()];
		java.util.List<String> obtenidos = SelectorGrupo.obtenerPosiblesPuestos();
		for (int i = 0; i < puestosPosibles.length; i++) {
			puestosPosibles[i] = new JMenuItem(obtenidos.get(i));
		}
		return puestosPosibles;
	}

	private void crearBotonCargarRestricciones() {
		JButton cargarRestricciones = new JButton("Cargar Restricciones");
		cargarRestricciones.setBounds(290, 26, 188, 23);
		panel.add(cargarRestricciones);

		cargarRestricciones.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (grupo.tamano() > 0) { // CAMBIADO
					ventanaAgregarTrabajador = null;
					crearVentanaRestricciones();
				} else
					JOptionPane.showMessageDialog(null, "No hay datos para cargar");
			}
		});
	}
	

	private void crearBotonReiniciarLista() {
		JButton reiniciarLista = new JButton("Reiniciar Lista");
		reiniciarLista.setBounds(290, 308, 188, 23);
		panel.add(reiniciarLista);
		reiniciarLista.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				for (String s : TrabajadoresACargar.getItems()) {
					TrabajadoresACargar.remove(s);
				}
				grupo = new Grupo();
				selector = new SelectorGrupo(grupo);
			}
		});
	}

	private void crearBotonesHistorial() {
		crearBotonVerHistorial();
		crearBotonNumeroABorrar();
		crearBotonBorrarHistorial();
	}

	private void crearBotonBorrarHistorial() {
		JButton borrarHistorial = new JButton("Eliminar Historial");
		borrarHistorial.setBounds(290, 270, 188, 23);
		panel.add(borrarHistorial);
		borrarHistorial.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int n = JOptionPane.showConfirmDialog(null,
						"Si elimina el historial no podra ver grupos \n armados anteriormente",
						"Desea eliminar el historial?", JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					careTaker.borrarHistorial();
				} else if (n == JOptionPane.NO_OPTION) {
					return;
				}

			}
		});
	}

	private void crearBotonNumeroABorrar() {
		JTextField numeroABorrar;
		numeroABorrar = new JTextField();
		numeroABorrar.setBounds(431, 237, 47, 23);
		numeroABorrar.setColumns(10);
		panel.add(numeroABorrar);
		JButton borrarDeHistorial = new JButton("Borrar Grupo N\u00B0:");
		borrarDeHistorial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Integer temp = Integer.parseInt(numeroABorrar.getText());
					careTaker.borrarDeHistorial(temp - 1);
					JOptionPane.showMessageDialog(null, "Grupo N°: " + temp + " borrado exitosamente");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Inserte un numero de grupo valido");
				}

			}
		});

		borrarDeHistorial.setBounds(290, 237, 143, 23);
		panel.add(borrarDeHistorial);
	}

	private JButton crearBotonVerHistorial() {
		JButton verHistorial = new JButton("Historial");
		verHistorial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				crearFrameHistorial();
			}
		});
		verHistorial.setBounds(290, 216, 188, 23);
		panel.add(verHistorial);
		return verHistorial;
	}
	
	private void crearFrameHistorial() {
		JFrame frmprincipal;
		frmprincipal = new JFrame();
		frmprincipal.setSize(500, 500);
		frmprincipal.setTitle("Historial");
		JScrollPane scroll;
		JTextArea box = new JTextArea();
		try {
			box.setText(careTaker.traerCareDArch().toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		scroll = new JScrollPane(box);
		scroll.setBounds(new Rectangle(30, 30, 400, 400));
		frmprincipal.setVisible(true);
		frmprincipal.getContentPane().add(scroll);
	}

	private void crearVentanaRestricciones() {
		selector.setGrupo(grupo);
		ventanaCargarRestricciones = new CargarRestricciones(selector.valoresLimitePorPuesto());
		ventanaCargarRestricciones.setVisible(true);
		ventanaCargarRestricciones.enlazar(this);
	}

	private void crearBotonEliminarIncopatibilidad() {
		JButton eliminarIncompatible = new JButton("Eliminar Seleccionado");
		eliminarIncompatible.setBounds(290, 155, 188, 22);
		panel.add(eliminarIncompatible);

		eliminarIncompatible.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (selector.tamano() > 0)
					eliminarSeleccionado();
				else
					JOptionPane.showMessageDialog(null, "No hay trabajadores para seleccionar");
			}
		});
	}

	private void crearBotonVerRequerimientos() {
		JButton requerimientos = new JButton("Ver Requerimientos");
		requerimientos.setFont(new Font("Tahoma", Font.PLAIN, 11));
		requerimientos.setBounds(288, 188, 190, 23);
		panel.add(requerimientos);
		requerimientos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (selector.tamano() > 0) {
					RequerimientosEquipo ventanaRequerimientos = new RequerimientosEquipo(selector);
					ventanaRequerimientos.setVisible(true);
				} else
					JOptionPane.showMessageDialog(null, "El grupo aún no contiene integrantes para mostrar");

			}
		});
	}

	private void reiniciarIncompatibles() {
		for (int i = 0; i < trabajadoresSeleccionados.length; i++)
			trabajadoresSeleccionados[i] = null;
		for (String s : incompatibles.getItems())
			incompatibles.remove(s);
	}

	@Override
	public void update() {
		if (ventanaAgregarTrabajador != null) {
			if (existeTrabajadorEnVentanaEmergente() && !grupo.existe(ventanaAgregarTrabajador.getTrabajador())) {
				mostrarTrabajadorEnLaLista();
				agregarAlGrupo();
				ventanaAgregarTrabajador.reiniciar();
				ventanaAgregarTrabajador.cerrar();
			}
		} else if (ventanaCargarRestricciones != null) {
			if (ventanaCargarRestricciones.getRestricciones().size() == selector.cantidadPuestos()) {
				selector.setRestricciones(ventanaCargarRestricciones.getRestricciones());
				JOptionPane.showMessageDialog(null, "Las restricciones se han agregado exitosamente");
				ventanaCargarRestricciones.cerrar();
				ventanaCargarRestricciones = null;
			} else {
				ventanaCargarRestricciones.reiniciarLista();
				JOptionPane.showMessageDialog(null, "Datos ingresados invalidos");
			}
		}

	}

	private void agregarAlGrupo() {
		grupo.agregar(ventanaAgregarTrabajador.getTrabajador());
	}

	private void mostrarTrabajadorEnLaLista() {
		TrabajadoresACargar.add(ventanaAgregarTrabajador.getTrabajador().toString());
	}

	private boolean existeTrabajadorEnVentanaEmergente() {
		return ventanaAgregarTrabajador.getTrabajador() != null;
	}

	private void agregarSeleccionado() {
		if (trabajadoresSeleccionados[0] == null && selector.tamano() > 0) {
			trabajadoresSeleccionados[0] = grupo.obtener(TrabajadoresACargar.getSelectedIndex()); 
			incompatibles.add(trabajadoresSeleccionados[0].toString());
		} else if (trabajadoresSeleccionados[1] == null && selector.tamano() > 0) {
			trabajadoresSeleccionados[1] = grupo.obtener(TrabajadoresACargar.getSelectedIndex());
			incompatibles.add(trabajadoresSeleccionados[1].toString());
		}
	}

	private void eliminarSeleccionado() {
		trabajadoresSeleccionados[incompatibles.getSelectedIndex()] = null;
		incompatibles.remove(incompatibles.getSelectedIndex());
	}
}
