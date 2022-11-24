package id.go.bps.mamasa.vikkand.Entity;

/**
 * Created by ASUS on 27/06/2018.
 */

public class ObjectTipeHelper {

    private String tipe;
    private int counterdone;
    private int all;

    public ObjectTipeHelper(){

    }
    public ObjectTipeHelper(String tipe, int counterdone, int all){
        this.setTipe(tipe);
        this.counterdone = counterdone;
        this.all = all;
    }

    public int getCounterdone() {
        return counterdone;
    }

    public void setCounterdone(int counterdone) {
        this.counterdone = counterdone;
    }

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }
}
