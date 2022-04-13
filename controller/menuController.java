package controller;

public class MenuController{

    public PageInput setThruRandom()){ //create randomInput obejct
        PageInput input = new PageInput();
        input.generateRandomInput();
        return input;
    }

    public PageInput setThruUserInput(int refLen, int frameLen, String refValues){
        PageInput input = new PageInput();
        try{
            input.setValues(refLen, frameLen, refValues);
            return input;
        }catch(InvalidInputException iie){
            new ErrorReport(iie.getMessage(), "Invalid Inputs");
        }
    }

    public PageInput setThruFileInput(java.io.File file){
        PageInput input = new PageInput();
        try{
            input.getFromFileInput(file);
            return input;
        }catch(InvalidInputException iie){
            new ErrorReport(iie.getMessage(), "Invalid Inputs");
        }
    }

    public void runPageReplacement(){
        //use thread
    }

    public void saveOutput(){
        //save the graphical output of pr
    }
}