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
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import model.Game;
import model.Type;
import model.TypeEtudiant;
import model.TypeSpecialite;

public class MenuJeu extends JPanel {//implements MouseMotionListener, MouseListener{
	
	private Game game;

	
	private boolean _isMenuPrincipal = true;
	private boolean _isOption = false;
	private boolean _isMenuMap = false;
	private boolean _isMenuConfigPartie = false;
	private boolean _isMenuChoixEtudiant = false;
	private boolean _isPleinEcran = false;
	
	static final public int[] DIMENSION_BOUTONS = {150, 50};
	//private BufferedImage _imBtNouvellePart = null;
	//private BufferedImage _imBtOptions = null;
	//private BufferedImage _imBtQuitter = null;
	private JButton _bNouvellePartie = new JButton("Nouvelle partie");
	private JButton _bOptions = new JButton("Options");
	private JButton _bQuitter = new JButton("Quitter");
	
	private JButton _bRetour = new JButton("<< Précédent");
	private JButton _bSuivant = new JButton("Suivant >>");
	
	private JCheckBox _cbAntiAlia = new JCheckBox("Antialiasing");
	private JCheckBox _cbPleinEcran = new JCheckBox("Mode plein écran");
	private JButton _bAppliquer = new JButton("Appliquer");
	
	private JTextField _tfNomJoueur1 = new JTextField();
	private JTextField _tfNomJoueur2 = new JTextField();
	private JButton _bPlusTour = new JButton("+");
	private JButton _bMoinsTour = new JButton("-");
	private int _nombreTour = 10;
	
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
		this.setLayout(null);
		//_tfNomJoueur1.setEditable(b)
		try {
			_imRepFond = ImageIO.read(new File("images/repet/image-repetee-fond.png"));
			_imRepG = ImageIO.read(new File("images/repet/image-repetee-g.png"));
			_imRepD = ImageIO.read(new File("images/repet/image-repetee-d.png"));
		} catch (IOException e) {}
		this._bMoinsTour.setFont(new Font("monospaced", Font.BOLD, 10));
		this._bPlusTour.setFont(new Font("monospaced", Font.BOLD, 10));
		
		this.add(_bNouvellePartie);
		this.add(_bOptions);
		this.add(_bQuitter);
	}
	
	public void reinitialiser()
	{
		//_isAntialiasing = true;
		
		_isMenuPrincipal = true;
		_isMenuMap = false;
		_isMenuConfigPartie = false;
		_isMenuChoixEtudiant = false;
		
		_nombreTour = 10;
		
		_isJoueur1AChoisi = false;
		_isJoueur2AChoisi = false;
		_mCombinaisons = null;
		_nbCombiRestante = 4;
	}
	
	@Override
	public void paint(Graphics g2) {
		super.paint(g2);
		Graphics2D g = (Graphics2D)g2;
		g.setColor(Color.BLACK);
		if (this.game.isAntialiasing())
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
		if(_isMenuPrincipal)
		{
			g.setColor(Color.BLACK);
				g.setFont(new Font("monospaced", Font.BOLD, 80));
					g.drawString("UTBM", this.getWidth()/2-100, this.getHeight()/10+4);
				g.setFont(new Font("monospaced", Font.BOLD, 50));
					g.drawString("Total War", this.getWidth()/2-130, this.getHeight()*2/12+4);
			g.setColor(Color.WHITE);
				g.setFont(new Font("monospaced", Font.BOLD, 80));
					g.drawString("UTBM", this.getWidth()/2-100, this.getHeight()/10);
				g.setFont(new Font("monospaced", Font.BOLD, 50));
					g.drawString("Total War", this.getWidth()/2-130, this.getHeight()*2/12);
			
			g.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			
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
		else if (_isOption)
		{
			g.setColor(Color.BLACK);
				g.setFont(new Font("monospaced", Font.BOLD, 80));
					g.drawString("UTBM", this.getWidth()/2-100, this.getHeight()/10+4);
				g.setFont(new Font("monospaced", Font.BOLD, 50));
					g.drawString("Total War", this.getWidth()/2-130, this.getHeight()*2/12+4);
			g.setColor(Color.WHITE);
				g.setFont(new Font("monospaced", Font.BOLD, 80));
					g.drawString("UTBM", this.getWidth()/2-100, this.getHeight()/10);
				g.setFont(new Font("monospaced", Font.BOLD, 50));
					g.drawString("Total War", this.getWidth()/2-130, this.getHeight()*2/12);
		
			g.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			
			_cbAntiAlia.setBounds(this.getWidth()/2-85, this.getHeight()/3, 170, 50);
			_cbPleinEcran.setBounds(this.getWidth()/2-120, this.getHeight()/2, 240, 50);
			
			_bRetour.setBounds(	(this.getWidth()/2)-200,
					(this.getHeight()*5/6), 
					DIMENSION_BOUTONS[0], 
					DIMENSION_BOUTONS[1]);
			_bAppliquer.setBounds((this.getWidth()/2)+50,
					(this.getHeight()*5/6), 
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
		else if (_isMenuConfigPartie)
		{
			g.setFont(new Font("Times New Roman", Font.BOLD, 20));
			g.drawString("Configuration de la partie", this.getWidth()/2-200, this.getHeight()/10);
			g.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			
			g.drawString("Nombre de tour(s) :", 
					this.getWidth()/2-165, 
					this.getHeight()/6+18);
			
			g.drawString(""+_nombreTour, 
					this.getWidth()/2+40, 
					this.getHeight()/6+18);
			
			_bPlusTour.setBounds(this.getWidth()/2-35+200-80, 
					this.getHeight()/6, 
					40, 
					30);
			_bMoinsTour.setBounds(this.getWidth()/2-35+200-40, 
					this.getHeight()/6, 
					40, 
					30);
			
			//_sNombreTour.setBounds(this.getWidth()/2-35, 
			//		this.getHeight()/6, 
			//		200, 
			//		30);
			
			g.drawString("Pseudo du joueur 1 :", 
					this.getWidth()/2-165, 
					this.getHeight()/3+18);
			_tfNomJoueur1.setBounds(this.getWidth()/2-35, 
					this.getHeight()/3, 
					200, 
					30);
			g.drawString("Pseudo du joueur 2 :", 
					this.getWidth()/2-165, 
					this.getHeight()/2+18);
			_tfNomJoueur2.setBounds(this.getWidth()/2-35, 
					this.getHeight()/2, 
					200, 
					30);

			
			_bRetour.setBounds(	(this.getWidth()/2)-200,
					(this.getHeight()*5/6), 
					DIMENSION_BOUTONS[0], 
					DIMENSION_BOUTONS[1]);
			_bSuivant.setBounds((this.getWidth()/2)+50,
					(this.getHeight()*5/6), 
					DIMENSION_BOUTONS[0], 
					DIMENSION_BOUTONS[1]);
			
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
			
			
			g.fillRect(this.getWidth()/2-200-2, 
					this.getHeight()/6-2, 
					150+4, 
					150+4);
			Image img = _mCombinaisons.get(4-_nbCombiRestante)[0].get_image();
			img = Game.redimensionnerImage(img, img.getWidth(null), img.getHeight(null), 150, 150);
			g.drawImage(img, this.getWidth()/2-200, this.getHeight()/6, this);
			
			g.fillRect(this.getWidth()/2+50-2, 
					this.getHeight()/6-2, 
					150+4, 
					150+4);
			img = _mCombinaisons.get(4-_nbCombiRestante)[1].get_image();
			img = Game.redimensionnerImage(img, img.getWidth(null), img.getHeight(null), 150, 150);
			g.drawImage(img, this.getWidth()/2+50, this.getHeight()/6, this);
			
			g.drawString(_mCombinaisons.get(4-_nbCombiRestante)[0].get_nom(), 
					this.getWidth()/2-200, 
					this.getHeight()/6+170);
			g.drawString(_mCombinaisons.get(4-_nbCombiRestante)[0].get_type().getCode(), 
					this.getWidth()/2-200, 
					this.getHeight()/6+190);
			g.drawString(_mCombinaisons.get(4-_nbCombiRestante)[1].get_nom(), 
					this.getWidth()/2+50, 
					this.getHeight()/6+170);
			g.drawString(_mCombinaisons.get(4-_nbCombiRestante)[1].get_type().getCode(), 
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
		_isOption = false;
		_isMenuMap = false;
		_isMenuConfigPartie = false;
		_isMenuChoixEtudiant = false;
		this.removeAll();
		this.setLayout(null);
		this.add(_bNouvellePartie);
		this.add(_bOptions);
		this.add(_bQuitter);
		this.repaint();
	}
	
	public void mettreMenuOptions()
	{
		_isMenuPrincipal = false;
		_isOption = true;
		_isMenuMap = false;
		_isMenuConfigPartie = false;
		_isMenuChoixEtudiant = false;
		this.removeAll();
		this.setLayout(null);
		_cbAntiAlia.setSelected(true);
		_cbAntiAlia.setOpaque(false);
		_cbAntiAlia.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		_cbPleinEcran.setSelected(_isPleinEcran);
		_cbPleinEcran.setOpaque(false);
		_cbPleinEcran.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		//_cbPleinEcran.setEnabled(false);
		this.add(_cbAntiAlia);
		this.add(_cbPleinEcran);
		this.add(_bRetour);
		this.add(_bAppliquer);
		this.repaint();
	}
	
	public void mettreMenuMap()
	{
		_isMenuPrincipal = false;
		_isOption = false;
		_isMenuMap = true;
		_isMenuConfigPartie = false;
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
	
	public void mettreMenuConfigPartie()
	{
		_isMenuPrincipal = false;
		_isOption = false;
		_isMenuMap = false;
		_isMenuConfigPartie = true;
		_isMenuChoixEtudiant = false;
		
		_mCombinaisons = game.renewVisibleCoupleEtuSpe();
		
		this.removeAll();
		this.setLayout(null);
		this.add(_tfNomJoueur1);
		//_tfNomJoueur1.setText("j1");
		this.add(_tfNomJoueur2);
		this.add(_bPlusTour);
		this.add(_bMoinsTour);
		//this.add(_sNombreTour);
		//_tfNomJoueur2.setText("j2");
		this.add(_bRetour);
		this.add(_bSuivant);
		this.repaint();
		this.repaint();
	}
	
	public void mettreMenuChoixEtudiant()
	{
		_isMenuPrincipal = false;
		_isOption = false;
		_isMenuMap = false;
		_isMenuConfigPartie = false;
		_isMenuChoixEtudiant = true;
		this.removeAll();
		this.setLayout(null);
		_bCombinaisonSuivante.setEnabled(true);
		_bChoisirCombinaison.setEnabled(true);
		this.add(_bCombinaisonSuivante);
		this.add(_bChoisirCombinaison);
		this.add(_bRetour);
		this.add(_bSuivant);
		_bSuivant.setEnabled(false);
		this.repaint();
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

	public boolean isMenuConfigPartie() {
		return _isMenuConfigPartie;
	}

	public void set_isMenuConfigPartie(boolean _isMenuConfigPartie) {
		this._isMenuConfigPartie = _isMenuConfigPartie;
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

	public JButton get_bPlusTour() {
		return _bPlusTour;
	}

	public void set_bPlusTour(JButton _bPlusTour) {
		this._bPlusTour = _bPlusTour;
	}

	public JButton get_bMoinsTour() {
		return _bMoinsTour;
	}

	public void set_bMoinsTour(JButton _bMoinsTour) {
		this._bMoinsTour = _bMoinsTour;
	}

	public int get_nombreTour() {
		return _nombreTour;
	}

	public void set_nombreTour(int _nombreTour) {
		this._nombreTour = _nombreTour;
	}

	public boolean isOption() {
		return _isOption;
	}

	public void set_isOption(boolean _isOption) {
		this._isOption = _isOption;
	}

	public JCheckBox get_cbAntiAlia() {
		return _cbAntiAlia;
	}

	public void set_cbAntiAlia(JCheckBox _cbAntiAlia) {
		this._cbAntiAlia = _cbAntiAlia;
	}

	public JCheckBox get_cbPleinEcran() {
		return _cbPleinEcran;
	}

	public void set_cbPleinEcran(JCheckBox _cbPleinEcran) {
		this._cbPleinEcran = _cbPleinEcran;
	}

	public JButton get_bAppliquer() {
		return _bAppliquer;
	}

	public void set_bAppliquer(JButton _bAppliquer) {
		this._bAppliquer = _bAppliquer;
	}

	public boolean isPleinEcran() {
		return _isPleinEcran;
	}

	public void set_isPleinEcran(boolean _isPleinEcran) {
		this._isPleinEcran = _isPleinEcran;
	}
	
	

}
