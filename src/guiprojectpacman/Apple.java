package guiprojectpacman;
import java.awt.Color;
import java.awt.Rectangle;


import java.awt.Graphics;

public class Apple extends Rectangle{
    public Apple(int x, int y){
        setBounds(x, y, 32, 32);
    }

    public void render(Graphics g){
        g.setColor(new Color(47, 143, 229));
        g.fillRect(x, y, 8, 8);

//        SpriteSheet sheet = Game.spriteSheet;
//        g.drawImage(sheet.getSprite(1, 32),x, y, 16 ,16, null);
    }
}
