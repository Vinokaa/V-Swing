package VSwing.Exporter;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

class Left_Bar extends Bar{
    private Border ICON_BORDER = BorderFactory.createLineBorder(new Color(159, 159, 159), 1);
    private Color ICON_COLOR = Color.BLACK;
    private Color BUTTON_COLOR = new Color(127, 127, 127);
    protected JButton icone1;
    protected JButton icone2;
    protected JButton icone3;
    protected JButton icone4;

    public Left_Bar(Screen screen, Color color, int x, int y, int largura, int altura){
        super(screen, color, x, y, largura, altura);

        LeftButtonAction leftButtonAction = new LeftButtonAction(screen);

        MoveIcon icon1 = new MoveIcon(x, y, largura, largura);
        this.icone1 = new JButton(icon1);
        icone1.setBackground(BUTTON_COLOR);
        icone1.setBorder(ICON_BORDER);
        icone1.addActionListener(leftButtonAction);
        screen.frame.add(icone1);

        PolygonIcon icon2 = new PolygonIcon(x, y*2, largura, largura);
        this.icone2 = new JButton(icon2);
        icone2.setBackground(BUTTON_COLOR);
        icone2.setBorder(ICON_BORDER);
        icone2.addActionListener(leftButtonAction);
        screen.frame.add(icone2);

        CircleIcon icon3 = new CircleIcon(x, y*3, (int) (largura/1.5), (int) (largura/1.5));
        this.icone3 = new JButton(icon3);
        icone3.setBackground(BUTTON_COLOR);
        icone3.setBorder(ICON_BORDER);
        icone3.addActionListener(leftButtonAction);
        screen.frame.add(icone3);

        LineIcon icon4 = new LineIcon(largura);
        this.icone4 = new JButton(icon4);
        icone4.setBackground(BUTTON_COLOR);
        icone4.setBorder(ICON_BORDER);
        icone4.addActionListener(leftButtonAction);
        screen.frame.add(icone4);
    }

    public void paint(Graphics g){
        this.altura = screen.getHeight() - (screen.top_bar.altura + screen.bot_bar.altura);

        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(ICON_COLOR);

        icone1.setBounds(x, y, largura, largura);
        icone1.repaint();

        icone2.setBounds(x, y*2 + 1, largura, largura);
        icone2.repaint();

        icone3.setBounds(x, y*3 + 2, largura, largura);
        icone3.repaint();

        icone4.setBounds(x, y*4 + 3, largura, largura);
        icone4.repaint();
    }
}

class PolygonIcon implements Icon{
    private int x;
    private int y;
    private int largura;
    private int altura;
    private int PADDING = 4;

    public PolygonIcon(int x, int y, int largura, int altura){
        this.x = x;
        this.y = y;
        this.largura = largura;
        this.altura = altura;
    }

    public int getIconWidth(){ return largura; }

    public int getIconHeight(){ return altura; }

    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g;
        int largura = 38;
        int x_center = (int) x + largura / 2;
        int y_center = (int) y + largura / 2;

        g2d.setColor(Color.BLACK);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawPolygon(new int[]{x + PADDING * 2, x_center, x + largura - PADDING * 2, (x + largura - PADDING * 2 + x_center) / 2, (x + PADDING * 2 + x_center) / 2},
                        new int[]{y + PADDING * 2, y_center, y + PADDING * 2, y + largura - PADDING * 2, y + largura - PADDING * 2},
                5);
    }
}

class MoveIcon implements Icon{
    private int x;
    private int y;
    private int largura;
    private int altura;
    private int PADDING = 4;

    public MoveIcon(int x, int y, int largura, int altura){
        this.x = x;
        this.y = y;
        this.largura = largura;
        this.altura = altura;
    }

    public int getIconWidth(){ return largura; }

    public int getIconHeight(){ return altura; }

    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g;
        int x_center = (int) x + largura / 2;
        int y_center = (int) y + largura / 2;
        int arrow_size = 4;

        g2d.setColor(Color.BLACK);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawLine(x_center, y + PADDING * 2, x_center, y + largura - PADDING * 2);
        g2d.drawLine(x + PADDING * 2, y_center, x + largura - PADDING * 2, y_center);

        // Seta superior
        g2d.drawPolygon(new int[]{x_center, x_center + arrow_size, x_center - arrow_size}, new int[]{y + PADDING * 2, y + PADDING * 2 + arrow_size, y + PADDING * 2 + arrow_size}, 3);

        // Seta Inferior
        g2d.drawPolygon(new int[]{x_center, x_center + arrow_size, x_center - arrow_size}, new int[]{y + largura - PADDING * 2, y + largura - PADDING * 2 - arrow_size, y + largura - PADDING * 2 - arrow_size}, 3);

        // Seta Esquerda
        g2d.drawPolygon(new int[]{x + PADDING * 2, x + PADDING * 2 + arrow_size, x + PADDING * 2 + arrow_size}, new int[]{y_center, y_center + arrow_size, y_center - arrow_size}, 3);

        // Seta Direita
        g2d.drawPolygon(new int[]{x + largura - PADDING * 2, x + largura - PADDING * 2 - arrow_size, x + largura - PADDING * 2 - arrow_size}, new int[]{y_center, y_center + arrow_size, y_center - arrow_size}, 3);
    }
}

class CircleIcon implements Icon{
    private int x;
    private int y;
    private int largura;
    private int altura;

    public CircleIcon(int x, int y, int largura, int altura){
        this.x = x;
        this.y = y;
        this.largura = largura;
        this.altura = altura;
    }

    public int getIconWidth(){ return largura; }

    public int getIconHeight(){ return altura; }

    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLACK);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.draw(new Ellipse2D.Double(x, y, largura, largura));
    }
}

class LineIcon implements Icon{
    private int largura;
    private int PADDING = 4;

    public LineIcon(int largura){
        this.largura = largura;
    }

    public int getIconWidth(){
        return largura - (PADDING*2);
    }

    public int getIconHeight(){
        return largura - (PADDING*2);
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLACK);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.draw(new Line2D.Double(x+PADDING, y+PADDING, largura - (PADDING*2), largura - (PADDING*2)));
    }
}

class LeftButtonAction implements ActionListener {
    private Screen screen;

    public LeftButtonAction(Screen screen){
        this.screen = screen;
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == screen.left_bar.icone1){
            screen.desenho.setMode(Mode.MOVE);
        }else if(e.getSource() == screen.left_bar.icone2){
            screen.desenho.setMode(Mode.POLY);
        }else if(e.getSource() == screen.left_bar.icone3){
            screen.desenho.setMode(Mode.CIRCLE);
        }else{
            screen.desenho.setMode(Mode.LINE);
        }

        screen.requestFocus();
    }
}