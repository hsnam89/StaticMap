package com.doubotis.mappicturegenerator.layers;

import com.doubotis.mappicturegenerator.StaticMap;
import com.doubotis.mappicturegenerator.geo.MercatorProjection;
import com.doubotis.mappicturegenerator.geo.PointF;
import com.doubotis.mappicturegenerator.geo.Transform;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NumberingLayer implements Layer{

    private  List<HashMap<String, Object>> numberingPointsList =null;
    private  final GeometryFactory geometryFactory =new GeometryFactory();
    public NumberingLayer(List<HashMap<String,Object>> PointsWKT, List<HashMap<String,Object>> PolygonWKT){
        numberingPointsList =new ArrayList<>();
        WKTReader reader =new WKTReader();
        try{
            for(int a=0; a<PointsWKT.size(); a++){
                String wkt = PointsWKT.get(a).get("WKT").toString();
                if(wkt.contains("POINT")){
                    HashMap<String, Object> temp =new HashMap<String, Object>();
                    Point point =(Point)reader.read(wkt);
                    point.setSRID(4326);
                    temp.put("geom", point);
                    temp.put("num", PointsWKT.get(a).get("NUM").toString());
                    numberingPointsList.add(temp);
                }
            }//end of for : Points List
            for(int b = 0; b<PolygonWKT.size(); b++){
                String wkt = PolygonWKT.get(b).get("WKT").toString();
                if(wkt.contains("POLYGON")){
                    HashMap<String, Object> temp =new HashMap<String, Object>();
                    Polygon polygon =(Polygon) reader.read(wkt);
                    Coordinate coor =new Coordinate();
                    coor.x =polygon.getCoordinates()[1].x;
                    coor.y =polygon.getCoordinates()[1].y;

                    Point point =geometryFactory.createPoint(coor);
                    point.setSRID(4326);
                    temp.put("geom", point);
                    temp.put("num", PolygonWKT.get(b).get("NUM").toString());
                    numberingPointsList.add(temp);
                }
            }//end of for : Polygon List

        }catch(ParseException e){
            System.out.println("NumberingLayer Class ParseException ="+e.toString());
        }catch(Exception e){
            System.out.println("NumberingLayer Class Exception ="+e.toString());
        }

    }
    @Override
    public void draw(Graphics2D graphics, StaticMap map)  {
        MercatorProjection proj = map.getProjection();
        Transform transform =new Transform();

        if(!(numberingPointsList ==null) && !numberingPointsList.equals("")){
            int[] canvasX;
            int[] canvasY;
            for(int b=0; b<numberingPointsList.size(); b++){
                try{
                    String num = numberingPointsList.get(b).get("num").toString();
                    Point point = (Point) numberingPointsList.get(b).get("geom");



                    Point transformPoint= transform.PointTransForm("EPSG:4326", "EPSG:3857", point);
                    Polygon bufferPolygon= (Polygon)transformPoint.buffer(12000);
                    Polygon polygon = transform.PolygonTransForm("EPSG:3857", "EPSG:4326", bufferPolygon);

                    PointF pixelCenterPoint =proj.fromLatLngToPoint(polygon.getCoordinates()[0].y, polygon.getCoordinates()[0].x, map.getZoom());
                    int pixelCenterX = (int)(pixelCenterPoint.x - map.getOffset().x);
                    int pixelCenterY =(int)(pixelCenterPoint.y - map.getOffset().y);
                    int radius =20;

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

                }catch (Exception e){
                    System.out.println("NumberingLayer ==="+e.toString());
                }

            }
        }//numberingPointsList 인스턴스나 비어 있지 않을때



    }
}
