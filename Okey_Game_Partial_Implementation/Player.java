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
        Tile newTile[] = new Tile[playerTiles.length-1];
        for(int i = 0; i < playerTiles.length; i++){
            if(i != index && i < index){
                newTile[i] = playerTiles[i];
            }
            if(i != index && i > index){
                newTile[i] = playerTiles[i+1];
            }
        }
        Tile[] removed = new Tile[1];
        removed[0] = playerTiles[index];

        playerTiles = newTile;

        return removed[0];
    }

    /*
     * TODO: adds the given tile to the playerTiles in order
     * should also update numberOfTiles accordingly.
     * make sure playerTiles are not more than 15 at any time
     */
    public void addTile(Tile t) {

        //satisfying the condition of not having more than 15 tiles
        if(numberOfTiles <= 15){

            playerTiles[ 14 ] = t;
            sortTiles();
        }

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
        boolean winner = false;

        for (int i = 0; i < hand.length; i++) {

            check = true;
            count = 1;
            chain[0] = hand[i];

            for (int j = 0; j < hand.length ; j++) {

                if (hand[i] != null) {
                    
                    if (hand[i].canFormChainWith(hand[j])) {
    
                        for (int k = 0; k < chain.length; k++) {
    
                            if (chain[k] != null) {
                                
                                if(hand[j].compareTo(chain[k]) == 0){
        
                                    check = false;
        
                                }
                            }
                            
                        }
    
                        if(check){
    
                            chain[count] = hand[j];
                            count++;
    
                        }
                        
                    }
                }
                
            }

            if (count == 4) {

                if (threeChains[row][0] == null) {

                    threeChains[0][0] = new Tile(8, 'z');
                    threeChains[1][0] = new Tile(8, 'z');
                    threeChains[2][0] = new Tile(8, 'z');
                    
                    for (int j = 0; j < threeChains.length; j++) {
                        
                        if (threeChains[j][0].getValue() != chain[0].getValue()) {
                            
                            threeChains[row] = chain;
                        }
                    }

                    row++;
                }


                

                
            }

            chain = new Tile[4];
            
        }
    
        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 4; j++) {

                if(threeChains[i][j] == null){

                    winner = false;
                }
                else{
                    winner = true;
                }
                
            }
            
        }

        if (winner) {
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

            if(playerTiles[i] != null){
            System.out.print(playerTiles[i].toString() + " ");
            }
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
    public void sortTiles(){
        
        Tile[] tempTiles = new Tile[playerTiles.length]; 
        int posToSetTile = 0;
        for(int i = 1 ; i < 8 ; i++){

            for(int a = 0 ; a < playerTiles.length ; a++){
                
                if((playerTiles[a] != null) && playerTiles[a].value == i){

                    tempTiles[posToSetTile] = playerTiles[a];
                    posToSetTile++;
                }   
        }
            
        }
        playerTiles = tempTiles;
    }
}
