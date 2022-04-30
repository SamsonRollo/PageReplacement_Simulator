package ui;

import javax.swing.FocusManager;
import javax.swing.JTextField;
import java.awt.Graphics;
import java.awt.Color;

public class PRASTextField extends JTextField{
    private String text;
    
    public PRASTextField(int x, int y, int w, int h, String text){
        setBounds(x,y,w,h);
        this.text = text;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(getText().isEmpty() && ! (FocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == this)){
            g.setColor(Color.gray);
            g.drawString(text, 1, 18);
            g.dispose();
        }
    }
}