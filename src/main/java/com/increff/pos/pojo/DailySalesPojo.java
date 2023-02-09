package com.increff.pos.pojo;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DailySalesPojo implements Serializable{

    @Id
    @Column(nullable = false)
    private ZonedDateTime date;
    @Column(nullable = false)
    private Integer orderCount;
    @Column(nullable = false)
    private Integer itemsCount;
    @Column(nullable = false)
    private Double totalRevenue;
}
