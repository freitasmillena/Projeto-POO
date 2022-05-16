package Entities;

import Entities.Exceptions.DateAlreadyExistsException;
import Entities.Exceptions.LocationAlreadyExists;
import Entities.Exceptions.LocationDoesntExist;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class Casa implements Serializable {

    private String owner;
    private String NIF;
    private Map<String, SmartDevice> devices; // identificador -> SmartDevice
    private Map<String, List<String>> locations; // Espaço -> Lista codigo dos devices
    private String supplier;
    private double totalInstallationCost;

    //Construtor vazio
    public Casa(){
        this.owner= "";
        this.NIF = "";
        this.supplier = "";
        this.devices = new TreeMap<>();
        this.locations = new HashMap<>();
        this.totalInstallationCost = 0;

    }

    //Construtor completo
    public Casa(String owner, String nif, String fornecedor){
        this.owner = owner;
        this.NIF = nif;
        this.devices =  new TreeMap<>();
        this.locations = new HashMap<>();
        this.supplier = fornecedor;
        this.totalInstallationCost = 0;

    }

    //Construtor de cópia
    public Casa(Casa casa){
        this.owner = casa.getOwner();
        this.NIF = casa.getNIF();
        this.devices = casa.getDevices();
        this.locations = casa.getLocations();
        this.totalInstallationCost = casa.getTotalInstallationCost();
        this.supplier = casa.getSupplier();

    }

    //Getters e Setters
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
        Map<String,SmartDevice> result =  new TreeMap<>();

        for(SmartDevice sd : this.devices.values()){
            result.put(sd.getId(),sd.clone());
        }

        return result;
    }

    public Map<String, List<String>> getLocations() {
        Map<String, List<String>> result = new HashMap<>();

        for(String location : this.locations.keySet()){
            result.put(location, getDevicesFromLocation(location));
        }

        return result;
    }

    //Retorna lista de dispositivos de um cómodo da Casa
    public List<String> getDevicesFromLocation(String location){

        List<String> result = new ArrayList<>();

        for(String s: this.locations.get(location)){
            result.add(s);
        }

        return result;
    }


    //Clone
    public Casa  clone(){
        return new Casa(this);
    }

    //Equals
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Casa casa = (Casa) o;
        return (this.owner.equals(casa.getOwner()) &&
                this.NIF.equals(casa.getNIF()) &&
                this.devices.equals(casa.getDevices()) &&
                this.locations.equals(casa.getLocations()) &&
                this.supplier.equals(casa.getSupplier()) &&
                this.totalInstallationCost == casa.getTotalInstallationCost());
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Dono: ").append(this.owner)
                .append(" ")
                .append("NIF: ").append(this.NIF)
                .append(" Fornecedor: ").append(this.supplier)
                .append("\n");
                for(String location : this.locations.keySet()){
                    sb.append(location).append(": ").append(this.locations.get(location)).append("\n");
                }

        return sb.toString();
    }

    //Mudar o modo de um dispositivo "s"
    public void setMode(String s, int mode, LocalDate fromDate) throws DateAlreadyExistsException {
        this.devices.get(s).addLog(fromDate, mode);
    }

    //Mudar o modo de todos os dispositivos da casa
    public void setAllMode(int mode, LocalDate fromDate) throws DateAlreadyExistsException {
        for(SmartDevice sm : this.devices.values()){
            sm.addLog(fromDate, mode);
        }
    }

    //Mudar o modo de todos os dispositivos de um cómodo
    public void setAllModeLocation(String location, int mode, LocalDate fromDate) throws DateAlreadyExistsException, LocationDoesntExist {
        if(!hasLocation(location)){
            throw new LocationDoesntExist("Esta divisão: " + location + " não existe.");
        }
        for(String devices : this.locations.get(location)){
            this.devices.get(devices).addLog(fromDate, mode);
        }
    }

    //Adiciona dispositivo na casa
    private void addDevice(SmartDevice sd, double installationCost) throws DateAlreadyExistsException{
        if(this.devices.containsKey(sd.getId())){
            throw new DateAlreadyExistsException("Este dispositivo " + sd.getId() + "já existe.");
        }
        this.devices.put(sd.getId(),sd.clone());
        this.totalInstallationCost += installationCost;
    }

    //Verificar se localização existe
    private boolean hasLocation(String location){
        return this.locations.containsKey(location);
    }

    //Adicionar localização
    public void addLocation(String location) throws LocationAlreadyExists {
        if(hasLocation(location)){
           throw new LocationAlreadyExists("Esta divisão: " + location + " já existe.");
        }
        else {
            this.locations.put(location, new ArrayList<>());
        }
    }

    //Adicionar um dispositivo a um cómodo
    public void addDeviceToLocation(String location, SmartDevice sd, double installationCost) throws DateAlreadyExistsException, LocationDoesntExist{
        if(!hasLocation(location)){
            throw new LocationDoesntExist("Esta divisão: " + location + " não existe.");
        }

        this.locations.get(location).add(sd.getId());

        addDevice(sd.clone(), installationCost);
    }

    //Calcular consumo total da casa
    public double totalConsumption(LocalDate fromDate, LocalDate toDate){
        double totalConsumption = 0;
        for(SmartDevice sd : this.devices.values()){

              totalConsumption += sd.totalConsumo(fromDate, toDate);

        }

        return totalConsumption;
    }

    public int devicesON(){
        int nDevices = 0;
        for(SmartDevice sd : this.devices.values()){
            if(sd.lastRecentMode()==1) {
                nDevices ++;
            }
        }
        return nDevices;
    }

    public void printLocations(){
        for(String location : this.locations.keySet()){
            System.out.println(location);
        }
    }

    public void printDevicesFromLocation(String location){
        for(String sd : this.locations.get(location)){
            System.out.println(sd);
        }

    }

    public void printLogsfromDevice(String id){
        SmartDevice sd = this.devices.get(id);
        sd.printLogs();
    }

    public void printAllDevicesIDs(){
        for(String id : this.devices.keySet()){
            System.out.println(id);
        }
    }
}
