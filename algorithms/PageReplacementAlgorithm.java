package algorithms;

import model.PageInput;

abstract class PageReplacementAlgorithm {
    protected int[] values;
    protected int frameLen;
    protected int refLen;
    public int currentReference;
    public int currentFrame;
    public int pageFaults;
    public String[][] frames;

    public void setValues(PageInput input){
        values = input.getReferenceValues();
        frameLen = input.getFrameLength();
        refLen = input.getReferenceLength();
        frames = new String[refLen][frameLen];
        currentReference = 0;
        currentFrame = -1;
        pageFaults = 0;
    }

    public void move(){
        if(currentReference==0){
            frames[0][0]= String.valueOf(values[0]);
            this.currentFrame++;
            this.pageFaults++;
        }else{
            insertFrameAtReference(currentReference, currentFrame, frames);
        }
        this.currentReference++;
    }

    protected void pageFault(int currentReference, String[][] frames, int i){
        frames[currentReference][i] = String.valueOf(values[currentReference]);
        this.currentFrame = (this.currentFrame+1)%frameLen;
        this.pageFaults++;
    }


    protected boolean alreadyExistInFrame(int currentReference, String[][] frames){
        int prevRef = currentReference-1;
        for(int i=0; i<frameLen; i++){
            if(frames[prevRef][i]==null) //safety catch for null
                break;
            if(Integer.parseInt(frames[prevRef][i])==values[currentReference])
                return true;
        }
        return false;
    }

    public String[][] getFrames(){
        return frames;
    }

    public int getPageFaults(){
        return pageFaults;
    }

    public int getCurrentRefernce(){
        return currentReference;
    }

    public int getCurrentFrame(){
        return currentFrame;
    }

    public String[] getCurrentFrames(){
       String[] ret = new String[frameLen];
       if(currentReference>0){
            for(int i=0; i<frameLen; i++)
                ret[i] = frames[currentReference-1][i];
        }
        return ret;
    }

    abstract protected void insertFrameAtReference(int currentReference, int currentFrame, String[][] frames);
    
}
