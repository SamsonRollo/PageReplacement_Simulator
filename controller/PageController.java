package controller;

import model.*;
import algorithms.Algorithm;
import exceptions.*;

public class PageController{
    PageInput input;

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
        //use thread
    }

    public void stopAlgorithm(){
        //
    } 

    public void saveOutput(){
        //save the graphical output of pr
    }

    public void clickedRight(){

    }

    public void clickedLeft(){

    }

    public PageInput getPageInput(){
        return input;
    }
}