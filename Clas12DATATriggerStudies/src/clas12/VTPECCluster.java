package clas12;

public class VTPECCluster {

	private int energy, time;
	private double u, v, w;
	private int sector;

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public double getU() {
		return u;
	}

	public void setU(double u) {
		this.u = u;
	}

	public double getV() {
		return v;
	}

	public void setV(double v) {
		this.v = v;
	}

	public double getW() {
		return w;
	}

	public void setW(double w) {
		this.w = w;
	}

	public int getSector() {
		return sector;
	}

	public void setSector(int sector) {
		this.sector = sector;
	}

	public VTPECCluster(int sector, int energy, int time, double u, double v, double w) {
		this.sector = sector;
		this.energy = energy;
		this.time = time;
		this.u = u;
		this.v = v;
		this.w = w;
	}
	
	public boolean isDuplicate(VTPECCluster cluster) {
		boolean ret=false;
		if ((cluster.getW()==w)&&(cluster.getV()==v)&&(cluster.getU()==v)&&(cluster.getSector()==sector)&&(cluster.getEnergy()==energy)){
			ret=true;
		}
		return ret;
	}
	
	public void print() {
		System.out.println("VTPCluster sector["+(sector+1)+"] energy: "+energy+" time: "+time+" U: "+u+" V: "+v+" W: "+w);
	}
}
