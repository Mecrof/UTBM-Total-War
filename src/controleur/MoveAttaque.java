package controleur;

import model.Joueur;

public class MoveAttaque extends Move {
	
	private Joueur _joueur;
	private int _idSalle;
	private int _nbJetons;

	public MoveAttaque(MoveType type, Joueur j, int idSalle, int nbJetons) {
		super(type);
		this._joueur = j;
		this._idSalle = idSalle;
		this._nbJetons = nbJetons;
	}

	public Joueur get_joueur() {
		return _joueur;
	}

	public void set_joueur(Joueur _joueur) {
		this._joueur = _joueur;
	}

	public int get_idSalle() {
		return _idSalle;
	}

	public void set_idSalle(int _idSalle) {
		this._idSalle = _idSalle;
	}

	public int get_nbJetons() {
		return _nbJetons;
	}

	public void set_nbJetons(int _nbJetons) {
		this._nbJetons = _nbJetons;
	}

}
