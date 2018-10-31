package clas12;

public class RunData {
	long triggerWord;
	int eventN;
	int timestamp;
	
	RunData(int ineventN,long inword,int itimestamp){
		triggerWord=inword;
		eventN=ineventN;
		timestamp=itimestamp;
	}
	
	int getEventN() {
		return eventN;
	}
	
	int getTimestamp() {
		return timestamp;
	}
	
	boolean isBitSet(int bit) {
		boolean ret=false;
		
		ret=(((triggerWord>>bit)&0x1)==1);
		
		return ret;
	}
}
