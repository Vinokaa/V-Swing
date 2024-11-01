package Trabalho_RA3.Exporter;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Teclado implements KeyListener{
    private Screen screen;

    public Teclado(Screen screen){
        this.screen = screen;
    }

    public void keyTyped(KeyEvent e){

    }

    public void keyPressed(KeyEvent e){
        screen.desenho.handleKey(e);
    }

    public void keyReleased(KeyEvent e){

    }
}
