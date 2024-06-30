package Snake;

import java.awt.Component;
import javax.swing.JFrame;

public class GameFrame extends JFrame {
    GameFrame() {
        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(3);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo((Component)null);
    }
}
