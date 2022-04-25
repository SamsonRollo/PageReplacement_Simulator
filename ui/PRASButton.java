package ui;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.io.IOException;

public class PRASButton extends JButton{
 
    public PRASButton(){
        setBorders();
    }

    public PRASButton(int x, int y, int w, int h){
        setBorders();
        setBounds(x,y,w,h); 
    }

    public void setBorders(){
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

    public void setIcons(String selectAddress, String unselectAddress, String alt){
        try{
		    setIcon(new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResource(unselectAddress))));
		    setRolloverIcon(new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResource(selectAddress))));
		}catch(IOException e){
		    setText(alt);
		};
    }

    public JButton getButton(){
        return this;
    }
}