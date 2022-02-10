package ee.himaster.core.localization;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "himaster.localization.entity.scan", name = "enabled", havingValue = "true")
@EntityScan(value = "ee.himaster")
public class EntityScanAutoconfiguration {
}
