package com.doubotis.mappicturegenerator;

import com.doubotis.mappicturegenerator.layers.LegendTable;
import com.doubotis.mappicturegenerator.layers.NumberingLayer;
import com.doubotis.mappicturegenerator.layers.PointLayer;
import com.doubotis.mappicturegenerator.layers.PolygonLayer;
import com.doubotis.mappicturegenerator.maps.TMSMapType;
import com.vividsolutions.jts.io.ParseException;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {

    public static void main(String args[]) throws ParseException {
        //https://github.com/hsnam89/StaticMap 참조
        String outPath = "D:/test/export_2017_11.png";
        StaticMap mp = new StaticMap(1600, 1200);
        //TMSMapType baseMap = new TMSMapType("http://{s}.tile.osm.org/{z}/{x}/{y}.png"); // OSM
        TMSMapType baseMap = new TMSMapType("http://ngw.sgone.co.kr:9000/basemap.do?z={z}&y={y}&x={x}"); //VWORLD
        /*TMSMapType baseMap = new TMSMapType("http://ngw.sgone.co.kr:9000/basemap.do?z={z}&y={y}&x={x}"); //자체 타일링*/



        //Point
        List list =new ArrayList();
        HashMap<String,Object> PointWKT =new HashMap<>();
        PointWKT.put("WKT", "POINT(130.0 35.833333333333336)");
        PointWKT.put("RADIUS", "4828.0199999999995");
        PointWKT.put("NUM", "1");
        HashMap<String,Object> PointWKT2 =new HashMap<>();
        PointWKT2.put("WKT", "POINT(129.73333333333332 35.375)");
        PointWKT2.put("RADIUS", "4828.0199999999995");
        PointWKT2.put("NUM", "2");
        HashMap<String,Object> PointWKT3 =new HashMap<>();
        PointWKT3.put("WKT", "POINT(124.123123123 36.123123123)");
        PointWKT3.put("RADIUS", "9656.06");
        PointWKT3.put("NUM", "3");
        list.add(PointWKT);
        list.add(PointWKT2);
        list.add(PointWKT3);
        final PointLayer pwkt = new PointLayer();
        pwkt.getPointLayers(list);
        //
        //Polygon
        HashMap<String,Object> PolygonWKT4 =new HashMap<>();
        PolygonWKT4.put("WKT","POLYGON((130.26944444444445 35.76555555555556,130.26944444444445 35.5175,129.96305555555554 35.5175,129.96305555555554 35.76555555555556,130.26944444444445 35.76555555555556))");
        PolygonWKT4.put("NUM","4");
        HashMap<String,Object> PolygonWKT5 =new HashMap<>();
        PolygonWKT5.put("WKT","POLYGON((128.3452777777778 38.546388888888885,128.43166666666664 38.60111111111111,128.57777777777778 38.4775,128.49749999999997 38.42222222222222,128.3452777777778 38.546388888888885))");
        PolygonWKT5.put("NUM","5");
        HashMap<String,Object> PolygonWKT6 =new HashMap<>();
        PolygonWKT6.put("WKT","POLYGON((123.90340907366162071 37.23668987610177084, 125.37357952800242344 37.22651122973856985, 125.43110793708531503 36.44378202784270826, 124.83664770989534532 36.00548144179258259, 123.8458806645787007 36.24298436786038735, 123.8458806645787007 36.24298436786038735, 123.90340907366162071 37.23668987610177084))");
        PolygonWKT6.put("NUM","6");


        List listPolygon =new ArrayList();
        listPolygon.add(PolygonWKT4);
        listPolygon.add(PolygonWKT5);
        listPolygon.add(PolygonWKT6);

        PolygonLayer pon =new PolygonLayer();
        pon.getPolygonWKT(listPolygon);
        NumberingLayer number =new NumberingLayer(list, listPolygon);
        LegendTable legend =new LegendTable();
        //
        //
        mp.addLayer(baseMap);
        mp.addLayer(pon); // polygon
        mp.addLayer(pwkt); //point
        mp.addLayer(legend); //범례표
        mp.addLayer(number); //numbering
        mp.setLocation(36.448, 128.156);
        mp.setZoom(7);

        try{
            mp.drawInto(new File(outPath));
        }catch(Exception e){
            System.out.println("main--"+e.toString());
        }
    }

}
