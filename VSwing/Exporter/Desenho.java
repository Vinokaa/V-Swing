package VSwing.Exporter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Desenho extends JComponent implements Serializable {
    transient private Screen screen;
    transient private ArrayList<Shape> poligonos;
    private ArrayList<Shape> resized_polygons;
    transient private ArrayList<Integer> x_points;
    transient private ArrayList<Integer> y_points;
    transient private ArrayList<Integer> poly_initial_width;
    transient private ArrayList<Integer> poly_initial_height;
    transient private int[] old_linha = new int[2];
    transient private ArrayList<Line2D.Double> linhas;
    transient private int[] circle_coords_x;
    transient private int[] circle_coords_y;
    transient private byte contador_circle;
    transient private Mode mode = Mode.NONE;
    transient private int selected_polygon = -1;
    transient private int[] last_resize_click = new int[2];

    public Desenho(Screen screen){
        this.screen = screen;
        this.poligonos = new ArrayList<Shape>();
        this.x_points = new ArrayList<Integer>();
        this.y_points = new ArrayList<Integer>();
        this.poly_initial_width = new ArrayList<Integer>();
        this.poly_initial_height = new ArrayList<Integer>();
    }

    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLACK);

        if(linhas != null){
            for(Line2D.Double l : linhas) g2d.draw(l);
        }

        if(!poligonos.isEmpty()) {
            resized_polygons = new ArrayList<Shape>();

            for(Shape s : poligonos){
                if(s instanceof Polygon) {
                    Polygon p = (Polygon) s;
                    int[] xpoints = new int[p.npoints];
                    int[] ypoints = new int[p.npoints];

                    for (int i = 0; i < p.npoints; i++) {
                        xpoints[i] = p.xpoints[i];
                        ypoints[i] = p.ypoints[i];
                    }

                    resized_polygons.add(new Polygon(xpoints, ypoints, p.npoints));
                }else if(s instanceof Ellipse2D.Double){
                    Ellipse2D.Double e = (Ellipse2D.Double) s;

                    resized_polygons.add(new Ellipse2D.Double(e.getX(), e.getY(), e.getWidth(), e.getHeight()));
                }else if(s instanceof Line2D.Double){
                    Line2D.Double l = (Line2D.Double) s;

                    resized_polygons.add(new Line2D.Double(l.x1, l.y1, l.x2, l.y2));
                }
            }

            for(int i = 0; i < resized_polygons.size(); i++) {
                Shape s = resized_polygons.get(i);
                double poly_aspect_ratio = Quadro.aspect_ratio(screen.quadro.fit_width, poly_initial_width.get(i), screen.quadro.fit_height, poly_initial_height.get(i));

                if(s instanceof Polygon) {
                    Polygon p = (Polygon) s;

                    for (int j = 0; j < p.npoints; j++) {
                        p.xpoints[j] = (int) Math.round((p.xpoints[j] - screen.left_bar.largura) * poly_aspect_ratio + screen.left_bar.largura);
                        p.ypoints[j] = (int) Math.round((p.ypoints[j] - screen.top_bar.altura) * poly_aspect_ratio + screen.top_bar.altura);
                    }

                    g2d.drawPolygon(p);
                }else if(s instanceof Ellipse2D.Double){
                    Ellipse2D.Double e = (Ellipse2D.Double) s;

                    int x_final = (int) Math.round(((e.x + e.width) - screen.left_bar.largura) * poly_aspect_ratio + screen.left_bar.largura);
                    int y_final = (int) Math.round(((e.y + e.height) - screen.top_bar.altura) * poly_aspect_ratio + screen.top_bar.altura);
                    e.x = (int) Math.round((e.x - screen.left_bar.largura) * poly_aspect_ratio + screen.left_bar.largura);
                    e.y = (int) Math.round((e.y - screen.top_bar.altura) * poly_aspect_ratio + screen.top_bar.altura);
                    e.width = x_final - e.x;
                    e.height = y_final - e.y;

                    g2d.draw(e);
                }else if(s instanceof Line2D.Double){
                    Line2D.Double l = (Line2D.Double) s;

                    l.x1 = (int) Math.round((l.x1 - screen.left_bar.largura) * poly_aspect_ratio + screen.left_bar.largura);
                    l.y1 = (int) Math.round((l.y1 - screen.top_bar.altura) * poly_aspect_ratio + screen.top_bar.altura);
                    l.x2 = (int) Math.round((l.x2 - screen.left_bar.largura) * poly_aspect_ratio + screen.left_bar.largura);
                    l.y2 = (int) Math.round((l.y2 - screen.top_bar.altura) * poly_aspect_ratio + screen.top_bar.altura);

                    g2d.draw(l);
                }

                if(mode == Mode.MOVE) {
                    if(i == selected_polygon){
                        g2d.setColor(Color.RED);
                    }else {
                        g2d.setColor(Color.BLUE);
                    }

                    g2d.draw(s.getBounds());
                    g2d.setColor(Color.BLACK);
                }
            }
        }
    }

    protected void handleClick(int x, int y) throws OutOfDrawingBoardException, NullBackgroundImageException{
        if(x < screen.left_bar.largura || x > screen.right_bar.x || y < screen.top_bar.altura || y > screen.bot_bar.y){
            throw new OutOfDrawingBoardException();
        }

        if(screen.quadro.img == null) throw new NullBackgroundImageException();

        if(mode == Mode.POLY || mode == Mode.LINE) {
            if (linhas == null) {
                linhas = new ArrayList<Line2D.Double>();
            } else {
                linhas.add(new Line2D.Double(old_linha[0], old_linha[1], x, y));
                screen.repaint(new Rectangle(screen.left_bar.largura, screen.top_bar.altura, screen.quadro.fit_width, screen.quadro.fit_height));
            }

            old_linha = new int[]{x, y};

            x_points.add(x);
            y_points.add(y);
        }else if(mode == Mode.MOVE){
            ArrayList<Shape> opcoes = new ArrayList<Shape>();

            for(Shape p : resized_polygons){
                if(p.getBounds().contains(x, y)){
                    opcoes.add(p);
                }
            }

            if (!opcoes.isEmpty()){
                Shape mais_proximo = opcoes.get(0);
                int meio_x, meio_y, meio_x_prox, meio_y_prox;

                for(int i = 1; i < opcoes.size(); i++){
                    meio_x = (2 * opcoes.get(i).getBounds().x + opcoes.get(i).getBounds().width) / 2;
                    meio_y = (2 * opcoes.get(i).getBounds().y + opcoes.get(i).getBounds().height) / 2;
                    meio_x_prox = (2 * mais_proximo.getBounds().x + mais_proximo.getBounds().width) / 2;
                    meio_y_prox = (2 * mais_proximo.getBounds().y + mais_proximo.getBounds().height) / 2;

                    if(Math.sqrt(Math.pow(meio_x - x, 2) + Math.pow(meio_y - y, 2)) < Math.sqrt(Math.pow(meio_x_prox - x, 2) + Math.pow(meio_y_prox - y, 2))){
                        mais_proximo = opcoes.get(i);
                    }
                }

                selected_polygon = resized_polygons.indexOf(mais_proximo);
                last_resize_click = new int[]{x, y};
                screen.repaint(new Rectangle(screen.left_bar.largura, screen.top_bar.altura, screen.quadro.fit_width, screen.quadro.fit_height));
            }
        }else if(mode == Mode.CIRCLE){
            if(circle_coords_x == null){
                circle_coords_x = new int[4];
                circle_coords_y = new int[4];
                contador_circle = 0;
            }else{
                contador_circle++;
            }

            circle_coords_x[contador_circle] = x;
            circle_coords_y[contador_circle] = y;

            if (contador_circle == 3){
                int x_menor = Arrays.stream(circle_coords_x).min().getAsInt();
                int x_maior = Arrays.stream(circle_coords_x).max().getAsInt();
                int y_menor = Arrays.stream(circle_coords_y).min().getAsInt();
                int y_maior = Arrays.stream(circle_coords_y).max().getAsInt();

                poligonos.add(new Ellipse2D.Double(x_menor, y_menor, x_maior-x_menor, y_maior-y_menor));
                poly_initial_width.add(screen.quadro.fit_width);
                poly_initial_height.add(screen.quadro.fit_height);

                circle_coords_x = null;
                circle_coords_y = null;
                screen.repaint(new Rectangle(screen.left_bar.largura, screen.top_bar.altura, screen.quadro.fit_width, screen.quadro.fit_height));
            }
        }
    }

    protected void handleKey(KeyEvent e){
        switch(e.getKeyCode()) {
            case 10:
                // ENTER
                if(!x_points.isEmpty()) {
                    if(mode == Mode.POLY) {
                        int[] x_array = new int[x_points.size()];
                        int[] y_array = new int[y_points.size()];

                        for (int i = 0; i < x_points.size(); i++) {
                            x_array[i] = x_points.get(i);
                            y_array[i] = y_points.get(i);
                        }

                        poligonos.add(new Polygon(x_array, y_array, x_array.length));

                        poly_initial_width.add(screen.quadro.fit_width);
                        poly_initial_height.add(screen.quadro.fit_height);
                    }else{
                        for(Line2D.Double l : linhas){
                            poligonos.add(l);
                            poly_initial_width.add(screen.quadro.fit_width);
                            poly_initial_height.add(screen.quadro.fit_height);
                        }
                    }

                    linhas = null;
                    x_points.clear();
                    y_points.clear();
                    screen.repaint(new Rectangle(screen.left_bar.largura, screen.top_bar.altura, screen.quadro.fit_width, screen.quadro.fit_height));
                }
                break;
            case 27:
                // ESC
                if(!x_points.isEmpty()) {
                    linhas = null;
                    x_points.clear();
                    y_points.clear();
                }else{
                    mode = Mode.NONE;
                    selected_polygon = -1;
                }

                screen.repaint(new Rectangle(screen.left_bar.largura, screen.top_bar.altura, screen.quadro.fit_width, screen.quadro.fit_height));
                break;
            case 127:
                if(mode == Mode.MOVE){
                    poligonos.remove(selected_polygon);
                    resized_polygons.remove(selected_polygon);
                    poly_initial_width.remove(selected_polygon);
                    poly_initial_height.remove(selected_polygon);
                    selected_polygon = -1;
                    screen.repaint(new Rectangle(screen.left_bar.largura, screen.top_bar.altura, screen.quadro.fit_width, screen.quadro.fit_height));
                }
                break;
        }
    }

    public void handleDrag(int x, int y) throws OutOfDrawingBoardException{
        if(x < screen.left_bar.largura || x > screen.right_bar.x || y < screen.top_bar.altura || y > screen.bot_bar.y){
            throw new OutOfDrawingBoardException();
        }

        if(mode == Mode.MOVE && selected_polygon != -1){
            int diff_x = x - last_resize_click[0];
            int diff_y = y - last_resize_click[1];

            last_resize_click = new int[]{x,y};

            Shape s = poligonos.get(selected_polygon);

            if(s instanceof Polygon) {
                Polygon p = (Polygon) s;
                p.translate(diff_x, diff_y);
            }else if(s instanceof Ellipse2D.Double){
                Ellipse2D.Double e = (Ellipse2D.Double) s;
                e.x += diff_x;
                e.y += diff_y;
            }else if(s instanceof Line2D.Double){
                Line2D.Double l = (Line2D.Double) s;
                l.x1 += diff_x;
                l.y1 += diff_y;
                l.x2 += diff_x;
                l.y2 += diff_y;
            }

                screen.repaint(new Rectangle(screen.left_bar.largura, screen.top_bar.altura, screen.quadro.fit_width, screen.quadro.fit_height));

            try {
                Thread.sleep((long) 1000 / 60);
            }catch(InterruptedException e){

            }
        }
    }

    public void setMode(Mode modo){
        mode = modo;
        screen.repaint(new Rectangle(screen.left_bar.largura, screen.top_bar.altura, screen.quadro.fit_width, screen.quadro.fit_height));
    }

    public void exportar(){
        String[] opcoes = {"txt", "Objeto"};
        int escolha = JOptionPane.showInternalOptionDialog(screen.frame.getContentPane(), "Como deseja extrair o desenho?", "Escolha", 0, JOptionPane.QUESTION_MESSAGE, null, opcoes, "txt");

        String path = JOptionPane.showInputDialog(screen.frame.getContentPane(), "Digite o nome do arquivo de saída");

        if(escolha == 0) {
            File arquivo = new File(path + ".txt");

            try {
                arquivo.createNewFile();

                FileWriter writer = new FileWriter(arquivo);

                for (Shape s : resized_polygons) {
                    if (s instanceof Polygon) {
                        Polygon p = (Polygon) s;

                        writer.write("Polygon{");
                        for (int i = 0; i < p.npoints; i++) {
                            writer.write("[" + p.xpoints[i] + "," + p.ypoints[i] + "]");
                        }
                        writer.write("}\n");
                    } else if (s instanceof Ellipse2D.Double) {
                        Ellipse2D.Double e = (Ellipse2D.Double) s;

                        writer.write("Ellipse{" + (int) e.x + "," + (int) e.y + "," + (int) e.width + "," + (int) e.height + "}\n");
                    } else if (s instanceof Line2D.Double) {
                        Line2D.Double l = (Line2D.Double) s;

                        writer.write("Line{" + (int) l.x1 + "," + (int) l.y1 + "," + (int) l.x2 + "," + (int) l.y2 + "}\n");
                    }
                }

                writer.flush();
                writer.close();

                JOptionPane.showMessageDialog(screen.frame.getContentPane(), "Desenho exportado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(screen.frame.getContentPane(), "Não foi possível exportar o desenho.", "Falha", JOptionPane.INFORMATION_MESSAGE);
            }
        }else{
            try {
                FileOutputStream arquivo = new FileOutputStream(path + ".ser");
                ObjectOutputStream gravador = new ObjectOutputStream(arquivo);

                gravador.writeObject(this);

                gravador.close();
                arquivo.close();

                JOptionPane.showMessageDialog(screen.frame.getContentPane(), "Desenho exportado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }catch(Exception e){
                JOptionPane.showMessageDialog(screen.frame.getContentPane(), "Não foi possível exportar o desenho.", "Falha", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public ArrayList<Shape> getResizedPolygons(){ return resized_polygons; }
}