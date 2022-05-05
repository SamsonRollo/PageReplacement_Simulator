package algorithms;

import model.PageInput;

public class Clock extends PageReplacementAlgorithm{
    private boolean[] secondChance;

    public Clock(PageInput input){
        setValues(input);
        setAddValues();
    }

    private void setAddValues(){
        secondChance = new boolean[frameLen];
    }

    @Override
    protected void insertFrameAtReference(int currentReference, int currentFrame, String[][] frames){
        int prevRef = currentReference-1;
        int idx = alreadyExistInFrame(currentReference, frames);
        boolean exist = (idx==-1) ? false : true;

        for(int i=0; i<frameLen; i++){
            if(!exist && i==(currentFrame+1)%frameLen){
            		if(secondChance[i]){
            			secondChance[i]=false;
            			this.currentFrame = (this.currentFrame+1)%frameLen;
            			currentFrame = this.currentFrame;
            			frames[currentReference][i] = frames[prevRef][i];
                    		if(i+1==frameLen)
                        		i=-1;
            		}else{
            			pageFault(currentReference, frames, i);
            		}
            }else{ //if current value exists
            	if(exist)
            		setExecutingFrame(idx);
                frames[currentReference][i] = frames[prevRef][i];
            }
        }
        
        if(exist)
        	updateHit(true);
        else
        	updateHit(false);
    } 

    @Override
    protected int alreadyExistInFrame(int currentReference, String[][] frames){
        int prevRef = currentReference-1;
        for(int i=0; i<frameLen; i++){
            if(frames[prevRef][i]==null) //safety catch for null
                break;
            if((frames[prevRef][i]).equals(values[currentReference])){
                secondChance[i] = true;
                return i;
            }
        }
        return -1;
    }
}