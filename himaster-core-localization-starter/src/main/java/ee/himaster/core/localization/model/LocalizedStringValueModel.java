package ee.himaster.core.localization.model;

import ee.himaster.core.service.model.ItemModel;
import lombok.*;

import javax.persistence.*;



@Table(name = "localized_string_value", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"language", "localized_value_id"})
})
@NoArgsConstructor
@Getter
@Setter
@Entity
public class LocalizedStringValueModel extends ItemModel {
    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(nullable = false)
    private String value;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "localized_value_id")
    private LocalizedStringModel localizedString;
}
