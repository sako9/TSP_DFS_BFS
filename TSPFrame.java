package traveling_salesman;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This program demonstrates how to work with JFrame in Swing.
 * @author www.codejava.net
 *
 */
public class TSPFrame extends JFrame implements ActionListener {
	private File tspFile = null;
	private Map map = null;
	private JButton dfs = null;
	private JButton bfs = null;
	private JTextArea status;
	private JComboBox<Point> cities;
	private Point search = null;
	TSP t = new TSP();
	public double minPath = 0.0;
	public ArrayList<Point> minPathList;

	
	public void actionPerformed(ActionEvent e) {
	    System.out.println("Selected: " + e.getActionCommand());
	    switch(e.getActionCommand()){
	    	case "Exit":
	    		int reply = JOptionPane.showConfirmDialog(TSPFrame.this,
						"Are you sure you want to quit?",
						"Exit",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (reply == JOptionPane.YES_OPTION) {
					dispose();
				} else {
					return;
				}
	    		break;
	    	case "Open TSP":
	    		JFileChooser fileChooser = new JFileChooser();
	    		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
	    		FileFilter filter = new FileNameExtensionFilter("TSP File","tsp");
	    		fileChooser.setFileFilter(filter);
	    		int result = fileChooser.showOpenDialog(this);
	    		if(result == JFileChooser.APPROVE_OPTION){
	    			File fileSelected = fileChooser.getSelectedFile();
	    			tspFile = fileSelected;
	    			status.setText("Press Start");
	    			status.update(getGraphics());
	    			t.readPointsFromFile(tspFile);
	    			t.createTree();
	    			cities.removeAll();
	    			for(int i = 0; i< t.points.size(); i++){
	    				cities.addItem(t.points.get(i));
	    			}
	    		}
	    		break;
	    	case "DFS":
	    		search = (Point)cities.getSelectedItem();
	    		if(t.points.size() > 0){
					minPathList = new ArrayList<>();
					minPath = 0.0;
					status.setText("Processing...");
					this.update(getGraphics());
	
					findShortestPathList(minPathList, t.points, null);
					map.setRoute(minPathList);
					map.update(map.getGraphics());
					String path = "";
					for (int i = 0; i < minPathList.size(); i++) {
						path += "(" + minPathList.get(i).x + ","
								+ minPathList.get(i).y + ")";
					}
					status.setText("The min path is:" + path
							+ "with a distance of " + minPath);
					status.validate();
					this.update(getGraphics());
	    		}
    			break;
    			
	    	case "BFS":
	    		search = (Point)cities.getSelectedItem();
	    		if(t.points.size() > 0){
					minPathList = new ArrayList<>();
					minPath = 0.0;
					status.setText("Processing...");
					this.update(getGraphics());
	
					findShortestPathListBFS(t.points.get(0));
					map.setRoute(minPathList);
					map.update(map.getGraphics());
					String path = "";
					for (int i = 0; i < minPathList.size(); i++) {
						path += "(" + minPathList.get(i).x + ","
								+ minPathList.get(i).y + ")";
					}
					status.setText("The min path is:" + path
							+ "with a distance of " + minPath);
					status.validate();
					this.update(getGraphics());
	    		}
    			break;
	    		
	    	default :
	    		System.out.println("Invalid option");
	    		break;
	    }

	  }
	
	
	public void findShortestPathListBFS(Point start){
		List<Point> points = new ArrayList<Point>(); 
		Point current = null;
		points.add(start);
		if(start.equals(search)){
			minPath = 0;
			minPathList.clear();
			minPathList.add(start);
			map.setRoute(minPathList);
			map.update(map.getGraphics());
		}
		while(!points.isEmpty()){
			current = points.remove(0);
			for(Point p : current.p){
				if(!points.contains(p)){
					p.parents.clear();
					p.parents.addAll(current.parents);
					p.parents.add(current);
					if(p.equals(search)){
						List<Point> route = new ArrayList<Point>();
						route.addAll(p.parents);
						route.add(p);
						double dist = totalDistance(route);
						if(minPath == 0 || dist < minPath){
							map.setRoute(route);
							map.update(map.getGraphics());
							minPath = dist;
							minPathList.clear();
							minPathList.addAll(route);
						}
					}
					points.add(p);
				}
			}
		}
	}
	
	public void findShortestPathList(List<Point> route,List<Point> notInroute, Point q){
		List<Point> p = new ArrayList<Point>(); 
		List<Point> r = new ArrayList<Point>();
		r.addAll(route); 
		p.addAll(notInroute); 
		//we have to make copies or else we'll lose reference to points
		
		if(q != null){// only null when we start out, did this to create a starting position
			p.remove(q);
			r.add(q);
		}
		map.setRoute(r);
		map.update(map.getGraphics());
		if(	q != null && q.equals(search)){ // if all points have been visited,
			//r.add(r.get(0)); // the problem requires us to come back to where we started
			//map.setRoute(r);
			//map.update(map.getGraphics());
			double dist = totalDistance(r);
			if(minPath == 0 || dist < minPath){
				minPath = dist;
				minPathList.clear();
				minPathList.addAll(r);
			}
		}else{
			if(q == null){
				for(int i = 0; i < p.get(0).p.size(); i++){
					findShortestPathList(r,p.get(0).p,p.get(0));
				}
			}else{
				for(int i = 0; i < p.size(); i++){
					findShortestPathList(r,p.get(i).p,p.get(i)); // recursively call function on each Point in point of the list
				}
			}
		}
	}
	
	public double distance(Point a, Point b){
		return Math.sqrt(Math.pow((b.x - a.x),2) + Math.pow((b.y - a.y),2));
	}
	
	public double totalDistance(List<Point> p){
		double dist = 0.0;
		for(int i = 0 ; i < p.size() -1; i++){
			dist += distance(p.get(i),p.get(i+1)); 
		}
		return dist;
	}
	

	public TSPFrame() {
		super("TSP GUI");
		map = new Map();
		minPathList = new ArrayList<>();
		status = new JTextArea("Select a tsp file");
		
		status.setLineWrap(true);
		status.setEditable(false);
		status.setWrapStyleWord(true);
		setLayout(new BorderLayout());
		add(map,"Center");
		add(status,"South");
		
		JPanel panel = new JPanel();
		cities = new JComboBox<Point>();
		dfs = new JButton("DFS");
		bfs = new JButton("BFS");
		dfs.addActionListener(this);
		bfs.addActionListener(this);
		panel.add(cities);
		panel.add(dfs);
		panel.add(bfs);
		add(panel,"North");
	
		// adds menu bar
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		JMenuItem menuItemExit = new JMenuItem("Exit");
		menuItemExit.addActionListener(this);
		menuFile.add(menuItemExit);
		JMenuItem menuItemOpen = new JMenuItem("Open TSP");
		menuItemOpen.addActionListener(this);
		menuFile.add(menuItemOpen);

		menuBar.add(menuFile);
		
		// adds menu bar to the frame
		setJMenuBar(menuBar);

		// adds window event listener
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				int reply = JOptionPane.showConfirmDialog(TSPFrame.this,
						"Are you sure you want to quit?",
						"Exit",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (reply == JOptionPane.YES_OPTION) {
					dispose();
				} else {
					return;
				}
			}
		});

		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(true);
		
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new TSPFrame();
			}
		});
	}
}
