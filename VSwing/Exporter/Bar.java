package VSwing.Exporter;

import javax.swing.*;
import java.awt.*;

public class Bar extends JComponent{
    protected Screen screen;
    protected Color color;
    protected int x;
    protected int y;
    protected int largura;
    protected int altura;


    public Bar(Screen screen, Color color, int x, int y, int largura, int altura){
        this.color = color;
        this.screen = screen;
        this.x = x;
        this.y = y;
        this.largura = largura;
        this.altura = altura;
    }

    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(color);
        g2d.fillRect(x, y, largura, altura);
    }
}
