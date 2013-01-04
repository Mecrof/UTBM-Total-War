package controleur;

import model.Joueur;

public class MoveRedeploiement extends Move {
	
	//private Joueur _joueur;
	private int _idSalle;
	private int _nbJetons;
	
	public MoveRedeploiement(MoveType type, int idSalle, int nbJetons) {
		super(type);
		this._idSalle = idSalle;
		this._nbJetons = nbJetons;
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
