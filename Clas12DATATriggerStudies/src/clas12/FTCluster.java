package clas12;

import java.util.ArrayList;

public abstract class FTCluster implements MatchedCluster,HodoCluster{

	private float energy;
	private float time;
	private float x,y;
	private int idx,idy;
	
	private boolean isMatched;
	private int nhits;
	
	
	protected boolean h1tag;
	protected boolean h2tag;
	private double h1energy,h2energy;
	private double h1time,h2time;
	
	private double h1x,h1y,h2x,h2y;
	
	public double getH1x() {
		return h1x;
	}
	public void setH1x(double h1x) {
		this.h1x = h1x;
	}
	public double getH1y() {
		return h1y;
	}
	public void setH1y(double h1y) {
		this.h1y = h1y;
	}
	public double getH2x() {
		return h2x;
	}
	public void setH2x(double h2x) {
		this.h2x = h2x;
	}
	public double getH2y() {
		return h2y;
	}
	public void setH2y(double h2y) {
		this.h2y = h2y;
	}
	
	public double getHdistance(int layer) {
		if (layer==1) {
		return Math.sqrt((this.h1x-this.x)*(this.h1x-this.x)+(this.h1y-this.y)*(this.h1y-this.y));
		}
		else if (layer==2) {
			return Math.sqrt((this.h2x-this.x)*(this.h2x-this.x)+(this.h2y-this.y)*(this.h2y-this.y));
		}
		else {
			return -1;
		}
	}
	
	
	public double getH1time() {
		return h1time;
	}
	public void setH1time(double h1time) {
		this.h1time = h1time;
	}
	public double getH2time() {
		return h2time;
	}
	public void setH2time(double h2time) {
		this.h2time = h2time;
	}
	public double getH1energy() {
		return h1energy;
	}
	public void setH1energy(double h1energy) {
		this.h1energy = h1energy;
	}
	public double getH2energy() {
		return h2energy;
	}
	public void setH2energy(double h2energy) {
		this.h2energy = h2energy;
	}

	private FTCluster matchedCluster;
	
	public int getNhits() {
		return nhits;
	}
	public void setNhits(int nhits) {
		this.nhits = nhits;
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
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
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
	public FTCluster(float energy, float time, float x, float y) {
		super();
		this.energy = energy;
		this.time = time;
		this.x = x;
		this.y = y;
		
		
	}
	public FTCluster(float energy, float time, int idx, int idy) {
		super();
		this.energy = energy;
		this.time = time;
		this.idx = idx;
		this.idy = idy;
		matchedCluster = null;
	}
	
	public boolean isMatched() {
		return isMatched;
	}
	
	public void setMatched() {
		isMatched=true;
	}
	public void unsetMatched() {
		isMatched=false;
	}
	
	public FTCluster getMatchedCluster() {	
		return matchedCluster;
	}
	public void setMatchedCluster(FTCluster cluster) {
		matchedCluster = cluster;
	}
	
	public boolean getH1tag() {
		return h1tag;
	}

	public void setH1tag(int h1tag) {
		if (h1tag!=0) this.h1tag=true;
		else this.h1tag=false;
	}

	public boolean getH2tag() {
		return h2tag;
	}

	public void setH2tag(int h2tag) {
		if (h2tag!=0) this.h2tag=true;
		else this.h2tag=false;
	}
	
	@Override 
	public boolean hasHodo() {
		return (h1tag && h2tag);
	}
	
	public boolean hasHodoLayer(int layer) {
		switch (layer) {
		case 1:
			return h1tag;
		case 2:
			return h2tag;
		default:
			return false;	
		}
	}

	@Override
	public void setHodo() {
		// TODO Auto-generated method stub
		h1tag=true;
		h2tag=true;
	}

	@Override
	public void unsetHodo() {
		// TODO Auto-generated method stub
		h1tag=false;
		h2tag=false;
	}
	
	
	/*Given the idx and idy, that are those from VTP+1, return xy of reconstruction frame*/
	 
	/*These are 2 usefull methods for printing during debug, given VTP's x-y system return calorimeter x-y system*/
	public int getIdxRec() {
		int ret = 0;
		int xvtp=this.idx;
		xvtp=xvtp-1; //go back to xvtp
		if (xvtp <= 10)
			ret = (xvtp - 11);
		else
			ret = (xvtp - 10);
		return ret;
	}
	public int getIdyRec() {
		int ret = 0;
		int yvtp=this.idy;
		yvtp=yvtp-1; //go back to yvtp
		if (yvtp <= 10)
			ret = (yvtp - 11);
		else
			ret = (yvtp - 10);
		return ret;
	}
	
	
	
}
