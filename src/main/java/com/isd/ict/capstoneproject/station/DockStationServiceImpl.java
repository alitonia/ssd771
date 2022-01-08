package com.isd.ict.capstoneproject.station;

import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;

import java.util.List;
/**
 * The {@link DockStationServiceImpl dockStationServiceImpl} class is service of dock station.
 *
 * @author Group 3
 *
 */
public class DockStationServiceImpl implements DockStationService {
    /**
     * Dock station repository
     */
    private DockStationRepo dockStationRepo;

    public DockStationServiceImpl() {
        dockStationRepo = new DockStationRepoImpl();
    }

    @Override
    public DockStation getById(int dockStationId) throws DataSourceException {
        return dockStationRepo.getById(dockStationId);
    }

    @Override
    public List<DockStation> getAll() throws DataSourceException {
        return dockStationRepo.getAll();
    }

    @Override
    public List<DockStation> getByKeyword(String keyword) throws DataSourceException {
        return dockStationRepo.getByKeyword(keyword);
    }

    @Override
    public void updateCapacity(int dockStationId) throws DataSourceException {
        dockStationRepo.updateCapacity(dockStationId);
    }
}
