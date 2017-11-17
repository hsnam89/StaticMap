package com.doubotis.mappicturegenerator.layers;

import com.doubotis.mappicturegenerator.StaticMap;
import com.doubotis.mappicturegenerator.geo.MercatorProjection;
import com.doubotis.mappicturegenerator.geo.PointF;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PointLayer implements Layer{
    public java.util.List<HashMap<String,Object>> PointsWKT = null;

    public void getPointLayers(List<HashMap<String,Object>> PointsWKT){
        this.PointsWKT = PointsWKT;
    }

    @Override
    public void draw(Graphics2D graphics, StaticMap map)  {
        MercatorProjection proj = map.getProjection();
        Random rand = new Random();
        try{
            for(int i=0; i<this.PointsWKT.size(); i++){
                int r = rand.nextInt(255);int g = rand.nextInt(255);int b = rand.nextInt(255);
                HashMap<String, Object> Point =this.PointsWKT.get(i);
                String WKT = this.PointsWKT.get(i).get("WKT").toString();
                String RADIUS = this.PointsWKT.get(i).get("RADIUS").toString();
                WKTReader reader =new WKTReader();
                Point point = null;

                int canvasX = 0, canvasY= 0;
                if(!WKT.equals("")&& !RADIUS.equals("")){//Point 와 Radius를 가지고 있는경우(Polygone)
                    point = (Point)reader.read(WKT);
                    PointF pixelsLocation =proj.fromLatLngToPoint(point.getX(), point.getY(), map.getZoom());
                    canvasX = (int)(pixelsLocation.x - map.getOffset().x);
                    canvasY = (int)(pixelsLocation.y - map.getOffset().y);

                }else if(!WKT.equals("") && RADIUS.equals("")){//Point와 Radius를 가지고 있지 않는경우(Point)
                    point = (Point)reader.read(WKT);
                    PointF pixelsLocation =proj.fromLatLngToPoint(point.getX(), point.getY(), map.getZoom());
                    canvasX = (int)(pixelsLocation.x - map.getOffset().x);
                    canvasY = (int)(pixelsLocation.y - map.getOffset().y);
                    RADIUS="5";

                }//end of if
                //Draw Fill

                // Draw Outline
                BasicStroke sCenter = new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
                graphics.setColor(new Color(r,g,b, 80));
                graphics.setStroke(sCenter);
                graphics.fillOval(canvasX,canvasY, Integer.parseInt(RADIUS), Integer.parseInt(RADIUS));

                BasicStroke Center = new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
                graphics.setColor(Color.red);
                graphics.setStroke(Center);
                graphics.drawOval(canvasX,canvasY, Integer.parseInt(RADIUS), Integer.parseInt(RADIUS));


                //graphics.drawLine(canvasX,canvasX,canvasX,canvasY);
            }//end of for

        }catch(ParseException e){
            System.out.println(e.toString());
        }



    }


}


