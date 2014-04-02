package photorainbow;

import java.lang.Integer;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImageColorData
{
    private static final Logger log = Logger.getLogger(ImageColorData.class.getName());

    private int imageWidth      = 0;
    private int imageHeight     = 0;
    private float rangeValue    = 0.0f;
    private HashMap<String, float> _brightnessColorDict = new HashMap<String, float>();
    private HashMap<String, List<Color>> _colorByPixel = new HashMap<String, List<Color>>();
    private HashMap<float, String> imgVIBGYORHueDiffDict = new HashMap<float, String>();
    private HashMap<Image, HashMap<String, float>> imageDataDictSorted = new HashMap<Image, HashMap<String, float>>();

    public HashMap<String, float> getBrightnessColorDict() {
        return this._brightnessColorDict;
    }
    
    public HashMap<String, List<Color>> getColorByPixel() {
        return this._colorByPixel;
    }
    
    //Ayesha Logic...
    public HashMap<Image, float> getSortedImagesByColor(String color) {
        HashMap<Image, float> dicImgHue = new HashMap<Image, float>();
        HashMap<Image, float> orderedItems = ImageColorSorter.sortByColorValue(imageDataDictSorted);
        Iterator<Map.Entry<Image, float>> entries = orderedItems.entrySet().iterator();
        while(entries.hasNext()) {
            Map.Entry<Image, float> entry = entries.next();
            dicImgHue.add(entry.getKey(), entry.getValue());
        }
        return dicImgHue;
    }
    
    public HashMap<Image, HashMap<String, float>> getSortedImagesByRainbow() {
        HashMap<Image, HashMap<String, float>> dicImgRainbowHue = new HashMap<Image, HashMap<String, float>>();
        List<int> colors = new ArrayList<int>();
        colors.add(Color.VIOLET);
        colors.add(Color.INDIGO);
        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);
        HashMap<Image, float> orderedItems = ImageColorSorter.sortByColorValue(imageDataDictSorted, colors);
        Iterator<Map.Entry<Image, float>> entries = orderedItems.entrySet().iterator();
        while(entries.hasNext()) {
            Map.Entry<Image, float> entry = entries.next();
            dicImgRainbowHue.add(entry.getKey(), entry.getValue());
        }
        return dicImgRainbowHue;
    }
    
    private boolean InRange(int min, int max, operand) {
        return (operand >= min && operand <= max) ? true : false;
    }
    
    public HashMap<String, List<Color>> getColorsInImage(Bitmap imageAsBitmap) {
        int xCoord = 0, yCoord = 0;
        float temp = 0;
        this.imageWidth = imageAsBitmap.getWidth();
        this.imageHeight = imageAsBitmap.getHeight();
        
        try {
            for (xCoord = 0; xCoord < this.imageWidth; xCoord++) {
                for (yCoord = 0; yCoord < this.imageHeight; yCoord++) {
                    List<float> imgVIBGYORHueDiff = new List<float>();
                    Color imgPixelColor = imageAsBitmap.getPixel(xCoord, yCoord);
                    float pixelColorHue = imgPixelColor.getHue();
                    temp = pixelColorHue;
                    rangeValue = temp;
                    
                    if (!imgVIBGYORHueDiffDict.containsKey(rangeValue)) {
                        if (InRange(300, 360, Integer.valueOf(rangeValue))) {
                            imgVIBGYORHueDiffDict.add(rangeValue, "Violet"); break;
                        }
                        else if (InRange(275, 299, Integer.valueOf(rangeValue))) {
                            imgVIBGYORHueDiffDict.add(rangeValue, "Indigo"); continue;
                        }
                        else if (InRange(240, 274,Integer.valueOf(rangeValue))) {
                            imgVIBGYORHueDiffDict.add(rangeValue, "Blue"); continue;
                        }
                        else if (InRange(120, 239,Integer.valueOf(rangeValue))) {
                            imgVIBGYORHueDiffDict.add(rangeValue, "Green"); continue;
                        }
                        else if (InRange(60, 119, Integer.valueOf(rangeValue))) {
                            imgVIBGYORHueDiffDict.add(rangeValue, "Yellow"); continue;
                        }
                        else if (InRange(40, 59, Integer.valueOf(rangeValue))) {
                            imgVIBGYORHueDiffDict.add(rangeValue, "Orange"); continue;
                        }
                        else if (InRange(0, 39, Integer.valueOf(rangeValue))) {
                            imgVIBGYORHueDiffDict.add(rangeValue, "Red"); continue;
                        }
                    }
                    
                    for (float hueDiff : imgVIBGYORHueDiffDict.getKeys()) {
                        imgVIBGYORHueDiff.add(hueDiff);
                    }
                    
                    float closestPixelColorByHue = imgVIBGYORHueDiff.getMinimum();
                    if (_colorByPixel.containsKey(imgVIBGYORHueDiffDict[closestPixelColorByHue])) {
                        _colorByPixel[imgVIBGYORHueDiffDict[closestPixelColorByHue]].add(imgPixelColor);
                        _brightnessColorDict[imgVIBGYORHueDiffDict[closestPixelColorByHue]] += imgPixelColor.GetBrightness();
                    }
                    else {
                        List<Color> pixelColorStructure = new List<Color>();
                        pixelColorStructure.add(imgPixelColor);
                        _colorByPixel.add(imgVIBGYORHueDiffDict[closestPixelColorByHue], pixelColorStructure);
                        _brightnessColorDict.add(imgVIBGYORHueDiffDict[closestPixelColorByHue], imgPixelColor.GetBrightness());
                    }
                }
            }
        }
        catch (Exception e) {
            log.log(Level.ERROR, e.printStackTrace());
        }
        return _colorByPixel;
    }
    
    public HashMap<Bitmap, List<HashMap<String, float>>> GetColorData2(Image imgObj) {
        _colorByPixel = getColorsInImage(imgObj.Img);
        HashMap<Bitmap, List<HashMap<String, float>>> imageDataDict = new HashMap<Bitmap, List<HashMap<String, float>>>();
        HashMap<String, float> colPercentageDict = new HashMap<String, float>();
        List<HashMap<String, float>> imgInfoDictList = new List<HashMap<string, float>>();
        colPercentageDict.add("Violet", percentageOfColorInImage("Violet"));
        colPercentageDict.add("Indigo", percentageOfColorInImage("Indigo"));
        colPercentageDict.add("Blue", percentageOfColorInImage("Blue"));
        colPercentageDict.add("Green", percentageOfColorInImage("Green"));
        colPercentageDict.add("Yellow", percentageOfColorInImage("Yellow"));
        colPercentageDict.add("Orange", percentageOfColorInImage("Orange"));
        colPercentageDict.add("Red", percentageOfColorInImage("Red"));
        imgInfoDictList.add(colPercentageDict);
        
        HashMap<String, float> colAvgBrightnessDict = new HashMap<String, float>();
        colAvgBrightnessDict.add("Violet", calcAverageBrightnessByColor("Violet"));
        colAvgBrightnessDict.add("Indigo", calcAverageBrightnessByColor("Indigo"));
        colAvgBrightnessDict.add("Blue", calcAverageBrightnessByColor("Blue"));
        colAvgBrightnessDict.add("Green", calcAverageBrightnessByColor("Green"));
        colAvgBrightnessDict.add("Yellow", calcAverageBrightnessByColor("Yellow"));
        colAvgBrightnessDict.add("Orange", calcAverageBrightnessByColor("Orange"));
        colAvgBrightnessDict.add("Red", calcAverageBrightnessByColor("Red"));
        imgInfoDictList.add(colAvgBrightnessDict);
        imageDataDict.add(imgObj.Img, imgInfoDictList);
        imageDataDictSorted.add(imgObj, colPercentageDict);
        return imageDataDict;
    }
    
    public float percentageOfColorInImage(String colorName) {
        float percentageOfColor = 0;
        if(_colorByPixel.containsKey(colorName)) {
            float numberofPixelsByColor = _colorByPixel[colorName].Length;
            float totalPixelsInImage = imageWidth * imageHeight;
            percentageOfColor = (numberofPixelsByColor / totalPixelsInImage) * 100;
            return percentageOfColor;
        }
        else {
            return percentageOfColor;
        }
    }
    
    public float calcAverageBrightnessByColor(String colorName) {
        float averageBrightnessByColor = 0;
        if(_colorByPixel.containsKey(colorName)) {
            int numberOfPixelsByColor = _colorByPixel[colorName].Length;
            averageBrightnessByColor = _brightnessColorDict[colorName] / numberOfPixelsByColor;
            return averageBrightnessByColor;
        }
        else {
            return averageBrightnessByColor;
        }
    }
    
    private static float adjustHue(float temp) {
        if (temp < 0) {
            temp = temp * (-1);
            return temp;
        }
        else {
            return temp;
        }
    }
}