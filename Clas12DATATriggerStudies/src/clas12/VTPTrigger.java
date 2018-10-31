package clas12;

public class VTPTrigger {
	private int pattern,time;
	
	

	public int getPattern() {
		return pattern;
	}

	public void setPattern(int pattern) {
		this.pattern = pattern;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
	public VTPTrigger(int pattern,int time) {
		this.pattern=pattern;
		this.time=time;
	}
	public VTPTrigger(int lpattern,int hpattern,int time) {
		this.pattern=(lpattern&0xFFFF)|((hpattern<<16)&0xFFFF0000);
		this.time=time;
	}
	
	public boolean hasBit(int ibit) {
		return (((pattern>>ibit) & 0x1) != 0);
	}
}
