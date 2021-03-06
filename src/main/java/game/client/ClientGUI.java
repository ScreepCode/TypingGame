package game.client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ClientGUI extends JFrame implements KeyListener{
	protected JPasswordField textFieldPassword;
	protected JTextField textFieldAnschlaegePM;
	protected JTextField textFieldFehler;
	protected JTextField textFieldRechnung;
	protected JTextField textFieldErgebnis;
	protected JTextField textFieldAnschlaege;

	private JPanel panelLogin;
	private JPanel panelLobby;
	private JPanel panelGame;
	private JPanel panelErgebnis;

	protected JLabel lblReadyCounter;
	protected JLabel lblZeit;
	protected JLabel lblAnschlaege;

	protected StyledDocument doc = new DefaultStyledDocument();
	protected JTextPane txtpnText;

	protected JButton btnRekordUpload;

	protected DefaultTableModel dTMHHSc = new DefaultTableModel();
	protected JTable highscoreTable;

	protected DefaultTableModel dTMSc = new DefaultTableModel();
	protected JTable scoreTable;

	protected DefaultTableModel dTMLobby = new DefaultTableModel();
	protected JTable lobbyTable;

	private final ClientHead clientHead;

	
	public ClientGUI(ClientHead clientHead) {
		super("Typing Game Multiplayer");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		ImageIcon img = new ImageIcon("Icon.png");
		setIconImage(img.getImage());

		this.clientHead = clientHead;
		
		//##############################################
		//LOGINPAGE
		//##############################################

		panelLogin = new JPanel();
		panelLogin.setBounds(10, 11, 734, 513);
		panelLogin.setLayout(null);
		panelLogin.setVisible(true);
		
		JLabel lblHeading = new JLabel("Typing Game Multiplayer");
		lblHeading.setBounds(76, 0, 582, 64);
		lblHeading.setFont(new Font("Tahoma", Font.PLAIN, 53));
		panelLogin.add(lblHeading);

		highscoreTable = new JTable(dTMHHSc);
		JScrollPane scrollTable = new JScrollPane(highscoreTable);
		scrollTable.setBounds(520, 150, 181, 320);

		panelLogin.add(scrollTable);
		
		JLabel lblHighScore = new JLabel("Highscores");
		lblHighScore.setLabelFor(highscoreTable);
		lblHighScore.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblHighScore.setBounds(520, 110, 204, 34);
		panelLogin.add(lblHighScore);

		textFieldPassword = new JPasswordField();
		textFieldPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textFieldPassword.setColumns(10);
		textFieldPassword.setBounds(122, 234, 181, 58);
		textFieldPassword.setEchoChar('*');
		panelLogin.add(textFieldPassword);
		
		JTextField textFieldUsername = new JTextField();
		textFieldUsername.setToolTipText("");
		textFieldUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textFieldUsername.setBounds(123, 151, 180, 56);
		panelLogin.add(textFieldUsername);
		textFieldUsername.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setLabelFor(textFieldUsername);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lblUsername.setBounds(10, 160, 103, 34);
		panelLogin.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Passwort");
		lblPassword.setLabelFor(textFieldPassword);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lblPassword.setBounds(10, 246, 103, 34);
		panelLogin.add(lblPassword);
		
		JButton btnAccErstellen = new JButton("Account \r\nerstellen");
		btnAccErstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = textFieldUsername.getText();
				String password = new String(textFieldPassword.getPassword());
				clientHead.login.accCreation(username, password);
			}
		});
		btnAccErstellen.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnAccErstellen.setBounds(328, 151, 161, 56);
		panelLogin.add(btnAccErstellen);
		
		JButton btnAnmelden = new JButton("Anmelden");
		btnAnmelden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = textFieldUsername.getText();
				String password = new String(textFieldPassword.getPassword());
				clientHead.login.accLogin(username, password);
			}
		});
		btnAnmelden.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnAnmelden.setBounds(328, 234, 161, 56);
		panelLogin.add(btnAnmelden);
		
		JButton btnAlsGastAnmelden = new JButton("Als Gast anmelden");
		btnAlsGastAnmelden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clientHead.login.accLoginAsGuest();
			}
		});
		btnAlsGastAnmelden.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnAlsGastAnmelden.setBounds(76, 373, 271, 56);
		panelLogin.add(btnAlsGastAnmelden);

		//##############################################
		//LOBBY
		//##############################################
		
		panelLobby = new JPanel();
		panelLobby.setBounds(10, 11, 734, 513);
		// getContentPane().add(panelLobby);
		panelLobby.setLayout(null);
		
		JLabel lblLobby = new JLabel("LOBBY");
		lblLobby.setBounds(320, 0, 96, 40);
		lblLobby.setFont(new Font("Tahoma", Font.PLAIN, 33));
		panelLobby.add(lblLobby);

		lobbyTable = new JTable(dTMLobby);
		lobbyTable.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JScrollPane scrollLobbyTable = new JScrollPane(lobbyTable);
		scrollLobbyTable.setBounds(50, 120, 650, 300);
		panelLobby.add(scrollLobbyTable);
		
		JButton btnBereit = new JButton("Bereit");
		btnBereit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clientHead.lobby.setReady();
			}
		});
		btnBereit.setBounds(50, 452, 650, 50);
		panelLobby.add(btnBereit);
		
		lblReadyCounter = new JLabel("Bereit: 0/0");
		lblReadyCounter.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblReadyCounter.setBounds(583, 43, 120, 40);
		panelLobby.add(lblReadyCounter);
		
		//##############################################
		//Game
		//##############################################

		panelGame = new JPanel();
		panelGame.setBounds(10, 11, 734, 513);
		panelGame.setLayout(null);
		panelGame.addKeyListener(this);
		
		JLabel lblGame = new JLabel("Schreib so gut und schnell du kannst");
		lblGame.setFont(new Font("Tahoma", Font.PLAIN, 36));
		lblGame.setBounds(83, 0, 587, 67);
		panelGame.add(lblGame);
		
		JLabel lblZeitLabel1 = new JLabel("Verbleibende Zeit:");
		lblZeitLabel1.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblZeitLabel1.setBounds(341, 92, 211, 36);
		panelGame.add(lblZeitLabel1);
		
		lblZeit = new JLabel("100");
		lblZeit.setForeground(Color.RED);
		lblZeit.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblZeit.setBounds(562, 92, 50, 36);
		panelGame.add(lblZeit);
		
		JLabel lblZeitLabel2 = new JLabel("Sekunden");
		lblZeitLabel2.setBounds(609, 92, 114, 36);
		panelGame.add(lblZeitLabel2);
		lblZeitLabel2.setFont(new Font("Tahoma", Font.PLAIN, 26));
		
		JLabel lblAnschlaegeLabel = new JLabel("Anschl\u00E4ge/m:");
		lblAnschlaegeLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblAnschlaegeLabel.setBounds(46, 92, 186, 36);
		panelGame.add(lblAnschlaegeLabel);
		
		lblAnschlaege = new JLabel("32");
		lblAnschlaege.setForeground(Color.RED);
		lblAnschlaege.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblAnschlaege.setBounds(230, 92, 50, 36);
		panelGame.add(lblAnschlaege);
		
		txtpnText = new JTextPane(doc);
		txtpnText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtpnText.setBounds(46, 139, 677, 349);
		txtpnText.setEnabled(false);
		panelGame.add(txtpnText);
		
		//##############################################
		//Ergebnis
		//##############################################

		panelErgebnis = new JPanel();
		panelErgebnis.setBounds(10, 11, 734, 513);
		panelErgebnis.setLayout(null);
		
		JLabel lblErgebnisHeading = new JLabel("ERGEBNIS");
		lblErgebnisHeading.setBounds(276, 0, 164, 40);
		lblErgebnisHeading.setFont(new Font("Tahoma", Font.PLAIN, 33));
		panelErgebnis.add(lblErgebnisHeading);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(Color.GRAY);
		separator.setBackground(Color.GRAY);
		separator.setBounds(365, 84, 2, 395);
		panelErgebnis.add(separator);
		
		JLabel lblPAuswertung = new JLabel("Pers\u00F6nlich:");
		lblPAuswertung.setFont(new Font("Tahoma", Font.PLAIN, 31));
		lblPAuswertung.setBounds(47, 104, 270, 40);
		panelErgebnis.add(lblPAuswertung);
		
		JLabel lblGAuswertung = new JLabel("Gesamt:");
		lblGAuswertung.setFont(new Font("Tahoma", Font.PLAIN, 31));
		lblGAuswertung.setBounds(420, 104, 270, 40);
		panelErgebnis.add(lblGAuswertung);
		
		btnRekordUpload = new JButton("Rekord hochladen");
		btnRekordUpload.setEnabled(false);
		btnRekordUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clientHead.ergebnis.uploadRecord();
			}
		});
		btnRekordUpload.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnRekordUpload.setBounds(47, 439, 194, 40);
		panelErgebnis.add(btnRekordUpload);


		scoreTable = new JTable(dTMSc);
		JScrollPane scrollScoreTable = new JScrollPane(scoreTable);
		scrollScoreTable.setBounds(420, 150, 244, 278);
		panelErgebnis.add(scrollScoreTable);

		
		JLabel lblAnschlaegePM = new JLabel("Anschl\u00E4ge/m");
		lblAnschlaegePM.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblAnschlaegePM.setBounds(179, 206, 146, 40);
		panelErgebnis.add(lblAnschlaegePM);
		
		JLabel lblFehler = new JLabel("Fehler");
		lblFehler.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblFehler.setBounds(179, 257, 146, 40);
		panelErgebnis.add(lblFehler);
		
		JLabel lblRechnung = new JLabel("Rechnung");
		lblRechnung.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblRechnung.setBounds(229, 305, 96, 40);
		panelErgebnis.add(lblRechnung);
		
		JLabel lblErgebnis = new JLabel("Gesamt Punktzahl");
		lblErgebnis.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblErgebnis.setBounds(172, 388, 183, 40);
		panelErgebnis.add(lblErgebnis);
		
		textFieldAnschlaegePM = new JTextField();
		lblAnschlaegePM.setLabelFor(textFieldAnschlaegePM);
		textFieldAnschlaegePM.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldAnschlaegePM.setText("52");
		textFieldAnschlaegePM.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textFieldAnschlaegePM.setEnabled(false);
		textFieldAnschlaegePM.setEditable(false);
		textFieldAnschlaegePM.setBounds(47, 208, 122, 35);
		panelErgebnis.add(textFieldAnschlaegePM);
		textFieldAnschlaegePM.setColumns(10);
		
		textFieldFehler = new JTextField();
		lblFehler.setLabelFor(textFieldFehler);
		textFieldFehler.setText("12");
		textFieldFehler.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldFehler.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textFieldFehler.setEnabled(false);
		textFieldFehler.setEditable(false);
		textFieldFehler.setColumns(10);
		textFieldFehler.setBounds(47, 259, 122, 35);
		panelErgebnis.add(textFieldFehler);
		
		textFieldRechnung = new JTextField();
		lblRechnung.setLabelFor(textFieldRechnung);
		textFieldRechnung.setText("104 * (1 - 12/104)");
		textFieldRechnung.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldRechnung.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textFieldRechnung.setEnabled(false);
		textFieldRechnung.setEditable(false);
		textFieldRechnung.setColumns(10);
		textFieldRechnung.setBounds(47, 305, 172, 35);
		panelErgebnis.add(textFieldRechnung);
		
		textFieldErgebnis = new JTextField();
		lblErgebnis.setLabelFor(textFieldErgebnis);
		textFieldErgebnis.setText("92");
		textFieldErgebnis.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldErgebnis.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textFieldErgebnis.setEnabled(false);
		textFieldErgebnis.setEditable(false);
		textFieldErgebnis.setColumns(10);
		textFieldErgebnis.setBounds(47, 388, 103, 35);
		panelErgebnis.add(textFieldErgebnis);
		
		textFieldAnschlaege = new JTextField();
		textFieldAnschlaege.setText("104");
		textFieldAnschlaege.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldAnschlaege.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textFieldAnschlaege.setEnabled(false);
		textFieldAnschlaege.setEditable(false);
		textFieldAnschlaege.setColumns(10);
		textFieldAnschlaege.setBounds(47, 157, 122, 35);
		panelErgebnis.add(textFieldAnschlaege);
		
		JLabel lblAnschlaegeE = new JLabel("Anschl\u00E4ge");
		lblAnschlaegeE.setLabelFor(textFieldAnschlaege);
		lblAnschlaegeE.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblAnschlaegeE.setBounds(179, 155, 146, 40);
		panelErgebnis.add(lblAnschlaegeE);


		getContentPane().add(panelLogin);
		getContentPane().add(panelLobby);
		getContentPane().add(panelGame);
		getContentPane().add(panelErgebnis);

		panelLogin.setVisible(false);
		panelLobby.setVisible(false);
		panelGame.setVisible(false);
		panelErgebnis.setVisible(false);
	}

	public void setPanelLayout(String panelType){
		switch (panelType) {
			case ("login") -> {
				panelLogin.setVisible(true);
				panelLobby.setVisible(false);
				panelGame.setVisible(false);
				panelErgebnis.setVisible(false);
			}
			case ("lobby") -> {
				panelLogin.setVisible(false);
				panelLobby.setVisible(true);
				panelGame.setVisible(false);
				panelErgebnis.setVisible(false);
			}
			case ("game") -> {
				panelLogin.setVisible(false);
				panelLobby.setVisible(false);
				panelGame.setVisible(true);
				panelErgebnis.setVisible(false);
			}
			case ("ergebnis") -> {
				panelLogin.setVisible(false);
				panelLobby.setVisible(false);
				panelGame.setVisible(false);
				panelErgebnis.setVisible(true);
			}
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		clientHead.game.typeChar(e.getKeyChar());
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
			clientHead.game.removeLastChar();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}
}
