package guiprojectpacman;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Player extends Rectangle implements BackgroundMusic{
    public boolean right, left, up, down;
    private int speed = 4;
    private int time = 0, targetTime = 10;
    private int imageIndex = 0;
    private int lastDir = 1;
    private String backsoundPath = "pacman_eatfruit.wav";
    public int score = 0;
    public static int mapNo;
    private boolean cekEnemies = false;

    public Player(int x, int y){
        setBounds(x, y, 32, 32);
    }

    public void tick(Game game){
        if(right && canMove(x+speed, y)){
            x+=speed;
            lastDir = 1;
        }
        if(left && canMove(x-speed, y)){
            x-=speed;
            lastDir = -1;
        }
        if(up && canMove(x, y-speed))y-=speed;
        if(down && canMove(x, y+speed))y+=speed;

        Level level = Game.level;

        for(int i = 0; i < Game.level.apples.size(); i++){
            if(this.intersects(level.apples.get(i))){                
                level.apples.remove(i);
                score++;
                playMusic(backsoundPath);
                break;
            }
        }

        if(level.apples.size() == 0){
//            Game end, we win!
            JOptionPane.showMessageDialog(null, "Win with Score : " + this.score);
            insertScore(score, mapNo);
            
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(game);
            parentFrame.dispose();
            
            GameMenu gm = new GameMenu();
            gm.setVisible(true);
            gm.setLocationRelativeTo(null);
            game.stop();
            gm.setResizable(false);
            return;
        }

        for(int i = 0; i < Game.level.enemies.size(); i++){
            Enemy en  = Game.level.enemies.get(i);
            if(en.intersects(this)) {
                cekEnemies = true;
                if(cekEnemies == true){
                    JOptionPane.showMessageDialog(null, "Score : " + this.score);
                    insertScore(this.score, mapNo);
                    
                    JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(game);
                    parentFrame.dispose();
                    
                    GameMenu gm = new GameMenu();
                    gm.setVisible(true);
                    gm.setLocationRelativeTo(null);
                    game.stop();
                    gm.setResizable(false);
                    break;
                }
                return;
            }
        }
        time++;
        if(time == targetTime){
            time = 0;
            imageIndex++;
        }
    }

    @Override
    public void playMusic(String filepath) {
        try{
            File musicPath = new File(filepath);
            if(musicPath.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-10.0f);
                clip.start();
            }else
                System.out.println("Can't find music file");
        }catch(Exception e){
            System.out.println(e);
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
        if(lastDir == 1)
            g.drawImage(Texture.player[imageIndex%2],x, y,width,height,null);
        else
            g.drawImage(Texture.player[imageIndex%2],x+32, y,-width,height,null);
    }
    
    public void insertScore(int score, int map) {
    if (Login.username != null && Login.password != null) {
        String sql;
        switch (map) {
            case 1:
                sql = "UPDATE user SET score_map1 = ? WHERE username = ? AND password = ?";
                break;
            case 2:
                sql = "UPDATE user SET score_map2 = ? WHERE username = ? AND password = ?";
                break;
            default:
                sql = "UPDATE user SET score_map3 = ? WHERE username = ? AND password = ?";
                break;
        }
        Config con = new Config();
        java.sql.Connection conn = con.mysqlconfig;
        try {
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, score);
            pst.setString(2, Login.username);
            pst.setString(3, Login.password);
            
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Score updated successfully");
            } else {
                System.out.println("No user found or score not updated");
            }
        } catch (Exception ex) {
            System.out.println("Error " + ex.getMessage());
        }
    }
}
}
