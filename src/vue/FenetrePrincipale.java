package vue;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import model.Game;

public class FenetrePrincipale extends JFrame {
	
	private Game game;
	private MenuJeu _menuJeu;
	public PlateForme _plateForme;
	
	private boolean _isMenuJeu = true;
	private boolean _isPlateForme = false;;
	
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
		_isMenuJeu = true;
		_isPlateForme = false;
		this.getContentPane().removeAll();
		this._plateForme.reinitialiser();
		//this._plateForme = new PlateForme(game);
		this.getContentPane().add(_menuJeu);
		this.validate();
		this.repaint();
		//_menuJeu.setVisible(true);
		//_plateForme.setVisible(false);
	}
	
	public void changerVersPlateForme()
	{
		/*
		try{
			this.remove(_menuJeu);
		}catch(Exception e){};*/
		_isMenuJeu = false;
		_isPlateForme = true;
		this.getContentPane().removeAll();
		this.getContentPane().add(_plateForme);
		this.validate();
		this.repaint();
		//_menuJeu.setVisible(false);
		//_plateForme.setVisible(true);
	}

	public MenuJeu get_menuJeu() {
		return _menuJeu;
	}

	public void set_menuJeu(MenuJeu _menuJeu) {
		this._menuJeu = _menuJeu;
	}

	public boolean isMenuJeu() {
		return _isMenuJeu;
	}

	public void set_isMenuJeu(boolean _isMenuJeu) {
		this._isMenuJeu = _isMenuJeu;
	}

	public boolean isPlateForme() {
		return _isPlateForme;
	}

	public void set_isPlateForme(boolean _isPlateForme) {
		this._isPlateForme = _isPlateForme;
	}
	
	

}
