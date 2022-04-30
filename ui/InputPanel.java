package ui;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import javax.swing.AbstractButton;
import java.util.Enumeration;

import model.PageInput;

public class InputPanel extends JPanel{
    private PRASRadioButton randomRadio, userRadio, fileRadio;
    private PRASTextField userInputText, userInputFrame, userInputRef;
    private PRASButton loadButton;
    private ButtonGroup butGrp;
	private MainClass mainClass;
    private BufferedImage selInp;
    private PageInput input;
    private AlgorithmPanel ap;
    private boolean isUserSelect = false;
	
    //32, 256, 385, 132
	public InputPanel(int x, int y, int w, int h, MainClass mainClass, AlgorithmPanel ap){
		setLayout(null);
		setOpaque(false);
		setBounds(x,y,w,h);
		this.mainClass = mainClass;
		this.ap = ap;
	}
	
	public void loadInputPanel(){
		ImageLoader imgLoader = new ImageLoader("src/select_input.png", "selInp");
    	selInp = imgLoader.getBuffImage();
    	randomRadio = new PRASRadioButton(16, 39, 100, 27);
    	userRadio = new PRASRadioButton(126, 39, 120, 27);
    	fileRadio = new PRASRadioButton(256, 39, 100, 27);
    	loadButton = new PRASButton(258, 107, 100, 27);
    	userInputText = new PRASTextField(16, 73, 343, 30, "Reference String here: 0-40 (Separate with a comma)");
    	userInputFrame = new PRASTextField(16, 107, 105, 27, "Frame size: 3-10");
    	userInputRef = new PRASTextField(130, 107, 120, 27, "Refence len: 10-40");
  
    	setTextFieldEnable(false);
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
        			}else if(name.equals("USER")){
        				input = mainClass.getPageController().setThruUserInput(userInputRef.getText(), 
        											userInputFrame.getText(),
        											userInputText.getText());
        			}else{
        				System.out.println("file");
        			}
				System.out.println(input.getFrameLength()+" kol "+input.getReferenceLength()+" olop "+input.getReferenceString());
				ap.setButtonOnLoad();
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
    	add(userInputFrame);
    	add(userInputRef);
	
	}

    public void setLoadEnable(boolean stat){
        loadButton.setEnabled(stat);
    }

    public void setTextFieldEnable(boolean stat){
    	userInputText.setEnabled(stat);
    	userInputFrame.setEnabled(stat);
    	userInputRef.setEnabled(stat);
    }
    
    public void setRadioEnable(boolean stat){
    	randomRadio.setEnabled(stat);
    	userRadio.setEnabled(stat);
    	fileRadio.setEnabled(stat);
    }
    
    public PageInput getInputs(){
    	return input;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(selInp, 0, 0, this);
        
        if(userRadio.isSelected() && !isUserSelect){
        	isUserSelect = true;
        	setTextFieldEnable(true);
        }else if(!userRadio.isSelected() && isUserSelect){
            isUserSelect = false;
            setTextFieldEnable(false);
        }
        updateUI(); //test if does not overlap on execution
    }

}