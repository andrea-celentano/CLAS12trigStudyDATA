package clas12;

import java.util.ArrayList;

public class ReconFTCluster extends FTCluster {
	private boolean h1tag, h2tag;
	ArrayList<FTHit> hits;

	public ReconFTCluster(float energy, float time, float x, float y) {
		super(energy, time, x, y);
		// TODO Auto-generated constructor stub
		hits = new ArrayList<FTHit>();
	}

	public ReconFTCluster(float energy, float time, int idx, int idy) {
		super(energy, time, idx, idy);
		// TODO Auto-generated constructor stub
		hits = new ArrayList<FTHit>();
	}

	public void addHit(FTHit hit) {
		hits.add(hit);
	}

	public void clearHits() {
		hits.clear();
	}

	@Override
	public int getNhits() {
		return hits.size();
	}


}
