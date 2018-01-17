package com.doubotis.mappicturegenerator.layers;


import com.doubotis.mappicturegenerator.StaticMap;

import java.awt.*;

public class LegendTable  extends ColorMap  implements Layer {

    @Override
    public void draw(Graphics2D graphics, StaticMap map)  {

       BasicStroke legendStroke = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
       graphics.setStroke(legendStroke);

        //Table Header 만들기
        int height =30;
        //순번 column header
        graphics.setColor(new Color(30, 181, 4));
        graphics.fillRect(1300, 80, 70,height);
        graphics.setColor(new Color(0, 0, 0));
        graphics.drawRect(1300, 80, 70,height);
        //색상 column header
        graphics.setColor(new Color(30, 181, 4));
        graphics.fillRect(1370, 80, 100,height);
        graphics.setColor(new Color(0, 0, 0));
        graphics.drawRect(1370, 80, 100,height);

        FontMetrics fm = graphics.getFontMetrics();
        //순번 column header text
        graphics.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        graphics.setColor(new Color(0,0,0));
        graphics.drawString("순번", 1315, height+72);
        //색상 column header text
        graphics.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        graphics.setColor(new Color(0,0,0));
        graphics.drawString("색상", 1397, height+72);

        int x1 =1300;int y1 = 80;
        int x2 = 1370;int y2 = 80;
        ColorMap colormap =new ColorMap();
        for(int c=0; c<colormap.ColorTable.size(); c++){
            colormap.getRGB(Integer.toString(c+1));

            y1+=30;y2+=30;

            //순번 column fill
            graphics.setColor(new Color(255,255,255));
            graphics.fillRect(x1, y1, 70,30);
            //순번 column 테두리
            graphics.setColor(new Color(0,0,0));
            graphics.drawRect(x1, y1, 70,30);

            //색상 column
            graphics.setColor(new Color(colormap.r, colormap.g,colormap.b));
            //graphics.drawRect(1370, 80, 100,30);
            graphics.fillRect(x2, y2, 100,30);

            //색상 column 테두리
            graphics.setColor(new Color(0,0,0));
            graphics.drawRect(x2, y2, 100,30);

            //number text
            graphics.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            graphics.setColor(new Color(0,0,0));
            graphics.drawString(Integer.toString(c+1), x1+30, y1+22 );

        }

    }
}
