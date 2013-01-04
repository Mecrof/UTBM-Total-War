package controleur;

import model.Joueur;

public class MoveDeclin extends Move {
	
	private Joueur _joueur;
	private int _nouveauIdEtudiant;

	public MoveDeclin( MoveType type, Joueur joueur, int nouveauID) {
		super(type);
		this._joueur = joueur;
		this._nouveauIdEtudiant = nouveauID;
	}

	public Joueur get_joueur() {
		return _joueur;
	}

	public void set_joueur(Joueur _joueur) {
		this._joueur = _joueur;
	}

	public int get_nouveauIdEtudiant() {
		return _nouveauIdEtudiant;
	}

	public void set_nouveauIdEtudiant(int _nouveauIdEtudiant) {
		this._nouveauIdEtudiant = _nouveauIdEtudiant;
	}

}
