package com.increff.pos.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
//@Table(name = "inventory", uniqueConstraints = {@UniqueConstraint(name = "productId",columnNames = {"productId"})},indexes = {@Index(name="", columnList = "")})
public class InventoryPojo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
//    @Column()
    private Integer productId;

//    @Column(nullable = false)
    private Integer quantity;
//    nullable=

}
