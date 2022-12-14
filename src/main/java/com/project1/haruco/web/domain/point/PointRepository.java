package com.project1.haruco.web.domain.point;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PointRepository extends JpaRepository<Point, Long> {
    List<Point> findAllByOrderByAcquiredPointDesc();

    @Modifying(clearAutomatically = true)
    @Query("update Point p set p.acquiredPoint = p.acquiredPoint + 1L where p.pointId in:memberList")
    int updatePoint(List<Long> memberList);
}
