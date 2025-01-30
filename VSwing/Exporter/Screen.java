package VSwing.Exporter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

class Screen extends JPanel{
    private int LARGURA = 1920 - 14;
    private int ALTURA = 870 - 30;
    private int old_width = 0;
    private int old_height = 0;
    private int FONT_SIZE = 16;
    protected Color MAIN_COLOR = new Color(48, 48, 48);
    protected Color SEC_COLOR = new Color(112, 112, 112);
    protected Top_Bar top_bar;
    protected Left_Bar left_bar;
    protected Right_Bar right_bar;
    protected Bot_Bar bot_bar;
    protected Quadro quadro;
    protected Desenho desenho;
    protected JFrame frame;
    private boolean first_resize = true;

    public Screen(String nome){
        frame = new JFrame(nome);

        this.top_bar = new Top_Bar(this, MAIN_COLOR, 0, 0, LARGURA, 38, FONT_SIZE);
        this.bot_bar = new Bot_Bar(this, MAIN_COLOR, 0, ALTURA - 38, LARGURA, 38);
        this.right_bar = new Right_Bar(this, MAIN_COLOR, LARGURA - 304, top_bar.altura, 304, ALTURA - (top_bar.altura + bot_bar.altura));
        this.left_bar = new Left_Bar(this, MAIN_COLOR, 0, top_bar.altura, 38, ALTURA - (top_bar.altura + bot_bar.altura));
        this.quadro = new Quadro(this, 38, 38, LARGURA - (left_bar.largura + right_bar.largura), ALTURA - (top_bar.altura + bot_bar.altura));
        this.desenho = new Desenho(this);

        add(top_bar);
        add(bot_bar);
        add(right_bar);
        add(left_bar);
        add(quadro);
        add(desenho);

        frame.add(this);
        frame.setSize(LARGURA, ALTURA);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        KeyListener teclado = new Teclado(this);
        MouseListener mouse = new Mouse(this);
        MouseMotion mouseMotion = new MouseMotion(this);

        addKeyListener(teclado);
        addMouseListener(mouse);
        addMouseMotionListener(mouseMotion);
        setFocusable(true);
    }

    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(SEC_COLOR);
        g2d.fillRect(top_bar.altura, left_bar.largura, getWidth() - (left_bar.largura + right_bar.largura), getHeight() - (top_bar.altura + bot_bar.altura));

        g2d.setColor(MAIN_COLOR);
        top_bar.paint(g);
        left_bar.paint(g);
        right_bar.paint(g);
        bot_bar.paint(g);
        quadro.paint(g);
        desenho.paint(g);
    }

    static int ptToPix(int pt){ return (int) Math.round(pt/1.33); }

    static int text_align(int shape_center, int font_size){ return shape_center - ptToPix(font_size/2); }
}