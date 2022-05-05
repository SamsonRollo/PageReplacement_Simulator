package algorithms;

import model.PageInput;

public class OPT extends PageReplacementAlgorithm{

    public OPT(PageInput input){
        setValues(input);
    }

    @Override
    protected void insertFrameAtReference(int currentReference, int currentFrame, String[][] frames) {
        int idx = alreadyExistInFrame(currentReference, frames);
        boolean exists = (idx==-1) ? false : true;
        int prevRef = currentReference-1;

        if(exists){
        	updateHit(true);//
            for(int i=0; i<frameLen; i++){
                String curVal = frames[prevRef][i];
                if(curVal==null)
                    break;
                 if(curVal.equals(values[currentReference])){
                 	executingFrame = currentFrame;// check validity for step by step
                 	this.currentFrame = i;
                 }
                 setExecutingFrame(idx);
                frames[currentReference][i] = curVal;
            }
        }else{
        	updateHit(false);//
            if(!isFrameFull(prevRef, frames)){
                for(int i=0; i<frameLen; i++){
                    String curVal = frames[prevRef][i];
                    if(curVal==null){
                        pageFault(currentReference, frames, i);
                        this.currentFrame = i;
                        this.executingFrame = i;
                        break;
                    }
                    frames[currentReference][i] = curVal;
                }
            }else{
                int vicIdx = getOPTVictimIdx(prevRef, currentReference, frames);
                for(int i=0; i<frameLen; i++){
                    if(i==vicIdx){
                        pageFault(currentReference, frames, i);
                        this.currentFrame = i;
                        this.executingFrame = i;
                        continue;
                    }
                    frames[currentReference][i] = frames[prevRef][i];
                }
            }
        }
    }

    private int getOPTVictimIdx(int prevRef, int currentReference, String[][] frames){
        int idx = 0;
        int ranker = frameLen;
        int[] refLoc = new int[frameLen];
        String[] prevFrame = privGetCurrentFrames(prevRef, frameLen, frames);	
        for(int i=currentReference; i<refLen; i++){
            String iStringVal = values[i];
            for(int j=0; j<frameLen; j++){
                String jStringVal = prevFrame[j];
                if(iStringVal.equals(jStringVal) && refLoc[j]==0){
                    refLoc[j] = ranker--;
                    break;
                }
            }
        }
        for(int i=0; i<frameLen; i++){
            if(refLoc[i]==1)
                idx = i;
            if(refLoc[i]==0){
            	idx=i;
            	break;
            }
        }
        return idx;
    }

    private boolean isFrameFull(int prevRef, String[][] frames){
        for(int i=0; i<frameLen; i++){
            if(frames[prevRef][i]==null)
                return false;
        }
        return true;
    }
}