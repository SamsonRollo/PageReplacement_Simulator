package algorithms;

import model.PageInput;

public class OPT extends PageReplacementAlgorithm{

    public OPT(PageInput input){
        setValues(input);
    }

    @Override
    protected void insertFrameAtReference(int currentReference, int currentFrame, String[][] frames) {
        boolean exists = alreadyExistInFrame(currentReference, frames);
        int prevRef = currentReference-1;

        if(exists){ 
            for(int i=0; i<frameLen; i++){
                String curVal = frames[prevRef][i];
                if(curVal==null)
                    break;
                if(curVal.equals(values[currentReference]))
                    this.currentFrame = i;
                frames[currentReference][i] = curVal;
            }
        }else{
            if(!isFrameFull(prevRef, frames)){
                for(int i=0; i<frameLen; i++){
                    String curVal = frames[prevRef][i];
                    if(curVal==null){
                        pageFault(currentReference, frames, i);
                        this.currentFrame = i;
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
