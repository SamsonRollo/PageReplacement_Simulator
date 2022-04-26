package ui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Dimension;

public class VisualPanel extends JPanel{
    public JPanel outputPanel;
    public JPanel valuesPanel;
    private BufferedImage execImg;

    public VisualPanel(int x, int y, int w, int h){
    	setLayout(null);
        setBounds(x, y, w, h);
        setOpaque(false);
        add(loadValuePanel());
        add(loadFramePanel());
    }

    public JPanel loadValuePanel(){
        execImg = loadImage("src/execution_values.png");
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
                    //select a font
                    //select font size
                   //g.drawString("REFERENCE STRING", 0,0 );
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

    private BufferedImage loadImage(String source){
        BufferedImage img = null;
        try{
            img = ImageIO.read(this.getClass().getClassLoader().getResource(source));
        }catch(IOException ioe){}
        return img;
    }
}