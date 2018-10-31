package clas12;

public class VTPmask {
	private int time;
	private int patternL, patternM, patternH;
	private int sector;

	public long getPatternL() {
		return patternL;
	}

	public long getPatternM() {
		return patternM;
	}

	public long getPatternH() {
		return patternH;
	}

	public void setPattern(int patternL, int patternM, int patternH) {
		this.patternL = patternL;
		this.patternM = patternM;
		this.patternH = patternH;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	// specific for PCU and FTOF, data coded as 3 integers
	public VTPmask(int sector, int patternL, int patternM, int patternH, int time) {
		this.patternL = patternL;
		this.patternM = patternM;
		this.patternH = patternH;
		this.time = time;
		this.sector = sector;
	}

	public boolean hasBit(int ibit) {
		if (ibit < 0)
			return false;
		else if (ibit <= 31)
			return (((patternL >> ibit) & 0x1) != 0);
		else if (ibit <= 63)
			return (((patternM >> (ibit-32)) & 0x1) != 0);
		else if (ibit <= 95)
			return (((patternH >> (ibit-64)) & 0x1) != 0);
		else
			return false;
	}

}
