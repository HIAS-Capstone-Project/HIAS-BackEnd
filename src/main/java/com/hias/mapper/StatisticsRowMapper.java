package com.hias.mapper;

import com.hias.model.response.StatisticDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatisticsRowMapper implements RowMapper<StatisticDTO> {
    @Override
    public StatisticDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return StatisticDTO.builder().key(rs.getString("key")).value(rs.getLong("value")).build();
    }
}
