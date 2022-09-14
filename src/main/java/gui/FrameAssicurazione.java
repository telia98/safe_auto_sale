package gui;

import cars.Cars;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

public class FrameAssicurazione extends JFrame {

    private JPanel panel;
    private JPanel panelInfo;
    private JPanel panelsInfoCar;
    private JPanel panelSuperiore;
    private JPanel panelCentrale;
    private JPanel panelInferiore;
    private JPanel panelBottone;
    private JLabel labelInfo;
    private JLabel labelCF;
    private JTextField textFieldCF;
    private JLabel labelCognome;
    private JTextField textFieldCognome;
    private JLabel labelNome;
    private JTextField textFieldNome;
    private JButton aggiornaProprietario;
    private JButton aggiornaStato;
    private Cars contract;
    private int id;

    public FrameAssicurazione(Cars contract){
        this.contract = contract;

        panel = new JPanel();

        //gestisco il pannello info
        panelInfo = setPanelInfo();
        panelsInfoCar = setPanelInfoCars();

        //gestisco il pannello del bottone
        panelBottone = setPanelBottone();

        panel.setLayout(new GridLayout(3,1));
        panel.add(labelInfo);
        panel.add(panelsInfoCar);
        panel.add(panelBottone);
        add(panel);
    }

    private JPanel setPanelInfoCars() {
        JPanel panel = new JPanel();

        JPanel panelComboBox = setPanelComboBox();
        JPanel panelInfoCars = createSinglePanel();
        panel.setLayout(new BorderLayout());
        panel.add(panelComboBox,BorderLayout.NORTH);
        panel.add(panelInfoCars,BorderLayout.SOUTH);
        return panel;
    }

    private JPanel setPanelComboBox() {
        JPanel panel = new JPanel();

        JLabel label = new JLabel("Selezionare l'auto di cui si vogliono vedere i dettagli");
        label.setFont(new Font("Times", Font.CENTER_BASELINE, 14));
        label.setForeground(new Color(14, 44, 114));

        JComboBox comboCars = new JComboBox();
        try {
            //carico la comboBox delle autovetture registrate
            comboCars.addItem("");

            //recupero il numero totale di auto memorizzate
            BigInteger nCars = contract.getNCars().send();

            //carico le voci della comboBox
            for (int i = 0; i < nCars.intValue(); i++) {
                //recupero l'auto
                String carInfo = contract.getCarInfo(BigInteger.valueOf(i)).send();
                //costruisco il contenuto della combobox
                int vTarga = carInfo.indexOf(",");
                String sub = carInfo.substring(vTarga + 1, carInfo.length());
                int vTelaio = sub.indexOf(",");
                sub = sub.substring(vTelaio + 1, sub.length());
                int vMarca = sub.indexOf(",");
                String copia = sub;
                String marca = copia.substring(0, vMarca);
                sub = sub.substring(vMarca + 1, sub.length());
                int vModello = sub.indexOf(",");
                copia = sub;
                String modello = copia.substring(0, vModello);
                sub = sub.substring(vModello + 1, sub.length());

                comboCars.addItem(i + " - " + marca + " " + modello);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        class ComboCarsListener implements ActionListener {

            public void actionPerformed(ActionEvent event) {

                String selectedItem=(String) comboCars.getSelectedItem();
                //operazioni per visualizzare il pannello con i dettagli dell'auto selezionata
                showInformationsAboutCar(selectedItem);
            }
        }
        ActionListener listener=new ComboCarsListener();
        comboCars.addActionListener(listener);

        panel.add(label);
        panel.add(comboCars);
        return panel;
    }

    private void showInformationsAboutCar(String autoSelezionata) {
        //recupero id numerico dell'auto
        int pos=autoSelezionata.indexOf("-");
        String sub=autoSelezionata.substring(0,pos-1);
        int id= Integer.valueOf(sub);
        this.id = id; //salvo l'indice dell'auto selezionata che mi servirà per le operazioni successive

        try {
            //recupero le informazioni singole delle auto (targa,marca,etc...)
            String carOwner= contract.getCarOwner(BigInteger.valueOf(id)).send();

            //costruisco il contenuto della combobox
            int vCodiceFiscale = carOwner.indexOf(",");
            String codiceFiscale = carOwner.substring(0, vCodiceFiscale);                //CODICE FISCALE
            sub = carOwner.substring(vCodiceFiscale + 1, carOwner.length());
            int vCognome = sub.indexOf(",");
            String copia = sub;
            String cognome = copia.substring(0, vCognome);                                //COGNOME
            String nome = sub.substring(vCognome + 1, sub.length());             //NOME

            //carico le textfield
            textFieldCF.setText(codiceFiscale);
            textFieldCF.setEditable(true);
            textFieldCognome.setText(cognome);
            textFieldCognome.setEditable(true);
            textFieldNome.setText(nome);
            textFieldNome.setEditable(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JPanel createSinglePanel(){
        JPanel panel = new JPanel();
        JPanel panelSuperiore = setPanelSuperiore();
        JPanel panelCentrale = setPanelCentrale();
        JPanel panelInferiore = setPanelInferiore();

        panel.setLayout(new GridLayout(3,1));
        panel.add(panelSuperiore);
        panel.add(panelCentrale);
        panel.add(panelInferiore);
        return panel;
    }

    private JPanel setPanelInfo() {
        JPanel panel = new JPanel();
        labelInfo = new JLabel("SEZIONE AGENTE ASSICURATIVO");
        labelInfo.setFont(new Font("Times", Font.CENTER_BASELINE, 20));
        labelInfo.setForeground(new Color(14, 44, 114));
        labelInfo.setHorizontalAlignment(JLabel.CENTER);
        panel.add(labelInfo);
        return panel;
    }

    private JPanel setPanelSuperiore(){
        JPanel panel = new JPanel();

        labelCF = new JLabel("Codice fiscale");
        labelCF.setFont(new Font("Times", Font.CENTER_BASELINE, 14));
        labelCF.setForeground(new Color(14,44,114));
        textFieldCF = new JTextField(15);
        panel.add(labelCF);
        panel.add(textFieldCF);
        return panel;
    }

    private JPanel setPanelCentrale() {
        JPanel panel = new JPanel();

        labelCognome = new JLabel("Cognome");
        labelCognome.setFont(new Font("Times", Font.CENTER_BASELINE, 14));
        labelCognome.setForeground(new Color(14,44,114));
        textFieldCognome = new JTextField(15);
        panel.add(labelCognome);
        panel.add(textFieldCognome);
        return panel;
    }

    private JPanel setPanelInferiore() {
        JPanel panel = new JPanel();

        labelNome = new JLabel("Nome");
        labelNome.setFont(new Font("Times", Font.CENTER_BASELINE, 14));
        labelNome.setForeground(new Color(14,44,114));
        textFieldNome = new JTextField(15);
        panel.add(labelNome);
        panel.add(textFieldNome);
        return panel;
    }

    private JPanel setPanelBottone() {
        JPanel panel = new JPanel();

        aggiornaProprietario = new JButton("AGGIORNA");
        aggiornaProprietario.setFont(new Font("Times", Font.CENTER_BASELINE, 20));
        aggiornaProprietario.setForeground(new Color(14,44,114));

        class ListenerAggiorna implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                aggiorna();
            }
        }

        ActionListener listener = new ListenerAggiorna();
        aggiornaProprietario.addActionListener(listener);

        panel.add(aggiornaProprietario);
        return panel;
    }

    //metodo per aggiornare il proprietario dell'auto in blockchain
    private void aggiorna() {
        //recupero il contenuto delle textField
        String codiceFiscale = textFieldCF.getText();
        String cognome = textFieldCognome.getText();
        String nome = textFieldNome.getText();

        try {
            //carico l'aggiornamento in blockchain
            contract.setCarOwner(BigInteger.valueOf(id),codiceFiscale,cognome,nome).send();
            System.out.println("action done with success");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
