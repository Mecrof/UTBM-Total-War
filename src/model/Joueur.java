package model;

import java.util.ArrayList;
import java.util.LinkedList;

public class Joueur {

	private String _nom;
	private int _idEtudiantInactif;
	private int _idEtudiantActif;
	private int _idSpecialiteInactif;
	private int _idSpecialiteActif;
	private int _effectifDispo;
	private int _ptVictoire;
	private ArrayList<Integer> _listIdSalleOccupee;
	//private ArrayList<Integer> _listIDSalleOccupeeEnDeclin
	
	public Joueur() {
		this.reinitialiser();
		//_listIdSalleOccupee.add(new Integer(6));
		//_listIdSalleOccupee.add(new Integer(18));
	}
	
	public void reinitialiser()
	{
		this._nom = new String();
		this._effectifDispo = 10;
		this._ptVictoire = 5;
		this._listIdSalleOccupee = new ArrayList<Integer>();
	}

	public String get_nom() {
		return _nom;
	}

	public void set_nom(String _nom) {
		this._nom = _nom;
	}

	public int get_idEtudiantInactif() {
		return _idEtudiantInactif;
	}

	public void set_idEtudiantInactif(int _idEtudiantInactif) {
		this._idEtudiantInactif = _idEtudiantInactif;
	}

	public int get_idEtudiantActif() {
		return _idEtudiantActif;
	}

	public void set_idEtudiantActif(int _idEtudiantActif) {
		this._idEtudiantActif = _idEtudiantActif;
	}

	public int get_effectifDispo() {
		return _effectifDispo;
	}

	public void set_effectifDispo(int _effectifDispo) {
		this._effectifDispo = _effectifDispo;
	}

	public int get_ptVictoire() {
		return _ptVictoire;
	}

	public void set_ptVictoire(int _ptVictoire) {
		this._ptVictoire = _ptVictoire;
	}

	public ArrayList<Integer> get_listIdSalleOccupee() {
		return _listIdSalleOccupee;
	}

	public void set_listIdSalleOccupee(ArrayList<Integer> _listIdSalleOccupee) {
		this._listIdSalleOccupee = _listIdSalleOccupee;
	}

	public int get_idSpecialiteInactif() {
		return _idSpecialiteInactif;
	}

	public void set_idSpecialiteInactif(int _idSpecialiteInactif) {
		this._idSpecialiteInactif = _idSpecialiteInactif;
	}

	public int get_idSpecialiteActif() {
		return _idSpecialiteActif;
	}

	public void set_idSpecialiteActif(int _idSpecialiteActif) {
		this._idSpecialiteActif = _idSpecialiteActif;
	}
	
	
}
