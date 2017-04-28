package visualization;

import processing.core.PApplet;

import java.util.ArrayList;

/**
 * Created by Prasannakshi on 3/9/2017.
 */
public class sample extends PApplet{
    ArrayList<Particle> particles;
    private boolean selection = false;

    public static void main(String args[]){
        PApplet.main(new String[]{"visualization.sample"});
    }

    public void settings(){
        size(480, 270);
    }

    public void setup() {
        background(255);
    }

    public void mousePressed(){
        particles.remove(0);
    }

    public void draw() {

        particles = new ArrayList();
        Particle newone = new Particle(30,50);
        particles.add(newone);
        newone.display();

        if(particles.size()>10){
            particles.remove(0);
        }
//        // A new Particle object is added to the ArrayList every cycle through draw().
//        particles.add(new Particle());
//
//        background(255);
//        // Iterate through our ArrayList and get each Particle
//        // The ArrayList keeps track of the total number of particles.
//        for (Particle p : particles) {
//            p.run();
//            p.gravity();
//            p.display();
//        }
//
//        // If the ArrayList has more than 100 elements in it, we delete the first element, using remove().
//        if (particles.size() > 100) {
//            particles.remove(0);
//        }
    }

    class Particle {

        float u;
        float v;
//        float xspeed;
//        float yspeed;

        Particle(int x,int y) {
            u = x;
            v = y;
//            xspeed = random(-1,1);
//            yspeed = random(-2,0);
        }

//        void run() {
//            x = x + xspeed;
//            y = y + yspeed;
//        }
//
//        void gravity() {
//            yspeed += 0.1;
//        }

        void display() {
            stroke(0);
            fill(0,75);
            ellipse(u,v,10,10);
        }
    }
}