package customization.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import customization.model.Car;

import java.io.IOException;

import static customization.model.Color.RED;

public class JacksonCarSerializer extends StdSerializer<Car> {

    public JacksonCarSerializer(Class<Car> t) {
        super(t);
    }

    @Override
    public void serialize(Car car, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("brand_name", car.getBrand());
        jsonGenerator.writeObjectField("color_name", car.getColor());
        jsonGenerator.writeObjectField("is_red", car.getColor() == RED);
        jsonGenerator.writeEndObject();
    }
}
