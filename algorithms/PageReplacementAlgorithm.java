package algorithms;

import model.PageInput;

public class PageReplacementAlgorithm {
    protected int[] values;
    protected int frameLen;
    protected int refLen;
    protected int currentReference;
    protected int currentFrame;
    public int pageFaults;
    public String[][] frames;

    public void setValues(PageInput input){
        values = input.getReferenceValues();
        frameLen = input.getFrameLength();
        refLen = input.getReferenceLength();
        frames = new String[refLen][frameLen];
        currentReference = 0;
        currentFrame = 0;
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

    protected void insertFrameAtReference(int currentReference, int currentFrame, String[][] frames){}

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
}
