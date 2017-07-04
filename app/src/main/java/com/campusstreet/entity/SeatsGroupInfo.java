package com.campusstreet.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by develop2 on 2017/7/3.
 */

public class SeatsGroupInfo implements Serializable {

    private List<SeatsSingleInfo> seatsGroup;

    public List<SeatsSingleInfo> getSeatsGroup() {
        return seatsGroup;
    }

    public void setSeatsGroup(List<SeatsSingleInfo> seatsGroup) {
        this.seatsGroup = seatsGroup;
    }

}
