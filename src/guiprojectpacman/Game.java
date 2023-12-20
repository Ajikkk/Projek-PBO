package guiprojectpacman;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable, KeyListener {
    private boolean isRunning = false;
    private static final int WIDTH = 640, HEIGHT = 480;
    public static final String TITLE = "Pac-Man";
    private Thread thread;
    public static Player player;
    public static Level level;
    public static SpriteSheet spriteSheet;
    
    public Game(){        
        Dimension dimension = new Dimension(Game.WIDTH, Game.HEIGHT);
        setPreferredSize(dimension);
        setMinimumSize(dimension);
        setMaximumSize(dimension);
        addKeyListener(this);
        player = new Player(Game.WIDTH/2, Game.HEIGHT/2);
        startGameMap1();
        spriteSheet = new SpriteSheet("/Sprites/spritesheet.png");
        new Texture();
    }
    
    public static void startGameMap1(){
        level = new Level("/map/Map1.png");
    }
    
    public void startGameMap2(){
        level = new Level("/map/Map2.png");
    }
    
    public void startGameMap3(){
        level = new Level("/map/Map3.png");
    }
    
    public synchronized void start(){
        if(isRunning) return;
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }
    
    public synchronized void stop(){
        if(!isRunning) return;
        isRunning = false;
        try{
            thread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
    
    private void tick(){
        player.tick(this);
        level.tick();
    }

    private void render(){
        BufferStrategy bs = getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
        player.render(g);
        level.render(g);
        g.dispose();
        bs.show();
    }

    public void run(){
        requestFocus();
        int fps = 0;
        double timer = System.currentTimeMillis();
        long lastTime = System.nanoTime();
        double targetTick = 60.0;
        double delta = 0;
        double ns = 1000000000 / targetTick;
        while(isRunning){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while(delta >= 1){
                tick();
                render();
                fps++;
                delta--;
            }
            if(System.currentTimeMillis() - timer >= 1000){
//                System.out.println(fps);
                fps = 0;
                timer += 1000;
            }
        }        
        stop();
    }

    public void gameStart(int map){
        Game game = new Game();
        if(map == 1)
            startGameMap1();
        else if(map == 2)
            startGameMap2();
        else if(map == 3)
            startGameMap3();
        JFrame frame = new JFrame();
        frame.setTitle(Game.TITLE);
        frame.add(game);
        frame.setResizable(false);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        game.start();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) player.right = true;
        if(e.getKeyCode() == KeyEvent.VK_LEFT) player.left = true;
        if(e.getKeyCode() == KeyEvent.VK_UP) player.up = true;
        if(e.getKeyCode() == KeyEvent.VK_DOWN) player.down = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) player.right = false;
        if(e.getKeyCode() == KeyEvent.VK_LEFT) player.left = false;
        if(e.getKeyCode() == KeyEvent.VK_UP) player.up = false;
        if(e.getKeyCode() == KeyEvent.VK_DOWN) player.down = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
