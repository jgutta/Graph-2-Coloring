public class node{// simple node class, holds an int that refers to the vertex number, and a pointer to the next node.
	private node next;
	private int sto;

	public node(){
		next = null;
		sto = 0;
	}
	public node(int s, node n){
		next = n;
		sto = s;
	}
	public node(int s){
			next = null;
			sto = s;
	}
	public int getV(){
		return sto;
	}
	public node getNext(){
		return next;
	}
	public void setV(int i){
		sto = i;
	}
	public void setNext(node n){
		next = n;
	}
	public boolean hasNext(){
		return next != null;
	}
}