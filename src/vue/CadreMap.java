package vue;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
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

import model.Batiment;
import model.Game;
import model.Salle;

public class CadreMap extends JPanel {

	private Game game;

	public CadreMap(Game g) {
		this.game = g;
		this.setMinimumSize(new Dimension(600, 320));
		this.setMaximumSize(new Dimension(600, 320));
		this.setVisible(true);
		this.setBackground(Color.WHITE);
	}
	
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
		System.out.println(nomCarte);

		int xFrame = this.getWidth();
		int yFrame = this.getHeight();
		System.out.println(xFrame+"   :   "+yFrame);
		
		try {
				Image img = ImageIO.read(new File("./maps/"+nomCarte));
				g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);

				int xImage = img.getWidth(null);
				int yImage = img.getHeight(null);
				
				System.out.println(xImage+"   :   "+yImage);
				
				float xQuot = ((float)xFrame)/xImage;
				float yQuot = ((float)yFrame)/yImage;	

				System.out.println(xQuot+"   :   "+yQuot);	
									  
				ArrayList newListe = game.get_batiment().get_listeSalles();

				g.setColor(Color.GREEN);

			    Polygon[] p = new Polygon[newListe.size()];

				for(int i=0; i<newListe.size(); i++)
				{
					p[i] = new Polygon();
				    String coordo = "";
				    Salle temp = (Salle) newListe.get(i);

					for (int j=0; j<temp.get_tabCoordonnees().length; j++)
					{
					    String[] result = temp.get_tabCoordonnees()[j].split(":");
					    for (int k=0; k<result.length; k++)
					    {
					    	int _x = Integer.parseInt(result[0]);
					    	int _y = Integer.parseInt(result[1]);
					    	
					    	int x= (int) (xQuot*_x);
					    	int y= (int) (yQuot*_y);
					    	
							p[i].addPoint(x, y);
					    }
					}
					
				    System.out.println(coordo);
					

				    String[] result = coordo.split(":");

				    g.drawPolygon(p[i]);	//polygone bordure
				    //g.fillPolygon(p[i]);	//polygone plein
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	} 
}