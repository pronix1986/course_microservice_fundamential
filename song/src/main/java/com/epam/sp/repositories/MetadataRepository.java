package com.epam.sp.repositories;

import com.epam.sp.models.SongMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetadataRepository extends JpaRepository<SongMetadata, Integer> {}
