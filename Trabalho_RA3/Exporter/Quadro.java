package Trabalho_RA3.Exporter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Quadro extends JComponent{
    private Screen screen;
    protected BufferedImage img;
    private int x;
    private int y;
    private int largura;
    private int altura;
    private File img_file;
    protected int fit_width;
    protected int fit_height;

    public Quadro(Screen screen, int x, int y, int largura, int altura){
        this.screen = screen;
        this.x = x;
        this.y = y;
        this.largura = largura;
        this.altura = altura;
    }

    public void paint(Graphics g){
        this.largura = screen.getWidth() - screen.left_bar.largura - screen.right_bar.largura;
        this.altura = screen.getHeight() - (screen.top_bar.altura + screen.bot_bar.altura);

        Graphics2D g2d = (Graphics2D) g;

        if(img_file != null) {
            try {
                this.img = ImageIO.read(img_file);

                int img_width = img.getWidth();
                int img_height = img.getHeight();

                double aspect_ratio = aspect_ratio(largura, img_width, altura, img_height);

                fit_width = (int) Math.round(img_width * aspect_ratio);
                fit_height = (int) Math.round(img_height * aspect_ratio);

                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
                g2d.drawImage(img, x, y, fit_width, fit_height, this);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            } catch (IOException e) {
                System.out.println("Não foi possível carregar a imagem");
            }
        }
    }

    protected static double aspect_ratio(int largura, int img_width, int altura, int img_height){
        if(img_width >= img_height){
            return largura / (double) img_width;
        }else{
            return altura / (double) img_height;
        }
    }

    public void setImgFile(File file){
        this.img_file = file;
        screen.repaint();
    }
}
