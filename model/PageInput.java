package model;

import java.lang.Math;
import java.util.Arrays;
import java.io.*;
import exceptions.*;

public class PageInput{
    private static final int MAX_FRAME_LEN = 10;
    private static final int MIN_FRAME_LEN = 3;
    private static final int MAX_REF_LEN = 40;
    private static final int MIN_REF_LEN = 10;
    private static final int MAX_REF_VAL = 20;
    private static final int MIN_REF_VAL = 0;

    public int frameLen = 0;
    public int refLen = 0;
    public String[] refValArr;
    private boolean hasError = false;

    public PageInput(){}

    public void generateRandomInput(){
        frameLen = (int)Math.floor(Math.random()*(MAX_FRAME_LEN - MIN_FRAME_LEN + 1) + MIN_FRAME_LEN);
        refLen = (int)Math.floor(Math.random()*(MAX_REF_LEN - MIN_REF_LEN +1) + MIN_REF_LEN);
        refValArr = new String[refLen];
        generateRefValues();
    }

    public void setValues(String refLenStr, String frameLenStr, String refValues) throws InvalidInputException{
        int refLen, frameLen;
        hasError = false;
        try{
            refLen = Integer.parseInt(refLenStr);
            frameLen = Integer.parseInt(frameLenStr);
        }catch(NumberFormatException nfx){
        	hasError = true;
            throw new InvalidInputException("Invalid Frame length or reference length.");
        }
        if(refLen>MAX_REF_LEN || refLen<MIN_REF_LEN){
        	hasError = true;
            throw new InvalidInputException("Reference Length out of range! Range: "+MIN_REF_LEN+"-"+MAX_REF_LEN);
        }
        if(frameLen>MAX_FRAME_LEN || frameLen<MIN_FRAME_LEN){
        	hasError = true;
            throw new InvalidInputException("Frame Length out of range! Range: "+MIN_FRAME_LEN+"-"+MAX_FRAME_LEN);
        }
    
        String[] tempRefVal = refValues.replaceAll(" ","").split(",|-");
        if(tempRefVal.length != refLen){
        	hasError = true;
            throw new InvalidInputException("Number of Reference values not equal to Refence length.");
        }
        refValArr = new String[refLen];
        for(int i = 0; i<refLen; i++){
            try{
                Integer.parseInt(tempRefVal[i]);
                refValArr[i] = tempRefVal[i];
            }catch(NumberFormatException ex){
            	hasError = true;
                refValArr = null;
                throw new InvalidInputException("Some Reference values are invalid.");
            }
        }
        //set the lengths
        this.refLen = refLen;
        this.frameLen = frameLen;
    }

    public void getFromFileInput(File file) throws Exception{
        String refString = "";
        String frameLen = "";
        hasError = false;
        
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while((line = reader.readLine())!= null){
                line = line.toLowerCase().replaceAll(" ", "");

                if(line.startsWith("referencestring:"))
                    refString = line.split(":")[1]; //unsafe
                if(line.startsWith("framesize:")){
                    try{
                        frameLen = line.split(":")[1]; //unsafe
                    }catch(NumberFormatException nex){
                    	hasError = true;
                        throw new InvalidInputException("Frame length not a number!");
                    }
                }
            }
            reader.close();
            setValues(refLenFunc(refString), frameLen, refString);
        }catch(Exception iie){
        	hasError = true;
            throw new InvalidInputException("Cannot access input file!"+"\n"+iie.getMessage());
        }
    }
    
    public boolean hasError(){
    	return this.hasError;
    }
    
    public void hasError(boolean stat){
    	this.hasError = stat;
    }

    public int getReferenceLength(){
        return refLen;
    }
    
    public int getFrameLength(){
        return frameLen;
    }

    public String getReferenceString(){
        String values = Arrays.toString(refValArr);
        return values.substring(1, values.length()-1);
    }

    public String[] getReferenceValues(){
        return refValArr;
    }
    
    private String refLenFunc(String refString){
        return String.valueOf(refString.split(",|-").length);
    }

    private void generateRefValues(){
        for(int i=0; i<refLen; i++){
            refValArr[i] = String.valueOf((int)Math.floor(Math.random()*(MAX_REF_VAL - MIN_REF_VAL + 1) + MIN_REF_VAL));
        }
    }
}
