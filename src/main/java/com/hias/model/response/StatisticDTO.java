package com.hias.model.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class StatisticDTO implements Comparable {
    private String key;
    private Long value;

    @Override
    public int compareTo(Object o) {
        return this.key.compareTo(((StatisticDTO) o).getKey());
    }
}
