package client;

import java.util.Random;

public class RandomNumber implements IRandom{
    Random rand = new Random();

    @Override
    public int nextInt(int bound) {
        return rand.nextInt(bound);
    }
}
