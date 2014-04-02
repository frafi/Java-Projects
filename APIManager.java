package photorainbow;

/**
 * Generic methods for authenticaing and storing images.
 *
 * @author afawad
 */

public interface APIManager {

    /**
     * Authenticate the client
     * @return
     */
    public void authenticate();

    /**
     * Persist images
     * @return Metadata about each image
     */
    public ImageDataStore storeMedia();

    /**
     * Check if client is authenticated
     * @return true or false
     */
    public boolean isAuthenticated();
}

