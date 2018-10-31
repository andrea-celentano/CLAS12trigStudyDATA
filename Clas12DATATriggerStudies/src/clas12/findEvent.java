package clas12;

import java.io.IOException;

import org.jlab.io.base.DataBank;
import org.jlab.io.base.DataEvent;
import org.jlab.io.hipo.HipoDataSource;

public class findEvent {

	private static String inputFileName = "/work/clas12/trigStudyDATA/004288.hipo";
	private static DataReaderAndMatcher dataReader;
	private static int nevent = 0;
	private static int eventN=15776;
	public static void main(String[] args) throws IOException {

		HipoDataSource reader = new HipoDataSource();
		reader.open(inputFileName);
		System.out.println("There are: " + reader.getSize() + " events in the file");
		dataReader=new DataReaderAndMatcher(null);
		outerloop: while (reader.hasEvent() == true) {

			DataEvent event = reader.getNextEvent();

			DataBank RunDB = null;
			if (event.hasBank("RUN::config")) RunDB = event.getBank("RUN::config");
			if (RunDB == null) {
				System.out.println("Skipping event without head bank");
				nevent++;
				continue;
			}
			RunData runData = dataReader.readHead(RunDB);
			if (runData.getEventN()==eventN){
				System.out.println("FOUND event "+eventN+" at position "+(nevent+1));
				break;
			}
			nevent++;
		}
	}
}
