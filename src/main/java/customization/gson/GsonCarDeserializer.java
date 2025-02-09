package customization.gson;

import com.google.gson.*;
import customization.model.Car;
import customization.model.Color;

import java.lang.reflect.Type;

public class GsonCarDeserializer implements JsonDeserializer<Car> {
    @Override
    public Car deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        Car car = new Car();
        String colorName = jsonObject.get("color_name").getAsString();
        
        car.setBrand(jsonObject.get("brand_name").getAsString());
        car.setColor(Color.valueOf(colorName.toUpperCase()));
        
        return car;
    }
}
