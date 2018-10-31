package clas12;

public class FTHit {

	int idx,idy;
	float energy,time;
	public FTHit(int idx, int idy, float energy, float time) {
		super();
		this.idx = idx;
		this.idy = idy;
		this.energy = energy;
		this.time = time;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getIdy() {
		return idy;
	}
	public void setIdy(int idy) {
		this.idy = idy;
	}
	public float getEnergy() {
		return energy;
	}
	public void setEnergy(float energy) {
		this.energy = energy;
	}
	public float getTime() {
		return time;
	}
	public void setTime(float time) {
		this.time = time;
	}
	
	
}
