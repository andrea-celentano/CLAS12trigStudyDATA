package clas12;

import org.jlab.clas.physics.Vector3;
import org.jlab.groot.data.TDirectory;
import org.jlab.groot.ui.TBrowser;
import org.jlab.io.base.DataBank;
import org.jlab.io.base.DataEvent;
import org.jlab.io.hipo.*;
import org.jlab.io.evio.*;

import java.util.List;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Date;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

@SuppressWarnings("unused")
public class AnalysisClass {

	public static double phiAngle_CLAS12 = 60.;
	public static double thetaAngle_CLAS12 = 25.;
	public static double L0FTOF1B_CLAS12 = 668.09;
	private DataReaderAndMatcher dataReader;
	private String inputFileName;
	private int nEvents;
	private boolean doShowHistograms;
	private boolean doMap;
	private GuiClass guiClass;
	public int nevent;

	private float VTP_Emin;
	private float PCAL_Emin;
	private float FTOF1B_Emin;
	private float FTOF1B_Tmin;
	private float FTOF1B_Tmax;
	private float eMinHisto;
	private float eMaxHisto;

	private String outputFileName;
	public boolean debug;
	private HipoDataSync writer;

	private RunData runData;
	private List<VTPFTCluster> VTPClusters;
	private List<ReconFTCluster> ReconClusters;
	private List<ReconParticleFD> ReconParticlesFD;
	private List<ReconParticleCD> ReconParticlesCD;
	private List<VTPTrigger> VTPTriggers;
	private List<VTPmask>[] FTOFVTPmask;
	private List<VTPmask>[] PCUVTPmask;
	private List<VTPECCluster>[] PCALVTPclusters;

	private List<RawFTOFHit>[] FTOFhits;

	private boolean flagSkim;
	private boolean doSkim;

	private int triggerBit;
	private int nTriggersEvent;
	private static int nBITS = 32;

	// 13 FTOF-PCAL
	// 19 FTOF-PCAL & PCAL>15 MeV
	// 25 FTOF-PCAL & PCAL>15 MEV & DC (old)
	// 25 FTOF-PCAL & PCAL>15 MEV & DC (new)
	private static int sector1TrgBit = 1; /*From bit 1 to bit 6: CLAS12-FD, FTOF*PCU*/
	private static int sector1TrgBit2 = 7;/*From bit 7 to bit 12: CLAS12-FD, FTOF*PCU AND PCU>15MeV*/
	private static int sector1TrgBit3 = 13;/*From bit 13 to bit 18: CLAS12-FD, FTOF*PCU AND PCU>15MeV AND 5-out-of-6 segments in DC*/
	private static int sector1TrgBit4 = 19;/*From bit 19 to bit 25: CLAS12-FD, FTOF*PCU AND PCU>15MeV AND 5-out-of-6 segments in DC AND roads*/
	
	private static int centralTrgBit1N = -1;
	private static int centralTrgBit2N = -1;/*TODO*/
	
	private boolean sectorTrgBit1[];
	private boolean sectorTrgBit2[];
	private boolean sectorTrgBit3[];
	private boolean sectorTrgBit4[];
	private boolean sectorTrgBitSimulatedFromVTP[];

	private boolean centralTrgBit1;
	private boolean centralTrgBit2;
	
	private int nTracksPerSector[];
	private int idTrackPerSector[];

	int nTriggers;
	int nTracksFD;
	int nTracksCD;

	int nTriggers2;
	int nTracks2;

	int nClusters, nTwoClusters;

	// OLD TABLE
	int TABLE[] = new int[] { 1, 1, 2, 3, 3, 4, 4, 5, 6, 7, 7, 8, 8, 9, 10, 11, 11, 12, 12, 12, 13, 14, 14, 15, 15, 16, 17, 17, 18, 19, 19, 20, 21, 21, 22, 23,
			23, 24, 25, 25, 26, 27, 28, 28, 29, 30, 30, 31, 32, 32, 33, 34, 35, 36, 38, 39, 41, 42, 43, 45, 46, 48, 49, 50, 52, 53, 55, 56 };

	// NEW TABLE
	int TABLE_NEW[] = new int[] { 0, 1, 1, 2, 2, 3, 4, 4, 5, 6, 7, 7, 8, 9, 9, 10, 11, 11, 12, 13, 13, 14, 15, 15, 16, 16, 17, 17, 18, 19, 19, 20, 21, 21, 22,
			23, 23, 24, 24, 25, 26, 26, 27, 28, 28, 29, 30, 30, 31, 32, 32, 33, 34, 35, 37, 38, 39, 41, 42, 44, 45, 46, 48, 49, 50, 52, 53, 54 };

	@SuppressWarnings("unchecked")
	Vector<Integer>[] trgTimes = (Vector<Integer>[]) new Vector[nBITS];

	/* counters */
	private int n_FT_AllMATCH;
	private int n_FT_AllREC;
	private int n_FT_HodoMATCH;
	private int n_FT_HodoREC;
	private int n_FT_HodoMATCHHODO;

	/* PCAL U: from 1 to 68 */
	/* FTOF1B: from 1 to 62 */
	private int getTOF1B(int pcalU) {
		int TOF1B = 0;
		int idx = pcalU - 1; // to have it
		/* This is the table with in the trigger, with FTOF indexed from 0 to 61 */

		TOF1B = TABLE_NEW[idx] + 1;
		return TOF1B;
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		AnalysisClass myClass = new AnalysisClass();
		myClass.setup(args);
		myClass.print();
		myClass.run();
	}

	/* Here starts non-physics related methods */
	public void print() {
		System.out.println("Ana class");
		System.out.println("HIPO fname: " + this.inputFileName);
	}

	private boolean match(FTCluster lhs, FTCluster rhs) {
		boolean ret = false;
		if ((lhs.getIdx() == rhs.getIdx()) && (lhs.getIdy() == rhs.getIdy())) ret = true;
		return ret;
	}

	public void setup(String[] args) throws IOException {
		nEvents = -1;
		outputFileName = "";
		debug = false;
		doMap = false;
		doSkim = false;
		triggerBit = -1;
		nTriggers = 0;
		nTracksFD = 0;
		nTracksCD = 0;
		nTracks2 = 0;
		nTriggers2 = 0;

		nTracksPerSector = new int[6];
		idTrackPerSector = new int[6];

		sectorTrgBit1 = new boolean[6];
		sectorTrgBit2 = new boolean[6];
		sectorTrgBit3 = new boolean[6];
		sectorTrgBit4 = new boolean[6];

		sectorTrgBitSimulatedFromVTP = new boolean[6];

		nClusters = 0;
		nTwoClusters = 0;

		PCAL_Emin = 0;
		FTOF1B_Emin = 0;
		FTOF1B_Tmin = 0;
		FTOF1B_Tmax = 999;

		FileReader in = new FileReader(args[0]);
		BufferedReader br = new BufferedReader(in);
		String line;
		while ((line = br.readLine()) != null) {
			if (line.startsWith("#")) /* This is a comment */
				continue;
			String[] splited = line.split(" ");
			if (splited[0].contains("infname")) {
				this.inputFileName = splited[1];
			} else if (splited[0].contains("ofname")) {
				this.outputFileName = splited[1];
			} else if (splited[0].contains("skim")) {
				this.doSkim = true;
			} else if (splited[0].contains("debug")) {
				this.debug = true;
			} else if (splited[0].contains("nevents")) {
				this.nEvents = Integer.parseInt(splited[1]);
			} else if (splited[0].contains("showHistograms")) {
				this.doShowHistograms = true;
			} else if (splited[0].contains("doMap")) {
				this.doMap = true;
			} else if (splited[0].contains("VTP_Emin")) {
				this.VTP_Emin = Float.parseFloat(splited[1]);
			} else if (splited[0].contains("eMinHisto")) {
				this.eMinHisto = Float.parseFloat(splited[1]);
			} else if (splited[0].contains("eMaxHisto")) {
				this.eMaxHisto = Float.parseFloat(splited[1]);
			} else if (splited[0].contains("triggerBit")) {
				this.triggerBit = Integer.parseInt(splited[1]);
			} else if (splited[0].contains("PCAL_Emin")) {
				this.PCAL_Emin = Float.parseFloat(splited[1]);
			} else if (splited[0].contains("FTOF_Emin")) {
				this.FTOF1B_Emin = Float.parseFloat(splited[1]);
			} else if (splited[0].contains("FTOF_T")) {
				this.FTOF1B_Tmin = Float.parseFloat(splited[1]);
				this.FTOF1B_Tmax = Float.parseFloat(splited[2]);
			}
		}

		br.close();
		this.setupDataReader();
		if (doSkim) this.setupDataWriter();

		this.setupHistograms();

		/* Some counters */
		n_FT_AllREC = 0;
		n_FT_AllMATCH = 0;
		n_FT_HodoREC = 0;
		n_FT_HodoMATCH = 0;
		n_FT_HodoMATCHHODO = 0;
	}

	private void setupDataWriter() {
		writer = new HipoDataSync();
		writer.open(inputFileName + ".skim");

	}

	public void setupHistograms() {
		System.out.println("Going to setup histograms");
		guiClass = new GuiClass(this);

		guiClass.seteMaxHisto(eMaxHisto);
		guiClass.seteMinHisto(eMinHisto);

		guiClass.setupHistograms();
		System.out.println("Setup histograms done");
	}

	private void setupDataReader() {
		dataReader = new DataReaderAndMatcher(this);
		dataReader.setVTP_Emin(this.VTP_Emin);
		/* Also setup here what needed to read data */
		VTPClusters = new ArrayList<VTPFTCluster>();
		ReconClusters = new ArrayList<ReconFTCluster>();
		ReconParticlesFD = new ArrayList<ReconParticleFD>();
		ReconParticlesCD = new ArrayList<ReconParticleCD>();
		VTPTriggers = new ArrayList<VTPTrigger>();
		FTOFVTPmask = new ArrayList[6];
		PCUVTPmask = new ArrayList[6];
		PCALVTPclusters = new ArrayList[6];
		FTOFhits = new ArrayList[6];

		for (int ii = 0; ii < 6; ii++) {
			FTOFVTPmask[ii] = new ArrayList<VTPmask>();
			PCUVTPmask[ii] = new ArrayList<VTPmask>();
			FTOFhits[ii] = new ArrayList<RawFTOFHit>();
			PCALVTPclusters[ii] = new ArrayList<VTPECCluster>();
		}

		for (int i = 0; i < trgTimes.length; i++) {
			trgTimes[i] = new Vector<Integer>();
		}
	}

	public void run() {

		int nReconClusters;
		int nVTPClusters;
		int nReconParticlesFD;
		int nReconParticlesCD;

		int nTrgClusters = 0;
		VTPFTCluster TrgCluster = null;

		HipoDataSource reader = new HipoDataSource();
		reader.open(this.inputFileName);
		System.out.println("There are: " + reader.getSize() + " events in the file");

		if (nEvents == -1) {
			nEvents = reader.getSize();
			System.out.println("Reading ALL ");
		} else {
			System.out.println("Reading: " + nEvents);
		}
		outerloop: while (reader.hasEvent() == true && nevent < nEvents) {

			DataEvent event = reader.getNextEvent();

			if (nevent % 10000 == 0) System.out.println("Analyzing " + nevent + " events");

			DataBank RunDB = null;
			if (event.hasBank("RUN::config")) RunDB = event.getBank("RUN::config");
			if (RunDB == null) {
				System.out.println("Skipping event without head bank");
				nevent++;
				continue;
			}
			runData = dataReader.readHead(RunDB);

			// If triggerBit !=0, get only events with that trigger bit set
			if (triggerBit >= 0) {
				if ((runData.triggerWord >> triggerBit & 0x1) == 0) {
					nevent++;
					continue;
				}
			}
			nTriggers++;

			Date date = new Date();
			date.setTime((long) runData.getTimestamp() * 1000);

			// System.out.println(date);

			//Read data from banks
			DataBank FTRecClustersDB = null;
			if (event.hasBank("FTCAL::clusters")) FTRecClustersDB = event.getBank("FTCAL::clusters");

			DataBank FTHitsDB = null;
			if (event.hasBank("FTCAL::hits")) FTHitsDB = event.getBank("FTCAL::hits");

			DataBank FTHodoHitsDB = null;
			if (event.hasBank("FTHODO::hits")) FTHodoHitsDB = event.getBank("FTHODO::hits");

			DataBank FTHodoClustersDB = null;
			if (event.hasBank("FTHODO::clusters")) FTHodoClustersDB = event.getBank("FTHODO::clusters");

			DataBank VTPDB = null;
			if (event.hasBank("RAW::vtp")) VTPDB = event.getBank("RAW::vtp");

			DataBank FTRecParticlesDB = null;
			if (event.hasBank("FT::particles")) FTRecParticlesDB = event.getBank("FT::particles");

			DataBank RecParticlesDB = null;
			if (event.hasBank("REC::Particle")) RecParticlesDB = event.getBank("REC::Particle");

			DataBank TBTracksDB = null;
			if (event.hasBank("REC::Track")) TBTracksDB = event.getBank("REC::Track");

			DataBank RecCalorimeterDB = null;
			if (event.hasBank("REC::Calorimeter")) RecCalorimeterDB = event.getBank("REC::Calorimeter");

			DataBank ClustersDB = null;
			if (event.hasBank("ECAL::clusters")) ClustersDB = event.getBank("ECAL::clusters");

			DataBank RecScintillatorDB = null;
			if (event.hasBank("REC::Scintillator")) RecScintillatorDB = event.getBank("REC::Scintillator");

			DataBank RawFTOFHitsDB = null;
			if (event.hasBank("FTOF::rawhits")) RawFTOFHitsDB = event.getBank("FTOF::rawhits");

			nVTPClusters = dataReader.makeFTVTPClusters(VTPDB, VTPClusters);
			nReconClusters = dataReader.makeFTReconClusters(FTRecParticlesDB, FTRecClustersDB, FTHitsDB, FTHodoClustersDB, FTHodoHitsDB, ReconClusters);
			dataReader.matchClusters(ReconClusters, VTPClusters);

			dataReader.makeRawFTOFhits(RawFTOFHitsDB, FTOFhits);

			/*Following two lines take date from reconstructed particle banks and decode*/
			nReconParticlesFD = dataReader.makeReconParticlesFD(RecParticlesDB, TBTracksDB, RecCalorimeterDB, RecScintillatorDB, ClustersDB, FTOFhits,
					ReconParticlesFD);
			nReconParticlesCD = dataReader.makeReconParticlesCD(RecParticlesDB, RecScintillatorDB, ReconParticlesCD);

			//Decode data from VTP trigger bank
			nTriggersEvent = dataReader.makeVTPTriggers(VTPDB, VTPTriggers, FTOFVTPmask, PCUVTPmask, PCALVTPclusters);

			//System.out.println(nReconParticlesFD+" "+nReconParticlesCD);
			
			/* DoTriggerAnalysis: verify which bits are set, for each sector. Do also central trigger bits*/
			DoTriggerAnalysis();

			/*
			 * DoParticleAnalysis: take events with exactly 1 GOOD track per sector and
			 * verify the trigger efficiency
			 */
			DoParticleAnalysisFD();
			DoParticleAnalysisCD();

			// DoFTAnalysis();

			flagSkim = false;

			

			if (flagSkim && doSkim) {
				writer.writeEvent(event);
			}
			nevent++;
		}

		if (doSkim) writer.close();
		System.out.println("process histograms");
		guiClass.processHistograms();
		if (this.doShowHistograms == true) {
			guiClass.showHistograms();
		}
		if (this.outputFileName != "") {
			/* Save all histograms */
			TDirectory dir = new TDirectory();
			guiClass.saveHistograms(dir);
			dir.writeFile(this.outputFileName);
		}

		printFTefficiency();
		System.out.println("TRIGGERS: " + nTriggers + " TRACKS-FD: " + nTracksFD + " TRACKS-CD: " + nTracksCD);

		nTracksFD = 0;
		nTracksCD = 0;
		nTriggers = 0;

	}

	private void printFTefficiency() {
		double n_FT_AllMATCH_EFF = 1. * n_FT_AllMATCH / n_FT_AllREC;
		double n_FT_HODOMATCH_EFF = 1. * n_FT_HodoMATCH / n_FT_HodoREC;
		double n_FT_HODOMATCHHODO_EFF = 1. * n_FT_HodoMATCHHODO / n_FT_HodoREC;

		System.out.println("Total FT clusters: " + n_FT_AllREC + " those matched to VTP: " + " " + n_FT_AllMATCH + " ratio: " + n_FT_AllMATCH_EFF);
		System.out.println(
				"Total FT clusters with hodo: " + n_FT_HodoREC + " those matched to VTP(any VTP): " + " " + n_FT_HodoMATCH + " ratio: " + n_FT_HODOMATCH_EFF);
		System.out.println("Total FT clusters with hodo: " + n_FT_HodoREC + " those matched to VTP(with hodo VTP): " + " " + n_FT_HodoMATCHHODO + " ratio: "
				+ n_FT_HODOMATCHHODO_EFF);

	}

	private void DoFTAnalysis() {

		/*
		 * This part is the analysis part for 2-clusters events
		 * 
		 * Plot clusters multiplicity Plot clusters multiplicity (E>500 MeV)
		 */

		/*
		 * ArrayList<VTPFTCluster> highEClusters = new ArrayList<VTPFTCluster>();
		 * guiClass.getHistogram1D("nClustersALL").fill(VTPClusters.size());
		 * nClusters++; for (VTPFTCluster cluster : VTPClusters) {
		 * guiClass.getHistogram1D("clusterMultiplicityAll").fill(cluster.getNhits());
		 * guiClass.getHistogram2D("clusterMultiplicityVsE").fill(cluster.getNhits(),
		 * cluster.getEnergy() / 1E3); if (cluster.getEnergy() > 500) {
		 * guiClass.getHistogram1D("clusterMultiplicityHighE").fill(cluster.getNhits());
		 * highEClusters.add(cluster); } }
		 * guiClass.getHistogram1D("nClustersHighE").fill(highEClusters.size()); if
		 * (highEClusters.size() >= 2) {
		 * guiClass.getHistogram2D("TwoClustersE").fill(highEClusters.get(0).getEnergy()
		 * / 1E3, highEClusters.get(1).getEnergy() / 1E3);
		 * guiClass.getHistogram1D("TwoClustersEsum").fill(highEClusters.get(0).
		 * getEnergy() / 1E3 + highEClusters.get(1).getEnergy() / 1E3); clustersLoop:
		 * for (int i1 = 0; i1 < highEClusters.size(); i1++) { for (int i2 = (i1 + 1);
		 * i2 < highEClusters.size(); i2++) { VTPFTCluster cluster1 =
		 * highEClusters.get(i1); VTPFTCluster cluster2 = highEClusters.get(i2); if
		 * (Math.abs(cluster1.getTime() - cluster2.getTime()) < 16) { nTwoClusters++;
		 * break clustersLoop; } } } }/*
		 * 
		 * 
		 * 
		 * 
		 * 
		 * /*First, loop on the recon clusters. Plot all of them and also those that are
		 * matched to a VTP cluster
		 */
		for (ReconFTCluster cluster : ReconClusters) {
			/*
			 * Clusters too early / too late are not properly reconstructed by the decoder
			 */
			if ((cluster.getTime() < 450) || (cluster.getTime() > 700)) {
				continue;
			}

			n_FT_AllREC++;

			guiClass.getHistogram1D("h1_FT_E_AllREC").fill(cluster.getEnergy());
			guiClass.getHistogram1D("h1_FT_T_AllREC").fill(cluster.getTime());
			guiClass.getHistogram2D("h2_FT_XY_AllREC").fill(cluster.getIdx(), cluster.getIdy());

			/*
			 * guiClass.getHistogram1D("hodoL1EnergyMatch").fill(cluster.getH1energy());
			 * guiClass.getHistogram1D("hodoL2EnergyMatch").fill(cluster.getH2energy());
			 */
			if (cluster.hasHodoLayer(1)) {
				guiClass.getHistogram1D("h1_FT_h1E_Hodo1REC").fill(cluster.getH1energy());
			}
			if (cluster.hasHodoLayer(2)) {
				guiClass.getHistogram1D("h1_FT_h2E_Hodo2REC").fill(cluster.getH2energy());
			}

			if (cluster.hasHodo()) {
				n_FT_HodoREC++;
				guiClass.getHistogram1D("h1_FT_E_HodoREC").fill(cluster.getEnergy());
				guiClass.getHistogram1D("h1_FT_T_HodoREC").fill(cluster.getTime());
				guiClass.getHistogram2D("h2_FT_XY_HodoREC").fill(cluster.getIdx(), cluster.getIdy());

				guiClass.getHistogram1D("h1_FT_TCmTH1_HodoREC").fill(cluster.getTime() - cluster.getH1time());
				guiClass.getHistogram1D("h1_FT_TCmTH2_HodoREC").fill(cluster.getTime() - cluster.getH2time());
				guiClass.getHistogram1D("h1_FT_TH2mTH1_HodoREC").fill(cluster.getH2time() - cluster.getH1time());

				guiClass.getHistogram1D("h1_FT_RCmRH1_HodoREC").fill(cluster.getHdistance(1));
				guiClass.getHistogram1D("h1_FT_RCmRH2_HodoREC").fill(cluster.getHdistance(2));

				if ((cluster.isMatched() == false) || (cluster.isMatched() && (cluster.getMatchedCluster().hasHodo() == false))) {
					guiClass.getHistogram2D("h2_FT_XY_HodoNOMATCHHODO").fill(cluster.getIdx(), cluster.getIdy());
					guiClass.getHistogram1D("h1_FT_TCmTH1_HodoNOMATCHHODO").fill(cluster.getTime() - cluster.getH1time());
					guiClass.getHistogram1D("h1_FT_TCmTH2_HodoNOMATCHHODO").fill(cluster.getTime() - cluster.getH2time());
					guiClass.getHistogram1D("h1_FT_TH2mTH1_HodoNOMATCHHODO").fill(cluster.getH2time() - cluster.getH1time());

					guiClass.getHistogram1D("h1_FT_RCmRH1_HodoNOMATCHHODO").fill(cluster.getHdistance(1));
					guiClass.getHistogram1D("h1_FT_RCmRH2_HodoNOMATCHHODO").fill(cluster.getHdistance(2));

					System.out.println("Missing! " + nevent + " " + runData.eventN + " " + cluster.getIdxRec() + " " + cluster.getIdyRec());
					for (VTPFTCluster cluster2 : VTPClusters) {
						System.out.println(
								"VTP: " + cluster2.getIdxRec() + " " + cluster2.getIdyRec() + " " + cluster2.hasHodoLayer(1) + " " + cluster2.hasHodoLayer(2));
					}
				}

			}

			if (cluster.isMatched()) {
				n_FT_AllMATCH++;
				guiClass.getHistogram1D("h1_FT_E_AllMATCH").fill(cluster.getEnergy());
				guiClass.getHistogram1D("h1_FT_T_AllMATCH").fill(cluster.getTime());
				guiClass.getHistogram2D("h2_FT_XY_AllMATCH").fill(cluster.getIdx(), cluster.getIdy());

				guiClass.getHistogram2D("h2_FT_T_vs_VTP_T_AllMATCH").fill(cluster.getTime(), cluster.getMatchedCluster().getTime());

				if (cluster.hasHodoLayer(1)) {
					guiClass.getHistogram1D("h1_FT_h1E_Hodo1MATCH").fill(cluster.getH1energy());
					if (cluster.getMatchedCluster().hasHodoLayer(1)) {
						guiClass.getHistogram1D("h1_FT_h1E_Hodo1MATCHHODO1").fill(cluster.getH1energy());
					}
				}

				if (cluster.hasHodoLayer(2)) {
					guiClass.getHistogram1D("h1_FT_h2E_Hodo2MATCH").fill(cluster.getH2energy());
					if (cluster.getMatchedCluster().hasHodoLayer(2)) {
						guiClass.getHistogram1D("h1_FT_h2E_Hodo2MATCHHODO2").fill(cluster.getH2energy());
					}
				}

				if (cluster.hasHodo()) {
					n_FT_HodoMATCH++;
					guiClass.getHistogram1D("h1_FT_E_HodoMATCH").fill(cluster.getEnergy());
					guiClass.getHistogram1D("h1_FT_T_HodoMATCH").fill(cluster.getTime());
					guiClass.getHistogram2D("h2_FT_XY_HodoMATCH").fill(cluster.getIdx(), cluster.getIdy());
					guiClass.getHistogram2D("h2_FT_T_vs_VTP_T_HodoMATCH").fill(cluster.getTime(), cluster.getMatchedCluster().getTime());

					guiClass.getHistogram1D("h1_FT_TCmTH1_HodoMATCH").fill(cluster.getTime() - cluster.getH1time());
					guiClass.getHistogram1D("h1_FT_TCmTH2_HodoMATCH").fill(cluster.getTime() - cluster.getH2time());
					guiClass.getHistogram1D("h1_FT_TH2mTH1_HodoMATCH").fill(cluster.getH2time() - cluster.getH1time());

					guiClass.getHistogram1D("h1_FT_RCmRH1_HodoMATCH").fill(cluster.getHdistance(1));
					guiClass.getHistogram1D("h1_FT_RCmRH2_HodoMATCH").fill(cluster.getHdistance(2));

					if (cluster.getMatchedCluster().hasHodo()) {
						n_FT_HodoMATCHHODO++;
						guiClass.getHistogram1D("h1_FT_E_HodoMATCHHODO").fill(cluster.getEnergy());
						guiClass.getHistogram1D("h1_FT_T_HodoMATCHHODO").fill(cluster.getTime());
						guiClass.getHistogram2D("h2_FT_XY_HodoMATCHHODO").fill(cluster.getIdx(), cluster.getIdy());
						guiClass.getHistogram2D("h2_FT_T_vs_VTP_T_HodoMATCHHODO").fill(cluster.getTime(), cluster.getMatchedCluster().getTime());

						guiClass.getHistogram1D("h1_FT_TCmTH1_HodoMATCHHODO").fill(cluster.getTime() - cluster.getH1time());
						guiClass.getHistogram1D("h1_FT_TCmTH2_HodoMATCHHODO").fill(cluster.getTime() - cluster.getH2time());
						guiClass.getHistogram1D("h1_FT_TH2mTH1_HodoMATCHHODO").fill(cluster.getH2time() - cluster.getH1time());

						guiClass.getHistogram1D("h1_FT_RCmRH1_HodoMATCHHODO").fill(cluster.getHdistance(1));
						guiClass.getHistogram1D("h1_FT_RCmRH2_HodoMATCHHODO").fill(cluster.getHdistance(2));
					}

				}

				// guiClass.getHistogram1D("hodoL1EnergyMatchVTP").fill(cluster.getH1energy());
				// guiClass.getHistogram1D("hodoL2EnergyMatchVTP").fill(cluster.getH2energy());

				// if (cluster.getMatchedCluster().hasHodoLayer(1))
				// guiClass.getHistogram1D("hodoL1EnergyMatchVTPL1").fill(cluster.getH1energy());
				// if (cluster.getMatchedCluster().hasHodoLayer(2))
				// guiClass.getHistogram1D("hodoL2EnergyMatchVTPL2").fill(cluster.getH2energy());

			} else {
				guiClass.getHistogram2D("h2_FT_XY_AllNOMATCH").fill(cluster.getIdx(), cluster.getIdy());
				if (cluster.hasHodo()) {
					guiClass.getHistogram2D("h2_FT_XY_HodoNOMATCH").fill(cluster.getIdx(), cluster.getIdy());
				}
			}

		}

		/*
		 * for (VTPFTCluster cluster : VTPClusters) {
		 * 
		 * // Clusters too late: ignore them if (cluster.getTime() > 300) { continue; }
		 * guiClass.getHistogram1D("allVTPEnergy").fill(cluster.getEnergy());
		 * guiClass.getHistogram1D("hVTPenergy_" + cluster.getIdx() + "_" +
		 * cluster.getIdy()).fill(cluster.getEnergy());
		 * guiClass.getHistogram1D("allVTPTime").fill(cluster.getTime());
		 * 
		 * guiClass.getHistogram2D("allVTP").fill(cluster.getIdx(), cluster.getIdy());
		 * guiClass.getHistogram2D("allVTPEnergyVsTime").fill(cluster.getEnergy(),
		 * cluster.getTime()); if (cluster.hasHodo()) {
		 * guiClass.getHistogram1D("allVTPhodoEnergy").fill(cluster.getEnergy());
		 * guiClass.getHistogram2D("allVTPhodo").fill(cluster.getIdx(),
		 * cluster.getIdy()); } if (cluster.hasHodoLayer(1)) {
		 * guiClass.getHistogram1D("allVTPhodo1Energy").fill(cluster.getEnergy());
		 * guiClass.getHistogram2D("allVTPhodo1").fill(cluster.getIdx(),
		 * cluster.getIdy()); } if (cluster.hasHodoLayer(2)) {
		 * guiClass.getHistogram1D("allVTPhodo2Energy").fill(cluster.getEnergy());
		 * guiClass.getHistogram2D("allVTPhodo2").fill(cluster.getIdx(),
		 * cluster.getIdy()); }
		 * 
		 * if (debug) System.out.println("VTP cluster: " + cluster.getIdx() + " " +
		 * cluster.getIdy() + " " + cluster.getEnergy() + " " + cluster.hasHodoLayer(1)
		 * + " " + cluster.hasHodoLayer(2)); if (cluster.isMatched()) {
		 * guiClass.getHistogram1D("allVTPmatchedEnergy").fill(cluster.getEnergy());
		 * guiClass.getHistogram1D("hVTPenergyMatched_" + cluster.getIdx() + "_" +
		 * cluster.getIdy()).fill(cluster.getEnergy());
		 * guiClass.getHistogram1D("allVTPmatchedTime").fill(cluster.getTime()); }
		 * 
		 * if (nTrgClusters == 1) {
		 * guiClass.getHistogram1D("Etrg1").fill(cluster.getEnergy()); if
		 * (match(TrgCluster, cluster)) {
		 * guiClass.getHistogram1D("Etrg2").fill(cluster.getEnergy()); } }
		 * 
		 * }
		 */

		/*
		 * if (FTHodoHitsDB != null) { for (int ihodo = 0; ihodo < FTHodoHitsDB.rows();
		 * ihodo++) {
		 * 
		 * int hodoL = FTHodoHitsDB.getByte("layer", ihodo); double hodoHitE =
		 * FTHodoHitsDB.getFloat("energy", ihodo);
		 * 
		 * if (hodoL == 1) guiClass.getHistogram1D("hodoL1EnergyAll").fill(hodoHitE);
		 * else guiClass.getHistogram1D("hodoL2EnergyAll").fill(hodoHitE); } }
		 */

	}

	private void DoParticleAnalysisFD() {
		int imom;
		boolean flagMatch1 = false;
		boolean flagMatch2 = false;
		boolean flagMatch3 = false;
		boolean flagMatch4 = false;
		double theta, phi, p = 0;

		double xFTOF1B, yFTOF1B;
		double xPCAL, yPCAL, E_L, E_R;
		Vector3 vPCAL;
		Vector3 vFTOF1B;
		int q;
		int sector = -1;
		int iparticle;
		for (int ii = 0; ii < 6; ii++) {
			nTracksPerSector[ii] = 0;
			idTrackPerSector[ii] = -1;
		}

		/*Count the number of particles in each sector*/
		for (iparticle = 0; iparticle < ReconParticlesFD.size(); iparticle++) {

			ReconParticleFD particle = ReconParticlesFD.get(iparticle);
			theta = Math.toDegrees(particle.theta());
			phi = Math.toDegrees(particle.phi());
			p = particle.p();
			q = particle.charge();

			if (!particle.hasFTOF1B()) continue;
			sector = particle.getFTOF1Bsector();

			// System.out.println(q+" "+p+" "+theta+" "+phi);

			/*
			 * Do some checks on the quality of the track Require that: Particle has both
			 * PCAL and FTOF, Particle has same sector in PCAL and FTOF PCAL energy is more
			 * than PCAL_Emin FTOF energy is more than FTOF1B_Emin FTOF time is within
			 * FTOF1B_Tmin and FTOF1B_Tmax well defined sector, and PCALenergy is higher
			 * than thr
			 * 
			 * CHECK THE TEMPROARY FLAG IN DATAREADERANDMATCHER
			 */

			if (!isGoodParticleFD(particle)) continue;

			nTracksFD++;
			idTrackPerSector[sector - 1] = iparticle;
			nTracksPerSector[sector - 1]++;

		}

		for (int isector = 1; isector <= 6; isector++) {

			xFTOF1B = 0;
			yFTOF1B = 0;
			xPCAL = 0;
			yPCAL = 0;
			vPCAL = new Vector3(0, 0, 0);
			vFTOF1B = new Vector3(0, 0, 0);

			/*
			 * Only consider those events with excatly 1 track per sector
			 */
			if (nTracksPerSector[isector - 1] == 1) {
				iparticle = idTrackPerSector[isector - 1];
				ReconParticleFD particle = ReconParticlesFD.get(iparticle);
				theta = Math.toDegrees(particle.theta());
				phi = Math.toDegrees(particle.phi());
				p = particle.p();
				q = particle.charge();

				if (particle.hasPCAL()) {
					vPCAL = particle.getPCAL_hit();
					xPCAL = vPCAL.x();
					yPCAL = vPCAL.y();
				}
				E_R = particle.getRawFTOFHit().get_EnergyR();
				E_L = particle.getRawFTOFHit().get_EnergyL();

				vFTOF1B = particle.getFTOF1B_hit();
				xFTOF1B = vFTOF1B.x();
				yFTOF1B = vFTOF1B.y();

				/* Fill momentum/angle histograms with quantities from this track */
				guiClass.getHistogram1D("h1_MomAllREC").fill(p);
				guiClass.getHistogram1D("h1_MomAllREC_" + isector).fill(p);
				guiClass.getHistogram2D("h2_ThetaPhiAllREC").fill(phi, theta);
				guiClass.getHistogram2D("h2_ThetaPAllREC_" + isector).fill(p, theta);

				if (q > 0) {
					guiClass.getHistogram1D("h1_MomQPREC").fill(p);
					guiClass.getHistogram1D("h1_MomQPREC_" + isector).fill(p);
					guiClass.getHistogram2D("h2_ThetaPhiQPREC").fill(phi, theta);
					guiClass.getHistogram2D("h2_ThetaPQPREC_" + isector).fill(p, theta);

				} else if (q < 0) {
					guiClass.getHistogram1D("h1_MomQMREC").fill(p);
					guiClass.getHistogram1D("h1_MomQMREC_" + isector).fill(p);
					guiClass.getHistogram2D("h2_ThetaPhiQMREC").fill(phi, theta);
					guiClass.getHistogram2D("h2_ThetaPQMREC_" + isector).fill(p, theta);

				}

				/* Fill PCAL and FTOF related histograms with quantities from this track */
				guiClass.getHistogram2D("h2_PCAL_XY_AllREC").fill(xPCAL, yPCAL);
				guiClass.getHistogram1D("h1_PCAL_E_AllREC_" + isector).fill(particle.getPCALenergy());
				guiClass.getHistogram1D("h1_PCAL_ID_AllREC_" + isector).fill(particle.getPCALU_id());

				guiClass.getHistogram2D("h2_FTOF1B_XY_AllREC").fill(xFTOF1B, yFTOF1B);
				guiClass.getHistogram1D("h1_FTOF1B_E_AllREC_" + isector).fill(particle.getFTOF1Benergy());
				guiClass.getHistogram1D("h1_FTOF1B_Esqrt_AllREC_" + isector).fill(Math.sqrt(E_L * E_R));
				guiClass.getHistogram2D("h2_FTOF1B_E2_AllREC_" + isector).fill(E_L, E_R);
				guiClass.getHistogram1D("h1_FTOF1B_T_AllREC_" + isector).fill(particle.getFTOF1Btime());
				guiClass.getHistogram1D("h1_FTOF1B_ID_AllREC_" + isector).fill(particle.getFTOF1B_id());
				if (particle.hasPCAL()) {
					guiClass.getHistogram1D("h1_delta_PCAL_FTOF_AllREC").fill(getTOF1B(particle.getPCALU_id()) - particle.getFTOF1B_id());
				}
				guiClass.getHistogram2D("h2_FTOF1BT_Mom_AllREC_" + isector).fill(p, particle.getFTOF1Btime());
				/* Fill from VTP */
				for (VTPECCluster cluster : PCALVTPclusters[isector - 1]) {
					guiClass.getHistogram1D("h1_VTPPCAL_E_AllREC_" + isector).fill(cluster.getEnergy() * 0.1);
					guiClass.getHistogram2D("h2_VTPCAL_E_ClusterE_AllREC").fill(cluster.getEnergy() * 0.1, particle.getPCALenergy() * 1000);
				}
				/* Check the FTOF-vs-PCAL map from the VTP data */
				/*
				 * if (doMap) {
				 * 
				 * guiClass.getHistogram2D("h2_PCAL_FTOF").fill(particle.getPCALU_id(),
				 * particle.getFTOF1B_id()); if (q > 0)
				 * guiClass.getHistogram2D("h2_PCAL_FTOF_qP").fill(particle.getPCALU_id(),
				 * particle.getFTOF1B_id()); if (q < 0)
				 * guiClass.getHistogram2D("h2_PCAL_FTOF_qM").fill(particle.getPCALU_id(),
				 * particle.getFTOF1B_id());
				 * 
				 * for (int iPCU = 0; iPCU < PCUVTPmask[isector - 1].size(); iPCU++) { for (int
				 * iFTOF = 0; iFTOF < FTOFVTPmask[isector - 1].size(); iFTOF++) { double
				 * deltaTime = 4 * (PCUVTPmask[isector - 1].get(iPCU).getTime() -
				 * FTOFVTPmask[isector - 1].get(iFTOF).getTime());
				 * 
				 * if ((deltaTime < 0) || (deltaTime > 60)) continue;
				 * 
				 * for (int iu = 0; iu < 68; iu++) { if (PCUVTPmask[isector -
				 * 1].get(iPCU).hasBit(iu) == false) continue; for (int is = 0; is < 62; is++) {
				 * if (FTOFVTPmask[isector - 1].get(iFTOF).hasBit(is) == false) continue;
				 * guiClass.getHistogram2D("h2_VTP_PCAL_FTOF_track").fill(iu + 1, is + 1); } } }
				 * } }
				 */

				/* Now check different bits */
				if (sectorTrgBit1[isector - 1]) {
					flagMatch1 = true;
					guiClass.getHistogram1D("h1_MomAllTRG1").fill(p);
					guiClass.getHistogram1D("h1_MomAllTRG1_" + isector).fill(p);
					guiClass.getHistogram2D("h2_ThetaPhiAllTRG1").fill(phi, theta);
					guiClass.getHistogram2D("h2_ThetaPAllTRG1_" + isector).fill(p, theta);

					if (q > 0) {
						guiClass.getHistogram1D("h1_MomQPTRG1").fill(p);
						guiClass.getHistogram1D("h1_MomQPTRG1_" + isector).fill(p);
						guiClass.getHistogram2D("h2_ThetaPhiQPTRG1").fill(phi, theta);
						guiClass.getHistogram2D("h2_ThetaPQPTRG1_" + isector).fill(p, theta);

					} else if (q < 0) {
						guiClass.getHistogram1D("h1_MomQMTRG1").fill(p);
						guiClass.getHistogram1D("h1_MomQMTRG1_" + isector).fill(p);
						guiClass.getHistogram2D("h2_ThetaPhiQMTRG1").fill(phi, theta);
						guiClass.getHistogram2D("h2_ThetaPQMTRG1_" + isector).fill(p, theta);

					}

					/* Fill PCAL and FTOF related histograms with quantities from this track */
					guiClass.getHistogram2D("h2_PCAL_XY_AllTRG1").fill(xPCAL, yPCAL);
					if (particle.hasPCAL()) {
						guiClass.getHistogram1D("h1_PCAL_E_AllTRG1_" + isector).fill(particle.getPCALenergy());
						guiClass.getHistogram1D("h1_PCAL_ID_AllTRG1_" + isector).fill(particle.getPCALU_id());
					}
					guiClass.getHistogram2D("h2_FTOF1B_XY_AllTRG1").fill(xFTOF1B, yFTOF1B);
					guiClass.getHistogram1D("h1_FTOF1B_E_AllTRG1_" + isector).fill(particle.getFTOF1Benergy());
					guiClass.getHistogram1D("h1_FTOF1B_Esqrt_AllTRG1_" + isector).fill(Math.sqrt(E_L * E_R));
					guiClass.getHistogram2D("h2_FTOF1B_E2_AllTRG1_" + isector).fill(E_L, E_R);
					guiClass.getHistogram1D("h1_FTOF1B_T_AllTRG1_" + isector).fill(particle.getFTOF1Btime());
					guiClass.getHistogram1D("h1_FTOF1B_ID_AllTRG1_" + isector).fill(particle.getFTOF1B_id());
					if (particle.hasPCAL()) {
						guiClass.getHistogram1D("h1_delta_PCAL_FTOF_AllTRG1").fill(getTOF1B(particle.getPCALU_id()) - particle.getFTOF1B_id());
					}

				} else { // a track was found in this sector no trigger associated to it!
					flagMatch1 = false;

					guiClass.getHistogram1D("h1_MomAllNOTRG1").fill(p);
					guiClass.getHistogram1D("h1_MomAllNOTRG1_" + isector).fill(p);
					guiClass.getHistogram2D("h2_ThetaPhiAllNOTRG1").fill(phi, theta);
					guiClass.getHistogram2D("h2_ThetaPAllNOTRG1_" + isector).fill(p, theta);

					if (q > 0) {
						guiClass.getHistogram1D("h1_MomQPNOTRG1").fill(p);
						guiClass.getHistogram1D("h1_MomQPNOTRG1_" + isector).fill(p);
						guiClass.getHistogram2D("h2_ThetaPhiQPNOTRG1").fill(phi, theta);
						guiClass.getHistogram2D("h2_ThetaPQPNOTRG1_" + isector).fill(p, theta);

					} else if (q < 0) {
						guiClass.getHistogram1D("h1_MomQMNOTRG1").fill(p);
						guiClass.getHistogram1D("h1_MomQMNOTRG1_" + isector).fill(p);
						guiClass.getHistogram2D("h2_ThetaPhiQMNOTRG1").fill(phi, theta);
						guiClass.getHistogram2D("h2_ThetaPQMNOTRG1_" + isector).fill(p, theta);

					}

					/* Fill PCAL and FTOF related histograms with quantities from this track */
					guiClass.getHistogram2D("h2_PCAL_XY_AllNOTRG1").fill(xPCAL, yPCAL);
					if (particle.hasPCAL()) {
						guiClass.getHistogram1D("h1_PCAL_E_AllNOTRG1_" + isector).fill(particle.getPCALenergy());
						guiClass.getHistogram1D("h1_PCAL_ID_AllNOTRG1_" + isector).fill(particle.getPCALU_id());
					}
					guiClass.getHistogram2D("h2_FTOF1B_XY_AllNOTRG1").fill(xFTOF1B, yFTOF1B);
					guiClass.getHistogram1D("h1_FTOF1B_E_AllNOTRG1_" + isector).fill(particle.getFTOF1Benergy());
					guiClass.getHistogram1D("h1_FTOF1B_Esqrt_AllNOTRG1_" + isector).fill(Math.sqrt(E_L * E_R));
					guiClass.getHistogram2D("h2_FTOF1B_E2_AllNOTRG1_" + isector).fill(E_L, E_R);
					guiClass.getHistogram1D("h1_FTOF1B_T_AllNOTRG1_" + isector).fill(particle.getFTOF1Btime());
					guiClass.getHistogram1D("h1_FTOF1B_ID_AllNOTRG1_" + isector).fill(particle.getFTOF1B_id());

					if (particle.hasPCAL()) {
						guiClass.getHistogram1D("h1_delta_PCAL_FTOF_AllNOTRG1").fill(getTOF1B(particle.getPCALU_id()) - particle.getFTOF1B_id());
					}
					/* Fill from VTP */
					for (VTPECCluster cluster : PCALVTPclusters[isector - 1]) {
						guiClass.getHistogram1D("h1_VTPPCAL_E_AllNOTRG1_" + isector).fill(cluster.getEnergy() * 0.1);
					}
				}

				if (sectorTrgBit2[isector - 1]) {
					flagMatch2 = true;
					guiClass.getHistogram1D("h1_MomAllTRG2").fill(p);
					guiClass.getHistogram1D("h1_MomAllTRG2_" + isector).fill(p);
					guiClass.getHistogram2D("h2_ThetaPhiAllTRG2").fill(phi, theta);
					guiClass.getHistogram2D("h2_ThetaPAllTRG2_" + isector).fill(p, theta);

					if (q > 0) {
						guiClass.getHistogram1D("h1_MomQPTRG2").fill(p);
						guiClass.getHistogram1D("h1_MomQPTRG2_" + isector).fill(p);
						guiClass.getHistogram2D("h2_ThetaPhiQPTRG2").fill(phi, theta);
						guiClass.getHistogram2D("h2_ThetaPQPTRG2_" + isector).fill(p, theta);

					} else if (q < 0) {
						guiClass.getHistogram1D("h1_MomQMTRG2").fill(p);
						guiClass.getHistogram1D("h1_MomQMTRG2_" + isector).fill(p);
						guiClass.getHistogram2D("h2_ThetaPhiQMTRG2").fill(phi, theta);
						guiClass.getHistogram2D("h2_ThetaPQMTRG2_" + isector).fill(p, theta);

					}

					/* Fill PCAL and FTOF related histograms with quantities from this track */
					guiClass.getHistogram2D("h2_PCAL_XY_AllTRG2").fill(xPCAL, yPCAL);
					if (particle.hasPCAL()) {
						guiClass.getHistogram1D("h1_PCAL_E_AllTRG2_" + isector).fill(particle.getPCALenergy());
						guiClass.getHistogram1D("h1_PCAL_ID_AllTRG2_" + isector).fill(particle.getPCALU_id());
					}
					guiClass.getHistogram2D("h2_FTOF1B_XY_AllTRG2").fill(xFTOF1B, yFTOF1B);
					guiClass.getHistogram1D("h1_FTOF1B_E_AllTRG2_" + isector).fill(particle.getFTOF1Benergy());
					guiClass.getHistogram1D("h1_FTOF1B_Esqrt_AllTRG2_" + isector).fill(Math.sqrt(E_L * E_R));
					guiClass.getHistogram2D("h2_FTOF1B_E2_AllTRG2_" + isector).fill(E_L, E_R);
					guiClass.getHistogram1D("h1_FTOF1B_T_AllTRG2_" + isector).fill(particle.getFTOF1Btime());
					guiClass.getHistogram1D("h1_FTOF1B_ID_AllTRG2_" + isector).fill(particle.getFTOF1B_id());
					if (particle.hasPCAL()) {
						guiClass.getHistogram1D("h1_delta_PCAL_FTOF_AllTRG2").fill(getTOF1B(particle.getPCALU_id()) - particle.getFTOF1B_id());
					}

				} else { // a track was found in this sector no trigger associated to it!
					flagMatch2 = false;

					guiClass.getHistogram1D("h1_MomAllNOTRG2").fill(p);
					guiClass.getHistogram1D("h1_MomAllNOTRG2_" + isector).fill(p);
					guiClass.getHistogram2D("h2_ThetaPhiAllNOTRG2").fill(phi, theta);
					guiClass.getHistogram2D("h2_ThetaPAllNOTRG2_" + isector).fill(p, theta);

					if (q > 0) {
						guiClass.getHistogram1D("h1_MomQPNOTRG2").fill(p);
						guiClass.getHistogram1D("h1_MomQPNOTRG2_" + isector).fill(p);
						guiClass.getHistogram2D("h2_ThetaPhiQPNOTRG2").fill(phi, theta);
						guiClass.getHistogram2D("h2_ThetaPQPNOTRG2_" + isector).fill(p, theta);

					} else if (q < 0) {
						guiClass.getHistogram1D("h1_MomQMNOTRG2").fill(p);
						guiClass.getHistogram1D("h1_MomQMNOTRG2_" + isector).fill(p);
						guiClass.getHistogram2D("h2_ThetaPhiQMNOTRG2").fill(phi, theta);
						guiClass.getHistogram2D("h2_ThetaPQMNOTRG2_" + isector).fill(p, theta);

					}

					/* Fill PCAL and FTOF related histograms with quantities from this track */
					guiClass.getHistogram2D("h2_PCAL_XY_AllNOTRG2").fill(xPCAL, yPCAL);
					if (particle.hasPCAL()) {
						guiClass.getHistogram1D("h1_PCAL_E_AllNOTRG2_" + isector).fill(particle.getPCALenergy());
						guiClass.getHistogram1D("h1_PCAL_ID_AllNOTRG2_" + isector).fill(particle.getPCALU_id());
					}
					guiClass.getHistogram2D("h2_FTOF1B_XY_AllNOTRG2").fill(xFTOF1B, yFTOF1B);
					guiClass.getHistogram1D("h1_FTOF1B_E_AllNOTRG2_" + isector).fill(particle.getFTOF1Benergy());
					guiClass.getHistogram1D("h1_FTOF1B_Esqrt_AllNOTRG2_" + isector).fill(Math.sqrt(E_L * E_R));
					guiClass.getHistogram2D("h2_FTOF1B_E2_AllNOTRG2_" + isector).fill(E_L, E_R);
					guiClass.getHistogram1D("h1_FTOF1B_T_AllNOTRG2_" + isector).fill(particle.getFTOF1Btime());
					guiClass.getHistogram1D("h1_FTOF1B_ID_AllNOTRG2_" + isector).fill(particle.getFTOF1B_id());
					if (particle.hasPCAL()) {
						guiClass.getHistogram1D("h1_delta_PCAL_FTOF_AllNOTRG2").fill(getTOF1B(particle.getPCALU_id()) - particle.getFTOF1B_id());
					}
					/* Fill from VTP */
					for (VTPECCluster cluster : PCALVTPclusters[isector - 1]) {
						guiClass.getHistogram1D("h1_VTPPCAL_E_AllNOTRG2_" + isector).fill(cluster.getEnergy() * 0.1);
						if (sectorTrgBit1[isector - 1]) {
							guiClass.getHistogram1D("h1_VTPPCAL_E_AllTRG1NOTRG2_" + isector).fill(cluster.getEnergy() * 0.1);
						}
					}
					/* Special */
					if (sectorTrgBit1[isector - 1]) {
						guiClass.getHistogram2D("h2_PCAL_XY_AllTRG1NOTRG2").fill(xPCAL, yPCAL);
						guiClass.getHistogram1D("h1_PCAL_E_AllTRG1NOTRG2_" + isector).fill(particle.getPCALenergy());

						// System.out.println("Event: " + runData.eventN + " " + nevent + " sector: " +
						// isector + " X-Y:" + xPCAL + " " + yPCAL + " energy: "
						// + particle.getPCALenergy() + " U:" + particle.getPCAL_hitUVW().x() + " V:" +
						// particle.getPCAL_hitUVW().y() + " W:"
						// + particle.getPCAL_hitUVW().z());
						// for (VTPECCluster cluster : PCALVTPclusters[isector - 1]) {
						// cluster.print();
						// }

					}
				}

				if (sectorTrgBit3[isector - 1]) {
					flagMatch3 = true;
					guiClass.getHistogram1D("h1_MomAllTRG3").fill(p);
					guiClass.getHistogram1D("h1_MomAllTRG3_" + isector).fill(p);
					guiClass.getHistogram2D("h2_ThetaPhiAllTRG3").fill(phi, theta);
					guiClass.getHistogram2D("h2_ThetaPAllTRG3_" + isector).fill(p, theta);

					if (q > 0) {
						guiClass.getHistogram1D("h1_MomQPTRG3").fill(p);
						guiClass.getHistogram1D("h1_MomQPTRG3_" + isector).fill(p);
						guiClass.getHistogram2D("h2_ThetaPhiQPTRG3").fill(phi, theta);
						guiClass.getHistogram2D("h2_ThetaPQPTRG3_" + isector).fill(p, theta);

					} else if (q < 0) {
						guiClass.getHistogram1D("h1_MomQMTRG3").fill(p);
						guiClass.getHistogram1D("h1_MomQMTRG3_" + isector).fill(p);
						guiClass.getHistogram2D("h2_ThetaPhiQMTRG3").fill(phi, theta);
						guiClass.getHistogram2D("h2_ThetaPQMTRG3_" + isector).fill(p, theta);

					}

					/* Fill PCAL and FTOF related histograms with quantities from this track */
					guiClass.getHistogram2D("h2_PCAL_XY_AllTRG3").fill(xPCAL, yPCAL);
					if (particle.hasPCAL()) {
						guiClass.getHistogram1D("h1_PCAL_E_AllTRG3_" + isector).fill(particle.getPCALenergy());
						guiClass.getHistogram1D("h1_PCAL_ID_AllTRG3_" + isector).fill(particle.getPCALU_id());
					}
					guiClass.getHistogram2D("h2_FTOF1B_XY_AllTRG3").fill(xFTOF1B, yFTOF1B);
					guiClass.getHistogram1D("h1_FTOF1B_E_AllTRG3_" + isector).fill(particle.getFTOF1Benergy());
					guiClass.getHistogram1D("h1_FTOF1B_Esqrt_AllTRG3_" + isector).fill(Math.sqrt(E_L * E_R));
					guiClass.getHistogram2D("h2_FTOF1B_E2_AllTRG3_" + isector).fill(E_L, E_R);
					guiClass.getHistogram1D("h1_FTOF1B_T_AllTRG3_" + isector).fill(particle.getFTOF1Btime());
					guiClass.getHistogram1D("h1_FTOF1B_ID_AllTRG3_" + isector).fill(particle.getFTOF1B_id());

					if (particle.hasPCAL()) {
						guiClass.getHistogram1D("h1_delta_PCAL_FTOF_AllTRG3").fill(getTOF1B(particle.getPCALU_id()) - particle.getFTOF1B_id());
					}

				} else { // a track was found in this sector no trigger associated to it!
					flagMatch3 = false;
					guiClass.getHistogram1D("h1_MomAllNOTRG3").fill(p);
					guiClass.getHistogram1D("h1_MomAllNOTRG3_" + isector).fill(p);
					guiClass.getHistogram2D("h2_ThetaPhiAllNOTRG3").fill(phi, theta);
					guiClass.getHistogram2D("h2_ThetaPAllNOTRG3_" + isector).fill(p, theta);

					if (q > 0) {
						guiClass.getHistogram1D("h1_MomQPNOTRG3").fill(p);
						guiClass.getHistogram1D("h1_MomQPNOTRG3_" + isector).fill(p);
						guiClass.getHistogram2D("h2_ThetaPhiQPNOTRG3").fill(phi, theta);
						guiClass.getHistogram2D("h2_ThetaPQPNOTRG3_" + isector).fill(p, theta);

					} else if (q < 0) {
						guiClass.getHistogram1D("h1_MomQMNOTRG3").fill(p);
						guiClass.getHistogram1D("h1_MomQMNOTRG3_" + isector).fill(p);
						guiClass.getHistogram2D("h2_ThetaPhiQMNOTRG3").fill(phi, theta);
						guiClass.getHistogram2D("h2_ThetaPQMNOTRG3_" + isector).fill(p, theta);

					}

					/* Fill PCAL and FTOF related histograms with quantities from this track */
					guiClass.getHistogram2D("h2_PCAL_XY_AllNOTRG3").fill(xPCAL, yPCAL);
					if (particle.hasPCAL()) {
						guiClass.getHistogram1D("h1_PCAL_E_AllNOTRG3_" + isector).fill(particle.getPCALenergy());
						guiClass.getHistogram1D("h1_PCAL_ID_AllNOTRG3_" + isector).fill(particle.getPCALU_id());
					}

					guiClass.getHistogram2D("h2_FTOF1B_XY_AllNOTRG3").fill(xFTOF1B, yFTOF1B);
					guiClass.getHistogram1D("h1_FTOF1B_E_AllNOTRG3_" + isector).fill(particle.getFTOF1Benergy());
					guiClass.getHistogram1D("h1_FTOF1B_Esqrt_AllNOTRG3_" + isector).fill(Math.sqrt(E_L * E_R));
					guiClass.getHistogram2D("h2_FTOF1B_E2_AllNOTRG3_" + isector).fill(E_L, E_R);
					guiClass.getHistogram1D("h1_FTOF1B_T_AllNOTRG3_" + isector).fill(particle.getFTOF1Btime());
					guiClass.getHistogram1D("h1_FTOF1B_ID_AllNOTRG3_" + isector).fill(particle.getFTOF1B_id());
					if (particle.hasPCAL()) {
						guiClass.getHistogram1D("h1_delta_PCAL_FTOF_AllNOTRG3").fill(getTOF1B(particle.getPCALU_id()) - particle.getFTOF1B_id());
					}
				}

				if (sectorTrgBit4[isector - 1]) {
					flagMatch1 = true;
					guiClass.getHistogram1D("h1_MomAllTRG4").fill(p);
					guiClass.getHistogram1D("h1_MomAllTRG4_" + isector).fill(p);
					guiClass.getHistogram2D("h2_ThetaPhiAllTRG4").fill(phi, theta);
					guiClass.getHistogram2D("h2_ThetaPAllTRG4_" + isector).fill(p, theta);

					if (q > 0) {
						guiClass.getHistogram1D("h1_MomQPTRG4").fill(p);
						guiClass.getHistogram1D("h1_MomQPTRG4_" + isector).fill(p);
						guiClass.getHistogram2D("h2_ThetaPhiQPTRG4").fill(phi, theta);
						guiClass.getHistogram2D("h2_ThetaPQPTRG4_" + isector).fill(p, theta);

					} else if (q < 0) {
						guiClass.getHistogram1D("h1_MomQMTRG4").fill(p);
						guiClass.getHistogram1D("h1_MomQMTRG4_" + isector).fill(p);
						guiClass.getHistogram2D("h2_ThetaPhiQMTRG4").fill(phi, theta);
						guiClass.getHistogram2D("h2_ThetaPQMTRG4_" + isector).fill(p, theta);

					}

					/* Fill PCAL and FTOF related histograms with quantities from this track */
					guiClass.getHistogram2D("h2_PCAL_XY_AllTRG4").fill(xPCAL, yPCAL);
					if (particle.hasPCAL()) {
						guiClass.getHistogram1D("h1_PCAL_E_AllTRG4_" + isector).fill(particle.getPCALenergy());
						guiClass.getHistogram1D("h1_PCAL_ID_AllTRG4_" + isector).fill(particle.getPCALU_id());
					}
					guiClass.getHistogram2D("h2_FTOF1B_XY_AllTRG4").fill(xFTOF1B, yFTOF1B);
					guiClass.getHistogram1D("h1_FTOF1B_E_AllTRG4_" + isector).fill(particle.getFTOF1Benergy());
					guiClass.getHistogram1D("h1_FTOF1B_Esqrt_AllTRG4_" + isector).fill(Math.sqrt(E_L * E_R));
					guiClass.getHistogram2D("h2_FTOF1B_E2_AllTRG4_" + isector).fill(E_L, E_R);
					guiClass.getHistogram1D("h1_FTOF1B_T_AllTRG4_" + isector).fill(particle.getFTOF1Btime());
					guiClass.getHistogram1D("h1_FTOF1B_ID_AllTRG4_" + isector).fill(particle.getFTOF1B_id());
					if (particle.hasPCAL()) {
						guiClass.getHistogram1D("h1_delta_PCAL_FTOF_AllTRG4").fill(getTOF1B(particle.getPCALU_id()) - particle.getFTOF1B_id());
					}

				} else { // a track was found in this sector no trigger associated to it!
					flagMatch4 = false;
					guiClass.getHistogram1D("h1_MomAllNOTRG4").fill(p);
					guiClass.getHistogram1D("h1_MomAllNOTRG4_" + isector).fill(p);
					guiClass.getHistogram2D("h2_ThetaPhiAllNOTRG4").fill(phi, theta);
					guiClass.getHistogram2D("h2_ThetaPAllNOTRG4_" + isector).fill(p, theta);

					if (q > 0) {
						guiClass.getHistogram1D("h1_MomQPNOTRG4").fill(p);
						guiClass.getHistogram1D("h1_MomQPNOTRG4_" + isector).fill(p);
						guiClass.getHistogram2D("h2_ThetaPhiQPNOTRG4").fill(phi, theta);
						guiClass.getHistogram2D("h2_ThetaPQPNOTRG4_" + isector).fill(p, theta);

					} else if (q < 0) {
						guiClass.getHistogram1D("h1_MomQMNOTRG4").fill(p);
						guiClass.getHistogram1D("h1_MomQMNOTRG4_" + isector).fill(p);
						guiClass.getHistogram2D("h2_ThetaPhiQMNOTRG4").fill(phi, theta);
						guiClass.getHistogram2D("h2_ThetaPQMNOTRG4_" + isector).fill(p, theta);

					}

					/* Fill PCAL and FTOF related histograms with quantities from this track */
					guiClass.getHistogram2D("h2_PCAL_XY_AllNOTRG4").fill(xPCAL, yPCAL);
					if (particle.hasPCAL()) {
						guiClass.getHistogram1D("h1_PCAL_E_AllNOTRG4_" + isector).fill(particle.getPCALenergy());
						guiClass.getHistogram1D("h1_PCAL_ID_AllNOTRG4_" + isector).fill(particle.getPCALU_id());
					}
					guiClass.getHistogram2D("h2_FTOF1B_XY_AllNOTRG4").fill(xFTOF1B, yFTOF1B);
					guiClass.getHistogram1D("h1_FTOF1B_E_AllNOTRG4_" + isector).fill(particle.getFTOF1Benergy());
					guiClass.getHistogram1D("h1_FTOF1B_Esqrt_AllNOTRG4_" + isector).fill(Math.sqrt(E_L * E_R));
					guiClass.getHistogram2D("h2_FTOF1B_E2_AllNOTRG4_" + isector).fill(E_L, E_R);
					guiClass.getHistogram1D("h1_FTOF1B_T_AllNOTRG4_" + isector).fill(particle.getFTOF1Btime());
					guiClass.getHistogram1D("h1_FTOF1B_ID_AllNOTRG4_" + isector).fill(particle.getFTOF1B_id());
					if (particle.hasPCAL()) {
						guiClass.getHistogram1D("h1_delta_PCAL_FTOF_AllNOTRG4").fill(getTOF1B(particle.getPCALU_id()) - particle.getFTOF1B_id());
					}
				}

				// Here is the "trigger simulation"
				// if (sectorTrgBitSimulatedFromVTP[isector - 1])
				/*
				 * if (sectorTrgBit3[isector - 1]) {
				 * guiClass.getHistogram2D("h2_ThetaPhiAllTRGVTP").fill(phi, theta);
				 * guiClass.getHistogram2D("h2_ThetaPhiAllTRGVTP_" + imom).fill(phi, theta);
				 * guiClass.getHistogram1D("h1_MomAllTRGVTP").fill(p);
				 * 
				 * guiClass.getHistogram2D("h2_FTOF1B_VTPFTOF_T").fill(particle.getFTOF1Btime(),
				 * FTOFVTPmask[isector - 1].get(0).getTime());
				 * 
				 * if (q > 0) { guiClass.getHistogram2D("h2_ThetaPhiQPTRGVTP").fill(phi, theta);
				 * guiClass.getHistogram2D("h2_ThetaPhiQPTRGVTP_" + imom).fill(phi, theta);
				 * guiClass.getHistogram1D("h1_MomQPTRGVTP").fill(p); } else if (q < 0) {
				 * 
				 * guiClass.getHistogram2D("h2_ThetaPhiQMTRGVTP").fill(phi, theta);
				 * guiClass.getHistogram2D("h2_ThetaPhiQMTRGVTP_" + imom).fill(phi, theta);
				 * guiClass.getHistogram1D("h1_MomQMTRGVTP").fill(p);
				 * 
				 * }
				 * 
				 * }
				 */

			}
		}

		/*
		 * if (!flagMatch) {
		 * 
		 * if (p > 1) { System.out.println(nevent + " " + runData.eventN); for (int
		 * isector = 1; isector <= 6; isector++) { System.out.println( isector + " " +
		 * nTracksPerSector[isector - 1] + " " + sectorTrgBit[isector - 1] + " " +
		 * sectorTrgBitSimulatedFromVTP[isector - 1]); for (VTPmask mask :
		 * PCUVTPmask[isector - 1]) { System.out.println("PCAL: " +
		 * String.format("0x%08X", mask.getPatternH()) + " " + String.format("0x%08X",
		 * mask.getPatternM()) + " " + String.format("0x%08X", mask.getPatternL())); for
		 * (int ibit = 0; ibit < 90; ibit++) { if (mask.hasBit(ibit))
		 * System.out.println("PCAL HAS " + (ibit + 1)); } } for (VTPmask mask :
		 * FTOFVTPmask[isector - 1]) { System.out.println("FTOF: " +
		 * String.format("0x%08X", mask.getPatternH()) + " " + String.format("0x%08X",
		 * mask.getPatternM()) + " " + String.format("0x%08X", mask.getPatternL())); for
		 * (int ibit = 0; ibit < 90; ibit++) { if (mask.hasBit(ibit))
		 * System.out.println("FTOF HAS " + (ibit + 1)); } } for (RawFTOFHit hit :
		 * FTOFhits[isector-1]) { System.out.println("FTOF recon: " + hit.get_Paddle());
		 * } }
		 * 
		 * } }
		 */

	}

	private void DoParticleAnalysisCD() {
		int iparticle,IDparticle;
		IDparticle=0;
		int nGoodParticlesCDThisEvent=0;
		double theta,phi,p;
		for (iparticle = 0; iparticle < ReconParticlesCD.size(); iparticle++) {

			ReconParticleCD particle = ReconParticlesCD.get(iparticle);

			// System.out.println(q+" "+p+" "+theta+" "+phi);

			/*
			 * Do some checks on the quality of the track Require that: Particle has both
			 * PCAL and FTOF, Particle has same sector in PCAL and FTOF PCAL energy is more
			 * than PCAL_Emin FTOF energy is more than FTOF1B_Emin FTOF time is within
			 * FTOF1B_Tmin and FTOF1B_Tmax well defined sector, and PCALenergy is higher
			 * than thr
			 * 
			 * CHECK THE TEMPROARY FLAG IN DATAREADERANDMATCHER
			 */

			if (!isGoodParticleCD(particle)) continue;

			nTracksCD++;
			nGoodParticlesCDThisEvent++;
			IDparticle=iparticle; 
		}
		
		/*Select event with exactly one good particle in CD*/
		if (nGoodParticlesCDThisEvent==1) {
			
			ReconParticleCD particle = ReconParticlesCD.get(IDparticle);
			theta = Math.toDegrees(particle.theta());
			phi = Math.toDegrees(particle.phi());
			p = particle.p();
			
			//Fill all-rec histograms
			guiClass.getHistogram1D("h1_CD_MomAllRec").fill(p);
			guiClass.getHistogram2D("h2_CD_ThetaPhiAllRec").fill(phi,theta);
			
			guiClass.getHistogram1D("h1_CTOF_EAllRec").fill(particle.getCTOFenergy());
			guiClass.getHistogram1D("h1_CND1_EAllRec").fill(particle.getCND1energy());
			guiClass.getHistogram1D("h1_CND2_EAllRec").fill(particle.getCND2energy());
			guiClass.getHistogram1D("h1_CND3_EAllRec").fill(particle.getCND3energy());
			
			
			int ctof=particle.getCTOFid();
			int cnd1=particle.getCND1id();
			
		
			guiClass.getHistogram2D("h2_CTOFvsCND1AllRec").fill(particle.getCTOFid(),particle.getCND1id());
		
			
			if (centralTrgBit1==true) {
				guiClass.getHistogram1D("h1_CD_MomAllTRG1").fill(p);
				
				
			}
			
		}

	}

	/*THis is the method that classifies a CD track as "good" or "bad"*/
	
	private boolean isGoodParticleCD(ReconParticleCD particle) {

		if (!particle.hasCTOF()) return false;
		if (!particle.hasCND1()) return false;
		if (particle.getCTOFenergy()<1.) return false;
		
		
		return true;
	}

	/*
	 * Method to determine if a particle is "good" for the trigger analysis.
	 * Requires that:
	 * 
	 * There is FTOF hit with E>Emin There is PCAL hit with E>Emin The FTOF hit and
	 * the PCAL hit, provided by the event builder, are in the same sector. Fiducial
	 * cuts are satisfied.
	 */
	private boolean isGoodParticleFD(ReconParticleFD particle) {

		if ((!particle.hasFTOF1B()) || (!particle.hasPCAL()) || (particle.getFTOF1Bsector() != particle.getPCALsector())
				|| (particle.getPCALenergy() < PCAL_Emin) || (particle.getFTOF1Benergy() < FTOF1B_Emin) || (particle.getFTOF1Btime() < FTOF1B_Tmin)
				|| (particle.getFTOF1Btime() > FTOF1B_Tmax)) {
			return false;
		}

		double PCal_UMax = 490.;
		double PCal_UMin = 10.; // For oubendings this doesn't matter much;
		double PCal_VMax = 535.;
		double PCal_VMin = 10.;
		double PCal_WMax = 535.;
		double PCal_WMin = 10.;

		double u = particle.getPCAL_hitUVW().x();
		double v = particle.getPCAL_hitUVW().y();
		double w = particle.getPCAL_hitUVW().z();

		if ((u > PCal_UMax) || (u < PCal_UMin) || (v > PCal_VMax) || (v < PCal_VMin) || (w > PCal_WMax) || (u < PCal_WMin)) {
			return false;
		}
		return true;
	}

	/*Given the list of triggers - VTPTriggers, 
	 * decode them and associate each bit to each sector
	 */
	private void DoTriggerAnalysis() {
		int nTriggersVTP = VTPTriggers.size();
		int time;
		int nTrg = 0;
		int id_ftof_matched = 0;
		for (int i = 0; i < nBITS; i++) {
			trgTimes[i].clear();
		}
		
		for (int i = 0; i < 6; i++) {
			sectorTrgBit1[i] = false;
			sectorTrgBit2[i] = false;
			sectorTrgBit3[i] = false;
			sectorTrgBit4[i] = false;
			sectorTrgBitSimulatedFromVTP[i] = false;
		}
		
		centralTrgBit1=false;
		centralTrgBit2=false;
		
		for (int itrg = 0; itrg < nTriggersVTP; itrg++) {
			time = VTPTriggers.get(itrg).getTime();
			for (int ibit = 0; ibit < nBITS; ibit++) {
				if (VTPTriggers.get(itrg).hasBit(ibit)) {
					nTrg++;
					trgTimes[ibit].add(time);

				}
			}
			for (int ibit = 0; ibit < 6; ibit++) {
				if (VTPTriggers.get(itrg).hasBit(sector1TrgBit + ibit)) {
					sectorTrgBit1[ibit] = true;
				}
				if (VTPTriggers.get(itrg).hasBit(sector1TrgBit2 + ibit)) {
					sectorTrgBit2[ibit] = true;
				}
				if (VTPTriggers.get(itrg).hasBit(sector1TrgBit3 + ibit)) {
					sectorTrgBit3[ibit] = true;
				}
				if (VTPTriggers.get(itrg).hasBit(sector1TrgBit4 + ibit)) {
					sectorTrgBit4[ibit] = true;
				}
			}
		
			if (VTPTriggers.get(itrg).hasBit(centralTrgBit1N)) {
				centralTrgBit1=true;
			}
			if (VTPTriggers.get(itrg).hasBit(centralTrgBit2N)) {
				centralTrgBit2=true;
			}
		}

		guiClass.getHistogram1D("hTriggerMultiplicity").fill(nTrg);
		for (int ibit = 0; ibit < nBITS; ibit++) {
			if (trgTimes[ibit].size() > 0) {
				guiClass.getHistogram1D("hTriggerBits").fill(ibit, trgTimes[ibit].size());
				for (int jj = 0; jj < trgTimes[ibit].size(); jj++) {
					guiClass.getHistogram1D("hTriggerTimes").fill(trgTimes[ibit].get(jj));
					guiClass.getHistogram2D("hTriggerTimes2D").fill(trgTimes[ibit].get(jj), ibit);
				}
			}
		}

		for (int isector = 1; isector <= 6; isector++) {

			if ((FTOFVTPmask[isector - 1].size() == 0) || (PCUVTPmask[isector - 1].size() == 0)) continue;

			for (int iPCU = 0; iPCU < PCUVTPmask[isector - 1].size(); iPCU++) {
				for (int iFTOF = 0; iFTOF < FTOFVTPmask[isector - 1].size(); iFTOF++) {

					guiClass.getHistogram2D("h2_VTP_PCAL_FTOF_time_all").fill(4 * PCUVTPmask[isector - 1].get(iPCU).getTime(),
							4 * FTOFVTPmask[isector - 1].get(iFTOF).getTime());

					guiClass.getHistogram1D("h1_VTP_delta_PCALFTOF_time_all_" + isector)
							.fill(4 * PCUVTPmask[isector - 1].get(iPCU).getTime() - 4 * FTOFVTPmask[isector - 1].get(iFTOF).getTime());

					// PCAL has: 68 U-bars (1..68)
					// FTOF has: 62 counters (1..62)
					for (int iu = 0; iu < 68; iu++) {
						if (PCUVTPmask[isector - 1].get(iPCU).hasBit(iu) == false) continue;
						id_ftof_matched = this.getTOF1B(iu + 1); // iu is from 0 to 67, getTOF1B wants from 1 to 68, getTOF1B returns from 1 to
																	// 62
						id_ftof_matched = id_ftof_matched - 1;

						for (int imatch = -3; imatch <= 3; imatch++) {
							if (FTOFVTPmask[isector - 1].get(iFTOF).hasBit(id_ftof_matched + imatch) == true) {
								sectorTrgBitSimulatedFromVTP[isector - 1] = true;
								break;
							}
						}
						if (doMap) {
							for (int is = 0; is < 62; is++) {
								if (FTOFVTPmask[isector - 1].get(iFTOF).hasBit(is) == false) continue;
								guiClass.getHistogram2D("h2_VTP_PCAL_FTOF_all").fill(iu + 1, is + 1);
							}
						}
					}

				}
			}
		}
	}

	/*
	 * Here check if a good FTOF hit (both L/R TDC and ADC, matched by the EB) is
	 * seen by the VTP
	 */

	private void DoFTOFAnalysis() {

		boolean flagHasVTP = false;
		for (int isector = 0; isector < 6; isector++) {

			for (RawFTOFHit hit : FTOFhits[isector]) {

				if ((hit.get_EnergyL() < 8) || (hit.get_EnergyR() < 8)) continue;
				guiClass.getHistogram1D("h1_ftofALL_" + isector).fill(hit.get_Paddle());
				flagHasVTP = false;
				for (VTPmask mask : FTOFVTPmask[isector]) {
					if (mask.hasBit(hit.get_Paddle() - 1)) flagHasVTP = true;
				}
				if (flagHasVTP == true) guiClass.getHistogram1D("h1_ftofVTPmatch_" + isector).fill(hit.get_Paddle());
			}

		}

	}

}
