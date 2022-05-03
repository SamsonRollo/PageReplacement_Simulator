package ui;

import javax.swing.ImageIcon;
import javax.swing.JRadioButton;

public class PRASRadioButton extends JRadioButton{

    public PRASRadioButton(int x, int y, int w, int h){
        setBounds(x,y,w,h); 
    }

    public void setIcons(String selectAddress, String unselectAddress, String alt){
        ImageLoader imgLoader = new ImageLoader(selectAddress, alt);
        setIcon(new ImageIcon(imgLoader.getBuffImage()));
        imgLoader.reloadImage(unselectAddress, alt);
        setSelectedIcon(new ImageIcon(imgLoader.getBuffImage()));
        
        setName(alt);

        if(imgLoader.isNull())
            setText(alt);
    }

    public JRadioButton getButton(){
        return this;
    }
}
