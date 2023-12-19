package com.epam.sp.song.repositories;

import com.epam.sp.song.models.SongMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetadataRepository extends JpaRepository<SongMetadata, Integer> {}
