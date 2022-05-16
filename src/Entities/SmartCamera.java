package Entities;

import java.time.LocalDate;

public class SmartCamera extends SmartDevice{

    private int resolutionX;
    private int resolutionY;
    private double fileSize;

    //Construtor vazio
    public SmartCamera(){
        super();
        this.fileSize = 0;
        this.resolutionX = 0;
        this.resolutionY = 0;
    }

    //Construtor completo
    public SmartCamera(String s, double consumptionBase, int resolutionX, int resolutionY, int filesize, int mode, LocalDate fromDate){
        super(s, mode,consumptionBase,fromDate);
        this.resolutionX = resolutionX;
        this.resolutionY = resolutionY;
        this.fileSize = filesize;
    }

    //Construtor sem Mode e Tone
    public SmartCamera(String s, double consumptionBase, int resolutionX, int resolutionY, int filesize, LocalDate fromDate){
        super(s, consumptionBase,fromDate);
        this.resolutionX = resolutionX;
        this.resolutionY = resolutionY;
        this.fileSize = filesize;
    }

    //Construtor de cópia
    public SmartCamera(SmartCamera sc){
        super(sc);
        this.fileSize = sc.getFilesize();
        this.resolutionX = sc.getResolutionX();
        this.resolutionY = sc.getResolutionY();

    }

    //Getters e Setters
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

    //Clone
    public SmartCamera clone(){
        return new SmartCamera(this);
    }

    //Equals
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        SmartCamera sc = (SmartCamera) o;
        return (super.equals(sc) &&
                this.resolutionX == sc.getResolutionX() &&
                this.resolutionY == sc.getResolutionY() &&
                this.fileSize == sc.getFilesize());
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString())
                .append("Resolução: ").append(this.resolutionX).append("x").append(this.resolutionY)
                .append("\n")
                .append("Tamanho do ficheiro: ").append(this.fileSize)
                .append("\n");

        return sb.toString();
    }

    //Consumo energético para câmera
    public double consumoEnergetico(){
        double resolution = (double) (this.resolutionX/this.resolutionY)*0.01;
        return this.fileSize * super.getConsumptionBase() * resolution;
    }
}
