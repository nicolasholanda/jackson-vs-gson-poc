package customization.jackson;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import customization.model.Car;
import customization.model.Color;

import java.io.IOException;

public class JacksonCarDeserializer extends JsonDeserializer<Car> {

    @Override
    public Car deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        Car car = new Car();
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String colorName = node.get("color_name").asText();
        
        
        car.setColor(Color.valueOf(colorName.toUpperCase()));
        car.setBrand(node.get("brand_name").asText());
        
        return car;
    }
}
