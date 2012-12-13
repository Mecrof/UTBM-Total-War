package vue;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.Batiment;
import model.Game;
import model.Salle;

public class CadreMap extends JPanel {//implements MouseListener, MouseMotionListener{

	private Game game;
	private ArrayList<Salle> sallePossedee = new ArrayList<Salle>();
	private ArrayList<Salle> salleAttaquable = new ArrayList<Salle>();
	private Polygon[] _polySalle;
	private Polygon _polySurligner;
	
	public CadreMap(Game g) {
		this.game = g;
		this.setMinimumSize(new Dimension(600, 320));
		this.setMaximumSize(new Dimension(600, 320));
		this.setVisible(true);
		this.setBackground(Color.WHITE);
		
		/*
		addMouseListener( this );
		addMouseMotionListener( this );
		*/
	}
	
/*
////////////////////////////////////////////////////////
	public void mouseClicked(MouseEvent e) {
		try
		{
			for (int l = 0; l < _polySalle.length; l++) {
		
				if (_polySalle[l].contains(e.getX(), e.getY())) {
					System.out.println("Vous avez cliqué sur la salle : "
							+ salleAttaquable.get(l).get_id());
				}
			}
			repaint();
		}
		catch (NullPointerException n){} 
	}

	@Override
	public void mousePressed(MouseEvent event) {}
	@Override
	public void mouseReleased(MouseEvent event) {}
	@Override
	public void mouseEntered(MouseEvent event) {}
	@Override
	public void mouseExited(MouseEvent event) {}
	@Override
	public void mouseDragged(MouseEvent event) {}
	@Override
	public void mouseMoved(MouseEvent event) {}
//////////////////////////////////////////////////////*/
	
	public void traiterListeSalle(ArrayList<Salle> listeSalles)
	{
		
	}
	@Override
	public void paint(Graphics g2) {
		super.paint(g2);
		
		Graphics2D g = (Graphics2D)g2;
		g.setColor(Color.RED); 
		g.setStroke(new BasicStroke (2.0f));
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		String nomCarte = game.get_batiment().get_nomBatiment();
		nomCarte = nomCarte+".png";
		//System.out.println(nomCarte);

		int xFrame = this.getWidth();
		int yFrame = this.getHeight();
		//System.out.println(xFrame+"   :   "+yFrame);
		
		try {
				Image img = ImageIO.read(new File("./maps/"+nomCarte));
				g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);

				int xImage = img.getWidth(null);
				int yImage = img.getHeight(null);
				
				//System.out.println(xImage+"   :   "+yImage);
				
				float xQuot = ((float)xFrame)/xImage;
				float yQuot = ((float)yFrame)/yImage;	

				//System.out.println(xQuot+"   :   "+yQuot);	
									  
				//ArrayList newListe = this.game.get_batiment().get_listeSalles();

				

				if (!sallePossedee.isEmpty())
				{
					g.setColor(Color.GREEN);
					//System.out.println("##SallePossedee :: "+sallePossedee);
				   this.dessinerSalle(g, sallePossedee, xQuot, yQuot);
				}
				
				if (!salleAttaquable.isEmpty())
				{
					
					g.setColor(Color.RED);
					//System.out.println("##SalleAttaquable :: "+salleAttaquable);
					this.dessinerSalle(g, salleAttaquable, xQuot, yQuot);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
	} 
	
	private void dessinerSalle(Graphics2D g, ArrayList<Salle> newListe, float xQuot, float yQuot)
	{
		//System.out.println("##New liste : "+newListe.size() +" : "+newListe);
		 _polySalle = new Polygon[newListe.size()];
		 
			
			for(int i=0; i<newListe.size(); i++)
			{
				_polySalle[i] = new Polygon();
			    String coordo = "";
			    Salle temp = (Salle) newListe.get(i);

				for (int j=0; j<temp.get_tabCoordonnees().length; j++)
				{
				    String[] result = temp.get_tabCoordonnees()[j].split(";");
				    
				    for (int k=0; k<result.length; k++)
				    {
				    	String[] res = result[k].split(":");
				    	int _x = Integer.parseInt(res[0]);
				    	int _y = Integer.parseInt(res[1]);
				    	
				    	int x= (int) (xQuot*_x);
				    	int y= (int) (yQuot*_y);
				    	
						_polySalle[i].addPoint(x, y);
				    }
				}

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
}