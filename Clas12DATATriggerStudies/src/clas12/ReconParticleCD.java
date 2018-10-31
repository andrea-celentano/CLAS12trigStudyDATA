package clas12;

import org.jlab.clas.physics.Particle;
import org.jlab.clas.physics.Vector3;

public class ReconParticleCD extends Particle {

	
	boolean hasCTOF,hasCND1,hasCND2,hasCND3;
	
	int CTOFid,CND1id,CND2id,CND3id;

	int status;
	
	

	double CTOFenergy,CTOFtime;
	double CND1energy,CND1time;
	double CND2energy,CND2time;
	double CND3energy,CND3time;
	
	
	
	public ReconParticleCD(int pid, double px, double py, double pz, double vx, double vy, double vz,short m_status) {
		super(pid, px, py, pz, vx, vy, vz);
		hasCTOF=false;
		hasCND1=false;
		hasCND2=false;
		hasCND3=false;
		CTOFid=-1;
		CND1id=-1;
		CND2id=-1;
		CND3id=-1;
		status=m_status;
	}

	public ReconParticleCD(int pid, double px, double py, double pz, short m_status) {
		super(pid, px, py, pz);
		hasCTOF=false;
		hasCND1=false;
		hasCND2=false;
		hasCND3=false;
		CTOFid=-1;
		CND1id=-1;
		CND2id=-1;
		CND3id=-1;
		status=m_status;
	}

	public boolean hasCTOF() {
		return hasCTOF;
	}

	public void setCTOF() {
		this.hasCTOF = true;
	}
	public void unsetCTOF() {
		this.hasCTOF = false;
	}

	public boolean hasCND1() {
		return hasCND1;
	}

	public void setCND1() {
		this.hasCND1 = true;
	}
	public void unsetCND1() {
		this.hasCND1 = false;
	}

	public boolean hasCND2() {
		return hasCND2;
	}

	public void setCND2(){
		this.hasCND2 = true;
	}
	
	public void unsetCND2(){
		this.hasCND2 = false;
	}

	public boolean hasCND3() {
		return hasCND3;
	}

	public void setCND3() {
		this.hasCND3 = true;
	}
	public void unsetCND3() {
		this.hasCND3 = false;
	}

	public int getCTOFid() {
		return CTOFid;
	}

	public void setCTOFid(int cTOFid) {
		CTOFid = cTOFid;
	}

	public int getCND1id() {
		return CND1id;
	}

	public void setCND1id(int cND1id) {
		CND1id = cND1id;
	}

	public int getCND2id() {
		return CND2id;
	}

	public void setCND2id(int cND2id) {
		CND2id = cND2id;
	}

	public int getCND3id() {
		return CND3id;
	}

	public void setCND3id(int cND3id) {
		CND3id = cND3id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
	public double getCTOFenergy() {
		return CTOFenergy;
	}

	public void setCTOFenergy(double cTOFenergy) {
		CTOFenergy = cTOFenergy;
	}

	public double getCTOFtime() {
		return CTOFtime;
	}

	public void setCTOFtime(double cTOFtime) {
		CTOFtime = cTOFtime;
	}

	public double getCND1energy() {
		return CND1energy;
	}

	public void setCND1energy(double cND1energy) {
		CND1energy = cND1energy;
	}

	public double getCND1time() {
		return CND1time;
	}

	public void setCND1time(double cND1time) {
		CND1time = cND1time;
	}

	public double getCND2energy() {
		return CND2energy;
	}

	public void setCND2energy(double cND2energy) {
		CND2energy = cND2energy;
	}

	public double getCND2time() {
		return CND2time;
	}

	public void setCND2time(double cND2time) {
		CND2time = cND2time;
	}

	public double getCND3energy() {
		return CND3energy;
	}

	public void setCND3energy(double cND3energy) {
		CND3energy = cND3energy;
	}

	public double getCND3time() {
		return CND3time;
	}

	public void setCND3time(double cND3time) {
		CND3time = cND3time;
	}

	
}
