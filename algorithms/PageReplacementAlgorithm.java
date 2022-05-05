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
    public int[][] hitBoolean;

    public void setValues(PageInput input){
        values = input.getReferenceValues();
        frameLen = input.getFrameLength();
        refLen = input.getReferenceLength();
        frames = new String[refLen][frameLen];
        hitBoolean = new int[refLen][2];
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
        hitBoolean[currentReference][0] = executingFrame;
        hitBoolean[currentReference][1] = hit ? 1:0;
        executingRef = currentReference;
        this.currentReference++;
    }

    protected void pageFault(int currentReference, String[][] frames, int i){
        frames[currentReference][i] = String.valueOf(values[currentReference]);
        this.currentFrame = (this.currentFrame+1)%frameLen;
        this.executingFrame= this.currentFrame;
		this.pageFaults++;
    }


    protected int alreadyExistInFrame(int currentReference, String[][] frames){
        int prevRef = currentReference-1;
        for(int i=0; i<frameLen; i++){
            if(frames[prevRef][i]==null) //safety catch for null
                break;
            if((frames[prevRef][i]).equals(values[currentReference]))
                return i;
        }
        return -1;
    }

    public void updateHit(boolean hit){
        this.hit = hit;
    }

    public boolean isHit(){
        return this.hit;
    }

    public void setExecutingFrame(int frame){
    	this.executingFrame = frame;
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

    public int[][] getHitArray(){
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

