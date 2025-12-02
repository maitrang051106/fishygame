package enity;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Aquarium extends Entity {
    GamePanel gp;
    Random rand = new Random();
    
    // Danh sách chứa tất cả cá (trừ người chơi)
    public ArrayList<Entity> entities = new ArrayList<>();
    
    // Bộ đếm thời gian
    int spawnCounter = 0;

    public Aquarium(GamePanel gp) {
        this.gp = gp;
    }

    // Hàm sinh ngẫu nhiên 1 con cá
    public void spawnEntity() {
        Entity obj = new Entity();

        // 1. Random vị trí (trong phạm vi World)
        obj.x = rand.nextInt(0,2);
        obj.direction = "right";
        if(obj.x == 1){
            obj.x = gp.screenWidth;
            obj.direction = "left";
        }
        obj.y = rand.nextInt(gp.screenHeight - gp.tileSize ); // Vị trí y ngẫu nhiên
        obj.name = "Enemy";
        obj.speed = 7;
        try {
            if(obj.x == 0)
                obj.up1 = ImageIO.read(getClass().getResourceAsStream("/res/eat1.png"));
            else
            obj.up1 = ImageIO.read(getClass().getResourceAsStream("/res/eat2.png"));
        } catch (IOException e) {
            System.out.println("Lỗi khi tải ảnh kẻ thù!");
            e.printStackTrace();
        }
        entities.add(obj); // Thêm vào danh sách
    }

    // Hàm cập nhật (Gọi trong GamePanel.update)
    public void update() {
        // --- LOGIC SINH CÁ ---
        spawnCounter++;
        if (spawnCounter > 60) { // Cứ 60 khung hình (khoảng 1s) thì sinh 1 con
            spawnEntity();
            spawnCounter = 0;
        }

        // --- CẬP NHẬT VỊ TRÍ TỪNG CON ---
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            if (e != null) {
                if(e.direction.equals("right"))
                    e.x += e.speed; // Di chuyển sang phải
                else
                    e.x -= e.speed; // Di chuyển sang trái
            }
            // (Tùy chọn) Xóa cá nếu bơi ra xa quá hoặc danh sách quá dài
        }
    }

    // Hàm vẽ (Gọi trong GamePanel.paintComponent)
    public void draw(Graphics2D g2) {
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            if (e != null) { 
                g2.drawImage(e.up1, e.x, e.y, gp.tileSize, gp.tileSize, null);
            }
        }
    }
}
