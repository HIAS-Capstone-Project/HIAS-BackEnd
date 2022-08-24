package com.hias.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class StatisticDTO implements Comparable {
    private String key;
    private Long value;

    @Override
    public int compareTo(Object o) {
        return this.key.compareTo(((StatisticDTO) o).getKey());
    }
}
