package it.unibs.pajc.pokeproject.view;

import javax.swing.*;

import it.unibs.pajc.pokeproject.controller.PKClientController;
import it.unibs.pajc.pokeproject.model.Pokemon;

import java.awt.*;
import java.awt.event.*;
import java.util.Map;

public class PKClientWindow extends JFrame {
	
	private static final String TITLE = "PokeBattle Client v0.2";
	private JPanel mainPanel = null;
	private IpPanel ipPanel = null;
	private PokeChooserPanel pokeChooserPanel = null;
	private BattlePanel battlePanel = null;
	private PKClientController controller = null;
	
	
	/**
	 * Create the application.
	 */
	public PKClientWindow() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					initialize();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setTitle(TITLE);
		setResizable(false);
		setBounds(100, 100, 600, 450);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);	
		setVisible(true);
	}
	
	private void drawIpPanel() {
		ipPanel = new IpPanel();
		ipPanel.setBounds(0, 0, 663, 429);
		ipPanel.setVisible(true);
		getContentPane().add(ipPanel);
		ipPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(controller.connectToServer()) {
					drawPokeChooserPanel();
					ipPanel.setVisible(false);
					for(Map.Entry<Integer, Pokemon> entry : controller.getPkDatabase().entrySet()) {
						pokeChooserPanel.drawPokemon(entry.getValue());
					}
				}
				else {
					showErrorPopup();
				}
			}
		});
	}
	
	public void drawMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 594, 421);
		getContentPane().add(mainPanel);
		mainPanel.setLayout(null);
		
		JButton btnSinglePlayer = new JButton("Single Player");
		btnSinglePlayer.setBounds(96, 160, 152, 58);
		mainPanel.add(btnSinglePlayer);
		btnSinglePlayer.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JButton btnMultiPlayer = new JButton("Multi Player");
		btnMultiPlayer.setBounds(358, 160, 152, 58);
		mainPanel.add(btnMultiPlayer);
		btnMultiPlayer.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JLabel background = new JLabel();
		background.setBounds(0, 0, 594, 421);
		mainPanel.add(background);
		background.setIcon(new ImageIcon(new ImageIcon(BattleFrame.class.getResource("/img/client_back.jpg")).getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT)));
		
		btnMultiPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawIpPanel();
				setBounds(getX(), getY(), ipPanel.getWidth(), ipPanel.getHeight());
				mainPanel.setVisible(false); 
			}
		});
		
		btnSinglePlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawPokeChooserPanel();
				mainPanel.setVisible(false); 
			}

			
		});
	}
	
	private void drawPokeChooserPanel() {
		pokeChooserPanel = new PokeChooserPanel();
		pokeChooserPanel.setBounds(0, 0, 663, 429);
		pokeChooserPanel.setVisible(true);
		getContentPane().add(pokeChooserPanel);
	}
	
	public void setController(PKClientController controller) {
		this.controller = controller;
	}
	
	private void showErrorPopup() {
		JOptionPane error = new JOptionPane();
		error.setBounds(getBounds());
		error.showMessageDialog(this, "Problema nella connessione al server", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
	}
}
