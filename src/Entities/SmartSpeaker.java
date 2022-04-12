package Entities;

public class SmartSpeaker extends SmartDevice{
    public static final int MAX = 20; //volume máximo

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


    public SmartSpeaker(String cod, String channel, int i, String brand) {
        // initialise instance variables
        super(cod);
        this.channel = channel;
        if(i>=0 && i<=MAX) {
            this.volume = i;
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
        //tem que implementar
        return 0;
    }

    /*
    TO THINK
    --------
     O consumo diário de cada coluna é também função do consumo diário de colunas daquela marca +
     factor em função do volume em que a coluna está a tocar.

     Valor fixo de consumo diário para cada marca específica. Como guardar valores para marcas existentes?
     */
}

