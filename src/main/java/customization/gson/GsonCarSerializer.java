package customization.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import customization.model.Car;
import customization.model.Color;

import java.lang.reflect.Type;

public class GsonCarSerializer implements JsonSerializer<Car> {
    
    @Override
    public JsonElement serialize(Car car, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject carJson = new JsonObject();
        
        carJson.addProperty("color_name", car.getColor().name().toLowerCase());
        carJson.addProperty("brand_name", car.getBrand());
        carJson.addProperty("is_red", car.getColor() == Color.RED);
        
        return carJson;
    }
}
