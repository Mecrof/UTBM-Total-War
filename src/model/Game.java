package model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Game {
	
	private boolean _isAntialiasing = true;
	private boolean _isOver;
	private boolean _isStarted;
	private int _nombreDeTour;
	private int _nombreDeTourEffectue;
	private ArrayList<Type[]> _coupleEtuSpe = new ArrayList<Type[]>(5);
	private Joueur j1 = new Joueur();
	private Joueur j2 = new Joueur();
	

	private ArrayList<TypeEtudiant> _listEtudiant = new ArrayList<TypeEtudiant>();
	private ArrayList<TypeSpecialite> _listSpecialite = new ArrayList<TypeSpecialite>();
	// private ArrayList<Joueur> _joueurs = new ArrayList<Joueur>();
	private Batiment _batiment;
	
	public Game() {
		reinitialiser();
	}
	
	public void reinitialiser()
	{
		_coupleEtuSpe = new ArrayList<Type[]>(5);
		j1.reinitialiser();
		j2.reinitialiser();
		_batiment = null;
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
					for (int i = 0; i < salle.get_listeSallesAdjacentes().size(); ++i)
					{
						Salle s = this.getSalle(salle.get_listeSallesAdjacentes().get(i));
						if (s.get_nombreOccupant()<effectifEngage) //////////////////////////// CHANGER PLUS TARD, METTRE <=
							if (!joueur.get_listIdSalleOccupee().contains(s.get_id()))
								if (!listSalle.contains(s))
								{
									//System.out.println("## Salle:"+s);
									if (this.isAttaquable(s, effectifEngage))
										listSalle.add(s);
								}
							
					}
					/*
					if (!salle.get_isOccupe())
						listSalle.add(salle);
					else if (salle.get_nombreOccupant()<effectifEngage)
						listSalle.add(salle);
						*/
				}
			}
		}
		//System.out.println("## listeSalle :"+listSalle);
		if (listSalle.isEmpty())
			return null;
		else
			return listSalle;
	}
	
	public ArrayList<Salle> getSalleAttaquable(Joueur j, int effectif) throws Exception
	{
		ArrayList<Salle> listSalle = new ArrayList<Salle>();
		if (!j.get_listIdSalleOccupee().isEmpty())
		{
			listSalle = this.calculSalleAttaquable(j, effectif);
		}
		else
		{
			try{
			for (Integer idSalle : this._batiment.get_listePremiereSalle())
				if (this.isAttaquable(this.getSalle(idSalle.intValue()), effectif))
					listSalle.add(this.getSalle(idSalle.intValue()));
			}catch (Exception e)
			{
				throw new Exception("Erreur : Aucune premiere salle sur la map !");
			}
		}
		return listSalle;
	}
	
	public boolean isAttaquable(Salle salle, int effectifEngage)
	{
		if (effectifEngage >= 2)
		{
			if (!salle.get_isOccupe())
				return true;//listSalle.add(salle);
			else if (salle.get_nombreOccupant()<effectifEngage)
				return true;
		}
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
		Geek = new TypeSpecialite("Fou d'informatique", Specialite.GEEK, "images/specialites/specialite00.png");
		TypeSpecialite Mecano;
		Mecano = new TypeSpecialite("Machine", Specialite.MECANO, "images/specialites/specialite01.png");
		TypeSpecialite Elec;
		Elec = new TypeSpecialite("Para-tonnerre", Specialite.ELEC, "images/specialites/specialite02.png");
		TypeSpecialite Gribouilleur;
		Gribouilleur = new TypeSpecialite("Artiste", Specialite.GRIBOUILLEUR, "images/specialites/specialite03.png");
		TypeSpecialite Management;
		Management = new TypeSpecialite("Beau-parleur", Specialite.MANAGEMENT, "images/specialites/specialite04.png");
		TypeSpecialite Matheux;
		Matheux = new TypeSpecialite("Matheux", Specialite.MATHEUX, "images/specialites/specialite05.png");
		TypeSpecialite Langues;
		Langues = new TypeSpecialite("N-lingue", Specialite.LANGUES, "images/specialites/specialite06.png");
		TypeSpecialite CultureGenerale;
		CultureGenerale = new TypeSpecialite("Encyclopédie", Specialite.CULTUREGENERALE, "images/specialites/specialite07.png");
		TypeSpecialite PhysiqueChimie;
		PhysiqueChimie = new TypeSpecialite("Bachelier frais", Specialite.PHYSIQUECHIMIE, "images/specialites/specialite08.png");
		TypeSpecialite Intello;
		Intello = new TypeSpecialite("Bucheur", Specialite.INTELLO, "images/specialites/specialite09.png");

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
		GI = new TypeEtudiant("GI", Specialite.GEEK, "images/etudiants/etudiant00.png", null);
		TypeEtudiant EE;
		EE = new TypeEtudiant("EE", Specialite.ELEC, "images/etudiants/etudiant01.png", null);
		TypeEtudiant GMC;
		GMC = new TypeEtudiant("GMC", Specialite.MECANO, "images/etudiants/etudiant02.png", null);
		TypeEtudiant EDIM;
		EDIM = new TypeEtudiant("EDIM", Specialite.GRIBOUILLEUR, "images/etudiants/etudiant03.png", null);
		TypeEtudiant IMAP;
		IMAP = new TypeEtudiant("IMAP", Specialite.MANAGEMENT, "images/etudiants/etudiant04.png", null);
		TypeEtudiant TC;
		TC = new TypeEtudiant("TC", Specialite.PHYSIQUECHIMIE, "images/etudiants/etudiant05.png", null);
		TypeEtudiant Doctorant;
		Doctorant = new TypeEtudiant("Doctorant", Specialite.INTELLO, "images/etudiants/etudiant06.png", null);

		_listEtudiant.add(GI);
		_listEtudiant.add(EE);
		_listEtudiant.add(GMC);
		_listEtudiant.add(EDIM);
		_listEtudiant.add(IMAP);
		_listEtudiant.add(TC);
		_listEtudiant.add(Doctorant);
	}
	
	public TypeSpecialite getSpecialite(int id)
	{
		for (int i = 0; i < _listSpecialite.size(); ++i) {
			TypeSpecialite s = _listSpecialite.get(i);
			if (id == s.get_id())
				return s;
		}
		return null;
	}

	public TypeEtudiant getEtudiant(int id)
	{
		for (int i = 0; i < _listEtudiant.size(); ++i) {
			TypeEtudiant s = _listEtudiant.get(i);
			if (id == s.get_id())
				return s;
		}
		return null;
	}
	
	public Salle getSalle(int id)
	{
		for (Salle s : _batiment.get_listeSalles())
			if (id == s.get_id())
				return s;
		return null;
	}
	
	public ArrayList<Salle> getSalles(ArrayList<Integer> idSalles)
	{
		ArrayList<Salle> list = new ArrayList<Salle>();
		for (Integer id : idSalles)
		{
			list.add(this.getSalle(id.intValue()));
		}
		return list;
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

	public int get_nombreDeTour() {
		return _nombreDeTour;
	}

	public void set_nombreDeTour(int _nombreDeTour) {
		this._nombreDeTour = _nombreDeTour;
	}

	public int get_nombreDeTourEffectue() {
		return _nombreDeTourEffectue;
	}

	public void set_nombreDeTourEffectue(int _nombreDeTourEffectue) {
		this._nombreDeTourEffectue = _nombreDeTourEffectue;
	}

	public ArrayList<Type[]> get_coupleEtuSpe() {
		return _coupleEtuSpe;
	}

	public void set_coupleEtuSpe(ArrayList<Type[]> _coupleEtuSpe) {
		this._coupleEtuSpe = _coupleEtuSpe;
	}

	public boolean isAntialiasing() {
		return _isAntialiasing;
	}

	public void set_isAntialiasing(boolean _isAntialiasing) {
		this._isAntialiasing = _isAntialiasing;
	}
	
	

}
