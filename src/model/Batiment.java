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
	private ArrayList<Salle> _listePremiereSalle;
	
	public Batiment(String nomMap) {
		this._nomBatiment = nomBatiment(nomMap);
		this._listeSalles = chargerListeSalles(nomMap);
		this._listePremiereSalle = null;
	}

	public static String nomBatiment(String map) {
		String nomBatiment = map.substring(map.lastIndexOf("/")+1, map.lastIndexOf('.'));
		//nomBatiment = nomBatiment.replace("/", "");
		return nomBatiment;
	}
	
	//M�thode qui va charger les salles dans une liste _listeSalles
	public static ArrayList<Salle> chargerListeSalles(String fichierCarte) {
		try {
			File file = new File(fichierCarte);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			NodeList NodeList_salle = doc.getElementsByTagName("Salle");
			
			ArrayList<Salle> _listeSalles = new ArrayList<Salle>();
			Salle salle;
			
			for (int s=0; s<NodeList_salle.getLength(); s++) {

				Node Node_salle = NodeList_salle.item(s);


					Element balise_salle = (Element) Node_salle;

					NodeList NodeListe_NomSalle = balise_salle.getElementsByTagName("NomSalle");
						Element balise_NomSalle = (Element) NodeListe_NomSalle.item(0);
						NodeList NodeListe_NomSalle_child  = balise_NomSalle.getChildNodes();
						String NomSalle = ((Node) NodeListe_NomSalle_child.item(0)).getNodeValue();
						   
					NodeList NodeListe_IdSalle = balise_salle.getElementsByTagName("IdSalle");
						Element balise_IdSalle = (Element) NodeListe_IdSalle.item(0);
						NodeList NodeListe_IdSalle_child = balise_IdSalle.getChildNodes();
						int IdSalle = Integer.parseInt(((Node) NodeListe_IdSalle_child.item(0)).getNodeValue());

					NodeList NodeListe_AtoutSalle = balise_salle.getElementsByTagName("AtoutSalle");
						Element balise_AtoutSalle = (Element) NodeListe_AtoutSalle.item(0);
						NodeList NodeListe_AtoutSalle_child  = balise_AtoutSalle.getChildNodes();
						String AtoutSalle = ((Node) NodeListe_AtoutSalle_child.item(0)).getNodeValue();
						        	
					NodeList NodeListe_Coordonnes = balise_salle.getElementsByTagName("Coordonnees");
						Element balise_coordonnes = (Element) NodeListe_Coordonnes.item(0);
						NodeList NodeListe_coordonnes_child = balise_coordonnes.getChildNodes();
						String Coordonnes = ((Node) NodeListe_coordonnes_child.item(0)).getNodeValue();
				      	
					NodeList NodeListe_SallesAdjacentes = balise_salle.getElementsByTagName("SallesAdjacentes");
						Element balise_SallesAdjacentes = (Element) NodeListe_SallesAdjacentes.item(0);
						NodeList NodeListe_SallesAdjacentes_child = balise_SallesAdjacentes.getChildNodes();
						String SallesAdjacentes = ((Node) NodeListe_SallesAdjacentes_child.item(0)).getNodeValue();

			      	
					NodeList NodeListe_SalleDepart = balise_salle.getElementsByTagName("SalleDepart");
						Element balise_SalleDepart = (Element) NodeListe_SalleDepart.item(0);
						NodeList NodeListe_SalleDepart_child = balise_SalleDepart.getChildNodes();						
						boolean isSalleDepart = Boolean.parseBoolean(((Node) NodeListe_SalleDepart_child.item(0)).getNodeValue());
					
					
					String[] coord = Coordonnes.split("-");					
					String[] _tabCoordonneesTemporaire = new String[coord.length];
					for (int i=0; i<coord.length; i++)
					{
						_tabCoordonneesTemporaire[i] = ""+coord[i];
					}
					
					String[] salleproches = SallesAdjacentes.split("-");					
					String[] _tabSallesAdjacentesTemporaire = new String[salleproches.length];
					for (int i=0; i<salleproches.length; i++)
					{
						_tabSallesAdjacentesTemporaire[i] = ""+salleproches[i];
					}
					
					
					salle = new Salle(IdSalle, NomSalle, AtoutSalle, false, _tabCoordonneesTemporaire, _tabSallesAdjacentesTemporaire, isSalleDepart);
					_listeSalles.add(salle);
				}
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

			for (int j=0; j<temp.get_tabCoordonnees().length; j++)
			{
				if (j==temp.get_tabCoordonnees().length-1)
					coordonnees += temp.get_tabCoordonnees()[j];
				else
					coordonnees += temp.get_tabCoordonnees()[j]+"-";
			}
			
			for (int j=0; j<temp.get_tabSallesAdjacentes().length; j++)
			{
				if (j==temp.get_tabSallesAdjacentes().length-1)
					sallesAdjacentes += temp.get_tabSallesAdjacentes()[j];
				else
					sallesAdjacentes += temp.get_tabSallesAdjacentes()[j]+"-";
			}
			System.out.println("Nom = "+nom+" et Id = "+id+" IsSalleDepart = "+_isSalleDepart+" et AtoutSalle = "+type+" et IsOccupe = "+isOccupe+" et Coordonnees =  "+coordonnees+" et SallesProches =  "+sallesAdjacentes+ " et AtoutSalle = "+aoutSalle);
		}
	}
	
	public static void main(String[] args){

	}

	public static void afficherBatiment(Batiment batiment) {
		System.out.println("Le batiment \""+batiment._nomBatiment+"\" contient "+batiment._listeSalles.size()+" salles :\n");
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

	public ArrayList<Salle> get_listePremiereSalle() {
		return _listePremiereSalle;
	}

	public void set_listePremiereSalle(ArrayList<Salle> _listePremiereSalle) {
		this._listePremiereSalle = _listePremiereSalle;
	}

}