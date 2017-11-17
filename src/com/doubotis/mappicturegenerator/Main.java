package com.doubotis.mappicturegenerator;

import com.doubotis.mappicturegenerator.geo.Location;
import com.doubotis.mappicturegenerator.geo.PointF;
import com.doubotis.mappicturegenerator.layers.LocationPathLayer;
import com.doubotis.mappicturegenerator.layers.PointLayer;
import com.doubotis.mappicturegenerator.layers.PolygonLayer;
import com.doubotis.mappicturegenerator.maps.TMSMapType;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {

    public static void main(String args[]) throws ParseException {
        String outPath = "D:/test/export_2017_11.png";
        StaticMap mp = new StaticMap(1600, 1200);
        //TMSMapType baseMap = new TMSMapType("http://{s}.tile.osm.org/{z}/{x}/{y}.png"); // OSM
        TMSMapType baseMap = new TMSMapType("http://xdworld.vworld.kr:8080/2d/Base/201512/{z}/{x}/{y}.png"); //VWORLD
        /*TMSMapType baseMap = new TMSMapType("http://ngw.sgone.co.kr:9000/basemap.do?z={z}&y={y}&x={x}"); //자체 타일링*/
        //mp.setLocation(128.156, 36.448);

        //Test
        //Point
        List list =new ArrayList();
        HashMap<String,Object> PointWKT =new HashMap<>();
        PointWKT.put("WKT", "POINT(36 126)");
        PointWKT.put("RADIUS", "50");
        HashMap<String,Object> PointWKT2 =new HashMap<>();
        PointWKT2.put("WKT", "POINT(36 127)");
        PointWKT2.put("RADIUS", "");
        list.add(PointWKT);
        list.add(PointWKT2);
        final PointLayer pwkt = new PointLayer();
        pwkt.getPointLayers(list);
        //
        //Polygon
        List polygonWKT = new ArrayList();
        polygonWKT.add("Polygon((37.31031609485071243 125.72583154427798036,37.54151867434724466 125.92194418912387732,37.59440198850148818 125.62878611177690402,37.40673726511754893 125.45289126536862057,37.31031609485071243 125.72583154427798036))");
        polygonWKT.add("Polygon ((37.61394935122008576 129.76411116164808845,37.25754212633006546 130.56661321608982007,36.83959266194796101 130.74683613509580482,37.1790133241972427 129.71310467513694675,37.61394935122008576 129.76411116164808845))");
        PolygonLayer pon =new PolygonLayer();
        pon.getPolygonWKT(polygonWKT);

        mp.setLocation(36.448, 128.156);
        mp.setZoom(8);
        mp.addLayer(baseMap);
        //mp.addLayer(layer);
        mp.addLayer(pwkt);
        mp.addLayer(pon);
        try{
            mp.drawInto(new File(outPath));
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }

}
