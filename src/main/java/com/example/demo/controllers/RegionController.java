package com.example.demo.controllers;

import com.example.demo.exceptions.JsonResponse;
import com.example.demo.models.Region;
import com.example.demo.service.RegionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class RegionController {

    public static final Logger LOG = LoggerFactory.getLogger(RegionController.class);
    private final RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping("/getAllRegions")
    public List<Region> getAllRegions() {
        return regionService.getRegions();
    }

    @GetMapping("/getRegion/{id}")
    public Region getRegion(@PathVariable Long id) {
        return regionService.getRegion(id);
    }

    @PostMapping("/saveRegion")
    public Region saveRegion(@RequestBody Region region) throws SQLException {
        return regionService.saveRegion(region);
    }

    @PutMapping("/updateRegion")
    public Region updateRegion(@RequestBody @NonNull Region region) throws SQLException {
        return regionService.updateRegion(region);
    }

    @DeleteMapping("/deleteRegion/{id}")
    public JsonResponse deleteRegion(@PathVariable Long id) {
        return regionService.deleteRegion(id);
    }

}
