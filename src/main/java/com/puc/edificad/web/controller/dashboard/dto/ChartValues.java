package com.puc.edificad.web.controller.dashboard.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ChartValues implements Serializable {

    @Serial
    private static final long serialVersionUID = -8186853081971830668L;

    private List<String> labels = new ArrayList<>();
    private List<Integer> data = new ArrayList<>();
    private List<String> backgroundColors = new ArrayList<>();
    private List<String> borderColors = new ArrayList<>();

    public void addData(int data) {
        getData().add(data);
    }

    public void addLabel(String label) {
        getLabels().add(label);
    }
}
