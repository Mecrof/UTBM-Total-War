package model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Game {
	private boolean _isOver;
	private boolean _isStarted;
	private ArrayList<Type[]> _coupleEtuSpe = new ArrayList<Type[]>(5);
	private Joueur j1 = new Joueur();
	private Joueur j2 = new Joueur();
	

	private ArrayList<TypeEtudiant> _listEtudiant = new ArrayList<TypeEtudiant>();
	private ArrayList<TypeSpecialite> _listSpecialite = new ArrayList<TypeSpecialite>();
	// private ArrayList<Joueur> _joueurs = new ArrayList<Joueur>();
	private Batiment _batiment;
	
	public Game() {
		creerType();
	}

	//m�lange la liste d'�tudiants et de sp�cialit�
	public void shuffle() {
		Collections.shuffle(_listEtudiant);
		Collections.shuffle(_listSpecialite);
	}
	
	private ArrayList<Salle> calculSalleAttaquable(Joueur joueur, int effectifEngage)
	{
		ArrayList<Salle> listSalle = new ArrayList<Salle>();
		for (Integer idSalle : joueur.get_listIdSalleOccupee()) {
			Salle salle;
			if ( (salle = this.getSalle(idSalle.intValue())) != null)
			{
				if (effectifEngage >= 2)
				{
					if (this.isAttaquable(salle, effectifEngage))
						listSalle.add(salle);
					/*
					if (!salle.get_isOccupe())
						listSalle.add(salle);
					else if (salle.get_nombreOccupant()<effectifEngage)
						listSalle.add(salle);
						*/
				}
			}
		}
		if (listSalle.isEmpty())
			return null;
		else
			return listSalle;
	}
	
	public ArrayList<Salle> getSalleAttaquable(Joueur j, int effectif)
	{
		ArrayList<Salle> listSalle = new ArrayList<Salle>();
		if (!j.get_listIdSalleOccupee().isEmpty())
		{
			listSalle = this.calculSalleAttaquable(j, effectif);
		}
		else
		{
			for (Salle salle : this._batiment.get_listePremiereSalle())
				if (this.isAttaquable(salle, effectif))
					listSalle.add(this._batiment.get_listePremiereSalle().get(0));
		}
		return listSalle;
	}
	
	public boolean isAttaquable(Salle salle, int effectifEngage)
	{
		if (!salle.get_isOccupe())
			return true;//listSalle.add(salle);
		else if (salle.get_nombreOccupant()<effectifEngage)
			return true;
		return false;
	}
	
	public static BufferedImage redimensionnerImage(Image src, int srcWidth, int srcHeight, int width, int height) {
	    //ImageIcon tst = new ImageIcon(source);
	    float w = srcWidth, h = srcHeight;
	    float i = w/h;
	    if (i<1)
	    { 
	    	w = width*i; h = height; 
	    }
	    else if (i>1)
	    { 
	    	i = h/w;
	    	w = width; h = height*i; 
	    }
	    else
	    { 
	    	w = width; h = height; 
	    }
	    	
	    /* On crée une nouvelle image aux bonnes dimensions. */
	    BufferedImage buf = new BufferedImage((int)w, (int)h, BufferedImage.TYPE_INT_ARGB);

	    /* On dessine sur le Graphics de l'image bufferisée. */
	    Graphics2D g = buf.createGraphics();
	    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

	    g.drawImage(src, 0, 0, (int)w, (int)h, null);
	    	
	    g.dispose();

	    /* On retourne l'image bufferisée, qui est une image. */
	    return buf;
	}

	//reg�n�rer des couples de Etudiants/Specialit�
	public void refillCoupleEtuSpe() {
		//si stack pleine, on fait rien
		if (_coupleEtuSpe.size() == 5)
			return;
		//si le stack n'est pas plein, on reg�n�re le le stack visible
		else {
			while (_coupleEtuSpe.size() < 5) {
				int randEtu = (int) (Math.random() * (_listEtudiant.size()));
				int randSpe = (int) (Math.random() * (_listSpecialite.size()));

				TypeEtudiant etu = _listEtudiant.get(randEtu);
				TypeSpecialite spe = _listSpecialite.get(randSpe);
				if (!contientElements(etu) || !contientElements(spe)) {
					_coupleEtuSpe.add(new Type[]{etu, spe});
				}
			}

		}

	}
	
	private boolean contientElements(Type t)
	{
		for (int i = 0; i<_coupleEtuSpe.size(); ++i)
			if (_coupleEtuSpe.get(i)[0].equals(t)||_coupleEtuSpe.get(i)[1].equals(t))
				return true;
		return false;
	}
	
	//regénère de 0 les couples visibles
	public ArrayList<Type[]> renewVisibleCoupleEtuSpe()
	{
		shuffle();
		_coupleEtuSpe.clear();
		refillCoupleEtuSpe();
		return _coupleEtuSpe;
	}

	//retourne les couples �tudiants/sp�cialit� visible
	public ArrayList<Type[]> getVisibleCoupleEtuSpe() {
		shuffle();
		refillCoupleEtuSpe();
		return _coupleEtuSpe;
	}

	
	//cr�e les types
	public void creerType() {
		TypeSpecialite Geek;
		Geek = new TypeSpecialite("Geek", "Geek", "");
		TypeSpecialite Mecano;
		Mecano = new TypeSpecialite("Mecano", "Mecano", "");
		TypeSpecialite Elec;
		Elec = new TypeSpecialite("Elec", "Elec", "");
		TypeSpecialite Gribouilleur;
		Gribouilleur = new TypeSpecialite("Gribouilleur", "Gribouilleur", "");
		TypeSpecialite Management;
		Management = new TypeSpecialite("Management", "Management", "");
		TypeSpecialite Matheux;
		Matheux = new TypeSpecialite("Matheux", "Matheux", "");
		TypeSpecialite Langues;
		Langues = new TypeSpecialite("Langues", "Langues", "");
		TypeSpecialite CultureGenerale;
		CultureGenerale = new TypeSpecialite("CultureGenerale", "CultureGenerale", "");
		TypeSpecialite PhysiqueChimie;
		PhysiqueChimie = new TypeSpecialite("PhysiqueChimie", "PhysiqueChimie", "");
		TypeSpecialite Intello;
		Intello = new TypeSpecialite("Intello", "Intello", "");

		_listSpecialite.add(Geek);
		_listSpecialite.add(Mecano);
		_listSpecialite.add(Elec);
		_listSpecialite.add(Gribouilleur);
		_listSpecialite.add(Management);
		_listSpecialite.add(Matheux);
		_listSpecialite.add(Langues);
		_listSpecialite.add(CultureGenerale);
		_listSpecialite.add(PhysiqueChimie);
		_listSpecialite.add(Intello);

		TypeEtudiant GI;
		GI = new TypeEtudiant("GI", Geek.get_nom(), "", null);
		TypeEtudiant EE;
		EE = new TypeEtudiant("EE", Elec.get_nom(), "", null);
		TypeEtudiant GMC;
		GMC = new TypeEtudiant("GMC", Mecano.get_nom(), "", null);
		TypeEtudiant EDIM;
		EDIM = new TypeEtudiant("EDIM", Gribouilleur.get_nom(), "", null);
		TypeEtudiant IMAP;
		IMAP = new TypeEtudiant("IMAP", Management.get_nom(), "", null);
		TypeEtudiant TC;
		TC = new TypeEtudiant("TC", PhysiqueChimie.get_nom(), "", null);
		TypeEtudiant Doctorant;
		Doctorant = new TypeEtudiant("Doctorant", Intello.get_nom(), "", null);

		_listEtudiant.add(GI);
		_listEtudiant.add(EE);
		_listEtudiant.add(GMC);
		_listEtudiant.add(EDIM);
		_listEtudiant.add(IMAP);
		_listEtudiant.add(TC);
		_listEtudiant.add(Doctorant);
	}
	
	public TypeSpecialite getSpecialite(int id) throws Exception
	{
		for (int i = 0; i < _listSpecialite.size(); ++i) {
			TypeSpecialite s = _listSpecialite.get(i);
			if (id == s.get_id())
				return s;
		}
		throw new Exception("Erreur : id de spécialité non existant !");
	}

	public TypeEtudiant getEtudiant(int id) throws Exception
	{
		for (int i = 0; i < _listEtudiant.size(); ++i) {
			TypeEtudiant s = _listEtudiant.get(i);
			if (id == s.get_id())
				return s;
		}
		throw new Exception("Erreur : id de spécialité non existant !");
	}
	
	public Salle getSalle(int id)
	{
		for (Salle s : _batiment.get_listeSalles())
			if (id == s.get_id())
				return s;
		return null;
	}
	
public LinkedList<String> afficherListeSalles() {
		
		String[] dir = new File("maps").list();
		LinkedList<String> ListBatiment = new LinkedList<String>();;
		for (int i=0; i<dir.length; i++)
		{
			if (dir[i].endsWith(".png") == true)
	    	{
				System.out.println(dir[i]);
				ListBatiment.add(dir[i]);
	    	}
		}
		return ListBatiment;
	}

public Joueur getJ1() {
	return j1;
}

public void setJ1(Joueur j1) {
	this.j1 = j1;
}

public Joueur getJ2() {
	return j2;
}

public void setJ2(Joueur j2) {
	this.j2 = j2;
}

public Batiment get_batiment() {
	return _batiment;
}

public void set_batiment(Batiment _batiment) {
	this._batiment = _batiment;
}

public ArrayList<TypeEtudiant> get_listEtudiant() {
	return _listEtudiant;
}

public void set_listEtudiant(ArrayList<TypeEtudiant> _listEtudiant) {
	this._listEtudiant = _listEtudiant;
}

public ArrayList<TypeSpecialite> get_listSpecialite() {
	return _listSpecialite;
}

public void set_listSpecialite(ArrayList<TypeSpecialite> _listSpecialite) {
	this._listSpecialite = _listSpecialite;
}

public boolean IsOver() {
	return _isOver;
}

public void setIsOver(boolean _isOver) {
	this._isOver = _isOver;
}

	
	/*
	 * public Game() {
	 * 
	 * }
	 * 
	 * public void genererGraphe(String cheminFichier) { //// A FAIRE : appeller
	 * le generer de Batiment }
	 * 
	 * public void affecterEffet(Joueur joueur, Salle salle) { //// A FAIRE }
	 * 
	 * public LinkedList<TypeEtudiant> get_listEtudiant() { return
	 * _listEtudiant; }
	 * 
	 * public void set_listEtudiant(LinkedList<TypeEtudiant> _listEtudiant) {
	 * this._listEtudiant = _listEtudiant; }
	 * 
	 * public LinkedList<TypeSpecialite> get_listSpecialite() { return
	 * _listSpecialite; }
	 * 
	 * public void set_listSpecialite(LinkedList<TypeSpecialite>
	 * _listSpecialite) { this._listSpecialite = _listSpecialite; }
	 * 
	 * public LinkedList<Joueur> get_joueurs() { return _joueurs; }
	 * 
	 * public void set_joueurs(LinkedList<Joueur> _joueurs) { this._joueurs =
	 * _joueurs; }
	 * 
	 * public Batiment get_batiment() { return _batiment; }
	 * 
	 * public void set_batiment(Batiment _batiment) { this._batiment =
	 * _batiment; }
	 */

}
