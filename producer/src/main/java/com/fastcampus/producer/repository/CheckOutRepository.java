package com.fastcampus.producer.repository;

import com.fastcampus.producer.entity.CheckOutEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckOutRepository extends JpaRepository<CheckOutEntity, Long> {
}
