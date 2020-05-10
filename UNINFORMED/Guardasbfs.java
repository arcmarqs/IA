import java.util.*;
import java.lang.*;


class Node{
    int n_rects;
    Rect rect[]=new Rect[n_rects];
    Node parent=null;
    int guard = 0;
    int depth = 0;
    LinkedList<Vert> verts = new LinkedList<>();


    Node(int length,Rect rect[]){
        this.n_rects=length;
        this.rect=rect;
        
    }

    public int get_depth(){
      return depth;
    }

    public void assign_parent(Node n){
        parent = n;
    }

    public boolean check_guard(){
        if(guard == 1) return true;

        return false;
    }

    public void print_verts(){
        while(!(verts.isEmpty())){
          Vert temp = verts.remove();
        System.out.println("(" + temp.x + "," + temp.y + ")");
    }}

    public boolean all_visited(){
        for(int i=1;i<n_rects;i++){
            if((this.rect[i].guarded)==0) return false;
        }

        return true;
    }
    

}
class Vert{
  int x,y,id;
  int rect_ids[] = new int[3+1];
  int n_rects = 0;
  boolean guarded = false;

  Vert(int x, int y, int id){
    this.x = x;
    this.y = y;
    this.id = id;
  }

  boolean in_rect(int rect_id){
    for(int i=0;i<3;i++){
      if(rect_ids[i] == rect_id) return true;
    }
    return false;
  }

  void insert_rect(int id){
    rect_ids[++n_rects] = id;
  }
  int get_x(){
    return this.x;
  }
  int get_y(){
    return this.y;
  }
  int get_id_vert(){
    return this.id;
  }
}

class Rect{
  int vert_ids[];
  int n_verts=0;
  int id;
  int guarded = 1;

  Rect(int n_verts,int id_rect){
    vert_ids = new int[n_verts+1];
    id = id_rect;
  }

  Boolean has_vert(int vert_id){
    for(int i=1;i<=n_verts;i++){
      if(vert_ids[i] == vert_id) return true;
    }
    return false;
  }

  void insert_vert(int vert_id){
    if(has_vert(id)) return;
    vert_ids[++n_verts] = vert_id;
  }
}


public class Guardasbfs{



private static Node child(Node n,HashMap<Integer,Vert> hash,int iteration){  // funçao geradora de estados
  Node child = new Node(n.n_rects,n.rect);
  child.assign_parent(n);
  child.depth=1+n.get_depth();
  child.verts=n.verts;

   Vert newvert= hash.get(iteration);

    place_guard(child,newvert);
   
  
return child;
}


private static Node child_noguard(Node n){  // funçao geradora de estados
  Node child = new Node(n.n_rects,n.rect);
  child.assign_parent(n);
  child.depth=1+n.get_depth();
  child.verts=n.verts;
  child.guard = 0;
return child;
}

private static void place_guard(Node n,Vert newvert){  //colocar um guarda num certo retangulo
 
 if(!n.verts.contains(newvert)){
  for(int i=1;i<newvert.n_rects;i++){

    n.rect[newvert.rect_ids[i]].guarded++;
  }
  newvert.guarded = true; 
  n.verts.add(newvert);
  n.guard=1;   
 }
 
}

public static void print_all_visited(Node n){
  for(int i=1;i<n.n_rects;i++){
    System.out.print(n.rect[i].guarded + " ");
  }
  System.out.println();
}
public static Node bfs(Node start,HashMap<Integer,Vert> hash){
    if(start.all_visited()) return start;
    Queue<Node> q = new LinkedList<>();
    q.add(start);
    Node n;
    int i=0;
    while(!q.isEmpty()){
      n=q.remove();

     print_all_visited(n);

      if(n.all_visited()) return n;

      q.add(child(n,hash,i));     

      q.add(child_noguard(n)); 
      i++;
  
    }
    return null;
}
   public static Vert check_hash(HashMap<Integer,Vert> Vertmap,int x,int y){
    for(Vert vert : Vertmap.values()){
      if(vert.get_x() == x && vert.get_y() == y) return vert;
    }
    return null;
  }
public static void process_rectangles(Scanner input,Rect[] rect,HashMap<Integer,Vert> Vertmap,int n_ret,int to_visit[], int verts_in_rect[]){
    int n_verts,x,y,id,rect_id;
    n_verts = id = 1;
    Vert aux;
    for(int i=1;i<=n_ret;i++){
      rect_id = input.nextInt();
      n_verts = input.nextInt();
      verts_in_rect[rect_id] = n_verts;
      rect[rect_id] = new Rect(n_verts,rect_id);
      if(to_visit[rect_id] == 0) rect[rect_id].guarded = 0;
      for(int j=1;j<=n_verts;j++){
        x = input.nextInt();
        y = input.nextInt();

        aux=check_hash(Vertmap,x,y);
        //already existing vertex
        if(aux!=null){
          rect[rect_id].insert_vert(aux.id);
          if(to_visit[rect_id] == 0) aux.insert_rect(rect_id);
        }
        //new vertex
        else{
        aux = new Vert(x,y,id);
        Vertmap.put(id,aux);
        rect[rect_id].insert_vert(id);
        if(to_visit[rect_id] == 0) aux.insert_rect(rect_id);
        id++;
        }
      }
    }
  }

   public static void main(String [] args){
     Scanner input = new Scanner(System.in);
    int n_instancias = input.nextInt();

    int []verts_in_rect;
    for(int i=1;i<=n_instancias;i++){
      int n_ret = input.nextInt();
      int n_want_visit = input.nextInt();
      int []to_visit = new int[n_ret+1];
      Arrays.fill(to_visit,1);
      for(int k=1;k<=n_want_visit;k++) to_visit[input.nextInt()] = 0;

      HashMap <Integer,Vert> Vertmap = new HashMap<Integer,Vert>();
      //save rectangles
      Rect [] rect = new Rect[n_ret+1];
      verts_in_rect = new int[n_ret+1];
      process_rectangles(input,rect,Vertmap,n_ret,to_visit,verts_in_rect);

    Node n = new Node(n_ret+1,rect);
    Node solution = bfs(n,Vertmap);

    if(solution == null) System.out.println("Nao tem soluçao");

    else{
    solution.print_verts();
    System.out.println(solution.depth);
    }

    for(int c=1;c<solution.n_rects;c++){
      System.out.print(solution.rect[c].guarded+" ");
    }
    Vertmap.clear();
    }
  
  }
}