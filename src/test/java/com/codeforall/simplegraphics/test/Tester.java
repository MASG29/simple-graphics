package com.codeforall.simplegraphics.test;

import com.codeforall.simplegraphics.graphics.*;
import com.codeforall.simplegraphics.keyboard.Keyboard;
import com.codeforall.simplegraphics.keyboard.KeyboardEventType;
import com.codeforall.simplegraphics.keyboard.KeyboardHandler;
import com.codeforall.simplegraphics.mouse.Mouse;
import com.codeforall.simplegraphics.mouse.MouseEvent;
import com.codeforall.simplegraphics.mouse.MouseHandler;
import com.codeforall.simplegraphics.pictures.Picture;
import com.codeforall.simplegraphics.keyboard.KeyboardEvent;

public class Tester implements KeyboardHandler, MouseHandler {

    public static void main(String[] args) throws InterruptedException {

        Tester t = new Tester();
        t.test();

    }

    public void test() throws InterruptedException {

        Keyboard k = new Keyboard(this);
        KeyboardEvent event = new KeyboardEvent();
        event.setKey(KeyboardEvent.KEY_SPACE);
        event.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        k.addEventListener(event);

        Mouse m = new Mouse(this);

        Rectangle rect = new Rectangle(10, 10, 400, 400);
        rect.setColor(Color.BLACK);
        rect.draw();

        Rectangle smallRect = new Rectangle(50, 50, 100, 100);
        smallRect.setColor(Color.RED);
        smallRect.fill();

        Ellipse ellipse = new Ellipse(30, 30, 50, 60);
        ellipse.setColor(Color.YELLOW);
        ellipse.fill();

        Line line = new Line(200, 200, 300, 250);
        line.setColor(Color.BLUE);
        line.draw();

        Text text = new Text(20, 180, "Simple Graphics");
        text.setColor(Color.MAGENTA);
        text.draw();

        Picture pic = new Picture(20, 220, "https://codeforall.com/hs-fs/hubfs/Logos%20And%20Symbols/Code%20for%20All_/Favicon-2.png?width=190&height=190&name=Favicon-2.png");
        pic.draw();

        Thread.sleep(2000);

        smallRect.translate(100, 0);
        ellipse.translate(20, 20);
        line.translate(20, -10);
        text.translate(20, 20);
        pic.translate(40, 0);

        Thread.sleep(2000);

        smallRect.grow(10, 10);
        ellipse.grow(-20, -20);
        line.grow(10, 10);
        text.grow(5, 5);
        pic.grow(-50, -50);

        Thread.sleep(2000);

        text.setText("Code for All_");
    }

    @Override
    public void keyPressed(KeyboardEvent e) {
        System.out.println("SPACE KEY PRESSED");

    }

    @Override
    public void keyReleased(KeyboardEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
       System.out.println(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
