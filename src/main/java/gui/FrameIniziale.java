package gui;

import cars.Cars;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * frame iniziale del programma per la scelta della modalita' di operazione: acquirente, agente assicurativo o meccanico
 */
public class FrameIniziale extends JFrame {

	private ButtonGroup bottoni;
	private JButton acquirente;
	private JButton assicurazione;
	private JButton meccanico;
	private JLabel labelAcquirente;
	private JLabel labelAssicurazione;
	private JLabel labelMeccanico;
	private JPanel panel;
	private ActionListener listener;
	private Cars contract;

	public FrameIniziale(Cars contract) {
		this.contract = contract;

		//creo ed imposto i bottoni
		//creo il bottone per il possibile acquirente
		ImageIcon icon1 = new ImageIcon("immagini/shop.png");
		Image newIcon1 = icon1.getImage().getScaledInstance(100,100, java.awt.Image.SCALE_SMOOTH);
		ImageIcon iconAcquirente = new ImageIcon(newIcon1);
		acquirente = new JButton(iconAcquirente);
		acquirente.setPreferredSize(new Dimension(150,150));
		acquirente.setBorder(null);
		//creo il bottone per il meccanico
		ImageIcon icon2 = new ImageIcon("immagini/mechanic.png");
		Image newIcon2 = icon2.getImage().getScaledInstance(100,100, java.awt.Image.SCALE_SMOOTH);
		ImageIcon iconMeccanico=new ImageIcon(newIcon2);
		meccanico = new JButton(iconMeccanico);
		meccanico.setPreferredSize(new Dimension(150,150));
		meccanico.setBorder(null);
		//creo il bottone per l'agente assicurativo
		ImageIcon icon3 = new ImageIcon("immagini/insurance.png");
		Image newIcon3 = icon3.getImage().getScaledInstance(100,100, java.awt.Image.SCALE_SMOOTH);
		ImageIcon iconAssicurazione=new ImageIcon(newIcon3);
		assicurazione = new JButton(iconAssicurazione);
		assicurazione.setPreferredSize(new Dimension(150,150));
		assicurazione.setBorder(null);

		//creo le didascalie da accompagnare ai bottoni
		labelAcquirente = new JLabel("Cliente");
		labelAcquirente.setFont(new Font("Times", Font.CENTER_BASELINE, 20));
		labelAcquirente.setForeground(new Color(14,44,114));
		labelAcquirente.setHorizontalAlignment(JLabel.CENTER);
		labelMeccanico = new JLabel("Meccanico");
		labelMeccanico.setFont(new Font("Times", Font.CENTER_BASELINE, 20));
		labelMeccanico.setForeground(new Color(14,44,114));
		labelMeccanico.setHorizontalAlignment(JLabel.CENTER);
		labelAssicurazione = new JLabel("Agente Assicurativo");
		labelAssicurazione.setFont(new Font("Times", Font.CENTER_BASELINE, 20));
		labelAssicurazione.setForeground(new Color(14,44,114));
		labelAssicurazione.setHorizontalAlignment(JLabel.CENTER);

		//aggiungo i bottoni al button group per renderne la selezione mutuamente esclusiva
		bottoni=new ButtonGroup();
		bottoni.add(acquirente);
		bottoni.add(meccanico);
		bottoni.add(assicurazione);

		//aggiungo i bottoni al pannello e lo rendo visibile
		panel=new JPanel();
		panel.setLayout(new GridLayout(2,3));
		panel.add(acquirente);
		panel.add(meccanico);
		panel.add(assicurazione);
		panel.add(labelAcquirente);
		panel.add(labelMeccanico);
		panel.add(labelAssicurazione);
		add(panel);

		class ListenerAcquirente implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				acquirente();
			}
		}
		listener=new ListenerAcquirente();
		acquirente.addActionListener(listener);

		class ListenerMeccanico implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				meccanico();
			}
		}
		listener=new ListenerMeccanico();
		meccanico.addActionListener(listener);


		class ListenerAssicurazione implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				assicurazione();
			}
		}
		listener=new ListenerAssicurazione();
		assicurazione.addActionListener(listener);
	}

	private void acquirente() {
		JFrame frame=new FrameAcquirente(contract);

		frame.setSize(600,600);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}

	private void meccanico() {
		JFrame frame=new FrameMeccanico(contract);

		frame.setSize(600,600);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}

	private void assicurazione() {
		JFrame frame=new FrameAssicurazione(contract);

		frame.setSize(600,450);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
}
