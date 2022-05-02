package ui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.GraphicsEnvironment;

public class VisualPanel extends JPanel{
	private MainClass mainClass;
    public JPanel outputPanel;
    public ValuesPanel valPanel;
    private Font font;
    private ImageLoader imgLoader;

    public VisualPanel(MainClass mainClass, int x, int y, int w, int h, Font font){
    	this.mainClass = mainClass;
        this.font  = font;
    	setLayout(null);
        setBounds(x, y, w, h);
        setOpaque(false);
        add(loadValuePanel());
        add(loadFramePanel());
    }

    public JPanel loadValuePanel(){
   	valPanel = new ValuesPanel(0,0,395,160, mainClass.getPageController(), font);
    	return valPanel;    
    }
    
    public JScrollPane loadFramePanel(){
    	outputPanel = new JPanel(null){
    		@Override
    		public void paintComponent(Graphics g){
    			super.paintComponent(g);
    			//put the frames here
    			setPreferredSize(new Dimension(800, 500)); //recalculate based on the farthest frame	
    		}
    	
    	};
    		
	outputPanel.setBackground(Color.white);

	JScrollPane jspDraw = new JScrollPane(outputPanel);
	jspDraw.setBounds(5,165,385, 386); 
    	return jspDraw;
    }
    
    public ValuesPanel getValuePanel(){
    	return valPanel;
    }
}	
