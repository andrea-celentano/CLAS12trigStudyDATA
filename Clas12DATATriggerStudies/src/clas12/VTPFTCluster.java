package clas12;

public class VTPFTCluster extends FTCluster {

	public VTPFTCluster(float energy, float time, float x, float y) {
		super(energy, time, x, y);
		// TODO Auto-generated constructor stub
	}

	public VTPFTCluster(float energy, float time, int idx, int idy) {
		super(energy, time, idx, idy);
		// TODO Auto-generated constructor stub
	}

	public VTPFTCluster(float energy, float time, int idx, int idy, boolean h1tag, boolean h2tag) {
		super(energy, time, idx, idy);
		super.h1tag = h1tag;
		super.h2tag = h2tag;
	}

}
