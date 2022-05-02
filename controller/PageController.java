package controller;

import model.*;
import ui.AlgorithmPanel;
import algorithms.*;
import exceptions.*;

public class PageController{
    private PageInput input;
    private PageReplacementAlgorithm pras;
    
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
        Thread t = new Thread(new Runnable(){
        	public void run(){
        		while(true){
        			if((pras.getCurrentRefernce())==input.getReferenceLength())
        				break;
        			pras.move();
        			try{
        				System.out.println(panel.getSpeed());
        				Thread.sleep(panel.getSpeed());
        			}catch(Exception e){};			
        		}
        	}
        });
        t.start();   	
    }

    public void stopAlgorithm(){
        //
    } 

    public void saveOutput(){
        //save the graphical output of pr
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

    public void setPageInput(PageInput input){
        this.input = input;
    }

    public void setPRAStoNull(){
        this.pras = null;
    }
}
