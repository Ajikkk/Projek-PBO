package guiprojectpacman;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.util.Random;

public class Enemy extends Rectangle{
    private int random = 0, smart = 1, find_path = 2;
    private int state = random;
    private int right = 0, left = 1, up = 2, down = 3;
    private int dir = -1;
    public Random randomGen;
    private int time = 0;
    private int targetTime = 60 * 4;
    private int speed = 2; 
    private int lastDir = -1;
    private int timeAnimation = 0, targetTimeAnimation = 10;
    public int imageIndexGhost = 0;

    public Enemy(int x, int y){
        randomGen = new Random();
        setBounds(x, y, 32, 32);
        dir = randomGen.nextInt(4);
    }

    public void tick(){
        if(state == random){
            if(dir == right){
                if(canMove(x+speed, y)){
                    if(randomGen.nextInt(100) < 50)
                        x+=speed;
                }else{
                    dir = randomGen.nextInt(4);
                }
            }
            else if(dir == left){
                if(canMove(x-speed, y)){
                    if(randomGen.nextInt(100) < 50)
                        x-=speed;
                }else{
                    dir = randomGen.nextInt(4);
                }
            }
            else if(dir == up){
                if(canMove(x, y-speed)){
                    if(randomGen.nextInt(100) < 50)
                        y-=speed;
                }else{
                    dir = randomGen.nextInt(4);
                }
            }
            else if(dir == down){
                if(canMove(x, y+speed)){
                    if(randomGen.nextInt(100) < 50)
                        y+=speed;
                }else{
                    dir = randomGen.nextInt(4);
                }
            }
            time++;
            if(time == targetTime*2){
                state = smart;
                time = 0;
            }
        }else if(state == smart){
            boolean move = false;
            if(x < Game.player.x){
                if(canMove(x+speed, y)){
                    if(randomGen.nextInt(100) < 50)
                        x += speed;
                    move = true;
                    lastDir = right;
                }
            }
            if(x > Game.player.x){
                if(canMove(x-speed, y)){
                    if(randomGen.nextInt(100) < 50)
                        x -= speed;
                    move = true;
                    lastDir = left;
                }
            }
            if(y < Game.player.y){
                if(canMove(x, y+speed)){
                    if(randomGen.nextInt(100) < 50)
                        y += speed;
                    move = true;
                    lastDir = down;
                }
            }
            if(y > Game.player.y){
                if(canMove(x, y-speed)){
                    if(randomGen.nextInt(100) < 50)
                        y -= speed;
                    move = true;
                    lastDir = up;
                }
            }

            if(x == Game.player.x && y == Game.player.y)
                move = true;

            if(!move){
                state = find_path;
            }

            time++;
            if(time == targetTime){
                state = random;
                time = 0;
            }
        }else if(state == find_path){
                if(lastDir == right){
                    if(y < Game.player.y){
                        if(canMove(x, y+speed)){
                            if(randomGen.nextInt(100) < 50)
                                y += speed;
                            state = smart;
                        }
                    }else{
                        if(canMove(x, y-speed)) {
                            if(randomGen.nextInt(100) < 50)
                                y -= speed;
                            state = smart;
                        }
                    }
                    if(canMove(x+speed, y))
                        x += speed;
                }else if(lastDir == left){
                    if(y < Game.player.y){
                        if(canMove(x, y+speed)) {
                            if(randomGen.nextInt(100) < 50)
                                y += speed;
                            state = smart;
                        }
                    }else{
                        if(canMove(x, y-speed)){
                            if(randomGen.nextInt(100) < 50)
                                y -= speed;
                            state = smart;
                        }
                    }
                    if(canMove(x-speed, y))
                        x -= speed;
                }else if(lastDir == up){
                    if(x < Game.player.x){
                        if(canMove(x+speed, y)){
                            if(randomGen.nextInt(100) < 50)
                                x += speed;
                            state = smart;
                        }
                    }else{
                        if(canMove(x-speed, y)){
                            if(randomGen.nextInt(100) < 50)
                                x -= speed;
                            state = smart;
                        }
                    }
                    if(canMove(x, y-speed))
                        y -= speed;
                }else if(lastDir == down){
                    if(x < Game.player.x){
                        if(canMove(x+speed, y)) {
                            if(randomGen.nextInt(100) < 50)
                                x += speed;
                            state = smart;
                        }
                    }else{
                        if(canMove(x-speed, y)) {
                            if(randomGen.nextInt(100) < 50)
                                x -= speed;
                            state = smart;
                        }
                    }
                    if(canMove(x, y+speed))
                        y += speed;
                }

            time++;
            if(time == targetTime){
                state = random;
                time = 0;
            }
        }

        timeAnimation++;
        if(timeAnimation == targetTimeAnimation){
            timeAnimation = 0;
            imageIndexGhost++;
        }
    }

    private boolean canMove(int nextx, int nexty){
        Rectangle bounds = new Rectangle(nextx, nexty, width, height);
        Level level = Game.level;
        for(int i = 0; i < level.tiles.length; i++){
            for(int j = 0; j < level.tiles[0].length; j++){
                if(level.tiles[i][j] != null){
                    if(bounds.intersects(level.tiles[i][j])){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void render(Graphics g){
        g.drawImage(Texture.ghost[imageIndexGhost%2],x,y,width,height,null);
    }
}