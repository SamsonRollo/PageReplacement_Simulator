package controller;

import model.*;
import ui.AlgorithmPanel;
import algorithms.*;
import exceptions.*;

public class PageController{
    private PageInput input;
    private PageReplacementAlgorithm pras;
    private Thread prasThread;
    
    public PageController(){
    	input = new PageInput();
    }

    public PageInput setThruRandom(){ //create randomInput obejct
        input = new PageInput();
        input.generateRandomInput();
        return input;
    }

    public PageInput setThruUserInput(String refLen, String frameLen, String refValues){
        input = new PageInput();
        try{
            input.setValues(refLen, frameLen, refValues);
        }catch(InvalidInputException iie){
            new ErrorReport(iie.getMessage(), "Invalid Inputs");
        }
        return input;
    }

    public PageInput setThruFileInput(java.io.File file) throws Exception{
        input = new PageInput();
        try{
            input.getFromFileInput(file);
        }catch(InvalidInputException iie){
            new ErrorReport(iie.getMessage(), "Invalid Inputs");
        }
        return input;
    }

    public void startAlgorithm(Algorithm algorithm, AlgorithmPanel panel){   	
    	if(algorithm.equals(Algorithm.FIFO)){
    		pras = new Fifo(input);
    	}else if(algorithm.equals(Algorithm.LRU)){
    		pras = new LRU(input);
    	}else if(algorithm.equals(Algorithm.OPT)){
    		pras = new OPT(input);
    	}else{ // if(algorithm.equals(Algorithm.CLOCK))
    		pras = new Clock(input);
    	}
        prasThread = new Thread(new Runnable(){
        	public void run(){
        		while(true){
        			if((pras.getCurrentRefernce())==input.getReferenceLength())
        				break;
        			pras.move();
                    panel.getVisualPanel().getOutputPanel().revalidate();
                    panel.getVisualPanel().getOutputPanel().repaint();
        			try{
        				Thread.sleep(panel.getSpeed());
        			}catch(Exception e){};			
                    if(pras.getCurrentRefernce()==input.getReferenceLength()){
                        panel.setDoneExec(true);
                        break;
                    }
        		}
        	}
        });
        prasThread.start();   	
    }


    @SuppressWarnings("deprecation")
    public boolean stopAlgorithm(){
        try{
            getPRASThread().stop();
        }catch(Exception e){
            return false;
        }
        return true;
    } 

    public boolean saveOutput(javax.swing.JPanel panel, String extension){
        ImageSaver is = new ImageSaver(panel);
        if(extension.equals("PNG"))
            is.saveAsImage();
        else
            is.saveAsPDF();
        if(is.getHasError())
            return false;
        return true;
	}
    public PageInput getPageInput(){
        return input;
    }

    public int getNumberOfFrames(){
        return input.getFrameLength();
    }
    
    public String[] getPageInputValues(){
    	return input.getReferenceValues();
    }
    
    public int getExecCurrentReference(){
    	try{
    		return pras.getExecutingRef();
    	}catch(Exception e){
    		return 0;
    	}
    }
    
    public int getExecCurrentFrame(){
    	try{
    		return pras.getExecutingFrame();
    	}catch(Exception e){
    		return 0;
    	}
    }
    
    public int getExecCurrentPageFault(){
    	try{
    		return pras.getPageFaults();
    	}catch(Exception e){
    		return 0;
    	}
    }
    
    public int getExecCurrentHitCount(){
    	try{
    		return (pras.getCurrentRefernce()-pras.getPageFaults());
    	}catch(Exception e){
    		return 0;
    	}
    }
    
    public int getExecCurrentMissCount(){
    	try{
    		return pras.getPageFaults();
    	}catch(Exception e){
    		return 0;
    	}
    }
    
    public boolean getExecCurrentIsHit(){
    	try{
    		return pras.isHit();
    	}catch(Exception e){
    		return false;
    	}
    }

    public String[][] getPRASFrames(){
        try{
            return pras.getFrames();
        }catch(Exception e){
            return null;
        }
    }

    public boolean[] getPRASBolArr(){
        try{
            return pras.getHitArray();
        }catch(Exception e){
            return null;
        }
    }

    public Thread getPRASThread(){
        return this.prasThread;
    }

    public void setPageInput(PageInput input){
        this.input = input;
    }

    public void setPRAStoNull(){
        this.pras = null;
    }
}
