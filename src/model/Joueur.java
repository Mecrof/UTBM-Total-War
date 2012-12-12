package model;

import java.util.LinkedList;

public class Joueur {

	private String _nom;
	private int _idEtudiantInactif;
	private int _idEtudiantActif;
	private int _idSpecialiteInactif;
	private int _idSpecialiteActif;
	private int _effectifDispo;
	private int _ptVictoire;
	private LinkedList<Integer> _listIdSalleOccupee;
	
	public Joueur() {
		this._nom = new String();
		this._effectifDispo = 10;
		this._ptVictoire = 5;
		this._listIdSalleOccupee = new LinkedList<Integer>();
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

	public LinkedList<Integer> get_listIdSalleOccupee() {
		return _listIdSalleOccupee;
	}

	public void set_listIdSalleOccupee(LinkedList<Integer> _listIdSalleOccupee) {
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
