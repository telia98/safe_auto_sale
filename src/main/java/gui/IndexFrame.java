package gui;

import cars.Cars;

import javax.swing.*;

public class IndexFrame {

    public IndexFrame(Cars contract) {

        JFrame frame=new FrameIniziale(contract);

        frame.setSize(650, 300);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
