package com.doubotis.mappicturegenerator.geo;

import com.vividsolutions.jts.geom.*;
import org.osgeo.proj4j.*;

public class Transform {
    private CoordinateTransformFactory ctFactory;
    private CRSFactory csFactory;
    private CoordinateReferenceSystem targetCRS;
    private CoordinateReferenceSystem sourceCRS;
    private CoordinateTransform transForm;
    public Transform(){
        this.ctFactory = new CoordinateTransformFactory();
        this.csFactory = new CRSFactory();
    }

    /*
     * @param targetCRS ori
     */
    public Point PointTransForm(String targetCRS_Name, String sourceCRS_Name, Point point ){
        Point resultPoint = null;
        try{
            this.targetCRS = csFactory.createFromName(targetCRS_Name);
            this.sourceCRS = csFactory.createFromName(sourceCRS_Name);

            this.transForm = ctFactory.createTransform(targetCRS, sourceCRS);

            ProjCoordinate targetCoordinate =new ProjCoordinate();
            ProjCoordinate sourceCoordinate =new ProjCoordinate();

            targetCoordinate.x= point.getX(); targetCoordinate.y = point.getY();
            transForm.transform(targetCoordinate, sourceCoordinate);

            GeometryFactory geometryFactory =new GeometryFactory();
            Coordinate coor =new Coordinate();
            coor.x = sourceCoordinate.x; coor.y=sourceCoordinate.y;
            resultPoint = geometryFactory.createPoint(coor);

        }catch(Exception e){
            System.out.println("Transform Class - PointTransForm error -"+e.toString());
        }
        return resultPoint;
    }

    public Polygon PolygonTransForm(String targetCRS_Name, String sourceCRS_Name, Polygon polygon){
        Polygon resultPolygon =null;
        String WKT= "POLYGON((";
        try{
            this.targetCRS = csFactory.createFromName(targetCRS_Name);
            this.sourceCRS = csFactory.createFromName(sourceCRS_Name);
            this.transForm = ctFactory.createTransform(targetCRS, sourceCRS);
            CoordinateList coorList =new CoordinateList();
            Coordinate[] coorLists =new Coordinate[polygon.getCoordinates().length];
            for(int a=0; a<polygon.getCoordinates().length; a++){
                Coordinate coor = polygon.getCoordinates()[a];
                ProjCoordinate targetCoordinate =new ProjCoordinate();
                ProjCoordinate sourceCoordinate =new ProjCoordinate();
                targetCoordinate.x = coor.x; targetCoordinate.y =coor.y;
                transForm.transform(targetCoordinate, sourceCoordinate);

                Coordinate outCoordinate =new Coordinate();
                outCoordinate.x = sourceCoordinate.x; outCoordinate.y=sourceCoordinate.y;
                coorList.add(outCoordinate);
                coorLists[a]=outCoordinate;

            }//end of for
            GeometryFactory geometryFactory =new GeometryFactory();
            if(coorList.size()>0){
                //resultPolygon =geometryFactory.createPolygon((CoordinateSequence) coorList);
                resultPolygon =geometryFactory.createPolygon(coorLists);

            }

        }catch (Exception e){
            System.out.println("Transform Class - PolygonTransForm -"+e.toString());
        }
        return resultPolygon;
    }

}
