package comparison;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.Date;

public class JsonComparison {
    
    private final static Gson gson = new Gson();
    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        handleNoConstructorGettersAndSetters();
        handleDateSerializationWithoutSpecifiedFormatter();
        handleNestedObjectSerialization();
        handleNullValues();
        handleCustomFieldNaming();
        handleUnknownProperties();
        handleExcludingFields();
        handlePrettyPrinting();
    }
    
    // Jackson can't serialize/deserialize classes without default constructor, while gson can
    private static void handleNoConstructorGettersAndSetters() {
        System.out.println("\n*** Handling serialization of class without default constructor, getters and setters ***\n");

        DogOnlyWithAllArgsConstructor dog = new DogOnlyWithAllArgsConstructor("Rex", new Date());
        
        System.out.println("Gson result: " + gson.toJson(dog));
        
        try {
            System.out.println("Jackson result: " + objectMapper.writeValueAsString(dog));
        } catch (Exception e) {
            System.out.println("Jackson failed! Cause: " + e.getMessage());
        }
        
        System.out.println();
    }

    // Jackson deserializes date in milliseconds by default, while gson provides readable format
    private static void handleDateSerializationWithoutSpecifiedFormatter() throws JsonProcessingException {
        System.out.println("\n **** Handling date serialization without specified formatter ****\n");
        Date date = new Date();
        System.out.println("Gson result: " + gson.toJson(date));
        System.out.println("Jackson result: " + objectMapper.writeValueAsString(date));
        System.out.println();
    }
    
    // Both libs can handle nested object serialization
    private static void handleNestedObjectSerialization() throws JsonProcessingException {
        System.out.println("\n*** Handling nested object serialization ***\n");

        Dog dog = new Dog("Bob", new Date());
        Owner owner = new Owner("John Doe", dog);
        
        System.out.println("Gson result: " + gson.toJson(owner));
        System.out.println("Jackson result: " + objectMapper.writeValueAsString(owner));
    }

    // By default, Gson ignores null fields
    private static void handleNullValues() throws JsonProcessingException {
        System.out.println("\n*** Handling null values ***\n");

        Dog nullDog = new Dog(null, null);

        System.out.println("Gson result: " + gson.toJson(nullDog));
        System.out.println("Jackson result: " + objectMapper.writeValueAsString(nullDog));
    }

    // Both libs can handle custom field naming
    private static void handleCustomFieldNaming() throws JsonProcessingException {
        System.out.println("\n*** Handling custom field naming ***\n");

        CustomDog customDog = new CustomDog("Buddy", new Date());

        System.out.println("Gson result: " + gson.toJson(customDog));
        System.out.println("Jackson result: " + objectMapper.writeValueAsString(customDog));
    }

    // By default, Jackson fails when deserializing unknown properties, unless it's configured not to
    private static void handleUnknownProperties() {
        System.out.println("\n*** Handling unknown properties ***\n");
        
        String json = "{\"name\":\"Nina\",\"dateOfBirth\":\"2025-02-09\",\"color\":\"Brown\"}";

        try {
            Dog dogFromGson = gson.fromJson(json, Dog.class);
            System.out.println("Gson result: " + dogFromGson);
        } catch (Exception e) {
            System.out.println("Gson failed! Cause: " + e.getMessage());
        }

        try {
            // This configuration prevents fails for unknown properties
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Dog dogFromJackson = objectMapper.readValue(json, Dog.class);
            System.out.println("Jackson result: " + dogFromJackson);
        } catch (Exception e) {
            System.out.println("Jackson failed! Cause: " + e.getMessage());
        }
    }

    /* Both libs can handle exclusion of fields, however Gson is trickier to configure to do that.
    *  For Jackson, you can simply annotate the excluded field with @JsonIgnore, but for Gson, you'll
    *  need to configure Gson mapper to exclude fields without @Expose annotation, thus all the remaining fields
    *  will need to be annotated with @Expose. Other way to do that is by placing "transient" modifier to
    *  the excluded field. */
    private static void handleExcludingFields() throws JsonProcessingException {
        System.out.println("\n*** Handling exclusion of fields ***\n");

        Gson gsonWithExposeConfig = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        DogWithIgnoreField dog = new DogWithIgnoreField("Rex", new Date(), "Secret Value");

        System.out.println("Gson result: " + gsonWithExposeConfig.toJson(dog));
        System.out.println("Jackson result: " + objectMapper.writeValueAsString(dog));
    }

    // Both libs have similar results when pretty printing
    private static void handlePrettyPrinting() throws JsonProcessingException {
        System.out.println("\n*** Handling pretty printing ***\n");

        Dog dog = new Dog("Marley", new Date());
        
        String gsonPretty = new GsonBuilder().setPrettyPrinting().create().toJson(dog);
        String jacksonPretty = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dog);

        System.out.println("Gson result:\n" + gsonPretty);
        System.out.println("Jackson result:\n" + jacksonPretty);
    }
}

@AllArgsConstructor
class DogOnlyWithAllArgsConstructor {
    String name;
    Date dateOfBirth;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
class Dog {
    String name;
    Date dateOfBirth;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Owner {
    String name;
    Dog dog;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class CustomDog {
    
    // Custom name for a field. SerializedName is specifically for Gson while JsonProperty is for Jackson
    @SerializedName("dog_name")
    @JsonProperty("dog_name")
    String name;

    Date dateOfBirth;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class DogWithIgnoreField {
    @Expose
    String name;
    
    @Expose
    Date dateOfBirth;
    
    // Not annotated with @Expose
    @JsonIgnore
    String secretValue;
}
