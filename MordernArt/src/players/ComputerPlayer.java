package players;

import paintings.Painting;

import java.util.concurrent.ThreadLocalRandom;

public class ComputerPlayer extends Player{
    private int[][] scoreboard;
    public ComputerPlayer(int money, int[][] scoreboard){
        super(money, "Computer "+totalPlayers++);
        this.scoreboard=scoreboard;
    }

    @Override
    public int bid(int currentBid, Painting p) {
        int potentialValue =calculatePotentialValue(p,getCurrentRound());
        int bid = 0;
        if(currentBid<potentialValue) {
            bid = Math.min(potentialValue, getMoney());
            System.out.println(getName()+" bids "+bid);
            return bid;
        }else{
            System.out.println(getName()+" pass");
            return 0;}
    }
    private int calculatePotentialValue(Painting painting, int currentRound){
        int artistId = painting.getArtistId();
        int maxSold = 0;
//        int currentRound = getCurrentRound();
        if (currentRound < scoreboard.length)
            maxSold = scoreboard[currentRound][artistId];
        return ThreadLocalRandom.current().nextInt(0,maxSold+10);
    }
    private int getCurrentRound(){
        boolean completeRound = false;
        int currentRound = 0;
        for(int round = 0; round < scoreboard.length; round++){
            for( int artistId = 0; artistId < scoreboard[round].length; artistId++){
                if(scoreboard[round][artistId]>0)
                    completeRound = true;
            }
            if(completeRound)
                currentRound++;
        }
        return currentRound;
    }



    @Override
    public Painting playPainting() {
        return handPaintings.remove(ThreadLocalRandom.current().nextInt(0,handPaintings.size()));
    }
}
