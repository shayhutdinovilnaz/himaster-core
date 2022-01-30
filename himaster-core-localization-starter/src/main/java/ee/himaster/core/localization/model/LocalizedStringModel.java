package ee.himaster.core.localization.model;

import ee.himaster.core.service.model.ItemModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class LocalizedStringModel extends ItemModel {
    @Column(name = "default_value")
    private String defaultValue;
    @OneToMany(mappedBy = "localizedString", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<LocalizedStringValueModel> localizedValues = new HashSet<>();
}
