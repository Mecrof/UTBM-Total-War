package model;

public class TypeEtudiant extends Type {
	private static int compteur=0;
	private boolean _isInactif;
	private TypeSpecialite _specialite;
	
	public TypeEtudiant(String nom, Specialite type, String adresseImage, TypeSpecialite spe) {
		super(compteur, nom, type, adresseImage);
		this._specialite = spe;
		compteur++;
	}
	
	public boolean isInactif() {
		return _isInactif;
	}
	public void set_Inactif(boolean _isInactif) {
		this._isInactif = _isInactif;
	}
	public TypeSpecialite get_specialite() {
		return _specialite;
	}
	public void set_specialite(TypeSpecialite _specialite) {
		this._specialite = _specialite;
	}

}
