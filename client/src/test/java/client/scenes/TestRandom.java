package client.scenes;

import client.IRandom;

public class TestRandom implements IRandom {

    private int[] fakeRandomNumber;
    private int counter;

    public TestRandom(){
        counter = 0;
    }

    public void setFakeRandomNumber(int[] fakeRandomNumber) {
        this.fakeRandomNumber = fakeRandomNumber;
    }

    @Override
    public int nextInt(int bound) {
        return fakeRandomNumber[counter++];
    }
}
