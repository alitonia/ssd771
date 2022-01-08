package com.isd.ict.capstoneproject.station;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The {@link DockStation dockStation} object provide functionalities for dock object.
 *
 * @author Group 3
 *
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DockStation {
    /**
     * dock station id
     */
    private int dockStationId;
    /**
     * name
     */
    private String name;
    /**
     * address
     */
    private String address;
    /**
     * distance
     */
    private int distance;
    /**
     * max capacity
     */
    private int maxCapacity;
    /**
     * capacity
     */
    private int capacity;
    private int walkingTimeByMinutes;

    public DockStation(int dockStationId) {
        this.dockStationId = dockStationId;
    }

    /**
     * Get number of empty slots
     * @return - number of empty slots
     */
    public int getNoOfEmptySlots() {
        return this.maxCapacity - this.capacity;
    }
}
