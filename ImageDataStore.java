package photorainbow;

import java.util.List;

public class ImageDataStore {
    private ImageColorData iCDObj = new ImageColorData();
    private List<Image> imgObjs = null;
    List<PickTask> pickTasks = GetPickTasksByCriteria(args);

    public ImageDataStore() {
    }
    
    public ImageDataStore(List<Image> imgObjs) {
        this.imgObjs = imgObjs;
    }
    
    public List<Image> getImages()
    {
        return this.imgObjs;
    }
    
    public void setImages(List<Image> imageList) {
        imgObjs = imageList;
    }

    public ImageColorData getImgObjColorData() {
        return iCDObj;
    }
    
    public void setImgObjColorData(ImageColorData imageData) {
        this.iCDObj = imageData;
    }
}