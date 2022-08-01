package com.project1.haruco.web.domain.point;

import com.project1.haruco.web.domain.commom.Timestamped;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
public class Point extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long pointId;

    @Column
    private Long acquiredPoint;

    @Builder
    public Point(Long pointId, Long acquiredPoint) {
        this.pointId = pointId;
        this.acquiredPoint = acquiredPoint;
    }

    public Point(){
        this.acquiredPoint = 0L;
    }
}
