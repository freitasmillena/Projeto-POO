package Entities;

import Enums.Mode;

import java.util.*;

public class Casa {

    private String owner;
    private String NIF;
    private Map<String, SmartDevice> devices; // identificador -> SmartDevice
    private Map<String, List<String>> locations; // Espaço -> Lista codigo dos devices
    private String supplier;
    private double totalInstallationCost;


    public Casa(){
        this.owner= "";
        this.NIF = "";
        this.supplier = "";
        this.devices = new HashMap<>();
        this.locations = new HashMap<>();
        this.totalInstallationCost = 0;

    }

    public Casa(String owner, String nif, String fornecedor, double totalInstallationCost){
        this.owner = owner;
        this.NIF = nif;
        this.devices = new HashMap<>();
        this.locations = new HashMap<>();
        this.supplier = fornecedor;
        this.totalInstallationCost = totalInstallationCost;

    }

    public Casa(Casa casa){
        this.owner = casa.getOwner();
        this.NIF = casa.getNIF();
        this.devices = casa.getDevices();
        this.locations = casa.getLocations();
        this.totalInstallationCost = casa.getTotalInstallationCost();

    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getNIF() {
        return this.NIF;
    }

    public void setNIF(String NIF) {
        this.NIF = NIF;
    }

    public String getSupplier(){ return this.supplier;}

    public void setSupplier(String supplier){ this.supplier = supplier;}

    public double getTotalInstallationCost() {
        return this.totalInstallationCost;
    }

    public void setTotalInstallationCost(double totalInstallationCost) {
        this.totalInstallationCost = totalInstallationCost;
    }

    public Map<String, SmartDevice> getDevices(){
        Map<String,SmartDevice> result = new HashMap<>();

        for(SmartDevice sd : this.devices.values()){
            result.put(sd.getId(),sd.clone());
        }

        return result;
    }

    public List<String> getDevicesFromLocation(String location){
        List<String> result = new ArrayList<>();

        for(String s: this.locations.get(location)){
            result.add(s);
        }

        return result;
    }

    public Map<String, List<String>> getLocations(){
        Map<String, List<String>> result = new HashMap<>();

        for(String location : this.locations.keySet()){
            result.put(location, getDevicesFromLocation(location));
        }

        return result;
    }

    public Casa  clone(){
        return new Casa(this);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Casa casa = (Casa) o;
        return (this.owner.equals(casa.getOwner()) &&
                this.NIF.equals(casa.getNIF()) &&
                this.devices.equals(casa.getDevices()) &&
                this.locations.equals(casa.getLocations()));
    }

    public void setMode(String s, Mode mode){
        this.devices.get(s).setMode(mode);
    }

    public void setAllMode(Mode mode) {
        for(SmartDevice sm : this.devices.values()){
            sm.setMode(mode);
        }
    }

    public void setAllLocation(String location, Mode mode){

        for(String devices : this.locations.get(location)){
            this.devices.get(devices).setMode(mode);
        }
    }

    private void addDevice(SmartDevice sd){

        this.devices.put(sd.getId(),sd.clone());
        this.totalInstallationCost += sd.getInstallationCost();
    }

    private boolean hasLocation(String location){
        return this.locations.containsKey(location);
    }

    public void addLocation(String location){
        if(hasLocation(location)){
            // tratamento de exceção -> divisão já existe
        }
        else {
            this.locations.put(location, new ArrayList<>());
        }
    }

    public void addDeviceToLocation(String location, SmartDevice sd){
        if(!(hasLocation(location))) {
           addLocation(location);
        }
        this.locations.get(location).add(sd.getId());

        addDevice(sd.clone());
    }

    public boolean locationHasDevice(String location, String device){
        boolean result = false;
        if(hasLocation(location)) {
            result = this.locations.get(location).contains(device);
        }

        return result;
    }



}
