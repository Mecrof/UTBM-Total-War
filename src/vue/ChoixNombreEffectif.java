package vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner.DefaultEditor;

import model.Salle;

public class ChoixNombreEffectif extends JDialog implements ActionListener{
	
	private boolean _isAntiAliasing;
	//private Salle salle;
	private int _effectifSalle;
	
	private SpinnerNumberModel _smNombreModel;
	private JSpinner _spNombreEffectif;
	
	private JButton _bOk;
	private JButton _bAnnuler;
	
	private int _effectifChoisit = 1;
	private boolean _isOk = false;
	
	public ChoixNombreEffectif(int effectifSalle, int x, int y, boolean antiAlia) {
		this._isAntiAliasing = antiAlia;
		
		this._effectifSalle = effectifSalle;
		
		this.setLayout(new GridLayout(2, 1));
		
		_smNombreModel = new SpinnerNumberModel(1, 1, this._effectifSalle, 1);
		_spNombreEffectif = new JSpinner(_smNombreModel);
		((DefaultEditor) _spNombreEffectif.getEditor()).getTextField().setEditable(false);
		
		JPanel pan = new JPanel();
		_bOk = new JButton("Ok");
		_bAnnuler = new JButton("Annuler");
		pan.add(_bAnnuler);
		pan.add(_bOk);
		
		this.add(_spNombreEffectif);
		this.add(pan);
		
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this._bAnnuler.addActionListener(this);
		this._bOk.addActionListener(this);
		this.setModal(true);
		this.pack();
		this.setLocation(x-this.getWidth()/2, y-this.getHeight()/2);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(_bAnnuler))
		{
			_isOk = false;
			this.dispose();
		}
		else if (e.getSource().equals(_bOk))
		{
			_effectifChoisit = (int) _spNombreEffectif.getValue();
			_isOk = true;
			this.dispose();
		}
		
	}
	
	@Override
	public void paint(Graphics g2) {
		super.paint(g2);
		Graphics2D g = (Graphics2D)g2;
		g.setColor(Color.BLACK);
		if (_isAntiAliasing)
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	public int get_effectifChoisit() {
		return _effectifChoisit;
	}

	public void set_effectifChoisit(int _effectifChoisit) {
		this._effectifChoisit = _effectifChoisit;
	}

	public boolean isOk() {
		return _isOk;
	}

	public void set_isOk(boolean isOk) {
		this._isOk = _isOk;
	}

}
