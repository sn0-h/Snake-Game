package Snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = 14400;
    static final int DELAY = 1000;
    final int[] X = new int[14400];
    final int[] Y = new int[14400];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random = new Random();

    GamePanel() {
        this.setPreferredSize(new Dimension(600, 600));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.startGame();
    }

    public void startGame() {
        this.newApple();
        this.running = true;
        this.timer = new Timer(1000, this);
        this.timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.draw(g);
    }

    public void draw(Graphics g) {
        if (this.running) {
            int i;
            for(i = 0; i < 24; ++i) {
                g.drawLine(i * 25, 0, i * 25, 600);
                g.drawLine(0, i * 25, 600, i * 25);
            }

            g.setColor(Color.red);
            g.fillOval(this.appleX, this.appleY, 25, 25);

            for(i = 0; i < this.bodyParts; ++i) {
                if (i == 0) {
                    g.setColor(Color.green);
                } else {
                    g.setColor(new Color(45, 180, 0));
                }

                g.fillRect(this.X[i], this.Y[i], 25, 25);
                g.setColor(Color.red);
                g.setFont(new Font("Ink Free", 1, 40));
                FontMetrics metrics = this.getFontMetrics(g.getFont());
                g.drawString("Score: " + this.applesEaten, (600 - metrics.stringWidth("Score: " + this.applesEaten)) / 2, g.getFont().getSize());
            }
        } else {
            this.gameOver(g);
        }

    }

    public void newApple() {
        this.appleX = this.random.nextInt(24) * 25;
        this.appleY = this.random.nextInt(24) * 25;
    }

    public void move() {
        for(int i = this.bodyParts; i > 0; --i) {
            this.X[i] = this.X[i - 1];
            this.Y[i] = this.Y[i - 1];
        }

        switch (this.direction) {
            case 'D':
                this.Y[0] += 25;
                break;
            case 'L':
                this.X[0] -= 25;
                break;
            case 'R':
                this.X[0] += 25;
                break;
            case 'U':
                this.Y[0] -= 25;
        }

    }

    public void checkApple() {
        if (this.X[0] == this.appleX && this.Y[0] == this.appleY) {
            ++this.bodyParts;
            ++this.applesEaten;
            this.newApple();
        }

    }

    public void checkCollision() {
        for(int i = this.bodyParts; i > 0; --i) {
            if (this.X[0] == this.X[i] && this.Y[0] == this.Y[i]) {
                this.running = false;
            }
        }

        if (this.X[0] < 0) {
            this.running = false;
        }

        if (this.X[0] > 600) {
            this.running = false;
        }

        if (this.Y[0] < 0) {
            this.running = false;
        }

        if (this.Y[0] > 600) {
            this.running = false;
        }

        if (!this.running) {
            this.timer.stop();
        }

    }

    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", 1, 40));
        FontMetrics metrics1 = this.getFontMetrics(g.getFont());
        g.drawString("Score: " + this.applesEaten, (600 - metrics1.stringWidth("Score: " + this.applesEaten)) / 2, g.getFont().getSize());
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", 1, 75));
        FontMetrics metrics = this.getFontMetrics(g.getFont());
        g.drawString("Game Over", (600 - metrics.stringWidth("Game Over")) / 2, 300);
    }

    public void actionPerformed(ActionEvent e) {
        if (this.running) {
            this.move();
            this.checkApple();
            this.checkCollision();
        }

        this.repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        public MyKeyAdapter() {
        }

        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case 37:
                    if (GamePanel.this.direction != 'R') {
                        GamePanel.this.direction = 'L';
                    }
                    break;
                case 38:
                    if (GamePanel.this.direction != 'D') {
                        GamePanel.this.direction = 'U';
                    }
                    break;
                case 39:
                    if (GamePanel.this.direction != 'L') {
                        GamePanel.this.direction = 'R';
                    }
                    break;
                case 40:
                    if (GamePanel.this.direction != 'U') {
                        GamePanel.this.direction = 'D';
                    }
            }

        }
    }
}

