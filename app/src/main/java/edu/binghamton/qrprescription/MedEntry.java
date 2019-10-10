package edu.binghamton.qrprescription;

public class MedEntry {

    String name;
    int morning, afternoon, night;

    public MedEntry(String name, int morning, int afternoon, int night){
        this.afternoon = afternoon;
        this.morning = morning;
        this.name = name;
        this.night = night;
    }

    public int getAfternoon() {
        return afternoon;
    }

    public int getMorning() {
        return morning;
    }

    public int getNight() {
        return night;
    }

    public String getName() {
        return name;
    }

    public void setAfternoon(int afternoon) {
        this.afternoon = afternoon;
    }

    public void setMorning(int morning) {
        this.morning = morning;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNight(int night) {
        this.night = night;
    }
}
