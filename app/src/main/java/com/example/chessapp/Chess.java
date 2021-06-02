package com.example.chessapp;


// conventions for coardinates
// x--> always will say x direction i.e. board[][x] this will be the way of accessing
// y--> always will say y direction i.e. board[y][] this will be the way of accessing
// 1--> always being WHITE 0--> always being black .......P.S. not being racist

// write a ischeck which will check for check ..........P.S. dont take this as Sarcasm ;


class Chess {


    piece [][] allpeices= new piece[8][8];




    // put this all in the set_board function of android studio
    void setBoard(char[][] board)
    {

        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                if(board[i][j]!='.')
                {
                    char ch=board[i][j];
                    if(ch<='z' && ch>='a')
                    {
                        if(ch=='q')
                        {
                            allpeices[i][j] = new Queen(j, i, 0);

                        }
                        else if(ch=='r')
                        {
                            allpeices[i][j]=new Rook(j, i, 0);
                        }
                        else if(ch=='n')
                        {
                            allpeices[i][j]=new Knight(j, i, 0);
                        }
                        else if(ch=='b')
                        {
                            allpeices[i][j]= new Bishop(j, i, 0);

                        }
                        else if(ch=='p')
                        {
                            allpeices[i][j]=new Pawn(j,i,0);
                        }
                        else if(ch=='k')
                        {
                            allpeices[i][j]=new King(j, i,0);
                        }
                    }
                    else
                    {
                        if(ch=='Q')
                        {
                            allpeices[i][j] = new Queen(j, i, 1);
                        }
                        else if(ch=='R')
                        {
                            allpeices[i][j]=new Rook(j, i, 1);
                        }
                        else if(ch=='N')
                        {

                            allpeices[i][j]=new Knight(j, i, 1);
                        }
                        else if(ch=='B')
                        {
                            allpeices[i][j]= new Bishop(j, i, 1);
                        }
                        else if(ch=='P')
                        {
                            allpeices[i][j]=new Pawn(j,i,1);
                        }
                        else if(ch=='K')
                        {
                            allpeices[i][j]=new King(j, i,1);
                        }
                    }
                }
            }
        }
    }




    public boolean isCheck(char [][]board,int color)
    {
        int[][] boardControl = new int [8][8];
        if(color==1)
        {
            int kingposx=1;
            int kingposy=1;
            for(int i=0;i<8;i++)
            {
                for(int j=0;j<8;j++)
                {
                    if(allpeices[i][j]==null)
                        continue;
                    if(allpeices[i][j].getColor()==0)
                    {
                        allpeices[i][j].getControlledSquares(board, boardControl);
                    }
                    if(board[i][j]=='K')
                    {
                        kingposx=allpeices[i][j].getX();
                        kingposy=allpeices[i][j].getY();
                    }
                }
            }
            if(boardControl[kingposy][kingposx]==1)
                return true;
            else
                return false;
        }
        else
        {
            int kingposx=1;
            int kingposy=1;
            for(int i=0;i<8;i++)
            {
                for(int j=0;j<8;j++)
                {
                    if(allpeices[i][j]==null)
                        continue;
                    if(allpeices[i][j].getColor()==1)
                    {
                        //System.out.println("#"+board[i][j]);
                        allpeices[i][j].getControlledSquares(board, boardControl);
                    }
                    if(board[i][j]=='k')
                    {
                        kingposx=allpeices[i][j].getX();
                        kingposy=allpeices[i][j].getY();
                    }
                }
            }
            if(boardControl[kingposy][kingposx]==1)
                return true;
            else
                return false;
        }
    }

    public boolean ismate(char[][] board,int color)
    {
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                if(allpeices[i][j]==null)
                    continue;
                if(allpeices[i][j].getColor()==color)
                {
                    int [][] moves = new int[8][8];
                    for(int u=0;u<8;u++)
                    {
                        for(int v=0;v<8;v++)
                            moves[i][j]=0;
                    }
                    allpeices[i][j].getMoves(board, moves);
                    System.out.println(board[i][j]);
                    for(int u=0;u<8;u++)
                    {
                        for(int v=0;v<8;v++)
                        {
                            if(moves[u][v]==1)
                            {
                                char prevCharatuv=board[u][v];
                                char prevCharatij=board[i][j];
                                board[u][v]=board[i][j];
                                board[i][j]='.';
                                piece prevPeiceatuv = allpeices[u][v];
                                piece prevPeiceatij = allpeices[i][j];
                                allpeices[u][v]=allpeices[i][j];
                                allpeices[u][v].set(v,u,color);
                                allpeices[i][j]=null;
                                if(!isCheck(board, color))
                                {
                                    allpeices[i][j]=prevPeiceatij;
                                    if(allpeices[i][j]!=null)
                                        allpeices[i][j].set(j,i,color);
                                    allpeices[u][v]=prevPeiceatuv;
                                    if(allpeices[u][v]!=null)
                                        allpeices[u][v].set(v,u,allpeices[u][v].getColor());
                                    board[i][j]=prevCharatij;
                                    board[u][v]=prevCharatuv;

                                    return false;
                                }
                                allpeices[i][j]=prevPeiceatij;
                                if(allpeices[i][j]!=null)
                                    allpeices[i][j].set(j,i,color);
                                allpeices[u][v]=prevPeiceatuv;
                                if(allpeices[u][v]!=null)
                                    allpeices[u][v].set(v,u,allpeices[u][v].getColor());
                                board[i][j]=prevCharatij;
                                board[u][v]=prevCharatuv;

                            }
                        }
                    }
                }

            }
        }
        return true;
    }

    public void validMoves(char[][] board,int[][] legalMoves,int i,int j)
    {
        for(int u=0;u<8;u++)
        {
            for(int v=0;v<8;v++)
                legalMoves[u][v]=0;
        }
        if(allpeices[i][j]==null)
            return;
        int color=allpeices[i][j].getColor() ;
        allpeices[i][j].getMoves(board, legalMoves);
        for(int u=0;u<8;u++)
        {
            for(int v=0;v<8;v++)
            {
                if(legalMoves[u][v]==1)
                {
                    char prevCharatuv=board[u][v];
                    char prevCharatij=board[i][j];
                    board[u][v]=board[i][j];
                    board[i][j]='.';
                    piece prevPeiceatuv = allpeices[u][v];
                    piece prevPeiceatij = allpeices[i][j];
                    allpeices[u][v]=allpeices[i][j];
                    allpeices[u][v].set(v,u,color);
                    allpeices[i][j]=null;
                    if(isCheck(board, color))
                    {
                        allpeices[i][j]=prevPeiceatij;
                        if(allpeices[i][j]!=null)
                            allpeices[i][j].set(j,i,color);
                        allpeices[u][v]=prevPeiceatuv;
                        if(allpeices[u][v]!=null)
                            allpeices[u][v].set(v,u,allpeices[u][v].getColor());
                        board[i][j]=prevCharatij;
                        board[u][v]=prevCharatuv;
                        legalMoves[u][v]=0;
                    }
                    allpeices[i][j]=prevPeiceatij;
                    if(allpeices[i][j]!=null)
                        allpeices[i][j].set(j,i,color);
                    allpeices[u][v]=prevPeiceatuv;
                    if(allpeices[u][v]!=null)
                        allpeices[u][v].set(v,u,allpeices[u][v].getColor());
                    board[i][j]=prevCharatij;
                    board[u][v]=prevCharatuv;
                }
            }
        }
        // special castling case starts
        if(board[i][j]=='k' && i==0 && j==4 ){


            int[][] boardControl = new int [8][8];
            for(int u=0;u<8;u++)
            {
                for(int v=0;v<8;v++)
                {
                    if(allpeices[u][v]==null)
                        continue;
                    if(allpeices[u][v].getColor()==1)
                    {
                        allpeices[u][v].getControlledSquares(board, boardControl);
                    }
                }
            }
            if(board[i][j+1]=='.' && board[i][j+2]=='.' && boardControl[i][j+1]==0 && boardControl[i][j+2]==0 && board[i][7]=='r' ){
                legalMoves[i][j+2]=2; // castling special case
            }
            if(board[i][j-1]=='.' && board[i][j-2]=='.' && board[i][j-3]=='.' && boardControl[i][j-1]==0 && boardControl[i][j-2]==0 && boardControl[i][j-3]==0 && board[i][0]=='r'){
                legalMoves[i][j-2]=2; // castling special case
            }

        }
        else if (board[i][j]=='K' && i==7 && j==4 ){


            int[][] boardControl = new int [8][8];
            for(int u=0;u<8;u++)
            {
                for(int v=0;v<8;v++)
                {
                    if(allpeices[u][v]==null)
                        continue;
                    if(allpeices[u][v].getColor()==0)
                    {
                        allpeices[u][v].getControlledSquares(board, boardControl);
                    }
                }
            }
            if(board[i][j+1]=='.' && board[i][j+2]=='.' && boardControl[i][j+1]==0 && boardControl[i][j+2]==0 && board[i][7]=='R' ){
                legalMoves[i][j+2]=2; // castling special case
            }
            if(board[i][j-1]=='.' && board[i][j-2]=='.' && board[i][j-3]=='.' && boardControl[i][j-1]==0 && boardControl[i][j-2]==0 && boardControl[i][j-3]==0 && board[i][0]=='R'){
                legalMoves[i][j-2]=2; // castling special case
            }
        }
        else if (board[i][j]=='K' && i==7 && j==3 ){   // black player multiplayer


            int[][] boardControl = new int [8][8];
            for(int u=0;u<8;u++)
            {
                for(int v=0;v<8;v++)
                {
                    if(allpeices[u][v]==null)
                        continue;
                    if(allpeices[u][v].getColor()==0)
                    {
                        allpeices[u][v].getControlledSquares(board, boardControl);
                    }
                }
            }
            if(board[i][j-1]=='.' && board[i][j-2]=='.' && boardControl[i][j-1]==0 && boardControl[i][j-2]==0 && board[i][0]=='R' ){
                legalMoves[i][j-2]=2; // castling special case
            }
            if(board[i][j+1]=='.' && board[i][j+2]=='.' && board[i][j+3]=='.' && boardControl[i][j+1]==0 && boardControl[i][j+2]==0 && boardControl[i][j+3]==0 && board[i][7]=='R'){
                legalMoves[i][j+2]=2; // castling special case
            }
        }

        // special castling case ends

        // pawn double push starts
        if(board[i][j]=='P' && i==6 ){
            if(board[i-2][j]=='.' && board[i-1][j]=='.'){
                int u=i-2,v = j;
                char prevCharatuv=board[u][v];
                char prevCharatij=board[i][j];
                board[u][v]=board[i][j];
                board[i][j]='.';
                piece prevPeiceatuv = allpeices[u][v];
                piece prevPeiceatij = allpeices[i][j];
                allpeices[u][v]=allpeices[i][j];
                allpeices[u][v].set(v,u,color);
                allpeices[i][j]=null;
                if(isCheck(board, color))
                {
                    allpeices[i][j]=prevPeiceatij;
                    if(allpeices[i][j]!=null)
                        allpeices[i][j].set(j,i,color);
                    allpeices[u][v]=prevPeiceatuv;
                    if(allpeices[u][v]!=null)
                        allpeices[u][v].set(v,u,allpeices[u][v].getColor());
                    board[i][j]=prevCharatij;
                    board[u][v]=prevCharatuv;
                    legalMoves[u][v]=0;
                }
                else {
                    legalMoves[u][v]=1;
                    allpeices[i][j] = prevPeiceatij;
                    if (allpeices[i][j] != null)
                        allpeices[i][j].set(j, i, color);
                    allpeices[u][v] = prevPeiceatuv;
                    if (allpeices[u][v] != null)
                        allpeices[u][v].set(v, u, allpeices[u][v].getColor());
                    board[i][j] = prevCharatij;
                    board[u][v] = prevCharatuv;
                }
            }
        }

        if(board[i][j]=='p' && i==1 ){
            if(board[i+2][j]=='.' && board[i+1][j]=='.'){
                int u=i+2,v = j;
                char prevCharatuv=board[u][v];
                char prevCharatij=board[i][j];
                board[u][v]=board[i][j];
                board[i][j]='.';
                piece prevPeiceatuv = allpeices[u][v];
                piece prevPeiceatij = allpeices[i][j];
                allpeices[u][v]=allpeices[i][j];
                allpeices[u][v].set(v,u,color);
                allpeices[i][j]=null;
                if(isCheck(board, color))
                {
                    allpeices[i][j]=prevPeiceatij;
                    if(allpeices[i][j]!=null)
                        allpeices[i][j].set(j,i,color);
                    allpeices[u][v]=prevPeiceatuv;
                    if(allpeices[u][v]!=null)
                        allpeices[u][v].set(v,u,allpeices[u][v].getColor());
                    board[i][j]=prevCharatij;
                    board[u][v]=prevCharatuv;
                    legalMoves[u][v]=0;
                }
                else {
                    legalMoves[u][v]=1;
                    allpeices[i][j] = prevPeiceatij;
                    if (allpeices[i][j] != null)
                        allpeices[i][j].set(j, i, color);
                    allpeices[u][v] = prevPeiceatuv;
                    if (allpeices[u][v] != null)
                        allpeices[u][v].set(v, u, allpeices[u][v].getColor());
                    board[i][j] = prevCharatij;
                    board[u][v] = prevCharatuv;
                }
            }
        }


    }

    public void makeMove(int i,int j,int u,int v,char[][]board)// move peice from i,j to u,v
    {
        board[u][v]=board[i][j];
        board[i][j]='.';
        allpeices[u][v]=allpeices[i][j];
        allpeices[u][v].set(v,u,allpeices[i][j].getColor());
//        if(board[u][v]=='P'|| board[u][v]=='p'){
//            allpeices[u][v].setForpawn(false);
//        }
        allpeices[i][j]=null;
    }

    public void castlinemove(int i,int j,int u,int v,char[][]board)// move peice from i,j to u,v
    {
        makeMove(i,j, u, v,board);
        if(v==5 || v==6){
            makeMove(i,7,u,v-1,board);
        }
        else if(v==1 || v==2){
            makeMove(i,0,u,v+1,board);
        }

    }

    public void promotionmove(int i,int j,int u,int v,char ch,char[][]board)// move peice from i,j to u,v
    {
        board[u][v]=ch;
        board[i][j]='.';
        allpeices[u][v]=allpeices[i][j];
        if(ch =='N')
            allpeices[u][v]=new Knight(v,u,allpeices[i][j].getColor());
        else if(ch == 'B')
            allpeices[u][v]=new Bishop(v,u,allpeices[i][j].getColor());
        else if(ch == 'R')
            allpeices[u][v]=new Rook(v,u,allpeices[i][j].getColor());
        else if(ch == 'Q')
            allpeices[u][v]=new Queen(v,u,allpeices[i][j].getColor());
        allpeices[i][j]=null;
    }
}





abstract class piece{
    public int x;
    public int y;
    public int color;
    public boolean hasplayed;
    public void set(int x,int y,int color){
        this.x=x;
        this.y=y;
        this.color=color;
        hasplayed = false;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getColor()
    {
        return color;
    }

    public boolean isHasplayed() {
        return hasplayed;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setHasplayed(boolean hasplayed) {
        this.hasplayed = hasplayed;
    }

    abstract void getMoves(char[][] board, int [][] moves);
    abstract void getControlledSquares(char[][] board,int [][] boardControl);

}














class Pawn extends piece{

    public boolean virgin=true;

    public void setPawn(int x,int y,int color){
        this.x=x;
        this.y=y;
        this.color=color;
        virgin=true;
    }

    public Pawn(int x,int y,int color){
        this.x=x;
        this.y=y;
        this.color=color;
    }

    public  void firstmove(){
        virgin = false;
    }


    // public void get_moves()
    // {
    //     int pos1x=x,pos2x=x;
    //     int pos1y=y+1;// same x;
    //     if(virgin)
    //     {
    //         virgin=false;
    //         int pos2=y+2;// same y
    //     }
    // }
    public void getMoves(char[][] board,int[][] moves)
    {
        if(color==1)
        {
            if(board[y-1][x]=='.')
            {
                int pos1x=x;
                int pos1y=y-1;
                if(y-1==0)
                    moves[pos1y][pos1x]=3; // 3 denotes promotion spacial case
                else
                    moves[pos1y][pos1x]=1;

            }
            // apply code for on passent
            if((x+1<8 && x+1>=0) && (y-1<8 && y-1>=0))
            {
                int pos1x=x+1;
                int pos1y=y-1;
                char ch= board[pos1y][pos1x];
                if( (ch>='a') && (ch <='z')  ) {
                    if(pos1y==0)
                        moves[pos1y][pos1x] = 3; // 3 denotes promotion spacial case
                    else
                        moves[pos1y][pos1x] = 1;
                }
            }
            if((x-1<8 && x-1>=0) && (y-1<8 && y-1>=0))
            {
                int pos2x=x-1;
                int pos2y=y-1;
                char ch=board[pos2y][pos2x];
                if( (ch>='a') && (ch <='z')  ) {
                    if(pos2y==0)
                        moves[pos2y][pos2x] = 3; // 3 denotes promotion spacial case
                    else
                        moves[pos2y][pos2x] = 1;

                }
            }
        }
        else
        {
            if(board[y+1][x]=='.')
            {
                int pos1x=x;
                int pos1y=y+1;
                moves[pos1y][pos1x]=1;

            }
            // apply code for op passent
            if((x+1<8 && x+1>=0) && (y+1<8 && y+1>=0))
            {

                int pos1x=x+1;
                int pos1y=y+1;
                char ch= board[pos1y][pos1x];
                if( (ch>='A') && (ch <='Z')  ){
                    if(pos1y==7)
                        moves[pos1y][pos1x] = 3; // 3 denotes promotion spacial case
                    else
                        moves[pos1y][pos1x] = 1;
                }

            }
            if((x-1<8 && x-1>=0) && (y+1<8 && y+1>=0))
            {

                int pos2x=x-1;
                int pos2y=y+1;
                char ch=board[pos2y][pos2x];
                if( (ch>='A') && (ch <='Z')  ){
                    if(pos2y==7)
                        moves[pos2y][pos2x] = 3; // 3 denotes promotion spacial case
                    else
                        moves[pos2y][pos2x] = 1;

                }

            }
        }
    }

    public void getControlledSquares(char[][] board,int[][] boardControl){

        if(color==1)
        {
            if((x+1<8 && x+1>=0) && (y-1<8 && y-1>=0))
            {
                int pos1x=x+1;
                int pos1y=y-1;
                boardControl[pos1y][pos1x]=1;

            }
            if((x-1<8 && x-1>=0) && (y-1<8 && y-1>=0))
            {

                int pos2x=x-1;
                int pos2y=y-1;
                boardControl[pos2y][pos2x]=1;

            }
        }
        else
        {
            if((x+1<8 && x+1>=0) && (y+1<8 && y+1>=0))
            {

                int pos1x=x+1;
                int pos1y=y+1;
                boardControl[pos1y][pos1x]=1;


            }
            if((x-1<8 && x-1>=0) && (y+1<8 && y+1>=0))
            {

                int pos2x=x-1;
                int pos2y=y+1;
                boardControl[pos2y][pos2x]=1;

            }
        }
    }
}

class Knight extends piece{

    public void setKnight(int x,int y,int color){
        this.x=x;
        this.y=y;
        this.color=color;
    }


    public Knight(int x,int y,int color){
        this.x=x;
        this.y=y;
        this.color=color;
    }


    public void getMoves(char[][] board,int [][] moves)
    {
        int dir1[]={1,1,-1,-1};
        int dir2[]={1,-1,1,-1};
        for(int i=0;i<4;i++)
        {
            int pos1_x=x+(dir1[i]*2);
            int pos1_y=y+(dir2[i]*1);
            int pos2_x=x+(dir1[i]*1);
            int pos2_y=y+(dir2[i]*2);
            if((pos1_x>=0 && pos1_x<8)&&(pos1_y>=0 && pos1_y<8))
            {
                char ch=board[pos1_y][pos1_x]; // coz its convinient
                if(color==1)
                {
                    if((ch=='.') || ( (ch>='a') && (ch <='z') ) )
                        moves[pos1_y][pos1_x]=1;
                }
                else
                {
                    if((ch=='.') || ( (ch>='A') && (ch <='Z') ) )
                        moves[pos1_y][pos1_x]=1;
                }
            }
            if((pos2_x>=0 && pos2_x<8)&&(pos2_y>=0 && pos2_y<8))
            {
                char ch=board[pos2_y][pos2_x]; // coz its convinient
                if(color==1)
                {
                    if((ch=='.') || ( (ch>='a') && (ch <='z') ) )
                        moves[pos2_y][pos2_x]=1;
                }
                else
                {
                    if((ch=='.') || ( (ch>='A') && (ch <='Z') ) )
                        moves[pos2_y][pos2_x]=1;
                }
            }
        }
    }

    // public void get_legal_moves(char[][] board,int [][]legalMoves)
    // {
    //     char [][] new_board=board;
    //     new_board[y][x]='.';
    //     int dir1[]={1,1,-1,-1};
    //     int dir2[]={1,-1,1,-1};
    //     int tmpx=x;// for undoing of the moves
    //     int tmpy=y;
    //     for(int i=0;i<4;i++)
    //     {
    //         int pos1_x=x+(dir1[i]*2);
    //         int pos1_y=y+(dir2[i]*1);
    //         int pos2_x=x+(dir1[i]*1);
    //         int pos2_y=y+(dir2[i]*2);
    //         if((pos1_x>=0 && pos1_x<8)&&(pos1_y>=0 && pos1_y<8))
    //         {
    //             x=pos1_x;
    //             y=pos1_y;
    //             if(color==1)
    //                 new_board[pos1_y][pos1_x]='N';
    //             else
    //                 new_board[pos1_y][pos1_x]='n';
    //         }
    //         if((pos2_x>=0 && pos2_x<8)&&(pos2_y>=0 && pos2_y<8))
    //            // boardControl[pos2_y][pos2_x]=1;// include pos2_x and pos2_y
    //     }
    // }

    public void getControlledSquares(char [][]board,int [][]boardControl)
    {
        int dir1[]={1,1,-1,-1};
        int dir2[]={1,-1,1,-1};
        for(int i=0;i<4;i++)
        {
            int pos1_x=x+(dir1[i]*2);
            int pos1_y=y+(dir2[i]*1);
            int pos2_x=x+(dir1[i]*1);
            int pos2_y=y+(dir2[i]*2);
            if((pos1_x>=0 && pos1_x<8)&&(pos1_y>=0 && pos1_y<8))
            {
                boardControl[pos1_y][pos1_x]=1;// include pos1_x and pos1_y
            }
            if((pos2_x>=0 && pos2_x<8)&&(pos2_y>=0 && pos2_y<8))
                boardControl[pos2_y][pos2_x]=1;// include pos2_x and pos2_y
        }
    }


}

class Bishop extends piece{

    public Bishop(int x,int y,int color){
        this.x=x;
        this.y=y;
        this.color=color;
    }



    public void setBishop(int x,int y,int color){
        this.x=x;
        this.y=y;
        this.color=color;
    }

    public void getMoves(char [][] board,int [][] moves)
    {
        int dir1[]={1,1,-1,-1};
        int dir2[]={1,-1,1,-1};
        for(int i=0;i<4;i++)
        {
            int u=x+dir1[i],v=y+dir2[i];
            while((u<8 && u>=0) && (v<8 && v>=0))
            {
                if(board[v][u]=='.')
                    moves[v][u]=1;// include u,v
                else
                {
                    char ch=board[v][u]; // coz its convinient
                    if(color==1)
                    {
                        if((ch=='.') || ( (ch>='a') && (ch <='z') ) )
                            moves[v][u]=1;
                    }
                    else
                    {
                        if((ch=='.') || ( (ch>='A') && (ch <='Z') ) )
                            moves[v][u]=1;
                    }
                    break;
                }
                u=u+dir1[i];
                v=v+dir2[i];
            }
        }
    }

    public void getControlledSquares(char[][] board,int [][] boardControl)
    {
        int dir1[]={1,1,-1,-1};
        int dir2[]={1,-1,1,-1};
        for(int i=0;i<4;i++)
        {
            int u=x+dir1[i],v=y+dir2[i];
            while((u<8 && u>=0) && (v<8 && v>=0))
            {
                if(board[v][u]=='.')
                    boardControl[v][u]=1;// include u,v
                else
                {
                    boardControl[v][u]=1;
                    break;
                }
                u=u+dir1[i];
                v=v+dir2[i];

            }
        }
    }
}

class Rook extends piece{

    public Rook(int x,int y,int color){
        this.x=x;
        this.y=y;
        this.color=color;
    }


    public void setRook(int x,int y,int color){
        this.x=x;
        this.y=y;
        this.color=color;
    }

    public void getMoves(char[][] board,int[][]moves)
    {
        int dir1[]={1,0,-1,0};
        int dir2[]={0,-1,0,1};
        for(int i=0;i<4;i++)
        {
            int u=x+dir1[i],v=y+dir2[i];
            while((u<8 && u>=0) && (v<8 && v>=0))
            {
                if(board[v][u]=='.')
                    moves[v][u]=1;// include u,v
                else
                {
                    char ch=board[v][u]; // coz its convinient
                    if(color==1)
                    {
                        if((ch=='.') || ( (ch>='a') && (ch <='z') ) )
                            moves[v][u]=1;
                    }
                    else
                    {
                        if((ch=='.') || ( (ch>='A') && (ch <='Z') ) )
                            moves[v][u]=1;
                    }
                    break;
                }
                u=u+dir1[i];
                v=v+dir2[i];
            }
        }
    }

    public void getControlledSquares(char[][] board,int [][] boardControl)
    {
        int dir1[]={1,0,-1,0};
        int dir2[]={0,-1,0,1};
        for(int i=0;i<4;i++)
        {
            int u=x+dir1[i],v=y+dir2[i];
            while((u<8 && u>=0) && (v<8 && v>=0))
            {
                if(board[v][u]=='.')
                    boardControl[v][u]=1;// include u,v
                else
                {
                    boardControl[v][u]=1;
                    break;
                }
                u=u+dir1[i];
                v=v+dir2[i];
            }
        }
    }

}

class Queen extends piece{

    public Queen(int x,int y,int color){
        this.x=x;
        this.y=y;
        this.color=color;
    }

    public void setQueen(int x,int y,int color){
        this.x=x;
        this.y=y;
        this.color=color;
    }


    public void getMoves(char[][] board,int[][] moves)
    {
        int dir1[]={1,1,-1,-1};
        int dir2[]={1,-1,1,-1};
        for(int i=0;i<4;i++)
        {
            int u=x+dir1[i],v=y+dir2[i];
            while((u<8 && u>=0) && (v<8 && v>=0))
            {
                if(board[v][u]=='.')
                    moves[v][u]=1;// include u,v
                else
                {
                    char ch=board[v][u]; // coz its convinient
                    if(color==1)
                    {
                        if((ch=='.') || ( (ch>='a') && (ch <='z') ) )
                            moves[v][u]=1;
                    }
                    else
                    {
                        if((ch=='.') || ( (ch>='A') && (ch <='Z') ) )
                            moves[v][u]=1;
                    }
                    break;
                }
                u=u+dir1[i];
                v=v+dir2[i];
            }
        }
        int dir3[]={1,0,-1,0};
        int dir4[]={0,-1,0,1};
        for(int i=0;i<4;i++)
        {
            int u=x+dir3[i],v=y+dir4[i];
            while((u<8 && u>=0) && (v<8 && v>=0))
            {
                if(board[v][u]=='.')
                    moves[v][u]=1;// include u,v
                else
                {
                    char ch=board[v][u]; // coz its convinient
                    if(color==1)
                    {
                        if((ch=='.') || ( (ch>='a') && (ch <='z') ) )
                            moves[v][u]=1;
                    }
                    else
                    {
                        if((ch=='.') || ( (ch>='A') && (ch <='Z') ) )
                            moves[v][u]=1;
                    }
                    break;
                }
                u=u+dir3[i];
                v=v+dir4[i];
            }
        }
    }

    public void getControlledSquares(char [][] board,int [][] boardControl)
    {
        int dir1[]={1,1,-1,-1};
        int dir2[]={1,-1,1,-1};
        for(int i=0;i<4;i++)
        {
            int u=x+dir1[i],v=y+dir2[i];
            while((u<8 && u>=0) && (v<8 && v>=0))
            {
                if(board[v][u]=='.')
                    boardControl[v][u]=1; // use u , v ;
                else
                {
                    boardControl[v][u]=1;
                    break;
                }
                u=u+dir1[i];
                v=v+dir2[i];
            }
        }
        int dir3[]={1,0,-1,0};
        int dir4[]={0,-1,0,1};
        for(int i=0;i<4;i++)
        {
            int  u=x+dir3[i],v=y+dir4[i];
            while((u<8 && u>=0) && (v<8 && v>=0))
            {
                if(board[v][u]=='.')
                    boardControl[v][u]=1;// use u,v
                else
                {
                    boardControl[v][u]=1;
                    break;
                }
                u=u+dir3[i];
                v=v+dir4[i];
            }
        }
    }
}

class King extends piece{

    boolean virgin;

    public void setKing(int x,int y,int color){
        this.x=x;
        this.y=y;
        this.color=color;
        virgin=true;
    }

    public King(int x,int y,int color){
        this.x=x;
        this.y=y;
        this.color=color;
        virgin=true;
    }

    public void getMoves(char[][] board,int[][]moves)
    {
        int dir1[]={1,1,-1,-1};
        int dir2[]={1,-1,1,-1};
        for(int i=0;i<4;i++)
        {
            int u=x+dir1[i];
            int v=y+dir2[i];
            if((u<8 && u>=0) && (v<8 && v>=0))
            {
                if(board[v][u]=='.')
                    moves[v][u]=1;// include u,v
                else
                {
                    char ch=board[v][u]; // coz its convinient
                    if(color==1)
                    {
                        if((ch=='.') || ( (ch>='a') && (ch <='z') ) )
                            moves[v][u]=1;
                    }
                    else
                    {
                        if((ch=='.') || ( (ch>='A') && (ch <='Z') ) )
                            moves[v][u]=1;
                    }
                }
            }
        }
        int dir3[]={1,0,-1,0};
        int dir4[]={0,-1,0,1};
        for(int i=0;i<4;i++)
        {
            int u=x+dir3[i];
            int v=y+dir4[i];
            if((u<8 && u>=0) && (v<8 && v>=0))
            {
                if(board[v][u]=='.')
                    moves[v][u]=1;// include u,v
                else
                {
                    char ch=board[v][u]; // coz its convinient
                    if(color==1)
                    {
                        if((ch=='.') || ( (ch>='a') && (ch <='z') ) )
                            moves[v][u]=1;
                    }
                    else
                    {
                        if((ch=='.') || ( (ch>='A') && (ch <='Z') ) )
                            moves[v][u]=1;
                    }
                }
            }
        }
    }

    public void getControlledSquares(char[][] board,int[][]boardControl)
    {

        int dir1[]={1,1,-1,-1};
        int dir2[]={1,-1,1,-1};
        for(int i=0;i<4;i++)
        {
            int u=x+dir1[i];
            int v=y+dir2[i];
            if((u<8 && u>=0) && (v<8 && v>=0))
                boardControl[v][u]=1;
        }
        int dir3[]={1,0,-1,0};
        int dir4[]={0,-1,0,1};
        for(int i=0;i<4;i++)
        {
            int u=x+dir3[i];
            int v=y+dir4[i];
            if((u<8 && u>=0) && (v<8 && v>=0))
                boardControl[v][u]=1;
        }
    }
}
