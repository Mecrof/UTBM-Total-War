package vue;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.Game;
import model.Salle;

public class CadreMap extends JPanel {

	private Game game;
	private ArrayList<Salle> sallePossedee = new ArrayList<Salle>();
	private ArrayList<Salle> salleAttaquable = new ArrayList<Salle>();
	private Polygon[] _polySalle;
	private Polygon _polySurligner;
	private float xQuot;
	private float yQuot;
	private int _effectifEnDeplacement = 0;
	private int posCurseurX = 0;
	private int posCurseurY = 0;
	private Image imgMap; 
	private Image imgJoueur1 ;
	private Image imgJoueur2 ;

	public CadreMap(Game g) {
		this.game = g;
		try{
			imgJoueur1 = ImageIO.read(new File("images/joueurs/joueur1.png"));
			imgJoueur2 = ImageIO.read(new File("images/joueurs/joueur2.png"));
		}catch(Exception e){e.printStackTrace();}
		this.reinitialiser();
		this.setMinimumSize(new Dimension(600, 320));
		this.setMaximumSize(new Dimension(600, 320));
		this.setVisible(true);
		this.setBackground(Color.WHITE);
	}
	
	public void chargerMap()
	{
		String nomCarte = game.get_batiment().get_nomBatiment();
		nomCarte = nomCarte+".png";
		try{
			imgMap = ImageIO.read(new File("./maps/"+nomCarte));
		}catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Map impossible à charger !! ");
		}
	}

	public void reinitialiser()
	{
		sallePossedee = new ArrayList<Salle>();
		salleAttaquable = new ArrayList<Salle>();
		_polySalle = null;
		_polySurligner = null;
	}
	
	public void testSalleOccupees(Graphics2D g, float _xQuot, float _yQuot) throws IOException
	{
		//charger liste de toutes les salles
		ArrayList<Salle> listeSalles = this.game.get_batiment().get_listeSalles();

		//listes Conctenant Les Salles De Joueur1 et Joueur 2
		ArrayList<Salle> listeTemp1 = new ArrayList<Salle>();		
		ArrayList<Salle> listeTemp2 = new ArrayList<Salle>();

		//on déclare les deux listes qui vont contenir les sallesOccupées de chaque joueur.
		ArrayList<Integer> listeSallesOccupeeJoueur1 = this.game.getJ1().get_listIdSalleOccupee();	
		ArrayList<Integer> listeSallesOccupeeJoueur2 = this.game.getJ2().get_listIdSalleOccupee();
		
		for (int k=0; k<listeSalles.size(); k++)
		{
			//tester si salle occupée
			if (listeSalles.get(k).get_isOccupe() == true)
			{
				int idSalle = listeSalles.get(k).get_id();				
				
				if (!listeSallesOccupeeJoueur1.isEmpty())
				{
					for (int l=0; l<listeSallesOccupeeJoueur1.size(); l++)
					{						
						if (idSalle == this.game.getJ1().get_listIdSalleOccupee().get(l))
						{
							Salle s1 = this.game.getSalle(idSalle);
							listeTemp1.add(s1);		
						}
					}
					dessinerSalle(g, listeTemp1, _xQuot, _yQuot);
				}
				
				if (!listeSallesOccupeeJoueur2.isEmpty())
				{
					for (int m=0; m<listeSallesOccupeeJoueur2.size(); m++)
					{						
						if (idSalle == this.game.getJ2().get_listIdSalleOccupee().get(m))
						{
							Salle s2 = this.game.getSalle(idSalle);
							listeTemp2.add(s2);		
						}
					}
					dessinerSalle(g, listeTemp2, _xQuot, _yQuot);
				}
			}
		}
	}
	
	@Override
	public void paint(Graphics g2) {
		super.paint(g2);
		Graphics2D g = (Graphics2D)g2;
		g.setColor(Color.BLACK);
		if (this.game.isAntialiasing())
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(Color.RED); 
		g.setStroke(new BasicStroke (2.0f));

		int xFrame = this.getWidth();
		int yFrame = this.getHeight();
		
		float xQuot = 0;
		float yQuot = 0;
				
		g.drawImage(imgMap, 0, 0, this.getWidth(), this.getHeight(), this);

		int xImage = imgMap.getWidth(null);
		int yImage = imgMap.getHeight(null);
				
		xQuot = ((float)xFrame)/xImage;
		yQuot = ((float)yFrame)/yImage;					

		try {
			testSalleOccupees(g, xQuot, yQuot);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (!sallePossedee.isEmpty())
		{
			g.setColor(Color.GREEN);
			//System.out.println("##SallePossedee :: "+sallePossedee);
		   this.dessinerSalle(g, sallePossedee, xQuot, yQuot);
				  
		}
				
		if (!salleAttaquable.isEmpty())
		{
					
			g.setColor(Color.YELLOW);
			//System.out.println("##SalleAttaquable :: "+salleAttaquable);
			this.dessinerSalle(g, salleAttaquable, xQuot, yQuot);
					
		}
		dessinerEffectifEnDeplacement(g);
	} 
	
	private void dessinerEffectifEnDeplacement(Graphics2D g)
	{
		if (_effectifEnDeplacement > 0)
		{
			Color c = g.getColor();
			g.setColor(Color.BLACK);
			g.drawString(""+_effectifEnDeplacement, posCurseurX-1+15, posCurseurY+1+20);
			g.setColor(Color.WHITE);
			g.drawString(""+_effectifEnDeplacement, posCurseurX+15, posCurseurY+20);
			g.setColor(c);
		}
	}
	
	private void dessinerSalle(Graphics2D g, ArrayList<Salle> newListe, float xQuot, float yQuot)
	{
		//System.out.println("##New liste : "+newListe.size() +" : "+newListe);
		 _polySalle = new Polygon[newListe.size()];
		 
			
			for(int i=0; i<newListe.size(); i++)
			{
				_polySalle[i] = new Polygon();
			    Salle temp = (Salle) newListe.get(i);
			    int idSalle = temp.get_id();
			    int nbrOcc = temp.get_nombreOccupant();
			    int nbrOccDeclin = temp.get_nombreOccupantEnDeclin();

			    int Xtotal = 0;
				int Ytotal = 0;

				for (int j=0; j<temp.get_listeCoordonnees().size(); j++)
				{
					Integer[] tabTemp = temp.get_listeCoordonnees().get(j);
				    int _x = tabTemp[0];
				    int _y = tabTemp[1];
				    	
				    int x= (int) (xQuot*_x);
				    int y= (int) (yQuot*_y);

				    Xtotal += x;
				    Ytotal += y;
				    
					_polySalle[i].addPoint(x, y);			    
				}
				
				int x_tot = (int) (Xtotal) / _polySalle[i].npoints;
				int y_tot = (int) (Ytotal) / _polySalle[i].npoints;
				
				if (_polySurligner != null)
				{
					if(this.coordonneesEgales(_polySalle[i], _polySurligner))
					{
						g.setStroke(new BasicStroke (4.0f));
						g.drawPolygon(_polySalle[i]);
						g.setStroke(new BasicStroke (2.0f));
					}
					else
						g.drawPolygon(_polySalle[i]);
				}
				else
					g.drawPolygon(_polySalle[i]);	//polygone bordure
					//g.fillPolygon(p[i]);	//polygone plein


				Image img = imgJoueur1;// = ImageIO.read(new File("images/specialite/specialite_5.png"));		// trouver une meilleure solution
				
				if (!this.game.getJ1().get_listIdSalleOccupee().isEmpty())
				{
					for (int l=0; l<this.game.getJ1().get_listIdSalleOccupee().size(); l++)
					{			
						if (idSalle == this.game.getJ1().get_listIdSalleOccupee().get(l))
						{
							
							int x_tot1 = ((int) Xtotal / _polySalle[i].npoints) - (img.getWidth(null) / 2);
							int y_tot2 = ((int) Ytotal / _polySalle[i].npoints) - (img.getHeight(null) / 2);
		
							g.drawImage(img, x_tot1, y_tot2, null);
						}
					}
				}
				
				img = imgJoueur2;
				if (!this.game.getJ2().get_listIdSalleOccupee().isEmpty())
				{
					for (int n=0; n<this.game.getJ2().get_listIdSalleOccupee().size(); n++)
					{			
						if (idSalle == this.game.getJ2().get_listIdSalleOccupee().get(n))
						{
							
							int x_tot1 = ((int) Xtotal / _polySalle[i].npoints) - (img.getWidth(null) / 2);
							int y_tot2 = ((int) Ytotal / _polySalle[i].npoints) - (img.getHeight(null) / 2);
		
							g.drawImage(img, x_tot1, y_tot2, null);
						}
					}	
				}
				Color c = g.getColor();
				Font font = new Font("Courier", Font.ITALIC, 20);
				
				g.setFont(font);
				
				g.setColor(Color.BLACK);
				g.drawString(""+nbrOcc, (x_tot + (img.getWidth(null) /2) - 1) , (y_tot + ((img.getHeight(null) /2) - 20 + 1)));
				g.drawString(""+nbrOccDeclin, (x_tot + (img.getWidth(null) /2) - 1) , (y_tot + (img.getHeight(null) /2) + 1));
				
				g.setColor(c);
				g.drawString(""+nbrOcc, (x_tot + (img.getWidth(null) /2)) , (y_tot + ((img.getHeight(null) /2) - 20)));
				g.setColor(Color.LIGHT_GRAY);
				g.drawString(""+nbrOccDeclin, (x_tot + img.getWidth(null) /2) , (y_tot + (img.getHeight(null) /2)));
				
				g.setColor(c);
			}
	}

	private static boolean coordonneesEgales(Polygon p1, Polygon p2)
	{
		if (p1.npoints != p2.npoints)
		{
			return false;
		}
		for (int i = 0; i < p1.npoints ; ++i)
			if (p1.xpoints[i]!=p2.xpoints[i] || p1.ypoints[i]!=p2.ypoints[i])
				return false;
		return true;
	}
	
	public void dessinerSalleAttaquable(ArrayList<Salle> listSalle)
	{
		this.salleAttaquable = listSalle;
	}
	
	public void dessinerSallePossedee(ArrayList<Salle> listSalle)
	{
		this.sallePossedee = listSalle;
	}

	public Polygon[] get_polySalle() {
		return _polySalle;
	}

	public void set_polySalle(Polygon[] _polySalle) {
		this._polySalle = _polySalle;
	}

	public ArrayList<Salle> getSallePossedee() {
		return sallePossedee;
	}

	public void setSallePossedee(ArrayList<Salle> sallePossedee) {
		this.sallePossedee = sallePossedee;
	}

	public ArrayList<Salle> getSalleAttaquable() {
		return salleAttaquable;
	}

	public void setSalleAttaquable(ArrayList<Salle> salleAttaquable) {
		this.salleAttaquable = salleAttaquable;
	}

	public Polygon get_polySurligner() {
		return _polySurligner;
	}

	public void set_polySurligner(Polygon _polySurligner) {
		this._polySurligner = _polySurligner;
	}

	public float getxQuot() {
		return xQuot;
	}

	public void setxQuot(float xQuot) {
		this.xQuot = xQuot;
	}

	public float getyQuot() {
		return yQuot;
	}

	public void setyQuot(float yQuot) {
		this.yQuot = yQuot;
	}

	public int get_effectifEnDeplacement() {
		return _effectifEnDeplacement;
	}

	public void set_effectifEnDeplacement(int _effectifEnDeplacement) {
		this._effectifEnDeplacement = _effectifEnDeplacement;
	}

	public int getPosCurseurX() {
		return posCurseurX;
	}

	public void setPosCurseurX(int posCurseurX) {
		this.posCurseurX = posCurseurX;
	}

	public int getPosCurseurY() {
		return posCurseurY;
	}

	public void setPosCurseurY(int posCurseurY) {
		this.posCurseurY = posCurseurY;
	}

	public Image getImgMap() {
		return imgMap;
	}

	public void setImgMap(Image imgMap) {
		this.imgMap = imgMap;
	}
	
	
}