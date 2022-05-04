package ui;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JLabel;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Hashtable;
import javax.swing.JOptionPane;

import algorithms.Algorithm;

public class AlgorithmPanel extends JPanel{
    private static final String PAGE_PATH = "src/page.png";
    public final String PANEL_NAME = "alPanel";
    private Algorithm currentAlgorithm = Algorithm.FIFO;
    private BufferedImage page = null;
    private MainClass mainClass;
    private ImagePanel aboutPanel;
    private InputPanel inputPanel;
    private VisualPanel visualPanel;

    public AlgorithmPanel(Dimension d, MainClass mainClass){
    	this.mainClass = mainClass;
    	drawPage();
        setLayout(null);
        setSize(d);
        setPreferredSize(d);
    }

    private void drawPage(){
    	ImageLoader il = new ImageLoader(PAGE_PATH, "pager");
    	page = il.getBuffImage();
    	loadArrows();
       	add(loadPageTools());
    	add(loadAboutAlgo());
    	add(loadInputs());  
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

	@SuppressWarnings("deprecation")
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
        
        Hashtable<Integer, JLabel> labelTable = 
      	new Hashtable<Integer, JLabel>();
      	labelTable.put(new Integer( 1 ),
      	new JLabel("Slow") );
      	labelTable.put(new Integer( 100 ),
      	new JLabel("Fast") );
      	slider.setLabelTable(labelTable);
      	slider.setPaintLabels(true); 

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
                visualPanel.setIsExecuting(true);
				mainClass.getPageController().startAlgorithm(currentAlgorithm, getAlgoPanel());
				setButtonsOnExec(true);
			}
		});
	    stopButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(mainClass.getPageController().stopAlgorithm()){
                    visualPanel.setIsExecuting(false);
				    setButtonsOnExec(false);
                }
			}
		});
	    mainMenuButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
                		mainClass.openMenu();
			}
		});
        saveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
                Object[] choices = {"PNG", "PDF", "CANCEL"};
                Object selected = JOptionPane.showOptionDialog(mainClass, "Select format to save.", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, choices, choices[0]);
            
                if((int)selected!=2){
                    String option = "PNG";
                    if((int)selected == 1)
                        option = "PDF"; 
                    visualPanel.setIsSaving(true);  
				    boolean ok = mainClass.getPageController().saveOutput(getVisualPanel().getOutputPanel(), option);	
			         visualPanel.setIsSaving(false);
                    if(ok)
                        JOptionPane.showMessageDialog(mainClass, "Saved successfully.", "Save", JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(mainClass, "Cannot save at the moment.", "Save", JOptionPane.WARNING_MESSAGE);
                }
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
                mainClass.getPageController().setPRAStoNull();
                setButtonAfterExec(false);
            	repaintAlgorithms();
            }
        });
        right.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	updateAboutAlgo(currentAlgorithm.next());
                mainClass.getPageController().setPRAStoNull();
                setButtonAfterExec(false);
            	repaintAlgorithms();
            }
        });
        
        add(left);
        add(right);
    }
    
   private JPanel loadVisualPanel(){
    	visualPanel = new VisualPanel(mainClass.getPageController(), 421, 16, 395, 552, font);
    	return visualPanel;
    }
    
    public String getPanelName(){
        return PANEL_NAME;
    }
    
    public void setLabelsOnScreen(){
    	add(loadVisualPanel());
    }
    
    public void setButtonNewState(){ //new state
    	startButton.setEnabled(false);
    	stopButton.setEnabled(false);
    	saveButton.setEnabled(false);
    	slider.setEnabled(false);
    }
    
    public void setButtonOnLoad(){ //loaded the input
        saveButton.setEnabled(false);
    	startButton.setEnabled(true);
    	slider.setEnabled(true);
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
    	slider.setEnabled(true);
    }
    
    public void setButtonAfterExec(boolean stat){ //called after exec
    	saveButton.setEnabled(stat);	
    }
    
    public void repaintAlgorithms(){
    	repaint();
    }
    
    public int getSpeed(){
    	return Math.abs(slider.getValue()-100)*10;
    }
    
    public AlgorithmPanel getAlgoPanel(){
    	return this;
    }

    public VisualPanel getVisualPanel(){
        return this.visualPanel;
    }
    
    public Font getFont(){
    	return font;
    }

    public void setDoneExec(boolean stat){
        this.doneExec = stat;
    }
    
    @Override
    public void paintComponent(Graphics g){
    	super.paintComponent(g);
    	g.drawImage(page,0,0,null);
        if(doneExec){
            visualPanel.setIsExecuting(false);
            setButtonsOnExec(false);
            setButtonAfterExec(true);
            doneExec = false;
    	}
        updateUI();
        
    }
    
    private PRASButton startButton, stopButton, saveButton, mainMenuButton, left, right;
    private JSlider slider;
    private boolean doneExec = false;
    private Font font = new Font("sans_serif", Font.BOLD, 15);
}
