package program;

import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.Textarea;
import controlP5.Textfield;
import processing.core.PApplet;
import processing.core.PFont;
import structures.KDtreeADT;
import structures.kdtree;

import java.util.ArrayList;

/**
 * Created by Prasannakshi on 1/30/2017.
 */
public class myApplication extends PApplet {
    int coordinate =0;
    KDtreeADT<Integer> BST;
    boolean mousepoint = false;
    ArrayList xcoordinate = new ArrayList();
    ArrayList ycoordinate = new ArrayList();
    ControlP5 control; // controls IO
    Textarea myTextarea;
//    PApplet thistree;

    //Design GUI
    public static void main(String[] args) {
        PApplet.main(new String[]{"program.myApplication"});
    }

    public void settings()
    {
        fullScreen();
    }

    public void setup() {
        BST = new kdtree<>(this);
        control = new ControlP5(this);
        PFont font = createFont("arial", 20);
        control.addTextfield("Enter the coordinates")
                .setPosition(70, 20)
                .setSize(200, 30)
                .setFont(font)
                .setColorBackground(color(255, 255, 255))
                .setFocus(true)
                .setColor(0)
                .setColorCursor(0)
        ;

        control.addButton("Search")
                .setPosition(320, 20)
                .setSize(80, 30);

        control.addButton("Insert")
                .setPosition(420, 20)
                .setSize(80, 30);

        control.addButton("Delete")
                .setPosition(520, 20)
                .setSize(80, 30);

        myTextarea = control.addTextarea("text")
                            .setPosition(650,25)
                            .setSize(700,20)
                            .setFont(createFont("arial",15))
                            .setText("Incorrect data.Please enter coordinate of form (a,b)")
                            .setVisible(false);

        control.getController("Enter the coordinates").getCaptionLabel().setFont(createFont("arial", 10));
        control.getController("Insert").getCaptionLabel().setFont(createFont("arial", 15));
        control.getController("Delete").getCaptionLabel().setFont(createFont("arial", 15));
        control.getController("Search").getCaptionLabel().setFont(createFont("arial", 15));
    }

    public void draw() {
        control.get(Textfield.class, "Enter the coordinates").setFocus(true);
        update(mouseX,mouseY);
        if(coordinate ==0 ){return;}
        if(mousepoint == true) {
            this.stroke(255, 0, 0);
            this.strokeWeight(6);
            this.point((int) xcoordinate.get(coordinate), (int) ycoordinate.get(coordinate));
        } else {
            this.stroke(255);
            this.strokeWeight(6);
            this.point((int)xcoordinate.get(coordinate), (int)ycoordinate.get(coordinate));
        }
    }

    public void controlEvent(ControlEvent theEvent) {
        String test = control.get(Textfield.class,"Enter the coordinates").getText();
        if(test.isEmpty()) {
            myTextarea.setVisible(true);
        } else {
            myTextarea.setVisible(false);
            if (theEvent.getController().getName() == "Insert") {
                Comparable[] node = new Comparable[2];
                String[] input = test.split(",");
                node[0] = Integer.parseInt(input[0]);
                node[1] = Integer.parseInt(input[1]);
                BST.insertmd(node);
                xcoordinate.add(node[0]);
                ycoordinate.add(node[1]);

            } else if (theEvent.getController().getName() == "Search") {
                Comparable[] node = new Comparable[2];
                String[] input = test.split(",");
                node[0] = Integer.parseInt(input[0]);
                node[1] = Integer.parseInt(input[1]);
                BST.searchmd(node);
            } else {
                Comparable[] node = new Comparable[2];
                String[] input = test.split(",");
                node[0] = Integer.parseInt(input[0]);
                node[1] = Integer.parseInt(input[1]);
                BST.deletemd(node);
                xcoordinate.remove(xcoordinate.indexOf(node[0]));
                ycoordinate.remove(ycoordinate.indexOf(node[1]));
            }
            control.get(Textfield.class, "Enter the coordinates").clear();
        }
    }

    private void update(int X, int Y) {
        if(overpoint()){
            mousepoint = true;
        } else {
            mousepoint = false;
        }
    }

    private boolean overpoint() {
        boolean fate = false;
        for(int i=0; i<xcoordinate.size();i++){
            int nodex = (int) xcoordinate.get(i);
            int nodey = (int) ycoordinate.get(i);
            if ((mouseX <= nodex + 8) && (mouseX >= (nodex - 8)))
                if ((mouseY >= nodey - 8) && (mouseY <= nodey + 8)) {
                    fate = true;
                    coordinate = i;
                    break;
                }        }
        return fate;
    }

}