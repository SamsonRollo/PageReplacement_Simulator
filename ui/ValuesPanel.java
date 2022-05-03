package ui;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;

import controller.PageController;;

public class ValuesPanel extends JPanel{
	private PageController controller;
	private Font font;
	
	public ValuesPanel(int x, int y, int w, int h, PageController controller, Font font){
		this.controller = controller;
		this.font = font;
		setLayout(null);
		setOpaque(false);
		setBounds(x,y,w,h);
	}
	
	@Override
	public void paintComponent(Graphics g){
               super.paintComponent(g);
                   g.setFont(font);
                   g.setColor(Color.black);
                   
                   String[] values = controller.getPageInputValues(); //
                   int curRef = controller.getExecCurrentReference();//
                   int x = 5, y=42;
                   String val;
                if(values !=null){   
                	
                   	for(int i=0; i<values.length; i++){
                   		val = values[i]; 
                   		if(i>0){
                   			g.drawString("-",x,y);
                   			x+=8;
                   		}
                   		if(x>=380 || ((g.getFontMetrics().stringWidth(val))+2+x)>=380){
                   			y+=18;
                   			x=5;
                   		}
		                 
		   				if(i==curRef){
		   					g.setColor(Color.red);
		   					g.drawString(val, x, y);
		   					g.setColor(Color.black);
		   				}else{
		   					g.drawString(val, x, y);
		   				}		                	
		               	x+= (g.getFontMetrics().stringWidth(val))+2;
               	    }
               }
              	g.drawString(String.valueOf(controller.getNumberOfFrames()), 141, 122);//number of frames
             	g.drawString(String.valueOf(controller.getExecCurrentHitCount()), 141, 152); //hits
             	g.drawString(String.valueOf(controller.getExecCurrentPageFault()), 351, 122); //pagefault
             	g.drawString(String.valueOf(controller.getExecCurrentMissCount()), 351, 152); //miss	
        }
}
