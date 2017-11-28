package com.doubotis.mappicturegenerator.layers;

import com.doubotis.mappicturegenerator.StaticMap;
import com.doubotis.mappicturegenerator.geo.MercatorProjection;
import com.doubotis.mappicturegenerator.geo.PointF;
import com.doubotis.mappicturegenerator.geo.Transform;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.osgeo.proj4j.CRSFactory;
import org.osgeo.proj4j.CoordinateReferenceSystem;
import org.osgeo.proj4j.CoordinateTransform;
import org.osgeo.proj4j.CoordinateTransformFactory;
import org.osgeo.proj4j.*;
import org.osgeo.proj4j.parser.Proj4Parser;


import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PointLayer implements Layer{
    public java.util.List<HashMap<String,Object>> PointsWKT = null;
    public void getPointLayers(List<HashMap<String,Object>> PointsWKT){
        this.PointsWKT = PointsWKT;
    }
    public PointLayer(){


    }

    @Override
    public void draw(Graphics2D graphics, StaticMap map)  {
        MercatorProjection proj = map.getProjection();
        Random rand = new Random();
        int[] canvasX;
        int[] canvasY;
        try{
            for(int i=0; i<this.PointsWKT.size(); i++){
                int r = rand.nextInt(255);int g = rand.nextInt(255);int b = rand.nextInt(255);
                HashMap<String, Object> Point =this.PointsWKT.get(i);
                String WKT = this.PointsWKT.get(i).get("WKT").toString();
                String RADIUS = this.PointsWKT.get(i).get("RADIUS").toString();
                String num = this.PointsWKT.get(i).get("NUM").toString();

                WKTReader reader =new WKTReader();
                Point point = null;
                com.vividsolutions.jts.geom.Polygon polygon = null;

                Transform transform =new Transform();
                if(!WKT.equals("")&& !RADIUS.equals("")){//Point 와 Radius를 가지고 있는경우(Polygone)
                    point = (Point)reader.read(WKT);
                    Point transformPoint =transform.PointTransForm("EPSG:4326","EPSG:3857",point);
                    Polygon bufferPolygon = (Polygon) transformPoint.buffer(Double.parseDouble(RADIUS));
                    polygon = transform.PolygonTransForm("EPSG:3857", "EPSG:4326", bufferPolygon);

                }else if(!WKT.equals("") && RADIUS.equals("")){//Point와 Radius를 가지고 있지 않는경우(Point)
                    point = (Point)reader.read(WKT);
                    point = (Point)reader.read(WKT);
                    Point transformPoint =transform.PointTransForm("EPSG:4326","EPSG:3857",point);
                    Polygon bufferPolygon = (Polygon) transformPoint.buffer(Double.parseDouble("2"));
                    polygon = transform.PolygonTransForm("EPSG:3857", "EPSG:4326", bufferPolygon);

                }//end of if

                canvasX = new int[polygon.getCoordinates().length]; canvasY =new int[polygon.getCoordinates().length];
                for(int a=0; a<polygon.getCoordinates().length; a++){
                    PointF pixelsLocation =proj.fromLatLngToPoint(polygon.getCoordinates()[a].y, polygon.getCoordinates()[a].x, map.getZoom());
                    canvasX[a] = (int)(pixelsLocation.x - map.getOffset().x);
                    canvasY[a] = (int)(pixelsLocation.y - map.getOffset().y);

                }//end of for

                PointF pixelCenterPoint =proj.fromLatLngToPoint(polygon.getCoordinates()[1].y, polygon.getCoordinates()[1].x, map.getZoom());
                int pixelCenterX = (int)(pixelCenterPoint.x - map.getOffset().x);
                int pixelCenterY =(int)(pixelCenterPoint.y - map.getOffset().y);

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
                graphics.fillOval(pixelCenterX-20/2,pixelCenterY-20/2, 20, 20);

                //Draw Text
                graphics.setFont(new Font("TimesRoman", Font.PLAIN, 20));
                graphics.setColor(new Color(255,255,255));
                FontMetrics fm = graphics.getFontMetrics();
                double textWidth = fm.getStringBounds(num, graphics).getWidth();
                double textHeight =fm.getStringBounds(num,graphics).getHeight();
                graphics.drawString(num, (int)(pixelCenterX-textWidth/2.2),(int)(pixelCenterY+7));

            }//end of for

        }catch(ParseException e){
            System.out.println("PointLayer Class Exception Error =="+e.toString());
        }catch(Exception e){
            System.out.println("PointLayer Class Exception Error =="+e.toString());
        }

    }//end of draw


}


