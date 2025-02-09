package benchmark;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Warmup(iterations = 1)
@Measurement(iterations = 2)
@BenchmarkMode(Mode.Throughput)
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class JsonBenchmark {
    
    // Test Data
    private final Course MATH_COURSE = new Course(new Date(), "Math");
    private final Course ENGLISH_COURSE = new Course(new Date(), "English");
    private final List<Course> COURSES = List.of(MATH_COURSE, ENGLISH_COURSE);
    private final Student STUDENT = new Student(45, "Antônio Silva", "asilva@email.com", COURSES);
    private String json;
    
    // Json mappers configuration
    private final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final ObjectMapper jacksonMapper = new ObjectMapper();
    private final Gson gson = new GsonBuilder()
            .setDateFormat(DATE_FORMAT)
            .create();

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }
    
    @Setup(Level.Iteration)
    public void setUp() throws Exception {
        jacksonMapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
        json = jacksonMapper.writeValueAsString(STUDENT);
    }
    
    @Benchmark
    public String jacksonSerialization() throws Exception {
        return jacksonMapper.writeValueAsString(STUDENT);
    }

    @Benchmark
    public Student jacksonDeserialization() throws Exception {
        return jacksonMapper.readValue(json, Student.class);
    }

    @Benchmark
    public String gsonSerialization() throws Exception {
        return gson.toJson(STUDENT);
    }

    @Benchmark
    public Student gsonDeserialization() throws Exception {
        return gson.fromJson(json, Student.class);
    }
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Course implements Serializable {
    private Date startDate;
    private String title;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Student implements Serializable {
    private int age;
    private String name;
    private String email;
    private List<Course> courses;
}
