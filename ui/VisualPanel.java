package ui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Font;

public class VisualPanel extends JPanel{
    private MainClass mainClass;
    public  JPanel outputPanel, valuesPanel;
    private BufferedImage execImg;
    private Font font;
    private ImageLoader imgLoader;

    public VisualPanel(MainClass mainClass, int x, int y, int w, int h){
        imgLoader = new ImageLoader();
    	this.mainClass = mainClass;
    	setLayout(null);
        setBounds(x, y, w, h);
        setOpaque(false);
        font  = new Font("sans_serif", Font.BOLD, 17);
        add(loadValuePanel());
        add(loadFramePanel());
    }

    public JPanel loadValuePanel(){
        imgLoader.reloadImage("src/execution_values.png", "ex");
        execImg = imgLoader.getBuffImage();
        //panel to be saved, it contains the frames and and everything.
       //add outputPanel to a JScrollPane to expand the coverage if we have a longer reference string
        // outputPanel must append the reference string, number of frames, number of page fault, number of hits, number of misses if the user chose to save the output
        //add colors to frames to make it more amazing.
        // do not update the above values until the algorithm is done, only update the executionPanel where the same elements are present

        valuesPanel = new JPanel(null){
                @Override
                public void paintComponent(Graphics g){
                   super.paintComponent(g);
                   g.drawImage(execImg, 0, 0, this);
                   g.setFont(font);
                   g.drawString("20", 0,0 );
                   updateUI();
                }   
        };
        valuesPanel.setOpaque(false);
        valuesPanel.setBounds(0,0,395,140);
    	return valuesPanel;    
    }
    
    public JScrollPane loadFramePanel(){
    	outputPanel = new JPanel(null){
    		@Override
    		public void paintComponent(Graphics g){
    			super.paintComponent(g);
    			
    			setPreferredSize(new Dimension(800, 500)); //recalculate based on the farthest frame
    			updateUI();
    		}
    	
    	};
    		
	outputPanel.setBackground(java.awt.Color.white);

	JScrollPane jspDraw = new JScrollPane(outputPanel);
	jspDraw.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.cyan));
	jspDraw.setBounds(0,140,395, 400); 
    	return jspDraw;
    }
}