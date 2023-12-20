package guiprojectpacman;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Texture {
    public static BufferedImage[] player;
    public static BufferedImage[] ghost;
    private BufferedImage spriteSheet;

    public Texture(){
        try {
            spriteSheet = ImageIO.read(getClass().getResource("/Sprites/spritesheet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        player = new BufferedImage[2];
        player[0] = getSprite(0, 0);
        player[1] = getSprite(16, 0);

        ghost = new BufferedImage[2];
        ghost[0] = getSprite(0, 16);
        ghost[1] = getSprite(16, 16);
    }

    public BufferedImage getSprite(int x, int y){
        return spriteSheet.getSubimage(x, y, 16, 16);
    }
}
