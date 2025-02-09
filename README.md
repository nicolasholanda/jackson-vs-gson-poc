# Jackson vs Gson

This repository contains a proof of concept (PoC) project for benchmarking and comparing two popular JSON processing libraries in Java: **Jackson** and **Gson**.

## Project Overview

The project demonstrates:

- Benchmarking the serialization and deserialization performance of Jackson and Gson using JMH (Java Microbenchmark Harness).
- Practical comparisons between Jackson and Gson in handling various JSON processing scenarios.
- Customization examples for serialization and deserialization using both libraries.
- Hands-on exploration of edge cases like null handling, nested objects, and unknown properties.

## Classes Overview

### 1. **JsonBenchmark**

This class benchmarks JSON serialization and deserialization performance using JMH annotations.

#### Key Benchmarked Methods:

- `jacksonSerialization()`: Serializes a `Student` object using Jackson.
- `jacksonDeserialization()`: Deserializes a JSON string into a `Student` object using Jackson.
- `gsonSerialization()`: Serializes a `Student` object using Gson.
- `gsonDeserialization()`: Deserializes a JSON string into a `Student` object using Gson.

#### How to Run:

1. Compile and package the project:
   ```bash
   ./mvnw clean package
   ```
2. Execute the benchmark:
   ```bash
   java -jar target/benchmarks.jar
   ```

### 2. **JsonComparison**

This class contains practical examples of JSON handling comparisons between Jackson and Gson.

#### Key Methods:

- `handleNoConstructorGettersAndSetters()`: Compares serialization/deserialization without default constructors.
- `handleDateSerializationWithoutSpecifiedFormatter()`: Explores date formatting differences.
- `handleNestedObjectSerialization()`: Demonstrates handling of nested objects.
- `handleNullValues()`: Compares handling of `null` values.
- `handleCustomFieldNaming()`: Demonstrates custom field naming.
- `handleUnknownProperties()`: Shows how to handle unknown properties.
- `handleExcludingFields()`: Compares exclusion of fields.
- `handlePrettyPrinting()`: Compares pretty-printing of JSON.

#### How to Run:

1. Execute the main class directly:
   ```bash
   ./mvnw exec:java -Dexec.mainClass="comparison.JsonComparison"
   ```

### 3. **JsonCustomization**

This class demonstrates custom serialization and deserialization using Gson and Jackson.

#### Key Features:

- Custom serializers and deserializers for `Car` objects.
- Registration of custom handlers with both libraries.

#### How to Run:

1. Execute the main class directly:
   ```bash
   ./mvnw exec:java -Dexec.mainClass="customization.JsonCustomization"
   ```

## Dependencies

- **JMH** for benchmarking.
- **Jackson Databind** for JSON processing.
- **Gson** for JSON processing.

## Key Observations

- Jackson provides faster performance for large datasets.
- Gson is more forgiving with missing constructors and getters/setters.
- Date handling behavior differs significantly between the libraries.
- Customization is powerful but configured differently in each library.

## Use Cases

- Benchmarking JSON performance in Java applications.
- Choosing between Jackson and Gson based on project requirements.
- Learning JSON customization techniques.

## Conclusion

This project provides a comprehensive comparison between Jackson and Gson, empowering developers to make informed decisions when choosing JSON processing libraries.

