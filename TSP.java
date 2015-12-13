package traveling_salesman;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TSP {
	public ArrayList<Point> points;	
	
	public TSP(){
		points = new ArrayList<>();
	}
	
	public static void printList(List<Point> p){
		for(int i = 0; i < p.size(); i++){
			System.out.print("("+p.get(i).x + "," + p.get(i).y + ")");
		}
		System.out.println("");
	}
	
	public void readPointsFromFile(String fileName){
		try{
			for(Scanner sc = new Scanner(new File(fileName)); sc.hasNext();){
				String line = sc.nextLine();
				if(Character.isDigit(line.charAt(0))){
					String[] filePoints = line.split(" ");
					points.add(new Point(Double.parseDouble(filePoints[1]),
							Double.parseDouble(filePoints[2])));
				}
			}
		}catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}
	}
	
	public void readPointsFromFile(File tspFile){
		points.clear();
		int count = 1;
		try{
			for(Scanner sc = new Scanner(tspFile); sc.hasNext();){
				String line = sc.nextLine();
				if(Character.isDigit(line.charAt(0))){
					String[] filePoints = line.split(" ");
					points.add(new Point(Double.parseDouble(filePoints[1]),
							Double.parseDouble(filePoints[2])));
				}
			}
		}catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}
	}
	
	public void createTree(){
		for(int i = 0; i < points.size(); i++){
			switch(i){
			case 0:
				points.get(0).p.add(points.get(1));
				points.get(0).p.add(points.get(2));
				points.get(0).p.add(points.get(3));
				break;
			case 1:
				points.get(1).p.add(points.get(2));
				break;
			case 2:
				points.get(2).p.add(points.get(3));
				points.get(2).p.add(points.get(4));
				break;
			case 3:
				points.get(3).p.add(points.get(4));
				points.get(3).p.add(points.get(5));
				points.get(3).p.add(points.get(6));
				break;
			case 4:
				points.get(4).p.add(points.get(6));
				points.get(4).p.add(points.get(7));
				break;
			case 5:
				points.get(5).p.add(points.get(7));
				break;
			case 6:
				points.get(6).p.add(points.get(8));
				points.get(6).p.add(points.get(9));
				break;
			case 7:
				points.get(7).p.add(points.get(8));
				points.get(7).p.add(points.get(9));
				points.get(7).p.add(points.get(10));
				break;
			case 8:
				points.get(8).p.add(points.get(10));
				break;
			case 9:
				points.get(9).p.add(points.get(10));
				break;
			}
		}
	}
	
}
