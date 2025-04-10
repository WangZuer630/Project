package paintings;

import players.Player;

public class FixedPriceAuctionPainting extends Painting{
    public FixedPriceAuctionPainting(int id){
        super(id);
    }

    @Override
    public String getType() {
        return "Fixed Price Auction";
    }

    @Override
    public void auction(Player[] players) {
//        currentBid = 0;
//        currentBidder = null;
//        int playerIndex = -1;
//        for(int i = 0; i < players.length; i++){
//            for(Painting painting : players[i].handPaintings){
//                if(painting.getArtistId()==this.getArtistId()){
//                    playerIndex =i;
//                    break;
//                }
//                if(playerIndex !=-1)
//                    break;
//            }
//        }
//        Scanner in = new Scanner(System.in);
//        System.out.println(players[playerIndex]+", please fix a price for the auction");
//
//        int bid=players[playerIndex].bid(currentBid, this);
//        if(bid>currentBid) {
//            currentBid = bid;
//            currentBidder = players[playerIndex];
//        }
//
//        for(int i = 0; i < players.length-1; i++){
//            int currentPlayer = (playerIndex+i)%players.length;
//            Player player = players[currentPlayer];
//            int newBid = player.bid(currentBid,this);
//            if(newBid>currentBid){
//                currentBid=newBid;
//                currentBidder=player;
//            }
//        }
//        if(currentBidder!=null)
//            sold();
        currentBid=0;
        int turn = 0;
        int startIndex = 0;
        for(int i = 0; i < players.length; i++){
            if(players[i].equals(owner)){
                startIndex=i;
                break;
            }
        }
        currentBidder=players[startIndex];
        currentBid = players[startIndex].bid(currentBid,this);
        for(int i = 0; i < players.length; i++){
            if(turn==players.length-1)
                break;
            int bid = players[(1+i+startIndex)% players.length].bid(currentBid,this);
            if(bid>=currentBid){
                currentBidder=players[(1+i+startIndex)% players.length];
                super.sold();
            }
            else{
                System.out.println(players[(1+i+startIndex)% players.length]+" pass.");
            }
            turn++;
        }
        if(currentBidder.equals(owner))
            sold();
    }
}