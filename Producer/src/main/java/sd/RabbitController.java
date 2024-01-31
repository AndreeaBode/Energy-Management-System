package sd;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



@Component
public class RabbitController implements CommandLineRunner {
    @Autowired
    private MQConfig rabbitMqSender;

    private final JSONParser jsonParser = new JSONParser();

    @Value("${app.message}")
    private String message;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(message);
        List<Integer> devices = readConfigFile();

        for (int deviceId : devices) {
            rabbitMqSender.startThreadForDevice(deviceId);
        }
    }

    private List<Integer> readConfigFile() {
        List<Integer> deviceIds = new ArrayList<>();

        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("/app/resources/config.json"));
            JSONArray devices = (JSONArray) jsonObject.get("devices");

            for(Object object : devices) {
                JSONObject device = (JSONObject) object;
                deviceIds.add(Integer.parseInt((String) device.get("deviceId")));
            }

            return deviceIds;
        } catch (IOException | ParseException e) {
            System.out.println(e.getMessage());
        }

        return deviceIds;
    }
}

