package visualization;

import processing.core.PApplet;

import java.awt.*;

/**
 * Created by Prasannakshi on 3/7/2017.
 */
public class doublescreen extends PApplet{
    PFrame f;
    secondApplet s;

    public static void main(String args[]){
        PApplet.main(new String[]{"visualization.doublescreen"});
    }
    public void settings(){
        size(320, 240);
    }

    public void setup() {
        PFrame f = new PFrame();
    }

    public void draw() {
        background(255,0,0);
        fill(255);
        rect(10,10,frameCount%100,10);
        s.background(0, 0, 255);
        s.fill(100);
        s.rect(10,20,frameCount%120,10);
        s.redraw();
    }

    public class PFrame extends Frame {
        public PFrame() {
            setBounds(100,100,400,300);
            s = new secondApplet();
        }
    }

    public class secondApplet extends PApplet {
        public void settings(){
            size(400, 300);
        }

        public void setup() {
            noLoop();
        }

        public void draw() {
        }
    }
}
