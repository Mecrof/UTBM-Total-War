package model;

public abstract class Type {
	
	protected int _id;
	protected String _nom;
	protected String _type;
	protected String _adresseImage;
	
	
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String get_nom() {
		return _nom;
	}
	public void set_nom(String _nom) {
		this._nom = _nom;
	}
	public String get_type() {
		return _type;
	}
	public void set_type(String _type) {
		this._type = _type;
	}
	public String get_adresseImage() {
		return _adresseImage;
	}
	public void set_adresseImage(String _adresseImage) {
		this._adresseImage = _adresseImage;
	}
	
	

}
