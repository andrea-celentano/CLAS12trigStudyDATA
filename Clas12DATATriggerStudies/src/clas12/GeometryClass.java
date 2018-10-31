package clas12;

public class GeometryClass {
	
	public static int ix_min = 1;
	public static int ix_max = 22;
	public static int nx = 22;
	
	
	public static int iy_min = 1;
	public static int iy_max = 22;
	public static int ny = 22;
	
	static int getIdxFromXY(int x,int y) {
		int id=GeometryClass.ny*(y-GeometryClass.iy_min) + (x-GeometryClass.ix_min);
		return id;
	}
	
	static boolean crystalExists(int x,int y) {
		boolean ret=false;
		
		return ret;
	}
	
}
