package clas12;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.data.TDirectory;
import org.jlab.groot.fitter.DataFitter;
import org.jlab.groot.math.Axis;
import org.jlab.groot.math.F1D;
import org.jlab.groot.ui.TCanvas;

public class GuiClass {

	AnalysisClass analysisClass;
	private double eMinHisto = 0;
	private double eMaxHisto = 2200;

	ArrayList<H1F> allH1F;
	ArrayList<H2F> allH2F;

	/* DoTriggerAnalysis histograms */
	H1F hTriggerBits;
	H1F hTriggerMultiplicity;
	H1F hTriggerTimes;
	H2F hTriggerTimes2D;
	H2F h2_VTP_PCAL_FTOF_time_all;
	H2F h2_VTP_PCAL_FTOF_all;
	ArrayList<H1F> h1_VTP_delta_PCALFTOF_time_all;

	/* DoParticleAnalysis histograms */
	/* All Good tracks */
	H1F h1_MomAllREC, h1_MomQPREC, h1_MomQMREC;
	ArrayList<H1F> h1_MomAllREC_sector;
	ArrayList<H1F> h1_MomQPREC_sector;
	ArrayList<H1F> h1_MomQMREC_sector;
	H2F h2_ThetaPhiAllREC, h2_ThetaPhiQPREC, h2_ThetaPhiQMREC;
	ArrayList<H2F> h2_ThetaPAllREC_sector;
	ArrayList<H2F> h2_ThetaPQPREC_sector;
	ArrayList<H2F> h2_ThetaPQMREC_sector;

	H2F h2_PCAL_XY_AllREC;
	ArrayList<H1F> h1_PCAL_E_AllREC, h1_PCAL_ID_AllREC;

	H2F h2_FTOF1B_XY_AllREC;
	ArrayList<H1F> h1_FTOF1B_E_AllREC, h1_FTOF1B_Esqrt_AllREC, h1_FTOF1B_T_AllREC, h1_FTOF1B_ID_AllREC;
	ArrayList<H2F> h2_FTOF1B_E2_AllREC;
	
	H1F h1_delta_PCAL_FTOF_AllREC;
	
	ArrayList<H2F> h2_FTOF1BT_Mom_AllREC;
	
	ArrayList<H1F> h1_VTPPCAL_E_AllREC;
	H2F	h2_VTPCAL_E_ClusterE_AllREC;

	/* Tracks with TRG1 bit set or not */
	H1F h1_MomAllTRG1, h1_MomQPTRG1, h1_MomQMTRG1;
	ArrayList<H1F> h1_MomAllTRG1_sector;
	ArrayList<H1F> h1_MomQPTRG1_sector;
	ArrayList<H1F> h1_MomQMTRG1_sector;
	H2F h2_ThetaPhiAllTRG1, h2_ThetaPhiQPTRG1, h2_ThetaPhiQMTRG1;
	ArrayList<H2F> h2_ThetaPAllTRG1_sector;
	ArrayList<H2F> h2_ThetaPQPTRG1_sector;
	ArrayList<H2F> h2_ThetaPQMTRG1_sector;

	H2F h2_PCAL_XY_AllTRG1;
	ArrayList<H1F> h1_PCAL_E_AllTRG1, h1_PCAL_ID_AllTRG1;

	H2F h2_FTOF1B_XY_AllTRG1;
	ArrayList<H1F> h1_FTOF1B_E_AllTRG1, h1_FTOF1B_Esqrt_AllTRG1, h1_FTOF1B_T_AllTRG1, h1_FTOF1B_ID_AllTRG1;
	ArrayList<H2F> h2_FTOF1B_E2_AllTRG1;

	H1F h1_delta_PCAL_FTOF_AllTRG1;

	H1F h1_MomAllNOTRG1, h1_MomQPNOTRG1, h1_MomQMNOTRG1;
	ArrayList<H1F> h1_MomAllNOTRG1_sector;
	ArrayList<H1F> h1_MomQPNOTRG1_sector;
	ArrayList<H1F> h1_MomQMNOTRG1_sector;
	H2F h2_ThetaPhiAllNOTRG1, h2_ThetaPhiQPNOTRG1, h2_ThetaPhiQMNOTRG1;
	ArrayList<H2F> h2_ThetaPAllNOTRG1_sector;
	ArrayList<H2F> h2_ThetaPQPNOTRG1_sector;
	ArrayList<H2F> h2_ThetaPQMNOTRG1_sector;

	H2F h2_PCAL_XY_AllNOTRG1;
	ArrayList<H1F> h1_PCAL_E_AllNOTRG1, h1_PCAL_ID_AllNOTRG1;

	H2F h2_FTOF1B_XY_AllNOTRG1;
	ArrayList<H1F> h1_FTOF1B_E_AllNOTRG1, h1_FTOF1B_Esqrt_AllNOTRG1, h1_FTOF1B_T_AllNOTRG1, h1_FTOF1B_ID_AllNOTRG1;
	ArrayList<H2F> h2_FTOF1B_E2_AllNOTRG1;

	H1F h1_delta_PCAL_FTOF_AllNOTRG1;

	H1F h1_MomAllEFFTRG1;
	ArrayList<H1F> h1_MomAllEFFTRG1_sector;
	
	ArrayList<H1F> h1_VTPPCAL_E_AllNOTRG1;


	/* Tracks with TRG2 bit set or not */
	H1F h1_MomAllTRG2, h1_MomQPTRG2, h1_MomQMTRG2;
	ArrayList<H1F> h1_MomAllTRG2_sector;
	ArrayList<H1F> h1_MomQPTRG2_sector;
	ArrayList<H1F> h1_MomQMTRG2_sector;
	H2F h2_ThetaPhiAllTRG2, h2_ThetaPhiQPTRG2, h2_ThetaPhiQMTRG2;
	ArrayList<H2F> h2_ThetaPAllTRG2_sector;
	ArrayList<H2F> h2_ThetaPQPTRG2_sector;
	ArrayList<H2F> h2_ThetaPQMTRG2_sector;

	H2F h2_PCAL_XY_AllTRG2;
	ArrayList<H1F> h1_PCAL_E_AllTRG2, h1_PCAL_ID_AllTRG2;
	ArrayList<H1F >h1_PCAL_E_AllTRG1NOTRG2; //special for this bit

	H2F h2_FTOF1B_XY_AllTRG2;
	ArrayList<H1F> h1_FTOF1B_E_AllTRG2, h1_FTOF1B_Esqrt_AllTRG2, h1_FTOF1B_T_AllTRG2, h1_FTOF1B_ID_AllTRG2;
	ArrayList<H2F> h2_FTOF1B_E2_AllTRG2;

	H1F h1_delta_PCAL_FTOF_AllTRG2;

	H1F h1_MomAllNOTRG2, h1_MomQPNOTRG2, h1_MomQMNOTRG2;
	ArrayList<H1F> h1_MomAllNOTRG2_sector;
	ArrayList<H1F> h1_MomQPNOTRG2_sector;
	ArrayList<H1F> h1_MomQMNOTRG2_sector;
	H2F h2_ThetaPhiAllNOTRG2, h2_ThetaPhiQPNOTRG2, h2_ThetaPhiQMNOTRG2;
	ArrayList<H2F> h2_ThetaPAllNOTRG2_sector;
	ArrayList<H2F> h2_ThetaPQPNOTRG2_sector;
	ArrayList<H2F> h2_ThetaPQMNOTRG2_sector;

	H2F h2_PCAL_XY_AllNOTRG2;
	H2F h2_PCAL_XY_AllTRG1NOTRG2; //special for this bit
	ArrayList<H1F> h1_PCAL_E_AllNOTRG2, h1_PCAL_ID_AllNOTRG2;

	H2F h2_FTOF1B_XY_AllNOTRG2;
	ArrayList<H1F> h1_FTOF1B_E_AllNOTRG2, h1_FTOF1B_Esqrt_AllNOTRG2, h1_FTOF1B_T_AllNOTRG2, h1_FTOF1B_ID_AllNOTRG2;
	ArrayList<H2F> h2_FTOF1B_E2_AllNOTRG2;

	H1F h1_delta_PCAL_FTOF_AllNOTRG2;

	H1F h1_MomAllEFFTRG2;
	ArrayList<H1F> h1_MomAllEFFTRG2_sector;

	ArrayList<H1F> h1_VTPPCAL_E_AllNOTRG2;
	ArrayList<H1F> h1_VTPPCAL_E_AllTRG1NOTRG2;
	
	/* Tracks with TRG3 bit set or not */
	H1F h1_MomAllTRG3, h1_MomQPTRG3, h1_MomQMTRG3;
	ArrayList<H1F> h1_MomAllTRG3_sector;
	ArrayList<H1F> h1_MomQPTRG3_sector;
	ArrayList<H1F> h1_MomQMTRG3_sector;
	H2F h2_ThetaPhiAllTRG3, h2_ThetaPhiQPTRG3, h2_ThetaPhiQMTRG3;
	ArrayList<H2F> h2_ThetaPAllTRG3_sector;
	ArrayList<H2F> h2_ThetaPQPTRG3_sector;
	ArrayList<H2F> h2_ThetaPQMTRG3_sector;

	H2F h2_PCAL_XY_AllTRG3;
	ArrayList<H1F> h1_PCAL_E_AllTRG3, h1_PCAL_ID_AllTRG3;

	H2F h2_FTOF1B_XY_AllTRG3;
	ArrayList<H1F> h1_FTOF1B_E_AllTRG3, h1_FTOF1B_Esqrt_AllTRG3, h1_FTOF1B_T_AllTRG3, h1_FTOF1B_ID_AllTRG3;
	ArrayList<H2F> h2_FTOF1B_E2_AllTRG3;

	H1F h1_delta_PCAL_FTOF_AllTRG3;

	H1F h1_MomAllNOTRG3, h1_MomQPNOTRG3, h1_MomQMNOTRG3;
	ArrayList<H1F> h1_MomAllNOTRG3_sector;
	ArrayList<H1F> h1_MomQPNOTRG3_sector;
	ArrayList<H1F> h1_MomQMNOTRG3_sector;
	H2F h2_ThetaPhiAllNOTRG3, h2_ThetaPhiQPNOTRG3, h2_ThetaPhiQMNOTRG3;
	ArrayList<H2F> h2_ThetaPAllNOTRG3_sector;
	ArrayList<H2F> h2_ThetaPQPNOTRG3_sector;
	ArrayList<H2F> h2_ThetaPQMNOTRG3_sector;

	H2F h2_PCAL_XY_AllNOTRG3;
	ArrayList<H1F> h1_PCAL_E_AllNOTRG3, h1_PCAL_ID_AllNOTRG3;

	H2F h2_FTOF1B_XY_AllNOTRG3;
	ArrayList<H1F> h1_FTOF1B_E_AllNOTRG3, h1_FTOF1B_Esqrt_AllNOTRG3, h1_FTOF1B_T_AllNOTRG3, h1_FTOF1B_ID_AllNOTRG3;
	ArrayList<H2F> h2_FTOF1B_E2_AllNOTRG3;

	H1F h1_delta_PCAL_FTOF_AllNOTRG3;

	H1F h1_MomAllEFFTRG3;
	ArrayList<H1F> h1_MomAllEFFTRG3_sector;

	/* Tracks with TRG4 bit set or not */
	H1F h1_MomAllTRG4, h1_MomQPTRG4, h1_MomQMTRG4;
	ArrayList<H1F> h1_MomAllTRG4_sector;
	ArrayList<H1F> h1_MomQPTRG4_sector;
	ArrayList<H1F> h1_MomQMTRG4_sector;
	H2F h2_ThetaPhiAllTRG4, h2_ThetaPhiQPTRG4, h2_ThetaPhiQMTRG4;
	ArrayList<H2F> h2_ThetaPAllTRG4_sector;
	ArrayList<H2F> h2_ThetaPQPTRG4_sector;
	ArrayList<H2F> h2_ThetaPQMTRG4_sector;

	H2F h2_PCAL_XY_AllTRG4;
	ArrayList<H1F> h1_PCAL_E_AllTRG4, h1_PCAL_ID_AllTRG4;

	H2F h2_FTOF1B_XY_AllTRG4;
	ArrayList<H1F> h1_FTOF1B_E_AllTRG4, h1_FTOF1B_Esqrt_AllTRG4, h1_FTOF1B_T_AllTRG4, h1_FTOF1B_ID_AllTRG4;
	ArrayList<H2F> h2_FTOF1B_E2_AllTRG4;

	H1F h1_delta_PCAL_FTOF_AllTRG4;

	H1F h1_MomAllNOTRG4, h1_MomQPNOTRG4, h1_MomQMNOTRG4;
	ArrayList<H1F> h1_MomAllNOTRG4_sector;
	ArrayList<H1F> h1_MomQPNOTRG4_sector;
	ArrayList<H1F> h1_MomQMNOTRG4_sector;
	H2F h2_ThetaPhiAllNOTRG4, h2_ThetaPhiQPNOTRG4, h2_ThetaPhiQMNOTRG4;
	ArrayList<H2F> h2_ThetaPAllNOTRG4_sector;
	ArrayList<H2F> h2_ThetaPQPNOTRG4_sector;
	ArrayList<H2F> h2_ThetaPQMNOTRG4_sector;

	H2F h2_PCAL_XY_AllNOTRG4;
	ArrayList<H1F> h1_PCAL_E_AllNOTRG4, h1_PCAL_ID_AllNOTRG4;

	H2F h2_FTOF1B_XY_AllNOTRG4;
	ArrayList<H1F> h1_FTOF1B_E_AllNOTRG4, h1_FTOF1B_Esqrt_AllNOTRG4, h1_FTOF1B_T_AllNOTRG4, h1_FTOF1B_ID_AllNOTRG4;
	ArrayList<H2F> h2_FTOF1B_E2_AllNOTRG4;

	H1F h1_delta_PCAL_FTOF_AllNOTRG4;

	H1F h1_MomAllEFFTRG4;
	ArrayList<H1F> h1_MomAllEFFTRG4_sector;

	
	/*CD histogram*/
	/*All tracks*/
	H1F h1_CD_MomAllRec;
	H2F h2_CD_ThetaPhiAllRec;
	
	H1F h1_CTOF_EAllRec;
	H1F h1_CND1_EAllRec;
	H1F h1_CND2_EAllRec;
	H1F h1_CND3_EAllRec;
	H2F h2_CTOFvsCND1AllRec;
	
	/*Tracks with first BIT*/
	H1F h1_CD_MomAllTRG1;
	H1F h1_CD_MomAllEFFTRG1;
	
	/* FT histograms */
	/* All reconstructed clusters */
	H1F h1_FT_E_AllREC, h1_FT_T_AllREC;
	H2F h2_FT_XY_AllREC;
	/* Reconstructed clusters with hodo */
	H1F h1_FT_E_HodoREC, h1_FT_T_HodoREC;
	H2F h2_FT_XY_HodoREC;
	H1F h1_FT_TCmTH1_HodoREC, h1_FT_TCmTH2_HodoREC, h1_FT_TH2mTH1_HodoREC;
	H1F h1_FT_RCmRH1_HodoREC,h1_FT_RCmRH2_HodoREC;
	/* Reconstruced clusters with one specific hodo layer */
	H1F h1_FT_h1E_Hodo1REC, h1_FT_h2E_Hodo2REC;

	/*
	 * Reconstructed clusters (all/with hodo/with one specific hodo layer) matched
	 * to a VTP cluster
	 */
	H1F h1_FT_E_AllMATCH, h1_FT_T_AllMATCH, h1_FT_E_HodoMATCH, h1_FT_T_HodoMATCH;
	H2F h2_FT_XY_AllMATCH, h2_FT_XY_HodoMATCH;
	H2F h2_FT_T_vs_VTP_T_AllMATCH, h2_FT_T_vs_VTP_T_HodoMATCH;
	H1F h1_FT_h1E_Hodo1MATCH, h1_FT_h2E_Hodo2MATCH;
	H1F h1_FT_h1E_Hodo1EFFMATCH, h1_FT_h2E_Hodo2EFFMATCH;
	H1F h1_FT_TCmTH1_HodoMATCH, h1_FT_TCmTH2_HodoMATCH, h1_FT_TH2mTH1_HodoMATCH;
	H1F h1_FT_RCmRH1_HodoMATCH,h1_FT_RCmRH2_HodoMATCH;
	
	
	/* Reconstructed clusters WITH HODO, matched to a VTP cluster WITH HODO */
	H1F h1_FT_E_HodoMATCHHODO, h1_FT_T_HodoMATCHHODO;
	H2F h2_FT_XY_HodoMATCHHODO;
	H2F h2_FT_T_vs_VTP_T_HodoMATCHHODO;
	H1F h1_FT_TCmTH1_HodoMATCHHODO, h1_FT_TCmTH2_HodoMATCHHODO, h1_FT_TH2mTH1_HodoMATCHHODO;
	H1F h1_FT_RCmRH1_HodoMATCHHODO,h1_FT_RCmRH2_HodoMATCHHODO;

	/*
	 * REconstructed clusters with one specific hodo layer, matched to a VTP cluster
	 * with that specific hodo layer
	 */
	H1F h1_FT_h1E_Hodo1MATCHHODO1, h1_FT_h2E_Hodo2MATCHHODO2;
	H1F h1_FT_h1E_Hodo1EFFMATCHHODO1, h1_FT_h2E_Hodo2EFFMATCHHODO2;

	/* Reconstructed clusters (all/with hodo) not matched to any VTP cluster */
	H2F h2_FT_XY_AllNOMATCH, h2_FT_XY_HodoNOMATCH;

	/* Reconstructed clusters with hodo not matched to HODO-VTP cluster */
	H2F h2_FT_XY_HodoNOMATCHHODO;
	H1F h1_FT_TCmTH1_HodoNOMATCHHODO, h1_FT_TCmTH2_HodoNOMATCHHODO, h1_FT_TH2mTH1_HodoNOMATCHHODO;
	H1F h1_FT_RCmRH1_HodoNOMATCHHODO,h1_FT_RCmRH2_HodoNOMATCHHODO;
	public double geteMinHisto() {
		return eMinHisto;
	}

	public void seteMinHisto(double eMinHisto) {
		this.eMinHisto = eMinHisto;
	}

	public double geteMaxHisto() {
		return eMaxHisto;
	}

	public void seteMaxHisto(double eMaxHisto) {
		this.eMaxHisto = eMaxHisto;
	}

	public GuiClass(AnalysisClass ana) {
		this.analysisClass = ana;
	}

	public void setupHistograms() {

		this.allH1F = new ArrayList<H1F>();
		this.allH2F = new ArrayList<H2F>();

		/* DoTriggerAnalysis histograms */
		hTriggerBits = new H1F("hTriggerBits", "hTriggerBits", 32, -0.5, 31.5);
		allH1F.add(hTriggerBits);
		hTriggerMultiplicity = new H1F("hTriggerMultiplicity", "hTriggerMultiplicity", 20, 0.5, 19.5);
		allH1F.add(hTriggerMultiplicity);
		hTriggerTimes = new H1F("hTriggerTimes", "hTriggerTimes", 100, -0.5, 99.5);
		allH1F.add(hTriggerTimes);
		hTriggerTimes2D = new H2F("hTriggerTimes2D", "hTriggerTimes2D", 100, -0.5, 99.5, 32, -0.5, 31.5);
		allH2F.add(hTriggerTimes2D);
		h2_VTP_PCAL_FTOF_time_all = new H2F("h2_VTP_PCAL_FTOF_time_all", "h2_VTP_PCAL_FTOF_time_all", 100, -0.5, 399.5, 100, -0.5, 399.5);
		allH2F.add(h2_VTP_PCAL_FTOF_time_all);
		h2_VTP_PCAL_FTOF_all = new H2F("h2_VTP_PCAL_FTOF_all", "h2_VTP_PCAL_FTOF_all", 70, -0.5, 69.5, 70, -0.5, 69.5);
		allH2F.add(h2_VTP_PCAL_FTOF_all);

		h1_VTP_delta_PCALFTOF_time_all = new ArrayList<H1F>();
		addSectorHistograms1D(h1_VTP_delta_PCALFTOF_time_all, "h1_VTP_delta_PCALFTOF_time_all", 200, -400, 400);

		/* DoParticleAnalysis histograms */

		/* All Good Tracks */
		h1_MomAllREC = new H1F("h1_MomAllREC", "h1_MomAllREC", 100, 0, 10.);
		allH1F.add(h1_MomAllREC);
		h1_MomQPREC = new H1F("h1_MomQPREC", "h1_MomQPREC", 100, 0, 10.);
		allH1F.add(h1_MomQPREC);
		h1_MomQMREC = new H1F("h1_MomQMREC", "h1_MomQMREC", 100, 0, 10.);
		allH1F.add(h1_MomQMREC);

		h1_MomAllREC_sector = new ArrayList<H1F>();
		h1_MomQPREC_sector = new ArrayList<H1F>();
		h1_MomQMREC_sector = new ArrayList<H1F>();
		addSectorHistograms1D(h1_MomAllREC_sector, "h1_MomAllREC", 100, 0, 10.);
		addSectorHistograms1D(h1_MomQPREC_sector, "h1_MomQPREC", 100, 0, 10.);
		addSectorHistograms1D(h1_MomQMREC_sector, "h1_MomQMREC", 100, 0, 10.);

		h2_ThetaPhiAllREC = new H2F("h2_ThetaPhiAllREC", "h2_ThetaPhiAllREC", 100, -180., 180., 100, 0, 60.);
		allH2F.add(h2_ThetaPhiAllREC);
		h2_ThetaPhiQPREC = new H2F("h2_ThetaPhiQPREC", "h2_ThetaPhiQPREC", 100, -180., 180., 100, 0, 60.);
		allH2F.add(h2_ThetaPhiQPREC);
		h2_ThetaPhiQMREC = new H2F("h2_ThetaPhiQMREC", "h2_ThetaPhiQMREC", 100, -180., 180., 100, 0, 60.);
		allH2F.add(h2_ThetaPhiQMREC);

		h2_ThetaPAllREC_sector = new ArrayList<H2F>();
		h2_ThetaPQPREC_sector = new ArrayList<H2F>();
		h2_ThetaPQMREC_sector = new ArrayList<H2F>();
		addSectorHistograms2D(h2_ThetaPAllREC_sector, "h2_ThetaPAllREC", 100, -180., 180., 100, 0, 60.);
		addSectorHistograms2D(h2_ThetaPQPREC_sector, "h2_ThetaPQPREC", 100, -180., 180., 100, 0, 60.);
		addSectorHistograms2D(h2_ThetaPQMREC_sector, "h2_ThetaPQMREC", 100, -180., 180., 100, 0, 60.);

		h2_PCAL_XY_AllREC = new H2F("h2_PCAL_XY_AllREC", "h2_PCAL_XY_AllREC", 200, -600, 600, 200, -600, 600);
		allH2F.add(h2_PCAL_XY_AllREC);
		h1_PCAL_E_AllREC = new ArrayList<H1F>();
		h1_PCAL_ID_AllREC = new ArrayList<H1F>();
		addSectorHistograms1D(h1_PCAL_E_AllREC, "h1_PCAL_E_AllREC", 100, 0, 0.1);
		addSectorHistograms1D(h1_PCAL_ID_AllREC, "h1_PCAL_ID_AllREC", 80, -0.5, 79.5);

		h2_FTOF1B_XY_AllREC = new H2F("h2_FTOF1B_XY_AllREC", "h2_FTOF1B_XY_AllREC", 200, -600, 600, 200, -600, 600);
		allH2F.add(h2_FTOF1B_XY_AllREC);
		h1_FTOF1B_E_AllREC = new ArrayList<H1F>();
		h1_FTOF1B_Esqrt_AllREC = new ArrayList<H1F>();
		h1_FTOF1B_T_AllREC = new ArrayList<H1F>();
		h1_FTOF1B_ID_AllREC = new ArrayList<H1F>();
		h2_FTOF1B_E2_AllREC = new ArrayList<H2F>();
		addSectorHistograms1D(h1_FTOF1B_E_AllREC, "h1_FTOF1B_E_AllREC", 100, 0, 20);
		addSectorHistograms1D(h1_FTOF1B_Esqrt_AllREC, "h1_FTOF1B_Esqrt_AllREC", 100, 0, 20);
		addSectorHistograms1D(h1_FTOF1B_T_AllREC, "h1_FTOF1B_T_AllREC", 600, 0, 600.);
		addSectorHistograms1D(h1_FTOF1B_ID_AllREC, "h1_FTOF1B_ID_AllREC", 80, -0.5, 79.5);
		addSectorHistograms2D(h2_FTOF1B_E2_AllREC, "h2_FTOF1B_E2_AllREC", 100, 0, 20., 100, 0, 20.);

		h1_delta_PCAL_FTOF_AllREC = new H1F("h1_delta_PCAL_FTOF_AllREC", "h1_delta_PCAL_FTOF_AllREC", 41, -20.5, 20.5);
		allH1F.add(h1_delta_PCAL_FTOF_AllREC);
		
		
		
		h2_FTOF1BT_Mom_AllREC = new ArrayList<H2F>();
		addSectorHistograms2D(h2_FTOF1BT_Mom_AllREC, "h2_FTOF1BT_Mom_AllREC", 100, 0, 10., 100,0, 400.);

		h1_VTPPCAL_E_AllREC = new ArrayList<H1F>();
		addSectorHistograms1D(h1_VTPPCAL_E_AllREC,"h1_VTPPCAL_E_AllREC",100,0,100.);
		
		
		h2_VTPCAL_E_ClusterE_AllREC=new H2F("h2_VTPCAL_E_ClusterE_AllREC","h2_VTPCAL_E_ClusterE_AllREC",100,0,100,100,0,100);
		allH2F.add(h2_VTPCAL_E_ClusterE_AllREC);
		/* Good tracks with TRG1 bit or not */
		h1_MomAllTRG1 = new H1F("h1_MomAllTRG1", "h1_MomAllTRG1", 100, 0, 10.);
		allH1F.add(h1_MomAllTRG1);
		h1_MomQPTRG1 = new H1F("h1_MomQPTRG1", "h1_MomQPTRG1", 100, 0, 10.);
		allH1F.add(h1_MomQPTRG1);
		h1_MomQMTRG1 = new H1F("h1_MomQMTRG1", "h1_MomQMTRG1", 100, 0, 10.);
		allH1F.add(h1_MomQMTRG1);

		h1_MomAllTRG1_sector = new ArrayList<H1F>();
		h1_MomQPTRG1_sector = new ArrayList<H1F>();
		h1_MomQMTRG1_sector = new ArrayList<H1F>();
		addSectorHistograms1D(h1_MomAllTRG1_sector, "h1_MomAllTRG1", 100, 0, 10.);
		addSectorHistograms1D(h1_MomQPTRG1_sector, "h1_MomQPTRG1", 100, 0, 10.);
		addSectorHistograms1D(h1_MomQMTRG1_sector, "h1_MomQMTRG1", 100, 0, 10.);

		h2_ThetaPhiAllTRG1 = new H2F("h2_ThetaPhiAllTRG1", "h2_ThetaPhiAllTRG1", 100, -180., 180., 100, 0, 60.);
		allH2F.add(h2_ThetaPhiAllTRG1);
		h2_ThetaPhiQPTRG1 = new H2F("h2_ThetaPhiQPTRG1", "h2_ThetaPhiQPTRG1", 100, -180., 180., 100, 0, 60.);
		allH2F.add(h2_ThetaPhiQPTRG1);
		h2_ThetaPhiQMTRG1 = new H2F("h2_ThetaPhiQMTRG1", "h2_ThetaPhiQMTRG1", 100, -180., 180., 100, 0, 60.);
		allH2F.add(h2_ThetaPhiQMTRG1);

		h2_ThetaPAllTRG1_sector = new ArrayList<H2F>();
		h2_ThetaPQPTRG1_sector = new ArrayList<H2F>();
		h2_ThetaPQMTRG1_sector = new ArrayList<H2F>();
		addSectorHistograms2D(h2_ThetaPAllTRG1_sector, "h2_ThetaPAllTRG1", 100, -180., 180., 100, 0, 60.);
		addSectorHistograms2D(h2_ThetaPQPTRG1_sector, "h2_ThetaPQPTRG1", 100, -180., 180., 100, 0, 60.);
		addSectorHistograms2D(h2_ThetaPQMTRG1_sector, "h2_ThetaPQMTRG1", 100, -180., 180., 100, 0, 60.);

		h2_PCAL_XY_AllTRG1 = new H2F("h2_PCAL_XY_AllTRG1", "h2_PCAL_XY_AllTRG1", 200, -600, 600, 200, -600, 600);
		allH2F.add(h2_PCAL_XY_AllTRG1);
		h1_PCAL_E_AllTRG1 = new ArrayList<H1F>();
		h1_PCAL_ID_AllTRG1 = new ArrayList<H1F>();
		addSectorHistograms1D(h1_PCAL_E_AllTRG1, "h1_PCAL_E_AllTRG1", 100, 0, 0.1);
		addSectorHistograms1D(h1_PCAL_ID_AllTRG1, "h1_PCAL_ID_AllTRG1", 80, -0.5, 79.5);

		h2_FTOF1B_XY_AllTRG1 = new H2F("h2_FTOF1B_XY_AllTRG1", "h2_FTOF1B_XY_AllTRG1", 200, -600, 600, 200, -600, 600);
		allH2F.add(h2_FTOF1B_XY_AllTRG1);
		h1_FTOF1B_E_AllTRG1 = new ArrayList<H1F>();
		h1_FTOF1B_Esqrt_AllTRG1 = new ArrayList<H1F>();
		h1_FTOF1B_T_AllTRG1 = new ArrayList<H1F>();
		h1_FTOF1B_ID_AllTRG1 = new ArrayList<H1F>();
		h2_FTOF1B_E2_AllTRG1 = new ArrayList<H2F>();
		addSectorHistograms1D(h1_FTOF1B_E_AllTRG1, "h1_FTOF1B_E_AllTRG1", 100, 0, 20);
		addSectorHistograms1D(h1_FTOF1B_Esqrt_AllTRG1, "h1_FTOF1B_Esqrt_AllTRG1", 100, 0, 20);
		addSectorHistograms1D(h1_FTOF1B_T_AllTRG1, "h1_FTOF1B_T_AllTRG1", 600, 0, 600.);
		addSectorHistograms1D(h1_FTOF1B_ID_AllTRG1, "h1_FTOF1B_ID_AllTRG1", 80, -0.5, 79.5);
		addSectorHistograms2D(h2_FTOF1B_E2_AllTRG1, "h2_FTOF1B_E2_AllTRG1", 100, 0, 20., 100, 0, 20.);

		h1_delta_PCAL_FTOF_AllTRG1 = new H1F("h1_delta_PCAL_FTOF_AllTRG1", "h1_delta_PCAL_FTOF_AllTRG1", 41, -20.5, 20.5);
		allH1F.add(h1_delta_PCAL_FTOF_AllTRG1);

		h1_MomAllNOTRG1 = new H1F("h1_MomAllNOTRG1", "h1_MomAllNOTRG1", 100, 0, 10.);
		allH1F.add(h1_MomAllNOTRG1);
		h1_MomQPNOTRG1 = new H1F("h1_MomQPNOTRG1", "h1_MomQPNOTRG1", 100, 0, 10.);
		allH1F.add(h1_MomQPNOTRG1);
		h1_MomQMNOTRG1 = new H1F("h1_MomQMNOTRG1", "h1_MomQMNOTRG1", 100, 0, 10.);
		allH1F.add(h1_MomQMNOTRG1);

		h1_MomAllNOTRG1_sector = new ArrayList<H1F>();
		h1_MomQPNOTRG1_sector = new ArrayList<H1F>();
		h1_MomQMNOTRG1_sector = new ArrayList<H1F>();
		addSectorHistograms1D(h1_MomAllNOTRG1_sector, "h1_MomAllNOTRG1", 100, 0, 10.);
		addSectorHistograms1D(h1_MomQPNOTRG1_sector, "h1_MomQPNOTRG1", 100, 0, 10.);
		addSectorHistograms1D(h1_MomQMNOTRG1_sector, "h1_MomQMNOTRG1", 100, 0, 10.);

		h2_ThetaPhiAllNOTRG1 = new H2F("h2_ThetaPhiAllNOTRG1", "h2_ThetaPhiAllNOTRG1", 100, -180., 180., 100, 0, 60.);
		allH2F.add(h2_ThetaPhiAllNOTRG1);
		h2_ThetaPhiQPNOTRG1 = new H2F("h2_ThetaPhiQPNOTRG1", "h2_ThetaPhiQPNOTRG1", 100, -180., 180., 100, 0, 60.);
		allH2F.add(h2_ThetaPhiQPNOTRG1);
		h2_ThetaPhiQMNOTRG1 = new H2F("h2_ThetaPhiQMNOTRG1", "h2_ThetaPhiQMNOTRG1", 100, -180., 180., 100, 0, 60.);
		allH2F.add(h2_ThetaPhiQMNOTRG1);

		h2_ThetaPAllNOTRG1_sector = new ArrayList<H2F>();
		h2_ThetaPQPNOTRG1_sector = new ArrayList<H2F>();
		h2_ThetaPQMNOTRG1_sector = new ArrayList<H2F>();
		addSectorHistograms2D(h2_ThetaPAllNOTRG1_sector, "h2_ThetaPAllNOTRG1", 100, 0., 10., 100, 0, 60.);
		addSectorHistograms2D(h2_ThetaPQPNOTRG1_sector, "h2_ThetaPQPNOTRG1", 100, 0., 10., 100, 0, 60.);
		addSectorHistograms2D(h2_ThetaPQMNOTRG1_sector, "h2_ThetaPQMNOTRG1", 100, 0., 10., 100, 0, 60.);

		h2_PCAL_XY_AllNOTRG1 = new H2F("h2_PCAL_XY_AllNOTRG1", "h2_PCAL_XY_AllNOTRG1", 200, -600, 600, 200, -600, 600);
		allH2F.add(h2_PCAL_XY_AllNOTRG1);
		h1_PCAL_E_AllNOTRG1 = new ArrayList<H1F>();
		h1_PCAL_ID_AllNOTRG1 = new ArrayList<H1F>();
		addSectorHistograms1D(h1_PCAL_E_AllNOTRG1, "h1_PCAL_E_AllNOTRG1", 100, 0, 0.1);
		addSectorHistograms1D(h1_PCAL_ID_AllNOTRG1, "h1_PCAL_ID_AllNOTRG1", 80, -0.5, 79.5);

		h2_FTOF1B_XY_AllNOTRG1 = new H2F("h2_FTOF1B_XY_AllNOTRG1", "h2_FTOF1B_XY_AllNOTRG1", 200, -600, 600, 200, -600, 600);
		allH2F.add(h2_FTOF1B_XY_AllNOTRG1);
		h1_FTOF1B_E_AllNOTRG1 = new ArrayList<H1F>();
		h1_FTOF1B_Esqrt_AllNOTRG1 = new ArrayList<H1F>();
		h1_FTOF1B_T_AllNOTRG1 = new ArrayList<H1F>();
		h1_FTOF1B_ID_AllNOTRG1 = new ArrayList<H1F>();
		h2_FTOF1B_E2_AllNOTRG1 = new ArrayList<H2F>();
		addSectorHistograms1D(h1_FTOF1B_E_AllNOTRG1, "h1_FTOF1B_E_AllNOTRG1", 100, 0, 20);
		addSectorHistograms1D(h1_FTOF1B_Esqrt_AllNOTRG1, "h1_FTOF1B_Esqrt_AllNOTRG1", 100, 0, 20);
		addSectorHistograms1D(h1_FTOF1B_T_AllNOTRG1, "h1_FTOF1B_T_AllNOTRG1", 600, 0, 600.);
		addSectorHistograms1D(h1_FTOF1B_ID_AllNOTRG1, "h1_FTOF1B_ID_AllNOTRG1", 80, -0.5, 79.5);
		addSectorHistograms2D(h2_FTOF1B_E2_AllNOTRG1, "h2_FTOF1B_E2_AllNOTRG1", 100, 0, 20., 100, 0, 20.);

		h1_delta_PCAL_FTOF_AllNOTRG1 = new H1F("h1_delta_PCAL_FTOF_AllNOTRG1", "h1_delta_PCAL_FTOF_AllNOTRG1", 41, -20.5, 20.5);
		allH1F.add(h1_delta_PCAL_FTOF_AllNOTRG1);
		
		h1_VTPPCAL_E_AllNOTRG1 = new ArrayList<H1F>();
		addSectorHistograms1D(h1_VTPPCAL_E_AllNOTRG1,"h1_VTPPCAL_E_AllNOTRG1",100,0,100.);

		/* Good tracks with TRG2 bit or not */
		h1_MomAllTRG2 = new H1F("h1_MomAllTRG2", "h1_MomAllTRG2", 100, 0, 10.);
		allH1F.add(h1_MomAllTRG2);
		h1_MomQPTRG2 = new H1F("h1_MomQPTRG2", "h1_MomQPTRG2", 100, 0, 10.);
		allH1F.add(h1_MomQPTRG2);
		h1_MomQMTRG2 = new H1F("h1_MomQMTRG2", "h1_MomQMTRG2", 100, 0, 10.);
		allH1F.add(h1_MomQMTRG2);

		h1_MomAllTRG2_sector = new ArrayList<H1F>();
		h1_MomQPTRG2_sector = new ArrayList<H1F>();
		h1_MomQMTRG2_sector = new ArrayList<H1F>();
		addSectorHistograms1D(h1_MomAllTRG2_sector, "h1_MomAllTRG2", 100, 0, 10.);
		addSectorHistograms1D(h1_MomQPTRG2_sector, "h1_MomQPTRG2", 100, 0, 10.);
		addSectorHistograms1D(h1_MomQMTRG2_sector, "h1_MomQMTRG2", 100, 0, 10.);

		h2_ThetaPhiAllTRG2 = new H2F("h2_ThetaPhiAllTRG2", "h2_ThetaPhiAllTRG2", 100, -180., 180., 100, 0, 60.);
		allH2F.add(h2_ThetaPhiAllTRG2);
		h2_ThetaPhiQPTRG2 = new H2F("h2_ThetaPhiQPTRG2", "h2_ThetaPhiQPTRG2", 100, -180., 180., 100, 0, 60.);
		allH2F.add(h2_ThetaPhiQPTRG2);
		h2_ThetaPhiQMTRG2 = new H2F("h2_ThetaPhiQMTRG2", "h2_ThetaPhiQMTRG2", 100, -180., 180., 100, 0, 60.);
		allH2F.add(h2_ThetaPhiQMTRG2);

		h2_ThetaPAllTRG2_sector = new ArrayList<H2F>();
		h2_ThetaPQPTRG2_sector = new ArrayList<H2F>();
		h2_ThetaPQMTRG2_sector = new ArrayList<H2F>();
		addSectorHistograms2D(h2_ThetaPAllTRG2_sector, "h2_ThetaPAllTRG2", 100, 0., 10., 100, 0, 60.);
		addSectorHistograms2D(h2_ThetaPQPTRG2_sector, "h2_ThetaPQPTRG2", 100, 0., 10., 100, 0, 60.);
		addSectorHistograms2D(h2_ThetaPQMTRG2_sector, "h2_ThetaPQMTRG2", 100, 0., 10., 100, 0, 60.);

		h2_PCAL_XY_AllTRG2 = new H2F("h2_PCAL_XY_AllTRG2", "h2_PCAL_XY_AllTRG2", 200, -600, 600, 200, -600, 600);
		allH2F.add(h2_PCAL_XY_AllTRG2);
		h1_PCAL_E_AllTRG2 = new ArrayList<H1F>();
		h1_PCAL_ID_AllTRG2 = new ArrayList<H1F>();
		addSectorHistograms1D(h1_PCAL_E_AllTRG2, "h1_PCAL_E_AllTRG2", 100, 0, 0.1);
		addSectorHistograms1D(h1_PCAL_ID_AllTRG2, "h1_PCAL_ID_AllTRG2", 80, -0.5, 79.5);

		h2_FTOF1B_XY_AllTRG2 = new H2F("h2_FTOF1B_XY_AllTRG2", "h2_FTOF1B_XY_AllTRG2", 200, -600, 600, 200, -600, 600);
		allH2F.add(h2_FTOF1B_XY_AllTRG2);
		h1_FTOF1B_E_AllTRG2 = new ArrayList<H1F>();
		h1_FTOF1B_Esqrt_AllTRG2 = new ArrayList<H1F>();
		h1_FTOF1B_T_AllTRG2 = new ArrayList<H1F>();
		h1_FTOF1B_ID_AllTRG2 = new ArrayList<H1F>();
		h2_FTOF1B_E2_AllTRG2 = new ArrayList<H2F>();
		addSectorHistograms1D(h1_FTOF1B_E_AllTRG2, "h1_FTOF1B_E_AllTRG2", 100, 0, 20);
		addSectorHistograms1D(h1_FTOF1B_Esqrt_AllTRG2, "h1_FTOF1B_Esqrt_AllTRG2", 100, 0, 20);
		addSectorHistograms1D(h1_FTOF1B_T_AllTRG2, "h1_FTOF1B_T_AllTRG2", 600, 0, 600.);
		addSectorHistograms1D(h1_FTOF1B_ID_AllTRG2, "h1_FTOF1B_ID_AllTRG2", 80, -0.5, 79.5);
		addSectorHistograms2D(h2_FTOF1B_E2_AllTRG2, "h2_FTOF1B_E2_AllTRG2", 100, 0, 20., 100, 0, 20.);

		h1_delta_PCAL_FTOF_AllTRG2 = new H1F("h1_delta_PCAL_FTOF_AllTRG2", "h1_delta_PCAL_FTOF_AllTRG2", 41, -20.5, 20.5);
		allH1F.add(h1_delta_PCAL_FTOF_AllTRG2);

		h1_MomAllNOTRG2 = new H1F("h1_MomAllNOTRG2", "h1_MomAllNOTRG2", 100, 0, 10.);
		allH1F.add(h1_MomAllNOTRG2);
		h1_MomQPNOTRG2 = new H1F("h1_MomQPNOTRG2", "h1_MomQPNOTRG2", 100, 0, 10.);
		allH1F.add(h1_MomQPNOTRG2);
		h1_MomQMNOTRG2 = new H1F("h1_MomQMNOTRG2", "h1_MomQMNOTRG2", 100, 0, 10.);
		allH1F.add(h1_MomQMNOTRG2);

		h1_MomAllNOTRG2_sector = new ArrayList<H1F>();
		h1_MomQPNOTRG2_sector = new ArrayList<H1F>();
		h1_MomQMNOTRG2_sector = new ArrayList<H1F>();
		addSectorHistograms1D(h1_MomAllNOTRG2_sector, "h1_MomAllNOTRG2", 100, 0, 10.);
		addSectorHistograms1D(h1_MomQPNOTRG2_sector, "h1_MomQPNOTRG2", 100, 0, 10.);
		addSectorHistograms1D(h1_MomQMNOTRG2_sector, "h1_MomQMNOTRG2", 100, 0, 10.);

		h2_ThetaPhiAllNOTRG2 = new H2F("h2_ThetaPhiAllNOTRG2", "h2_ThetaPhiAllNOTRG2", 100, -180., 180., 100, 0, 60.);
		allH2F.add(h2_ThetaPhiAllNOTRG2);
		h2_ThetaPhiQPNOTRG2 = new H2F("h2_ThetaPhiQPNOTRG2", "h2_ThetaPhiQPNOTRG2", 100, -180., 180., 100, 0, 60.);
		allH2F.add(h2_ThetaPhiQPNOTRG2);
		h2_ThetaPhiQMNOTRG2 = new H2F("h2_ThetaPhiQMNOTRG2", "h2_ThetaPhiQMNOTRG2", 100, -180., 180., 100, 0, 60.);
		allH2F.add(h2_ThetaPhiQMNOTRG2);

		h2_ThetaPAllNOTRG2_sector = new ArrayList<H2F>();
		h2_ThetaPQPNOTRG2_sector = new ArrayList<H2F>();
		h2_ThetaPQMNOTRG2_sector = new ArrayList<H2F>();
		addSectorHistograms2D(h2_ThetaPAllNOTRG2_sector, "h2_ThetaPAllNOTRG2", 100, 0., 10., 100, 0, 60.);
		addSectorHistograms2D(h2_ThetaPQPNOTRG2_sector, "h2_ThetaPQPNOTRG2", 100, 0., 10., 100, 0, 60.);
		addSectorHistograms2D(h2_ThetaPQMNOTRG2_sector, "h2_ThetaPQMNOTRG2", 100, 0., 10., 100, 0, 60.);

		h2_PCAL_XY_AllNOTRG2 = new H2F("h2_PCAL_XY_AllNOTRG2", "h2_PCAL_XY_AllNOTRG2", 200, -600, 600, 200, -600, 600);
		allH2F.add(h2_PCAL_XY_AllNOTRG2);
		h2_PCAL_XY_AllTRG1NOTRG2 = new H2F("h2_PCAL_XY_AllTRG1NOTRG2", "h2_PCAL_XY_AllTRG1NOTRG2", 200, -600, 600, 200, -600, 600);
		allH2F.add(h2_PCAL_XY_AllTRG1NOTRG2);
		
		h1_PCAL_E_AllNOTRG2 = new ArrayList<H1F>();
		h1_PCAL_ID_AllNOTRG2 = new ArrayList<H1F>();
		h1_PCAL_E_AllTRG1NOTRG2= new ArrayList<H1F>(); //special for this bit
		addSectorHistograms1D(h1_PCAL_E_AllNOTRG2, "h1_PCAL_E_AllNOTRG2", 100, 0, 0.1);
		addSectorHistograms1D(h1_PCAL_ID_AllNOTRG2, "h1_PCAL_ID_AllNOTRG2", 80, -0.5, 79.5);
		addSectorHistograms1D(h1_PCAL_E_AllTRG1NOTRG2, "h1_PCAL_E_AllTRG1NOTRG2", 100, 0, 0.1); //special for this bit

		h2_FTOF1B_XY_AllNOTRG2 = new H2F("h2_FTOF1B_XY_AllNOTRG2", "h2_FTOF1B_XY_AllNOTRG2", 200, -600, 600, 200, -600, 600);
		allH2F.add(h2_FTOF1B_XY_AllNOTRG2);
		h1_FTOF1B_E_AllNOTRG2 = new ArrayList<H1F>();
		h1_FTOF1B_Esqrt_AllNOTRG2 = new ArrayList<H1F>();
		h1_FTOF1B_T_AllNOTRG2 = new ArrayList<H1F>();
		h1_FTOF1B_ID_AllNOTRG2 = new ArrayList<H1F>();
		h2_FTOF1B_E2_AllNOTRG2 = new ArrayList<H2F>();
		addSectorHistograms1D(h1_FTOF1B_E_AllNOTRG2, "h1_FTOF1B_E_AllNOTRG2", 100, 0, 20);
		addSectorHistograms1D(h1_FTOF1B_Esqrt_AllNOTRG2, "h1_FTOF1B_Esqrt_AllNOTRG2", 100, 0, 20);
		addSectorHistograms1D(h1_FTOF1B_T_AllNOTRG2, "h1_FTOF1B_T_AllNOTRG2", 600, 0, 600.);
		addSectorHistograms1D(h1_FTOF1B_ID_AllNOTRG2, "h1_FTOF1B_ID_AllNOTRG2", 80, -0.5, 79.5);
		addSectorHistograms2D(h2_FTOF1B_E2_AllNOTRG2, "h2_FTOF1B_E2_AllNOTRG2", 100, 0, 20., 100, 0, 20.);

		h1_delta_PCAL_FTOF_AllNOTRG2 = new H1F("h1_delta_PCAL_FTOF_AllNOTRG2", "h1_delta_PCAL_FTOF_AllNOTRG2", 41, -20.5, 20.5);
		allH1F.add(h1_delta_PCAL_FTOF_AllNOTRG2);
		
		h1_VTPPCAL_E_AllNOTRG2 = new ArrayList<H1F>();
		addSectorHistograms1D(h1_VTPPCAL_E_AllNOTRG2,"h1_VTPPCAL_E_AllNOTRG2",100,0,100.);
		
		h1_VTPPCAL_E_AllTRG1NOTRG2 = new ArrayList<H1F>();
		addSectorHistograms1D(h1_VTPPCAL_E_AllTRG1NOTRG2,"h1_VTPPCAL_E_AllTRG1NOTRG2",100,0,100.);

		/* Good tracks with TRG3 bit or not */
		h1_MomAllTRG3 = new H1F("h1_MomAllTRG3", "h1_MomAllTRG3", 100, 0, 10.);
		allH1F.add(h1_MomAllTRG3);
		h1_MomQPTRG3 = new H1F("h1_MomQPTRG3", "h1_MomQPTRG3", 100, 0, 10.);
		allH1F.add(h1_MomQPTRG3);
		h1_MomQMTRG3 = new H1F("h1_MomQMTRG3", "h1_MomQMTRG3", 100, 0, 10.);
		allH1F.add(h1_MomQMTRG3);

		h1_MomAllTRG3_sector = new ArrayList<H1F>();
		h1_MomQPTRG3_sector = new ArrayList<H1F>();
		h1_MomQMTRG3_sector = new ArrayList<H1F>();
		addSectorHistograms1D(h1_MomAllTRG3_sector, "h1_MomAllTRG3", 100, 0, 10.);
		addSectorHistograms1D(h1_MomQPTRG3_sector, "h1_MomQPTRG3", 100, 0, 10.);
		addSectorHistograms1D(h1_MomQMTRG3_sector, "h1_MomQMTRG3", 100, 0, 10.);

		h2_ThetaPhiAllTRG3 = new H2F("h2_ThetaPhiAllTRG3", "h2_ThetaPhiAllTRG3", 100, -180., 180., 100, 0, 60.);
		allH2F.add(h2_ThetaPhiAllTRG3);
		h2_ThetaPhiQPTRG3 = new H2F("h2_ThetaPhiQPTRG3", "h2_ThetaPhiQPTRG3", 100, -180., 180., 100, 0, 60.);
		allH2F.add(h2_ThetaPhiQPTRG3);
		h2_ThetaPhiQMTRG3 = new H2F("h2_ThetaPhiQMTRG3", "h2_ThetaPhiQMTRG3", 100, -180., 180., 100, 0, 60.);
		allH2F.add(h2_ThetaPhiQMTRG3);

		h2_ThetaPAllTRG3_sector = new ArrayList<H2F>();
		h2_ThetaPQPTRG3_sector = new ArrayList<H2F>();
		h2_ThetaPQMTRG3_sector = new ArrayList<H2F>();
		addSectorHistograms2D(h2_ThetaPAllTRG3_sector, "h2_ThetaPAllTRG3", 100, 0, 10., 100, 0, 60.);
		addSectorHistograms2D(h2_ThetaPQPTRG3_sector, "h2_ThetaPQPTRG3", 100, 0, 10., 100, 0, 60.);
		addSectorHistograms2D(h2_ThetaPQMTRG3_sector, "h2_ThetaPQMTRG3", 100, 0, 10., 100, 0, 60.);

		h2_PCAL_XY_AllTRG3 = new H2F("h2_PCAL_XY_AllTRG3", "h2_PCAL_XY_AllTRG3", 200, -600, 600, 200, -600, 600);
		allH2F.add(h2_PCAL_XY_AllTRG3);
		h1_PCAL_E_AllTRG3 = new ArrayList<H1F>();
		h1_PCAL_ID_AllTRG3 = new ArrayList<H1F>();
		addSectorHistograms1D(h1_PCAL_E_AllTRG3, "h1_PCAL_E_AllTRG3", 100, 0, 0.1);
		addSectorHistograms1D(h1_PCAL_ID_AllTRG3, "h1_PCAL_ID_AllTRG3", 80, -0.5, 79.5);

		h2_FTOF1B_XY_AllTRG3 = new H2F("h2_FTOF1B_XY_AllTRG3", "h2_FTOF1B_XY_AllTRG3", 200, -600, 600, 200, -600, 600);
		allH2F.add(h2_FTOF1B_XY_AllTRG3);
		h1_FTOF1B_E_AllTRG3 = new ArrayList<H1F>();
		h1_FTOF1B_Esqrt_AllTRG3 = new ArrayList<H1F>();
		h1_FTOF1B_T_AllTRG3 = new ArrayList<H1F>();
		h1_FTOF1B_ID_AllTRG3 = new ArrayList<H1F>();
		h2_FTOF1B_E2_AllTRG3 = new ArrayList<H2F>();
		addSectorHistograms1D(h1_FTOF1B_E_AllTRG3, "h1_FTOF1B_E_AllTRG3", 100, 0, 20);
		addSectorHistograms1D(h1_FTOF1B_Esqrt_AllTRG3, "h1_FTOF1B_Esqrt_AllTRG3", 100, 0, 20);
		addSectorHistograms1D(h1_FTOF1B_T_AllTRG3, "h1_FTOF1B_T_AllTRG3", 600, 0, 600.);
		addSectorHistograms1D(h1_FTOF1B_ID_AllTRG3, "h1_FTOF1B_ID_AllTRG3", 80, -0.5, 79.5);
		addSectorHistograms2D(h2_FTOF1B_E2_AllTRG3, "h2_FTOF1B_E2_AllTRG3", 100, 0, 20., 100, 0, 20.);

		h1_delta_PCAL_FTOF_AllTRG3 = new H1F("h1_delta_PCAL_FTOF_AllTRG3", "h1_delta_PCAL_FTOF_AllTRG3", 41, -20.5, 20.5);
		allH1F.add(h1_delta_PCAL_FTOF_AllTRG3);

		h1_MomAllNOTRG3 = new H1F("h1_MomAllNOTRG3", "h1_MomAllNOTRG3", 100, 0, 10.);
		allH1F.add(h1_MomAllNOTRG3);
		h1_MomQPNOTRG3 = new H1F("h1_MomQPNOTRG3", "h1_MomQPNOTRG3", 100, 0, 10.);
		allH1F.add(h1_MomQPNOTRG3);
		h1_MomQMNOTRG3 = new H1F("h1_MomQMNOTRG3", "h1_MomQMNOTRG3", 100, 0, 10.);
		allH1F.add(h1_MomQMNOTRG3);

		h1_MomAllNOTRG3_sector = new ArrayList<H1F>();
		h1_MomQPNOTRG3_sector = new ArrayList<H1F>();
		h1_MomQMNOTRG3_sector = new ArrayList<H1F>();
		addSectorHistograms1D(h1_MomAllNOTRG3_sector, "h1_MomAllNOTRG3", 100, 0, 10.);
		addSectorHistograms1D(h1_MomQPNOTRG3_sector, "h1_MomQPNOTRG3", 100, 0, 10.);
		addSectorHistograms1D(h1_MomQMNOTRG3_sector, "h1_MomQMNOTRG3", 100, 0, 10.);

		h2_ThetaPhiAllNOTRG3 = new H2F("h2_ThetaPhiAllNOTRG3", "h2_ThetaPhiAllNOTRG3", 100, -180., 180., 100, 0, 60.);
		allH2F.add(h2_ThetaPhiAllNOTRG3);
		h2_ThetaPhiQPNOTRG3 = new H2F("h2_ThetaPhiQPNOTRG3", "h2_ThetaPhiQPNOTRG3", 100, -180., 180., 100, 0, 60.);
		allH2F.add(h2_ThetaPhiQPNOTRG3);
		h2_ThetaPhiQMNOTRG3 = new H2F("h2_ThetaPhiQMNOTRG3", "h2_ThetaPhiQMNOTRG3", 100, -180., 180., 100, 0, 60.);
		allH2F.add(h2_ThetaPhiQMNOTRG3);

		h2_ThetaPAllNOTRG3_sector = new ArrayList<H2F>();
		h2_ThetaPQPNOTRG3_sector = new ArrayList<H2F>();
		h2_ThetaPQMNOTRG3_sector = new ArrayList<H2F>();
		addSectorHistograms2D(h2_ThetaPAllNOTRG3_sector, "h2_ThetaPAllNOTRG3", 100, 0., 10., 100, 0, 60.);
		addSectorHistograms2D(h2_ThetaPQPNOTRG3_sector, "h2_ThetaPQPNOTRG3", 100, 0., 10., 100, 0, 60.);
		addSectorHistograms2D(h2_ThetaPQMNOTRG3_sector, "h2_ThetaPQMNOTRG3", 100, 0., 10., 100, 0, 60.);

		h2_PCAL_XY_AllNOTRG3 = new H2F("h2_PCAL_XY_AllNOTRG3", "h2_PCAL_XY_AllNOTRG3", 200, -600, 600, 200, -600, 600);
		allH2F.add(h2_PCAL_XY_AllNOTRG3);
		h1_PCAL_E_AllNOTRG3 = new ArrayList<H1F>();
		h1_PCAL_ID_AllNOTRG3 = new ArrayList<H1F>();
		addSectorHistograms1D(h1_PCAL_E_AllNOTRG3, "h1_PCAL_E_AllNOTRG3", 100, 0, 0.1);
		addSectorHistograms1D(h1_PCAL_ID_AllNOTRG3, "h1_PCAL_ID_AllNOTRG3", 80, -0.5, 79.5);

		h2_FTOF1B_XY_AllNOTRG3 = new H2F("h2_FTOF1B_XY_AllNOTRG3", "h2_FTOF1B_XY_AllNOTRG3", 200, -600, 600, 200, -600, 600);
		allH2F.add(h2_FTOF1B_XY_AllNOTRG3);
		h1_FTOF1B_E_AllNOTRG3 = new ArrayList<H1F>();
		h1_FTOF1B_Esqrt_AllNOTRG3 = new ArrayList<H1F>();
		h1_FTOF1B_T_AllNOTRG3 = new ArrayList<H1F>();
		h1_FTOF1B_ID_AllNOTRG3 = new ArrayList<H1F>();
		h2_FTOF1B_E2_AllNOTRG3 = new ArrayList<H2F>();
		addSectorHistograms1D(h1_FTOF1B_E_AllNOTRG3, "h1_FTOF1B_E_AllNOTRG3", 100, 0, 20);
		addSectorHistograms1D(h1_FTOF1B_Esqrt_AllNOTRG3, "h1_FTOF1B_Esqrt_AllNOTRG3", 100, 0, 20);
		addSectorHistograms1D(h1_FTOF1B_T_AllNOTRG3, "h1_FTOF1B_T_AllNOTRG3", 600, 0, 600.);
		addSectorHistograms1D(h1_FTOF1B_ID_AllNOTRG3, "h1_FTOF1B_ID_AllNOTRG3", 80, -0.5, 79.5);
		addSectorHistograms2D(h2_FTOF1B_E2_AllNOTRG3, "h2_FTOF1B_E2_AllNOTRG3", 100, 0, 20., 100, 0, 20.);

		h1_delta_PCAL_FTOF_AllNOTRG3 = new H1F("h1_delta_PCAL_FTOF_AllNOTRG3", "h1_delta_PCAL_FTOF_AllNOTRG3", 41, -20.5, 20.5);
		allH1F.add(h1_delta_PCAL_FTOF_AllNOTRG3);

		/* Good tracks with TRG4 bit */
		h1_MomAllTRG4 = new H1F("h1_MomAllTRG4", "h1_MomAllTRG4", 100, 0, 10.);
		allH1F.add(h1_MomAllTRG4);
		h1_MomQPTRG4 = new H1F("h1_MomQPTRG4", "h1_MomQPTRG4", 100, 0, 10.);
		allH1F.add(h1_MomQPTRG4);
		h1_MomQMTRG4 = new H1F("h1_MomQMTRG4", "h1_MomQMTRG4", 100, 0, 10.);
		allH1F.add(h1_MomQMTRG4);

		h1_MomAllTRG4_sector = new ArrayList<H1F>();
		h1_MomQPTRG4_sector = new ArrayList<H1F>();
		h1_MomQMTRG4_sector = new ArrayList<H1F>();
		addSectorHistograms1D(h1_MomAllTRG4_sector, "h1_MomAllTRG4", 100, 0, 10.);
		addSectorHistograms1D(h1_MomQPTRG4_sector, "h1_MomQPTRG4", 100, 0, 10.);
		addSectorHistograms1D(h1_MomQMTRG4_sector, "h1_MomQMTRG4", 100, 0, 10.);

		h2_ThetaPhiAllTRG4 = new H2F("h2_ThetaPhiAllTRG4", "h2_ThetaPhiAllTRG4", 100, -180., 180., 100, 0, 60.);
		allH2F.add(h2_ThetaPhiAllTRG4);
		h2_ThetaPhiQPTRG4 = new H2F("h2_ThetaPhiQPTRG4", "h2_ThetaPhiQPTRG4", 100, -180., 180., 100, 0, 60.);
		allH2F.add(h2_ThetaPhiQPTRG4);
		h2_ThetaPhiQMTRG4 = new H2F("h2_ThetaPhiQMTRG4", "h2_ThetaPhiQMTRG4", 100, -180., 180., 100, 0, 60.);
		allH2F.add(h2_ThetaPhiQMTRG4);

		h2_ThetaPAllTRG4_sector = new ArrayList<H2F>();
		h2_ThetaPQPTRG4_sector = new ArrayList<H2F>();
		h2_ThetaPQMTRG4_sector = new ArrayList<H2F>();
		addSectorHistograms2D(h2_ThetaPAllTRG4_sector, "h2_ThetaPAllTRG4", 100, 0., 10., 100, 0, 60.);
		addSectorHistograms2D(h2_ThetaPQPTRG4_sector, "h2_ThetaPQPTRG4", 100, 0., 10., 100, 0, 60.);
		addSectorHistograms2D(h2_ThetaPQMTRG4_sector, "h2_ThetaPQMTRG4", 100, 0., 10., 100, 0, 60.);

		h2_PCAL_XY_AllTRG4 = new H2F("h2_PCAL_XY_AllTRG4", "h2_PCAL_XY_AllTRG4", 200, -600, 600, 200, -600, 600);
		allH2F.add(h2_PCAL_XY_AllTRG4);
		h1_PCAL_E_AllTRG4 = new ArrayList<H1F>();
		h1_PCAL_ID_AllTRG4 = new ArrayList<H1F>();
		addSectorHistograms1D(h1_PCAL_E_AllTRG4, "h1_PCAL_E_AllTRG4", 100, 0, 0.1);
		addSectorHistograms1D(h1_PCAL_ID_AllTRG4, "h1_PCAL_ID_AllTRG4", 80, -0.5, 79.5);

		h2_FTOF1B_XY_AllTRG4 = new H2F("h2_FTOF1B_XY_AllTRG4", "h2_FTOF1B_XY_AllTRG4", 200, -600, 600, 200, -600, 600);
		allH2F.add(h2_FTOF1B_XY_AllTRG4);
		h1_FTOF1B_E_AllTRG4 = new ArrayList<H1F>();
		h1_FTOF1B_Esqrt_AllTRG4 = new ArrayList<H1F>();
		h1_FTOF1B_T_AllTRG4 = new ArrayList<H1F>();
		h1_FTOF1B_ID_AllTRG4 = new ArrayList<H1F>();
		h2_FTOF1B_E2_AllTRG4 = new ArrayList<H2F>();
		addSectorHistograms1D(h1_FTOF1B_E_AllTRG4, "h1_FTOF1B_E_AllTRG4", 100, 0, 20);
		addSectorHistograms1D(h1_FTOF1B_Esqrt_AllTRG4, "h1_FTOF1B_Esqrt_AllTRG4", 100, 0, 20);
		addSectorHistograms1D(h1_FTOF1B_T_AllTRG4, "h1_FTOF1B_T_AllTRG4", 600, 0, 600.);
		addSectorHistograms1D(h1_FTOF1B_ID_AllTRG4, "h1_FTOF1B_ID_AllTRG4", 80, -0.5, 79.5);
		addSectorHistograms2D(h2_FTOF1B_E2_AllTRG4, "h2_FTOF1B_E2_AllTRG4", 100, 0, 20., 100, 0, 20.);

		h1_delta_PCAL_FTOF_AllTRG4 = new H1F("h1_delta_PCAL_FTOF_AllTRG4", "h1_delta_PCAL_FTOF_AllTRG4", 41, -20.5, 20.5);
		allH1F.add(h1_delta_PCAL_FTOF_AllTRG4);

		h1_MomAllNOTRG4 = new H1F("h1_MomAllNOTRG4", "h1_MomAllNOTRG4", 100, 0, 10.);
		allH1F.add(h1_MomAllNOTRG4);
		h1_MomQPNOTRG4 = new H1F("h1_MomQPNOTRG4", "h1_MomQPNOTRG4", 100, 0, 10.);
		allH1F.add(h1_MomQPNOTRG4);
		h1_MomQMNOTRG4 = new H1F("h1_MomQMNOTRG4", "h1_MomQMNOTRG4", 100, 0, 10.);
		allH1F.add(h1_MomQMNOTRG4);

		h1_MomAllNOTRG4_sector = new ArrayList<H1F>();
		h1_MomQPNOTRG4_sector = new ArrayList<H1F>();
		h1_MomQMNOTRG4_sector = new ArrayList<H1F>();
		addSectorHistograms1D(h1_MomAllNOTRG4_sector, "h1_MomAllNOTRG4", 100, 0, 10.);
		addSectorHistograms1D(h1_MomQPNOTRG4_sector, "h1_MomQPNOTRG4", 100, 0, 10.);
		addSectorHistograms1D(h1_MomQMNOTRG4_sector, "h1_MomQMNOTRG4", 100, 0, 10.);

		h2_ThetaPhiAllNOTRG4 = new H2F("h2_ThetaPhiAllNOTRG4", "h2_ThetaPhiAllNOTRG4", 100, -180., 180., 100, 0, 60.);
		allH2F.add(h2_ThetaPhiAllNOTRG4);
		h2_ThetaPhiQPNOTRG4 = new H2F("h2_ThetaPhiQPNOTRG4", "h2_ThetaPhiQPNOTRG4", 100, -180., 180., 100, 0, 60.);
		allH2F.add(h2_ThetaPhiQPNOTRG4);
		h2_ThetaPhiQMNOTRG4 = new H2F("h2_ThetaPhiQMNOTRG4", "h2_ThetaPhiQMNOTRG4", 100, -180., 180., 100, 0, 60.);
		allH2F.add(h2_ThetaPhiQMNOTRG4);

		h2_ThetaPAllNOTRG4_sector = new ArrayList<H2F>();
		h2_ThetaPQPNOTRG4_sector = new ArrayList<H2F>();
		h2_ThetaPQMNOTRG4_sector = new ArrayList<H2F>();
		addSectorHistograms2D(h2_ThetaPAllNOTRG4_sector, "h2_ThetaPAllNOTRG4", 100, 0., 10., 100, 0, 60.);
		addSectorHistograms2D(h2_ThetaPQPNOTRG4_sector, "h2_ThetaPQPNOTRG4", 100, 0., 10., 100, 0, 60.);
		addSectorHistograms2D(h2_ThetaPQMNOTRG4_sector, "h2_ThetaPQMNOTRG4", 100, 0., 10., 100, 0, 60.);

		h2_PCAL_XY_AllNOTRG4 = new H2F("h2_PCAL_XY_AllNOTRG4", "h2_PCAL_XY_AllNOTRG4", 200, -600, 600, 200, -600, 600);
		allH2F.add(h2_PCAL_XY_AllNOTRG4);
		h1_PCAL_E_AllNOTRG4 = new ArrayList<H1F>();
		h1_PCAL_ID_AllNOTRG4 = new ArrayList<H1F>();
		addSectorHistograms1D(h1_PCAL_E_AllNOTRG4, "h1_PCAL_E_AllNOTRG4", 100, 0, 0.1);
		addSectorHistograms1D(h1_PCAL_ID_AllNOTRG4, "h1_PCAL_ID_AllNOTRG4", 80, -0.5, 79.5);

		h2_FTOF1B_XY_AllNOTRG4 = new H2F("h2_FTOF1B_XY_AllNOTRG4", "h2_FTOF1B_XY_AllNOTRG4", 200, -600, 600, 200, -600, 600);
		allH2F.add(h2_FTOF1B_XY_AllNOTRG4);
		h1_FTOF1B_E_AllNOTRG4 = new ArrayList<H1F>();
		h1_FTOF1B_Esqrt_AllNOTRG4 = new ArrayList<H1F>();
		h1_FTOF1B_T_AllNOTRG4 = new ArrayList<H1F>();
		h1_FTOF1B_ID_AllNOTRG4 = new ArrayList<H1F>();
		h2_FTOF1B_E2_AllNOTRG4 = new ArrayList<H2F>();
		addSectorHistograms1D(h1_FTOF1B_E_AllNOTRG4, "h1_FTOF1B_E_AllNOTRG4", 100, 0, 20);
		addSectorHistograms1D(h1_FTOF1B_Esqrt_AllNOTRG4, "h1_FTOF1B_Esqrt_AllNOTRG4", 100, 0, 20);
		addSectorHistograms1D(h1_FTOF1B_T_AllNOTRG4, "h1_FTOF1B_T_AllNOTRG4", 600, 0, 600.);
		addSectorHistograms1D(h1_FTOF1B_ID_AllNOTRG4, "h1_FTOF1B_ID_AllNOTRG4", 80, -0.5, 79.5);
		addSectorHistograms2D(h2_FTOF1B_E2_AllNOTRG4, "h2_FTOF1B_E2_AllNOTRG4", 100, 0, 20., 100, 0, 20.);

		h1_delta_PCAL_FTOF_AllNOTRG4 = new H1F("h1_delta_PCAL_FTOF_AllNOTRG4", "h1_delta_PCAL_FTOF_AllNOTRG4", 41, -20.5, 20.5);
		allH1F.add(h1_delta_PCAL_FTOF_AllNOTRG4);

		setupCD();
	
		
		
		/*FT*/
		h1_FT_E_AllREC = new H1F("h1_FT_E_AllREC", "h1_FT_E_AllREC", 100, eMinHisto, eMaxHisto);
		allH1F.add(h1_FT_E_AllREC);
		h1_FT_E_HodoREC = new H1F("h1_FT_E_HodoREC", "h1_FT_E_HodoREC", 100, eMinHisto, eMaxHisto);
		allH1F.add(h1_FT_E_HodoREC);
		h1_FT_T_AllREC = new H1F("h1_FT_T_AllREC", "h1_FT_T_AllREC", 300, 0, 1200);
		allH1F.add(h1_FT_T_AllREC);
		h1_FT_T_HodoREC = new H1F("h1_FT_T_HodoREC", "h1_FT_T_HodoREC", 300, 0, 1200);
		allH1F.add(h1_FT_T_HodoREC);
		h2_FT_XY_AllREC = new H2F("h2_FT_XY_AllREC", "h2_FT_XY_AllREC", 22, 0.5, 22.5, 22, 0.5, 22.5);
		allH2F.add(h2_FT_XY_AllREC);
		h2_FT_XY_HodoREC = new H2F("h2_FT_XY_HodoREC", "h2_FT_XY_HodoREC", 22, 0.5, 22.5, 22, 0.5, 22.5);
		allH2F.add(h2_FT_XY_HodoREC);
		h1_FT_TCmTH1_HodoREC = new H1F("h1_FT_TCmTH1_HodoREC", "h1_FT_TCmTH1_HodoREC", 100, -50, 50);
		allH1F.add(h1_FT_TCmTH1_HodoREC);
		h1_FT_TCmTH2_HodoREC = new H1F("h1_FT_TCmTH2_HodoREC", "h1_FT_TCmTH2_HodoREC", 100, -50, 50);
		allH1F.add(h1_FT_TCmTH2_HodoREC);
		h1_FT_TH2mTH1_HodoREC = new H1F("h1_FT_TH2mTH1_HodoREC", "h1_FT_TH2mTH1_HodoREC", 100, -50, 50);
		allH1F.add(h1_FT_TH2mTH1_HodoREC);
		h1_FT_RCmRH1_HodoREC = new H1F("h1_FT_RCmRH1_HodoREC","h1_FT_RCmRH1_HodoREC",100,0,50);
		allH1F.add(h1_FT_RCmRH1_HodoREC);	
		h1_FT_RCmRH2_HodoREC = new H1F("h1_FT_RCmRH2_HodoREC","h1_FT_RCmRH2_HodoREC",100,0,50);
		allH1F.add(h1_FT_RCmRH2_HodoREC);
		
		
		
		h1_FT_E_AllMATCH = new H1F("h1_FT_E_AllMATCH", "h1_FT_E_AllMATCH", 100, eMinHisto, eMaxHisto);
		allH1F.add(h1_FT_E_AllMATCH);
		h1_FT_E_HodoMATCH = new H1F("h1_FT_E_HodoMATCH", "h1_FT_E_HodoMATCH", 100, eMinHisto, eMaxHisto);
		allH1F.add(h1_FT_E_HodoMATCH);
		h1_FT_T_AllMATCH = new H1F("h1_FT_T_AllMATCH", "h1_FT_T_AllMATCH", 300, 0, 1200);
		allH1F.add(h1_FT_T_AllMATCH);
		h1_FT_T_HodoMATCH = new H1F("h1_FT_T_HodoMATCH", "h1_FT_T_HodoMATCH", 300, 0, 1200);
		allH1F.add(h1_FT_T_HodoMATCH);
		h2_FT_XY_AllMATCH = new H2F("h2_FT_XY_AllMATCH", "h2_FT_XY_AllMATCH", 22, 0.5, 22.5, 22, 0.5, 22.5);
		allH2F.add(h2_FT_XY_AllMATCH);
		h2_FT_XY_HodoMATCH = new H2F("h2_FT_XY_HodoMATCH", "h2_FT_XY_HodoMATCH", 22, 0.5, 22.5, 22, 0.5, 22.5);
		allH2F.add(h2_FT_XY_HodoMATCH);
		
		h1_FT_TCmTH1_HodoMATCH = new H1F("h1_FT_TCmTH1_HodoMATCH", "h1_FT_TCmTH1_HodoMATCH", 100, -50, 50);
		allH1F.add(h1_FT_TCmTH1_HodoMATCH);
		h1_FT_TCmTH2_HodoMATCH = new H1F("h1_FT_TCmTH2_HodoMATCH", "h1_FT_TCmTH2_HodoMATCH", 100, -50, 50);
		allH1F.add(h1_FT_TCmTH2_HodoMATCH);
		h1_FT_TH2mTH1_HodoMATCH = new H1F("h1_FT_TH2mTH1_HodoMATCH", "h1_FT_TH2mTH1_HodoMATCH", 100, -50, 50);
		allH1F.add(h1_FT_TH2mTH1_HodoMATCH);
		h1_FT_RCmRH1_HodoMATCH = new H1F("h1_FT_RCmRH1_HodoMATCH","h1_FT_RCmRH1_HodoMATCH",100,0,50);
		allH1F.add(h1_FT_RCmRH1_HodoMATCH);	
		h1_FT_RCmRH2_HodoMATCH = new H1F("h1_FT_RCmRH2_HodoMATCH","h1_FT_RCmRH2_HodoMATCH",100,0,50);
		allH1F.add(h1_FT_RCmRH2_HodoMATCH);
		
		

		h1_FT_E_HodoMATCHHODO = new H1F("h1_FT_E_HodoMATCHHODO", "h1_FT_E_HodoMATCHHODO", 100, eMinHisto, eMaxHisto);
		allH1F.add(h1_FT_E_HodoMATCHHODO);
		h1_FT_T_HodoMATCHHODO = new H1F("h1_FT_T_HodoMATCHHODO", "h1_FT_T_HodoMATCHHODO", 300, 0, 1200);
		allH1F.add(h1_FT_T_HodoMATCHHODO);
		h2_FT_XY_HodoMATCHHODO = new H2F("h2_FT_XY_HodoMATCHHODO", "h2_FT_XY_HodoMATCHHODO", 22, 0.5, 22.5, 22, 0.5, 22.5);
		allH2F.add(h2_FT_XY_HodoMATCHHODO);

		h1_FT_TCmTH1_HodoMATCHHODO = new H1F("h1_FT_TCmTH1_HodoMATCHHODO", "h1_FT_TCmTH1_HodoMATCHHODO", 100, -50, 50);
		allH1F.add(h1_FT_TCmTH1_HodoMATCHHODO);
		h1_FT_TCmTH2_HodoMATCHHODO = new H1F("h1_FT_TCmTH2_HodoMATCHHODO", "h1_FT_TCmTH2_HodoMATCHHODO", 100, -50, 50);
		allH1F.add(h1_FT_TCmTH2_HodoMATCHHODO);
		h1_FT_TH2mTH1_HodoMATCHHODO = new H1F("h1_FT_TH2mTH1_HodoMATCHHODO", "h1_FT_TH2mTH1_HodoMATCHHODO", 100, -50, 50);
		allH1F.add(h1_FT_TH2mTH1_HodoMATCHHODO);
		h1_FT_RCmRH1_HodoMATCHHODO = new H1F("h1_FT_RCmRH1_HodoMATCHHODO","h1_FT_RCmRH1_HodoMATCHHODO",100,0,50);
		allH1F.add(h1_FT_RCmRH1_HodoMATCHHODO);	
		h1_FT_RCmRH2_HodoMATCHHODO = new H1F("h1_FT_RCmRH2_HodoMATCHHODO","h1_FT_RCmRH2_HodoMATCHHODO",100,0,50);
		allH1F.add(h1_FT_RCmRH2_HodoMATCHHODO);
		
		h2_FT_XY_AllNOMATCH = new H2F("h2_FT_XY_AllNOMATCH", "h2_FT_XY_AllNOMATCH", 22, 0.5, 22.5, 22, 0.5, 22.5);
		allH2F.add(h2_FT_XY_AllNOMATCH);
		h2_FT_XY_HodoNOMATCH = new H2F("h2_FT_XY_HodoNOMATCH", "h2_FT_XY_HodoNOMATCH", 22, 0.5, 22.5, 22, 0.5, 22.5);
		allH2F.add(h2_FT_XY_HodoNOMATCH);
		h2_FT_XY_HodoNOMATCHHODO = new H2F("h2_FT_XY_HodoNOMATCHHODO", "h2_FT_XY_HodoNOMATCHHODO", 22, 0.5, 22.5, 22, 0.5, 22.5);
		allH2F.add(h2_FT_XY_HodoNOMATCHHODO);
		h1_FT_TCmTH1_HodoNOMATCHHODO = new H1F("h1_FT_TCmTH1_HodoNOMATCHHODO", "h1_FT_TCmTH1_HodoNOMATCHHODO", 100, -50, 50);
		allH1F.add(h1_FT_TCmTH1_HodoNOMATCHHODO);
		h1_FT_TCmTH2_HodoNOMATCHHODO = new H1F("h1_FT_TCmTH2_HodoNOMATCHHODO", "h1_FT_TCmTH2_HodoNOMATCHHODO", 100, -50, 50);
		allH1F.add(h1_FT_TCmTH2_HodoNOMATCHHODO);
		h1_FT_TH2mTH1_HodoNOMATCHHODO = new H1F("h1_FT_TH2mTH1_HodoNOMATCHHODO", "h1_FT_TH2mTH1_HodoNOMATCHHODO", 100, -50, 50);
		allH1F.add(h1_FT_TH2mTH1_HodoNOMATCHHODO);
		h1_FT_RCmRH1_HodoNOMATCHHODO = new H1F("h1_FT_RCmRH1_HodoNOMATCHHODO","h1_FT_RCmRH1_HodoNOMATCHHODO",100,0,50);
		allH1F.add(h1_FT_RCmRH1_HodoNOMATCHHODO);	
		h1_FT_RCmRH2_HodoNOMATCHHODO = new H1F("h1_FT_RCmRH2_HodoNOMATCHHODO","h1_FT_RCmRH2_HodoNOMATCHHODO",100,0,50);
		allH1F.add(h1_FT_RCmRH2_HodoNOMATCHHODO);
		
		h2_FT_T_vs_VTP_T_AllMATCH = new H2F("h2_FT_T_vs_VTP_T_AllMATCH", "h2_FT_T_vs_VTP_T_AllMATCH", 300, 0, 1200, 300, 0, 1200);
		allH2F.add(h2_FT_T_vs_VTP_T_AllMATCH);

		h2_FT_T_vs_VTP_T_HodoMATCH = new H2F("h2_FT_T_vs_VTP_T_HodoMATCH", "h2_FT_T_vs_VTP_T_HodoMATCH", 300, 0, 1200, 300, 0, 1200);
		allH2F.add(h2_FT_T_vs_VTP_T_HodoMATCH);

		h2_FT_T_vs_VTP_T_HodoMATCHHODO = new H2F("h2_FT_T_vs_VTP_T_HodoMATCHHODO", "h2_FT_T_vs_VTP_T_HodoMATCHHODO", 300, 0, 1200, 300, 0, 1200);
		allH2F.add(h2_FT_T_vs_VTP_T_HodoMATCHHODO);

		h1_FT_h1E_Hodo1REC = new H1F("h1_FT_h1E_Hodo1REC", "h1_FT_h1E_Hodo1REC", 100, 0, 10);
		allH1F.add(h1_FT_h1E_Hodo1REC);

		h1_FT_h2E_Hodo2REC = new H1F("h1_FT_h2E_Hodo2REC", "h1_FT_h2E_Hodo2REC", 100, 0, 10);
		allH1F.add(h1_FT_h2E_Hodo2REC);

		h1_FT_h1E_Hodo1MATCH = new H1F("h1_FT_h1E_Hodo1MATCH", "h1_FT_h1E_Hodo1MATCH", 100, 0, 10);
		allH1F.add(h1_FT_h1E_Hodo1MATCH);

		h1_FT_h2E_Hodo2MATCH = new H1F("h1_FT_h2E_Hodo2MATCH", "h1_FT_h2E_Hodo2MATCH", 100, 0, 10);
		allH1F.add(h1_FT_h2E_Hodo2MATCH);

		h1_FT_h1E_Hodo1MATCHHODO1 = new H1F("h1_FT_h1E_Hodo1MATCHHODO1", "h1_FT_h1E_Hodo1MATCHHODO1", 100, 0, 10);
		allH1F.add(h1_FT_h1E_Hodo1MATCHHODO1);

		h1_FT_h2E_Hodo2MATCHHODO2 = new H1F("h1_FT_h2E_Hodo2MATCHHODO2", "h1_FT_h2E_Hodo2MATCHHODO2", 100, 0, 10);
		allH1F.add(h1_FT_h2E_Hodo2MATCHHODO2);

	}

	private void setupCD() {
		
		/*CD*/
		h1_CD_MomAllRec= new H1F("h1_CD_MomAllRec", "h1_CD_MomAllRec", 100, 0, 10.);allH1F.add(h1_CD_MomAllRec);
		h2_CD_ThetaPhiAllRec=new H2F("h2_CD_ThetaPhiAllRec","h2_CD_ThetaPhiAllReC",100,-180,180,100,0,120);allH2F.add(h2_CD_ThetaPhiAllRec);
		
		h1_CTOF_EAllRec = new H1F("h1_CTOF_EAllRec","h1_CTOF_EAllRec",100,0,100);allH1F.add(h1_CTOF_EAllRec);
		h1_CND1_EAllRec = new H1F("h1_CND1_EAllRec","h1_CND1_EAllRec",100,0,100);allH1F.add(h1_CND1_EAllRec);
		h1_CND2_EAllRec = new H1F("h1_CND2_EAllRec","h1_CND2_EAllRec",100,0,100);allH1F.add(h1_CND2_EAllRec);
		h1_CND3_EAllRec = new H1F("h1_CND3_EAllRec","h1_CND3_EAllRec",100,0,100);allH1F.add(h1_CND3_EAllRec);
		
		h2_CTOFvsCND1AllRec=new H2F("h2_CTOFvsCND1AllRec","h2_CTOFvsCND1AllRec",60,-0.5,59.5,60,-0.5,59.5);allH2F.add(h2_CTOFvsCND1AllRec);
		
		h1_CD_MomAllTRG1 = new H1F("h1_CD_MomAllTRG1", "h1_CD_MomAllTRG1", 100, 0, 10.);allH1F.add(h1_CD_MomAllTRG1);
	
		
	}

	public void addSectorHistograms1D(ArrayList<H1F> myList, String name, int nbinsX, double xmin, double xmax) {

		for (int ibin = 1; ibin <= 6; ibin++) {
			myList.add(new H1F(name + "_" + ibin, name + "_" + ibin, nbinsX, xmin, xmax));
			allH1F.add(myList.get(ibin - 1));
		}

	}

	public void addSectorHistograms2D(ArrayList<H2F> myList, String name, int nbinsX, double xmin, double xmax, int nbinsY, double ymin, double ymax) {

		for (int ibin = 1; ibin <= 6; ibin++) {
			myList.add(new H2F(name + "_" + ibin, name + "_" + ibin, nbinsX, xmin, xmax, nbinsY, ymin, ymax));
			allH2F.add(myList.get(ibin - 1));
		}

	}

	public void showHistograms() {

	}

	public void processHistograms() {

		/* Efficiency BIT1 */
		h1_MomAllEFFTRG1 = h1_MomAllTRG4.histClone("h1_MomAllEFFTRG1");
		h1_MomAllEFFTRG1.divide(h1_MomAllREC);
		allH1F.add(h1_MomAllEFFTRG1);

		h1_MomAllEFFTRG1_sector = new ArrayList<H1F>();
		doSectorEfficiency1D(h1_MomAllEFFTRG1_sector, "h1_MomAllEFFTRG1", h1_MomAllTRG1_sector, h1_MomAllREC_sector);

		/* Efficiency BIT2 */
		h1_MomAllEFFTRG2 = h1_MomAllTRG2.histClone("h1_MomAllEFFTRG2");
		h1_MomAllEFFTRG2.divide(h1_MomAllREC);
		allH1F.add(h1_MomAllEFFTRG2);

		h1_MomAllEFFTRG2_sector = new ArrayList<H1F>();
		doSectorEfficiency1D(h1_MomAllEFFTRG2_sector, "h1_MomAllEFFTRG2", h1_MomAllTRG2_sector, h1_MomAllREC_sector);

		/* Efficiency BIT3 */
		h1_MomAllEFFTRG3 = h1_MomAllTRG3.histClone("h1_MomAllEFFTRG3");
		h1_MomAllEFFTRG3.divide(h1_MomAllREC);
		allH1F.add(h1_MomAllEFFTRG3);

		h1_MomAllEFFTRG3_sector = new ArrayList<H1F>();
		doSectorEfficiency1D(h1_MomAllEFFTRG3_sector, "h1_MomAllEFFTRG3", h1_MomAllTRG3_sector, h1_MomAllREC_sector);

		/* Efficiency BIT4 */
		h1_MomAllEFFTRG4 = h1_MomAllTRG4.histClone("h1_MomAllEFFTRG4");
		h1_MomAllEFFTRG4.divide(h1_MomAllREC);
		allH1F.add(h1_MomAllEFFTRG4);

		h1_MomAllEFFTRG4_sector = new ArrayList<H1F>();
		doSectorEfficiency1D(h1_MomAllEFFTRG4_sector, "h1_MomAllEFFTRG4", h1_MomAllTRG4_sector, h1_MomAllREC_sector);

		/* Efficiency FT Hodoscope */

		h1_FT_h1E_Hodo1EFFMATCH = h1_FT_h1E_Hodo1MATCH.histClone("h1_FT_h1E_Hodo1EFFMATCH");
		h1_FT_h1E_Hodo1EFFMATCH.divide(h1_FT_h1E_Hodo1REC);
		allH1F.add(h1_FT_h1E_Hodo1EFFMATCH);

		h1_FT_h2E_Hodo2EFFMATCH = h1_FT_h2E_Hodo2MATCH.histClone("h1_FT_h2E_Hodo2EFFMATCH");
		h1_FT_h2E_Hodo2EFFMATCH.divide(h1_FT_h2E_Hodo2REC);
		allH1F.add(h1_FT_h2E_Hodo2EFFMATCH);

		h1_FT_h1E_Hodo1EFFMATCHHODO1 = h1_FT_h1E_Hodo1MATCHHODO1.histClone("h1_FT_h1E_Hodo1EFFMATCHHODO1");
		h1_FT_h1E_Hodo1EFFMATCHHODO1.divide(h1_FT_h1E_Hodo1REC);
		allH1F.add(h1_FT_h1E_Hodo1EFFMATCHHODO1);

		h1_FT_h2E_Hodo2EFFMATCHHODO2 = h1_FT_h2E_Hodo2MATCHHODO2.histClone("h1_FT_h2E_Hodo2EFFMATCHHODO2");
		h1_FT_h2E_Hodo2EFFMATCHHODO2.divide(h1_FT_h2E_Hodo2REC);
		allH1F.add(h1_FT_h2E_Hodo2EFFMATCHHODO2);
		
		
		/*Efficiency CENTRAL */
		h1_CD_MomAllEFFTRG1=h1_CD_MomAllTRG1.histClone("h1_CD_MomAllEFFTRG1");
		h1_CD_MomAllEFFTRG1.divide(h1_CD_MomAllRec);
		allH1F.add(h1_CD_MomAllEFFTRG1);

	}

	private void doSectorEfficiency1D(ArrayList<H1F> input, String name, ArrayList<H1F> num, ArrayList<H1F> den) {
		for (int ibin = 1; ibin <= 6; ibin++) {
			input.add(num.get(ibin - 1).histClone(name + "_" + ibin));
			input.get(ibin - 1).divide(den.get(ibin - 1));
			allH1F.add(input.get(ibin - 1));
		}
	}

	public H1F getHistogram1D(String name) {
		for (H1F h : this.allH1F) {
			if (name.equals(h.getName()) == true) {
				return h;
			}
		}
		System.out.println("histogram: " + name + " not found");
		return null;
	}

	public H2F getHistogram2D(String name) {
		for (H2F h : this.allH2F) {
			if (name.equals(h.getName()) == true) {
				return h;
			}
		}
		System.out.println("histogram: " + name + " not found");
		return null;
	}

	public void saveHistograms(TDirectory dir) {
		dir.mkdir("/histo");
		dir.cd("/histo");
		for (H1F h : this.allH1F) {
			dir.addDataSet(h);
		}
		for (H2F h : this.allH2F) {
			dir.addDataSet(h);
		}
		// dir.addDataSet(allRECEnergy);
	}

	public static double[] getHistoMaxPosAndValInRange(H1F h, double min, double max) {
		double[] ret = new double[2];

		double iMin = min, iMax = max;
		double maxVal, maxX, x, y;
		Axis axis = h.getXaxis();
		if (axis.min() > iMin) {
			iMin = axis.min();
		}
		if (axis.max() < iMax) {
			iMax = axis.max();
		}

		maxVal = -1;
		maxX = 0;
		for (int ibin = 0; ibin < h.getData().length; ibin++) {
			x = axis.getBinCenter(ibin);
			// y = h.getBinContent(ibin);
			y = h.getData()[ibin];
			if ((x < iMin) || (x > iMax)) continue;
			if (y > maxVal) {
				maxVal = y;
				maxX = x;
			}
		}

		ret[0] = maxX;
		ret[1] = maxVal;

		return ret;

	}

}