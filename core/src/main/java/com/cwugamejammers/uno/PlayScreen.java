package com.cwugamejammers.uno;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class PlayScreen implements Screen, GestureDetector.GestureListener{
    Uno game;

    private Texture redBackground;
    private Texture blueBackground;

    private Texture t;
    private Texture t2;
    private Texture t3;

    Button addCard;
    Button removeCard;

    Button card1;
    static ArrayList<Button> cardList;

    Music song1;
    Music song2;
    Music song3;
    Music song4;
    Music song5;
    ArrayList<Music> musicList;

    private static ArrayList<ArrayList<Texture>> textureList;

    public PlayScreen(Uno game) {
        this.game = game;

        redBackground = new Texture("RedBackground.png");
        blueBackground = new Texture("BlueBackground.png");

        //TO BE REMOVED TEST PURPOSES
        t = new Texture("TestCard.png");
        t2 = new Texture("cat.jpg");
        t3 = new Texture("upcat.jpg");
        addCard = new Button(t2, Gdx.graphics.getWidth() - 250, Gdx.graphics.getHeight() - 250, 250, 250);
        removeCard = new Button(t3, 0, Gdx.graphics.getHeight() - 250,250, 250);

        song1 = Gdx.audio.newMusic(Gdx.files.internal("music/AndSoItBegins.mp3"));
        song2 = Gdx.audio.newMusic(Gdx.files.internal("music/Branch.mp3"));
        song3 = Gdx.audio.newMusic(Gdx.files.internal("music/Octilary.mp3"));
        song4 = Gdx.audio.newMusic(Gdx.files.internal("music/Onion.mp3"));
        song5 = Gdx.audio.newMusic(Gdx.files.internal("music/Portrait.mp3"));

        musicList = new ArrayList<Music>();
        musicList.add(song1);
        musicList.add(song2);
        musicList.add(song3);
        musicList.add(song4);
        musicList.add(song5);
        song1.setOnCompletionListener(a -> {
            song2.play();
        });
        song2.setOnCompletionListener(a -> {
            song3.play();
        });
        song3.setOnCompletionListener(a -> {
            song4.play();
        });
        song4.setOnCompletionListener(a -> {
            song5.play();
        });
        song5.setOnCompletionListener(a -> {
            song1.play();
        });

        song1.play();
        //Creates 5 separate ArrayLists for the 5 texture lists.
        textureList = new ArrayList<ArrayList<Texture>>(5);

        for(int i = 1; i < 13; i++)
        {
            //red
            Texture asset = new Texture("cards/R" + i + ".png");
            //blue
            Texture asset2 = new Texture("cards/B" + i +".png");
            //yellow
            Texture asset3 = new Texture("cards/Y" + i + ".png");
            //greebn
            Texture asset4 = new Texture("cards/G" + i +".png");

            textureList.get(0).add(asset);
            textureList.get(1).add(asset2);
            textureList.get(2).add(asset3);
            textureList.get(3).add(asset4);
        }

        Texture wild1 = new Texture("cards/W" + 13 + ".png");
        Texture wild2 = new Texture("cards/W" + 14 + ".png");
        //wild
        textureList.get(4).add(wild1);
        textureList.get(4).add(wild2);



        //TEST STUFF, WILL BE GONE LATER
        card1 = new Button(t, 0, 0, Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/3);
        cardList = new ArrayList<Button>();
        cardList.add(card1);


    }

    public ArrayList<ArrayList<Texture>> getTextureList()
    {
        return textureList;
    }

    public static void createCard(Card card)
    {
        int arrayNumber = 0;
        int indexNumber = card.getNumber();

        if(card.getColor() == "Red")
        {
            arrayNumber = 0;
        }

        if(card.getColor() == "Blue")
        {
            arrayNumber = 1;
        }

        if(card.getColor() == "Yellow")
        {
            arrayNumber = 2;
        }

        if(card.getColor() == "Green")
        {
            arrayNumber = 3;
        }

        Button cardButton = new Button(textureList.get(arrayNumber).get(indexNumber), 0, 0, Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 3);
        cardList.add(cardButton);

    }
    @Override
    public void show() {
        // Prepare your screen here.
    }

    public void update(float dt){
        //Sets hand bounds based on the size of the hand
        if (cardList.size() <= 0){

        }
        else if (cardList.size() < 2){
            cardList.get(0).setBounds(0, 0, Gdx.graphics.getWidth() - card1.rect.width, Gdx.graphics.getHeight());
        }
        else{
            cardList.get(0).setBounds(-card1.rect.width*(cardList.size() - 1), 0, Gdx.graphics.getWidth() - card1.rect.width, Gdx.graphics.getHeight());

            /////ALSO PART OF THE FUNCTION THAT NEEDS TO BE MADE
            for (int i = 1; i < cardList.size(); i++){
                cardList.get(i).reposition(cardList.get(i-1).getx() + cardList.get(i).rect.width, cardList.get(i).rect.y);
            }
        }

        //Calls the pan function to drag the cards around
        pan(Gdx.input.getX(), Gdx.input.getY(), Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
    }

    @Override
    public void render(float delta) {
        update(delta);

        //Sets a color for the screen to be cleared with
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        //Replaces everything from the previous frame with a solid color specified in the previous line
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Begins the spritebatch
        game.batch.begin();

        //TO BE REMOVED ADDS A CARD FOR TESTING
        if (Gdx.input.justTouched()) {
            if (addCard.collision(Gdx.input.getX(), Gdx.input.getY())) {
                cardList.add(new Button(t, cardList.get(cardList.size() - 1).rect.x + cardList.get(cardList.size() - 1).rect.width, 0, Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 3));
            }

            if (removeCard.collision(Gdx.input.getX(), Gdx.input.getY())) {
                if(cardList.size() > 0) {
                    cardList.remove(0);

                    ////MAKE A FUNCTION OUT OF THIS
                    cardList.get(0).reposition(0, 0);
                    for (int i = 1; i < cardList.size(); i++){
                        cardList.get(i).reposition(cardList.get(i-1).getx() + cardList.get(i).rect.width, cardList.get(i).rect.y);
                    }
                }
            }
        }




        //depending on the theme draw a specific color background
        if (game.colortheme == Uno.COLORTHEME.RED){
            game.batch.draw(redBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

        if (game.colortheme == Uno.COLORTHEME.BLUE){
            game.batch.draw(blueBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

        //Draws the cards
        for (Button b : cardList){
            b.draw(game.batch);
        }

        //TO BE REMOVED TEST PURPOSES ONLY
        addCard.draw(game.batch);
        removeCard.draw(game.batch);


        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }


    //Frees up memory from assets no longer used
    @Override
    public void dispose() {
        redBackground.dispose();
        blueBackground.dispose();

    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button){
        return true;
    }


    @Override
    public boolean tap(float x, float y, int count, int button){
        return true;
    }

    @Override
    public boolean longPress(float x, float y){
        return true;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button){
        return true;
    }


    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY){

        //For loop to move all the cards
        for (int i = 0; i < cardList.size(); i++){
            if (i == 0){
                //if the card will be in a position less than the minimum allowed x value break out and stop moving
                if (cardList.get(i).rect.x + deltaX < cardList.get(i).bounds.minX){
                    break;
                }

                //if the card will be in a position greater than the maximum allowed x value break out and stop moving
                if (cardList.get(i).rect.x + deltaX > cardList.get(i).bounds.maxX){
                    break;
                }
            }

            //Moves the cards
            cardList.get(i).reposition(cardList.get(i).rect.x + deltaX, cardList.get(i).rect.y);
        }

        return true;
    }


    @Override
    public boolean panStop(float x, float y, int a, int b){

        return true;
    }

    @Override
    public boolean zoom(float initialDistance, float distance){
        return true;
    }


    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2){
        return true;
    }

    @Override
    public void pinchStop(){

    }
}
