import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.PrintWriter;
public class graphColoring{// Graph coloring main. The ajacency list is built here, then processed, and outputted to the file.

	//TO RUN THIS PROGRAM, GIVE IT THE FILE NAME AS A COMMAND LINE INPUT. TO RUN IT ON EITHER OF THE LARGE GRAPHS, USE THE -Xss12m flag to increase recursive stack size.

	static String file = "";// I used a static string for file name so I could access it from my output method.
	public static void dump(Vtex[] grap, boolean color){//output to file. takes in the ajacency list, and a boolean of wether or not the graph was colorable.
			String name = file + "output.txt";// creates output filename
			try{	PrintWriter out = new PrintWriter(name, "UTF-8");
				out.println("Was this two colorable? true or false: " + color);// shows wether this was two colorable
				if(color){// if it was two colorable
					for(int i = 0; i < grap.length; i++)// for each entry in the ajacency list
						out.println(i + 1+ ": " +grap[i].toString() + "\n");// prints out node number, color, and neighbor list to file
				}
				else{// if the graph was not two colorable

					for(int i = 0; i < grap.length; i++){//for each vertex in the ajacency list
					if(grap[i] != null && grap[i].isOddLoop())// if it is a member of the loop that caused the program to break
							out.println(i + 1+ ": " +grap[i].toString() + "\n");// print it here
					}
				}

				out.close();// close output
			}
			catch(Exception e){
				e.printStackTrace();
			}
	}

	public static void main(String[] args){
		boolean isColorable = false;// keeps track of wether or not the graph is two colorable
		String line =""; // holder for the line read in
		int v1 = 0, v2 = 0;//holder for the numbers in the ripped string
		String rip[];// holder for the ripped string itself
		boolean isFirst = true;// waits for the first line
		if(args.length > 0)// if im given a file in args
			file = args[0];// takes file from args
		else{
			System.out.println("NO FILE GIVEN, DEFAULT TO SmallGraph");
			file = "largegraph2";
		}
		try{ BufferedReader diction = new BufferedReader(new FileReader(file));
				line = diction.readLine();// get the first line
			int size = Integer.parseInt(line);
				Vtex[] graph = new Vtex[size];// set size of ajacency list to input size
			while(diction.ready()){// while there are lines to read
					line = diction.readLine();// read the line
					rip = line.split("\\s+");// split the line at the space
					v1 = Integer.parseInt(rip[0]);// put the first number here
					v2 = Integer.parseInt(rip[1]);// second number here
					if(graph[v1 -1] == null)
						graph[v1 -1] =new Vtex();// if I havent accessed this entry yet, create it
					if(graph[v2 -1] == null)
						graph[v2 -1] =new Vtex();
					graph[v1 -1].addNeighbor(v2);// add neighbors from vertex one to two, and two to one
					graph[v2 -1].addNeighbor(v1);

			}// When this is done, the ajacency list is built

			for(int j = 0; j < graph.length; j++){// for each vertex in the graph
				if(graph[j] == null){// if the vertex was never created (and therefor has no edges)
					graph[j] = new Vtex();// create it
				}

				if(!graph[j].isColored()){// if the vertex is not colored
					isColorable = graph[j].setColor("RED");//set the color to red
						if(!isColorable){//should never be triggered, left in case of the impossible.
							System.out.println("This should never happen");
							break;
						}
					isColorable = graph[j].setColor(graph);// color all the verticies reachable from this one, BFS Style
						if(!isColorable){// if the graph is not two colorable, the above method returns false and then triggers this statement
							System.out.println("Graph not 2 Colorable");
							break;// the program no longer attempts to color the graph after an odd loop is discovered
						}
				}
			}

			dump(graph, isColorable);// output graph to file
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
}