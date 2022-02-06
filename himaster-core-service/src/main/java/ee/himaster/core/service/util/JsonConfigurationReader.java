package ee.himaster.core.service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonConfigurationReader {

    private JsonConfigurationReader() {
        throw new UnsupportedOperationException("The utility class only.");
    }

    public static <E> List<E> readFromFile(String pathToFile, Class<E> clazz) {
        final ObjectMapper mapper = new ObjectMapper();
        InputStream is = null;
        try {
            is = JsonConfigurationReader.class.getClassLoader()
                    .getResourceAsStream(pathToFile);
            return mapper.readValue(is, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            log.error("Error during parsing feature configuration file. " +
                    "The path to configuration file: {}", pathToFile, e);
            throw new RuntimeJsonMappingException("Error during parsing feature configuration file. " +
                    "The path to configuration file: " + pathToFile);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.error("Error during close of input stream of file with a path {}. "
                            , pathToFile, e);
                }
            }
        }
    }
}
