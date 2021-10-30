package com.example.demo.controllers;

import com.example.demo.dto.RegionMapper;
import com.example.demo.excpetions.CustomResponse;
import com.example.demo.excpetions.RegionNotFoundException;
import com.example.demo.models.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class RegionController {

    public static final Logger LOG = LoggerFactory.getLogger(RegionController.class);
    public static final String REGION_NOT_FOUND = "Region not found: ";
    private final RegionMapper regionMapper;

    public RegionController(RegionMapper regionMapper) {
        this.regionMapper = regionMapper;
    }

    @GetMapping("/getAllRegions")
    public List<Region> getAllRegions() {
        List<Region> regions = regionMapper.getAll();
        if (regions.isEmpty()) {
            throw new RegionNotFoundException("There is no elements");
        }
        return regions;
    }

    @GetMapping("/getRegion/{id}")
    public Region getRegion(@PathVariable Long id) {
        Region region = regionMapper.getRegion(id);
        if (region == null) {
            throw new RegionNotFoundException(REGION_NOT_FOUND, id);
        }
        return region;
    }

    @PostMapping("/saveRegion")
    public Region saveRegion(@RequestBody Region region) throws SQLException {
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

    @PutMapping("/updateRegion")
    public Region updateRegion(@RequestBody @NonNull Region region) throws SQLException {
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

    @DeleteMapping("/deleteRegion/{id}")
    public CustomResponse deleteRegion(@PathVariable Long id) {
        if (regionMapper.getRegion(id) == null) {
            throw new RegionNotFoundException(REGION_NOT_FOUND, id);
        }
        regionMapper.deleteRegion(id);
        return new CustomResponse(LocalDateTime.now(), HttpStatus.OK.value(), "Region deleted");
    }

}
