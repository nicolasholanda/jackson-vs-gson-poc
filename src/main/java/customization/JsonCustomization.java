package customization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import customization.gson.GsonCarDeserializer;
import customization.gson.GsonCarSerializer;
import customization.jackson.JacksonCarDeserializer;
import customization.jackson.JacksonCarSerializer;
import customization.model.Car;
import customization.model.Color;

public class JsonCustomization {

    // Test data
    static Car car = new Car(Color.RED, "Gson Wheels");
    static String json = "{\"color_name\": \"blue\",\"brand_name\": \"Gson Wheels\", \"is_red\": true}";
    
    // Mappers configuration
    static Gson gson = new GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting()
            .registerTypeAdapter(Car.class, new GsonCarSerializer())
            .registerTypeAdapter(Car.class, new GsonCarDeserializer())
            .create();
    static ObjectMapper objectMapper = new ObjectMapper();


    public static void main(String[] args) {
        SimpleModule module = new SimpleModule();
        module.addSerializer(new JacksonCarSerializer(Car.class));
        module.addDeserializer(Car.class, new JacksonCarDeserializer());
        objectMapper.registerModule(module);
        
        runCustomGsonSerialization();
        runCustomGsonDeserialization();
        runCustomJacksonSerialization();
        runCustomJacksonDeserialization();
    }

    private static void runCustomGsonSerialization() {
        Car car = new Car(Color.RED, "Gson Wheels");

        System.out.println("\n*** Custom Gson Serializer ***\n");
        System.out.println(gson.toJson(car));
    }

    private static void runCustomGsonDeserialization() {
        System.out.println("\n*** Custom Gson Deserializer ***\n");
        System.out.println(gson.fromJson(json, Car.class));
    }
    
    private static void runCustomJacksonSerialization() {
        System.out.println("\n*** Custom Jackson Serializer ***\n");
        try {
            System.out.println(objectMapper.writeValueAsString(car));
        } catch (JsonProcessingException e) {
            System.out.println("Jackson failed! Cause: " + e.getMessage());
        }
    }

    private static void runCustomJacksonDeserialization() {
        System.out.println("\n*** Custom Jackson Deserializer ***\n");
        try {
            System.out.println(objectMapper.readValue(json, Car.class));
        } catch (JsonProcessingException e) {
            System.out.println("Jackson failed! Cause: " + e.getMessage());
        }
    }
}
