package vue;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JWindow;

import model.Joueur;

public class SplasherFinPartie extends JDialog implements MouseListener, MouseMotionListener{
	
	private boolean _isAntiAliasing;
	
	private BufferedImage _imageFond = null;
	private Joueur jGagnant;
	private Joueur jPerdant;

	public SplasherFinPartie(Component c, Joueur jG, Joueur jP, boolean antiAlia) {
		this._isAntiAliasing = antiAlia;
		this.jGagnant = jG;
		this.jPerdant = jP;
		this.setSize(400, 300);
		try{
			_imageFond = ImageIO.read(new File("images/repet/splasherFin.png"));
		}catch (Exception e){}
		this.setUndecorated(true);
		this.setLocationRelativeTo(c);
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		this.setModal(true);
		this.setVisible(true);
	}
	
	@Override
	public void paint(Graphics g2) {
		super.paint(g2);
		Graphics2D g = (Graphics2D)g2;
		g.setColor(Color.BLACK);
		if (_isAntiAliasing)
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.drawImage(_imageFond,0,0,null);
		
		g.setColor(Color.WHITE);
		
		g.setFont(new Font("monospaced", Font.BOLD, 25));
			g.drawString("Fin de partie", 200, 25);
		
		g.setFont(new Font("monospaced", Font.BOLD, 20));
			g.drawString(jGagnant.get_nom(), 230, 60);
			g.drawString(""+jGagnant.get_ptVictoire()+" pts", 230, 120);
			
			g.drawString(jPerdant.get_nom(), 230, 200);
			g.drawString(""+jPerdant.get_ptVictoire()+" pts", 230, 260);
		
		g.setColor(Color.LIGHT_GRAY);
		g.setFont(new Font("monospaced", Font.BOLD, 15));
			g.drawString("gagne avec", 230, 90);
			
			g.drawString("perd avec", 230, 230);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getX() > 365 && e.getY() > 265 
				&& e.getX() < 395 && e.getY() < 295)
		{
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			this.dispose();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (e.getX() > 365 && e.getY() > 265 
				&& e.getX() < 395 && e.getY() < 295)
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		else
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseDragged(MouseEvent e) {}

}
