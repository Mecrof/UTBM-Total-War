package model;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Batiment {
	
	//Attributs de Batiments
	private String _nomBatiment;
	private ArrayList<Salle> _listeSalles;
	private ArrayList<Integer> _listePremiereSalle = new ArrayList<Integer>();
	
	public Batiment(String nomMap) {
		this._nomBatiment = nomBatiment(nomMap);
		this._listeSalles = chargerListeSalles(nomMap);
		//this._listePremiereSalle = new ArrayList<Integer>();
	}

	public static String nomBatiment(String map) {
		String nomBatiment = map.substring(map.lastIndexOf("/")+1, map.lastIndexOf('.'));
		//nomBatiment = nomBatiment.replace("/", "");
		return nomBatiment;
	}
	
	//M�thode qui va charger les salles dans une liste _listeSalles
	public ArrayList<Salle> chargerListeSalles(String fichierCarte) {
		try {
			File file = new File(fichierCarte);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			NodeList nodeList_salle = doc.getElementsByTagName("Salle");
			
			ArrayList<Salle> _listeSalles = new ArrayList<Salle>();
			Salle salle;
			
			for (int s=0; s<nodeList_salle.getLength(); s++) {

				Node node_salle = nodeList_salle.item(s);


					Element balise_salle = (Element) node_salle;

					NodeList nodeListe_nomSalle = balise_salle.getElementsByTagName("Nom");
						Element balise_nomSalle = (Element) nodeListe_nomSalle.item(0);
						NodeList nodeListe_nomSalle_child  = balise_nomSalle.getChildNodes();
						String nomSalle = ((Node) nodeListe_nomSalle_child.item(0)).getNodeValue();
						   
					NodeList nodeListe_idSalle = balise_salle.getElementsByTagName("Id");
						Element balise_idSalle = (Element) nodeListe_idSalle.item(0);
						NodeList nodeListe_idSalle_child = balise_idSalle.getChildNodes();
						int idSalle = Integer.parseInt(((Node) nodeListe_idSalle_child.item(0)).getNodeValue());

					NodeList nodeListe_atoutSalle = balise_salle.getElementsByTagName("AtoutSalle");
						Element balise_AtoutSalle = (Element) nodeListe_atoutSalle.item(0);
						NodeList nodeListe_atoutSalle_child  = balise_AtoutSalle.getChildNodes();
						String tmp = ((Node) nodeListe_atoutSalle_child.item(0)).getNodeValue();
						
						Specialite atoutSalle = null;
						Specialite [] spe = Specialite.values();
						for (int i = 0; i < spe.length; ++i)
						{
							if (tmp.equals(spe[i].getCode()))
								atoutSalle = spe[i];
						}
						if (atoutSalle == null)
							atoutSalle = Specialite.AUCUNE;
						/*
						if (tmp == Specialite.CULTUREGENERALE.getCode())
							atoutSalle = Specialite.CULTUREGENERALE;
						else if (tmp == Specialite.ELEC.getCode())
							atoutSalle = Specialite.ELEC;
						else if (tmp == Specialite.GEEK.getCode())
							atoutSalle = Specialite.GEEK;
						else if (tmp == Specialite.GRIBOUILLEUR.getCode())
							atoutSalle = Specialite.GRIBOUILLEUR;
						else if (tmp == Specialite.INTELLO.getCode())
							atoutSalle = Specialite.INTELLO;
						else if (tmp == Specialite.LANGUES.getCode())
							atoutSalle = Specialite.LANGUES;
						else if (tmp == Specialite.MANAGEMENT.getCode())
							atoutSalle = Specialite.MANAGEMENT;
						else if (tmp == Specialite.MATHEUX.getCode())
							atoutSalle = Specialite.MATHEUX;
						else if (tmp == Specialite.MECANO.getCode())
							atoutSalle = Specialite.MECANO;
						else if (tmp == Specialite.PHYSIQUECHIMIE.getCode())
							atoutSalle = Specialite.PHYSIQUECHIMIE;
						else
							atoutSalle = Specialite.AUCUNE;
						*/
						
					NodeList nodeListe_coordonnes = balise_salle.getElementsByTagName("Coordonnees");
						Element balise_coordonnes = (Element) nodeListe_coordonnes.item(0);
						NodeList nodeListe_coordonnes_child = balise_coordonnes.getChildNodes();
						String coordonnes = ((Node) nodeListe_coordonnes_child.item(0)).getNodeValue();
						coordonnes = coordonnes.substring(1, coordonnes.length()); 
				      	
					NodeList nodeListe_sallesAdjacentes = balise_salle.getElementsByTagName("SallesAdjacentes");
						Element balise_SallesAdjacentes = (Element) nodeListe_sallesAdjacentes.item(0);
						NodeList nodeListe_sallesAdjacentes_child = balise_SallesAdjacentes.getChildNodes();
						String sallesAdjacentes = ((Node) nodeListe_sallesAdjacentes_child.item(0)).getNodeValue();

			      	
					NodeList nodeListe_salleDepart = balise_salle.getElementsByTagName("SalleDepart");
						Element balise_SalleDepart = (Element) nodeListe_salleDepart.item(0);
						NodeList nodeListe_salleDepart_child = balise_SalleDepart.getChildNodes();						
						String isSalleDep = ((Node) nodeListe_salleDepart_child.item(0)).getNodeValue();
						
						boolean isSalleDepart = true;
						if (isSalleDep.equals("Oui"))
						{
							isSalleDepart=true;
							_listePremiereSalle.add(new Integer (idSalle));
						}
						if (isSalleDep.equals("Non"))
						{
							isSalleDepart=false;
						}
						
					String[] coord = coordonnes.split(";");
					ArrayList<Integer[]> _listeCoordonneesTemporaire = new ArrayList<Integer[]>();
					
					for(int m=0; m<coord.length; m++)
					{	
						String[] coord1 = coord[m].split(":");
						Integer[] tabCoordonnees = {0, 0};
						tabCoordonnees[0] = Integer.parseInt(coord1[0]);
						tabCoordonnees[1] = Integer.parseInt(coord1[1]);
						_listeCoordonneesTemporaire.add(tabCoordonnees);
					}
					
					String[] salleproches = sallesAdjacentes.split("-");					
					ArrayList<Integer> _listeSallesAdjacentesTemporaire = new ArrayList<Integer>();
					for (int i=0; i<salleproches.length; i++)
					{
						_listeSallesAdjacentesTemporaire.add(Integer.parseInt(salleproches[i]));
					}
					
					salle = new Salle(idSalle, nomSalle, atoutSalle, false, _listeCoordonneesTemporaire, _listeSallesAdjacentesTemporaire, isSalleDepart);
					_listeSalles.add(salle);
				}
				//afficherListeSalles(_listeSalles);
				return _listeSalles;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void afficherListeSalles(ArrayList<?> _listeSalles) {
				
		for(int i=0; i<_listeSalles.size(); i++)
		{
			Salle temp = (Salle) _listeSalles.get(i);
			
			String id = ""+temp.get_id();
			String nom = ""+temp.get_nom();
			String type = ""+temp.get_atoutSalle();
			String isOccupe = ""+temp.get_isOccupe();
			String coordonnees = "";
			String sallesAdjacentes = "";
			String aoutSalle = ""+temp.get_atoutSalle();
			String _isSalleDepart = ""+temp.get_isSalleDepart();

			for (int j=0; j<temp.get_listeCoordonnees().size(); j++)
			{
				Integer[] tabTemp = temp.get_listeCoordonnees().get(j);
				
				if (j==temp.get_listeCoordonnees().size()-1)					
				{	coordonnees += tabTemp[0]+":"+tabTemp[1] ;
				}
				else{			
					coordonnees += tabTemp[0]+":"+tabTemp[1]+";";
				}
			}
			
			for (int j=0; j<temp.get_listeSallesAdjacentes().size(); j++)
			{
				if (j==temp.get_listeSallesAdjacentes().size()-1)
					sallesAdjacentes += temp.get_listeSallesAdjacentes().get(j);
				else
					sallesAdjacentes += temp.get_listeSallesAdjacentes().get(j)+"-";
			}
			//System.out.println("Nom = "+nom+" et Id = "+id+" IsSalleDepart = "+_isSalleDepart+" et AtoutSalle = "+type+" et IsOccupe = "+isOccupe+" et Coordonnees =  "+coordonnees+" et SallesProches =  "+sallesAdjacentes+ " et AtoutSalle = "+aoutSalle);
		}
	}
	
	public static void afficherBatiment(Batiment batiment) {
		afficherListeSalles(batiment._listeSalles);
	}

	public String get_nomBatiment() {
		return _nomBatiment;
	}

	public void set_nomBatiment(String _nomBatiment) {
		this._nomBatiment = _nomBatiment;
	}

	public ArrayList<Salle> get_listeSalles() {
		return _listeSalles;
	}

	public void set_listeSalles(ArrayList<Salle> _listeSalles) {
		this._listeSalles = _listeSalles;
	}

	public ArrayList<Integer> get_listePremiereSalle() {
		return _listePremiereSalle;
	}

	public void set_listePremiereSalle(ArrayList<Integer> _listePremiereSalle) {
		this._listePremiereSalle = _listePremiereSalle;
	}

}