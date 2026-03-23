package com.jeenify.academic_calender.repository;

import com.jeenify.academic_calender.model.AcademicEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcademicEventRepository extends JpaRepository<AcademicEvent, Long> {
}