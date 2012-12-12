package model;

public class Salle {

	private int _id;
	private String _nom;
	private String _atoutSalle;
	private boolean _isOccupe;
	private int _nombreOccupant;
	private boolean _isSalleDepart;
	private String[] _tabCoordonnees;
	private String[] _tabSallesAdjacentes;
	

	public Salle(int id, String nom, String atoutSalle, boolean isOccupe, String[] _tabCoordonnees, String[] tabSallesAdjacentes, boolean isSalleDepart) {
		this._id = id;
		this._nom = nom;
		this._atoutSalle = atoutSalle;
		this._isOccupe = isOccupe;
		this._nombreOccupant = 0;
		this._tabCoordonnees = _tabCoordonnees;
		this._tabSallesAdjacentes = tabSallesAdjacentes;
		this._isSalleDepart = isSalleDepart;
	}
	
	public int get_id() {
		return _id;
	}
	
	public String get_nom() {
		return _nom;
	}
	
	public String get_atoutSalle() {
		return _atoutSalle;
	}
	
	public boolean get_isOccupe() {
		return _isOccupe;
	}
	
	public String[] get_tabCoordonnees() {
		return _tabCoordonnees;
	}
	
	public String[] get_tabSallesAdjacentes() {
		return _tabSallesAdjacentes;
	}
	
	public boolean get_isSalleDepart() {
		return _isSalleDepart;
	}

	public int get_nombreOccupant() {
		return _nombreOccupant;
	}

	public void set_nombreOccupant(int _nombreOccupant) {
		this._nombreOccupant = _nombreOccupant;
	}
	
}
