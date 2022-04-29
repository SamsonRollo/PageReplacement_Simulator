package ui;

import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.AbstractButton;
import javax.swing.JTextField;
import java.util.Enumeration;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import algorithms.Algorithm;
import model.PageInput;

public class AlgorithmPanel extends JPanel{
    private static final String PAGE_PATH = "src/page.png";
    private MainClass mainClass;
    public final String PANEL_NAME = "alPanel";
    private Algorithm currentAlgorithm;
    private JPanel aboutPanel;
    private ImageLoader imgLoader;
    private ButtonGroup butGrp;

    public AlgorithmPanel(Dimension d, MainClass mainClass){
    	this.mainClass = mainClass;
        currentAlgorithm = Algorithm.FIFO;
        imgLoader = new ImageLoader();
        setLayout(null);
        setSize(d);
        setPreferredSize(d);
        drawPage();
    }

    private void drawPage(){
        imgLoader.reloadImage(PAGE_PATH, "pager");
        page = imgLoader.getBuffImage();
        imgLoader.reloadImage("src/execution_values.png", "ex");
        valFrame = imgLoader.getBuffImage();
        loadArrows();
        loadInputs();
        add(loadPageTools());
	    add(loadAboutAlgo());   
    }
    
    private void loadInputs(){
    	imgLoader.reloadImage("src/select_input.png", "selInp");
    	selInp = imgLoader.getBuffImage();
    	randomRadio = new PRASRadioButton(58, 322, 100, 27);
    	userRadio = new PRASRadioButton(168, 322, 120, 27);
    	fileRadio = new PRASRadioButton(298, 322, 100, 27);
    	loadButton = new PRASButton(58, 387, 100, 27);
    	userInputText = new JTextField();
    	userInputText.setBounds(58, 353, 335, 30);
    	userInputText.setEnabled(false);
    	randomRadio.setSelected(true);
    	
    	randomRadio.setIcons("src/unselected/unselect_random.png",
                            "src/selected/select_random.png",
                            "RANDOM");
	    userRadio.setIcons("src/unselected/unselect_usrinp.png",
                            "src/selected/select_usrinp.png",
                            "USER");
        fileRadio.setIcons("src/unselected/unselect_file.png",
                            "src/selected/select_file.png",
                            "FILE");
        loadButton.setIcons("src/unselected/unselect_load.png",
                            "src/selected/select_load.png",
                            "LOAD");
        
        loadButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String name = "RANDOM";
				for (Enumeration<AbstractButton> buttons = butGrp.getElements(); buttons.hasMoreElements();) {
         	   			AbstractButton button = buttons.nextElement();
            			if (button.isSelected()) {
                			name = button.getName();
                			break;
            			}
        		}
                if(name.equals("RANDOM")){
                    input = mainClass.getPageController().setThruRandom();
                    System.out.println(input.getFrameLength()+" kol "+input.getReferenceLength()+" olop "+input.getReferenceString());
                }else if(name.equals("USER")){
                    System.out.println("User");
                }else{
                    System.out.println("file");
                }
                setButtonOnLoad();
			}
	    });
                            
        butGrp = new ButtonGroup();
        butGrp.add(randomRadio);
        butGrp.add(userRadio);
        butGrp.add(fileRadio);
    	add(randomRadio);
    	add(userRadio);
    	add(fileRadio);
    	add(loadButton);
    	add(userInputText);
    }

    private JPanel loadAboutAlgo(){
        imgLoader.reloadImage("src/"+currentAlgorithm.name()+"_about.png", currentAlgorithm.name());
    	currAboutImg = imgLoader.getBuffImage();
    	aboutPanel = new JPanel(null){
    		@Override
    		public void paintComponent(Graphics g){
        		g.drawImage(currAboutImg, 0, 0, this);
    		}
    	};
    	aboutPanel.setBounds(32,16,385,290);
    	return aboutPanel;
    }
    
    public void updateAboutAlgo(Algorithm currentAlgorithm){
    	this.currentAlgorithm = currentAlgorithm;
        imgLoader.reloadImage("src/"+currentAlgorithm.name()+"_about.png", currentAlgorithm.name());
    	currAboutImg = imgLoader.getBuffImage();
    	aboutPanel.repaint();
    }

    private JPanel loadPageTools(){
        JPanel pageTools = new JPanel(null);
        
        pageTools.setBounds(32,16,385,552); //specific measurement from the src
        pageTools.setOpaque(false);
        pageTools.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.green));

        startButton = new PRASButton(46, 415, 125, 34);
        stopButton = new PRASButton(46, 464, 125, 34);
        saveButton = new PRASButton(213, 415, 125,34);
        mainMenuButton = new PRASButton(213, 464, 125, 34);

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
				mainClass.getPageController().stopAlgorithm();
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
            	currentAlgorithm = currentAlgorithm.previous();
            	repaintAlgorithms();
            }
        });
        right.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	currentAlgorithm = currentAlgorithm.next();
            	repaintAlgorithms();
            }
        });
        
        add(left);
        add(right);
    }
    
    private JPanel loadVisualPanel(){
        VisualPanel visualPanel = new VisualPanel(mainClass, 421, 16, 395, 552);//
        return visualPanel;
    }
    
    public String getPanelName(){
        return PANEL_NAME;
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
    	randomRadio.setEnabled(!stat);
    	userRadio.setEnabled(!stat);
    	fileRadio.setEnabled(!stat);
    	loadButton.setEnabled(!stat);
    	userInputText.setEnabled(!stat);
    	//speed enabled
    }
    
    public void setButtonAfterExec(boolean stat){ //called after exec
    	saveButton.setEnabled(stat);	
    }
    
    public void repaintAlgorithms(){
    	aboutPanel.repaint();
    	//visualPanel.repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(page, 0, 0, this);
        g.drawImage(valFrame, 421,16 ,this);
        g.drawImage(selInp, 40, 286, this);
        
        if(userRadio.isSelected()){
            userInputText.setEnabled(true);
        }else{
            userInputText.setEnabled(false);
        }
        updateUI(); //test if does not overlap on execution
    }
    
    private PRASRadioButton randomRadio, userRadio, fileRadio;
    private PRASButton startButton, stopButton, saveButton, mainMenuButton, loadButton, left, right;
    private BufferedImage page, currAboutImg, valFrame, selInp;
    private boolean doneExecute = false;
    private JTextField userInputText;
    private PageInput input;
}