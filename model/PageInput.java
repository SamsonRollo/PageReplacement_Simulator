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
    public int[] refValArr;

    public PageInput(){}

    public void generateRandomInput(){
        frameLen = (int)Math.random()*(MAX_FRAME_LEN - MIN_FRAME_LEN + 1) + MIN_FRAME_LEN;
        refLen = (int)Math.random()*(MAX_REF_LEN - MIN_REF_LEN +1) + MIN_REF_LEN;
        refValArr = new int[refLen];
        generateRefValues();
    }

    public void setValues(String refLenStr, String frameLenStr, String refValues) throws InvalidInputException{
        int refLen, frameLen;
        try{
            refLen = Integer.parseInt(refLenStr);
            frameLen = Integer.parseInt(frameLenStr);
        }catch(NumberFormatException nfx){
            throw new InvalidInputException("Invalid Frame length or reference length.");
        }
        if(refLen>MAX_REF_LEN || refLen<MIN_REF_LEN)
            throw new InvalidInputException("Reference Length out of range! Range: "+MIN_REF_LEN+"-"+MAX_REF_LEN);
        if(frameLen>MAX_FRAME_LEN || frameLen<MIN_FRAME_LEN)
            throw new InvalidInputException("Frame Length out of range! Range: "+MIN_FRAME_LEN+"-"+MAX_FRAME_LEN);
        //set the lengths
        this.refLen = refLen;
        this.frameLen = frameLen;

        String[] tempRefVal = refValues.replaceAll(" ","").split(",");
        if(tempRefVal.length != refLen)
            throw new InvalidInputException("Number of Reference values not equal to Refence length.");
        refValArr = new int[refLen];
        for(int i = 0; i<refLen; i++){
            try{
                refValArr[i] = Integer.parseInt(tempRefVal[i]);
            }catch(NumberFormatException ex){
                throw new InvalidInputException("Some Reference values are invalid.");
            }
        }
    }

    public void getFromFileInput(File file) throws Exception{
        String refString = "";
        String frameLen = "";
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while((line = reader.readLine())!= null){
                line.toLowerCase().replaceAll(" ", "");
                if(line.startsWith("referencestring:"))
                    refString = line.split(":")[1]; //unsafe
                if(line.startsWith("framesize:")){
                    try{
                        frameLen = line.split(":")[1]; //unsafe
                    }catch(NumberFormatException nex){
                        throw new InvalidInputException("Frame length not a number!");
                    }
                }
            }
            reader.close();
            setValues(refLenFunc(refString), frameLen, refString);
        }catch(InvalidInputException iie){
            throw new InvalidInputException("Cannot access input file!");
        }
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

    public int[] getReferenceValues(){
        return refValArr;
    }
    
    private String refLenFunc(String refString){
        return String.valueOf(refString.split(",").length);
    }

    private void generateRefValues(){
        for(int i=0; i<refLen; i++){
            refValArr[i] = (int)Math.random()*(MAX_REF_VAL - MIN_REF_VAL + 1) + MIN_REF_VAL;
        }
    }
}