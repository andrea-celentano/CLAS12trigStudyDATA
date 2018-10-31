package clas12;

import java.util.List;

import org.jlab.clas.physics.Vector3;
import org.jlab.detector.base.DetectorType;
import org.jlab.io.base.DataBank;
import org.jlab.io.base.DataEvent;

public class DataReaderAndMatcher {

	private AnalysisClass analysisClass;

	private static int adcft1VTP = 125;
	private static int adcft2VTP = 126;
	private static int trig2VTP = 100;
	private static int ftof1VTP = 94;
	private static int pcu1VTP = 107;
	private static int headerVTPFT = 0x17;

	private static final int ID_DC = DetectorType.DC.ordinal();
	private static final int ID_FTOF = DetectorType.FTOF.ordinal();
	private static final int ID_CND = DetectorType.CND.ordinal();
	private static final int ID_CTOF = DetectorType.CTOF.ordinal();
	private static final int ID_CAL = DetectorType.ECAL.ordinal();

	private static final int LAYER_FTOF1B = 2;
	private static final int LAYER_PCAL = 1;

	private float VTP_Emin = 0;

	public DataReaderAndMatcher(AnalysisClass ana) {
		analysisClass = ana;

	}

	public float getVTP_Emin() {
		return VTP_Emin;
	}

	public void setVTP_Emin(float vTP_Emin) {
		VTP_Emin = vTP_Emin;
	}

	public int makeFTReconClusters(DataBank inParticles, DataBank inClusters, DataBank inHits, DataBank hodoClusters, DataBank hodoHits,
			List<ReconFTCluster> outClusters) {
		int nClusters = 0;

		int nrows1 = 0;
		if (inClusters != null) nrows1 = inClusters.rows();

		int nrows2 = 0;
		if (inHits != null) nrows2 = inHits.rows();

		int nrows3 = 0;
		if (inParticles != null) nrows3 = inParticles.rows();

		int nrows4 = 0;
		if (hodoHits != null) nrows4 = hodoHits.rows();

		float energy, time, x, y;
		int idx, idy;
		int idCluster;
		int nHits;

		float time_hit;
		float energy_hit, energy_hit_max;
		int idClusterAssociatedTo;
		int idx_hit, idy_hit;
		float x_hit, y_hit;
		int nHits_hit;

		int idHodoCluster;
		int hodoN, hodoS;
		float hodoE;
		boolean hodoL1, hodoL2;

		short calID = 0;
		byte charge = 0;
		int hodoC, hodoL;
		double hodoHitT, hodoHitE;
		boolean flagParticle = false;
		outClusters.clear();

		idx = 0;
		idy = 0;
		hodoL1 = false;
		hodoL2 = false;
		for (int loop1 = 0; loop1 < nrows1; loop1++) {
			energy = inClusters.getFloat("recEnergy", loop1);
			energy *= 1000; // in MeV
			time = inClusters.getFloat("time", loop1);
			nHits = inClusters.getInt("size", loop1);
			idCluster = inClusters.getInt("id", loop1); // if the cluster has been written to the output, clearly also the corresponding
														// hits are there

			x = inClusters.getFloat("x", loop1);
			y = inClusters.getFloat("y", loop1);
			/* Create the cluster */
			ReconFTCluster cluster = new ReconFTCluster(energy, time, 0, 0);
			nClusters++;
			/* Now check that the number of hits correspond, and also get the seed hit */
			energy_hit_max = 0;
			nHits_hit = 0;
			for (int loop2 = 0; loop2 < nrows2; loop2++) {
				idClusterAssociatedTo = inHits.getInt("clusterID", loop2);
				if (idClusterAssociatedTo != idCluster) continue;

				energy_hit = inHits.getFloat("energy", loop2);
				time_hit = inHits.getFloat("time", loop2);
				idx_hit = inHits.getInt("idx", loop2);
				idy_hit = inHits.getInt("idy", loop2);
				x_hit = inHits.getFloat("x", loop2);
				y_hit = inHits.getFloat("y", loop2);
				if (energy_hit > energy_hit_max) {
					energy_hit_max = energy_hit;
					idx = idx_hit;
					idy = idy_hit;
					x = x_hit;
					y = y_hit;
				}

				FTHit hit = new FTHit(idx_hit, idy_hit, energy_hit, time_hit);
				cluster.addHit(hit);

				nHits_hit++;
			}

			cluster.setIdx(idx);
			cluster.setIdy(idy);
			cluster.setX(x);
			cluster.setY(y);
			flagParticle = false;
			cluster.unsetHodo();

			/*
			 * Now check if we have a matching particle (we should - a part from low energy
			 * clusters), and if so get the charge of it. Also use it to match to the
			 * hodoscope.
			 */
			idHodoCluster = -1;
			for (int loop3 = 0; loop3 < nrows3; loop3++) {

				calID = inParticles.getShort("calID", loop3);
				charge = inParticles.getByte("charge", loop3);
				hodoL1 = false;
				hodoL2 = false;
				if (calID == (idCluster - 1)) { /* This particle has been matched to the hodo cluster */
					flagParticle = true;
					idHodoCluster = inParticles.getShort("hodoID", loop3);

					if (idHodoCluster > 0) { // there is an associated hodo cluster
						hodoN = hodoClusters.getShort("id", idHodoCluster - 1);
						hodoS = hodoClusters.getShort("size", idHodoCluster - 1);
						hodoE = hodoClusters.getFloat("energy", idHodoCluster - 1);
						if (hodoS == 2) {
							for (int loop4 = 0; loop4 < nrows4; loop4++) {
								hodoC = hodoHits.getShort("clusterID", loop4);
								hodoL = hodoHits.getByte("layer", loop4);
								hodoHitE = hodoHits.getFloat("energy", loop4);
								hodoHitT = hodoHits.getFloat("time", loop4);
								x = hodoHits.getFloat("x", loop4);
								y = hodoHits.getFloat("y", loop4);
								if (hodoC == hodoN) {
									if (hodoL == 1) {
										cluster.setH1tag(1);
										cluster.setH1energy(hodoHitE);
										cluster.setH1time(hodoHitT);
										cluster.setH1x(x);
										cluster.setH1y(y);
										hodoL1 = true;
									}
									if (hodoL == 2) {
										cluster.setH2tag(1);
										cluster.setH2energy(hodoHitE);
										cluster.setH2time(hodoHitT);
										cluster.setH2x(x);
										cluster.setH2y(y);
										hodoL2 = true;
									}
								}
							}
						}
					}

					if (hodoL1 && hodoL2) cluster.setHodo();
					break;
				}
			}

			outClusters.add(cluster);
			if (!flagParticle) {
				if (analysisClass.debug)
					System.out.println("Error in makeFTReconClusters, event: " + analysisClass.nevent + " no particle for cluster " + idCluster);
				return 0;
			}
		}
		return nClusters;

	}

	public RunData readHead(DataBank runConfig) {

		long word = runConfig.getLong("trigger", 0);
		int eventn = runConfig.getInt("event", 0);
		int timestamp = runConfig.getInt("unixtime", 0);
		RunData trigger = new RunData(eventn, word, timestamp);

		return trigger;
	}

	public int makeFTVTPClusters(DataBank inClusters, List<VTPFTCluster> outClusters) {
		int nClusters = 0;
		int nClustersVTP = 0;

		int nrows1 = 0;
		if (inClusters != null) nrows1 = inClusters.rows();

		int crate;
		int nwords;
		int word1VTP, word2VTP;

		outClusters.clear();

		for (int loop1 = 0; loop1 < nrows1; loop1++) {
			crate = inClusters.getByte("crate", loop1);
			if ((crate != adcft1VTP) && (crate != adcft2VTP))
				continue;
			else {
				// these are the number of words that follow.
				// first 4 are evthdr(2x) and trigtime(2x). Then, possibly clusters, each is 2
				// words
				nwords = inClusters.getInt("word", loop1);
				loop1++; // the word number has been read

				// check the number of clusters in this VTP
				if (nwords == 4)
					nClustersVTP = 0;
				else
					nClustersVTP = (nwords - 4) / 2;
				// decode them
				loop1 += 4;
				for (int loop2 = 0; loop2 < nClustersVTP; loop2++) {
					word1VTP = inClusters.getInt("word", loop1 + 2 * loop2);
					word2VTP = inClusters.getInt("word", loop1 + 2 * loop2 + 1);
					VTPFTCluster cluster = this.decodeVTPFTCluster(word1VTP, word2VTP);
					if (cluster.getEnergy() > this.VTP_Emin) {
						// if (cluster.getNhits() > 3) outClusters.add(cluster);
						outClusters.add(cluster);
					}
				}
				loop1 += (nwords - 4) - 1; // the other +1 is at the loop end!
				nClusters += nClustersVTP;
			}
		}
		return nClusters;
	}

	private VTPFTCluster decodeVTPFTCluster(int word1, int word2) {

		int h2tag, h1tag, nhits, idx, idy, energy, time;

		if (((word1 >> 27) & 0x1F) != headerVTPFT) {
			System.out.println("decodeVTPFTCluster error! " + String.format("0x%08X", word1) + " " + String.format("0x%08X", word2));
			return null;
		}

		h2tag = (word1 >> 15) & 0x1;
		h1tag = (word1 >> 14) & 0x1;
		nhits = (word1 >> 10) & 0xF;
		idy = (word1 >> 5) & 0x1F;
		idx = (word1 >> 0) & 0x1F;

		energy = (word2 >> 16) & 0x3FFF;
		time = (word2 >> 0) & 0x7FF;

		time = time * 4; // in ns
		// to be compatible with recon clusters, that have idx and idy from 1
		idx = idx + 1;
		idy = idy + 1;

		VTPFTCluster cluster = new VTPFTCluster((float) energy, (float) time, idx, idy);
		cluster.setNhits(nhits);
		cluster.setH1tag(h1tag);
		cluster.setH2tag(h2tag);

		return cluster;
	}

	/* Try to match recon clusters to VTP clusters and vice-versa */
	public void matchClusters(List<? extends FTCluster> ReconClusters, List<? extends FTCluster> VTPClusters) {

		int idx1, idy1;
		int idx2, idy2;

		FTCluster thecluster = null;
		double deltaE = 9999999;
		/* First try to match recon to VTP */
		/*
		 * If there are more VTP clusters close-by (may happen due to the 3x3
		 * requirement), take the one with the closest energy
		 */
		for (FTCluster recCluster : ReconClusters) {
			idx1 = recCluster.getIdx();
			idy1 = recCluster.getIdy();
			thecluster = null;
			deltaE = 9999999;
			for (FTCluster vtpCluster : VTPClusters) {
				idx2 = vtpCluster.getIdx();
				idy2 = vtpCluster.getIdy();
				if (((Math.abs(idx2 - idx1) <= 1) && Math.abs(idy2 - idy1) <= 1)) {
					// recCluster.setMatched();
					// recCluster.setMatchedCluster(vtpCluster);
					if (Math.abs(recCluster.getEnergy() - vtpCluster.getEnergy()) < deltaE) {
						deltaE = Math.abs(recCluster.getEnergy() - vtpCluster.getEnergy());
						thecluster = vtpCluster;
					}
				}
			}
			if (thecluster != null) {
				recCluster.setMatched();
				recCluster.setMatchedCluster(thecluster);
			}
		}

		/* Then match VTP to recon */
		for (FTCluster vtpCluster : VTPClusters) {
			idx1 = vtpCluster.getIdx();
			idy1 = vtpCluster.getIdy();
			thecluster = null;
			deltaE = 9999999;
			for (FTCluster recCluster : ReconClusters) {
				idx2 = recCluster.getIdx();
				idy2 = recCluster.getIdy();
				if (((Math.abs(idx2 - idx1) <= 1) && Math.abs(idy2 - idy1) <= 1)) {
					vtpCluster.setMatched();
					vtpCluster.setMatchedCluster(recCluster);
					if (Math.abs(recCluster.getEnergy() - vtpCluster.getEnergy()) < deltaE) {
						deltaE = Math.abs(recCluster.getEnergy() - vtpCluster.getEnergy());
						thecluster = recCluster;
					}

				}
			}
			if (thecluster != null) {
				vtpCluster.setMatched();
				vtpCluster.setMatchedCluster(thecluster);
			}
		}

	}

	public int makeRawFTOFhits(DataBank inHits, List<RawFTOFHit> outHits[]) {

		if (inHits == null) return 0;

		for (int ii = 0; ii < 6; ii++) {
			outHits[ii].clear();
		}
		int nhits = 0;
		int sector, layer, component;
		float energyL, energyR, timeL, timeR;
		int id;
		for (int ihit = 0; ihit < inHits.rows(); ihit++) {
			layer = inHits.getByte("layer", ihit);
			if (layer != 2) continue; // FTOF-1B
			sector = inHits.getByte("sector", ihit);
			component = inHits.getShort("component", ihit);

			energyL = inHits.getFloat("energy_left", ihit);
			energyR = inHits.getFloat("energy_right", ihit);
			timeL = inHits.getFloat("time_left", ihit);
			timeR = inHits.getFloat("time_right", ihit);

			RawFTOFHit hit = new RawFTOFHit(ihit, sector, layer, component, energyL, energyR, timeL, timeR);

			outHits[sector - 1].add(hit);
			nhits++;
		}

		return nhits;
	}

	public int makeReconParticlesCD(DataBank RecParticlesDB, DataBank RecScintillatorDB, List<ReconParticleCD> ReconParticles) {
		short status, iparticle2, id, component;
		byte charge, detector, sector, layer;
		int pid;
		double p;
		ReconParticles.clear();
		if (RecParticlesDB == null) return 0;

		for (int iparticle = 0; iparticle < RecParticlesDB.rows(); iparticle++) {
			status = RecParticlesDB.getShort("status", iparticle);
			charge = RecParticlesDB.getByte("charge", iparticle);
			pid = RecParticlesDB.getInt("pid", iparticle);

			if (charge == 0) continue; // only charge !=0
			if (status <= 4000) continue; // CD status is >=4000

			if (charge > 0)
				pid = 211;
			else if (charge < 0)
				pid = -211;
			else {
				pid = 22;
				continue; // for the moment, I don't care about neutrals
			}

			p = RecParticlesDB.getFloat("px", iparticle) * RecParticlesDB.getFloat("px", iparticle)
					+ RecParticlesDB.getFloat("py", iparticle) * RecParticlesDB.getFloat("py", iparticle)
					+ RecParticlesDB.getFloat("pz", iparticle) * RecParticlesDB.getFloat("pz", iparticle);

			ReconParticleCD recParticle = new ReconParticleCD(pid, RecParticlesDB.getFloat("px", iparticle), RecParticlesDB.getFloat("py", iparticle),
					RecParticlesDB.getFloat("pz", iparticle), RecParticlesDB.getFloat("vx", iparticle), RecParticlesDB.getFloat("vy", iparticle),
					RecParticlesDB.getFloat("vz", iparticle), RecParticlesDB.getShort("status", iparticle));

			/* Try to associate CND and CTOF */
			if (RecScintillatorDB != null) {

				for (int itof = 0; itof < RecScintillatorDB.rows(); itof++) {
					iparticle2 = RecScintillatorDB.getShort("pindex", itof); // check the particle
					if (iparticle != iparticle2) continue;

					detector = RecScintillatorDB.getByte("detector", itof); // check is CND/CTOF
					if ((detector != ID_CTOF) && (detector != ID_CND)) continue;
					
					sector = RecScintillatorDB.getByte("sector",itof); //check sector;
					layer = RecScintillatorDB.getByte("layer", itof); // check layer
					component = RecScintillatorDB.getShort("component", itof); // check component
					
					id = RecScintillatorDB.getShort("index", itof);

					if (detector == ID_CTOF) {
						recParticle.setCTOF();
						recParticle.setCTOFenergy(RecScintillatorDB.getFloat("energy", itof));
						recParticle.setCTOFtime(RecScintillatorDB.getFloat("time", itof));
						recParticle.setCTOFid(component);  	//CTOF has 48 components
					}

					if (detector == ID_CND) {
						if (layer == 1) {
							recParticle.setCND1();
							recParticle.setCND1energy(RecScintillatorDB.getFloat("energy", itof));
							recParticle.setCND1time(RecScintillatorDB.getFloat("time", itof));
							recParticle.setCND1id((sector-1)*2+component); //CND has 24 sectors (1..24), each with two components (1/2). This goes from 1 to 48
						}
						if (layer == 2) {
							recParticle.setCND2();
							recParticle.setCND2energy(RecScintillatorDB.getFloat("energy", itof));
							recParticle.setCND2time(RecScintillatorDB.getFloat("time", itof));
							recParticle.setCND2id((sector-1)*2+component);//CND has 24 sectors (1..24), each with two components (1/2). This goes from 1 to 48
						}
						if (layer == 3) {
							recParticle.setCND3();
							recParticle.setCND3energy(RecScintillatorDB.getFloat("energy", itof));
							recParticle.setCND3time(RecScintillatorDB.getFloat("time", itof));
							recParticle.setCND3id((sector-1)*2+component);//CND has 24 sectors (1..24), each with two components (1/2). This goes from 1 to 48
						}
					}

				}
			}

			ReconParticles.add(recParticle);

		}
		return ReconParticles.size();
	}

	public int makeReconParticlesFD(DataBank RecParticlesDB, DataBank TBTracksDB, DataBank RecCalorimeterDB, DataBank RecScintillatorDB, DataBank ClustersDB,
			List<RawFTOFHit> FTOFhits[], List<ReconParticleFD> ReconParticles) {
		ReconParticles.clear();
		if (RecParticlesDB == null) return 0;
		if (TBTracksDB == null) return 0;

		int detector, iparticle, iparticle2, id;
		int layer, sectorFTOF, sectorPCAL, sectorCluster;
		int pcalU;
		boolean flagFill = false;
		double p;
		/*
		 * Loop on rec particles and juste select those having q!=0, matched to a track
		 * in DC
		 */

		for (int itrack = 0; itrack < TBTracksDB.rows(); itrack++) {
			detector = TBTracksDB.getByte("detector", itrack);
			if (detector != ID_DC) continue;
			iparticle = TBTracksDB.getShort("pindex", itrack);

			byte charge = RecParticlesDB.getByte("charge", iparticle);
			int pid = RecParticlesDB.getInt("pid", iparticle);
			if (charge > 0)
				pid = 211;
			else if (charge < 0)
				pid = -211;
			else {
				pid = 22;
				continue; // for the moment, I don't care about neutrals
			}

			p = RecParticlesDB.getFloat("px", iparticle) * RecParticlesDB.getFloat("px", iparticle)
					+ RecParticlesDB.getFloat("py", iparticle) * RecParticlesDB.getFloat("py", iparticle)
					+ RecParticlesDB.getFloat("pz", iparticle) * RecParticlesDB.getFloat("pz", iparticle);

			p = Math.sqrt(p);
			if (p < 0.3) continue;

			ReconParticleFD recParticle = new ReconParticleFD(pid, 0.1, charge, RecParticlesDB.getFloat("px", iparticle),
					RecParticlesDB.getFloat("py", iparticle), RecParticlesDB.getFloat("pz", iparticle), RecParticlesDB.getFloat("vx", iparticle),
					RecParticlesDB.getFloat("vy", iparticle), RecParticlesDB.getFloat("vz", iparticle), RecParticlesDB.getShort("status", iparticle));

			recParticle.setTrackIndex(itrack);

			if (RecCalorimeterDB != null) {
				for (int icalo = 0; icalo < RecCalorimeterDB.rows(); icalo++) {
					iparticle2 = RecCalorimeterDB.getShort("pindex", icalo); // check the particle
					if (iparticle != iparticle2) continue;
					detector = RecCalorimeterDB.getByte("detector", icalo); // check is CAL
					if (detector != ID_CAL) continue;
					layer = RecCalorimeterDB.getByte("layer", icalo); // check layer
					if (layer != LAYER_PCAL) continue;

					recParticle.setPCALenergy(RecCalorimeterDB.getFloat("energy", icalo));
					recParticle.setPCALsector(RecCalorimeterDB.getByte("sector", icalo));
					recParticle.setPCAL_hit(
							new Vector3(RecCalorimeterDB.getFloat("x", icalo), RecCalorimeterDB.getFloat("y", icalo), RecCalorimeterDB.getFloat("z", icalo)));

					// ANDREA: TEMPORARY! RAFFA DIDN'T FILTER CLUSTERS DB//
					recParticle.setPCAL();
					recParticle.setPCALU_id(10);
					// END ANDREA TEMPORARY

				}
				if (ClustersDB != null) {
					for (int icalo2 = 0; icalo2 < ClustersDB.rows(); icalo2++) {
						layer = ClustersDB.getByte("layer", icalo2);
						if (layer != LAYER_PCAL) continue; // ignore non-PCAL clusters
						sectorCluster = ClustersDB.getByte("sector", icalo2);
						if (sectorCluster != recParticle.getPCALsector()) continue; // ignore PCAL clusters in other sectors
						/* I don't see a simple way to match ECAL::clusters with REC::calorimeter */
						Vector3 tmp = new Vector3(ClustersDB.getFloat("x", icalo2), ClustersDB.getFloat("y", icalo2), ClustersDB.getFloat("z", icalo2));
						if ((tmp.compare(recParticle.getPCAL_hit())) > 0.01) {
							continue; // ugly!
						}

						double u = ClustersDB.getInt("coordU", icalo2);
						double v = ClustersDB.getInt("coordV", icalo2);
						double w = ClustersDB.getInt("coordW", icalo2);

						// go to strip units
						// U goes in rec from 0 to 500
						// V goes in rec from 0 to 545
						// W goes in rec from 0 to 545

						// u = (u - 4) / 8. + 1;
						// v = (v - 4) / 8. + 1;
						// w = (w - 4) / 8. + 1;

						recParticle.setPCAL();
						recParticle.setPCAL_hitUVW(new Vector3(u, v, w));

					}
				}
			}

			if (RecScintillatorDB != null) {

				for (int itof = 0; itof < RecScintillatorDB.rows(); itof++) {
					iparticle2 = RecScintillatorDB.getShort("pindex", itof); // check the particle
					if (iparticle != iparticle2) continue;
					detector = RecScintillatorDB.getByte("detector", itof); // check is FTOF
					if (detector != ID_FTOF) continue;
					layer = RecScintillatorDB.getByte("layer", itof); // check layer
					if (layer != LAYER_FTOF1B) continue;

					id = RecScintillatorDB.getShort("index", itof);

					recParticle.setFTOF1B();
					recParticle.setFTOF1Benergy(RecScintillatorDB.getFloat("energy", itof));
					recParticle.setFTOF1Bsector(RecScintillatorDB.getByte("sector", itof));
					recParticle.setFTOF1Btime(RecScintillatorDB.getFloat("time", itof));
					recParticle.setFTOF1B_id(RecScintillatorDB.getShort("component", itof));
					recParticle.setFTOF1B_hit(
							new Vector3(RecScintillatorDB.getFloat("x", itof), RecScintillatorDB.getFloat("y", itof), RecScintillatorDB.getFloat("z", itof)));

					/* Now search for the raw hit */
					boolean flagMatchFTOFraw = false;
					RawFTOFHit the_hit = null;
					for (RawFTOFHit hit : FTOFhits[recParticle.getFTOF1Bsector() - 1]) {
						if (hit.get_id() == id) {
							flagMatchFTOFraw = true;

							double E_L = hit.get_EnergyL();
							double E_R = hit.get_EnergyR();
							double E = Math.sqrt(E_L * E_R);

							the_hit = hit;
							// Loop over all FTOF raw hits in this sector, check if there's one nearby (+-2)
							// with larger energy. If so, use it.
							for (RawFTOFHit hit2 : FTOFhits[recParticle.getFTOF1Bsector() - 1]) {
								double E_L2 = hit2.get_EnergyL();
								double E_R2 = hit2.get_EnergyR();
								double E2 = Math.sqrt(E_L2 * E_R2);
								if ((Math.abs(hit2.get_Paddle() - hit.get_Paddle()) <= 2) && (E2 > E)) {
									the_hit = hit2;
								}
							}
							break;
						}
					}
					if (flagMatchFTOFraw == false) {
						System.out.println("Error in dataReader and matcher no raw ftof hit");
					} else {
						recParticle.setRawFTOFHit(the_hit);
					}

				}
			}
			ReconParticles.add(recParticle);
		}
		return ReconParticles.size();
	}

	public int makeVTPTriggers(DataBank VTPDB, List<VTPTrigger> VTPTriggers, List<VTPmask> FTOFmask[], List<VTPmask> PCUmask[],
			List<VTPECCluster> PCALVTPclusters[]) {

		int nVTPTriggers = 0;

		int nrows1 = 0;
		if (VTPDB != null) nrows1 = VTPDB.rows();

		int crate;
		int ftofsector;
		int pcusector;
		int pcalsector;
		int nwords;
		int word1VTP, word2VTP, word3VTP;

		VTPTriggers.clear();
		for (int ii = 0; ii < 6; ii++) {
			FTOFmask[ii].clear();
			PCUmask[ii].clear();
			PCALVTPclusters[ii].clear();
		}
		int loop1 = 0;
		while (loop1 < nrows1) {
			crate = VTPDB.getByte("crate", loop1);
			ftofsector = crate - ftof1VTP;
			pcusector = crate - pcu1VTP;
			if (crate == trig2VTP) {
				nwords = VTPDB.getInt("word", loop1);
				loop1++; // the word number has been read
				if (nwords == 4)
					nVTPTriggers = 0;
				else
					nVTPTriggers = (nwords - 4) / 2;
				// decode them
				loop1 += 4;
				for (int loop2 = 0; loop2 < nVTPTriggers; loop2++) {
					word1VTP = VTPDB.getInt("word", loop1++);
					if (((word1VTP >> 27) & 0x1F) == 0x1D) {
						word2VTP = VTPDB.getInt("word", loop1++);
						VTPTrigger trigger = this.decodeVTPTrigger(word1VTP, word2VTP);
						VTPTriggers.add(trigger);
					}
				}
			} else if (ftofsector >= 0 && ftofsector <= 5) {
				nwords = VTPDB.getInt("word", loop1);
				loop1++; // the word number has been read
				if (nwords == 4)
					nVTPTriggers = 0;
				else
					nVTPTriggers = (nwords - 4) / 3;
				// decode them
				loop1 += 4;
				for (int loop2 = 0; loop2 < nVTPTriggers; loop2++) {
					word1VTP = VTPDB.getInt("word", loop1++);
					if (((word1VTP >> 27) & 0x1F) == 0x18) {
						word2VTP = VTPDB.getInt("word", loop1++);
						word3VTP = VTPDB.getInt("word", loop1++);
						VTPmask mask = this.decodeVTPFTOFmask(ftofsector, word1VTP, word2VTP, word3VTP);
						FTOFmask[ftofsector].add(mask);
					}
				}
			} else if (pcusector >= 0 && pcusector <= 5) {
				nwords = VTPDB.getInt("word", loop1);
				loop1++; // the word number has been read
				nwords = nwords - 4; // first 4 words are not interesting for now
				// decode them
				loop1 += 4;
				for (int loop2 = 0; loop2 < nwords; loop2++) {
					word1VTP = VTPDB.getInt("word", loop1++);

					/* Case of PCU */
					if (((word1VTP >> 27) & 0x1F) == 0x1B) {
						word2VTP = VTPDB.getInt("word", loop1++);
						loop2++;
						word3VTP = VTPDB.getInt("word", loop1++);
						loop2++;
						VTPmask mask = this.decodeVTPPCUmask(pcusector, word1VTP, word2VTP, word3VTP);
						PCUmask[pcusector].add(mask);
					}
					/* Case of PCAL cluster */
					else if (((word1VTP >> 27) & 0x1F) == 0x15) {
						word2VTP = VTPDB.getInt("word", loop1++);
						loop2++;
						VTPECCluster cluster = this.decodeVTPECCluster(pcusector, word1VTP, word2VTP);
						/* Some VTP clusters are reported multiple times. Following lines avoid this */
						boolean flagDuplicate = false;
						for (VTPECCluster cluster2 : PCALVTPclusters[pcusector]) {
							if (cluster.isDuplicate(cluster2)) {
								flagDuplicate = true;
								break;
							}
						}
						if (flagDuplicate == false) {
							PCALVTPclusters[pcusector].add(cluster);
						}
					}
				}
			}

			else {
				loop1++;
			}
		}

		return VTPTriggers.size();

	}

	private VTPECCluster decodeVTPECCluster(int pcusector, int word1vtp, int word2vtp) {
		int energy, time;
		double u, v, w;

		time = (word1vtp >> 16) & 0XFF; // 8 bits time
		energy = (word1vtp) & 0xFFFF; // 16 bits energy

		w = (word2vtp >> 20) & 0x3FF; // 10 bits W
		v = (word2vtp >> 10) & 0x3FF; // 10 bits V
		u = (word2vtp >> 00) & 0x3FF; // 10 bits U

		// go to strip units as suggested by rafo
		u = u / 2.75;
		v = v / 3;
		w = w / 3;

		VTPECCluster cluster = new VTPECCluster(pcusector, energy, time, u, v, w);
		return cluster;
	}

	private VTPmask decodeVTPFTOFmask(int sector, int word1vtp, int word2vtp, int word3vtp) {
		int time, trgL, trgM, trgH;
		time = (word1vtp >> 16) & 0x7FF; // 11 bits time
		trgM = (word2vtp & 0x7FFFFFFF); // 31 bits
		trgL = (word3vtp & 0x7FFFFFFF); // 31 bits

		/* Fix - for 32 bits */
		if ((trgM & 0x1) == 0x1) trgL = trgL | 0x1 << 31;
		trgM = trgM >> 1;

		trgH = 0;
		VTPmask mask = new VTPmask(sector, trgL, trgM, trgH, time);
		return mask;
	}

	private VTPmask decodeVTPPCUmask(int sector, int word1vtp, int word2vtp, int word3vtp) {
		int time, trgL, trgM, trgH;
		time = (word1vtp >> 16) & 0x7FF; // 11 bits time
		trgH = (word1vtp & 0x3F); // 6 bits
		trgM = (word2vtp & 0x7FFFFFFF); // 31 bits
		trgL = (word3vtp & 0x7FFFFFFF); // 31 bits

		/* Fix - for 32 bits */
		if ((trgM & 0x1) == 0x1) trgL = trgL | 1 << 31;
		trgM = trgM >> 1;

		if ((trgH & 0x1) == 0x1) trgM = trgM | 1 << 30;
		if ((trgH & 0x2) == 0x2) trgM = trgM | 1 << 31;

		trgH = trgH >> 2;

		VTPmask mask = new VTPmask(sector, trgL, trgM, trgH, time);
		return mask;
	}

	private VTPTrigger decodeVTPTrigger(int word1vtp, int word2vtp) {
		int time, trgL, trgH;
		time = (word1vtp >> 16) & 0x7FF; // 11 bits time
		trgL = (word1vtp & 0xFFFF); // 16 bits
		trgH = (word2vtp & 0xFFFF); // 16 bits
		VTPTrigger trigger = new VTPTrigger(trgL, trgH, time);
		return trigger;
	}

}