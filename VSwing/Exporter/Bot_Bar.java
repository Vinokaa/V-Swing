package VSwing.Exporter;

import java.awt.*;

public class Bot_Bar extends Bar{
    public Bot_Bar(Screen screen, Color color, int x, int y, int largura, int altura){
        super(screen, color, x, y, largura, altura);
    }

    public void paint(Graphics g){
        this.largura = screen.getWidth();
        this.y = screen.getHeight() - altura;

        super.paint(g);
    }
}