package com.epam.sp.song.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SongIds {
    private List<Integer> ids;
}
