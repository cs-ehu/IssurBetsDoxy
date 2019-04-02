package gui;

/**
 * @author Software Engineering teachers
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import businessLogic.BLFacade;
import domain.User;
import javax.swing.JPasswordField;

public class LoginGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private static BLFacade appFacadeInterface;

	public static BLFacade getBusinessLogic() {
		return appFacadeInterface;
	}

	public static void setBussinessLogic(BLFacade afi) {
		appFacadeInterface = afi;
	}

	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField dni;
	private JPasswordField pass;

	/**
	 * This is the default constructor
	 */
	public LoginGUI() {
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
		this.setSize(495, 319);
		this.setContentPane(getJContentPane());
		this.setTitle("Login");
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

			JLabel lblNumeroDni = new JLabel(ResourceBundle.getBundle(
					"Etiquetas").getString("LoginGUI.lblNumeroDni.text")); //$NON-NLS-1$ //$NON-NLS-2$
			lblNumeroDni.setBounds(60, 69, 105, 14);
			jContentPane.add(lblNumeroDni);

			JLabel lblContrasea = new JLabel(ResourceBundle.getBundle(
					"Etiquetas").getString("LoginGUI.lblContrasea.text")); //$NON-NLS-1$ //$NON-NLS-2$
			lblContrasea.setBounds(60, 120, 105, 14);
			jContentPane.add(lblContrasea);

			dni = new JTextField();
			dni.setText("");
			dni.setBounds(207, 66, 153, 17);
			jContentPane.add(dni);
			dni.setColumns(10);

			final JLabel JErrorLabel = new JLabel("");
			JErrorLabel.setForeground(Color.RED);
			JErrorLabel.setBounds(297, 150, 147, 36);
			jContentPane.add(JErrorLabel);

			JLabel lblLogin = new JLabel(ResourceBundle
					.getBundle("Etiquetas").getString("LoginGUI.lblLogin.text")); //$NON-NLS-1$ //$NON-NLS-2$
			lblLogin.setFont(new Font("Tahoma", Font.BOLD, 22));
			lblLogin.setBounds(180, 11, 81, 38);
			jContentPane.add(lblLogin);

			JButton loginButton = new JButton(ResourceBundle.getBundle(
					"Etiquetas").getString("LoginGUI.btnNewButton.text")); //$NON-NLS-1$ //$NON-NLS-2$
			loginButton.addActionListener(new ActionListener() {
				@Override
				// BOTON LOGIN
				public void actionPerformed(ActionEvent e) {
					BLFacade facade = LoginGUI.getBusinessLogic();
					User user = facade.getUser(dni.getText());
					if (user == null) {
						JErrorLabel.setText("No existe el usuario");
					} else if (user.getPassword().equals(pass.getText())) {
						if (user.getRol().equals("Client")) {
							JFrame a = new MainClientGUI(user);
							a.setVisible(true);
						} else {
							JFrame a = new MainAdminGUI();
							a.setVisible(true);
						}

					} else {
						JErrorLabel.setText("Contraseña incorrecta");
					}

				}
			});
			loginButton.setBounds(161, 145, 121, 61);
			jContentPane.add(loginButton);

			JButton btntodaviaNoEstas = new JButton(ResourceBundle.getBundle(
					"Etiquetas").getString("LoginGUI.btntodaviaNoEstas.text")); //$NON-NLS-1$ //$NON-NLS-2$
			btntodaviaNoEstas.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {

					JFrame a = new RegisterGUI();
					a.setVisible(true);
				}
			});
			btntodaviaNoEstas.setBounds(81, 217, 286, 23);
			jContentPane.add(btntodaviaNoEstas);
			
			pass = new JPasswordField();
			pass.setBounds(207, 117, 153, 17);
			jContentPane.add(pass);

		}
		return jContentPane;
	}

} // @jve:decl-index=0:visual-constraint="0,0"
