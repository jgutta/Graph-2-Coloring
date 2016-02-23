public class Vtex{// Vertex class. Holds a linked list of neighbors by number, the color of the node, and some status booleans. The main graph traversal method is here.
	private node head = null, tail = null; //pointers to the head and tail of the linked list of neighbors
	private int neighCount = 0; // number of items in neighbor list
	private String color = "NaN"; //node color. Default is NaN, other states are either RED or BLUE
	private boolean isColored = false, visited = false, oddLoop = false; //Booleans to check if it has been colored, visited during bfs, or marked as part of an odd loop.

	public Vtex(){// constructer defaults
		head = null;
		tail = null;
		neighCount = 0;
		color = "NaN";
	}
	public boolean isOddLoop(){
		return oddLoop;
	}
	public void oddLoop(){
		oddLoop = true;
	}
	public void wasVisited(){
		visited = true;
	}
	public boolean visited(){
		return visited;
	}
	public String oppo(){// returns the opposite color from whatever the set color is
		if(color.equals("RED"))
			return "BLUE";
		else
			return "RED";
	}
	public boolean setColor(Vtex[] grap){// BFS method. When run, the vertex the method is
		node temp = head; //called on becomes the source of a BFS style traversal that colors all nodes connected by any degree. This requires the node to already be colored.
		boolean boo = false;// this is a holder for what the set color methods return, this would be set to false if a coloring event failed
		visited = true;// any node this is called on has visited set to true
		if(neighCount == 0)// if a node is not connected to anything, the coloring is valid.
			return true;
		while(temp.hasNext()){//while there are more items in the list of neighbors
			if(!grap[temp.getV() -1].isColored()){//if a neighbor is not colored
				boo = grap[temp.getV() -1].setColor(oppo());//color it the opposite of the current node
				if(!boo){// this should never be triggered, but was left here as a precaution. If the coloring fails, the graph is invalid, and it involved nodes are marked
					oddLoop();// as part of the odd loop
					return false;
				}
			}
			else if(!grap[temp.getV() -1].color.equals(oppo())){ //If a neighbor's color is not the oppositite of the parent
				grap[temp.getV() -1].oddLoop();// it is part of the odd loop
				oddLoop();//and the parent is part of the odd loop
				return false;
			}
		temp = temp.getNext();// check the next neighbor
		}
		if(!grap[temp.getV() -1].isColored()){ //the code from here to the place where temp is set to head again is all a repeat of the code in the loop
						boo = grap[temp.getV() -1].setColor(oppo());// because the tail of the list never makes it through the check otherwise.
						if(!boo){
							oddLoop();
							return false;
						}
					}
					else if(!grap[temp.getV() -1].color.equals(oppo())){
						grap[temp.getV() -1].oddLoop();
						oddLoop();
						return false;
					}
		temp = head; // Now that all the neighbors of the current node are colored correctly, we loop through unvisited  neighbors and do the same.
		while(temp.hasNext()){
			if(!grap[temp.getV() -1].visited()){ //if the neighbor hasnt been visited
				boo = grap[temp.getV() -1].setColor(grap);// color their neighbors
				if(!boo){//if it was not successful, meaning the recursive call returned false
					oddLoop();// this is part of the odd loop
					return false;
				}
			}
			temp = temp.getNext();// check the next neighbor
		}
		if(!grap[temp.getV() -1].visited()){// same as the above, but to check the tail
			boo = grap[temp.getV() -1].setColor(grap);
				if(!boo){
					oddLoop();
					return false;
				}
		}
		return true;//If it made it all the way here without returning false, it is two colorable.

	}
	public boolean setColor(String s){ //sets node color, returbs false on a conflict
		if(color.equals("NaN")){// if it has no color, color it
			color = s;
			isColored = true;
			return true;
			}
		else if(!color.equals(s)){// if there is an attempt to assign an opposite color than this node already has, return false and keep original color
			oddLoop = true;
			return false;
		}
		else
			return true;
	}
	public boolean isColored(){
		return isColored;
	}
	public int count(){
		return neighCount;
	}
	public String color(){
		return color;
	}
	public void addNeighbor(int n){//method to add neighbors to linked list
		node nud = new node(n);// make new node
		if(neighCount == 0){// if there is nothing already here, this node is the head
			head = nud;
			neighCount++;
		}
		else if(neighCount == 1){// if there is one other node here, this node is the tail
			head.setNext(nud);
			tail = nud;
			neighCount++;
		}
		else{// if there are more, this is set as the new tail for O(1) addition time
			tail.setNext(nud);
			tail = nud;
			neighCount++;
		}
	}
	public node getHead(){
		return head;
	}
	public String toString(){// prints out color, and the list of edges
	if(neighCount == 0){//if it has no neighbors
		String s = color +": No Edges";
		return s;
	}
	String s = color +": " + head.getV();
	node temp = head;
	int i = 1;
	while(i < neighCount){
		temp = temp.getNext();
		s = s + ", " + temp.getV();
		i++;
	}
	return s;
}
}