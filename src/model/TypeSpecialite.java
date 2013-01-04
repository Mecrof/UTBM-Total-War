package model;

public class TypeSpecialite extends Type {
	public static int compteur = 0;

	public TypeSpecialite( String nom, Specialite type, String adresseImage) {
		super(compteur, nom, type, adresseImage);
		compteur++;
	}
}
