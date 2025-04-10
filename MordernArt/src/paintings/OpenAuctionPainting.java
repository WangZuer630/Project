package paintings;

import players.Player;

public class OpenAuctionPainting extends Painting {
    @Override
    public void auction(Player[] players) {
//        boolean deal = false;
//        int turn = 0;
//        int startIndex = 0;
//        for(int i = 0; i < players.length; i++){
//            if(players[i].equals(owner)){
//                startIndex=i;
//                break;
//            }
//        }
//        do{
//            deal=true;
//            for(int i = 0; i < players.length; i++){
//                if(turn== players.length&&deal)
//                    break;
//                int bid = players[(i+startIndex)% players.length].bid(currentBid,this);
//                if(bid > currentBid){
//                    currentBidder=players[(i+startIndex)% players.length];
//                    currentBid=bid;
//                    deal=false;
//                    turn=0;
//                }
//                turn++;
//            }
//        }while (deal);
//        super.sold();
        currentBid = 0;
        currentBidder = null;
        boolean auction = true;
        int currentBidderIndex = 0;

        for(int i = 0; i < players.length; i++){
            Player player = players[i];
            int bid = player.bid(currentBid,this);
            if(bid>currentBid){
                currentBid=bid;
                currentBidder=player;
                currentBidderIndex=i;
            }
        }
        if(currentBidderIndex==0) {
            sold();
            return;
        }
        while(auction){
            auction=false;
            for(int i = 0; i< players.length;i++){
                if(players[i]!=currentBidder){
                    Player player = players[i];

                    int bid = player.bid(currentBid,this);
                    if(bid>currentBid){
                        currentBid=bid;
                        currentBidder=player;
                        auction=true;
                    }
                }
                else
                    break;
            }
        }
        sold();
    }

    @Override
    public String getType() {
        return "Open Auction";
    }

    public OpenAuctionPainting(int id){
        super(id);
    }

}
