package com.marin.catch_the_drop;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class GameWindow extends JFrame {
    private static GameWindow game_window;
    private static long lastFrameTime;
    private static Image backGround;
    private static Image gameOver;
    private static Image drop;
    private static float drop_left = 200;
    private static float drop_top = -100;
    private static float dropV = 200;
    private static int score;
    public static void main(String[] args) throws IOException {
        backGround = ImageIO.read(GameWindow.class.getResourceAsStream("background.png"));
        gameOver = ImageIO.read(GameWindow.class.getResourceAsStream("game_over.png"));
        drop = ImageIO.read(GameWindow.class.getResourceAsStream("drop.png"));
        game_window = new GameWindow();
        game_window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game_window.setLocation(200, 100);
        game_window.setSize(906,478);
        game_window.setResizable(false);
        lastFrameTime = System.nanoTime();
        GameField game_field = new GameField();
        JButton restart = new JButton("Restart");
        game_field.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                float dropRight = drop_left + drop.getWidth(null);
                float dropBottom = drop_top + drop.getWidth(null);
                boolean isDrop = x >= drop_left && x <= dropRight && y >= drop_top && y <= dropBottom;
                if (isDrop){
                    drop_top = -100;
                    drop_left = (int)(Math.random() * (game_field.getWidth() - drop.getWidth(null)));
                    dropV = dropV + 20;
                    score++;
                    game_window.setTitle("Score: " + score);
                }
            }
        });
        game_window.add(game_field);
        game_window.setVisible(true);

    }
    private static void onRepaint(Graphics g){
        long currentTime = System.nanoTime();
        float deltaTime = (currentTime - lastFrameTime) * 0.000000001f;
        lastFrameTime = currentTime;
        drop_top = drop_top + dropV * deltaTime;
     g.drawImage(backGround,0,0,null);
     g.drawImage(drop,(int) drop_left,(int)drop_top,null);
     if (drop_top > game_window.getHeight()) {
         g.drawImage(gameOver,280,120,null);
         game_window.addMouseListener(new MouseInputAdapter() {
         });

     }
    }
    public static class GameField extends JPanel{

        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            onRepaint(g);
            repaint();
        }
    }
}
