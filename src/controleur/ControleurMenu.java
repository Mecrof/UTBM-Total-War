package controleur;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Batiment;
import model.Game;

import vue.FenetrePrincipale;
import vue.MenuJeu;

public class ControleurMenu implements ListSelectionListener, ActionListener {

	private MenuJeu menu;
	private Game game;
	
	private FenetrePrincipale fenetrePrincipale;
	private Object ancienneSource = new Object();
	private String _pseudoJoueur1 = "j1", _pseudoJoueur2 = "j2";
	private String _batimentChoisi;
	private int _combinaisonsRestantes = 4;
	private int _combiJoueur1 = 0;
	private int _combiJoueur2 = 0;
	
	public ControleurMenu(Game g, MenuJeu m, FenetrePrincipale f) {
		this.game = g;
		this.menu = m;
		this.fenetrePrincipale = f;
		this.menu.getListeDeBatiments().addListSelectionListener(this);
		this.menu.get_bNouvellePartie().addActionListener(this);
		this.menu.get_bOptions().addActionListener(this);
		this.menu.get_bQuitter().addActionListener(this);
		this.menu.get_bRetour().addActionListener(this);
		this.menu.get_bSuivant().addActionListener(this);
		this.menu.get_bCombinaisonSuivante().addActionListener(this);
		this.menu.get_bChoisirCombinaison().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(menu.isMenuPrincipal())
		{
			if(e.getSource().equals(menu.get_bNouvellePartie()))
			{
				menu.mettreMenuMap();
			}
			else if((e.getSource().equals(menu.get_bOptions())))
			{
				// OPTIONS
			}
			else if((e.getSource().equals(menu.get_bQuitter())))
			{
				System.exit(0);
			}
		}
		else if(menu.isMenuMap())
		{
			if(e.getSource().equals(menu.get_bRetour()))
			{
				ancienneSource = new Object();
				menu.mettreMenuPrincipal();
				
			}
			else if(e.getSource().equals(menu.get_bSuivant()))
			{
				ancienneSource = new Object();
				_batimentChoisi = (String) menu.getListeDeBatiments().getSelectedValue();
				menu.mettreMenuConfigJoueur();
				menu.get_tfNomJoueur1().setText(_pseudoJoueur1);
				menu.get_tfNomJoueur2().setText(_pseudoJoueur2);
			}
		}
		else if(menu.isMenuConfigJoueur())
		{
			if(e.getSource().equals(menu.get_bRetour()))
			{
				_pseudoJoueur1 = "j1";
				_pseudoJoueur2 = "j2";
				menu.mettreMenuMap();
			}
			else if (e.getSource().equals(menu.get_bSuivant()))
			{
				_pseudoJoueur1 = menu.get_tfNomJoueur1().getText();
				_pseudoJoueur2 = menu.get_tfNomJoueur2().getText();
				menu.mettreMenuChoixEtudiant();
			}
		}
		else if(menu.isMenuChoixEtudiant())
		{
			if(e.getSource().equals(menu.get_bRetour()))
			{
				menu.get_bSuivant().setEnabled(true);
				menu.mettreMenuConfigJoueur();
				menu.get_tfNomJoueur1().setText(_pseudoJoueur1);
				menu.get_tfNomJoueur2().setText(_pseudoJoueur2);
				menu.set_nbCombiRestante(4);
				menu.get_bCombinaisonSuivante().setEnabled(true);
				menu.get_bChoisirCombinaison().setEnabled(true);
				menu.set_isJoueur1AChoisi(false);
				menu.set_isJoueur2AChoisi(false);
			}
			else if (e.getSource().equals(menu.get_bCombinaisonSuivante()))
			{
				menu.set_nbCombiRestante(menu.get_nbCombiRestante()-1);
				menu.repaint();
				if(menu.get_nbCombiRestante()<1)
					menu.get_bCombinaisonSuivante().setEnabled(false);
			}
			else if (e.getSource().equals(menu.get_bChoisirCombinaison()))
			{
				if (!menu.isJoueur1AChoisi())
				{
					menu.set_isJoueur1AChoisi(true);
					_combiJoueur1 = 4-menu.get_nbCombiRestante();
					menu.set_nbCombiRestante(4);
					menu.get_bCombinaisonSuivante().setEnabled(true);
					menu.repaint();
				}
				else
				{
					menu.set_isJoueur2AChoisi(true);
					_combiJoueur2 = 4-menu.get_nbCombiRestante();
					menu.get_bCombinaisonSuivante().setEnabled(false);
					menu.get_bChoisirCombinaison().setEnabled(false);
					menu.get_bSuivant().setEnabled(true);
					menu.repaint();
				}
			}
			else if (e.getSource().equals(menu.get_bSuivant()))
			{
				
					game.getJ1().set_idEtudiantActif(menu.get_mCombinaisons().get(_combiJoueur1)[0].get_id());
					game.getJ1().set_idSpecialiteActif(menu.get_mCombinaisons().get(_combiJoueur1)[1].get_id());
					game.getJ2().set_idEtudiantActif(menu.get_mCombinaisons().get(_combiJoueur2)[0].get_id());
					game.getJ2().set_idSpecialiteActif(menu.get_mCombinaisons().get(_combiJoueur1)[1].get_id());
					if (_pseudoJoueur1.length()>9)
						_pseudoJoueur1 = _pseudoJoueur1.substring(0, 9) +"..";
					if (_pseudoJoueur2.length()>9)
						_pseudoJoueur2 = _pseudoJoueur2.substring(0, 9) +"..";
					game.getJ1().set_nom(_pseudoJoueur1);
					game.getJ2().set_nom(_pseudoJoueur2);
					// combinaison = 4 - nbCombiRestante
					_batimentChoisi = "./maps/"+_batimentChoisi.substring(0, _batimentChoisi.lastIndexOf(".")) + ".xml";
					game.set_batiment(new Batiment(_batimentChoisi));
					fenetrePrincipale.changerVersPlateForme();
					fenetrePrincipale.repaint();
				
				
			}
		}
		
	}
	
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		JList<String> jList = (JList<String>)e.getSource();
		if(jList.getSelectedValue()!=null)
			if (!jList.getSelectedValue().equals(ancienneSource)) // evite le double appel de la methode
			{
				if (e.getSource().equals(menu.getListeDeBatiments()))
				{
					if (!jList.isSelectionEmpty())
					{
						String s = jList.getSelectedValue();
						BufferedImage i;
						try {
							 i = ImageIO.read(new File("maps/"+s));
							 menu.set_imMap(Game.redimensionnerImage(i, i.getWidth(), i.getHeight(), 200, 200));
							 menu.get_bSuivant().setEnabled(true);
							 menu.repaint();
						} catch (IOException e1) {	e1.printStackTrace();  }
					}
				}
				ancienneSource = jList.getSelectedValue();
			}
		
	}
}
