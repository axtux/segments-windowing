import org.jetbrains.annotations.NotNull;

/**
 * Created by marco on 28/03/17.
 */
public class Tuple implements Comparable{

    private final int x1;
    private final int x2;
    private final int y1;
    private final int y2;

    public Tuple(float x1, float x2, float y1, float y2) {
        this.x1=(int)x1;
        this.x2=(int)x2;
        this.y1=(int)y1;
        this.y2=(int)y2;
    }

    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }
    public int getY1(){
        return y1;
    }
    public int getY2(){
        return y2;
    }

    @Override
    /***
     * we here compare the differents points in y wich have two value y1 and y2
     */
    public int compareTo(@NotNull Object o) {
        if (((Tuple) o).y1 > this.y1)
            return -1;//o est plus grand
        else if (((Tuple) o).y1 == this.y1){
            if (((Tuple) o).y2 > this.y2)
                return -1; //o est plus grand
            else if (((Tuple) o).y2 > this.y2)
                return 0;// they are equals
            else return 1; //o est plus petit
        }
        return 1; //o est plus petit
    }
}
