package Trabalho_RA3.Exporter;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse implements MouseListener {
    private Screen screen;

    public Mouse(Screen screen){
        this.screen = screen;
    }

    public void mouseClicked(MouseEvent e){
        try {
            screen.desenho.handleClick(e.getX(), e.getY());
        }catch(Exception except){
            except.printStackTrace();
        }
    }

    public void mouseEntered(MouseEvent e){

    }

    public void mouseExited(MouseEvent e){

    }

    public void mousePressed(MouseEvent e){

    }

    public void mouseReleased(MouseEvent e){

    }
}
