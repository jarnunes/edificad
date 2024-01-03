package com.puc.edificad.web.api;

import com.puc.edificad.model.State;
import com.puc.edificad.model.StateItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/states")
public class StatesRestController {

    @GetMapping
    public List<StateItem> stateItems(@RequestParam(value = "q", required = false) String query) {
        if (StringUtils.isEmpty(query)) {
            return Arrays.stream(State.values())
                    .limit(15)
                    .map(this::mapToStateItem)
                    .collect(Collectors.toList());
        }

        return Arrays.stream(State.values())
                .filter(state -> state.getLabel()
                        .toLowerCase()
                        .contains(query))
                .limit(15)
                .map(this::mapToStateItem)
                .collect(Collectors.toList());
    }

    private StateItem mapToStateItem(State state) {
        return StateItem.builder()
                .id(state)
                .text(state.getLabel())
                .slug(state.name())
                .build();
    }
}