package algorithms;

import model.PageInput;

public class Clock {
    private int[] values;
    private int frameLen;
    private int refLen;
    public String[][] frames;
    private int currentReference;
    private int currentFrame;
    public int pageFaults;
    private boolean[] secondChance;

    public Clock(PageInput input){
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
        secondChance = new boolean[frameLen];
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
                frames[currentReference][i] = frames[prevRef][i];
            }
        }
    } 

    private void pageFault(int currentReference, String[][] frames, int i){
        frames[currentReference][i] = String.valueOf(values[currentReference]);
        this.currentFrame = (this.currentFrame+1)%frameLen;
        this.pageFaults++;
    }

    private boolean alreadyExistInFrame(int currentReference, String[][] frames){
        int prevRef = currentReference-1;
        for(int i=0; i<frameLen; i++){
            if(frames[prevRef][i]==null) //safety catch for null
                break;
            if(Integer.parseInt(frames[prevRef][i])==values[currentReference]){
                secondChance[i] = true;
                return true;
            }
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
