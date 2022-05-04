package algorithms;

import model.PageInput;

public abstract class PageReplacementAlgorithm {
    protected String[] values;
    protected int frameLen;
    protected int refLen;
    public int currentReference, executingRef = 0;
    public int currentFrame, executingFrame = 0;
    public int pageFaults;
    public boolean hit = false;
    public String[][] frames;
    public boolean[] hitBoolean;

    public void setValues(PageInput input){
        values = input.getReferenceValues();
        frameLen = input.getFrameLength();
        refLen = input.getReferenceLength();
        frames = new String[refLen][frameLen];
        hitBoolean = new boolean[refLen];
        currentReference = 0;
        currentFrame = -1;
        pageFaults = 0;
    }

    public void move(){
        if(currentReference==0){
            frames[0][0]= String.valueOf(values[0]);
            updateHit(false);
            this.currentFrame++;
            this.pageFaults++;
        }else{
            insertFrameAtReference(currentReference, currentFrame, frames);
        }
        hitBoolean[currentReference] = hit;
        executingRef = currentReference;
        this.currentReference++;
    }

    protected void pageFault(int currentReference, String[][] frames, int i){
        frames[currentReference][i] = String.valueOf(values[currentReference]);
        executingFrame = currentFrame;
        this.currentFrame = (this.currentFrame+1)%frameLen;
        this.pageFaults++;
    }


    protected boolean alreadyExistInFrame(int currentReference, String[][] frames){
        int prevRef = currentReference-1;
        for(int i=0; i<frameLen; i++){
            if(frames[prevRef][i]==null) //safety catch for null
                break;
            if((frames[prevRef][i]).equals(values[currentReference]))
                return true;
        }
        return false;
    }

    public void updateHit(boolean hit){
        this.hit = hit;
    }

    public boolean isHit(){
        return this.hit;
    }

    public String[][] getFrames(){
        return this.frames;
    }

    public int getPageFaults(){
        return this.pageFaults;
    }
    
    public int getExecutingRef(){ //
    	return this.executingRef;
    }
    
    public int getExecutingFrame(){ //
	return this.executingFrame;
	}
    public int getCurrentRefernce(){
        return this.currentReference;
    }

    public int getCurrentFrame(){
        return this.currentFrame;
    }

    public boolean[] getHitArray(){
        return this.hitBoolean;
    }

    public String[] getCurrentFrames(){
        return privGetCurrentFrames(this.currentReference, this.frameLen, this.frames);
    }

    protected String[] privGetCurrentFrames(int reference, int frameLen, String[][] frames){
        String[] ret = new String[frameLen];
        if(reference>0){
             for(int i=0; i<frameLen; i++)
                 ret[i] = frames[reference][i];
         }
         return ret; 
    }

    abstract protected void insertFrameAtReference(int currentReference, int currentFrame, String[][] frames);
    
}

