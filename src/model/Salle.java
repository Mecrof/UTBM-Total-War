package model;

import java.util.ArrayList;

public class Salle {

	private int _id;
	private String _nom;
	private Specialite _atoutSalle;
	private boolean _isOccupe;
	private int _nombreOccupant;
	private int _nombreOccupantEnDeclin;
	private boolean _isSalleDepart;

	private ArrayList<Integer[]> _listeCoordonnees=new ArrayList();
	private ArrayList<Integer> _listeSallesAdjacentes;

	public Salle(int id, String nom, Specialite atoutSalle, boolean isOccupe, ArrayList<Integer[]> listeCoordonnees, ArrayList<Integer> listeSallesAdjacentes, boolean isSalleDepart) {
		this._id = id;
		this._nom = nom;
		this._atoutSalle = atoutSalle;
		this._isOccupe = isOccupe;
		this._nombreOccupant = 0;
		this._nombreOccupantEnDeclin = 0;
		this._listeCoordonnees = listeCoordonnees;
		this._listeSallesAdjacentes = listeSallesAdjacentes;
		this._isSalleDepart = isSalleDepart;
	}
	
	@Override
	public String toString() {
		return "Salle: id:"+_id+" - nom:"+_nom+" - atout:"+_atoutSalle+" - nbreOccupant:"+_nombreOccupant;
	}
	
	public int get_id() {
		return _id;
	}
	
	public String get_nom() {
		return _nom;
	}
	
	public Specialite get_atoutSalle() {
		return _atoutSalle;
	}
	
	public boolean get_isOccupe() {
		return _isOccupe;
	}
	
	public ArrayList<Integer[]> get_listeCoordonnees() {
		return _listeCoordonnees;
	}
	
	public ArrayList<Integer> get_listeSallesAdjacentes() {
		return _listeSallesAdjacentes;
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

	public void set_isOccupe(boolean _isOccupe) {
		this._isOccupe = _isOccupe;
	}

	public int get_nombreOccupantEnDeclin() {
		return _nombreOccupantEnDeclin;
	}

	public void set_nombreOccupantEnDeclin(int _nombreOccupantEnDeclin) {
		this._nombreOccupantEnDeclin = _nombreOccupantEnDeclin;
	}
	
}
