package clas12;

import org.jlab.clas.physics.Particle;
import org.jlab.clas.physics.Vector3;

public class ReconParticleFD extends Particle {

	int TrackIndex;

	boolean hasPCAL, hasFTOF1B;
	float PCALenergy, FTOF1Benergy;
	float FTOF1Btime;
	int PCALsector, FTOF1Bsector;
	
	short status;

	int PCALU_id, FTOF1B_id;

	Vector3 FTOF1B_hit;
	Vector3 PCAL_hit,PCAL_hitUVW;

	RawFTOFHit rawFTOFhit;
	

	public ReconParticleFD(int pid, double px, double py, double pz, double vx, double vy, double vz,short m_status) {
		super(pid, px, py, pz, vx, vy, vz);
		TrackIndex = -1;
		hasPCAL = false;
		hasFTOF1B = false;
		PCALenergy = -1;
		FTOF1Benergy = -1;
		status = m_status;
		rawFTOFhit = new RawFTOFHit();
		
	}

	public ReconParticleFD(int pid, double px, double py, double pz, short m_status) {
		super(pid, px, py, pz);
		TrackIndex = -1;
		hasPCAL = false;
		hasFTOF1B = false;
		PCALenergy = -1;
		FTOF1Benergy = -1;
		status = m_status;
		rawFTOFhit = new RawFTOFHit();
	}

	public ReconParticleFD(int pid, double mass, byte charge, double px, double py, double pz, double vx, double vy, double vz,short m_status) {
		super(pid, mass, charge, px, py, pz, vx, vy, vz);
		TrackIndex = -1;
		hasPCAL = false;
		hasFTOF1B = false;
		PCALenergy = -1;
		FTOF1Benergy = -1;
		FTOF1Btime = -1;
		status = m_status;
		rawFTOFhit = new RawFTOFHit();
	}

	public int getTrackIndex() {
		return TrackIndex;
	}

	public void setTrackIndex(int trackIndex) {
		TrackIndex = trackIndex;
	}

	public boolean hasPCAL() {
		return hasPCAL;
	}

	public void setPCAL() {
		this.hasPCAL = true;
	}

	public void unsetPCAL() {
		this.hasPCAL = false;
	}

	public boolean hasFTOF1B() {
		return hasFTOF1B;
	}

	public void setFTOF1B() {
		this.hasFTOF1B = true;
	}

	public void unsetFTOF1B() {
		this.hasFTOF1B = false;
	}

	public float getPCALenergy() {
		return PCALenergy;
	}

	public void setPCALenergy(float pCALenergy) {
		PCALenergy = pCALenergy;
	}

	public float getFTOF1Benergy() {
		return FTOF1Benergy;
	}

	public void setFTOF1Benergy(float fTOF1Benergy) {
		FTOF1Benergy = fTOF1Benergy;
	}

	public float getFTOF1Btime() {
		return FTOF1Btime;
	}

	public void setFTOF1Btime(float fTOF1Btime) {
		FTOF1Btime = fTOF1Btime;
	}

	public boolean isHasFTOF1B() {
		return hasFTOF1B;
	}

	public void setHasFTOF1B(boolean hasFTOF1B) {
		this.hasFTOF1B = hasFTOF1B;
	}

	public int getPCALsector() {
		return PCALsector;
	}

	public void setPCALsector(int pCALsector) {
		PCALsector = pCALsector;
	}

	public int getFTOF1Bsector() {
		return FTOF1Bsector;
	}

	public void setFTOF1Bsector(int fTOF1Bsector) {
		FTOF1Bsector = fTOF1Bsector;
	}

	public int getPCALU_id() {
		return PCALU_id;
	}

	public void setPCALU_id(int pCALU_id) {
		PCALU_id = pCALU_id;
	}

	public int getFTOF1B_id() {
		return FTOF1B_id;
	}

	public void setFTOF1B_id(int fTOF1B_id) {
		FTOF1B_id = fTOF1B_id;
	}

	public Vector3 getFTOF1B_hit() {
		return FTOF1B_hit;
	}

	public void setFTOF1B_hit(Vector3 fTOF1B_hit) {
		FTOF1B_hit = fTOF1B_hit;
	}

	public Vector3 getPCAL_hit() {
		return PCAL_hit;
	}

	public void setPCAL_hit(Vector3 PCAL_hit) {
		this.PCAL_hit = PCAL_hit;
	}

	public Vector3 getPCAL_hitUVW() {
		return PCAL_hitUVW;
	}
	
	public void setPCAL_hitUVW(Vector3 PCAL_hit) {
		this.PCAL_hitUVW = PCAL_hit;
	}

	
	
	public RawFTOFHit getRawFTOFHit() {
		return this.rawFTOFhit;
	}

	public void setRawFTOFHit(RawFTOFHit hit) {
		this.rawFTOFhit = hit;
	}
	
	public short getStatus() {
		return status;
	}

}
