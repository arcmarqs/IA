import java.util.*;
import java.lang.*;


class Vert{
    int x,y,id;
    LinkedList<Integer> rectid=new LinkedList<>();
    boolean guard=false;

    Vert(int x,int y,int id){
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public void add_rect(int rect_id){
        rectid.add(rect_id);
    }


  public String print_vert(){
    return ("x:"+ x + " y:"+ y);
  }
}
