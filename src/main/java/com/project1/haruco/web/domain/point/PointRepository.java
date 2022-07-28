package com.project1.haruco.web.domain.point;

import com.project1.haruco.web.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointRepository extends JpaRepository<Point, Long> {
    List<Point> findAllByMember(Member member);
}
