package UIBoundary;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class RepoLabel extends JPanel{

	public static ImageIcon icon;
	
	public RepoLabel(){
		icon = new ImageIcon("404.jpg");
	}
	
	@Override
	public void paint(Graphics g){
		
		
		super.paint(g);
		repaint();
	}
}
