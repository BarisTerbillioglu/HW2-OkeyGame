public class Player {
    String playerName;
    Tile[] playerTiles;
    int numberOfTiles;

    public Player(String name) {
        setName(name);
        playerTiles = new Tile[15]; // there are at most 15 tiles a player owns at any time
        numberOfTiles = 0; // currently this player owns 0 tiles, will pick tiles at the beggining of the game
    }

    /*
     * TODO: removes and returns the tile in given index
     */
    public Tile getAndRemoveTile(int index) {
        return null;
    }

    /*
     * TODO: adds the given tile to the playerTiles in order
     * should also update numberOfTiles accordingly.
     * make sure playerTiles are not more than 15 at any time
     */
    public void addTile(Tile t) {

    }

    /*
     * TODO: checks if this player's hand satisfies the winning condition
     * to win this player should have 3 chains of length 4, extra tiles
     * does not disturb the winning condition
     * @return
     */
    public boolean isWinningHand(Tile[] hand){

        Tile[] chain = new Tile[4];
        Tile[][] threeChains = new Tile[3][4];
        int count;
        int row = 0;
        boolean check;

        for (int i = 0; i < hand.length; i++) {

            check = true;
            count = 1;
            chain[0] = hand[i];

            for (int j = 0; j < hand.length ; j++) {

                if (hand[i].canFormChainWith(hand[j])) {

                    for (int k = 0; k < chain.length; k++) {

                        if(hand[j].compareTo(chain[k]) == 0){

                            check = false;

                        }
                        
                    }

                    if(check){

                        chain[count] = hand[j];
                        count++;

                    }
                    
                }
                
            }

            if (count == 4) {

                threeChains[row] = chain;
                row++;
                
            }

            chain = new Tile[4];
            
        }

        if (threeChains.length == 3) {
            return true;
        }

        else{
            return false;
        }
    }

    public int findPositionOfTile(Tile t) {
        int tilePosition = -1;
        for (int i = 0; i < numberOfTiles; i++) {
            if(playerTiles[i].compareTo(t) == 0) {
                tilePosition = i;
            }
        }
        return tilePosition;
    }

    public void displayTiles() {
        System.out.println(playerName + "'s Tiles:");
        for (int i = 0; i < numberOfTiles; i++) {
            System.out.print(playerTiles[i].toString() + " ");
        }
        System.out.println();
    }

    public Tile[] getTiles() {
        return playerTiles;
    }

    public void setName(String name) {
        playerName = name;
    }

    public String getName() {
        return playerName;
    }
}
