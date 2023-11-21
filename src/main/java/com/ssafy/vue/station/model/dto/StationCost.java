package com.ssafy.vue.station.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.swing.text.StyledEditorKit;

@AllArgsConstructor
@Builder
public class StationCost {
    private Station station;
    private boolean isTransfer;
}
