package ui;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class MainMenuPanel extends JPanel{
    private static final String COVER_PATH = "src/cover.png";
    private MainClass mainClass;
    public final String PANEL_NAME = "mmPanel";

    public MainMenuPanel(Dimension d, MainClass mainClass){
    	this.mainClass = mainClass;
        setLayout(null);
        setSize(d);
        setPreferredSize(d);
        drawMenu();
    }

    private void drawMenu(){
    	ImagePanel ip = new ImagePanel(COVER_PATH, "cover");
    	loadMenuTools();
    	add(ip);
    }

    private void loadMenuTools(){
        PRASButton openButton = new PRASButton(220, 280, 166, 45);
        PRASButton aboutButton = new PRASButton(220, 335, 166, 45);
        PRASButton exitButton = new PRASButton(220, 390, 166, 45);

        openButton.setIcons("src/unselected/unselect_open.png",
                            "src/selected/select_open.png",
                            "OPEN");
        aboutButton.setIcons("src/unselected/unselect_about.png",
                            "src/selected/select_about.png",
                            "ABOUT");
        exitButton.setIcons("src/unselected/unselect_exit.png",
                            "src/selected/select_exit.png",
                            "EXIT");
 
        openButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    mainClass.openPage();
                }
            });
        aboutButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    //add pop up for this about the app
                }
            });
        exitButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    System.exit(0);
                }
            });
            	
        add(openButton);
        add(aboutButton);
        add(exitButton);
    }
    
    public String getPanelName(){
        return PANEL_NAME;
    }
}
