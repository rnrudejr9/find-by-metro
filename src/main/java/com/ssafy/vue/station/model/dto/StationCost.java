package com.ssafy.vue.station.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.swing.text.StyledEditorKit;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class StationCost {
    private Station station;
    private boolean isTransfer;
    private Station before;
    private int value;
}
