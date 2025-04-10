package paintings;


import players.Player;

public class OneOfferAuctionPainting extends Painting{
    public OneOfferAuctionPainting(int id){
        super(id);
    }

    @Override
    public String getType() {
        return "One Offer Auction";
    }

    @Override
    public void auction(Player[] players) {
//        currentBid = 0;
//        currentBidder = null;
//        int artistIndex = -1;
//        for(int i = 0; i < players.length; i++){
//            for(Painting painting : players[i].handPaintings){
//                if(painting.getArtistId()==this.getArtistId()){
//                    artistIndex=i;
//                    break;
//                }
//                if(artistIndex!=-1)
//                    break;
//            }
//        }
//
//        int start = (artistIndex+1)% players.length;
//
//        for(int i = 0; i < players.length; i++){
//            int currentPlayer = (start+i)% players.length;
//            Player player = players[currentPlayer];
//
//            int bid = player.bid(currentBid,this);
//            if(bid>currentBid){
//                currentBid=bid;
//                currentBidder=player;
//            }
//        }
//
//        if(currentBidder!=null)
//            sold();
        currentBid=0;
        currentBidder=null;
        int startIndex = 0;
        int turn = 0;
        for(int i = 0; i < players.length; i++){
            if(players[i].equals(owner)){
                startIndex=i;
                break;
            }
        }
        for(int i = 0; i < players.length; i++){
            if(turn== players.length)
                break;
            int bid = players[(i+1+startIndex)% players.length].bid(currentBid,this);
            if(bid > currentBid){
                currentBidder=players[(i+1+startIndex)% players.length];
                currentBid=bid;
            }
            turn++;
        }
        super.sold();

    }
}
