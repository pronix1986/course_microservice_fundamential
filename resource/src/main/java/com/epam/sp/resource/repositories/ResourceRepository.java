package com.epam.sp.resource.repositories;

import com.epam.sp.resource.models.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Integer> {
}
