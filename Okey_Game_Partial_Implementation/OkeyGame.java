public class OkeyGame {

    Player[] players;
    Tile[] tiles;

    Tile lastDiscardedTile;

    int currentPlayerIndex = 0;

    public OkeyGame() {
        players = new Player[4];
    }

    public void createTiles() {
        tiles = new Tile[112];
        int currentTile = 0;

        // two copies of each color-value combination, no jokers
        for (int i = 1; i <= 7; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[currentTile++] = new Tile(i,'Y');
                tiles[currentTile++] = new Tile(i,'B');
                tiles[currentTile++] = new Tile(i,'R');
                tiles[currentTile++] = new Tile(i,'K');
            }
        }
    }

    /*
     * TODO: distributes the starting tiles to the players
     * player at index 0 gets 15 tiles and starts first
     * other players get 14 tiles
     * this method assumes the tiles are already shuffled
     */
    public void distributeTilesToPlayers() {
        System.arraycopy(tiles, 0, players[0], 0, 15);
         System.arraycopy(tiles, 15, players[1], 0, 14);
         System.arraycopy(tiles, 29, players[2], 0, 14);
         System.arraycopy(tiles, 43, players[3], 0, 14);
    }

    /*
     * TODO: get the last discarded tile for the current player
     * (this simulates picking up the tile discarded by the previous player)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getLastDiscardedTile() {
        return null;
    }

    /*
     * TODO: get the top tile from tiles array for the current player
     * that tile is no longer in the tiles array (this simulates picking up the top tile)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getTopTile() {
        return null;
    }

    /*
     * TODO: should randomly shuffle the tiles array before game starts
     */
    public void shuffleTiles() {
        Random rand = new Random();
        for(int i = 111; i >0 ; i--){
            int index = rand.nextInt(i+1);
            int temp  = tiles[i];
            tiles[i] = tiles[index];
            tiles[index]= temp;
    }

    /*
     * TODO: check if game still continues, should return true if current player
     * finished the game, use isWinningHand() method of Player to decide
     */
    public boolean didGameFinish(int index) {
        if (players[index].isWinningHand(players[index].getTiles())) {
            return true;
        }
        else{
            return false;
        }
    }

    /*
     * TODO: Pick a tile for the current computer player using one of the following:
     * - picking from the tiles array using getTopTile()
     * - picking from the lastDiscardedTile using getLastDiscardedTile()
     * You should consider if the discarded tile is useful for the computer in
     * the current status. Print whether computer picks from tiles or discarded ones.
     */
    public void pickTileForComputer() {

        // determining whether the computer needs to draw the discarded tile or draw the tile from the stack
        // checking if lastDiscardedTile would increase the chain

        // checking if we have same number but different colour versions of lastDiscardedTile
        boolean shouldPickLastDiscarded = false;
        Tile[] tilesOfCurrentPlayer = players[currentPlayerIndex].getTiles();
        for(Tile t : tilesOfCurrentPlayer){

            //properties of t
            char colorOfT = t.getColor();
            int valueOfT = t.getValue();

            //properties of last discarded
            char colorOfLastDiscarded = lastDiscardedTile.getColor();
            int valueOfLastDiscarded = lastDiscardedTile.getValue();

            if((colorOfT != colorOfLastDiscarded) && !(valueOfT == valueOfLastDiscarded) ){
                shouldPickLastDiscarded = true;
            }  
        }

        if(shouldPickLastDiscarded){

            getLastDiscardedTile();

        }

        else{
            getTopTile();
        }

    }

    /*
     * TODO: Current computer player will discard the least useful tile.
     * this method should print what tile is discarded since it should be
     * known by other players. You may first discard duplicates and then
     * the single tiles and tiles that contribute to the smallest chains.
     */
    public void discardTileForComputer() {
        
        boolean isRemoved = true;
        int removedIndex = -1;
        //remove dublicates
        for(int i = 0; i < players[currentPlayerIndex].getTiles().length && isRemoved ; i++){
            
            for(int j = 0; j < players[currentPlayerIndex].getTiles().length; j++){
                
                if(players[currentPlayerIndex].getTiles()[i].equals(players[currentPlayerIndex].getTiles()[j]) && i != j){
                    removedIndex = i;
                    isRemoved = false;
                }
                
            }

        }
        //if there is no removed tiles: remove single tile
        if(isRemoved){
            
            for(int i = 0; i < players[currentPlayerIndex].getTiles().length && isRemoved ; i++){
                int equalityCounter = 0;
                for(int j = 0; j < players[currentPlayerIndex].getTiles().length; j++){
                    
                    if(players[currentPlayerIndex].getTiles()[i].getValue() == players[currentPlayerIndex].getTiles()[i].getValue()){
                        equalityCounter++;
                    }
                }
                if(equalityCounter==1){
                    removedIndex = i;
                    isRemoved = false;
                }
            }
        }

        //if there is no single, then remove tile that contribute the smallest chain
        if(isRemoved){
            Integer[] chainCounter = new Integer[15];
            for(int i = 0; i < players[currentPlayerIndex].getTiles().length && isRemoved ; i++){
                int chainCount = 0;
                for(int j = 0; j < players[currentPlayerIndex].getTiles().length; j++){
                    
                    if(players[currentPlayerIndex].getTiles()[i].getValue() == players[currentPlayerIndex].getTiles()[i].getValue()){
                        chainCount++;
                    }
                    chainCounter[i] = chainCount;
                }
            }
            for(int k = 0; k < chainCounter.length ; k++){
                if(chainCounter[k] == 2){
                    removedIndex = k;
                    isRemoved = false;
                }
            }
            if(isRemoved){
                for(int k = 0; k < chainCounter.length ; k++){
                    if(chainCounter[k] == 3){
                        removedIndex = k;
                        isRemoved = false;
                    }
                }

            }

        }

        System.out.print("Discarded tile is : ");
        System.out.println(players[currentPlayerIndex].getTiles()[removedIndex]);
        discardTile(removedIndex);

    }

    /*
     * TODO: discards the current player's tile at given index
     * this should set lastDiscardedTile variable and remove that tile from
     * that player's tiles
     */
    public void discardTile(int tileIndex) {

    }

    public void displayDiscardInformation() {
        if(lastDiscardedTile != null) {
            System.out.println("Last Discarded: " + lastDiscardedTile.toString());
        }
    }

    public void displayCurrentPlayersTiles() {
        players[currentPlayerIndex].displayTiles();
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

      public String getCurrentPlayerName() {
        return players[currentPlayerIndex].getName();
    }

    public void passTurnToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
    }

    public void setPlayerName(int index, String name) {
        if(index >= 0 && index <= 3) {
            players[index] = new Player(name);
        }
    }
}
