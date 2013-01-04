package model;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public abstract class Type {
	
	protected int _id;
	protected String _nom;
	protected Specialite _type;
	protected String _adresseImage;
	protected Image _image;
	
	public Type(int id, String nom, Specialite type, String adresseImage) {
		this._id = id;
		this._nom = nom;
		this._type = type;
		this._adresseImage = adresseImage;
		this.chargerImage();
	}
	
	public void chargerImage()
	{
		try{
			this._image = ImageIO.read(new File(this._adresseImage));
		}
		catch(Exception e)
		{
			System.out.println("probleme : "+_adresseImage);
			e.printStackTrace();
		}
	}
	
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
	public Specialite get_type() {
		return _type;
	}
	public void set_type(Specialite _type) {
		this._type = _type;
	}
	public String get_adresseImage() {
		return _adresseImage;
	}
	public void set_adresseImage(String _adresseImage) {
		this._adresseImage = _adresseImage;
	}

	public Image get_image() {
		return _image;
	}

	public void set_image(Image _image) {
		this._image = _image;
	}
	
	

}
