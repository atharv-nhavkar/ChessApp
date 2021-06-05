package com.example.chessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class gametryscreen extends AppCompatActivity {


    ImageView drawoffer,drawaccept ,resign;
    int offer;


    TextView turntextview ,mynameview,opponantnameview,mytime,opponanttime;
    int [][] legalMoves = new int[8][8];
    int i=0,j=0;

    int ctime,otime;
    String myname,opponantname="mahitnahtapi";
    int activeplayer ; // 1-->white to play ,   0-->black to play
    // find a way to intialize it before starting the game ;
    // 1 --> white is represent in captial letters
    // 0 --> black is represented in capital letters
    int lasti=-1,lastj=-1;
    ImageView lastview;
    boolean lasttap=false;
    boolean allowmove ;
    boolean firstmove ;
    boolean promotion;
    int plasti=-1,plastj=-1;
    int pi=-1,pj=-1;
    int movecount ;
    int fullmove ; // for the ealuation pourpse

    Map<String, Integer> repetation  = new HashMap<>();

    chesstimer ct=null,ot=null; // ct --> challenger time ot --> opponant time

    DatabaseReference ref ;
    char[][] board = {

            //   '0','1','2','3','4','5','6','7'
                {'r','n','b','q','k','b','n','r'},// 0
                {'p','p','p','p','p','p','p','p'},// 1
                {'.','.','.','.','.','.','.','.'},// 2
                {'.','.','.','.','.','.','.','.'},// 3
                {'.','.','.','.','.','.','.','.'},// 4
                {'.','.','.','.','.','.','.','.'},// 5
                {'P','P','P','P','P','P','P','P'},// 6
                {'R','N','B','Q','K','B','N','R'},// 7
        };
    Chess ch = new Chess();


    // conventions --> player who is playing will always be represented by CAPITAL LETTERS



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.gametryscreen);
        activeplayer = getIntent().getExtras().getInt("activeplayer");
        myname = getIntent().getExtras().getString("myname");
        // take the value of activeplayer from intent or other ideas.
        String gamelink = getIntent().getExtras().getString("link");
        ref = FirebaseDatabase.getInstance().getReference().child("games").child(gamelink);

        promotion = true;
        firstmove = false;
        movecount = 0;
        fullmove =-1;
        drawoffer = findViewById(R.id.drawoffer);
        drawaccept = findViewById(R.id.drawaccept);
        resign = findViewById(R.id.resign);
        offer = 0;


        mynameview = (TextView)findViewById(R.id.mynametextview);
        mynameview.setText(myname);

        opponantnameview = (TextView)findViewById(R.id.opponantnametextview);

        mytime = (TextView) findViewById(R.id.mytime);
        opponanttime = (TextView) findViewById(R.id.opponanttime);

//        ot = new chesstimer(null,opponanttime,0);
//        ct = new chesstimer(null,mytime,0);

        Toast.makeText(getApplicationContext(), "game link is : " + gamelink , Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), "activeplayer is : " + activeplayer , Toast.LENGTH_SHORT).show();
        turntextview = (TextView)findViewById(R.id.whoseturn);
        if(activeplayer==1){
            String FEN = whiteboardtoFEN();
            Game game = new Game(FEN,myname,"mahitnahi",0,0,0,false,true,1);
            ref.setValue(game);
            allowmove = true;
        }

        ch.setBoard(board);
        setscreenfromboard();

        ValueEventListener FENlistner = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Toast.makeText(getApplicationContext(), "Onchange called ", Toast.LENGTH_SHORT).show();
                Game game = dataSnapshot.getValue(Game.class);

                if(activeplayer==1 && game.getOpponant()!=opponantname)
                {
                    opponantname = game.getOpponant();
                    opponantnameview.setText(opponantname);
                }
                else if(activeplayer == 0 && game.getChalleger()!=opponantname){
                    opponantname = game.getChalleger();
                    opponantnameview.setText(opponantname);
                }

                if(game!=null)
                {
                    if(game.isDrawn()){
                        Toast.makeText(gametryscreen.this, "The game has been a draw", Toast.LENGTH_SHORT).show();
                        allowmove = false;
                        //incrementcounter(2);
                        return;
                    }

                    offer = game.getOffer();
                    if(offer!=0){
                        // do something MF
                        if(offer==1){
                            // white resigns
                            if(activeplayer==1) {
                                Toast.makeText(gametryscreen.this, "You won the game", Toast.LENGTH_SHORT).show();
                                //incrementcounter(1);
                            }
                            else if(activeplayer==0){
                                Toast.makeText(gametryscreen.this, "You won the game", Toast.LENGTH_SHORT).show();
                                //incrementcounter(0);
                            }
                            allowmove = false;
                            return;
                        }
                        else if(offer==2){
                            // black resigns
                                if(activeplayer==0) {
                                    Toast.makeText(gametryscreen.this, "You won the game", Toast.LENGTH_SHORT).show();
                                    //incrementcounter(1);
                                }
                                else if(activeplayer==1){
                                    Toast.makeText(gametryscreen.this, "You won the game", Toast.LENGTH_SHORT).show();
                                    //incrementcounter(0);
                                }
                                allowmove = false;
                                return;
                        }
                        else if(offer==3){
                            // white offers draw
                            if(activeplayer==0) {
                                drawoffer.setVisibility(View.INVISIBLE);
                                drawaccept.setVisibility(View.VISIBLE);
                                drawaccept.setImageResource(R.drawable.drawaccepted);
                                Toast.makeText(gametryscreen.this, " Draw has been Offred", Toast.LENGTH_SHORT).show();

                            }
                            else if(activeplayer==1){
                                Toast.makeText(gametryscreen.this, "You offred a draw", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if(offer == 4){
                            if(activeplayer==1) {
                                // black offers a draw
                                drawoffer.setVisibility(View.INVISIBLE);
                                drawaccept.setVisibility(View.VISIBLE);
                                Toast.makeText(gametryscreen.this, " Draw has been Offred", Toast.LENGTH_SHORT).show();

                            }
                            else if(activeplayer==0){
                                Toast.makeText(gametryscreen.this, "You offred a draw", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }


                    String FEN = game.getFEN();
                    int turn = game.getTurn();
                    //Toast.makeText(GameScreen.this, "FEN is  " + FEN, Toast.LENGTH_SHORT).show();


                    if(turn == 1)
                        turntextview.setText("white to play");
                    else if (turn == 0)
                        turntextview.setText("black to play ");

                    if(turn == 1){
                        fullmove ++;
                    }

                    if(activeplayer==1) {
                        FENtowhiteboard(board, FEN);
                    }
                    else if(activeplayer == 0)
                        FENtoblackboard(board,FEN);

                    if(activeplayer == turn )
                        allowmove = true;
                    else
                        allowmove = false ;

                    ch.setBoard(board);
                    setscreenfromboard();

                    if(repetation.containsKey(FEN)){
                        int val = repetation.get(FEN);
                        if(val == 2){
                            // The game is a draw ;
                            Game ngame = new Game(FEN,opponantname,myname,0,0,offer,true,false,1);
                            ref.setValue(ngame);
                        }
                        else {
                            repetation.put(FEN,val+1);
                        }
                    }
                    else{
                        if(FEN=="rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR"){
                            // dont insert coz initial positotion
                        }
                        else {
                            repetation.put(FEN,1);
                        }
                    }

                    // rethink on this logic
                    if(ch.isCheck(board,1)){
                        if(ch.ismate(board,1)) {
                            Toast.makeText(getApplicationContext(), "you lost the game", Toast.LENGTH_SHORT).show();
                            //incrementcounter(0);

                        }else{
                            markcheckking(1);
                        }
                    }
                    if(ch.isCheck(board,0)){
                        if(ch.ismate(board,0)) {
                            Toast.makeText(getApplicationContext(), "You won the game !!! ", Toast.LENGTH_SHORT).show();
                            //incrementcounter(1);
                        }else{
                            markcheckking(0);
                        }
                    }
                    // rethink on this logic

//
//                    int challengertimer = game.getChalengerTimer();
//                    ctime = challengertimer;
//                    if(challengertimer<0)
//                    {
//                        // dont to anything
//                    }
//                    else if(challengertimer >0)
//                    {
//                        if(turn == 1){
//                            ct.settimer(challengertimer);
////                                if(ct != null) {
////                                    ct.cancel();
////                                }
////                                    ct = new CountDownTimer(challengertimer, 1000) {
////                                public void onTick(long millisUntilFinished) {
////                                    // Used for formatting digit to be in 2 digits only
////                                    NumberFormat f = new DecimalFormat("00");
////                                    long hour = (millisUntilFinished / 3600000) % 24;
////                                    long min = (millisUntilFinished / 60000) % 60;
////                                    long sec = (millisUntilFinished / 1000) % 60;
////                                    ctime--;
////                                    if(activeplayer==1)
////                                        mytime.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
////                                    else
////                                        opponanttime.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
////                                }
////                                // When the task is over it will print 00:00:00 there
////                                public void onFinish() {
////                                    if(activeplayer==1)
////                                        mytime.setText("00:00:00");
////                                    else
////                                        opponanttime.setText("00:00:00");
////                                }
////                            }.start();
//
//                        }
//                        else{
//                            ct.setRemtime(challengertimer);
//                            NumberFormat f = new DecimalFormat("00");
//                            long hour = (challengertimer / 3600000) % 24;
//                            long min = (challengertimer / 60000) % 60;
//                            long sec = (challengertimer / 1000) % 60;
//                            if(activeplayer==1)
//                                mytime.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
//                            else
//                                opponanttime.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
//
//                        }
//
//
//                    }
//                    int opponanattimer = game.getOpponantTimer();
//                    otime = opponanattimer;
//                    if(opponanattimer<0)
//                    {
//                        // dont to anything
//                    }
//                    else if(opponanattimer >0)
//                    {
//                        ot.settimer(opponanattimer);
////                        if(turn == 0){
////                                if(ot != null)
////                                    ot.cancel();
////                                ot = new CountDownTimer(opponanattimer, 1000) {
////                                public void onTick(long millisUntilFinished) {
////                                    // Used for formatting digit to be in 2 digits only
////                                    NumberFormat f = new DecimalFormat("00");
////                                    long hour = (millisUntilFinished / 3600000) % 24;
////                                    long min = (millisUntilFinished / 60000) % 60;
////                                    long sec = (millisUntilFinished / 1000) % 60;
////                                    otime--;
////                                    if(activeplayer==1)
////                                        opponanttime.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
////                                    else
////                                        mytime.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
////                                }
////                                // When the task is over it will print 00:00:00 there
////                                public void onFinish() {
////                                    if(activeplayer==1)
////                                        opponanttime.setText("00:00:00");
////                                    else
////                                        mytime.setText("00:00:00");
////                                }
////                            }.start();
//                    }
//                    else{
//                            ot.setRemtime(opponanattimer);
//                            NumberFormat f = new DecimalFormat("00");
//                            long hour = (challengertimer / 3600000) % 24;
//                            long min = (challengertimer / 60000) % 60;
//                            long sec = (challengertimer / 1000) % 60;
//                            if(activeplayer==1)
//                                opponanttime.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
//                            else
//                                mytime.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
//                        }

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Some error occured while fetching the data from RTDB" , Toast.LENGTH_SHORT).show();
                }

                // ..
            };

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Data Base error " , Toast.LENGTH_SHORT).show();
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        ref.addValueEventListener(FENlistner);

        drawoffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activeplayer==1) {
                    ref.child("offer").setValue(3);
                }
                else if(activeplayer==0){
                    ref.child("offer").setValue(4);
                }
            }
        });

        drawaccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.child("drawn").setValue(true);
            }
        });

        resign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activeplayer==1) {
                    ref.child("offer").setValue(1);
                }
                else if(activeplayer==0){
                    ref.child("offer").setValue(2);
                }
            }
        });



    }



    void cpeval(String FEN,char turn){
        char[][] b = new char[8][8];
        String cr="";
        FENtowhiteboard(board,FEN);
        if(board[7][4]=='K' && board[7][7]=='R'){
            cr+='K';
        }
        if(board[7][4]=='K' && board[7][0]=='R'){
            cr+='Q';
        }

        if(board[0][4]=='k' && board[7][7]=='r'){
            cr+='k';
        }
        if(board[7][4]=='k' && board[7][0]=='r'){
            cr+='q';
        }
        if(cr=="")
            cr = "-";
        String finalFEN = FEN + " "+ turn + " "+cr +" - "+movecount+ " " + fullmove;


    }




    void incrementcounter(int mijinklo){
        if(mijinklo==1){
            DatabaseReference userref = FirebaseDatabase.getInstance().getReference().child("users").child(myname);



            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get Post object and use the values to update the UI
                    User user = dataSnapshot.getValue(User.class);
                    user.setWins(user.getWins()+1);
                    userref.setValue(user);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                    Toast.makeText(getApplicationContext(), "Win Cannot cannot be updated on database due to " + databaseError.getMessage().toString() , Toast.LENGTH_SHORT).show();

                }
            };
            userref.addValueEventListener(postListener);


        }
        else if(mijinklo==2){
            // drawn
            DatabaseReference userref = FirebaseDatabase.getInstance().getReference().child("users").child(myname);
            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get Post object and use the values to update the UI
                    User user = dataSnapshot.getValue(User.class);
                    user.setDraws(user.getDraws()+1);
                    userref.setValue(user);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                    Toast.makeText(getApplicationContext(), "Draw Cannot cannot be updated on database due to " + databaseError.getMessage().toString() , Toast.LENGTH_SHORT).show();

                }
            };
            userref.addValueEventListener(postListener);

        }
        else{
            DatabaseReference userref = FirebaseDatabase.getInstance().getReference().child("users").child(myname);
            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get Post object and use the values to update the UI
                    User user = dataSnapshot.getValue(User.class);
                    user.setLoss(user.getLoss()+1);
                    userref.setValue(user);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                    Toast.makeText(getApplicationContext(), "Loss Cannot cannot be updated on database due to " + databaseError.getMessage().toString() , Toast.LENGTH_SHORT).show();

                }
            };
            userref.addValueEventListener(postListener);
        }

    }


    void setscreenfromboard(){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                ImageView square= giveimageview(i,j);
                if(activeplayer==1){
                    putinview(board[i][j],square);
                }
                else if(activeplayer==0){
                    putinviewforblack(board[i][j],square);
                }
            }
        }
    }

    String whiteboardtoFEN(){
        String s ="";
        int cnt=0;
        for(int i = 0;i<8;i++){
            cnt=0;
            for(int j=0;j<8;j++){
                if(board[i][j]>'a' && board[i][j]<'z'){
                    if(cnt>0)
                        s = s + (char)(cnt + '0');
                    s = s + board[i][j];
                    cnt = 0;
                }
                else if(board[i][j]>'A' && board[i][j]<'Z') {
                    if(cnt>0)
                        s = s + (char)(cnt + '0');
                    s = s + board[i][j];
                    cnt=0;
                }
                else{
                    cnt++;
                }
            }
            if(cnt>0)
            {
                System.out.println(cnt);
                if(cnt>0)
                    s = s + (char)(cnt + '0');


            }
            if(i<7)
            {
                s = s.concat("/");
            }
        }
        return s;
    }

    void FENtowhiteboard(char [][]board,String FEN){
        int i=0,j=0;
        for(char it : FEN.toCharArray()){
            if(it == '/')
            {
                i++;
                j=0;
            }
            if(it > '0' && it < '9')
            {
                int f=it-'0';
                for(int k=0;k<f;k++){
                    if(j<8)
                        board[i][j++]='.';
                }
            }
            if(it > 'A' && it < 'Z')
                board[i][j++]=it;
            else if(it > 'a' && it < 'z')
                board[i][j++]=it;
        }
    }



    String blackboardtoFEN(){
        String s ="";
        int cnt=0;
        for(int i = 7;i>=0;i--){
            cnt=0;
            for(int j=7;j>=0;j--){
                if(board[i][j]>'a' && board[i][j]<'z'){
                    if(cnt>0)
                        s = s + (char)(cnt + '0');
                    s = s + (Character.toUpperCase(board[i][j]));
                    cnt = 0;
                }
                else if(board[i][j]>'A' && board[i][j]<'Z') {
                    if(cnt>0)
                        s = s + (char)(cnt + '0');
                    s = s + (Character.toLowerCase(board[i][j]));
                    cnt=0;
                }
                else{
                    cnt++;
                }
            }
            if(cnt>0)
            {
                System.out.println(cnt);
                if(cnt>0)
                    s = s + (char)(cnt + '0');


            }
            if(i>0)
            {
                s = s.concat("/");
            }
        }
        return s;
    }


    void FENtoblackboard(char [][]board,String FEN){
        int i=7,j=7;
        for(char it : FEN.toCharArray()){
            if(it == '/')
            {
                i--;
                j=7;
            }
            if(it > '0' && it < '9')
            {
                int f=it-'0';
                for(int k=0;k<f;k++){
                    if(j>=0)
                        board[i][j--]='.';
                }
            }
            if(it > 'A' && it < 'Z')
                board[i][j--]=(Character.toLowerCase(it));
            else if(it > 'a' && it < 'z')
                board[i][j--]=(Character.toUpperCase(it));
        }
    }



    public int getj(char[] a){
        int j=-1;
        switch(a[0]){
            case 'A':
                j=0;
                break;
            case 'B':
                j=1;
                break;
            case 'C':
                j=2;
                break;
            case 'D':
                j=3;
                break;
            case 'E':
                j=4;
                break;
            case 'F':
                j=5;
                break;
            case 'G':
                j=6;
                break;
            case 'H':
                j=7;
                break;
        }
        return j;
    }

    public int geti(char [] a){
        int i=-1;
        switch(a[1]){
            case '1':
                i=7;
                break;
            case '2':
                i=6;
                break;
            case '3':
                i=5;
                break;
            case '4':
                i=4;
                break;
            case '5':
                i=3;
                break;
            case '6':
                i=2;
                break;
            case '7':
                i=1;
                break;
            case '8':
                i=0;
                break;
        }
        return i;
    }

    public char getchar(int j)
    {
        char c='.';
        switch(j){
            case 0:
                c='A';
                break;
            case 1:
                c='B';
                break;
            case 2:
                c='C';
                break;
            case 3:
                c='D';
                break;
            case 4:
                c='E';
                break;
            case 5:
                c='F';
                break;
            case 6:
                c='G';
                break;
            case 7:
                c='H';
                break;
        }
        return c;
    }

    public char getnum(int i) {
        char c='.';
        switch(i){
            case 7:
                c='1';
                break;
            case 6:
                c='2';
                break;
            case 5:
                c='3';
                break;
            case 4:
                c='4';
                break;
            case 3:
                c='5';
                break;
            case 2:
                c='6';
                break;
            case 1:
                c='7';
                break;
            case 0:
                c='8';
                break;
        }
        return c;
    }

    void putpurpinview(char c, ImageView iv){
        switch(c){
            case 'Q':
                iv.setImageResource(R.drawable.wqueenpurp);
                break;
            case 'N':
                iv.setImageResource(R.drawable.wknightpurp);
                break;
            case 'B':
                iv.setImageResource(R.drawable.wbishoppurp);
                break;
            case 'R':
                iv.setImageResource(R.drawable.wrookpurp);
                break;
            case 'P':
                iv.setImageResource(R.drawable.wpawnpurp);
                break;
            case 'q':
                iv.setImageResource(R.drawable.bqueenpurp);
                break;
            case 'n':
                iv.setImageResource(R.drawable.bknightpurp);
                break;
            case 'b':
                iv.setImageResource(R.drawable.bbishoppurp);
                break;
            case 'r':
                iv.setImageResource(R.drawable.brookpurp);
                break;
            case 'p':
                iv.setImageResource(R.drawable.bpawnpurp);
                break;
            case '.':
                iv.setImageResource(R.drawable.legalmoveemptysquare);

        }
    }

    void putpurpinviewforblack(char c, ImageView iv){
        switch(c){
            case 'q':
                iv.setImageResource(R.drawable.wqueenpurp);
                break;
            case 'n':
                iv.setImageResource(R.drawable.wknightpurp);
                break;
            case 'b':
                iv.setImageResource(R.drawable.wbishoppurp);
                break;
            case 'r':
                iv.setImageResource(R.drawable.wrookpurp);
                break;
            case 'p':
                iv.setImageResource(R.drawable.wpawnpurp);
                break;
            case 'Q':
                iv.setImageResource(R.drawable.bqueenpurp);
                break;
            case 'N':
                iv.setImageResource(R.drawable.bknightpurp);
                break;
            case 'B':
                iv.setImageResource(R.drawable.bbishoppurp);
                break;
            case 'R':
                iv.setImageResource(R.drawable.brookpurp);
                break;
            case 'P':
                iv.setImageResource(R.drawable.bpawnpurp);
                break;
            case '.':
                iv.setImageResource(R.drawable.legalmoveemptysquare);

        }
    }


    void putinview(char c, ImageView iv){
        switch(c){
            case 'K':
                iv.setImageResource(R.drawable.wking);
                break;
            case 'Q':
                iv.setImageResource(R.drawable.wqueen);
                break;
            case 'N':
                iv.setImageResource(R.drawable.wknight);
                break;
            case 'B':
                iv.setImageResource(R.drawable.wbishop);
                break;
            case 'R':
                iv.setImageResource(R.drawable.wrook);
                break;
            case 'P':
                iv.setImageResource(R.drawable.wpawn);
                break;
            case 'k':
                iv.setImageResource(R.drawable.bking);
                break;
            case 'q':
                iv.setImageResource(R.drawable.bqueen);
                break;
            case 'n':
                iv.setImageResource(R.drawable.bknight);
                break;
            case 'b':
                iv.setImageResource(R.drawable.bbishop);
                break;
            case 'r':
                iv.setImageResource(R.drawable.brook);
                break;
            case 'p':
                iv.setImageResource(R.drawable.bpawn);
                break;
            case '.':
                iv.setImageResource(0);
        }
    }

    void putinviewforblack(char c, ImageView iv){
        switch(c){
            case 'k':
                iv.setImageResource(R.drawable.wking);
                break;
            case 'q':
                iv.setImageResource(R.drawable.wqueen);
                break;
            case 'n':
                iv.setImageResource(R.drawable.wknight);
                break;
            case 'b':
                iv.setImageResource(R.drawable.wbishop);
                break;
            case 'r':
                iv.setImageResource(R.drawable.wrook);
                break;
            case 'p':
                iv.setImageResource(R.drawable.wpawn);
                break;
            case 'K':
                iv.setImageResource(R.drawable.bking);
                break;
            case 'Q':
                iv.setImageResource(R.drawable.bqueen);
                break;
            case 'N':
                iv.setImageResource(R.drawable.bknight);
                break;
            case 'B':
                iv.setImageResource(R.drawable.bbishop);
                break;
            case 'R':
                iv.setImageResource(R.drawable.brook);
                break;
            case 'P':
                iv.setImageResource(R.drawable.bpawn);
                break;
            case '.':
                iv.setImageResource(0);
        }
    }

    ImageView giveimageview(int i,int j){
        ImageView retview=null;
        char rank = getnum(i);
        char file = getchar(j);
        switch(rank) {
            case '1':
                switch (file) {
                    case 'A':
                        retview = (ImageView) findViewById(R.id.A1);
                        break;
                    case 'B':
                        retview = (ImageView) findViewById(R.id.B1);
                        break;
                    case 'C':
                        retview = (ImageView) findViewById(R.id.C1);
                        break;
                    case 'D':
                        retview = (ImageView) findViewById(R.id.D1);
                        break;
                    case 'E':
                        retview = (ImageView) findViewById(R.id.E1);
                        break;
                    case 'F':
                        retview = (ImageView) findViewById(R.id.F1);
                        break;
                    case 'G':
                        retview = (ImageView) findViewById(R.id.G1);
                        break;
                    case 'H':
                        retview = (ImageView) findViewById(R.id.H1);
                        break;
                }
                break;
            case '2':
                switch (file) {
                    case 'A':
                        retview = (ImageView) findViewById(R.id.A2);
                        break;
                    case 'B':
                        retview = (ImageView) findViewById(R.id.B2);
                        break;
                    case 'C':
                        retview = (ImageView) findViewById(R.id.C2);
                        break;
                    case 'D':
                        retview = (ImageView) findViewById(R.id.D2);
                        break;
                    case 'E':
                        retview = (ImageView) findViewById(R.id.E2);
                        break;
                    case 'F':
                        retview = (ImageView) findViewById(R.id.F2);
                        break;
                    case 'G':
                        retview = (ImageView) findViewById(R.id.G2);
                        break;
                    case 'H':
                        retview = (ImageView) findViewById(R.id.H2);
                        break;
                }
                break;
            case '3':
                switch (file) {
                    case 'A':
                        retview = (ImageView) findViewById(R.id.A3);
                        break;
                    case 'B':
                        retview = (ImageView) findViewById(R.id.B3);
                        break;
                    case 'C':
                        retview = (ImageView) findViewById(R.id.C3);
                        break;
                    case 'D':
                        retview = (ImageView) findViewById(R.id.D3);
                        break;
                    case 'E':
                        retview = (ImageView) findViewById(R.id.E3);
                        break;
                    case 'F':
                        retview = (ImageView) findViewById(R.id.F3);
                        break;
                    case 'G':
                        retview = (ImageView) findViewById(R.id.G3);
                        break;
                    case 'H':
                        retview = (ImageView) findViewById(R.id.H3);
                        break;
                }
                break;
            case '4':
                switch (file) {
                    case 'A':
                        retview = (ImageView) findViewById(R.id.A4);
                        break;
                    case 'B':
                        retview = (ImageView) findViewById(R.id.B4);
                        break;
                    case 'C':
                        retview = (ImageView) findViewById(R.id.C4);
                        break;
                    case 'D':
                        retview = (ImageView) findViewById(R.id.D4);
                        break;
                    case 'E':
                        retview = (ImageView) findViewById(R.id.E4);
                        break;
                    case 'F':
                        retview = (ImageView) findViewById(R.id.F4);
                        break;
                    case 'G':
                        retview = (ImageView) findViewById(R.id.G4);
                        break;
                    case 'H':
                        retview = (ImageView) findViewById(R.id.H4);
                        break;
                }
                break;
            case '5':
                switch (file) {
                    case 'A':
                        retview = (ImageView) findViewById(R.id.A5);
                        break;
                    case 'B':
                        retview = (ImageView) findViewById(R.id.B5);
                        break;
                    case 'C':
                        retview = (ImageView) findViewById(R.id.C5);
                        break;
                    case 'D':
                        retview = (ImageView) findViewById(R.id.D5);
                        break;
                    case 'E':
                        retview = (ImageView) findViewById(R.id.E5);
                        break;
                    case 'F':
                        retview = (ImageView) findViewById(R.id.F5);
                        break;
                    case 'G':
                        retview = (ImageView) findViewById(R.id.G5);
                        break;
                    case 'H':
                        retview = (ImageView) findViewById(R.id.H5);
                        break;
                }
                break;
            case '6':
                switch (file) {
                    case 'A':
                        retview = (ImageView) findViewById(R.id.A6);
                        break;
                    case 'B':
                        retview = (ImageView) findViewById(R.id.B6);
                        break;
                    case 'C':
                        retview = (ImageView) findViewById(R.id.C6);
                        break;
                    case 'D':
                        retview = (ImageView) findViewById(R.id.D6);
                        break;
                    case 'E':
                        retview = (ImageView) findViewById(R.id.E6);
                        break;
                    case 'F':
                        retview = (ImageView) findViewById(R.id.F6);
                        break;
                    case 'G':
                        retview = (ImageView) findViewById(R.id.G6);
                        break;
                    case 'H':
                        retview = (ImageView) findViewById(R.id.H6);
                        break;
                }
                break;
            case '7':
                switch (file) {
                    case 'A':
                        retview = (ImageView) findViewById(R.id.A7);
                        break;
                    case 'B':
                        retview = (ImageView) findViewById(R.id.B7);
                        break;
                    case 'C':
                        retview = (ImageView) findViewById(R.id.C7);
                        break;
                    case 'D':
                        retview = (ImageView) findViewById(R.id.D7);
                        break;
                    case 'E':
                        retview = (ImageView) findViewById(R.id.E7);
                        break;
                    case 'F':
                        retview = (ImageView) findViewById(R.id.F7);
                        break;
                    case 'G':
                        retview = (ImageView) findViewById(R.id.G7);
                        break;
                    case 'H':
                        retview = (ImageView) findViewById(R.id.H7);
                        break;
                }
                break;
            case '8':
                switch (file) {
                    case 'A':
                        retview = (ImageView) findViewById(R.id.A8);
                        break;
                    case 'B':
                        retview = (ImageView) findViewById(R.id.B8);
                        break;
                    case 'C':
                        retview = (ImageView) findViewById(R.id.C8);
                        break;
                    case 'D':
                        retview = (ImageView) findViewById(R.id.D8);
                        break;
                    case 'E':
                        retview = (ImageView) findViewById(R.id.E8);
                        break;
                    case 'F':
                        retview = (ImageView) findViewById(R.id.F8);
                        break;
                    case 'G':
                        retview = (ImageView) findViewById(R.id.G8);
                        break;
                    case 'H':
                        retview = (ImageView) findViewById(R.id.H8);
                        break;
                }
                break;
        }
        return retview;
    }

    void markvalidmoves(int i,int j){
        if(i==-1 || j ==-1 )
            return;
        ImageView sq= giveimageview(i,j);
        if(activeplayer==1)
            putpurpinview(board[i][j],sq);
        if(activeplayer==0)
            putpurpinviewforblack(board[i][j],sq);
        for(int u=0;u<8;u++) {
            for(int v=0;v<8;v++){
                if(legalMoves[u][v]==1){
                    ImageView square= giveimageview(u,v);
                    if(activeplayer==1)
                        putpurpinview(board[u][v],square);
                    if(activeplayer==0)
                        putpurpinviewforblack(board[u][v],square);
                }
                else if (legalMoves[u][v]>0){
                    ImageView square= giveimageview(u,v);
                    square.setImageResource(R.drawable.green);
                }
            }
        }
    }

    void unmarkvalidmoves(int i,int j){
        if(i==-1 || j ==-1 )
            return;
        ImageView sq= giveimageview(i,j);
        if(activeplayer==1)
            putinview(board[i][j],sq);
        if(activeplayer==0)
            putinviewforblack(board[i][j],sq);
        for(int u=0;u<8;u++) {
            for(int v=0;v<8;v++){
                if(legalMoves[u][v]==1){
                    ImageView square= giveimageview(u,v);
                    if(activeplayer==1)
                        putinview(board[u][v],square);
                    if(activeplayer==0)
                        putinviewforblack(board[u][v],square);
                    legalMoves[u][v]=0;
                }
            }

        }
    }


    void markcheckking(int color){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(color==1 ){
                    if(activeplayer==1 && board[i][j]=='K'){
                        ImageView square= giveimageview(i,j);
                        square.setImageResource(R.drawable.wkingred);
                    }
                    if(activeplayer==0 && board[i][j]=='K'){
                        ImageView square= giveimageview(i,j);
                        square.setImageResource(R.drawable.bkingred);
                    }
                }
                if(color==0){
                    if(activeplayer==1 && board[i][j]=='k'){
                        ImageView square= giveimageview(i,j);
                        square.setImageResource(R.drawable.bkingred);
                    }
                    if(activeplayer==0 && board[i][j]=='k'){
                        ImageView square= giveimageview(i,j);
                        square.setImageResource(R.drawable.wkingred);
                    }
                }
            }
        }
    }

    void unmarkcheckking(int color){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(color==1 && board[i][j]=='K'){
                    ImageView square= giveimageview(i,j);
                    square.setImageResource(R.drawable.wking);
                }
                if(color==0 && board[i][j]=='k'){
                    ImageView square= giveimageview(i,j);
                    square.setImageResource(R.drawable.bking);
                }
            }
        }
    }

    // complete it sometime later
    public void promot(View v){
        if(promotion== false){

            Toast.makeText(this, "No Promotion Allwed", Toast.LENGTH_SHORT).show();
            return;
        }
        ImageView iv = (ImageView)v;
        String s = (iv.getTag().toString());
        if(s=="N"){
            ch.promotionmove(plasti,plastj,pi,pj,'N',board);
            plastj=plasti=pi=pj=-1;
            promotion = false;
        }
        else if(s=="B"){
            ch.promotionmove(plasti,plastj,pi,pj,'B',board);
            plastj=plasti=pi=pj=-1;
            promotion = false;
        }
        else if(s=="R"){
            ch.promotionmove(plasti,plastj,pi,pj,'R',board);
            plastj=plasti=pi=pj=-1;
            promotion = false;
        }
        else if(s=="Q"){
            ch.promotionmove(plasti,plastj,pi,pj,'Q',board);
            plastj=plasti=pi=pj=-1;
            promotion = false;
        }
        if(activeplayer==1){
            String FEN = whiteboardtoFEN();

            if(firstmove==false) {
                firstmove = true;
                Game game = new Game(FEN, myname, opponantname, 900000, -1, offer,false,false,0);
                ref.setValue(game);
            }
            else{
                Game game;
                if(movecount==51)
                    game = new Game(FEN, myname, opponantname, 0, 0,offer, true,false,0);
                else
                    game = new Game(FEN, myname, opponantname, 0, 0, offer,false,false,0);
                ref.setValue(game);
            }
//                ct.canceltime();
//                ot.canceltime();
            // write in Realtime - database
        }
        else if(activeplayer==0){
//                ct.canceltime();
//                ot.canceltime();
            String FEN = blackboardtoFEN();
            if(firstmove == false){
                firstmove = true;
                Game game = new Game(FEN,opponantname,myname,ctime,900000,offer,false,false,1);
                ref.setValue(game);
            }
            else{

                Game game ;
                if(movecount==51)
                    game = new Game(FEN,opponantname,myname,0,0,offer,true,false,1);
                else
                    game = new Game(FEN,opponantname,myname,0,0,offer,false,false,1);
                ref.setValue(game);
            }
//                ct.canceltime();
//                ot.canceltime();
            // write in Realtime - database
        }
        lasttap=false;
    }



    public void tap(View v)
    {
        if(!allowmove)
            return;
        ImageView iv = (ImageView)v;
        String s = (iv.getTag().toString());
        char a[]=s.toCharArray();
        int i=geti(a),j=getj(a) ;// extract from android tag if A1 then 7,0;
        if(i==-1 || j==-1)
            ;// kahi tri gandlay
        if(lasttap && legalMoves[i][j]>0)
        {
            movecount++;
            if(board[lasti][lastj]=='p' || board[lasti][lastj]=='P'){
                repetation.clear();
            }

            if(board[i][j]>'a' && board[i][j]<'z'){
                repetation.clear();
            }
            else if(board[i][j]>'A' && board[i][j]<'Z'){
                repetation.clear();
            }

            if(legalMoves[i][j]==1)
                ch.makeMove(lasti,lastj,i,j,board);
            else if(legalMoves[i][j]==2)
                ch.castlinemove(lasti,lastj,i,j,board);
            if(legalMoves[i][j]==3){
                // show UI for promotion
                promotion = true;
                LinearLayout promotionbar = (LinearLayout)findViewById(R.id.promotionbar);
                promotionbar.setVisibility(View.VISIBLE);
                pi = i;pj=j;plasti = lasti;plastj=lastj;
                return;
            }
            //unmarkvalidmoves(lasti,lastj);
            //call make move function
            // make the move
//            char letter = getchar(lastj);
//            char rank = getnum(lasti);
//            char[] charr= {letter,rank};
//            String str= new String(charr);
//            putinview(board[i][j],iv);
//            lastview.setImageResource(0);
//            unmarkcheckking(activeplayer);

            if(activeplayer==1){
                String FEN = whiteboardtoFEN();

                if(firstmove==false) {
                    firstmove = true;
                    Game game = new Game(FEN, myname, opponantname, 900000, -1,offer, false,false,0);
                    ref.setValue(game);
                }
                else{
                    Game game;
                    if(movecount==51)
                        game = new Game(FEN, myname, opponantname, 0, 0, offer,true,false,0);
                    else
                        game = new Game(FEN, myname, opponantname, 0, 0, offer,false,false,0);
                    ref.setValue(game);
                }
//                ct.canceltime();
//                ot.canceltime();
                // write in Realtime - database
            }
            else if(activeplayer==0){
//                ct.canceltime();
//                ot.canceltime();
                String FEN = blackboardtoFEN();
                if(firstmove == false){
                    firstmove = true;
                    Game game = new Game(FEN,opponantname,myname,ctime,900000,offer,false,false,1);
                    ref.setValue(game);
                }
                else{

                    Game game ;
                    if(movecount==51)
                        game = new Game(FEN,opponantname,myname,0,0,offer,true,false,1);
                    else
                        game = new Game(FEN,opponantname,myname,0,0,offer,false,false,1);
                    ref.setValue(game);
                }
//                ct.canceltime();
//                ot.canceltime();
                // write in Realtime - database
            }
            lasttap=false;
            if(ch.isCheck(board, 0))
            {
                if(ch.ismate(board, 0)) {
                    TextView t = (TextView)findViewById(R.id.textView13);
                    String op = "You Won The GAME !!!!";
                    t.setText(op);
                    ;// show that active player has lost and make the restart game button visible

                }
            }
        }
        else
        {
            lasttap = false;
            if(ch.allpeices[i][j]!=null){
                if(ch.allpeices[i][j].getColor()==1)
                {
                    unmarkvalidmoves(lasti,lastj);
                    ch.validMoves(board, legalMoves, i, j);
                    lasti=i;
                    lastj=j;
                    lasttap=true;
                    lastview = iv;
                    // indicate on board all the 1s present on leagl move array
                    markvalidmoves(i,j);
                }
                else
                    ;// player has tapped the square of same color
            }
            else
                ;// tapped in an empty cell
        }

    }

}