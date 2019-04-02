package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

/**
 * @author Software Engineering teachers
 */
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import businessLogic.BLFacade;
import dataAccess.DataAccess;
import domain.User;
import com.toedter.calendar.JCalendar;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import javax.swing.JPasswordField;

public class RegisterGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonCreateQuery = null;

	private static BLFacade appFacadeInterface;

	public static BLFacade getBusinessLogic() {
		return appFacadeInterface;
	}

	public static void setBussinessLogic(BLFacade afi) {
		appFacadeInterface = afi;
	}

	protected JLabel jLabelSelectOption;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textField;
	private JLabel lblApellidos;
	private JLabel lblEmail;
	private JLabel lblFechaDeNacimiento;
	private JLabel lblDni;
	private JLabel lblPassword;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_4;

	private DataAccess businessLogic;
	private JLabel label_2;
	private final JButton jButtonClose = new JButton("Close");
	
	private final JCalendar jCalendar = new JCalendar();

	protected Calendar calendarMio;

	protected Date choosenDate;
	private JPasswordField passwordField;

	/**
	 * This is the default constructor
	 */
	public RegisterGUI() {
		super();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					// if (ConfigXML.getInstance().isBusinessLogicLocal())
					// facade.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out
							.println("Error: "
									+ e1.toString()
									+ " , probably problems with Business Logic or Database");
				}
				System.exit(1);
			}
		});

		initialize();
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		// this.setSize(271, 295);
		this.setSize(430, 513);
		this.setContentPane(getJContentPane());
		this.setTitle("Register");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getLblNewLabel());
			jContentPane.add(getBoton2());

			JLabel lblNombre = new JLabel(
					"Name");
			lblNombre.setBounds(55, 74, 86, 15);
			jContentPane.add(lblNombre);
			jContentPane.add(getLblApellidos());
			jContentPane.add(getLblEmail());
			jContentPane.add(getLblFechaDeNacimiento());
			jContentPane.add(getLblDni());
			jContentPane.add(getLblPassword());

			textField = new JTextField();
			textField.setText("");
			textField.setBounds(174, 74, 132, 20);
			jContentPane.add(textField);
			textField.setColumns(10);
			jContentPane.add(getTextField_1());
			jContentPane.add(getTextField_2());
			jContentPane.add(getTextField_4());
			jContentPane.add(getLabel_2());
			jButtonClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					jButtonClose_actionPerformed(arg0);
				}
			});
			
			
			jButtonClose.setBounds(211, 411, 92, 40);
			jContentPane.add(jButtonClose);
			
			
			jCalendar.setBounds(new Rectangle(40, 50, 225, 150));
			jCalendar.setBounds(174, 167, 202, 123);
			jContentPane.add(jCalendar);
			
			passwordField = new JPasswordField();
			passwordField.setBounds(174, 339, 132, 20);
			jContentPane.add(passwordField);

			// Code for JCalendar
			jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent propertychangeevent) {
					if (propertychangeevent.getPropertyName().equals("locale")) {
						jCalendar.setLocale((Locale) propertychangeevent
								.getNewValue());
					} else if (propertychangeevent.getPropertyName().equals(
							"calendar")) {
						calendarMio = (Calendar) propertychangeevent.getNewValue();
						choosenDate = calendarMio.getTime();
						jCalendar.setCalendar(calendarMio);

					}
				}
			});

		}
		return jContentPane;
	}
	

	/**
	 * This method initializes boton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBoton2() {
		if (jButtonCreateQuery == null) {
			jButtonCreateQuery = new JButton();
			jButtonCreateQuery.setBounds(85, 411, 100, 40);
			jButtonCreateQuery
					.setText(ResourceBundle
							.getBundle("Etiquetas").getString("RegisterGUI.jButtonCreateQuery.text")); //$NON-NLS-1$ //$NON-NLS-2$
			jButtonCreateQuery
					.addActionListener(new java.awt.event.ActionListener() {
						@Override
						public void actionPerformed(java.awt.event.ActionEvent e) {
							User u = new User(textField_4.getText(),
									textField.getText(), textField_1.getText(),
									choosenDate, 0.0,
									"Client", textField_2.getText(),
									passwordField.getText());

							BLFacade facade = LoginGUI.getBusinessLogic();
							facade.createUser(u);
							label_2.setText("Cuenta creada");

						}
					});
		}
		return jButtonCreateQuery;
	}

	private JLabel getLblNewLabel() {
		if (jLabelSelectOption == null) {
			jLabelSelectOption = new JLabel(ResourceBundle.getBundle(
					"Etiquetas").getString("SelectOption"));
			jLabelSelectOption.setBounds(0, 1, 306, 62);
			jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
			jLabelSelectOption.setForeground(Color.BLACK);
			jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jLabelSelectOption;
	}

	private void redibujar() {
		jLabelSelectOption.setText(ResourceBundle.getBundle("Etiquetas")
				.getString("SelectOption"));
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas")
				.getString("CreateQuery"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString(
				"MainTitle"));
	}

	private JLabel getLblApellidos() {
		if (lblApellidos == null) {
			lblApellidos = new JLabel(
					ResourceBundle
							.getBundle("Etiquetas").getString("RegisterGUI.lblApellidos.text")); //$NON-NLS-1$ //$NON-NLS-2$
			lblApellidos.setBounds(55, 105, 72, 14);
		}
		return lblApellidos;
	}

	private JLabel getLblEmail() {
		if (lblEmail == null) {
			lblEmail = new JLabel(
					ResourceBundle
							.getBundle("Etiquetas").getString("RegisterGUI.lblEmail.text")); //$NON-NLS-1$ //$NON-NLS-2$
			lblEmail.setBounds(55, 136, 46, 14);
		}
		return lblEmail;
	}

	private JLabel getLblFechaDeNacimiento() {
		if (lblFechaDeNacimiento == null) {
			lblFechaDeNacimiento = new JLabel(
					ResourceBundle
							.getBundle("Etiquetas").getString("RegisterGUI.lblFechaDeNacimiento.text")); //$NON-NLS-1$ //$NON-NLS-2$
			lblFechaDeNacimiento.setBounds(55, 167, 99, 14);
		}
		return lblFechaDeNacimiento;
	}

	private JLabel getLblDni() {
		if (lblDni == null) {
			lblDni = new JLabel(
					ResourceBundle
							.getBundle("Etiquetas").getString("RegisterGUI.lblDni.text")); //$NON-NLS-1$ //$NON-NLS-2$
			lblDni.setBounds(65, 311, 46, 14);
		}
		return lblDni;
	}

	private JLabel getLblPassword() {
		if (lblPassword == null) {
			lblPassword = new JLabel(
					"Password");
			lblPassword.setBounds(65, 342, 104, 14);
		}
		return lblPassword;
	}

	private JTextField getTextField_1() {
		if (textField_1 == null) {
			textField_1 = new JTextField();
			textField_1.setText((String) null);
			textField_1.setColumns(10);
			textField_1.setBounds(174, 105, 132, 20);
		}
		return textField_1;
	}

	private JTextField getTextField_2() {
		if (textField_2 == null) {
			textField_2 = new JTextField();
			textField_2.setText((String) null);
			textField_2.setColumns(10);
			textField_2.setBounds(174, 136, 132, 20);
		}
		return textField_2;
	}

	private JTextField getTextField_4() {
		if (textField_4 == null) {
			textField_4 = new JTextField();
			textField_4.setText((String) null);
			textField_4.setColumns(10);
			textField_4.setBounds(174, 308, 132, 20);
		}
		return textField_4;
	}

	private Date newDate(int year, int month, int day) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day, 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	public void setBusinessLogic(DataAccess d) {
		businessLogic = d;
	}

	private JLabel getLabel_2() {
		if (label_2 == null) {
			label_2 = new JLabel(
					ResourceBundle
							.getBundle("Etiquetas").getString("RegisterGUI.label_2.text")); //$NON-NLS-1$ //$NON-NLS-2$
			label_2.setForeground(Color.RED);
			label_2.setBounds(114, 380, 150, 20);
		}
		return label_2;
	}
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
} // @jve:decl-index=0:visual-constraint="0,0"
