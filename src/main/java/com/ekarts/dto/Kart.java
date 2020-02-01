package com.ekarts.dto;

import com.ekarts.enums.EnumKartType;

import java.util.Objects;

public class Kart {

    private int id;
    private String name;
    private Enum<EnumKartType> kartTypeEnum;
    private double pricePerMinute;

    public Kart(int id) {
        this.id = id;
    }

    public Kart(int id, String name, Enum<EnumKartType> kartTypeEnum, double pricePerMinute) {
        this.id = id;
        this.name = name;
        this.kartTypeEnum = kartTypeEnum;
        this.pricePerMinute = pricePerMinute;
    }

    public Kart(String name, Enum<EnumKartType> kartTypeEnum, double pricePerMinute) {
        this.name = name;
        this.kartTypeEnum = kartTypeEnum;
        this.pricePerMinute = pricePerMinute;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Enum<EnumKartType> getKartTypeEnum() {
        return kartTypeEnum;
    }

    public void setKartTypeEnum(Enum<EnumKartType> kartTypeEnum) {
        this.kartTypeEnum = kartTypeEnum;
    }

    public double getPricePerMinute() {
        return pricePerMinute;
    }

    public void setPricePerMinute(double pricePerMinute) {
        this.pricePerMinute = pricePerMinute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kart kart = (Kart) o;
        return id == kart.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Kart{" +
                "kart_id=" + id +
                ", kart_name='" + name + '\'' +
                ", kartTypeEnum=" + kartTypeEnum +
                ", pricePerMinute=" + pricePerMinute +
                '}';
    }
}
