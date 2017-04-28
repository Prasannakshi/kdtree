package structures;

import processing.core.PConstants;
import static structures.kdtree.*;

/**
 * Created by Prasannakshi on 3/9/2017.
 */
public class axisline {
    private kdtree.MDNode requestnode;
    private int xaxis;
    private int yaxis;

    public axisline(kdtree.MDNode node) {
        this.xaxis = (int)node.myData[0];
        this.yaxis = (int)node.myData[1];
        this.requestnode = node;
        displayline();
        displaypoint();
        displaytext();
    }

    void displayline() {
        parent.stroke(129, 206, 15);
        parent.strokeWeight(1);
        if(requestnode==root){
            parent.line(xaxis,0,xaxis,parent.height);
        } else if(requestnode.Disc==0) {
            parent.line(xaxis, (int)disc1.pop(), xaxis, findcoordinate(requestnode));
        } else {
            parent.line((int)disc0.pop(), yaxis, findcoordinate(requestnode), yaxis);
        }
    }

    void displaypoint() {
        parent.stroke(255);
        parent.strokeWeight(8);
        parent.point(xaxis, yaxis);
    }

    void displaytext() {
        parent.textAlign(PConstants.CENTER);
        parent.textSize(15);
        parent.text("("+xaxis+","+yaxis+")",xaxis+35,yaxis);
    }
}
