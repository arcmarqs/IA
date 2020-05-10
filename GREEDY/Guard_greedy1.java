import java.util.Scanner;
import java.util.HashMap;
import java.util.Arrays;

class Qnode {
    int vert;
    int vertkey;

    Qnode(int v, int key) {
  vert = v;
  vertkey = key;
    }
}

class Heapmax {
    private int posinvalida = 0;
    int sizeMax,size;

    Qnode[] a;
    int[] pos_a;

    Heapmax(int vec[], int n) {
  a = new Qnode[n + 1];
  pos_a = new int[n + 1];
  sizeMax = n;
  size = n;
  for (int i = 1; i <= n; i++) {
      a[i] = new Qnode(i,vec[i]);
      pos_a[i] = i;
  }

  for (int i = n/2; i >= 1; i--)
      heapify(i);
    }

    boolean isEmpty() {
  if (size == 0) return true;
  return false;
    }

    int extractMax() {
  int vertv = a[1].vert;
  swap(1,size);
  pos_a[vertv] = posinvalida;  // assinala vertv como removido
  size--;
  heapify(1);
  return vertv;
    }

    void increaseKey(int vertv, int newkey) {

  int i = pos_a[vertv];
  a[i].vertkey = newkey;

  while (i > 1 && compare(i, parent(i)) > 0) {
      swap(i, parent(i));
      i = parent(i);
  }
    }

    void decreaseKey(int vertv, int newkey) {
  int i = pos_a[vertv];
  a[i].vertkey = newkey;

  heapify(i);
  }

    void insert(int vertv, int key)
    {
  if (sizeMax == size)
      new Error("Heap is full\n");

  size++;
  a[size].vert = vertv;
  pos_a[vertv] = size;   // supondo 1 <= vertv <= n
  increaseKey(vertv,key);   // aumenta a chave e corrige posicao se necessario
    }

    void write_heap(){
  System.out.printf("Max size: %d\n",sizeMax);
  System.out.printf("Current size: %d\n",size);
  System.out.printf("(Vert,Key)\n---------\n");
  for(int i=1; i <= size; i++)
      System.out.printf("(%d,%d)\n",a[i].vert,a[i].vertkey);

  System.out.printf("-------\n(Vert,PosVert)\n---------\n");

  for(int i=1; i <= sizeMax; i++)
      if (pos_valida(pos_a[i]))
    System.out.printf("(%d,%d)\n",i,pos_a[i]);
    }

    private int parent(int i){
  return i/2;
    }
    private int left(int i){
  return 2*i;
    }
    private int right(int i){
  return 2*i+1;
    }

    private int compare(int i, int j) {
  if (a[i].vertkey < a[j].vertkey)
      return -1;
  if (a[i].vertkey == a[j].vertkey)
      return 0;
  return 1;
    }


    private void heapify(int i) {
  int l, r, largest;

  l = left(i);
  if (l > size) l = i;

  r = right(i);
  if (r > size) r = i;

  largest = i;
  if (compare(l,largest) > 0)
      largest = l;
  if (compare(r,largest) > 0)
      largest = r;

  if (i != largest) {
      swap(i, largest);
      heapify(largest);
  }

    }

    private void swap(int i, int j) {
  Qnode aux;
  pos_a[a[i].vert] = j;
  pos_a[a[j].vert] = i;
  aux = a[i];
  a[i] = a[j];
  a[j] = aux;
    }

    private boolean pos_valida(int i) {
  return (i >= 1 && i <= size);
    }
}



/*
  rect -id_ret
       -array com id_verts

  verts -id_vert
        -x,y
        -retangulos que cobre
*/
class Vert{
  int x,y,id;
  int rect_ids[] = new int[4];
  int n_rects = 0;

  Vert(int x, int y, int id){
    this.x = x;
    this.y = y;
    this.id = id;
  }

  boolean in_rect(int rect_id){
    for(int i=1;i<=3;i++){
      if(rect_ids[i] == rect_id) return true;
    }
    return false;
  }

  void insert_rect(int rect_id){
    if(in_rect(rect_id)) return;
    rect_ids[++n_rects] = rect_id;

  }
  int get_x(){
    return this.x;
  }
  int get_y(){
    return this.y;
  }
}

class Rect{
  int vert_ids[];
  int n_verts=0;
  int id;
  //boolean guarded = false;

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

public class Guard_greedy1{
  public static Vert check_hash(HashMap<Integer,Vert> Vertmap,int x,int y){
    for(Vert vert : Vertmap.values()){
      if(vert.get_x() == x && vert.get_y() == y) return vert;
    }
    return null;
  }

  public static boolean all_guarded(boolean []to_visit, int w_size){
    for(int i=1;i<=w_size;i++){
      if(!to_visit[i]) return false;
    }
    return true;
  }
  public static void process_rectangles(Scanner input,Rect[] rect,HashMap<Integer,Vert> Vertmap,int n_ret,boolean to_visit[]){
    int n_verts,x,y,id,rect_id;
    n_verts = id = 1;
    Vert aux;
    for(int i=1;i<=n_ret;i++){
      rect_id = input.nextInt();
      n_verts = input.nextInt();
      rect[rect_id] = new Rect(n_verts,rect_id);
      //if(to_visit[rect_id]) rect[rect_id].guarded = true;
      for(int j=1;j<=n_verts;j++){
        x = input.nextInt();
        y = input.nextInt();

        aux=check_hash(Vertmap,x,y);
        if(aux!=null){
          rect[rect_id].insert_vert(aux.id);
          if(!to_visit[rect_id]) aux.insert_rect(rect_id);
        }
        else{
        aux = new Vert(x,y,id);
        Vertmap.put(id,aux);
        rect[rect_id].insert_vert(id);
        if(!to_visit[rect_id]) aux.insert_rect(rect_id);
        id++;
        }
      }
    }
  }

  public static void answer(HashMap<Integer,Vert> Vertmap, int []rects_in_vert, Rect [] rect,int n_ret, boolean []to_visit){
    Heapmax pq = new Heapmax(rects_in_vert,Vertmap.size());
    int s,size,x,y;
    x=y=0;

    //guarded is number of rectangles guarded atm
    while(!pq.isEmpty()){
      //select vertex with most number of rectangles
      s = pq.extractMax();
      //System.out.println("s:"+s);
      Vert vert = Vertmap.get(s);
      //save values for answer
      x=vert.get_x();
      y=vert.get_y();
      //remove vertex
      Vertmap.remove(s);

      size = vert.n_rects;

      //update each rectangle(in that vertex) to be guarded
      //decrease n_rects in verts with those rectangles
      for(int i = 1;i<=size;i++){

        //rectangle is not guarded
        if(!to_visit[vert.rect_ids[i]]) to_visit[vert.rect_ids[i]] = true;
        //update each vertex that is in that rectangle
        for(Vert vert1 : Vertmap.values()){
          if(vert1.in_rect(vert.rect_ids[i])){
            rects_in_vert[vert1.id]-=1;
            pq.decreaseKey(vert1.id,rects_in_vert[vert1.id]);
          }
        }
      }
      //condition break(if all rectangles are guarded);
      if(all_guarded(to_visit,n_ret)) break;
      System.out.print("("+x+","+y+"), ");
    }
    System.out.println("("+x+","+y+")");
  }


  public static void main(String [] args){
    Scanner input = new Scanner(System.in);
    int n_instancias = input.nextInt();

    int []rects_in_vert;
    for(int i=1;i<=n_instancias;i++){
      int n_ret = input.nextInt();
      int n_want_visit = input.nextInt();
      boolean []w_visit = new boolean[n_ret+1];
      Arrays.fill(w_visit,true);
      for(int k=1;k<=n_want_visit;k++) w_visit[input.nextInt()] = false;

      HashMap <Integer,Vert> Vertmap = new HashMap<Integer,Vert>();
      //save rectangles
      Rect [] rect = new Rect[n_ret+1];
      process_rectangles(input,rect,Vertmap,n_ret,w_visit);
      rects_in_vert= new int[Vertmap.size()+1];
      for(Vert vert : Vertmap.values()){
        //number of rectangles in that vertex
        rects_in_vert[vert.id] = vert.n_rects;
      }
      System.out.println(".Answer "+i);
      System.out.print("Guards in: ");
      answer(Vertmap,rects_in_vert,rect,n_ret,w_visit);
      Vertmap.clear();
    }
  }
}
