package ui;

import javax.swing.JPanel;
import javax.swing.JSlider;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import algorithms.Algorithm;
import model.PageInput;

public class AlgorithmPanel extends JPanel{
    private static final String PAGE_PATH = "src/page.png";
    public final String PANEL_NAME = "alPanel";
    private Algorithm currentAlgorithm = Algorithm.FIFO;
    private MainClass mainClass;
    private ImagePanel aboutPanel;
    private InputPanel inputPanel;

    public AlgorithmPanel(Dimension d, MainClass mainClass){
    	this.mainClass = mainClass;
    	drawPage();
        setLayout(null);
        setSize(d);
        setPreferredSize(d);
    }

    private void drawPage(){
	loadArrows();
    add(loadPageTools());
	add(loadAboutAlgo());
	add(loadInputs());  
	//add(loadVisualPanel()); invoke after sucessful load
	add(new ImagePanel(PAGE_PATH, "pager"));
    }
    
    private JPanel loadInputs(){
    	inputPanel = new InputPanel(32,265,385,143, mainClass, this);
    	inputPanel.loadInputPanel();
    	return inputPanel;
    }

    private JPanel loadAboutAlgo(){
    	aboutPanel = new ImagePanel("src/"+currentAlgorithm.name()+"_about.png", currentAlgorithm.name(),32,16);
    	return aboutPanel;
    }
    
    public void updateAboutAlgo(Algorithm currentAlgorithm){
    	this.currentAlgorithm = currentAlgorithm;
    	aboutPanel.updateImage("src/"+currentAlgorithm.name()+"_about.png", currentAlgorithm.name());
    }

    private JPanel loadPageTools(){
        JPanel pageTools = new JPanel(null);
        
        pageTools.setBounds(32, 416,385,152);
        pageTools.setOpaque(false);

        startButton = new PRASButton(46, 7, 125, 34);
        stopButton = new PRASButton(46, 51, 125, 34);
        saveButton = new PRASButton(213, 7, 125,34);
        mainMenuButton = new PRASButton(213, 51, 125, 34);
        slider = new JSlider(1,100,50);
        slider.setBounds(46, 115, 300, 34);

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
				setButtonsOnExec(true);
			}
		});
	    stopButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//mainClass.getPageController().stopAlgorithm();
				setButtonsOnExec(false);
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
		
	    setButtonNewState();

        pageTools.add(startButton);
        pageTools.add(stopButton);
        pageTools.add(mainMenuButton);
      	pageTools.add(saveButton);
      	pageTools.add(slider);

        return pageTools;
        
    }
    
    private void loadArrows(){
        left = new PRASButton(2, 0, 45, 45); //273 =y
        right = new PRASButton(803, 0, 45, 45);
        
        left.setIcons("src/unselected/unselect_left.png",
                    "src/selected/select_left.png",
                    "LEFT");
        right.setIcons("src/unselected/unselect_right.png",
                    "src/selected/select_right.png",
                    "RIGHT");
        left.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	updateAboutAlgo(currentAlgorithm.previous());
            	repaintAlgorithms();
            }
        });
        right.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	updateAboutAlgo(currentAlgorithm.next());
            	repaintAlgorithms();
            }
        });
        
        add(left);
        add(right);
    }
    
   // private JPanel loadVisualPanel(){
    //	ValuesPanel valPanel = new ValuesPanel(0,0,395,140, mainClass, font);
        //VisualPanel visualPanel = new VisualPanel(mainClass, 421, 16, 395, 552, font);//
        //return visualPanel;
      //  return valPanel;
    //}
    
    public String getPanelName(){
        return PANEL_NAME;
    }
     
    public void setPageInput(PageInput input){
    	this.input = input;
    }
    
    public void setButtonNewState(){ //new state
    	startButton.setEnabled(false);
    	stopButton.setEnabled(false);
    	saveButton.setEnabled(false);
    	//speed disabled
    }
    
    public void setButtonOnLoad(){ //loaded the input
    	startButton.setEnabled(true);
    	//speed enabled
    }
    
    public void setButtonsOnExec(boolean stat){ //on start, false if not exec
    	startButton.setEnabled(!stat);
    	stopButton.setEnabled(stat);
    	mainMenuButton.setEnabled(!stat);
    	left.setEnabled(!stat);
    	right.setEnabled(!stat);
    	inputPanel.setRadioEnable(!stat);
    	inputPanel.setLoadEnable(!stat);
    	inputPanel.setTextFieldEnable(!stat);
    	//speed enabled
    }
    
    public void setButtonAfterExec(boolean stat){ //called after exec
    	saveButton.setEnabled(stat);	
    }
    
    public void repaintAlgorithms(){
    	repaint();
    }
    
    public Font getFont(){
    	return font;
    }
    
    private PRASButton startButton, stopButton, saveButton, mainMenuButton, left, right;
    private JSlider slider;
    private PageInput input;
    private Font font = new Font("sans_serif", Font.BOLD, 17);
}