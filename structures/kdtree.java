package structures;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Created by Prasannakshi on 1/31/2017.
 */
public class kdtree<E> implements KDtreeADT<E> {
    public static MDNode root;
    static PApplet parent;// The parent PApplet that we will render ourselves onto
    static Stack disc0;
    static Stack disc1;
    ArrayList<axisline> Lines;

    public kdtree(PApplet applet) {
        parent = applet;
        parent.background(0);
        Lines = new ArrayList<>();
    }

    @Override
    public void insertmd(Comparable entry[]) {
        root = insert(null,root,entry,0);
        drawline(root);
    }

    private MDNode insert(Comparable[] parentnode,MDNode node, Comparable[] newnode, int length) {
        if(node==null){
            node = new MDNode(parentnode,newnode,length);
        } else {
            if(newnode[length].compareTo(node.myData[length])<0){
                node.myleft = insert(node.myData,node.myleft,newnode,1-length);
            } else {
                node.myright = insert(node.myData,node.myright,newnode,1-length);
            }
        }
        return node;
    }

    private void drawline(MDNode node) {
        if(node.myline==false){
            disc0 = new Stack();
            disc1 = new Stack();
            find(root,node);
            Lines.add(new axisline(node));
            node.myline = true;
        } else {
            if(node.myleft!=null){
                drawline(node.myleft);
            }
            if(node.myright!=null) {
                drawline(node.myright);
            }
        }
    }

    public static int findcoordinate(MDNode node){
        int coordinate;
        if(node.Disc==0){
            if(node.myParent[1].compareTo(node.myData[1])>0){
                try{coordinate = (int)disc1.pop();}
                catch(EmptyStackException e){
                    coordinate = 0;
                }
                if(node.myData[1].compareTo(coordinate)<0){coordinate=0;}
            } else {
                try{coordinate = (int)disc1.pop();}
                catch(EmptyStackException e){
                    coordinate = parent.height;
                }
                if(node.myData[1].compareTo(coordinate)>0){coordinate=parent.height;}
            }
        } else {
            if(node.myParent[0].compareTo(node.myData[0])>0){
                try{coordinate = (int)disc0.pop();}
                catch(EmptyStackException e){
                    coordinate =0;
                }
                //Special case has to be handled
                if(node.myData[0].compareTo(coordinate)<0){
                    if(node.myData[0].compareTo(root.myData[0])>0){coordinate = (int)root.myData[0];}
                    else {coordinate = 0;}
                }
            } else {
                try{coordinate = (int)disc0.pop();}
                catch(EmptyStackException e){
                    coordinate = parent.width;
                }
                if(node.myData[0].compareTo(coordinate)>0){
                    if(node.myData[0].compareTo(root.myData[0])<0){coordinate = (int)root.myData[0];}
                    else {coordinate= parent.width;}
                }
            }
        }
        return coordinate;
    }

    private void find(MDNode tree, MDNode node) {
        int length = tree.Disc;
        if(tree.Disc==0){disc0.push(tree.myData[0]);}
        if(tree.Disc==1){disc1.push(tree.myData[1]);}
        if(tree.myData[length].compareTo(node.myData[length])>0){
            find(tree.myleft,node);
        } else if (tree.myData[length].compareTo(node.myData[length])<0){
            find(tree.myright,node);
        }
    }

    @Override
    public void deletemd(Comparable list[]) {
        if(root==null){System.out.println("Empty tree");}
//        else if(searchmd(list)==false){System.out.println("Sorry ("+list[0]+","+list[1]+") trying to delete is not present");}
        else {
            root = delete(root,list,0);
            System.out.println("The Element ("+list[0]+","+list[1]+") deleted succefully");
        }
    }

    private MDNode delete(MDNode node, Comparable[] theElement,int length) {
        if(node==null) {
            return null;
        } else if(node.myData[length].compareTo(theElement[length])>0){
            node.myleft = delete(node.myleft,theElement,1-length);
        } else if (node.myData[length].compareTo(theElement[length])<0){
            node.myright = delete(node.myright,theElement,1-length);
        } else {
            if(node.myleft == null && node.myright == null){
//                parent.background(0);
//                drawline(root);
                return null;
            }
            if(node.myright==null){
                node.myData = findmax(node.myleft, length);
                node.myleft = delete(node.myleft,node.myData,1-length);
            } else {
                node.myData = findmin(node.myright, length);
                node.myright = delete(node.myright,node.myData,1-length);
            }
        }
        return node;
    }

    private Comparable[] findmax(MDNode myleft, int length) {
        MDNode left = myleft.myleft;
        MDNode right = myleft.myright;
        Comparable[] newElement = myleft.myData;
        if(left==null && right==null) {
            return newElement;
        }
        if(left !=null && newElement[length].compareTo(left.myData[length])<0){
            newElement = left.myData;
        }
        if (right!=null && newElement[length].compareTo(right.myData[length])<0){
            newElement = right.myData;
        }
        return newElement;
    }

    private Comparable[] findmin(MDNode myright, int length) {
        MDNode left = myright.myleft;
        MDNode right = myright.myright;
        Comparable[] newElement = myright.myData;
        if(left == null && right == null) {
            return newElement;
        }
        if(left!=null && newElement[length].compareTo(left.myData[length])>0){
            newElement = left.myData;
        }
        if (right!=null && newElement[length].compareTo(right.myData[length])>0){
            newElement = right.myData;
        }
        return newElement;
    }

    @Override
    public boolean searchmd(Comparable list[]){
        return Search(root,list);
    }

    private boolean Search(MDNode node, Comparable[] theElement) {
        parent.stroke(255,0,0);
        parent.strokeWeight(6);
        parent.point((int)node.myData[0],(int)node.myData[1]);
        boolean found = false;
        while((node!=null) && !found ){
            int level = node.Disc;
            Comparable[] temp = node.myData;
            if(theElement[level].compareTo(temp[level])<0){
                node = node.myleft;
            } else if (theElement[level].compareTo(temp[level])>0){
                node = node.myright;
            } else {
                if (theElement[level].compareTo(temp[level])==0){
                    found = true;
                    parent.stroke(0,0,255);
                    parent.point((int)node.myData[0],(int)node.myData[1]);
                    break;
                }
                node = node.myright;
            }
            found = Search(node,theElement);
        }
        return found;
    }

    public static class MDNode {
        public Comparable[] myData;
        private Comparable[] myParent;
        private MDNode myleft;
        private MDNode myright;
        private boolean myline;
        public int Disc;

        MDNode(Comparable p[],Comparable theData[], int length){
            myData = new Comparable[2];
            for(int i =0 ;i<2;i++){
                myData[i] = theData[i];
            }
            this.myParent = p;
            this.Disc = length;
            this.myleft = null;
            this.myright = null;
            this.myline = false;
        }
    }
}
