package gui;

import cars.Cars;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

public class FrameAcquirente extends JFrame {

    private JPanel panel;
    private JPanel panelsInfoCar; //ospiterà tutti i panels che mostreranno le info delle varie auto recuperate da blockchain
    private JPanel panelInfo;
    private JLabel labelInfo;
    private JLabel labelTarga;
    private JLabel labelTelaio;
    private JLabel labelMarca;
    private JLabel labelModello;
    private JLabel labelAnno;
    private JLabel labelAlimentazione;
    private JLabel labelPotenza;
    private JLabel labelOptionals;
    private JTextField fieldTarga;
    private JTextField fieldTelaio;
    private JTextField fieldMarca;
    private JTextField fieldModello;
    private JTextField fieldAnno;
    private JTextField fieldAlimentazione;
    private JTextField fieldPotenza;
    private JTextArea areaOptionals;
    private Cars contract;

    public FrameAcquirente(Cars contract) {
        this.contract = contract;

        panel = new JPanel();

        panelInfo = setPanelInfo();
        panelsInfoCar = setPanelInfoCars();

        panel.add(panelInfo);
        panel.add(panelsInfoCar);
        add(panel);
    }

    private JPanel setPanelInfo() {
        JPanel panel = new JPanel();
        labelInfo = new JLabel("SEZIONE CLIENTE");
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
            String carInfo = contract.getCarInfo(BigInteger.valueOf(id)).send();

            //costruisco il contenuto della combobox
            int vTarga = carInfo.indexOf(",");
            String targa = carInfo.substring(0, vTarga);                //TARGA
            sub = carInfo.substring(vTarga + 1, carInfo.length());
            int vTelaio = sub.indexOf(",");
            String copia = sub;
            String telaio = copia.substring(0, vTelaio);                //NUMERO DI TELAIO
            sub = sub.substring(vTelaio + 1, sub.length());
            int vMarca = sub.indexOf(",");
            copia = sub;
            String marca = copia.substring(0, vMarca);                  //CASA AUTOMOBILISTICA
            sub = sub.substring(vMarca + 1, sub.length());
            int vModello = sub.indexOf(",");
            copia = sub;
            String modello = copia.substring(0, vModello);              //MODELLO
            sub = sub.substring(vModello + 1, sub.length());
            int vAnno = sub.indexOf(",");
            copia = sub;
            String anno = copia.substring(0, vAnno);                    //ANNO
            sub = sub.substring(vAnno + 1, sub.length());
            int vAlimentazione = sub.indexOf(",");
            copia = sub;
            String alimentazione = copia.substring(0, vAlimentazione);  //ALIMENTAZIONE
            sub = sub.substring(vAlimentazione + 1, sub.length());
            int vCilindrata = sub.indexOf(",");
            copia = sub;
            String cilindrata = copia.substring(0, vCilindrata);        //CILINDRATA
            sub = sub.substring(vCilindrata + 1, sub.length());
            int vPotenza = sub.indexOf(",");
            copia = sub;
            String potenza = copia.substring(0, vPotenza);              //POTENZA
            sub = sub.substring(vPotenza + 1, sub.length());
            String optional = sub.replace(",","\n");  //OPTIONAL

            //carico le textfield
            fieldTarga.setText(targa);
            fieldTarga.setEditable(false);
            fieldTelaio.setText(telaio);
            fieldTelaio.setEditable(false);
            fieldMarca.setText(marca);
            fieldMarca.setEditable(false);
            fieldModello.setText(modello);
            fieldModello.setEditable(false);
            fieldAnno.setText(anno);
            fieldAnno.setEditable(false);
            fieldAlimentazione.setText(alimentazione);
            fieldAlimentazione.setEditable(false);
            fieldPotenza.setText(potenza);
            fieldPotenza.setEditable(false);
            areaOptionals.setText(optional);
            areaOptionals.setEditable(false);
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

    private JPanel setPanelSuperiore() {
        JPanel panel = new JPanel();

        labelTarga = new JLabel("Targa");
        labelTarga.setFont(new Font("Times", Font.CENTER_BASELINE, 14));
        labelTarga.setForeground(new Color(14,44,114));
        fieldTarga = new JTextField(10);
        fieldTarga.setEditable(false);
        fieldTarga.setMaximumSize(fieldTarga.getPreferredSize());
        fieldTarga.setMinimumSize(fieldTarga.getPreferredSize());

        labelTelaio = new JLabel("Numero di telaio");
        labelTelaio.setFont(new Font("Times", Font.CENTER_BASELINE, 14));
        labelTelaio.setForeground(new Color(14,44,114));
        fieldTelaio = new JTextField(10);
        fieldTelaio.setEditable(false);

        panel.setLayout(new GridBagLayout());
        panel.add(labelTarga, new GridBagConstraints(0,0,1,1,0,0,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0,0,0,0),0,0));
        panel.add(fieldTarga, new GridBagConstraints(1,0,1,1,0,0,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0,0,0,0),0,0));
        panel.add(labelTelaio, new GridBagConstraints(0,1,1,1,0,0,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0,0,0,0),0,0));
        panel.add(fieldTelaio, new GridBagConstraints(1,1,1,1,0,0,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0,0,0,0),0,0));
        return panel;
    }

    private JPanel setPanelCentrale() {
        JPanel panel = new JPanel();

        labelMarca = new JLabel("Casa Automobilistica");
        labelMarca.setFont(new Font("Times", Font.CENTER_BASELINE, 14));
        labelMarca.setForeground(new Color(14,44,114));
        fieldMarca = new JTextField(10);
        fieldMarca.setEditable(false);

        labelModello = new JLabel("Modello");
        labelModello.setFont(new Font("Times", Font.CENTER_BASELINE, 14));
        labelModello.setForeground(new Color(14,44,114));
        fieldModello = new JTextField(10);
        fieldModello.setEditable(false);

        labelAnno = new JLabel("Anno");
        labelAnno.setFont(new Font("Times", Font.CENTER_BASELINE, 14));
        labelAnno.setForeground(new Color(14,44,114));
        fieldAnno = new JTextField(10);
        fieldAnno.setEditable(false);

        labelAlimentazione = new JLabel("Alimentazione");
        labelAlimentazione.setFont(new Font("Times", Font.CENTER_BASELINE, 14));
        labelAlimentazione.setForeground(new Color(14,44,114));
        fieldAlimentazione = new JTextField(10);
        fieldAlimentazione.setEditable(false);

        labelPotenza = new JLabel("Potenza");
        labelPotenza.setFont(new Font("Times", Font.CENTER_BASELINE, 14));
        labelPotenza.setForeground(new Color(14,44,114));
        fieldPotenza = new JTextField(10);
        fieldPotenza.setEditable(false);

        panel.setLayout(new GridLayout(5,2));
        panel.add(labelMarca);panel.add(fieldMarca);
        panel.add(labelModello);panel.add(fieldModello);
        panel.add(labelAnno);panel.add(fieldAnno);
        panel.add(labelAlimentazione);panel.add(fieldAlimentazione);
        panel.add(labelPotenza);panel.add(fieldPotenza);
        return panel;
    }

    private JPanel setPanelInferiore() {
        JPanel panel = new JPanel();

        labelOptionals = new JLabel("Optionals");
        labelOptionals.setFont(new Font("Times", Font.CENTER_BASELINE, 14));
        labelOptionals.setForeground(new Color(14,44,114));
        areaOptionals = new JTextArea(10,20);
        areaOptionals.setEditable(true);
        JScrollPane scroll=new JScrollPane(areaOptionals);
        scroll.setBorder(null);

        panel.add(labelOptionals);panel.add(scroll);
        return panel;
    }
}
