package com.example.demo;

import com.example.demo.dto.RegionMapper;
import com.example.demo.models.Region;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RegionMapperTest {

    @Autowired
    RegionMapper regionMapper;

    @Test
    void saveRegion(){
        regionMapper.saveRegion(2L, "Moscow", "MOS");
        Region region = regionMapper.getRegion(2L);
        assertThat(region).isNotNull();
        assertThat(region.getId()).isEqualTo(2L);
        assertThat(region.getName()).isEqualTo("Moscow");
        assertThat(region.getShortName()).isEqualTo("MOS");
    }

}

