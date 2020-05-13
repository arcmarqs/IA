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
