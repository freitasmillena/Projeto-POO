package Entities;

public class SmartCamera extends SmartDevice{

    double resolution;
    double fileSize;

    public SmartCamera(){
        super();
        this.fileSize = 0;
        this.resolution = 0;
    }

    public SmartCamera(String s){
        super(s);
        this.fileSize = 0;
        this.resolution = 0;
    }

    public SmartCamera(String s, double r, double f){
        super(s);
        this.resolution = r;
        this.fileSize = f;
    }

    public SmartCamera(SmartCamera sc){
        super(sc);
        this.fileSize = sc.getFilesize();
        this.resolution = sc.getResolution();
    }

    public double getResolution() {
        return resolution;
    }

    public void setResolution(double resolution) {
        this.resolution = resolution;
    }

    public double getFilesize() {
        return fileSize;
    }

    public void setFilesize(double filesize) {
        this.fileSize = filesize;
    }

    public SmartCamera clone(){
        return new SmartCamera(this);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        SmartCamera sc = (SmartCamera) o;
        return (super.equals(sc) &&
                this.resolution == sc.getResolution() &&
                this.fileSize == sc.getFilesize());
    }

    public double consumoEnergetico(){
        return this.fileSize * this.resolution;
    }
}
