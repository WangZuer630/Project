package paintings;

import players.Player;

public class HiddenAuctionPainting extends Painting{
    @Override
    public String getType() {
        return "Hidden Auction";
    }
    public HiddenAuctionPainting(int id){
        super(id);
    }

    @Override
    public void auction(Player[] players) {
//        currentBid=0;
//        currentBidder=null;
//        for(int i = 0; i < players.length; i++){
//            int bid = players[i].bid(0,this);
//            if(bid>currentBid){
//                currentBid=bid;
//                currentBidder=players[i];
//            }
//        }
//        if(currentBidder!=null){
//            sold();
//        }
        currentBid=0;
        currentBidder=null;
        int turn = 0;
        for(int i = 0; i < players.length; i++){
            if(turn== players.length)
                break;
            int bid = players[i].bid(currentBid,this);
            if(bid>currentBid){
                currentBidder=players[i];
                currentBid=bid;
            }
            turn++;
        }
        super.sold();
    }
}
