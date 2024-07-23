package com.dedekorkut.hotelbackend.specification;

public class RoomFilter {

    private String hotelName;
    private String city;
    private String startDate;
    private String endDate;
    private Integer beds;
    private Integer capacity;

    public RoomFilter(){}

    public RoomFilter(String hotelName, String city, String startDate, String endDate, Integer beds, Integer capacity) {
        this.hotelName = hotelName;
        this.city = city;
        this.startDate = startDate;
        this.endDate = endDate;
        this.beds = beds;
        this.capacity = capacity;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getBeds() {
        return beds;
    }

    public void setBeds(Integer beds) {
        this.beds = beds;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
