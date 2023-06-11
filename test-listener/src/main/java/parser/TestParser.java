package parser;

import model.Test;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import producer.TestProducer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestParser {
    private static final Logger logger = LogManager.getLogger(TestProducer.class);
    private static final String delimiter = ",";

    public static List<Test> parse(String resourceName) {
        List<Test> userActions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(resourceName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Test userAction = parseUserAction(line);
                if (userAction != null) {
                    userActions.add(userAction);
                }
            }
        } catch (IOException e) {
            logger.error("Error reading file: " + resourceName, e);
        }

        return userActions;
    }

    private static Test parseUserAction(String line) {
        String[] parts = line.split(delimiter);
        if (parts.length == 6) {
            try {
                Long id = Long.parseLong(parts[0].trim());
                String userId = parts[1].trim();
                long timestamp = Long.parseLong(parts[2].trim());
                String activity = parts[3].trim();
                String detailsPage = parts[4].trim();
                String detailsButton = parts[5].trim();
                return new Test(id, userId, timestamp, activity, detailsPage, detailsButton);
            } catch (NumberFormatException e) {
                logger.warn("Invalid line format: " + line);
            }
        } else {
            logger.warn("Invalid line format: " + line);
        }
        return null;
    }
}
