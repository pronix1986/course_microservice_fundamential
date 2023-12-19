package com.epam.sp.resource.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResourceIds {
    private List<Integer> ids;
}
