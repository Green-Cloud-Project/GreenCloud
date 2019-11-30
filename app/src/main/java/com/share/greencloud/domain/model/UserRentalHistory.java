package com.share.greencloud.domain.model;


public class UserRentalHistory {

    private String rentDate;
    private String returnDate;
    private String rentLocation;
    private String returnLocation;
    private int    status; // 1.반납,2.대여중,3.구매,4.분실

    public UserRentalHistory(String  rentDate, String returnDate, String rentLocation, String returnLocation,int status) {

        this.rentDate       = rentDate;
        this.returnDate     = returnDate;
        this.rentLocation   = rentLocation;
        this.returnLocation = returnLocation;
        this.status         = status;

    }

    public String getRentDate() {
        return rentDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public String getRentLocation() {
        return rentLocation;
    }

    public String getReturnLocation() {
        return returnLocation;
    }

    public int getStatus() {
        return status;
    }

}
