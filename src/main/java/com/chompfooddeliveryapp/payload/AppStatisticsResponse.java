package com.chompfooddeliveryapp.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppStatisticsResponse {
    private Map<String, Long> General_Statistics;
    private List<String> Most_Favourited_Meals;
}
