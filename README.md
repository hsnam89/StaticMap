# Static Maps Generator
Customizable Static Image Map Generator

This library manages the production of images for map vizualization in the Java language, using tiled maps.
This could be used for creation of small "preview" pictures, or produce very big images, for printing for instance.
**The nearest example that everybody knows is the [Google Static Maps API](https://developers.google.com/maps/documentation/static-maps/?hl=fr). Basically, this engine is able to generate the same-like images, except the tile source must be provided by yourself.**

Here's an exemple of what you can produce with this:

![Sample 0](https://github.com/doubotis/MapPictureGenerator/blob/master/samples/sample-0.png)
![Sample 1](https://github.com/doubotis/MapPictureGenerator/blob/master/samples/sample-1.png)
![Sample 2](https://github.com/doubotis/MapPictureGenerator/blob/master/samples/sample-2.png)

*Maps 1 to 3 are served with the OSM Topo Maps tile provider.*

![Sample 3](https://github.com/doubotis/MapPictureGenerator/blob/master/samples/sample-3.png)

 *Maps 4 to 6 are served with the OSM Base Maps tile provider.*

Supports any tile size with a very wide range of resolutions.
The library was tested from 8x8 to 9933x7026 (A1 page, 300dpi) resolutions with good results.

Best part of the engine is about the customization. You can add many layers as you want.
* Add transparent TMS or WMS layers on top of a basemap
* Add some agreements, like pins, lines or polygons
* At least 1 layer.
* A basemap is not required.