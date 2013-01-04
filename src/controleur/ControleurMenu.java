package controleur;

import java.awt.Cursor;
import java.awt.Dimension;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Batiment;
import model.Game;

import vue.FenetrePrincipale;
import vue.MenuJeu;

public class ControleurMenu implements ListSelectionListener, ActionListener {//, ChangeListener {

	private MenuJeu menu;
	private Game game;
	
	private FenetrePrincipale fenetrePrincipale;
	private Object ancienneSource = new Object();
	private String _pseudoJoueur1 = "Joueur 1", _pseudoJoueur2 = "Joueur 2";
	private boolean _isCheckBoxChangementDetat = false;
	private String _batimentChoisi;
	private int _combinaisonsRestantes = 4;
	private int _nombreTour = 0;
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
		this.menu.get_cbPleinEcran().addActionListener(this);
		this.menu.get_bAppliquer().addActionListener(this);
		this.menu.get_bRetour().addActionListener(this);
		this.menu.get_bSuivant().addActionListener(this);
		this.menu.get_bMoinsTour().addActionListener(this);
		this.menu.get_bPlusTour().addActionListener(this);
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
				menu.mettreMenuOptions();// OPTIONS
			}
			else if((e.getSource().equals(menu.get_bQuitter())))
			{
				System.exit(0);
			}
		}
		else if(menu.isOption())
		{
			if(e.getSource().equals(menu.get_bRetour()))
			{
				menu.mettreMenuPrincipal();
			}
			else if (e.getSource().equals(menu.get_cbPleinEcran()))
			{
				_isCheckBoxChangementDetat = !_isCheckBoxChangementDetat;
			}
			else if (e.getSource().equals(menu.get_bAppliquer()))
			{
				game.set_isAntialiasing(menu.get_cbAntiAlia().isSelected());
				if (_isCheckBoxChangementDetat)
				{
					if (menu.get_cbPleinEcran().isSelected())
					{
						fenetrePrincipale.dispose();
						fenetrePrincipale.setUndecorated(true);
						fenetrePrincipale.setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize());
						fenetrePrincipale.setLocation(0, 0);
						fenetrePrincipale.setVisible(true);
						menu.set_isPleinEcran(true);
					}
					else
					{
						fenetrePrincipale.dispose();
						fenetrePrincipale.setUndecorated(false);
						fenetrePrincipale.setSize(new Dimension(800, 600));
						fenetrePrincipale.setLocationRelativeTo(null);
						fenetrePrincipale.setVisible(true);
						menu.set_isPleinEcran(false);
					}
					_isCheckBoxChangementDetat = false;
				}
				menu.repaint();
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
				menu.mettreMenuConfigPartie();
				menu.get_tfNomJoueur1().setText(_pseudoJoueur1);
				menu.get_tfNomJoueur2().setText(_pseudoJoueur2);
			}
		}
		else if(menu.isMenuConfigPartie())
		{
			Object s = e.getSource();
			if(s.equals(menu.get_bPlusTour()))
			{
				this.menu.set_nombreTour(this.menu.get_nombreTour()+1);
				this.menu.repaint();
			}
			else if(s.equals(menu.get_bMoinsTour()))
			{
				int nb = this.menu.get_nombreTour()-1;
				if(nb>=0)
				{
					this.menu.set_nombreTour(nb);
					this.menu.repaint();
				}
			}
			else if(s.equals(menu.get_bRetour()))
			{
				_pseudoJoueur1 = "Joueur 1";
				_pseudoJoueur2 = "Joueur 2";
				menu.set_nombreTour(10);
				menu.mettreMenuMap();
			}
			else if (s.equals(menu.get_bSuivant()))
			{
				_pseudoJoueur1 = menu.get_tfNomJoueur1().getText();
				_pseudoJoueur2 = menu.get_tfNomJoueur2().getText();
				_nombreTour = menu.get_nombreTour();
				menu.mettreMenuChoixEtudiant();
			}
		}
		else if(menu.isMenuChoixEtudiant())
		{
			if(e.getSource().equals(menu.get_bRetour()))
			{
				menu.get_bSuivant().setEnabled(true);
				menu.mettreMenuConfigPartie();
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
					game.getJ2().set_idSpecialiteActif(menu.get_mCombinaisons().get(_combiJoueur2)[1].get_id());
					if (_pseudoJoueur1.length()>9)
						_pseudoJoueur1 = _pseudoJoueur1.substring(0, 9) +"..";
					if (_pseudoJoueur2.length()>9)
						_pseudoJoueur2 = _pseudoJoueur2.substring(0, 9) +"..";
					game.getJ1().set_nom(_pseudoJoueur1);
					game.getJ2().set_nom(_pseudoJoueur2);
					game.set_nombreDeTour(_nombreTour);
					game.set_nombreDeTourEffectue(1);
					// combinaison = 4 - nbCombiRestante
					_batimentChoisi = "./maps/"+_batimentChoisi.substring(0, _batimentChoisi.lastIndexOf(".")) + ".xml";
					game.set_batiment(new Batiment(_batimentChoisi));
					this.fenetrePrincipale._plateForme.get_champsMap().chargerMap();
					fenetrePrincipale.changerVersPlateForme();
					fenetrePrincipale.repaint();
			}
		}
		
	}
	
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		JList jList = (JList)e.getSource();
		if(jList.getSelectedValue()!=null)
			if (!jList.getSelectedValue().equals(ancienneSource)) // evite le double appel de la methode
			{
				if (e.getSource().equals(menu.getListeDeBatiments()))
				{
					if (!jList.isSelectionEmpty())
					{
						String s = (String) jList.getSelectedValue();
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
