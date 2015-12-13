package traveling_salesman;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;


public class Map extends JPanel{
	public ArrayList<Point> route;
	
	Map(List<Point> route){
		this.route = (ArrayList<Point>) route;
	}
	
	Map(){
		this.route = new ArrayList<>();
	}
	
	//update the map
	
	public void setRoute(List<Point> route){
		this.route.clear();
		this.route.addAll(route);
	}
	
	public void paint(Graphics g){
		super.paint(g);
		update(g);
	}
	
	public void update(Graphics g){
		int width = getBounds().width;
		int height = getBounds().height;
		
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);
		
		if(route == null || route.size() == 0) return;
		
		int prevx = (int)route.get(0).x;
		int prevy = (int)route.get(0).y;
		for( int i=0; i<route.size(); i++){
			if(i == route.size()-1){
				g.setColor(Color.red);
			}else{
				g.setColor(Color.green);
			}
			int xpos =(int) route.get(i).x *9 +100;
			int ypos = (int) route.get(i).y *3 +200;
			g.fillOval(xpos  -5 ,ypos -5, 10 , 10);
			if(i !=0 ){
				g.setColor(Color.white);
				g.drawLine(prevx , prevy, xpos , ypos );
			}
			prevx = xpos;
			prevy = ypos;
		}
		
	}

}
