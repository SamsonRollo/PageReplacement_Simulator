package controller;

import model.*;
import algorithms.*;
import exceptions.*;

public class PageController{
    PageInput input;
    
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

    public void startAlgorithm(Algorithm algorithm){
    	PageReplacementAlgorithm pras;

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
        			System.out.println("Current Ref: "+pras.getExecutingRef()+" Val: "+(input.getReferenceValues())[pras.getExecutingRef()]+" at Frame: "+pras.getExecutingFrame()+" Hit: "+pras.isHit());	
        			System.out.println("PageFault: "+pras.getPageFaults());
        			try{
        				Thread.sleep(600); //dynamic based on the slider
        			}catch(Exception e){};			
        		}
        	}
        });
        t.start();   	
    }

    public void stopAlgorithm(){
        //stop algo
    } 

    public void saveOutput(){
        //save the graphical output of pr
	}
    public PageInput getPageInput(){
        return input;
    }
}