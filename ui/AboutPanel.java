package ui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Dimension;

public class AboutPanel extends JPanel{
	public AboutPanel(){
		setLayout(null);
		
		setPreferredSize(new Dimension(420, 400));
		setSize(420, 400);
	}

	public void setAbout(){
		ImagePanel ip = new ImagePanel("src/help.png", "help");
		ip.setPreferredSize(new Dimension(ip.getWidth(), ip.getHeight()));
		JScrollPane jsp =  new JScrollPane(ip);
		jsp.setBounds(0,0,420, 400);
		add(jsp);
		repaint();
	}


}