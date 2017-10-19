package com.doubotis.mappicturegenerator;

import com.doubotis.mappicturegenerator.maps.TMSMapType;

import java.io.File;

public class Main {

    public static void main(String args[]) {
        String outPath = "D:/00_Dev/00_IntelliJWorkspace/StaticMap/img/export.png";
        StaticMap mp = new StaticMap(1600, 1200);
        TMSMapType baseMap = new TMSMapType("http://{s}.tile.osm.org/{z}/{x}/{y}.png");
        //mp.setLocation(128.156, 36.448);
        mp.setLocation(36.448, 128.156);
        mp.setZoom(8);
        mp.addLayer(baseMap);
        try{
            mp.drawInto(new File(outPath));
        }catch(Exception e){

        }


    }

}
