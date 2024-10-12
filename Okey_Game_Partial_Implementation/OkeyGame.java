import java.util.Random;

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
        System.arraycopy(tiles, 0, players[0].getTiles(), 0, 15);
        System.arraycopy(tiles, 15, players[1].getTiles(), 0, 14);
        System.arraycopy(tiles, 29, players[2].getTiles(), 0, 14);
        System.arraycopy(tiles, 43, players[3].getTiles(), 0, 14);
        for(int i = 0; i < 53; i++){
            tiles[i] = null;
        }
        players[0].numberOfTiles = 15;
        players[1].numberOfTiles = 15;
        players[2].numberOfTiles = 15;
        players[3].numberOfTiles = 15;
    }

    /*
     * TODO: get the last discarded tile for the current player
     * (this simulates picking up the tile discarded by the previous player)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getLastDiscardedTile() {
        Player player = players[currentPlayerIndex];
        player.addTile(lastDiscardedTile);
        return lastDiscardedTile.toString();
    }

    /*
     * TODO: get the top tile from tiles array for the current player
     * that tile is no longer in the tiles array (this simulates picking up the top tile)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getTopTile() {
        Player player = players[currentPlayerIndex];
        Tile topTile;
        boolean isGotten = false;
        int lastTileIndex = 0;
        String topTileCopy;
        for(int i = 0; i < 112; i++){
            if(tiles[i] != null){
                topTile = tiles[i];
                player.addTile(topTile);
                lastTileIndex = i;
                isGotten = true;
                break;
            }
        }
        if(isGotten){
            topTileCopy = tiles[lastTileIndex].toString();
            tiles[lastTileIndex] = null;
        }else{
            return "No more tiles left";
        }
        return topTileCopy;
    }

    /*
     * TODO: should randomly shuffle the tiles array before game starts
     */
    public void shuffleTiles() {
        Random rand = new Random();
        for(int i = 111; i >0 ; i--){
            int index = rand.nextInt(i+1);
            Tile temp  = tiles[i];
            tiles[i] = tiles[index];
            tiles[index]= temp;
        }
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
        
        boolean  notHavingSameTile = true; 
        boolean havingSameValueButDifColor = false;
        Tile[] tilesOfCurrentPlayer = players[currentPlayerIndex].getTiles();

        for(Tile t : tilesOfCurrentPlayer){
            if((t.value == lastDiscardedTile.value) && t.color == lastDiscardedTile.color){
                notHavingSameTile = false;
            }
        }

        if(notHavingSameTile){
            for(Tile t : tilesOfCurrentPlayer){
                if(t.value == lastDiscardedTile.value){
                    havingSameValueButDifColor = true;
                }
            }
        }

        if(havingSameValueButDifColor){
            getLastDiscardedTile();
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
        lastDiscardedTile = players[0].getTiles()[tileIndex];
        for(int i = tileIndex  ; i < 14; i++){
            players[0].getTiles()[i] = players[0].getTiles()[i + 1];
        }
        players[0].getTiles()[14] = null;
        
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
