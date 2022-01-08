package com.isd.ict.capstoneproject.controller;

import com.isd.ict.capstoneproject.bike.Bike;
import com.isd.ict.capstoneproject.bike.BikeService;
import com.isd.ict.capstoneproject.bike.BikeServiceImpl;
import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
import com.isd.ict.capstoneproject.controller.dto.DockDetailDTO;
import com.isd.ict.capstoneproject.station.DockStation;
import com.isd.ict.capstoneproject.station.DockStationService;
import com.isd.ict.capstoneproject.station.DockStationServiceImpl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The {@link ViewDockController viewDockController} class provides functionalities for view dock.
 *
 * @author Group 3
 */
public class ViewDockController {

    /**
     * singleton instance
     */
    private static ViewDockController INSTANCE;
    /**
     * Dock station repository
     */
    private final DockStationService dockStationService;
    /**
     * Bike repository
     */
    private final BikeService bikeService;

    public static ViewDockController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ViewDockController();
        }
        return INSTANCE;
    }

    private ViewDockController() {
        this.dockStationService = new DockStationServiceImpl();
        this.bikeService = new BikeServiceImpl();
    }

    /**
     * Get all existing dock stations
     *
     * @return {@link List allDockList}  - retrieve a list of all existing dock stations
     */
    public List<DockStation> getAllDockList() throws DataSourceException {
        return dockStationService.getAll();
    }

    /**
     * search all dock stations with name or address matched keyword
     *
     * @param keyword - keyword to search for dock stations
     * @return {@link List keywordDockList}  - retrieve a list of dock stations with name
     * or address matching keyword
     */
    public List<DockStation> getDockListByKeyword(String keyword) throws DataSourceException {
        return dockStationService.getByKeyword(keyword);
    }

    /**
     * get dock stations with empty slot
     *
     * @return {@link List emptyDockList}  - retrieve a list of all dock stations with empty slot
     */
    public List<DockStation> getEmptyDockList() throws DataSourceException {
        List<DockStation> allDockStations = dockStationService.getAll();
        return allDockStations.stream().filter(dockStation -> dockStation.getMaxCapacity() - dockStation.getCapacity() > 0)
                .collect(Collectors.toList());
    }

    /**
     * get details of a dock station
     *
     * @param dockStationId - id of dock station to get details from
     * @return {@link DockStation dock} - retrieve a dock station
     * with id matched dockStationId
     */
    public DockDetailDTO getDockStationDetails(int dockStationId) throws DataSourceException {
        DockStation dockStation = dockStationService.getById(dockStationId);
        List<Bike> bikeList = bikeService.getAllByLocation(dockStationId);
        Map<String, Long> bikeTypeList = bikeList.stream().collect(Collectors.groupingBy(bike -> bike.getType().getBikeTypeName(), Collectors.counting()));

        DockDetailDTO dto = DockDetailDTO.builder()
                .dockStation(dockStation)
                .bikeList(bikeList)
                .bikeTypeCounts(bikeTypeList)
                .build();

        return dto;
    }
}
