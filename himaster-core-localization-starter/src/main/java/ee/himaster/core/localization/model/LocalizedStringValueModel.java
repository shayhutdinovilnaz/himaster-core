package ee.himaster.core.localization.model;

import ee.himaster.core.service.model.ItemModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;


@Entity(name = "LOCALIZED_STRING_VALUE")
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"language", "localized_value_id"})})
@Data
@EqualsAndHashCode(callSuper = true)
public class LocalizedStringValueModel extends ItemModel {
    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(nullable = false)
    private String value;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "localized_value_id")
    private LocalizedStringModel localizedString;
}
