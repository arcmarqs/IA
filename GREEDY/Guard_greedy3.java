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

class Heapmin {
    private static int posinvalida = 0;
    int sizeMax,size;

    Qnode[] a;
    int[] pos_a;

    Heapmin(int vec[], int n) {
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

    int extractMin() {
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

	while (i > 1 && compare(i, parent(i)) < 0) {
	    swap(i, parent(i));
	    i = parent(i);
	}
    }


    void insert(int vertv, int key)
    {
	if (sizeMax == size)
	    new Error("Heap is full\n");

	size++;
	a[size].vert = vertv;
	pos_a[vertv] = size;   // supondo 1 <= vertv <= n
	decreaseKey(vertv,key);   // diminui a chave e corrige posicao se necessario
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
	int l, r, smallest;

	l = left(i);
	if (l > size) l = i;

	r = right(i);
	if (r > size) r = i;

	smallest = i;
	if (compare(l,smallest) < 0)
	    smallest = l;
	if (compare(r,smallest) < 0)
	    smallest = r;

	if (i != smallest) {
	    swap(i, smallest);
	    heapify(smallest);
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
  int n_rects_verts = 0;

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

public class Guard_greedy3{
  public static void remove(int x[],int size, int id){
    for(int i=1;i<=size;i++){
      if(x[i] == id){
        x[i] = x[size];
        break;
      }
    }
  }
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
  public static void process_rectangles(Scanner input,Rect[] rect,HashMap<Integer,Vert> Vertmap,int n_ret,boolean to_visit[], int verts_in_rect[]){
    int n_verts,x,y,id,rect_id;
    id = 1;
    Vert aux;
    for(int i=1;i<=n_ret;i++){
      rect_id = input.nextInt();
      n_verts = input.nextInt();
      verts_in_rect[rect_id] = n_verts;
      rect[rect_id] = new Rect(n_verts,rect_id);
      //if(to_visit[rect_id]) rect[rect_id].guarded = false;
      for(int j=1;j<=n_verts;j++){
        x = input.nextInt();
        y = input.nextInt();

        aux=check_hash(Vertmap,x,y);
        //already existing vertex
        if(aux!=null){
          rect[rect_id].insert_vert(aux.id);
          if(!to_visit[rect_id]) aux.insert_rect(rect_id);
        }
        //new vertex
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
  public static int get_total(Vert vert, HashMap<Integer,Vert> Vertmap, Rect rect[]){
    int n=0;
    for(int i = 1;i<=vert.n_rects;i++){
      Rect aux = rect[vert.rect_ids[i]];
      n+=aux.n_verts;
    }
    return n;
  }

  public static void answer(HashMap<Integer,Vert> Vertmap, int []verts_in_rect, Rect [] rect,int n_ret, boolean []to_visit){
    Heapmin pq = new Heapmin(verts_in_rect,n_ret);
    int s,size,x,y;
    x=y=0;

    //guarded is number of rectangles guarded atm
    while(!pq.isEmpty()){

      //select rectangle with less number of vertices
      while(true){
        s = pq.extractMin();
        if(!to_visit[s]) break;
      }

      Rect rect1 = rect[s];

      //number of vertices in rectangle
      size = rect1.n_verts;

      //select vertex with most rectangles seen
      Vert aux = new Vert(-1,-1,-1);
      int max=0;
      for(int i = 1;i<=size;i++){
        if(Vertmap.get(rect1.vert_ids[i]) == null) continue;
        else if(Vertmap.get(rect1.vert_ids[i]).n_rects > max) aux = Vertmap.get(rect1.vert_ids[i]);

        //select vertex with rectangles with more incident vertices
        else if(Vertmap.get(rect1.vert_ids[i]).n_rects == max){
          Vert aux2 =  Vertmap.get(rect1.vert_ids[i]);
          int total_verts1 = get_total(aux,Vertmap,rect);
          int total_verts2 = get_total(aux2,Vertmap,rect);
          if(total_verts1<total_verts2) aux = aux2;
        }
        max = aux.n_rects;
      }
      if(aux.get_x() == -1 && aux.get_y() == -1) continue;
      //rectangle guarded
      to_visit[s] = true;

      //save values for answer
      x=aux.get_x();
      y=aux.get_y();

      //update each vertex that has that rectangle(remove)
      for(Vert vert2 : Vertmap.values()){
        if(vert2.in_rect(rect1.id)){
          remove(vert2.rect_ids,vert2.n_rects,rect1.id);
          vert2.n_rects--;
         }
      }

      //update each rectangle with that vertex
      for(int k = 1;k<=aux.n_rects;k++){
        if(!to_visit[aux.rect_ids[k]]){
          verts_in_rect[aux.rect_ids[k]]-=1;

          //System.out.println("("+x+","+y+")");
          //System.out.print("reck id: "+aux.rect_ids[k]+"- ");
          //rectangle guarded
          to_visit[aux.rect_ids[k]] = true;
          //System.out.println("verts:"+verts_in_rect[rect[k].id]);
          pq.increaseKey(aux.rect_ids[k],verts_in_rect[aux.rect_ids[k]]);
          //System.out.println("end");
        }
      }
      Vertmap.remove(aux.id);

      //condition break(if all rectangles are guarded);
      if(all_guarded(to_visit,n_ret)) break;
      System.out.print("("+x+","+y+"), ");
    }
    System.out.println("("+x+","+y+")");
  }


  public static void main(String [] args){
    Scanner input = new Scanner(System.in);
    int n_instancias = input.nextInt();

    int []verts_in_rect;
    for(int i=1;i<=n_instancias;i++){
      int n_ret = input.nextInt();
      int n_want_visit = input.nextInt();
      boolean []to_visit = new boolean[n_ret+1];
      Arrays.fill(to_visit,true);
      for(int k=1;k<=n_want_visit;k++) to_visit[input.nextInt()] = false;

      HashMap <Integer,Vert> Vertmap = new HashMap<Integer,Vert>();
      //save rectangles
      Rect [] rect = new Rect[n_ret+1];
      verts_in_rect = new int[n_ret+1];
      process_rectangles(input,rect,Vertmap,n_ret,to_visit,verts_in_rect);
      System.out.println(".Answer "+i);
      System.out.print("Guards: ");
      answer(Vertmap,verts_in_rect,rect,n_ret,to_visit);
      Vertmap.clear();
    }
  }
}
