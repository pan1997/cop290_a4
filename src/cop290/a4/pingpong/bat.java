package cop290.a4.pingpong;

import cop290.a4.animation.Spirit;
import cop290.a4.animation.animPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by pankaj on 13/4/16.
 */
public class bat extends block {
    double loc;
    int or;
    double vel;
    int fillet;

    public bat(animPanel parent, int orientation) {
        super(parent);
        try {
            image = ImageIO.read(new File("./src/cop290/a4/backgrounds/wood.jpg"));
        } catch (IOException ex) {
            System.out.println("image 404 block");
        }
        or = orientation;
        loc = 0.75;
        color = Color.blue;
        width = 100;
        fillet = 3;
        thickness = 20;
        if (or % 2 == 0) {
            l = width;
            b = thickness;
        } else {
            l = thickness;
            b = width;
        }
        switch (or) {
            case 0:
                y = parent.getB() - thickness;
                break;
            case 1:
                x = parent.getL() - thickness;
                break;
            case 2:
                y = 0;
                break;
            case 3:
                x = 0;
                break;
        }
    }

    BufferedImage image;

    @Override
    public void renderSpirit(Graphics2D g) {
        g.setPaint(new TexturePaint(image,rect));
        //g.setColor(color);
        //g.fill(rect);
        g.fillRect((int)rect.getX(),(int)rect.getY(),(int)rect.getWidth(),(int)rect.getHeight());
    }

    @Override
    public void updateSpirit(double dt) {
        loc += vel * dt;
        loc = Math.min(loc, 1 - (width / 2 + 15.0) / parent.getB());
        loc = Math.max(loc, 0 + (width / 2 + 15.0) / parent.getB());
        switch (or) {
            case 0:
                x = (int) (loc * parent.getL()) - width / 2;
                break;
            case 1:
                y = (int) ((1 - loc) * parent.getB()) - width / 2;
                break;
            case 2:
                x = (int) ((1 - loc) * parent.getL()) - width / 2;
                break;
            case 3:
                y = (int) (loc * parent.getB()) - width / 2;
                break;
        }
        super.updateSpirit(dt);
    }
}