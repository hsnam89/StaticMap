package com.doubotis.mappicturegenerator.layers;

import com.doubotis.mappicturegenerator.StaticMap;
import com.doubotis.mappicturegenerator.geo.MercatorProjection;
import com.doubotis.mappicturegenerator.geo.PointF;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PolygonLayer implements Layer{
    public java.util.List<HashMap<String,Object>> PolygonWKT = null;

    public void getPolygonWKT(List<HashMap<String,Object>> PolygonWKT){
        this.PolygonWKT = PolygonWKT;
    }
    @Override
    public void draw(Graphics2D graphics, StaticMap map) {
        MercatorProjection proj = map.getProjection();
        int radius =20;
        Random rand = new Random();
        int[] canvasX;
        int[] canvasY;

        try{
            for(int i=0; i<this.PolygonWKT.size(); i++){
                int r = rand.nextInt(255);int g = rand.nextInt(255);int b = rand.nextInt(255);
                String WKT = this.PolygonWKT.get(i).get("WKT").toString();
                String num =this.PolygonWKT.get(i).get("NUM").toString();
                WKTReader reader =new WKTReader();
                Polygon polygon = (Polygon)reader.read(WKT);

                //Point getCenterPoint =polygon.getBoundary().getCentroid();
                //PointF pixelCenterPoint =proj.fromLatLngToPoint(getCenterPoint.getY(), getCenterPoint.getX(), map.getZoom());
                PointF pixelCenterPoint =proj.fromLatLngToPoint(polygon.getCoordinates()[1].y, polygon.getCoordinates()[1].x, map.getZoom());
                int pixelCenterX = (int)(pixelCenterPoint.x - map.getOffset().x);
                int pixelCenterY =(int)(pixelCenterPoint.y - map.getOffset().y);

                canvasX = new int[polygon.getCoordinates().length]; canvasY =new int[polygon.getCoordinates().length];
                for(int a=0; a<polygon.getCoordinates().length; a++){
                    PointF pixelsLocation =proj.fromLatLngToPoint(polygon.getCoordinates()[a].y, polygon.getCoordinates()[a].x, map.getZoom());
                    canvasX[a] = (int)(pixelsLocation.x - map.getOffset().x);
                    canvasY[a] = (int)(pixelsLocation.y - map.getOffset().y);

                }//end of for

                //Draw Fill
                BasicStroke sCenter = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
                graphics.setColor(new Color(r,g,b,80));
                graphics.setStroke(sCenter);
                graphics.fillPolygon(canvasX,canvasY, polygon.getCoordinates().length);

                // Draw Outline
                BasicStroke sOutline = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
                graphics.setColor(Color.RED);
                graphics.setStroke(sOutline);
                graphics.drawPolyline(canvasX,canvasY, polygon.getCoordinates().length);

                //Draw Text Circle
                BasicStroke textCircle = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
                graphics.setColor(new Color(30, 181, 4));
                graphics.setStroke(textCircle);
                graphics.fillOval(pixelCenterX-radius/2,pixelCenterY-radius/2, radius, radius);

                //Draw Text
                graphics.setFont(new Font("TimesRoman", Font.PLAIN, 20));
                graphics.setColor(new Color(255,255,255));
                FontMetrics fm = graphics.getFontMetrics();
                double textWidth = fm.getStringBounds(num, graphics).getWidth();
                double textHeight =fm.getStringBounds(num,graphics).getHeight();
                graphics.drawString(num, (int)(pixelCenterX-textWidth/2.2),(int)(pixelCenterY+7));

            }//end of for



        }catch(ParseException e){
            System.out.println("PolygonLayer Class Exception Error =="+e.toString());
        }catch(Exception e){
            System.out.println("PolygonLayer Class Exception Error =="+e.toString());
        }

    }//end of draw

}
