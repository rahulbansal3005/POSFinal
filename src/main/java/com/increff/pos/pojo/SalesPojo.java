package com.increff.pos.pojo;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
@Entity
public class SalesPojo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//    @JsonFormat(pattern="dd-MM-yyyy ")
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private Integer invoicedOrderCount;
    @Column(nullable = false)
    private Integer invoicedItemsCount;
    @Column(nullable = false)
    private Double totalRevenue;
    @Column(nullable = false)
//    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime lastRun;

}
