package photorainbow;

import FlickrNet.Flickr;
import FlickrNet.OAuthAccessToken;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

public class FlickrManager implements APIManager {

    private static final Logger log = Logger.getLogger(FlickrManager.class.getName());

    /* Constants */
    private static final String API_KEY        = "6c24e7c523faa6feee78c696b8ea31e2";
    private static final String SHARED_SECRET  = "88b95e7cc030a4cf";

    /* Instance fields */
    private String url;
    private OAuthRequestToken requestToken;
    private OAuthAccessToken flickrToken;
    private Flickr instance;

    /* Constructors */
    public FlickrManager() {
        instance = new Flickr(API_KEY, SHARED_SECRET);
    }

    /* Accessors and Mutators */
    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public OAuthAccessToken getOAuthToken() {
        return this.flickrToken;
    }
    
    public void getOAuthToken(OAuthAccessToken flickrToken) {
        this.flickrToken = flickrToken;
    }

    public Flickr getInstance() {
        return this.instance;
    }
    
    public void setInstance(Flickr flickr) {
        this.instance = flickr;
    }

    /* APIManager interface method implementations */
    public void authenticate() {
        requestToken = instance.getOAuthRequestToken("oob");
        url = instance.getOAuthCalculateAuthorizationUrl(requestToken.getToken(), AuthLevel.WRITE);
    }
    
    public boolean isAuthenticated() {
        return getOAuthToken() != null;
    }

    /**
     * Get all images from Flickr store. 
     * If large size variant exists for image (we want to ignore thumbnails), create a new image using the large size URL
     * And add the image to metadata collection
     * Also add color data to the metadata collection for the image
    **/
    public ImageDataStore storeMedia() {
        List<Image> images = new List<Image>();
        ImageDataStore iODObj = new ImageDataStore();
        ImageColorData iCDObj = new ImageColorData();
        PhotoCollection photocollection = instance.getPeoplePhotos();
        for (Photo p : photocollection) {
            if (p.getLargeUrl() != null) {
                Image userImage = new Image(p.getLargeUrl());
                iCDObj.getColorData(userImage);
                images.add(userImage);
            }
        }
        iODObj.setImages(images);
        iODObj.setImgObjColorData(iCDObj);
        return iODObj;
    }

    /* Other Public methods */
    public void completeAuth(String code) {
        try {
            setOAuthToken(iInstance.getOAuthAccessToken(requestToken, code));
        }
        catch (FlickrApiException ex) {
            log.log(Level.ERROR, ex.printStackTrace());
        }
    }

}