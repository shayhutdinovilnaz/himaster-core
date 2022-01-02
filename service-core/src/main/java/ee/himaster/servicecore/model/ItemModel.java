package ee.himaster.servicecore.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@MappedSuperclass
@Getter
@Setter
public abstract class ItemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdTime;
    @Column(nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date modifiedTime;
}
