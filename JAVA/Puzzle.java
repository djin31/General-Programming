// A program to find solution to the 8 block puzzle with differential cost of moving each tile
// The hole in the puzzle is represented by G and all other tiles are given by numbers from 1 to 8
// Usage:
// #javac Puzzle.java
// #java Puzzle
// <initial state> <final state>
// <space separated cost of moving each tile>
//eg. #java Puzzle
//	   12345678G 123G45678
//	   2 3 4 5 6 7 8 1

import java.util.*;
import java.io.*;

class entries{
	String state;
	int position_in_queue;
	int G_index;
	int cost;
	int number_of_steps;
	Vector<entries> neighbour;
	Vector<Integer> edgecost;
	entries prev;
}

class priorityqueue{
	entries[] q;
	int size;
	priorityqueue(){
		q=new entries[362881];
		size=0;
	}

	void add(entries e){
		++size;
		e.position_in_queue=size;
		q[size]=e;
	}

	void insert(entries e){
		int t=size+1;
		while (t>1 && (e.cost<q[t/2].cost||(e.cost==q[t/2].cost && e.number_of_steps<q[t/2].number_of_steps))){
			q[t]=q[t/2];
			q[t].position_in_queue=t;
			t/=2;
		}
		q[t]=e;
		q[t].position_in_queue=t;
		size++;
	}

	entries deletemin(){
		entries retVal= q[1];
		int t=1;
		while (2*t<=size){
			if (2*t+1<=size){
				if (q[2*t].cost<q[2*t+1].cost || (q[2*t].cost==q[2*t+1].cost && q[2*t].number_of_steps<q[2*t+1].number_of_steps)){
					q[t]=q[2*t];
					q[t].position_in_queue=t;
					t*=2;
				}
				else{
					q[t]=q[2*t+1];
					q[t].position_in_queue=t;
					t=2*t+1;
				}
			}
			else{
				q[t]=q[2*t];
				q[t].position_in_queue=t;
				t*=2;
			}
		}
		q[t]=q[size];
		q[t].position_in_queue=t;
		size--;
		return retVal;
	}

	void update(entries e){
		int t=e.position_in_queue;
		while (t>1 && (e.cost<q[t/2].cost||(e.cost==q[t/2].cost && e.number_of_steps<q[t/2].number_of_steps))){
			q[t]=q[t/2];
			q[t].position_in_queue=t;
			t/=2;
		}
		q[t]=e;
		e.position_in_queue=t;
	}
}

public class Puzzle{
	static HashMap<String,entries> graph = new HashMap<String,entries>(362880,(float)0.5);
	static priorityqueue find_min_node = new priorityqueue();
	static ArrayList<String> vertex_set = new ArrayList<String>(362880);
	static int[] costfunction = new int[9];
	static StringBuilder printer = new StringBuilder();

	static void permutations(String prefix, String str) {
    	int n = str.length();
    	if (n == 0) {
    		vertex_set.add(prefix);
    	}
    	else {
        	for (int i = 0; i < n; i++)
            	permutations(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
        }
	}

    static String swapindex(String s,int a, int b){
    	String retVal = s.substring(0,a)+s.substring(b,b+1)+s.substring(a+1,b)+s.substring(a,a+1)+s.substring(b+1,9);
    	return retVal;
    }

    static void makeGraph(){
    	//long t =System.currentTimeMillis();
    	permutations("","12345678G");
    	int G_index;
    	entries e,neighbour;
    	for (String i: vertex_set){
    		e= new entries();
    		e.state = i;
    		graph.put(i,e);
    	}
    	for (String i:vertex_set){
    		G_index=i.indexOf("G");
    		e = graph.get(i);
    		e.G_index=G_index;
    		e.neighbour= new Vector<entries>(4);
    		e.edgecost = new Vector<Integer>(4);
    		
    		if (G_index/3!=0){
    			e.neighbour.add(graph.get(swapindex(i,G_index-3,G_index)));
    			e.edgecost.add(Integer.valueOf(i.substring(G_index-3,G_index-2)));
    		}
    		if (G_index/3!=2){
    			e.neighbour.add(graph.get(swapindex(i,G_index,G_index+3)));
    			e.edgecost.add(Integer.valueOf(i.substring(G_index+3,G_index+4)));
    		}
    		if (G_index%3!=0){
    			e.neighbour.add(graph.get(swapindex(i,G_index-1,G_index)));
    			e.edgecost.add(Integer.valueOf(i.substring(G_index-1,G_index)));
    		}
    		if (G_index%3!=2){
    			e.neighbour.add(graph.get(swapindex(i,G_index,G_index+1)));
    			e.edgecost.add(Integer.valueOf(i.substring(G_index+1,G_index+2)));
    		}

    		graph.put(i,e);
    	}
    	//for(String i:graph.keySet())
   		//	System.out.println(i+" "+graph.get(i).neighbour.toString());  	
    	//System.out.println(System.currentTimeMillis()-t);
    }

    static boolean checkPossibility(String source, String target){
    	int source_count=0;
    	int target_count=0;
    	char[] source_temp = source.toCharArray();
    	char[] target_temp = target.toCharArray();
    	for (int i=0;i<8;i++)
    		for (int j=i+1;j<9;j++)
    			if (source_temp[i]!='G' && source_temp[j]!='G' && source_temp[i]>source_temp[j])
    				source_count++;
    	for (int i=0;i<8;i++)
    		for (int j=i+1;j<9;j++)
    			if (target_temp[i]!='G' && target_temp[j]!='G' && target_temp[i]>target_temp[j])
    				target_count++;
    	return (source_count%2==target_count%2);
    	
    }
	
    static void backtrack(entries e){
    	Vector<String> steps = new Vector<String>();
    	entries curr_node = e;
    	entries next_node;
    	while (curr_node.prev!=null){
    		next_node = curr_node.prev;
    		if (next_node.G_index==curr_node.G_index-1)
    			steps.add(curr_node.state.substring(curr_node.G_index-1,curr_node.G_index)+"L");
    		else if (next_node.G_index==curr_node.G_index+1)
    			steps.add(curr_node.state.substring(curr_node.G_index+1,curr_node.G_index+2)+"R");
    		else if (next_node.G_index==curr_node.G_index-3)
    			steps.add(curr_node.state.substring(curr_node.G_index-3,curr_node.G_index-2)+"U");
    		else if (next_node.G_index==curr_node.G_index+3)
    			steps.add(curr_node.state.substring(curr_node.G_index+3,curr_node.G_index+4)+"D");
    		curr_node=curr_node.prev;
    	}
    	for (int i=steps.size()-1;i>=0;i--)
    		printer.append(steps.get(i)+" ");
    	printer.append("\n");
    }

	static void findOptimalPath(String source, String target){
		//long t = System.currentTimeMillis();
		if (!checkPossibility(source,target)){
			printer.append("-1 -1\n\n");
			return;

		}
		if (source.equals(target)){
			printer.append("0 0\n\n");
			return;
		}

		find_min_node = new priorityqueue();

		entries curr_node,new_node;
		int temp_cost,temp_steps;
		for (String i : vertex_set){
			curr_node=graph.get(i);
			curr_node.cost=2147483647;
			curr_node.number_of_steps=2147483647;
			find_min_node.add(curr_node);
		}
		curr_node=graph.get(source);
		curr_node.cost=0;
		curr_node.number_of_steps=0;
		curr_node.prev=null;
		find_min_node.update(curr_node);

		while (find_min_node.q[1].cost<2147483647){
			curr_node = find_min_node.deletemin ();
			curr_node.position_in_queue=-1;
			if (curr_node.state.equals(target))
				break;
			for (int i=0;i<curr_node.neighbour.size();i++){
				new_node=curr_node.neighbour.get(i);

				if (new_node.position_in_queue!=-1){
					temp_cost=curr_node.cost + costfunction[curr_node.edgecost.get(i)];
					temp_steps=curr_node.number_of_steps + 1;
					if (temp_cost<new_node.cost || (temp_cost==new_node.cost && temp_steps<new_node.number_of_steps)){
						new_node.cost=temp_cost;
						new_node.number_of_steps=temp_steps;
						new_node.prev=curr_node;
						find_min_node.update(new_node);
					}
				}
			}
		}
		if (curr_node.state.equals(target)){
			printer.append(curr_node.number_of_steps+" "+curr_node.cost+"\n");
			backtrack(curr_node);
		}
		else{
			printer.append("-1 -1\n\n");
		}
		//System.out.println(System.currentTimeMillis()-t);	
	}

	public static void main(String[] args) {
		
		source=s.next();
		target=s.next();
				
		for (int i=1;i<9;i++){
			costfunction[i]=s.nextInt();
		}
		findOptimalPath(source,target);
		System.out.println(printer);
		
	}	
}
