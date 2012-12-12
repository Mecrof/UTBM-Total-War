package model;

public class TypeSpecialite extends Type {
	public static int compteur = 0;

	public TypeSpecialite( String nom, String type, String adresseImage) {
		this._id =compteur;
		this._nom = nom;
		this._type = type;
		this._adresseImage = adresseImage;
		compteur++;
	}
}
