package gui;

import cars.Cars;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

public class FrameMeccanico extends JFrame {

    private JPanel panel;
    private JPanel panelsInfoCar; //ospiterà tutti i panels che mostreranno le info delle varie auto recuperate da blockchain
    private JPanel panelInfo;
    private JPanel panelBottone;
    private JLabel labelInfo;
    private JLabel labelKm;
    private JLabel labelValore;
    private JLabel labelStato;
    private JTextField fieldKm;
    private JTextField fieldValore;
    private JTextArea areaStato;
    private JButton aggiorna;
    private Cars contract;
    private int id;

    public FrameMeccanico(Cars contract) {
        this.contract = contract;

        panel = new JPanel();

        panelInfo = setPanelInfo(); //dicitura sezione
        panelsInfoCar = setPanelInfoCars(); //combobox + pannello dati
        //gestisco il pannello del bottone
        panelBottone = setPanelBottone();

        panel.setLayout(new BorderLayout());
        panel.add(panelInfo,BorderLayout.NORTH);
        panel.add(panelsInfoCar,BorderLayout.CENTER);
        panel.add(panelBottone,BorderLayout.SOUTH);
        add(panel);
    }

    private JPanel setPanelInfo() {
        JPanel panel = new JPanel();
        labelInfo = new JLabel("SEZIONE MECCANICO");
        labelInfo.setFont(new Font("Times", Font.CENTER_BASELINE, 20));
        labelInfo.setForeground(new Color(14,44,114));
        labelInfo.setHorizontalAlignment(JLabel.CENTER);

        panel.add(labelInfo);
        return panel;
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
        label.setForeground(new Color(14,44,114));

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
                int vTarga=carInfo.indexOf(",");
                String sub=carInfo.substring(vTarga+1,carInfo.length());
                int vTelaio=sub.indexOf(",");
                sub=sub.substring(vTelaio+1,sub.length());
                int vMarca=sub.indexOf(",");
                String copia = sub;
                String marca = copia.substring(0,vMarca);
                sub=sub.substring(vMarca+1,sub.length());
                int vModello=sub.indexOf(",");
                copia=sub;
                String modello =copia.substring(0,vModello);
                sub=sub.substring(vModello+1,sub.length());

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

        try {
            //recupero le informazioni singole delle auto (targa,marca,etc...)
            String carState = contract.getCarState(BigInteger.valueOf(id)).send();

            //costruisco il contenuto della combobox
            int vKM = carState.indexOf(",");
            String km = carState.substring(0, vKM);                                     //KM
            sub = carState.substring(vKM + 1, carState.length());
            int vValore = sub.indexOf(",");
            String copia = sub;
            String valore = copia.substring(0, vValore);                                //VALORE
            sub = sub.substring(vValore + 1, sub.length());
            String stato = sub.replace(",","\n");                      //STATO

            //carico le textfield
            fieldKm.setText(km);
            fieldKm.setEditable(true);
            fieldValore.setText(valore);
            fieldValore.setEditable(true);
            areaStato.setText(stato);
            areaStato.setEditable(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JPanel createSinglePanel(){
        JPanel panel = new JPanel();
        JPanel panelSuperiore = setPanelSuperiore();
        JPanel panelInferiore = setPanelInferiore();

        panel.setLayout(new GridLayout(2,1));
        panel.add(panelSuperiore);
        panel.add(panelInferiore);
        return panel;
    }

    private JPanel setPanelSuperiore() {
        JPanel panel = new JPanel();

        labelKm = new JLabel("Km registrati");
        labelKm.setFont(new Font("Times", Font.CENTER_BASELINE, 14));
        labelKm.setForeground(new Color(14,44,114));
        fieldKm= new JTextField(10);
        fieldKm.setEditable(false);
        fieldKm.setMaximumSize(fieldKm.getPreferredSize());
        fieldKm.setMinimumSize(fieldKm.getPreferredSize());

        labelValore = new JLabel("valore dell'auto in €");
        labelValore.setFont(new Font("Times", Font.CENTER_BASELINE, 14));
        labelValore.setForeground(new Color(14,44,114));
        fieldValore = new JTextField(10);
        fieldValore.setEditable(false);

        panel.setLayout(new GridBagLayout());
        panel.add(labelKm, new GridBagConstraints(0,0,1,1,0,0,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0,0,0,0),0,0));
        panel.add(fieldKm, new GridBagConstraints(1,0,1,1,0,0,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0,0,0,0),0,0));
        panel.add(labelValore, new GridBagConstraints(0,1,1,1,0,0,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0,0,0,0),0,0));
        panel.add(fieldValore, new GridBagConstraints(1,1,1,1,0,0,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0,0,0,0),0,0));
        return panel;
    }

    private JPanel setPanelInferiore() {
        JPanel panel = new JPanel();

        labelStato = new JLabel("stato dell'auto");
        labelStato.setFont(new Font("Times", Font.CENTER_BASELINE, 14));
        labelStato.setForeground(new Color(14,44,114));
        areaStato = new JTextArea(10,20);
        areaStato.setEditable(true);
        JScrollPane scroll=new JScrollPane(areaStato);
        scroll.setBorder(null);

        panel.add(labelStato);panel.add(scroll);
        return panel;
    }

    private JPanel setPanelBottone() {
        JPanel panel = new JPanel();

        aggiorna = new JButton("AGGIORNA");
        aggiorna.setFont(new Font("Times", Font.CENTER_BASELINE, 20));
        aggiorna.setForeground(new Color(14,44,114));

        class ListenerAggiorna implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                aggiorna();
            }
        }

        ActionListener listener = new ListenerAggiorna();
        aggiorna.addActionListener(listener);

        panel.add(aggiorna);
        return panel;
    }

    //metodo per aggiornare il proprietario dell'auto in blockchain
    private void aggiorna() {
        //recupero il contenuto delle textField
        String km = fieldKm.getText();
        String valore = fieldValore.getText();
        String stato = areaStato.getText();

        try {
            //carico l'aggiornamento in blockchain
            contract.setStateCar(BigInteger.valueOf(id),stato,new BigInteger(km),new BigInteger(valore)).send();
            System.out.println("action done with success");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
