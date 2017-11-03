package com.doubotis.mappicturegenerator;

import com.doubotis.mappicturegenerator.geo.Location;
import com.doubotis.mappicturegenerator.geo.PointF;
import com.doubotis.mappicturegenerator.layers.LocationPathLayer;
import com.doubotis.mappicturegenerator.maps.TMSMapType;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

import java.io.File;

public class Main {

    public static void main(String args[]) throws ParseException {
        String outPath = "D:/test/export_2017_11.png";
        StaticMap mp = new StaticMap(1600, 1200);
        //TMSMapType baseMap = new TMSMapType("http://{s}.tile.osm.org/{z}/{x}/{y}.png");
        TMSMapType baseMap = new TMSMapType("http://xdworld.vworld.kr:8080/2d/Base/201512/{z}/{x}/{y}.png");
        //mp.setLocation(128.156, 36.448);

        Location location = new Location(36.442, 127.23);
        Location location2 = new Location(35.442, 126.23);
        Location location3 = new Location(35.542, 126.123);
        Location location4 = new Location(35.642, 126.223);

        Location[] locations = {location,location2,location3 ,location4};

        WKTReader reader =new WKTReader();

        Point point =null;
        Point point2 =null;
        point =(Point)reader.read("POINT(35 126)");
        point2 =(Point)reader.read("POINT(35 125)");

        Point[] points = {point};
        final LocationPathLayer Pointlayer = new LocationPathLayer(points);
        final LocationPathLayer layer = new LocationPathLayer(locations);

        mp.setLocation(36.448, 128.156);
        mp.setZoom(8);
        mp.addLayer(baseMap);
        mp.addLayer(layer);
        mp.addLayer(Pointlayer);
        try{
            mp.drawInto(new File(outPath));
        }catch(Exception e){

        }

    }

}
