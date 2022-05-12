package Entities;

public class SmartSpeaker extends SmartDevice{
    public static final int MAX = 100; //volume máximo


    private int volume;
    private String channel;
    private String brand;

    public SmartSpeaker() {
        // initialise instance variables
        super();
        this.volume = 0;
        this.channel = "";
    }

    public SmartSpeaker(String s) {
        // initialise instance variables
        super(s);
        this.volume = 0;
        this.channel = "";
        this.brand = "";
    }


    public SmartSpeaker(String cod, int consumptionBase, String channel, int volume, String brand) {
        // initialise instance variables
        super(cod,consumptionBase);
        this.channel = channel;
        if(volume>=0 && volume<=MAX) {
            this.volume = volume;
        }
        else this.volume = 0;
        this.brand = brand;
    }

    public SmartSpeaker(SmartSpeaker s){
        super(s);
        this.channel = s.getChannel();
        this.volume = s.getVolume();
        this.brand = s.getBrand();
    }

    public int getVolume() {
        return volume;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public SmartSpeaker clone(){
        return new SmartSpeaker(this);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        SmartSpeaker s = (SmartSpeaker) o;
        return (super.equals(s) &&
                this.volume == s.getVolume() &&
                this.channel.equals(s.getChannel()) &&
                this.brand.equals(s.getBrand()));
    }

    public void setPercentageVolume(int percentage){ //hey siri 30% volume :))
        this.volume = percentage * MAX;
    }

    public double consumoEnergetico(){
        double consumo;
        if(this.volume <= 0.1*MAX){
            consumo = super.getConsumptionBase();
        }
        else if(this.volume > 0.1*MAX && this.volume <= 0.5*MAX){
                consumo = super.getConsumptionBase()*1.25;
        }
        else{
                consumo = super.getConsumptionBase()*1.5;
        }
        return consumo;
    }


}

