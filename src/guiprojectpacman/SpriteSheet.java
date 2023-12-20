package guiprojectpacman;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {

    private BufferedImage sheet;
    public SpriteSheet(String path){
        try{
            sheet = ImageIO.read(getClass().getResource(path));
        }catch(IOException e){
            System.out.println("Failed to load image!");
        }
    }

    public BufferedImage getSprite(int x, int y){
        return sheet.getSubimage(x, y, 16, 16);
    }
}
