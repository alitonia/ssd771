package com.isd.ict.capstoneproject.controller.dto;

import com.isd.ict.capstoneproject.bike.Bike;
import com.isd.ict.capstoneproject.station.DockStation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * The {@link DockDetailDTO dockDetailDTO} object wraps information to display to user about dock detail.
 *
 *
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DockDetailDTO {
    /**
     * Dock station
     */
    private DockStation dockStation;
    /**
     * Bike list
     */
    private List<Bike> bikeList;
    /**
     * Bike types and quantity
     */
    private Map<String, Long> bikeTypeCounts;
}
