package ukma.springboot.nextskill.utils;

import org.slf4j.Marker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Component;
import ukma.springboot.nextskill.utils.interfaces.ICSVUtility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class CSVUtility implements ICSVUtility {

    private static final Logger logger = LoggerFactory.getLogger(CSVUtility.class);
    private static final Marker marker = MarkerFactory.getMarker("CSV_UTLITY");

    @Override
    public void saveTo(String directory, String fileBasename, List<? extends AbstractCSVConvertable> list) {
        String fullFilename = getFilename(fileBasename);
        String filePath = directory + File.separator + fullFilename + ".csv";

        try {
            Files.createDirectories(Paths.get(directory));
        } catch (IOException e) {
            logger.warn(marker, "Failed to create directory: {}", e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            if (!list.isEmpty()) {
                writer.write(list.getFirst().getHeading());
                writer.newLine();

                for (AbstractCSVConvertable item : list) {
                    writer.write(item.toSCV());
                    writer.newLine();
                }
            }
            writer.close();
        } catch (IOException e) {
            logger.warn(marker, "An error occurred while writing to a CSV file: {}", e.getMessage());
        }

        logger.info(marker, "Successfully saved data into a file: {}/{}", directory, fullFilename);
    }

    private String getFilename(String basename) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sevenDaysAgo = now.minusDays(7);

        return basename + sevenDaysAgo + '~' + now;
    }
}
