package guiprojectpacman;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;

public class Tile extends Rectangle{
    public Tile(int x, int y){
        setBounds(x, y, 32, 32);
    }

    public void render(Graphics g){
        g.setColor(new Color(229, 45, 43));
        g.fillRect(x, y, 32, 32);

//        SpriteSheet sheet = Game.spriteSheet;
//        g.drawImage(sheet.getSprite(64, 0),x, y, 32 ,32, null);
    }
}
