package algorithms;

import model.PageInput;

public class Fifo extends PageReplacementAlgorithm{

    //indicate on hit or miss?????
    public Fifo(PageInput input){
        setValues(input);
    }

    @Override
    protected void insertFrameAtReference(int currentReference, int currentFrame, String[][] frames){
        int prevRef = currentReference-1;
        boolean exist = alreadyExistInFrame(currentReference, frames);
        for(int i=0; i<frameLen; i++){
            if(!exist && i==(currentFrame+1)%frameLen){
                pageFault(currentReference, frames, i);
            }else{
                frames[currentReference][i] = frames[prevRef][i];
            }
        }

        if(exist){
            updateHit(true);
        }else{
            updateHit(false);
        }
    } 
}
