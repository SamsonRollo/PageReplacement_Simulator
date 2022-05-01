package algorithms;

import java.util.ArrayList;

import model.PageInput;

public class LRU extends PageReplacementAlgorithm{
    private ArrayList<String> currentFrameArr;

    public LRU(PageInput input){
        setValues(input);
        currentFrameArr = new ArrayList<>(frameLen);
    }


@Override
    protected void insertFrameAtReference(int currentReference, int currentFrame, String[][] frames) {
        int prevRef = currentReference-1;
        String currentVal = String.valueOf(values[currentReference]);
        if(currentFrameArr.size()==0)
            currentFrameArr.add(0, String.valueOf(values[prevRef]));
        if(currentFrameArr.contains(currentVal)){
        	updateHit(true);//
            rearrangeFrame(currentFrameArr, currentVal, currentVal); ///if full and not
            frames = produceNextFrame(frames, prevRef, currentReference, false, -1, currentVal);
            lRUPageFault(false, getIndexInFrame(frames, currentVal, currentReference));
        }else{
        	updateHit(false);
            int idx = getIndexInFrame(frames, getLRUVal(currentFrameArr), prevRef);
            if(currentFrameArr.size()!=frameLen)
            	idx=-1;
            frames = produceNextFrame(frames, prevRef, currentReference, true, idx, currentVal);
            rearrangeFrame(currentFrameArr, getLRUVal(currentFrameArr), currentVal);
            lRUPageFault(true, idx);
        }
    }

    private void lRUPageFault(boolean pagefault, int currentFrame){
        if(pagefault)
            this.pageFaults++;
        executingFrame = currentFrame;
        this.currentFrame = currentFrame;
    }

    private String[][] produceNextFrame(String[][] frames, int prevRef, int currentReference, boolean pagefault, int vicIdx, String currentVal){
        for(int i=0; i<frameLen; i++){
            if(frames[prevRef][i]==null){
             	if(pagefault && vicIdx==-1) //if does not exist and frame not full
             	   frames[currentReference][i] = currentVal;
                break;
            }
            if(pagefault && i==vicIdx)//if full and there is a victim
                frames[currentReference][i] = currentVal;
            else
                frames[currentReference][i] = frames[prevRef][i];
        }
        return frames;
    }

    private int getIndexInFrame(String[][] frames, String value, int reference){
        String iVal;
        int idx = -1;
        for(int i=0; i<frameLen; i++){
            iVal = frames[reference][i];
            if(iVal==null)
                break;
            if(iVal.equals(value))
                return i;    
        }
        return idx;
    }

    //refactor
    private void rearrangeFrame(ArrayList<String> currentFrameArr, String currentVal, String newVal){
    	if(currentFrameArr.size()==frameLen)
        	currentFrameArr.remove((Object)currentVal);
        currentFrameArr.add(newVal);
    }

    private String getLRUVal(ArrayList<String> currentFrameArr){
        return currentFrameArr.get(0);
    }
}