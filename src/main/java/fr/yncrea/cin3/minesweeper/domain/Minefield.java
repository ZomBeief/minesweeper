package fr.yncrea.cin3.minesweeper.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Minefield {
    @Id
    @GeneratedValue
    private UUID id;

    private long width;
    private long height;
    private long count;

    @Lob
    private int[][] minefield;

    public Minefield() {

    }

    public Minefield(long width, long height) {
        super();
        this.width = width;
        this.height = height;
    }

    public void setMinefield(){
        this.minefield = new int[(int) height][(int) width];
        for(int i=0; i< count; i++){
            while(true){
                int x = (int) (Math.random()*(height-1));
                int y = (int) (Math.random()*(width-1));
                if(minefield[x][y] != -1){
                    minefield[x][y] = -1;
                    break;
                }
            }
        }
        for(int i = 0; i<height;i++){
            for(int j=0;j<width;j++){
                if(minefield[i][j]==-1){
                    for(int k = -1; k<2; k++){
                        for(int l=-1; l<2;l++){
                            if(-1<i+k && i+k<height && -1 < j+l && j+l < width && minefield[i+k][j+l] != -1){
                                minefield[i+k][j+l]++;
                            }
                        }
                    }
                    minefield[i][j]=-1;
                }
            }
        }
        for(int i =0;i<height;i++){
            for(int j =0;j<width;j++){
                minefield[i][j]+=10;
            }
        }

    }
/**
 public void fill(int[][] tab, int i, int j){
 for (int k=-1;k<2;k++){
 for(int l=-1;l<2;l++){
 if(tab[i][j]!=10){
 break;
 }
 else{
 fill(tab,i+j,j+l);
 }
 }
 }
 }
 **/

     public boolean discover(int row, int col){

        switch (minefield[row][col]){
            case 9:
                minefield[row][col]-=10;
                return false;
            case 0:
            case 10:
                for(int i = -1; i<2;i++) {
                    for (int j = -1; j < 2; j++) {
                        if (-1 < row + i && row + i < height && -1 < col + j && col + j < width && minefield[row + j][col + j] > 8) {
                            minefield[row + i][col + j] -= 10;
                        }
                    }
                }
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
                minefield[row][col]-=10;
                break;
        default:
        }
     return true;
     }


    public void discoverAll(){
        for(int i=0;i<height;i++){
            for(int j=0;j<width;i++){
                discover(i,j);
            }
        }
    }

    public boolean end(){
        int i =0;
        for(int j=0;j<height;j++){
            for(int k=0; k < width;k++){
                if(minefield[j][k]<9){
                    i++;
                }
            }
        }
        if(i == width*height){
            return true;
        }else{
            return false;
        }
    }

}
