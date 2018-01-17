package com.doubotis.mappicturegenerator.layers;

import java.util.HashMap;

public class ColorMap {
    public int r,g,b;
    public HashMap<String, Object> ColorTable = null;
    public ColorMap(){
        ColorTable = new HashMap<String, Object>();
        ColorTable.put("1", "255,0,0");
        ColorTable.put("2", "0,0,255");
        ColorTable.put("3", "255,128,0");
        ColorTable.put("4", "255,255,0");
        ColorTable.put("5", "127,0,204");
        ColorTable.put("6", "0,255,0");
    }

    public void getRGB(String num){
        String rgb = ColorTable.get(num).toString();
        this.r = Integer.parseInt(rgb.split(",")[0]);
        this.g = Integer.parseInt(rgb.split(",")[1]);
        this.b = Integer.parseInt(rgb.split(",")[2]);
    }

}
