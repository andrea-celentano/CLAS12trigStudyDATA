package clas12;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.data.TDirectory;
import org.jlab.groot.fitter.DataFitter;
import org.jlab.groot.math.Axis;
import org.jlab.groot.math.F1D;
import org.jlab.groot.ui.TBrowser;
import org.jlab.groot.ui.TCanvas;

public class PlotClass {

	TDirectory dirIn;

	public static void main(String[] args) throws IOException {

		PlotClass myClass = new PlotClass();

		myClass.readFile();
		myClass.doPlot();

	}

	private void readFile() {

		dirIn = new TDirectory();
	//	dirIn.readFile("/work/clas12/trigStudyDATA/run1.out1.hipo");
		dirIn.readFile("/work/clas12/trigStudyDATA/run2.out.hipo");
	//	dirIn.readFile("/work/clas12/trigStudyDATA/run3.out.hipo");
	//	dirIn.readFile("/work/clas12/trigStudyDATA/run4.out.hipo");
		dirIn.cd();
		dirIn.tree();

	}

	private void doPlot() {

		doPlotTrigger();
		doPlotEfficiency();
		doPlotFTOF();
		doPlotPCAL();
		doPlotFT();
		doPlotCD();
	}
	
	private void doPlotCD() {
		H1F h1_CD_MomAllRec=(H1F)dirIn.getObject("/histo/h1_CD_MomAllRec");
		H2F h2_CD_ThetaPhiAllRec=(H2F)dirIn.getObject("/histo/h2_CD_ThetaPhiAllRec");
		

		H1F h1_CTOF_EAllRec=(H1F)dirIn.getObject("/histo/h1_CTOF_EAllRec");
		H1F h1_CND1_EAllRec=(H1F)dirIn.getObject("/histo/h1_CND1_EAllRec");
		H1F h1_CND2_EAllRec=(H1F)dirIn.getObject("/histo/h1_CND2_EAllRec");
		H1F h1_CND3_EAllRec=(H1F)dirIn.getObject("/histo/h1_CND3_EAllRec");
		
		H2F h2_CTOFvsCND1AllRec=(H2F)dirIn.getObject("/histo/h2_CTOFvsCND1AllRec");
		
		TCanvas CD1 = new TCanvas("CD1",900,900);
		CD1.divide(3,2);
		
		CD1.cd(0);
		CD1.draw(h1_CD_MomAllRec);
		
		CD1.cd(1);
		CD1.draw(h2_CD_ThetaPhiAllRec,"colz");
		
		CD1.cd(2);
		CD1.draw(h2_CTOFvsCND1AllRec,"colz");
		
		TCanvas CD2 = new TCanvas("CD2",900,900);
		CD2.divide(4,2);
		CD2.cd(0);
		CD2.draw(h1_CTOF_EAllRec);
		CD2.cd(1);
		CD2.draw(h1_CND1_EAllRec);
		CD2.cd(2);
		CD2.draw(h1_CND2_EAllRec);
		CD2.cd(3);
		CD2.draw(h1_CND3_EAllRec);
		
		
		/*Extract the geom. map*/
		
		
		
	}

	private void doPlotFT() {
	
		H1F h1_FT_E_AllREC = (H1F) dirIn.getObject("/histo/h1_FT_E_AllREC");
		H1F h1_FT_E_HodoREC = (H1F) dirIn.getObject("/histo/h1_FT_E_HodoREC");
		H1F h1_FT_E_AllMATCH = (H1F) dirIn.getObject("/histo/h1_FT_E_AllMATCH");
		H1F h1_FT_E_HodoMATCH = (H1F) dirIn.getObject("/histo/h1_FT_E_HodoMATCH");
		H1F h1_FT_E_HodoMATCHHODO = (H1F) dirIn.getObject("/histo/h1_FT_E_HodoMATCHHODO");
		
		H1F h1_FT_T_AllREC = (H1F) dirIn.getObject("/histo/h1_FT_T_AllREC");
		H1F h1_FT_T_HodoREC = (H1F) dirIn.getObject("/histo/h1_FT_T_HodoREC");
		H1F h1_FT_T_AllMATCH = (H1F) dirIn.getObject("/histo/h1_FT_T_AllMATCH");
		H1F h1_FT_T_HodoMATCH = (H1F) dirIn.getObject("/histo/h1_FT_T_HodoMATCH");
		H1F h1_FT_T_HodoMATCHHODO = (H1F) dirIn.getObject("/histo/h1_FT_T_HodoMATCHHODO");
		
		H2F h2_FT_XY_AllREC = (H2F) dirIn.getObject("/histo/h2_FT_XY_AllREC");
		H2F h2_FT_XY_HodoREC = (H2F) dirIn.getObject("/histo/h2_FT_XY_HodoREC");
		H2F h2_FT_XY_AllMATCH = (H2F) dirIn.getObject("/histo/h2_FT_XY_AllMATCH");
		H2F h2_FT_XY_HodoMATCH = (H2F) dirIn.getObject("/histo/h2_FT_XY_HodoMATCH");
		H2F h2_FT_XY_AllNOMATCH = (H2F) dirIn.getObject("/histo/h2_FT_XY_AllNOMATCH");
		H2F h2_FT_XY_HodoNOMATCH = (H2F) dirIn.getObject("/histo/h2_FT_XY_HodoNOMATCH");
		H2F h2_FT_XY_HodoNOMATCHHODO = (H2F) dirIn.getObject("/histo/h2_FT_XY_HodoNOMATCHHODO");
		
		H2F h2_FT_T_vs_VTP_T_AllMATCH = (H2F) dirIn.getObject("/histo/h2_FT_T_vs_VTP_T_AllMATCH");
		H2F h2_FT_T_vs_VTP_T_HodoMATCH = (H2F) dirIn.getObject("/histo/h2_FT_T_vs_VTP_T_HodoMATCH");
		H2F h2_FT_T_vs_VTP_T_HodoMATCHHODO = (H2F) dirIn.getObject("/histo/h2_FT_T_vs_VTP_T_HodoMATCHHODO");
		
		H1F h1_FT_h1E_Hodo1REC = (H1F) dirIn.getObject("/histo/h1_FT_h1E_Hodo1REC");
		H1F h1_FT_h2E_Hodo2REC = (H1F) dirIn.getObject("/histo/h1_FT_h2E_Hodo2REC");
		
		H1F h1_FT_h1E_Hodo1MATCH = (H1F) dirIn.getObject("/histo/h1_FT_h1E_Hodo1MATCH");
		H1F h1_FT_h2E_Hodo2MATCH = (H1F) dirIn.getObject("/histo/h1_FT_h2E_Hodo2MATCH");
		
		H1F h1_FT_h1E_Hodo1MATCHHODO1 = (H1F) dirIn.getObject("/histo/h1_FT_h1E_Hodo1MATCHHODO1");
		H1F h1_FT_h2E_Hodo2MATCHHODO2 = (H1F) dirIn.getObject("/histo/h1_FT_h2E_Hodo2MATCHHODO2");
		
		H1F h1_FT_h1E_Hodo1EFFMATCH = (H1F) dirIn.getObject("/histo/h1_FT_h1E_Hodo1EFFMATCH");
		H1F h1_FT_h2E_Hodo2EFFMATCH = (H1F) dirIn.getObject("/histo/h1_FT_h2E_Hodo2EFFMATCH");
		
		H1F h1_FT_h1E_Hodo1EFFMATCHHODO1 = (H1F) dirIn.getObject("/histo/h1_FT_h1E_Hodo1EFFMATCHHODO1");
		H1F h1_FT_h2E_Hodo2EFFMATCHHODO2 = (H1F) dirIn.getObject("/histo/h1_FT_h2E_Hodo2EFFMATCHHODO2");
		
		H1F h1_FT_TCmTH1_HodoREC = (H1F) dirIn.getObject("/histo/h1_FT_TCmTH1_HodoREC");
		H1F h1_FT_TCmTH2_HodoREC = (H1F) dirIn.getObject("/histo/h1_FT_TCmTH2_HodoREC");
		H1F h1_FT_TH2mTH1_HodoREC = (H1F) dirIn.getObject("/histo/h1_FT_TH2mTH1_HodoREC");
		
		H1F h1_FT_TCmTH1_HodoMATCH = (H1F) dirIn.getObject("/histo/h1_FT_TCmTH1_HodoMATCH");
		H1F h1_FT_TCmTH2_HodoMATCH = (H1F) dirIn.getObject("/histo/h1_FT_TCmTH2_HodoMATCH");
		H1F h1_FT_TH2mTH1_HodoMATCH = (H1F) dirIn.getObject("/histo/h1_FT_TH2mTH1_HodoMATCH");
		
		H1F h1_FT_TCmTH1_HodoMATCHHODO = (H1F) dirIn.getObject("/histo/h1_FT_TCmTH1_HodoMATCHHODO");
		H1F h1_FT_TCmTH2_HodoMATCHHODO = (H1F) dirIn.getObject("/histo/h1_FT_TCmTH2_HodoMATCHHODO");
		H1F h1_FT_TH2mTH1_HodoMATCHHODO = (H1F) dirIn.getObject("/histo/h1_FT_TH2mTH1_HodoMATCHHODO");
		
		H1F h1_FT_TCmTH1_HodoNOMATCHHODO = (H1F) dirIn.getObject("/histo/h1_FT_TCmTH1_HodoNOMATCHHODO");
		H1F h1_FT_TCmTH2_HodoNOMATCHHODO = (H1F) dirIn.getObject("/histo/h1_FT_TCmTH2_HodoNOMATCHHODO");
		H1F h1_FT_TH2mTH1_HodoNOMATCHHODO = (H1F) dirIn.getObject("/histo/h1_FT_TH2mTH1_HodoNOMATCHHODO");
		
		H1F h1_FT_RCmRH1_HodoREC=(H1F) dirIn.getObject("/histo/h1_FT_RCmRH1_HodoREC");
		H1F h1_FT_RCmRH2_HodoREC=(H1F) dirIn.getObject("/histo/h1_FT_RCmRH2_HodoREC");
		
		H1F h1_FT_RCmRH1_HodoMATCH=(H1F) dirIn.getObject("/histo/h1_FT_RCmRH1_HodoMATCH");
		H1F h1_FT_RCmRH2_HodoMATCH=(H1F) dirIn.getObject("/histo/h1_FT_RCmRH2_HodoMATCH");
		
		H1F h1_FT_RCmRH1_HodoMATCHHODO=(H1F) dirIn.getObject("/histo/h1_FT_RCmRH1_HodoMATCHHODO");
		H1F h1_FT_RCmRH2_HodoMATCHHODO=(H1F) dirIn.getObject("/histo/h1_FT_RCmRH2_HodoMATCHHODO");
		
		
		H1F h1_FT_RCmRH1_HodoNOMATCHHODO=(H1F) dirIn.getObject("/histo/h1_FT_RCmRH1_HodoNOMATCHHODO");
		H1F h1_FT_RCmRH2_HodoNOMATCHHODO=(H1F) dirIn.getObject("/histo/h1_FT_RCmRH2_HodoNOMATCHHODO");
		
		TCanvas FT1 = new TCanvas("FT1",900,900);
		FT1.divide(3,2);
		
		FT1.cd(0);
		FT1.draw(h1_FT_E_AllREC);
		h1_FT_E_AllMATCH.setLineColor(2);
		FT1.draw(h1_FT_E_AllMATCH,"same");
		
		FT1.cd(1);
		FT1.draw(h1_FT_T_AllREC);
		h1_FT_T_AllMATCH.setLineColor(2);
		FT1.draw(h1_FT_T_AllMATCH,"same");
		
		FT1.cd(2);
		FT1.draw(h2_FT_XY_AllNOMATCH,"colz");
		
		FT1.cd(3);
		FT1.draw(h1_FT_E_HodoREC);
		h1_FT_E_HodoMATCH.setLineColor(2);
		FT1.draw(h1_FT_E_HodoMATCH,"same");
		h1_FT_E_HodoMATCHHODO.setLineColor(3);
		FT1.draw(h1_FT_E_HodoMATCHHODO,"same");
		
		FT1.cd(4);
		FT1.draw(h1_FT_T_HodoREC);
		h1_FT_T_HodoMATCH.setLineColor(2);
		FT1.draw(h1_FT_T_HodoMATCH,"same");
		h1_FT_T_HodoMATCHHODO.setLineColor(3);
		FT1.draw(h1_FT_T_HodoMATCHHODO,"same");
		
		FT1.cd(5);
		FT1.draw(h2_FT_XY_HodoNOMATCHHODO,"colz");
		
		TCanvas FT2 = new TCanvas("FT2",1600,1600);
		FT2.divide(3,4);
		
		FT2.cd(0);
		FT2.draw(h2_FT_T_vs_VTP_T_AllMATCH,"colz");
		FT2.cd(1);
		FT2.draw(h2_FT_T_vs_VTP_T_HodoMATCH,"colz");
		FT2.cd(2);
		FT2.draw(h2_FT_T_vs_VTP_T_HodoMATCHHODO,"colz");
		
		FT2.cd(3);
		FT2.draw(h1_FT_h1E_Hodo1REC);
		h1_FT_h1E_Hodo1MATCH.setLineColor(2);
		FT2.draw(h1_FT_h1E_Hodo1MATCH,"same");
		h1_FT_h1E_Hodo1MATCHHODO1.setLineColor(3);
		FT2.draw(h1_FT_h1E_Hodo1MATCHHODO1,"same");
		
		FT2.cd(4);
		FT2.draw(h1_FT_h2E_Hodo2REC);
		h1_FT_h2E_Hodo2MATCH.setLineColor(2);
		FT2.draw(h1_FT_h2E_Hodo2MATCH,"same");
		h1_FT_h2E_Hodo2MATCHHODO2.setLineColor(3);
		FT2.draw(h1_FT_h2E_Hodo2MATCHHODO2,"same");
		
		FT2.cd(6);
		h1_FT_h1E_Hodo1EFFMATCH.setLineColor(2);
		FT2.draw(h1_FT_h1E_Hodo1EFFMATCH);
		h1_FT_h1E_Hodo1EFFMATCHHODO1.setLineColor(3);
		FT2.draw(h1_FT_h1E_Hodo1EFFMATCHHODO1,"same");
		
		FT2.cd(7);
		h1_FT_h2E_Hodo2EFFMATCH.setLineColor(2);
		FT2.draw(h1_FT_h2E_Hodo2EFFMATCH);
		h1_FT_h2E_Hodo2EFFMATCHHODO2.setLineColor(3);
		FT2.draw(h1_FT_h2E_Hodo2EFFMATCHHODO2,"same");
		
		FT2.cd(9);
		FT2.draw(h1_FT_TCmTH1_HodoREC);
		h1_FT_TCmTH1_HodoMATCH.setLineColor(2);
		FT2.draw(h1_FT_TCmTH1_HodoMATCH,"same");
		h1_FT_TCmTH1_HodoMATCHHODO.setLineColor(3);
		FT2.draw(h1_FT_TCmTH1_HodoMATCHHODO,"same");
		h1_FT_TCmTH1_HodoNOMATCHHODO.setLineColor(4);
		FT2.draw(h1_FT_TCmTH1_HodoNOMATCHHODO,"same");
		
		FT2.cd(10);
		FT2.draw(h1_FT_TCmTH2_HodoREC);
		h1_FT_TCmTH2_HodoMATCH.setLineColor(2);
		FT2.draw(h1_FT_TCmTH2_HodoMATCH,"same");
		h1_FT_TCmTH2_HodoMATCHHODO.setLineColor(3);
		FT2.draw(h1_FT_TCmTH2_HodoMATCHHODO,"same");
		h1_FT_TCmTH2_HodoNOMATCHHODO.setLineColor(4);
		FT2.draw(h1_FT_TCmTH2_HodoNOMATCHHODO,"same");
		
		FT2.cd(11);
		FT2.draw(h1_FT_TH2mTH1_HodoREC);
		h1_FT_TH2mTH1_HodoMATCH.setLineColor(2);
		FT2.draw(h1_FT_TH2mTH1_HodoMATCH,"same");
		h1_FT_TH2mTH1_HodoMATCHHODO.setLineColor(3);
		FT2.draw(h1_FT_TH2mTH1_HodoMATCHHODO,"same");
		h1_FT_TH2mTH1_HodoNOMATCHHODO.setLineColor(4);
		FT2.draw(h1_FT_TH2mTH1_HodoNOMATCHHODO,"same");
		
		TCanvas FT3 = new TCanvas("FT3",800,800);
		FT3.divide(3,4);
		
		FT3.cd(1);
		FT3.draw(h1_FT_RCmRH1_HodoREC);
		h1_FT_RCmRH1_HodoMATCH.setLineColor(2);
		FT3.draw(h1_FT_RCmRH1_HodoMATCH,"same");
		h1_FT_RCmRH1_HodoMATCHHODO.setLineColor(3);
		FT3.draw(h1_FT_RCmRH1_HodoMATCHHODO,"same");
		h1_FT_RCmRH1_HodoNOMATCHHODO.setLineColor(4);
		FT3.draw(h1_FT_RCmRH1_HodoNOMATCHHODO,"same");
		
		FT3.cd(2);
		FT3.draw(h1_FT_RCmRH2_HodoREC);
		h1_FT_RCmRH2_HodoMATCH.setLineColor(2);
		FT3.draw(h1_FT_RCmRH2_HodoMATCH,"same");
		h1_FT_RCmRH2_HodoMATCHHODO.setLineColor(3);
		FT3.draw(h1_FT_RCmRH2_HodoMATCHHODO,"same");
		h1_FT_RCmRH2_HodoNOMATCHHODO.setLineColor(4);
		FT3.draw(h1_FT_RCmRH2_HodoNOMATCHHODO,"same");
	}

	private void doPlotPCAL() {
		ArrayList<H1F> h1_PCAL_E_AllREC = new ArrayList<H1F>();
		getHistoSector1D(h1_PCAL_E_AllREC, "h1_PCAL_E_AllREC");

		ArrayList<H1F> h1_PCAL_E_AllNOTRG1 = new ArrayList<H1F>();
		getHistoSector1D(h1_PCAL_E_AllNOTRG1, "h1_PCAL_E_AllNOTRG1");

		ArrayList<H1F> h1_PCAL_E_AllNOTRG2 = new ArrayList<H1F>();
		getHistoSector1D(h1_PCAL_E_AllNOTRG2, "h1_PCAL_E_AllNOTRG2");

		
		ArrayList<H1F> h1_VTPPCAL_E_AllREC = new ArrayList<H1F>();
		getHistoSector1D(h1_VTPPCAL_E_AllREC, "h1_VTPPCAL_E_AllREC");
		
		ArrayList<H1F> h1_VTPPCAL_E_AllNOTRG1 = new ArrayList<H1F>();
		getHistoSector1D(h1_VTPPCAL_E_AllNOTRG1, "h1_VTPPCAL_E_AllNOTRG1");
		
		ArrayList<H1F> h1_VTPPCAL_E_AllNOTRG2 = new ArrayList<H1F>();
		getHistoSector1D(h1_VTPPCAL_E_AllNOTRG2, "h1_VTPPCAL_E_AllNOTRG2");
		
		ArrayList<H1F> h1_VTPPCAL_E_AllTRG1NOTRG2 = new ArrayList<H1F>();
		getHistoSector1D(h1_VTPPCAL_E_AllTRG1NOTRG2, "h1_VTPPCAL_E_AllTRG1NOTRG2");
		
		
		H2F h2_PCAL_XY_AllREC = (H2F) dirIn.getObject("/histo/h2_PCAL_XY_AllREC");
		H2F h2_PCAL_XY_AllNOTRG1 = (H2F) dirIn.getObject("/histo/h2_PCAL_XY_AllNOTRG1");
		H2F h2_PCAL_XY_AllNOTRG2 = (H2F) dirIn.getObject("/histo/h2_PCAL_XY_AllNOTRG2");
		
		H2F h2_PCAL_XY_AllTRG1NOTRG2 = null;
		h2_PCAL_XY_AllTRG1NOTRG2=(H2F) dirIn.getObject("/histo/h2_PCAL_XY_AllTRG1NOTRG2");
		
		H2F h2_VTPCAL_E_ClusterE_AllREC = null;
		h2_VTPCAL_E_ClusterE_AllREC = (H2F) dirIn.getObject("/histo/h2_VTPCAL_E_ClusterE_AllREC");
		
		TCanvas PCAL1 = new TCanvas("PCAL1", 1600, 1600);
		PCAL1.divide(6, 3);
		for (int isector = 1; isector <= 6; isector++) {
			PCAL1.cd(isector - 1);
			PCAL1.draw(h1_PCAL_E_AllREC.get(isector - 1));
			h1_PCAL_E_AllNOTRG1.get(isector - 1).setLineColor(2);
			PCAL1.draw(h1_PCAL_E_AllNOTRG1.get(isector - 1), "same");
			h1_PCAL_E_AllNOTRG2.get(isector - 1).setLineColor(3);
			PCAL1.draw(h1_PCAL_E_AllNOTRG2.get(isector - 1), "same");
		}
		PCAL1.cd(6);
		PCAL1.draw(h2_PCAL_XY_AllREC,"colz");
		
		PCAL1.cd(7);
		PCAL1.draw(h2_PCAL_XY_AllNOTRG1,"colz");
		
		PCAL1.cd(8);
		PCAL1.draw(h2_PCAL_XY_AllNOTRG2,"colz");
		if (h2_PCAL_XY_AllTRG1NOTRG2!=null) {
			PCAL1.cd(9);
			PCAL1.draw(h2_PCAL_XY_AllTRG1NOTRG2,"colz");
		}
		if (h2_VTPCAL_E_ClusterE_AllREC != null) {
			PCAL1.cd(10);
			PCAL1.draw(h2_VTPCAL_E_ClusterE_AllREC,"colz");
		}
		for (int isector = 1; isector <= 6; isector++) {
			PCAL1.cd(isector+12 - 1);
			PCAL1.draw(h1_VTPPCAL_E_AllREC.get(isector - 1));
			
			h1_VTPPCAL_E_AllNOTRG1.get(isector - 1).setLineColor(2);
			PCAL1.draw(h1_VTPPCAL_E_AllNOTRG1.get(isector - 1), "same");
			
			h1_VTPPCAL_E_AllNOTRG2.get(isector - 1).setLineColor(3);
			PCAL1.draw(	h1_VTPPCAL_E_AllNOTRG2.get(isector - 1), "same");
			
			h1_VTPPCAL_E_AllTRG1NOTRG2.get(isector - 1).setLineColor(4);
			PCAL1.draw(	h1_VTPPCAL_E_AllTRG1NOTRG2.get(isector - 1), "same");
		}
		
	}

	private void doPlotFTOF() {

		ArrayList<H1F> h1_FTOF1B_E_AllREC = new ArrayList<H1F>();
		getHistoSector1D(h1_FTOF1B_E_AllREC, "h1_FTOF1B_E_AllREC");

		ArrayList<H1F> h1_FTOF1B_Esqrt_AllREC = new ArrayList<H1F>();
		getHistoSector1D(h1_FTOF1B_Esqrt_AllREC, "h1_FTOF1B_Esqrt_AllREC");

		ArrayList<H1F> h1_FTOF1B_E_AllNOTRG1 = new ArrayList<H1F>();
		getHistoSector1D(h1_FTOF1B_E_AllNOTRG1, "h1_FTOF1B_E_AllNOTRG1");

		ArrayList<H1F> h1_FTOF1B_Esqrt_AllNOTRG1 = new ArrayList<H1F>();
		getHistoSector1D(h1_FTOF1B_Esqrt_AllNOTRG1, "h1_FTOF1B_Esqrt_AllNOTRG1");

		ArrayList<H2F> h2_FTOF1B_E2_AllNOTRG1 = new ArrayList<H2F>();
		getHistoSector2D(h2_FTOF1B_E2_AllNOTRG1, "h2_FTOF1B_E2_AllNOTRG1");

		ArrayList<H1F> h2_FTOF1B_ID_AllNOTRG1 = new ArrayList<H1F>();
		getHistoSector1D(h2_FTOF1B_ID_AllNOTRG1, "h1_FTOF1B_ID_AllNOTRG1");
		
		ArrayList<H2F> h2_FTOF1BT_Mom_AllREC= new ArrayList<H2F>();
		getHistoSector2D(h2_FTOF1BT_Mom_AllREC,"h2_FTOF1BT_Mom_AllREC");
		
		H2F h2_FTOF1B_XY_AllREC = (H2F) dirIn.getObject("/histo/h2_FTOF1B_XY_AllREC");
		H2F h2_FTOF1B_XY_AllNOTRG1 = (H2F) dirIn.getObject("/histo/h2_FTOF1B_XY_AllNOTRG1");

		TCanvas FTOF1 = new TCanvas("FTOF1", 1600, 1600);
		FTOF1.divide(6, 3);

		for (int isector = 1; isector <= 6; isector++) {
			FTOF1.cd(isector - 1);
			FTOF1.draw(h1_FTOF1B_E_AllREC.get(isector - 1));
			h1_FTOF1B_E_AllNOTRG1.get(isector - 1).setLineColor(2);
			FTOF1.draw(h1_FTOF1B_E_AllNOTRG1.get(isector - 1), "same");

			FTOF1.cd(isector + 5);
			FTOF1.draw(h1_FTOF1B_Esqrt_AllREC.get(isector - 1));
			h1_FTOF1B_Esqrt_AllNOTRG1.get(isector - 1).setLineColor(2);
			FTOF1.draw(h1_FTOF1B_Esqrt_AllNOTRG1.get(isector - 1), "same");

			FTOF1.cd(isector + 11);
			FTOF1.draw(h2_FTOF1B_E2_AllNOTRG1.get(isector - 1), "colz");
		}

		TCanvas FTOF2 = new TCanvas("FTOF2", 1600, 1600);
		FTOF2.divide(6, 3);
		FTOF2.cd(0);
		FTOF2.draw(h2_FTOF1B_XY_AllREC, "colz");
		FTOF2.cd(1);
		FTOF2.draw(h2_FTOF1B_XY_AllNOTRG1, "colz");
		FTOF2.cd(2);
		drawSectorsSameCanvas(FTOF2, h2_FTOF1B_ID_AllNOTRG1);

		for (int isector = 1; isector <= 6; isector++) {
			FTOF2.cd(isector+6 - 1);
			FTOF2.draw(h2_FTOF1BT_Mom_AllREC.get(isector - 1));
		}
		
		
	}

	@SuppressWarnings("unchecked")
	private void doPlotEfficiency() {
		int ibit;

		ArrayList<H1F>[] effHisto = new ArrayList[4];
		for (int ii = 0; ii < 4; ii++) {
			effHisto[ii] = new ArrayList<H1F>();
			ibit = ii + 1;
			getHistoSector1D(effHisto[ii], "h1_MomAllEFFTRG" + ibit);
		}

		TCanvas eff1 = new TCanvas("eff1", 1600, 1600);
		eff1.divide(6, 2); // 6 columns
		for (int isector = 1; isector <= 6; isector++) {
			eff1.cd(isector - 1);
			eff1.getPad().getAxisY().setRange(0.95,1.01);
			for (ibit = 0; ibit < 4; ibit++) {

				if (ibit == 0) {
					eff1.draw(effHisto[ibit].get(isector - 1));
				} else {
					effHisto[ibit].get(isector - 1).setLineColor(ibit + 1);
				
					eff1.draw(effHisto[ibit].get(isector - 1), "same");
		
				
				}
			}
		}

	}

	private void drawSectorsSameCanvas(TCanvas c, ArrayList<H1F> data) {
		for (int isector = 1; isector <= 6; isector++) {
			data.get(isector - 1).setLineColor(isector);
			if (isector == 1)
				c.draw(data.get(0));
			else
				c.draw(data.get(isector - 1), "same");
		}
	}

	private void getHistoSector1D(ArrayList<H1F> data, String name) {
		for (int isector = 1; isector <= 6; isector++) {
			data.add((H1F) dirIn.getObject("/histo/" + name + "_" + isector));
		}
	}

	private void getHistoSector2D(ArrayList<H2F> data, String name) {
		for (int isector = 1; isector <= 6; isector++) {
			data.add((H2F) dirIn.getObject("/histo/" + name + "_" + isector));
		}
	}

	private void doPlotTrigger() {
		H1F hTriggerMultiplicity = (H1F) dirIn.getObject("/histo/hTriggerMultiplicity");
		H1F hTriggerBits = (H1F) dirIn.getObject("/histo/hTriggerBits");
		H1F hTriggerTimes = (H1F) dirIn.getObject("/histo/hTriggerTimes");
		H2F hTriggerTimes2D = (H2F) dirIn.getObject("/histo/hTriggerTimes2D");

		TCanvas trg = new TCanvas("trg", 1600, 1600);
		trg.divide(2, 2);
		trg.cd(0);
		trg.draw(hTriggerMultiplicity);
		trg.cd(1);
		trg.draw(hTriggerBits);
		trg.cd(2);
		trg.draw(hTriggerTimes);
		trg.cd(3);
		trg.draw(hTriggerTimes2D, "colz");

	}

}
