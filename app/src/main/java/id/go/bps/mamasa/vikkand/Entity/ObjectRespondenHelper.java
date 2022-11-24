package id.go.bps.mamasa.vikkand.Entity;

/**
 * Created by ASUS on 27/06/2018.
 */

public class ObjectRespondenHelper {

    private ObjectResponden objectResponden;
    private int notEdited;
    private int editBenar;
    private int editSalah;
    private int sudahDikirim;
    private int submit;
    private int all;

    public ObjectRespondenHelper(){

    }

    public ObjectRespondenHelper(ObjectResponden objectResponden, int notEdited,
            int editBenar, int editSalah, int sudahDikirim, int submit, int all){
        this.setObjectResponden(objectResponden);
        this.setNotEdited(notEdited);
        this.setEditBenar(editBenar);
        this.setEditSalah(editSalah);
        this.setSudahDikirim(sudahDikirim);
        this.setSubmit(submit);
        this.setAll(all);
    }

    public ObjectResponden getObjectResponden() {
        return objectResponden;
    }

    public void setObjectResponden(ObjectResponden objectResponden) {
        this.objectResponden = objectResponden;
    }

    public int getNotEdited() {
        return notEdited;
    }

    public void setNotEdited(int notEdited) {
        this.notEdited = notEdited;
    }

    public int getEditBenar() {
        return editBenar;
    }

    public void setEditBenar(int editBenar) {
        this.editBenar = editBenar;
    }

    public int getEditSalah() {
        return editSalah;
    }

    public void setEditSalah(int editSalah) {
        this.editSalah = editSalah;
    }

    public int getSudahDikirim() {
        return sudahDikirim;
    }

    public void setSudahDikirim(int sudahDikirim) {
        this.sudahDikirim = sudahDikirim;
    }

    public int getSubmit() {
        return submit;
    }

    public void setSubmit(int submit) {
        this.submit = submit;
    }

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }
}
