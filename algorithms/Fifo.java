package algorithms;

import model.PageInput;

public class Fifo {
    private int[] values;
    private int frameLen;
    private int refLen;
    public String[][] frames;
    private int currentReference;
    private int currentFrame;
    public int pageFaults;

    public Fifo(PageInput input){
        setValues(input);
    }

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

    private void insertFrameAtReference(int currentReference, int currentFrame, String[][] frames){
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

    private boolean alreadyExistInFrame(int currentReference, String[][] frames){
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
