package Trabalho_RA3.Importer;

import Trabalho_RA3.Exporter.Desenho;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class DesenhoImporter extends JComponent{
    private int x;
    private int y;
    private int width;
    private int height;
    private ArrayList<Shape> poligonos;
    private ArrayList<Integer> x_points;
    private ArrayList<Integer> y_points;

    public DesenhoImporter(int x, int y, int width, int height, File arquivo){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.poligonos = new ArrayList<Shape>();
        this.x_points = new ArrayList<Integer>();
        this.y_points = new ArrayList<Integer>();

        if(arquivo.getName().contains(".txt")) {
            try {
                FileReader reader = new FileReader(arquivo);
                BufferedReader buffer = new BufferedReader(reader);

                String str;
                String leitura;
                String numero = "";
                boolean poligono_definido;
                int contador;
                int[] shape_info;

                while ((str = buffer.readLine()) != null) {
                    leitura = "";
                    poligono_definido = false;
                    shape_info = new int[4];
                    contador = 0;

                    for (int i = 0; i < str.length(); i++) {
                        char charLido = str.charAt(i);

                        if (!poligono_definido) {
                            leitura += charLido;
                        }

                        if (leitura.equals("Polygon")) {
                            poligono_definido = true;

                            if (charLido >= '0' && charLido <= '9') {
                                numero += charLido;
                            } else if (charLido == ',') {
                                x_points.add(Integer.parseInt(numero));
                                numero = "";
                            } else if (charLido == ']') {
                                y_points.add(Integer.parseInt(numero));
                                numero = "";
                            }
                        } else if (leitura.equals("Ellipse") || leitura.equals("Line")) {
                            poligono_definido = true;

                            if (charLido >= '0' && charLido <= '9') {
                                numero += charLido;
                            } else if (charLido == ',') {
                                shape_info[contador] = Integer.parseInt(numero);
                                numero = "";
                                contador++;
                            } else if (charLido == '}') {
                                shape_info[contador] = Integer.parseInt(numero);
                                numero = "";
                            }
                        }
                    }

                    if (leitura.equals("Polygon")) {
                        int[] xpoints = new int[x_points.size()];
                        int[] ypoints = new int[y_points.size()];

                        for (int i = 0; i < x_points.size(); i++) {
                            xpoints[i] = x_points.get(i);
                            ypoints[i] = y_points.get(i);
                        }

                        x_points.clear();
                        y_points.clear();
                        poligonos.add(new Polygon(xpoints, ypoints, xpoints.length));
                    } else if (leitura.equals("Ellipse")) {
                        poligonos.add(new Ellipse2D.Double(shape_info[0], shape_info[1], shape_info[2], shape_info[3]));
                    } else if (leitura.equals("Line")) {
                        poligonos.add(new Line2D.Double(shape_info[0], shape_info[1], shape_info[2], shape_info[3]));
                    }
                }

                buffer.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                FileInputStream inputStream = new FileInputStream(arquivo);
                ObjectInputStream restaurador = new ObjectInputStream(inputStream);

                Desenho temp = (Desenho) restaurador.readObject();
                poligonos = temp.getResizedPolygons();

                restaurador.close();
                inputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        ArrayList<Integer> x_bounds = new ArrayList<Integer>();
        ArrayList<Integer> y_bounds = new ArrayList<Integer>();

        for(Shape s : poligonos){
            x_bounds.add(s.getBounds().x);
            y_bounds.add(s.getBounds().y);
        }

        int x_menor = Collections.min(x_bounds);
        int y_menor = Collections.min(y_bounds);
        int largura = Collections.max(x_bounds) - x_menor;
        int altura = Collections.max(y_bounds) - y_menor;
        double aspect_ratio = aspect_ratio(width, largura, height, altura);

        for(Shape s : poligonos){
            if(s instanceof Polygon){
                Polygon p = (Polygon) s;

                for(int i = 0; i < p.npoints; i++){
                    p.xpoints[i] = (int) Math.round(p.xpoints[i] * aspect_ratio);
                    p.ypoints[i] = (int) Math.round(p.ypoints[i] * aspect_ratio);
                }
            }else if(s instanceof Ellipse2D.Double){
                Ellipse2D.Double e = (Ellipse2D.Double) s;

                int x_final = (int) Math.round((e.x + e.width) * aspect_ratio);
                int y_final = (int) Math.round((e.y + e.height) * aspect_ratio);
                e.x = (int) Math.round(e.x * aspect_ratio);
                e.y = (int) Math.round(e.y * aspect_ratio);
                e.width = x_final - e.x;
                e.height = y_final - e.y;
            }else if(s instanceof Line2D.Double){
                Line2D.Double l = (Line2D.Double) s;

                l.x1 = (int) Math.round(l.x1 * aspect_ratio);
                l.y1 = (int) Math.round(l.y1 * aspect_ratio);
                l.x2 = (int) Math.round(l.x2 * aspect_ratio);
                l.y2 = (int) Math.round(l.y2 * aspect_ratio);
            }
        }

        x_bounds = new ArrayList<Integer>();
        y_bounds = new ArrayList<Integer>();

        for(Shape s : poligonos){
            x_bounds.add(s.getBounds().x);
            y_bounds.add(s.getBounds().y);
        }

        x_menor = Collections.min(x_bounds);
        y_menor = Collections.min(y_bounds);
        int x_dist = x - x_menor;
        int y_dist = y - y_menor;

        for(Shape s : poligonos){
            if(s instanceof Polygon){
                Polygon p = (Polygon) s;

                p.translate(x_dist, y_dist);
            }else if(s instanceof Ellipse2D.Double){
                Ellipse2D.Double e = (Ellipse2D.Double) s;

                e.x += x_dist;
                e.y += y_dist;
            }else if(s instanceof Line2D.Double){
                Line2D.Double l = (Line2D.Double) s;

                l.x1 += x_dist;
                l.y1 += y_dist;
                l.x2 += x_dist;
                l.y2 += y_dist;
            }
        }
    }

    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        for(Shape s : poligonos){
            g2d.draw(s);
        }
    }

    private double aspect_ratio(int largura, int img_width, int altura, int img_height){
        if(img_width >= img_height){
            return largura / (double) img_width;
        }else{
            return altura / (double) img_height;
        }
    }
}
