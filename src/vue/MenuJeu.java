package vue;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import model.Game;
import model.Type;
import model.TypeEtudiant;
import model.TypeSpecialite;

public class MenuJeu extends JPanel {//implements MouseMotionListener, MouseListener{
	
	private Game game;
	
	private boolean _isAntialiasing = true;
	
	private boolean _isMenuPrincipal = true;
	private boolean _isMenuMap = false;
	private boolean _isMenuConfigJoueur = false;
	private boolean _isMenuChoixEtudiant = false;
	
	static final public int[] DIMENSION_BOUTONS = {150, 50};
	//private BufferedImage _imBtNouvellePart = null;
	//private BufferedImage _imBtOptions = null;
	//private BufferedImage _imBtQuitter = null;
	private JButton _bNouvellePartie = new JButton("Nouvelle partie");
	private JButton _bOptions = new JButton("Options");
	private JButton _bQuitter = new JButton("Quitter");
	
	private JButton _bRetour = new JButton("<< Précédent");
	private JButton _bSuivant = new JButton("Suivant >>");
	
	private JTextField _tfNomJoueur1 = new JTextField();
	private JTextField _tfNomJoueur2 = new JTextField();
	
	private BufferedImage _imMap = null;
	
	private BufferedImage _imRepFond = null; // 176x176 //894x894 //340x340
	private BufferedImage _imRepG = null; // 200x100
	private BufferedImage _imRepD = null; // 200x100
	
	private DefaultListModel defList = new DefaultListModel();
	private JList listeDeBatiments = new JList(defList);
	private JScrollPane listeDeBatimentsScroller;
	
	private boolean _isJoueur1AChoisi = false;
	private boolean _isJoueur2AChoisi = false;
	private ArrayList<Type[]> _mCombinaisons;
	private int _nbCombiRestante = 4;
	private JButton _bCombinaisonSuivante = new JButton("Combinaison suivante >");
	private JButton _bChoisirCombinaison = new JButton("Choisir");
	
	public MenuJeu(Game g) {
		this.game = g;
		this.setSize(800, 600);
		//_tfNomJoueur1.setEditable(b)
		try {
			_imRepFond = ImageIO.read(new File("images/repet/image-repetee-fond.png"));
			_imRepG = ImageIO.read(new File("images/repet/image-repetee-g.png"));
			_imRepD = ImageIO.read(new File("images/repet/image-repetee-d.png"));
		} catch (IOException e) {}
		
		this.add(_bNouvellePartie);
		this.add(_bOptions);
		this.add(_bQuitter);
	}
	
	@Override
	public void paint(Graphics g2) {
		super.paint(g2);
		Graphics2D g = (Graphics2D)g2;
		g.setColor(Color.BLACK);
		if (_isAntialiasing)
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		//gere la repetition des images de fond
		int posX = 0, posY = 0;
		do{
			do{
				g.drawImage(_imRepFond,posX,posY,null);
				posX += 340;
			}while(posX<this.getWidth());
			posX = 0;
			posY += 340;
		}while(posY<this.getHeight());
		posY = 0;
		do{
			g.drawImage(_imRepG,0,posY,null);
			g.drawImage(_imRepD,this.getWidth()-200,posY,null);
			posY += 100;
		}while(posY<this.getHeight());

		g.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		//g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		if(_isMenuPrincipal)
		{
			_bNouvellePartie.setBounds(	(this.getWidth()/2)-DIMENSION_BOUTONS[0]/2,
										(this.getHeight()/4)-DIMENSION_BOUTONS[1]/2, 
										DIMENSION_BOUTONS[0], 
										DIMENSION_BOUTONS[1]);
			_bOptions.setBounds(	(this.getWidth()/2)-DIMENSION_BOUTONS[0]/2,
									(this.getHeight()/2)-DIMENSION_BOUTONS[1]/2, 
									DIMENSION_BOUTONS[0], 
									DIMENSION_BOUTONS[1]);
			_bQuitter.setBounds(	(this.getWidth()/2)-DIMENSION_BOUTONS[0]/2,
									(this.getHeight()*3/4)-DIMENSION_BOUTONS[1]/2, 
									DIMENSION_BOUTONS[0], 
									DIMENSION_BOUTONS[1]);
		}
		else if (_isMenuMap)
		{
			g.fillRect((this.getWidth()/2)-101, (this.getHeight()/4)-101, 202, 202);
			if(_imMap!=null)
			{
				System.out.println("w:"+_imMap.getWidth()+"   h:"+_imMap.getHeight());
				int diff = (int)((200-_imMap.getHeight())/2);
				System.out.println("diff:"+diff);
				g.drawImage(_imMap,
							(this.getWidth()/2)-100,
							(this.getHeight()/4)-100+diff,
							null
							);
			}
			
			g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
			g.drawString("Choisissez le batiment à conquérir", this.getWidth()/2-200, this.getHeight()/2 - 8);
			
			g.setColor(Color.DARK_GRAY);
			g.fillRect(	(this.getWidth()/2)-204,
						(this.getHeight()/2)-4, 
						408, 
						(this.getHeight()/4)+8
						);
			listeDeBatimentsScroller.setBounds(	(this.getWidth()/2)-200,
									(this.getHeight()/2), 
									400, 
									(this.getHeight()/4)
									);
			_bRetour.setBounds(	(this.getWidth()/2)-200,
								(this.getHeight()*5/6), 
								DIMENSION_BOUTONS[0], 
								DIMENSION_BOUTONS[1]);
			_bSuivant.setBounds((this.getWidth()/2)+50,
								(this.getHeight()*5/6), 
								DIMENSION_BOUTONS[0], 
								DIMENSION_BOUTONS[1]);
		}
		else if (_isMenuConfigJoueur)
		{
			g.setFont(new Font("Times New Roman", Font.BOLD, 20));
			g.drawString("Saisissez vos pseudos", this.getWidth()/2-200, this.getHeight()/10);
			g.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			
			_bRetour.setBounds(	(this.getWidth()/2)-200,
					(this.getHeight()*5/6), 
					DIMENSION_BOUTONS[0], 
					DIMENSION_BOUTONS[1]);
			_bSuivant.setBounds((this.getWidth()/2)+50,
					(this.getHeight()*5/6), 
					DIMENSION_BOUTONS[0], 
					DIMENSION_BOUTONS[1]);
			g.drawString("Pseudo du joueur 1 :", 
					this.getWidth()/2-165, 
					this.getHeight()/4+18);
			_tfNomJoueur1.setBounds(this.getWidth()/2-35, 
					this.getHeight()/4, 
					200, 
					30);
			g.drawString("Pseudo du joueur 2 :", 
					this.getWidth()/2-165, 
					this.getHeight()/2+18);
			_tfNomJoueur2.setBounds(this.getWidth()/2-35, 
					this.getHeight()/2, 
					200, 
					30);
		}
		else if (_isMenuChoixEtudiant)
		{
			g.setFont(new Font("Times New Roman", Font.BOLD, 20));
			g.drawString("Choisissez votre type d'étudiant", this.getWidth()/2-200, this.getHeight()/10);
			
			if (_isJoueur1AChoisi && !_isJoueur2AChoisi)
			{
				g.drawString("> Joueur 1 OK", this.getWidth()/2+100, this.getHeight()/10);
				g.drawString("> Joueur 2", this.getWidth()/2+100, this.getHeight()/10 + 25);
			}
			else if (_isJoueur2AChoisi)
			{
				g.drawString("> Joueur 1 OK", this.getWidth()/2+100, this.getHeight()/10);
				g.drawString("> Joueur 2 OK", this.getWidth()/2+100, this.getHeight()/10 + 25);
			}
			else
				g.drawString("> Joueur 1", this.getWidth()/2+110, this.getHeight()/10);
			
			g.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			
			g.fillRect(this.getWidth()/2-200, 
					this.getHeight()/6, 
					150, 
					150);
			g.fillRect(this.getWidth()/2+50, 
					this.getHeight()/6, 
					150, 
					150);
			g.drawString(_mCombinaisons.get(4-_nbCombiRestante)[0].get_nom(), 
					this.getWidth()/2-200, 
					this.getHeight()/6+170);
			g.drawString(_mCombinaisons.get(4-_nbCombiRestante)[0].get_type(), 
					this.getWidth()/2-200, 
					this.getHeight()/6+190);
			g.drawString(_mCombinaisons.get(4-_nbCombiRestante)[1].get_nom(), 
					this.getWidth()/2+50, 
					this.getHeight()/6+170);
			g.drawString(_mCombinaisons.get(4-_nbCombiRestante)[1].get_type(), 
					this.getWidth()/2+50, 
					this.getHeight()/6+190);
			g.drawString(_nbCombiRestante+" combinaisons restantes",
					(this.getWidth()/2)-200, 
					(this.getHeight()*2/3)-5);
			_bCombinaisonSuivante.setBounds((this.getWidth()/2)-200, 
					(this.getHeight()*2/3), 
					290, 
					50);
			_bChoisirCombinaison.setBounds((this.getWidth()/2)+110, 
					(this.getHeight()*2/3), 
					90, 
					50);
			g.drawString("Attention,  aucun retour possible ! Réflechissez bien !", 
					(this.getWidth()/2)-200, 
					(this.getHeight()*2/3)+70);
			_bRetour.setBounds(	(this.getWidth()/2)-200,
					(this.getHeight()*5/6), 
					DIMENSION_BOUTONS[0], 
					DIMENSION_BOUTONS[1]);
			_bSuivant.setBounds((this.getWidth()/2)+50,
					(this.getHeight()*5/6), 
					DIMENSION_BOUTONS[0], 
					DIMENSION_BOUTONS[1]);
		}
		super.paintComponents(g);
	}
	
	public void mettreMenuPrincipal()
	{
		_isMenuPrincipal = true;
		_isMenuMap = false;
		_isMenuConfigJoueur = false;
		_isMenuChoixEtudiant = false;
		this.removeAll();
		this.setLayout(null);
		this.add(_bNouvellePartie);
		this.add(_bOptions);
		this.add(_bQuitter);
		this.repaint();
	}
	
	public void mettreMenuMap()
	{
		_isMenuPrincipal = false;
		_isMenuMap = true;
		_isMenuConfigJoueur = false;
		_isMenuChoixEtudiant = false;
		
		this.removeAll();
		
		this.setLayout(null);
		this.defList.removeAllElements();
		this._imMap = null;
		
		LinkedList<String> l = game.afficherListeSalles();		
		for (int i = 0; i<l.size(); ++i)
			defList.addElement(l.get(i));
		//listeDeBatiments.
		listeDeBatiments.setLayoutOrientation(JList.VERTICAL);
		listeDeBatimentsScroller = new JScrollPane(listeDeBatiments);
		this.add(_bRetour);
		_bSuivant.setEnabled(false);
		this.add(_bSuivant);
		this.add(listeDeBatimentsScroller);
		this.repaint();
	}
	
	public void mettreMenuConfigJoueur()
	{
		_isMenuPrincipal = false;
		_isMenuMap = false;
		_isMenuConfigJoueur = true;
		_isMenuChoixEtudiant = false;
		
		_mCombinaisons = game.renewVisibleCoupleEtuSpe();
		
		this.removeAll();
		this.add(_tfNomJoueur1);
		//_tfNomJoueur1.setText("j1");
		this.add(_tfNomJoueur2);
		//_tfNomJoueur2.setText("j2");
		this.add(_bRetour);
		this.add(_bSuivant);
		this.repaint();
	}
	
	public void mettreMenuChoixEtudiant()
	{
		_isMenuPrincipal = false;
		_isMenuMap = false;
		_isMenuConfigJoueur = false;
		_isMenuChoixEtudiant = true;
		this.removeAll();
		this.add(_bCombinaisonSuivante);
		this.add(_bChoisirCombinaison);
		this.add(_bRetour);
		this.add(_bSuivant);
		_bSuivant.setEnabled(false);
		this.repaint();
	}

	public boolean isAntialiasing() {
		return _isAntialiasing;
	}

	public void set_isAntialiasing(boolean _isAntialiasing) {
		this._isAntialiasing = _isAntialiasing;
	}

	public boolean isMenuPrincipal() {
		return _isMenuPrincipal;
	}

	public void set_isMenuPrincipal(boolean _isMenuPrincipal) {
		this._isMenuPrincipal = _isMenuPrincipal;
	}

	public boolean isMenuMap() {
		return _isMenuMap;
	}

	public void set_isMenuMap(boolean _isMenuMap) {
		this._isMenuMap = _isMenuMap;
	}

	public boolean isMenuConfigJoueur() {
		return _isMenuConfigJoueur;
	}

	public void set_isMenuConfigJoueur(boolean _isMenuConfigJoueur) {
		this._isMenuConfigJoueur = _isMenuConfigJoueur;
	}

	public boolean isMenuChoixEtudiant() {
		return _isMenuChoixEtudiant;
	}

	public void set_isMenuChoixEtudiant(boolean _isMenuChoixEtudiant) {
		this._isMenuChoixEtudiant = _isMenuChoixEtudiant;
	}

	public JList getListeDeBatiments() {
		return listeDeBatiments;
	}

	public void setListeDeBatiments(JList listeDeBatiments) {
		this.listeDeBatiments = listeDeBatiments;
	}

	public JScrollPane getListeDeBatimentsScroller() {
		return listeDeBatimentsScroller;
	}

	public void setListeDeBatimentsScroller(JScrollPane listeDeBatimentsScroller) {
		this.listeDeBatimentsScroller = listeDeBatimentsScroller;
	}

	public BufferedImage get_imMap() {
		return _imMap;
	}

	public void set_imMap(BufferedImage _imMap) {
		this._imMap = _imMap;
	}

	public JButton get_bNouvellePartie() {
		return _bNouvellePartie;
	}

	public void set_bNouvellePartie(JButton _bNouvellePartie) {
		this._bNouvellePartie = _bNouvellePartie;
	}

	public JButton get_bOptions() {
		return _bOptions;
	}

	public void set_bOptions(JButton _bOptions) {
		this._bOptions = _bOptions;
	}

	public JButton get_bQuitter() {
		return _bQuitter;
	}

	public void set_bQuitter(JButton _bQuitter) {
		this._bQuitter = _bQuitter;
	}

	public JButton get_bRetour() {
		return _bRetour;
	}

	public void set_bRetour(JButton _bRetour) {
		this._bRetour = _bRetour;
	}

	public JButton get_bSuivant() {
		return _bSuivant;
	}

	public void set_bSuivant(JButton _bSuivant) {
		this._bSuivant = _bSuivant;
	}

	public JTextField get_tfNomJoueur1() {
		return _tfNomJoueur1;
	}

	public void set_tfNomJoueur1(JTextField _tfNomJoueur1) {
		this._tfNomJoueur1 = _tfNomJoueur1;
	}

	public JTextField get_tfNomJoueur2() {
		return _tfNomJoueur2;
	}

	public void set_tfNomJoueur2(JTextField _tfNomJoueur2) {
		this._tfNomJoueur2 = _tfNomJoueur2;
	}

	public boolean isJoueur1AChoisi() {
		return _isJoueur1AChoisi;
	}

	public void set_isJoueur1AChoisi(boolean _isJoueur1AChoisi) {
		this._isJoueur1AChoisi = _isJoueur1AChoisi;
	}

	public boolean isJoueur2AChoisi() {
		return _isJoueur2AChoisi;
	}

	public void set_isJoueur2AChoisi(boolean _isJoueur2AChoisi) {
		this._isJoueur2AChoisi = _isJoueur2AChoisi;
	}

	public JButton get_bCombinaisonSuivante() {
		return _bCombinaisonSuivante;
	}

	public void set_bCombinaisonSuivante(JButton _bCombinaisonSuivante) {
		this._bCombinaisonSuivante = _bCombinaisonSuivante;
	}

	public JButton get_bChoisirCombinaison() {
		return _bChoisirCombinaison;
	}

	public void set_bChoisirCombinaison(JButton _bChoisirCombinaison) {
		this._bChoisirCombinaison = _bChoisirCombinaison;
	}

	public int get_nbCombiRestante() {
		return _nbCombiRestante;
	}

	public void set_nbCombiRestante(int _nbCombiRestante) {
		this._nbCombiRestante = _nbCombiRestante;
	}

	public ArrayList<Type[]> get_mCombinaisons() {
		return _mCombinaisons;
	}

	public void set_mCombinaisons(ArrayList<Type[]> _mCombinaisons) {
		this._mCombinaisons = _mCombinaisons;
	}

}
