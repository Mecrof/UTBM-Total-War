package vue;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import model.Game;

public class FenetrePrincipale extends JFrame {
	
	private Game game;
	private MenuJeu _menuJeu;
	public PlateForme _plateForme;
	
	public FenetrePrincipale(Game g) {
		this.game = g;
		initConfig();
		initVues();
		changerVersMenu();
		this.setVisible(true);
	}
	
	private void initConfig()
	{
		this.setTitle("UTBM Total War");
		//this.setUndecorated(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setMaximumSize(new Dimension(800, 600));
		this.setMinimumSize(new Dimension(800, 600));
		//this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
	private void initVues()
	{
		_menuJeu = new MenuJeu(game);
		_plateForme = new PlateForme(game);
		this.add(_menuJeu);
		//this.add(_plateForme);
	}
	
	public void changerVersMenu()
	{
		this.getContentPane().removeAll();
		this.getContentPane().add(_menuJeu);
		this.revalidate();
		//_menuJeu.setVisible(true);
		//_plateForme.setVisible(false);
	}
	
	public void changerVersPlateForme()
	{
		/*
		try{
			this.remove(_menuJeu);
		}catch(Exception e){};*/
		this.getContentPane().removeAll();
		this.getContentPane().add(_plateForme);
		this.revalidate();
		//_menuJeu.setVisible(false);
		//_plateForme.setVisible(true);
	}

	public MenuJeu get_menuJeu() {
		return _menuJeu;
	}

	public void set_menuJeu(MenuJeu _menuJeu) {
		this._menuJeu = _menuJeu;
	}
	
	

}
