package photorainbow;

import java.lang.StringBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.*;
import java.io.*;

public class Image {

    private static final Logger log = Logger.getLogger(Image.class.getName());

    public String imageUrl;
    private Bitmap bitmap;
    
    public Image(String imgUrl) {
        this.imageUrl = imgUrl;
        download();
    }
    
    public Image() {
    }
    
    public void download() {
        try {
            URL iurl = new URL(imageUrl);
            URLConnection yc = iurl.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(yc.getInputStream()));

            StringBuffer imageData = new StringBuffer();
            while ((inputLine = reader.readLine()) != null) {
                imageData.append(inputLine);
            }
            bitmap = new Bitmap(imageData);
            Bitmap scaledImage = new Bitmap(bitmap, new Size(100, 100));
            bitmap = scaledImage;
        }
        catch (Exception e) {
            log.log(Level.ERROR, e.printStackTrace());
        }
        finally {
            try {
                if (reader != null) reader.close();
			} catch (IOException ex) {
				log.log(Level.ERROR, ex.printStackTrace());
			}
        }
    }
    
    public String getUrl() {
        return this.imageUrl;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public Bitmap getBitmap() {
        return this.bitmap;
    }
    
    public void setBitmap(Bitmap data) {
        this.bitmap = data;
    }
}