package controleur;

import model.Joueur;

public class Move {
	
	private MoveType _type;
	

	public Move(MoveType type) 
	{
		_type = type;
	}

	public MoveType get_type() {
		return _type;
	}

	public void set_type(MoveType _type) {
		this._type = _type;
	}
}
