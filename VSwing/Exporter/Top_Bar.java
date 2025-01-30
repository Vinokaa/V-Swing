package VSwing.Exporter;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Top_Bar extends Bar {
    private int font_size;
    private int icon_size = 26;
    private int botao1_size = 160;
    protected JButton botao1;
    protected JButton botao2;
    private Border BUTTON_BORDER = BorderFactory.createLineBorder(new Color(159, 159, 159), 1);
    private Color BUTTON_COLOR = new Color(127, 127, 127);

    public Top_Bar(Screen screen, Color color, int x, int y, int largura, int altura, int font_size){
        super(screen, color, x, y, largura, altura);
        this.font_size = font_size;

        TopButtonAction topButtonAction = new TopButtonAction(screen);

        this.botao1 = new JButton("Selecionar Imagem");
        botao1.setBackground(BUTTON_COLOR);
        botao1.setBorder(BUTTON_BORDER);
        botao1.setFocusPainted(false);
        botao1.addActionListener(topButtonAction);
        screen.frame.add(botao1);

        this.botao2 = new JButton("Exportar");
        botao2.setBackground(BUTTON_COLOR);
        botao2.setBorder(BUTTON_BORDER);
        botao2.setFocusPainted(false);
        botao2.addActionListener(topButtonAction);
        screen.frame.add(botao2);
    }

    public void paint(Graphics g){
        this.largura = screen.getWidth();

        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.YELLOW);
        g2d.fillOval((altura - icon_size) / 2, (altura - icon_size) / 2, icon_size, icon_size);

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("SansSerif", Font.BOLD, font_size));
        g2d.drawString("V", Screen.text_align(20, font_size), Screen.ptToPix(font_size) + Screen.text_align(19, font_size));

        botao1.setBounds(2 * (altura - icon_size) + icon_size, (altura - icon_size) / 2, botao1_size, icon_size);
        botao1.repaint();

        botao2.setBounds(4 * (altura - icon_size) + icon_size + botao1_size, (altura - icon_size) / 2, botao1_size, icon_size);
        botao2.repaint();
    }
}

class TopButtonAction implements ActionListener{
    private Screen screen;

    public TopButtonAction(Screen screen){
        this.screen = screen;
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == screen.top_bar.botao1) {
            JFileChooser fc = new JFileChooser(new File(System.getProperty("user.dir")));

            int returnVal = fc.showOpenDialog(screen);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                screen.quadro.setImgFile(fc.getSelectedFile());
            }
        }else{
            screen.desenho.exportar();
        }
    }
}