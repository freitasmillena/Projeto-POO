package Entities;

import Enums.Mode;

import java.time.LocalDate;

public class SmartCamera extends SmartDevice{

    private int resolutionX;
    private int resolutionY;
    private double fileSize;

    public SmartCamera(){
        super();
        this.fileSize = 0;
        this.resolutionX = 0;
        this.resolutionY = 0;
    }

    public SmartCamera(String s){
        super(s);
        this.fileSize = 0;
        this.resolutionX = 0;
        this.resolutionY = 0;
    }

    public SmartCamera(String s, double consumptionBase, int resolutionX, int resolutionY, int filesize, Mode mode, LocalDate fromDate){
        super(s, mode,consumptionBase,fromDate);
        this.resolutionX = resolutionX;
        this.resolutionY = resolutionY;
        this.fileSize = filesize;
    }

    public SmartCamera(SmartCamera sc){
        super(sc);
        this.fileSize = sc.getFilesize();
        this.resolutionX = sc.getResolutionX();
        this.resolutionY = sc.getResolutionY();

    }

    public int getResolutionX() {
        return resolutionX;
    }

    public void setResolutionX(int resolution) {
        this.resolutionX = resolution;
    }

    public int getResolutionY() {
        return resolutionY;
    }

    public void setResolutionY(int resolution) {
        this.resolutionY = resolution;
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
                this.resolutionX == sc.getResolutionX() &&
                this.resolutionY == sc.getResolutionY() &&
                this.fileSize == sc.getFilesize());
    }

    public double consumoEnergetico(){
        double resolution = (double) (this.resolutionX/this.resolutionY)*0.001;
        return this.fileSize * super.getConsumptionBase() * resolution;
    }
}
