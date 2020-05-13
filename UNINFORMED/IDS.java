import java.util.*;
import java.lang.*;


class Node{
    
    int visited[];
    Node parent=null;
    int cost;
    boolean guard;
    int nrets;
    LinkedList<Vert> guardas = new LinkedList<>();
    int vertid=0;

    Node(int nrects,int visitados[]){
        nrets = nrects;
        visited = new int[nrects+1];
        for(int i=1;i<=nrects;i++){
            visited[i]=visitados[i];
        }
       // guardas = guardedverts;
    }

    public void update(Vert v){
       // if(v.guard==true){
        for(int i : v.rectid){
            visited[i]++;
       // }
        }
    }

    public boolean visited(Vert v){
        for(int i : v.rectid){
            if(visited[i] == 0) return false;
        }
        return true;
    }
    public boolean all_visited(){
        for(int i =1;i<=nrets;i++){
            if(visited[i]==0) return false;
        }
    
        return true;
    }

       public void print_guards(){
        while(!(guardas.isEmpty())){
          Vert temp = guardas.remove();
         System.out.println("(" + temp.x + "," + temp.y + ")");

    }}

    public void add_guard(Vert v){
        if(!(guardas.contains(v))){
        //v.guard = true;
        guardas.add(v);
        }
    }

 public void print_all_visited(){
  for(int i=1;i<=nrets;i++){
    System.out.print(visited[i] + " ");
  }
  System.out.println();
}
 public LinkedList<Vert> list_cpy(){
     LinkedList<Vert> clone = new LinkedList<>();
  for(int i = 0; i < guardas.size(); i++){
      clone.add(guardas.get(i));
  }
    return clone;
 }

 public int[] visited_cpy(){
     int copied[] = new int[nrets+1];
     for(int i=1;i<=nrets;i++){
        copied[i]=visited[i];

     }

     return copied;
 }


}



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



class IDS{

public static Node child(Node n,Vert vert){
  
    Node child = new Node(n.nrets,n.visited_cpy());
    child.cost = 1+n.cost;
    child.guardas = n.list_cpy();
    child.parent = n;

    child.add_guard(vert);
    child.update(vert);
    vert.guard = true;
    child.vertid = vert.id;
    
    return child;

    
}
public static Node ids_start(Node start,HashMap<Integer,Vert> hash,int recs){
    for(int limit = recs/3;limit <= hash.size();limit++){
            Node n = dfs(start,hash,limit);
            if(n!=null){
                return n;
            }
    }
return null;
}
public static Node dfs(Node start,HashMap<Integer,Vert> hash,int limit){
    if(start.all_visited()==true) return start;
    Stack<Node> q = new Stack<>();
    q.push(start);
    Node n;
 
    while(!(q.isEmpty())){
       
         n = q.pop();

        if(n.all_visited()) return n;
        if(n.cost<limit){
            for(int j=1;j<=hash.size();j++){
            if(n.vertid < hash.get(j).id && (n.visited(hash.get(j))!=true)){
                q.push(child(n,hash.get(j)));
             }
            }
        }

    }

    return null;
}


   public static Vert check_hash(HashMap<Integer,Vert> Vertmap,int x,int y){
    for(Vert vert : Vertmap.values()){
      if(vert.x == x && vert.y == y) return vert;
    }
    return null;
  }


public static void main(String[] args){
    Scanner in = new Scanner(System.in);
    int instancias = in.nextInt();
    for(int i = 0;i < instancias;i++){
        HashMap<Integer,Vert> hash = new HashMap<>();
        int id=1;
        int nrects = in.nextInt();
        int tovisit = in.nextInt();
        int rec[]= new int[nrects+1];
        Arrays.fill(rec,1);
        for(int k=1;k<=tovisit;k++) rec[in.nextInt()] = 0;
        for(int j = 1;j <= nrects;j++){
            int rectid=in.nextInt();
            int nverts = in.nextInt();
            for(int k = 1;k <= nverts ;k++){
                int x = in.nextInt();
                int y = in.nextInt();
                Vert newvert = check_hash(hash,x,y);
                if(newvert != null){
                    newvert.add_rect(rectid);
                }

                else{
                    newvert= new Vert(x,y,id);
                    hash.put(id,newvert);
                    newvert.add_rect(rectid);
                    id++;
                }
                
            }

        }
       Node start = new Node(nrects,rec);
       Node solution = ids_start(start,hash,tovisit);

       if(solution == null){
           System.out.println("Solução não encontrada");
       }

       else{
           solution.print_guards();
           System.out.println(solution.cost);
       }
       hash.clear();
    }
    

}

}