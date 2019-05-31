package it.unibs.pajc.pokeproject.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import it.unibs.pajc.pokeproject.model.PKMove;

import javax.swing.JButton;

public class BattlePanel extends JPanel {
	
	private static final long serialVersionUID = 6693764125058218350L;
	private static final String PKM_FONT = "PKMN_RBYGSC.ttf";
	
	private JLabel lblBackMyPoke;
	private JLabel lblFrontTrainerPoke;
	private JLabel lblNameOpponentPoke;
	private JLabel lblNameMyPoke;
	private JButton[] btnMoves;
	private JProgressBar trainerHPbar;
	private JProgressBar opponentHPbar;
	
	private ArrayList<ActionListener> listenerList = new ArrayList<>();
	
	private Timer trainerHPTimer;
	private Timer opponentHPTimer;
	private ActionListener trainerHPdelay;
	private ActionListener opponentHPdelay;

	/**
	 * Create the panel.
	 */
	public BattlePanel() {
		try {
		     GraphicsEnvironment ge = 
		         GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(PKM_FONT)));
		} catch (IOException|FontFormatException e) {
		     e.printStackTrace();
		}
		
		setLayout(null);
		setBounds(100, 100, 618, 400);
		setVisible(false);
		
		trainerHPbar = new JProgressBar();
		trainerHPbar.setValue(100);
		trainerHPbar.setStringPainted(true);
		trainerHPbar.setString("");
		trainerHPbar.setForeground(Color.GREEN);
		trainerHPbar.setBounds(488, 248, 99, 6);
		trainerHPbar.setString(""); //percentage string fix, please do NOT set to null
		add(trainerHPbar);
		
		opponentHPbar = new JProgressBar();
		opponentHPbar.setStringPainted(true);
		opponentHPbar.setValue(100);
		opponentHPbar.setForeground(Color.GREEN);
		opponentHPbar.setBounds(123, 72, 99, 6);
		opponentHPbar.setString(""); //percentage string fix, please do NOT set to null
		add(opponentHPbar);
		
		lblNameOpponentPoke = new JLabel("");
		lblNameOpponentPoke.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNameOpponentPoke.setBounds(111, 39, 155, 22);
		lblNameOpponentPoke.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 15));
		add(lblNameOpponentPoke);
		
		lblNameMyPoke = new JLabel("");
		lblNameMyPoke.setBounds(387, 217, 155, 24);
		lblNameMyPoke.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 15));
		add(lblNameMyPoke);
		
		JLabel lblMoveText = new JLabel("");
		lblMoveText.setForeground(Color.WHITE);
		lblMoveText.setBounds(344, 327, 254, 62);
		add(lblMoveText);
		
		JLabel lblEnemyHPLabel = new JLabel();
		lblEnemyHPLabel.setBounds(0, 28, 291, 69);
		lblEnemyHPLabel.setIcon(new ImageIcon(new ImageIcon(BattlePanel.class.getResource("/img/inbattle/utils/battleFoeBoxD.png")).getImage().getScaledInstance(lblEnemyHPLabel.getWidth(), lblEnemyHPLabel.getHeight(), Image.SCALE_DEFAULT))); //box scale
		add(lblEnemyHPLabel);
		
		JLabel lblTrainerHPLabel = new JLabel();
		lblTrainerHPLabel.setBounds(344, 205, 274, 69);
		lblTrainerHPLabel.setIcon(new ImageIcon(new ImageIcon(BattlePanel.class.getResource("/img/inbattle/utils/battlePlayerBoxD.png")).getImage().getScaledInstance(lblTrainerHPLabel.getWidth(), lblTrainerHPLabel.getHeight(), Image.SCALE_DEFAULT))); //box scale
		add(lblTrainerHPLabel);
		
		JButton btnMove1 = new JButton("MOVE1");
		btnMove1.setBounds(25, 323, 122, 31);
		btnMove1.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 10));
		btnMove1.setBorderPainted(false);
		add(btnMove1);
		btnMove1.addMouseListener(new MouseAdapter() {
	         public void mouseEntered(MouseEvent me) {
	            lblMoveText.setText("Usa " + btnMoves[0].getText());
	         }
	         public void mouseExited(MouseEvent me) {
	        	 lblMoveText.setText("");
	         }
			});
		
		JButton btnMove2 = new JButton("MOVE2");
		btnMove2.setBounds(157, 323, 122, 31);
		btnMove2.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 10));
		btnMove1.setBorderPainted(false);
		add(btnMove2);
		btnMove2.addMouseListener(new MouseAdapter() {
	         public void mouseEntered(MouseEvent me) {
	            lblMoveText.setText("Usa " + btnMoves[1].getText());
	         }
	         public void mouseExited(MouseEvent me) {
	        	 lblMoveText.setText("");
	         }
			});
		
		JButton btnMove3 = new JButton("MOVE3");
		btnMove3.setBounds(25, 358, 122, 31);
		btnMove3.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 10));
		btnMove1.setBorderPainted(false);
		add(btnMove3);
		btnMove3.addMouseListener(new MouseAdapter() {
	         public void mouseEntered(MouseEvent me) {
	            lblMoveText.setText("Usa " + btnMoves[2].getText());
	         }
	         public void mouseExited(MouseEvent me) {
	        	 lblMoveText.setText("");
	         }
			});
		
		JButton btnMove4 = new JButton("MOVE4");
		btnMove4.setBounds(157, 358, 122, 31);
		btnMove4.setFont(new Font("PKMN RBYGSC", Font.PLAIN, 10));
		btnMove1.setBorderPainted(false);
		add(btnMove4);
		btnMove4.addMouseListener(new MouseAdapter() {
	         public void mouseEntered(MouseEvent me) {
	            lblMoveText.setText("Usa " + btnMoves[3].getText());
	         }
	         public void mouseExited(MouseEvent me) {
	        	 lblMoveText.setText("");
	         }
			});
		
		btnMoves = new JButton[]{btnMove1, btnMove2, btnMove3, btnMove4};
				
		JLabel lblTextBoxLabel = new JLabel();
		lblTextBoxLabel.setBounds(0, 302, 618, 98);
		lblTextBoxLabel.setIcon(new ImageIcon(new ImageIcon(BattlePanel.class.getResource("/img/inbattle/utils/battleCommand.png")).getImage().getScaledInstance(lblTextBoxLabel.getWidth(), lblTextBoxLabel.getHeight(), Image.SCALE_DEFAULT))); //box scale
		add(lblTextBoxLabel);
		
		lblBackMyPoke = new JLabel();
		lblBackMyPoke.setBounds(42, 163, 196, 196);
		add(lblBackMyPoke);
		
		lblFrontTrainerPoke = new JLabel();
		lblFrontTrainerPoke.setBounds(446, 72, 96, 96);
		add(lblFrontTrainerPoke);
			
		JLabel lblTrainerBaseLabel = new JLabel();
		lblTrainerBaseLabel.setBounds(-28, 217, 319, 155);
		lblTrainerBaseLabel.setIcon(new ImageIcon(new ImageIcon(BattlePanel.class.getResource("/img/inbattle/enemybaseFieldGrass.png")).getImage().getScaledInstance(lblTrainerBaseLabel.getWidth(), lblTrainerBaseLabel.getHeight(), Image.SCALE_DEFAULT))); //base scale
		add(lblTrainerBaseLabel);
		
		JLabel lblEnemyBaseLabel = new JLabel();
		lblEnemyBaseLabel.setBounds(377, 101, 231, 125);
		lblEnemyBaseLabel.setIcon(new ImageIcon(new ImageIcon(BattlePanel.class.getResource("/img/inbattle/enemybaseFieldGrass.png")).getImage().getScaledInstance(lblEnemyBaseLabel.getWidth(), lblEnemyBaseLabel.getHeight(), Image.SCALE_DEFAULT))); //base scale
		add(lblEnemyBaseLabel);
		
		JLabel background = new JLabel();
		background.setIcon(new ImageIcon(new ImageIcon(BattlePanel.class.getResource("/img/inbattle/battlebgField.png")).getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT))); //back scale
		background.setBounds(0, 0, 618, 400);
		add(background);
		
		
		
		
	}
	
	public void setSprites(URL backSpriteUrl, URL frontSpriteUrl) {
		lblBackMyPoke.setIcon(new ImageIcon(backSpriteUrl));
		lblFrontTrainerPoke.setIcon(new ImageIcon(new ImageIcon(frontSpriteUrl).getImage().getScaledInstance(lblFrontTrainerPoke.getWidth(), lblFrontTrainerPoke.getHeight(), Image.SCALE_DEFAULT)));
	}
	
	public void setPokeNames(String trainer, String opponent) {
		lblNameMyPoke.setText(trainer);
		lblNameOpponentPoke.setText(opponent);
	}
	
	public void setMoveNames(PKMove[] moves) {
		for(int i=0;i<4;i++) {
			int j = i;
			btnMoves[i].setText(moves[i].getName());
			btnMoves[i].setActionCommand(String.valueOf(i));
			btnMoves[i].addActionListener(new ActionListener() {
				public void actionPerformed (ActionEvent e) {
					ActionEvent event = new ActionEvent(btnMoves[j], ActionEvent.ACTION_PERFORMED, btnMoves[j].getActionCommand());
					fireActionPerformed(event);
				}
			});
		}
	}
	
	public void setPokeHP(int trainerHP, int enemyHP) {
		trainerHPbar.setMaximum(trainerHP);
		opponentHPbar.setMaximum(enemyHP);
	}
	
	private void setBarColor(JProgressBar bar) {
		int percentage = (int)((double)bar.getValue()/bar.getMaximum()*100);
		System.out.println(percentage + "%");
		if(percentage>50) 
			bar.setForeground(Color.GREEN);
		else if(percentage>20 && percentage<=50) 
			bar.setForeground(Color.ORANGE);
		else
			bar.setForeground(Color.RED);
	}
	
	public void setTrainerHPLevel(int value) {
		trainerHPdelay = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(trainerHPbar.getValue() > value) {
					trainerHPbar.setValue(trainerHPbar.getValue()-1);
					setBarColor(trainerHPbar);
				}
				else
				trainerHPTimer.stop();
			}
			
		};
		trainerHPTimer = new Timer(200, trainerHPdelay);
		trainerHPTimer.start();
	}
	
	public void setOpponentHPLevel(int value) {
		opponentHPdelay = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(opponentHPbar.getValue() > value) {
					opponentHPbar.setValue(opponentHPbar.getValue()-1);
					setBarColor(opponentHPbar);
				}
				else
				opponentHPTimer.stop();
			}
			
		};
		opponentHPTimer = new Timer(200, opponentHPdelay);
		opponentHPTimer.start();
	}
	
	public void addListener(ActionListener e) {
			listenerList.add(e);
	}
	
	private void fireActionPerformed(ActionEvent e) {
		for(ActionListener l : listenerList) {
			l.actionPerformed(e);
		}
	}
}
