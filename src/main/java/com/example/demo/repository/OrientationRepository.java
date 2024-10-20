package com.example.demo.repository;

import com.example.demo.entity.Orientation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrientationRepository extends JpaRepository<Orientation, Integer> {
}