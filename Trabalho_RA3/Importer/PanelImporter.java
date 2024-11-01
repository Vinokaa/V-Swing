package Trabalho_RA3.Importer;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PanelImporter extends JPanel {
    private JFrame frame;
    private int LARGURA = 1920 - 14;;
    private int ALTURA = 870 - 30;
    private DesenhoImporter desenho;

    public PanelImporter(String nome){
        frame = new JFrame(nome);

        desenho = new DesenhoImporter(0, 0, 800, 500, new File("mustang2.txt"));
        add(desenho);

        frame.add(this);
        frame.setSize(LARGURA, ALTURA);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void paint(Graphics g){
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        desenho.paint(g);
    }
}
