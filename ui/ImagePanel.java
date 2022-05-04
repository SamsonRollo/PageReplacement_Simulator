package ui;

import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class ImagePanel extends JPanel {
    BufferedImage image = null;

    public ImagePanel(String path, String alt){
        ImageLoader il = new ImageLoader(path, alt);    
        image = il.getBuffImage();
	    loadAdditional();
    }
    
    public ImagePanel(String path, String alt, int x, int y){
    	setLocation(x,y);
        ImageLoader il = new ImageLoader(path, alt);    
        image = il.getBuffImage();
	    loadInitials();
    }

    public ImagePanel(BufferedImage image){
        this.image = image;
        loadInitials();
    }
    
    public void updateImage(String path, String alt){
    	ImageLoader il = new ImageLoader(path, alt);    
        image = il.getBuffImage();
    	loadAdditional();
    }
    
    private void loadAdditional(){
    	setSize(image.getWidth(), image.getHeight());
	repaint();
    }
    
    private void loadInitials(){
    	setLayout(null);
        setOpaque(false);
        loadAdditional();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
        g.dispose();
    }

}
