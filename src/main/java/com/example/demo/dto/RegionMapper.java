package com.example.demo.dto;

import com.example.demo.models.Region;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RegionMapper {
    @Select("SELECT * FROM REGION WHERE id = #{id}")
    Region getRegion(@Param("id") Long id);

    @Select("SELECT * FROM REGION")
    List<Region> getAll();

    @Insert("INSERT INTO REGION (id, name, short_name) VALUES (#{id}, #{name}, #{shortName})")
    void saveRegion(@Param("id") Long id, @Param("name") String name, @Param("shortName") String shortName);

    @Update("UPDATE REGION SET name = #{name}, short_name = #{shortName} WHERE id = #{id}")
    void updateRegion(@Param("id") Long id, @Param("name") String name, @Param("shortName") String shortName);

    @Delete("DELETE FROM REGION WHERE id =#{id}")
    void deleteRegion(long id);
}
