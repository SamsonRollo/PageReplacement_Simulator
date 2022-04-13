package controller;

import model.*;
import exceptions.*;

public class MenuController{

    public PageInput setThruRandom(){ //create randomInput obejct
        PageInput input = new PageInput();
        input.generateRandomInput();
        return input;
    }

    public PageInput setThruUserInput(String refLen, String frameLen, String refValues){
        PageInput input = new PageInput();
        try{
            input.setValues(refLen, frameLen, refValues);
        }catch(InvalidInputException iie){
            new ErrorReport(iie.getMessage(), "Invalid Inputs");
        }
        return input;
    }

    public PageInput setThruFileInput(java.io.File file) throws Exception{
        PageInput input = new PageInput();
        try{
            input.getFromFileInput(file);
        }catch(InvalidInputException iie){
            new ErrorReport(iie.getMessage(), "Invalid Inputs");
        }
        return input;
    }

    public void runPageReplacement(){
        //use thread
    }

    public void saveOutput(){
        //save the graphical output of pr
    }
}