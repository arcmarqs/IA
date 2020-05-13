import java.util.*;
import java.lang.*;

class BFS{

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

public static Node bfs(Node start,HashMap<Integer,Vert> hash){
    if(start.all_visited()==true) return start;
    Queue<Node> q = new LinkedList<>();
    q.add(start);
    Node n;

    while(!(q.isEmpty())){
       
         n = q.remove();

 
        if(n.all_visited()) return n;
                   
            for(int j=1;j<=hash.size();j++){
            if(n.vertid < hash.get(j).id && n.visited(hash.get(j))!=true ){
                q.add(child(n,hash.get(j)));
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
       Node solution = bfs(start,hash);

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