package Trabalho_RA3.Exporter;

import java.awt.*;

public class Right_Bar extends Bar{
    public Right_Bar(Screen screen, Color color, int x, int y, int largura, int altura){
        super(screen, color, x, y, largura, altura);
    }

    public void paint(Graphics g){
        this.x = screen.getWidth() - largura;
        this.altura = screen.getHeight() - (screen.top_bar.altura + screen.bot_bar.altura);
        super.paint(g);
    }
}