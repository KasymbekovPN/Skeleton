import org.KasymbekovPN.Skeleton.generator.SimpleGenerator;
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
        simpleGenerator.endObject();
        simpleGenerator.endObject();
        simpleGenerator.beginObject("test1");
        simpleGenerator.addProperty("value1", "anyString1");
        simpleGenerator.addProperty("value2", false);
        simpleGenerator.addProperty("value3", 123.7);
        simpleGenerator.addProperty("value4", 'x');
        simpleGenerator.endObject();

        System.out.println(simpleGenerator);
    }
}
