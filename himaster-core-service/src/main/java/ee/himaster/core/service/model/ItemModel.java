package ee.himaster.core.service.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@MappedSuperclass
@Getter
@Setter
public abstract class ItemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false, name = "created_time")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdTime;
    @Column(nullable = false, name="modified_time")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date modifiedTime;
}
