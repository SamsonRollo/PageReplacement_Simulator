package ui;

import controller.PageController;
import javax.swing.JPanel;
import algorithms.Algorithm;

import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;


public class AlgorithmPanel extends JPanel{
    private BufferedImage page;
    private static final String COVER_PATH = "src/page.png";
    private MainClass mainClass;
    public final String PANEL_NAME = "alPanel";
    private Algorithm currentAlgorithm;

    public AlgorithmPanel(Dimension d, MainClass mainClass){
    	this.mainClass = mainClass;
        currentAlgorithm = Algorithm.FIFO;
        setLayout(null);
        setSize(d);
        setPreferredSize(d);
        drawPage();
    }

    private void drawPage(){
        try{
            page = ImageIO.read(this.getClass().getClassLoader().getResource(COVER_PATH));
        }catch(IOException ioe){}
        loadArrows();
        loadPageTools();

    }

    private JPanel loadPageTools(){
        JPanel pageTools = new JPanel(null);
        
        pageTools.setBounds(32,16,385,552); //specific measurement from the src
        pageTools.setOpaque(false);
        pageTools.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.green));

        PRASButton startButton = new PRASButton(46, 405, 125, 34);
        PRASButton stopButton = new PRASButton(46, 454, 125, 34);
        PRASButton saveButton = new PRASButton(213, 405, 125,34);
        PRASButton mainMenuButton = new PRASButton(213, 454, 125, 34);

        startButton.setIcons("src/unselected/unselect_start.png",
                            "src/selected/select_start.png",
                            "START");
	    stopButton.setIcons("src/unselected/unselect_stop.png",
                            "src/selected/select_stop.png",
                            "STOP");
        mainMenuButton.setIcons("src/unselected/unselect_menu.png",
                            "src/selected/select_menu.png",
                            "MENU");
        saveButton.setIcons("src/unselected/unselect_save.png",
                            "src/selected/select_save.png",
                            "SAVE");
                            
        startButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                mainClass.getPageController().startAlgorithm(currentAlgorithm);
            }
        });
        stopButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                mainClass.getPageController().stopAlgorithm();
            }
        });
	    mainMenuButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
                mainClass.openMenu();
			}
		});
        saveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mainClass.getPageController().saveOutput();
			}
		});

        pageTools.add(startButton);
        pageTools.add(stopButton);
        pageTools.add(mainMenuButton);
      	pageTools.add(saveButton);

        return pageTools;   
    }

    private void loadArrows(){
        PRASButton left = new PRASButton();
        PRASButton right = new PRASButton(); 
    }

    public String getPanelName(){
        return PANEL_NAME;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(page, 0, 0, this);
    }
}
