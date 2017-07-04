package com.campusstreet.entity;

import java.io.Serializable;

/**
 * Created by develop2 on 2017/7/3.
 */

public class SeatsSingleInfo implements Serializable {

    private String seatState;
    private String seatId;


    public String getSeatState() {
        return seatState;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatState(String seatState) {
        this.seatState = seatState;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

}
