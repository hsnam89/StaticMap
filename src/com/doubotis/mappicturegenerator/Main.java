package com.doubotis.mappicturegenerator;

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
        PointWKT.put("WKT", "POINT(126 36)");
        PointWKT.put("RADIUS", "6437.38");
        PointWKT.put("NUM", "1");
        HashMap<String,Object> PointWKT2 =new HashMap<>();
        PointWKT2.put("WKT", "POINT(125 36)");
        PointWKT2.put("RADIUS", "");
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

        HashMap<String,Object> PolygonWKT1 =new HashMap<>();
        PolygonWKT1.put("WKT","POLYGON((126.38183333333333 36.35819444444444,125.58333333333333 35.85,125.73333333333333 35.68333333333333,126.63333333333334 36.233333333333334,126.5021111111111 36.35625,126.38183333333333 36.35819444444444))");
        PolygonWKT1.put("NUM","4");
        HashMap<String,Object> PolygonWKT2 =new HashMap<>();
        PolygonWKT2.put("WKT","POLYGON((128.75 34.75,128.68333333333334 34.333333333333336,128.86666666666667 34.333333333333336,129.1 34.61666666666667,129.0 34.8,128.75 34.75))");
        PolygonWKT2.put("NUM","5");
        HashMap<String,Object> PolygonWKT3 =new HashMap<>();
        PolygonWKT3.put("WKT","Polygon ((129.61789770922979415 38.27770657214405503, 129.78409089102481744 37.46027219184266954, 131.50994316351184921 38.10689873824456697, 130.87713066359992808 38.83752935643845916, 129.61789770922979415 38.27770657214405503))");
        PolygonWKT3.put("NUM","6");
        HashMap<String,Object> PolygonWKT4 =new HashMap<>();
        PolygonWKT4.put("WKT","Polygon ((130.30823861822457843 36.71070579049794702, 131.97656248162871861 36.06750798624886301, 130.96022725449748236 35.30432610186426245, 130.02059657281009208 36.21720365126056862, 130.02059657281009208 36.21720365126056862, 130.30823861822457843 36.71070579049794702))");
        PolygonWKT4.put("NUM","7");
        HashMap<String,Object> PolygonWKT5 =new HashMap<>();
        PolygonWKT5.put("WKT","Polygon ((123.90340907366162071 37.23668987610177084, 125.37357952800242344 37.22651122973856985, 125.43110793708531503 36.44378202784270826, 124.83664770989534532 36.00548144179258259, 123.8458806645787007 36.24298436786038735, 123.8458806645787007 36.24298436786038735, 123.90340907366162071 37.23668987610177084))");
        PolygonWKT5.put("NUM","8");
        HashMap<String,Object> PolygonWKT6 =new HashMap<>();
        PolygonWKT6.put("WKT","Polygon ((123.48792611917400563 36.41292378828193677, 126.37073861877270531 35.92787959238055606, 125.82741475521196151 35.16335781992877685, 123.80752839185677772 35.61671052025475603, 123.48792611917400563 36.41292378828193677))");
        PolygonWKT6.put("NUM","9");

        List listPolygon =new ArrayList();
        listPolygon.add(PolygonWKT1);
        listPolygon.add(PolygonWKT2);
        listPolygon.add(PolygonWKT3);
        listPolygon.add(PolygonWKT4);
        listPolygon.add(PolygonWKT5);
        listPolygon.add(PolygonWKT6);
        PolygonLayer pon =new PolygonLayer();
        pon.getPolygonWKT(listPolygon);
        mp.addLayer(baseMap);
        mp.addLayer(pon); // polygon
        mp.addLayer(pwkt); //point
        mp.setLocation(36.448, 128.156);
        mp.setZoom(7);

        try{
            mp.drawInto(new File(outPath));
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }

}
