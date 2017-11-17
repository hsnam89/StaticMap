package com.doubotis.mappicturegenerator.layers;

import com.doubotis.mappicturegenerator.StaticMap;
import com.doubotis.mappicturegenerator.geo.MercatorProjection;
import com.doubotis.mappicturegenerator.geo.PointF;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PolygonLayer implements Layer{
    public List PolygonWKT = null;

    public void getPolygonWKT(List PolygonWKT){
        this.PolygonWKT = PolygonWKT;
    }
    @Override
    public void draw(Graphics2D graphics, StaticMap map) {
        MercatorProjection proj = map.getProjection();
        Random rand = new Random();
        int[] canvasX;
        int[] canvasY;

        try{
            for(int i=0; i<this.PolygonWKT.size(); i++){
                int r = rand.nextInt(255);int g = rand.nextInt(255);int b = rand.nextInt(255);
                String WKT = this.PolygonWKT.get(i).toString();
                WKTReader reader =new WKTReader();
                Polygon polygon = (Polygon)reader.read(WKT);
                canvasX = new int[polygon.getCoordinates().length]; canvasY =new int[polygon.getCoordinates().length];
                for(int a=0; a<polygon.getCoordinates().length; a++){
                    PointF pixelsLocation =proj.fromLatLngToPoint(polygon.getCoordinates()[a].x, polygon.getCoordinates()[a].y, map.getZoom());
                    canvasX[a] = (int)(pixelsLocation.x - map.getOffset().x);
                    canvasY[a] = (int)(pixelsLocation.y - map.getOffset().y);
                }//end of for
                BasicStroke sCenter = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
                graphics.setColor(new Color(r,g,b,80));
                graphics.setStroke(sCenter);
                graphics.fillPolygon(canvasX,canvasY, polygon.getCoordinates().length);

                // Draw Outline
                BasicStroke sOutline = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
                graphics.setColor(Color.RED);
                graphics.setStroke(sOutline);
                graphics.drawPolyline(canvasX,canvasY, polygon.getCoordinates().length);


            }//end of for



        }catch(ParseException e){

        }

    }
}
