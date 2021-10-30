package com.example.demo.service;

import com.example.demo.dto.RegionMapper;
import com.example.demo.exceptions.JsonResponse;
import com.example.demo.exceptions.RegionNotFoundException;
import com.example.demo.models.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class RegionService {
    public static final String REGION_NOT_FOUND = "Region not found: ";
    public static final Logger LOG = LoggerFactory.getLogger(RegionService.class);
    private final RegionMapper regionMapper;

    public RegionService(RegionMapper regionMapper) {
        this.regionMapper = regionMapper;
    }

    public List<Region> getRegions() {
        List<Region> regions = regionMapper.getAll();
        if (regions.isEmpty()) {
            throw new RegionNotFoundException("There is no elements");
        }

        return regions;
    }

    public Region getRegion(@NonNull Long id) {
        Region region = regionMapper.getRegion(id);
        if (region == null) {
            throw new RegionNotFoundException(REGION_NOT_FOUND, id);
        }
        return region;
    }

    public Region saveRegion(@NonNull Region region) throws SQLException {
        if (regionMapper.getRegion(region.getId()) != null) {
            return region;
        }

        try {
            regionMapper.saveRegion(region.getId(), region.getName(), region.getShortName());
            LOG.info("Region created, {}", region);
        } catch (DataAccessException e) {
            LOG.error("SQL exception", e);
            throw new SQLException();
        }
        return region;
    }

    public Region updateRegion(Region region) throws SQLException {
        if (regionMapper.getRegion(region.getId()) == null) {
            throw new RegionNotFoundException(REGION_NOT_FOUND, region.getId());
        }

        try {
            regionMapper.updateRegion(region.getId(), region.getName(), region.getShortName());
            LOG.info("Region updated, {}", region);
        } catch (DataAccessException e) {
            LOG.error("SQL exception", e);
            throw new SQLException();
        }
        return region;
    }

    public JsonResponse deleteRegion(Long id) {
        if (regionMapper.getRegion(id) == null) {
            throw new RegionNotFoundException(REGION_NOT_FOUND, id);
        }
        regionMapper.deleteRegion(id);
        return new JsonResponse(HttpStatus.OK.value(), "Region deleted, id: " + id);
    }
}
