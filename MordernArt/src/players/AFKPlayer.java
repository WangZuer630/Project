package players;

import paintings.Painting;

import java.util.concurrent.ThreadLocalRandom;

public class AFKPlayer extends Player{
    public AFKPlayer(int money){
        super(money,"AFK "+totalPlayers++);

    }

    @Override
    public Painting playPainting() {
        if(handPaintings.size()==0)
            return null;
        return handPaintings.remove(ThreadLocalRandom.current().nextInt(0,handPaintings.size()));
    }

    @Override
    public int bid(int currentBid, Painting p) {
        System.out.println(getName()+" pass");
        return 0;
    }

}
