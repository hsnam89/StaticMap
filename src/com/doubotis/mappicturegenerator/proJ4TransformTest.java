package com.doubotis.mappicturegenerator;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.osgeo.proj4j.*;

public class proJ4TransformTest {

    public static void main(String args[]) throws ParseException {
        String cs4326 = "EPSG:4326";
        String cs3857 = "EPSG:3857";

        WKTReader reader =new WKTReader();
        Point point = (Point)reader.read("POINT(126 35)");

        CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
        CRSFactory csFactory = new CRSFactory();
        CoordinateReferenceSystem epsg5186 =csFactory.createFromParameters("EPSG:5186","+proj=tmerc +lat_0=38 +lon_0=127 +k=1 +x_0=200000 +y_0=600000 +ellps=GRS80 +units=m +no_defs");

        CoordinateReferenceSystem epsg4326 = csFactory.createFromName(cs4326);
        CoordinateReferenceSystem epsg3857 = csFactory.createFromName(cs3857);
        CoordinateTransform trans = ctFactory.createTransform(epsg4326, epsg3857);
        CoordinateTransform ori_trans = ctFactory.createTransform(epsg3857, epsg4326);

        ProjCoordinate tempPoint =new ProjCoordinate();
        tempPoint.x = point.getX();
        tempPoint.y =point.getY();
        ProjCoordinate temp2Point =new ProjCoordinate();

        trans.transform(tempPoint,temp2Point);
        GeometryFactory geometryFactory = new GeometryFactory();
        Coordinate coor =new Coordinate();
        coor.x=temp2Point.x;
        coor.y = temp2Point.y;
        System.out.println(coor.x);
        System.out.println(coor.y);
        Point tempJTS_Point=geometryFactory.createPoint(coor);
        Polygon tempJTS_Polygon =(com.vividsolutions.jts.geom.Polygon)tempJTS_Point.buffer(5);

        String Polygon_wkt = "POLYGON((";
        for(int a= 0; a<tempJTS_Polygon.getCoordinates().length; a++){
            Coordinate coortest = tempJTS_Polygon.getCoordinates()[a];
            ProjCoordinate polygonCoordinate = new ProjCoordinate();
            polygonCoordinate.x = coortest.x;
            polygonCoordinate.y = coortest.y;
            ProjCoordinate polygonCoordinate2 = new ProjCoordinate();
            ori_trans.transform(polygonCoordinate,polygonCoordinate2);
            Polygon_wkt += polygonCoordinate2.x +" "+polygonCoordinate2.y+",";
        }
        Polygon_wkt.substring(0,Polygon_wkt.length()-1); //마지막 , 제거
        Polygon_wkt+= "))";
        System.out.println(Polygon_wkt);


    }

}
