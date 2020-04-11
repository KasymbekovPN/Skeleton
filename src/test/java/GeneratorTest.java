import org.KasymbekovPN.Skeleton.generator.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testing ...")
public class GeneratorTest {

    @Test
    @DisplayName("Testing ...")
    void test(){

        SimpleGenerator simpleGenerator = new SimpleGenerator();
        simpleGenerator.reset();
        simpleGenerator.beginObject("test");
        simpleGenerator.beginObject("test_1");
        simpleGenerator.end();
        simpleGenerator.end();
        simpleGenerator.beginObject("test1");
        simpleGenerator.addProperty("value1", "anyString1");
        simpleGenerator.addProperty("value2", false);
        simpleGenerator.addProperty("value3", 123.7);
        simpleGenerator.addProperty("value4", 'x');
        simpleGenerator.end();

        simpleGenerator.beginArray("array");
        simpleGenerator.addProperty("arrayString");
        simpleGenerator.addProperty(true);
        simpleGenerator.addProperty('z');
        simpleGenerator.addProperty(125);

        simpleGenerator.beginObject();
        simpleGenerator.addProperty("123", 123);
        simpleGenerator.beginArray("innerArray");
        simpleGenerator.addProperty(125.4);
        simpleGenerator.beginArray();
        simpleGenerator.addProperty(false);
        simpleGenerator.end();
        simpleGenerator.end();
        simpleGenerator.end();
        simpleGenerator.end();

        simpleGenerator.end();

        System.out.println(simpleGenerator);

        Writer writer = new JsonWriter();
        new JsonObjectGenNodeWrHand(writer, GeneratorObjectNode.class);
        simpleGenerator.write(writer);

        System.out.println(writer.getBuffer());
    }
}
