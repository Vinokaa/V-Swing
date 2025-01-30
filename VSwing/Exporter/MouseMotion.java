package VSwing.Exporter;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

class MouseMotion implements MouseMotionListener {
    private Screen screen;

    public MouseMotion(Screen screen){
        this.screen = screen;
    }

    public void mouseMoved(MouseEvent e){

    }

    public void mouseDragged(MouseEvent e){
        try {
            screen.desenho.handleDrag(e.getX(), e.getY());
        }catch(OutOfDrawingBoardException except){
            except.printStackTrace();
        }
    }
}
