package traveling_salesman;

import java.util.ArrayList;

public class Point {
	public double x;
	public double y;
	public boolean visited;
	public ArrayList<Point> p;
	public ArrayList<Point>parents;
	
	public Point(double x, double y){
		this.x = x;
		this.y = y;
		p = new ArrayList<>();
		parents = new ArrayList<>();
	}
	
	public String toString(){
		return "(" + x +"," + y +")";
	}

}
