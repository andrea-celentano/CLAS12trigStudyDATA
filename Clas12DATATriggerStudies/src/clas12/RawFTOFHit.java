package clas12;

/*Since the AHit class in TOF package is quite complicate (and I do not need all the features), and the Hit class inherits from hit, it is better to create my own simple c
 * class
 */
public class RawFTOFHit {
	private int _Sector;
	private int _Panel;
	private int _Paddle;
	private float _EnergyL;
	private float _EnergyR;
	private float _TimeL;
	private float _TimeR;
	private float _TimeAVG;

	private int _id;

	

	public RawFTOFHit(int _id, int _Sector, int _Panel, int _Paddle, float _EnergyL, float _EnergyR, float _TimeL, float _TimeR) {

		this._Panel = _Panel;
		this._Sector = _Sector;
		this._Paddle = _Paddle;
		this._EnergyL = _EnergyL;
		this._EnergyR = _EnergyR;
		this._TimeL = _TimeL;
		this._TimeR = _TimeR;
		this._id = _id;
		this._TimeAVG = (this._TimeL + this._TimeR) / 2;

	}

	public RawFTOFHit() {

	}
	
	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_Panel() {
		return _Panel;
	}

	public void set_Panel(int _Panel) {
		this._Panel = _Panel;
	}

	public int get_Sector() {
		return _Sector;
	}

	public void set_Sector(int _Sector) {
		this._Sector = _Sector;
	}

	public int get_Paddle() {
		return _Paddle;
	}

	public void set_Paddle(int _Paddle) {
		this._Paddle = _Paddle;
	}

	public float get_EnergyL() {
		return _EnergyL;
	}

	public void set_EnergyL(float _EnergyL) {
		this._EnergyL = _EnergyL;
	}

	public float get_EnergyR() {
		return _EnergyR;
	}

	public void set_EnergyR(float _EnergyR) {
		this._EnergyR = _EnergyR;
	}

	public float get_TimeL() {
		return _TimeL;
	}

	public void set_TimeL(float _TimeL) {
		this._TimeL = _TimeL;
	}

	public float get_TimeR() {
		return _TimeR;
	}

	public void set_TimeR(float _TimeR) {
		this._TimeR = _TimeR;
	}

	public float get_TimeAVG() {
		return _TimeAVG;
	}

	public void set_TimeAVG(float _TimeAVG) {
		this._TimeAVG = _TimeAVG;
	}

}
