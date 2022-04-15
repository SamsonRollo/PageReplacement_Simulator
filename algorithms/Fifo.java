package algorithms;

import model.PageInput;

public class Fifo extends PageReplacementAlgorithm{

    public Fifo(PageInput input){
        setValues(input);
    }

    @Override
    protected void insertFrameAtReference(int currentReference, int currentFrame, String[][] frames){
        int prevRef = currentReference-1;
        boolean exist = alreadyExistInFrame(currentReference, frames);
        for(int i=0; i<frameLen; i++){
            if(!exist && i==currentFrame){
                frames[currentReference][i] = String.valueOf(values[currentReference]);
                this.currentFrame = (this.currentFrame+1)%frameLen;
                this.pageFaults++;
            }else{
                frames[currentReference][i] = frames[prevRef][i];
            }
        }
    } 
}
