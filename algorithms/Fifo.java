package algorithms;

import model.PageInput;

public class Fifo extends PageReplacementAlgorithm{

    public Fifo(PageInput input){
        setValues(input);
    }

    @Override
    protected void insertFrameAtReference(int currentReference, int currentFrame, String[][] frames){
        int prevRef = currentReference-1;
        int idx = alreadyExistInFrame(currentReference, frames);
        boolean exist = (idx==-1) ? false : true;
      
        if(exist)
        	updateHit(true);
        else
        	updateHit(false);
      
        for(int i=0; i<frameLen; i++){
            if(!exist && i==(currentFrame+1)%frameLen){
                pageFault(currentReference, frames, i);
        	}else{
            	if(exist)
            		setExecutingFrame(idx);
                frames[currentReference][i] = frames[prevRef][i];
            }
        }
    } 
}
