import org.KasymbekovPN.Skeleton.collector.SimpleCollector;
import org.KasymbekovPN.Skeleton.collector.formatter.JsonFormatter;
import org.KasymbekovPN.Skeleton.collector.node.*;
import org.KasymbekovPN.Skeleton.collector.writeHandler.*;
import org.KasymbekovPN.Skeleton.collector.writer.SimpleWriter;
import org.KasymbekovPN.Skeleton.collector.writer.Writer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

@DisplayName("Testing ...")
public class CollectorTest {

    @Test
    @DisplayName("Testing ...")
    void test(){

        SimpleCollector simpleGenerator = new SimpleCollector();
        simpleGenerator.clear();
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

        Writer writer = new SimpleWriter(new JsonFormatter());
        new ObjectWritingHandler(writer, ObjectNode.class);
        new ArrayWritingHandler(writer, ArrayNode.class);
        new StringWritingHandler(writer, StringNode.class);
        new CharacterWritingHandler(writer, CharacterNode.class);
        new BooleanWritingHandler(writer, BooleanNode.class);
        new NumberWritingHandler(writer, NumberNode.class);
        simpleGenerator.write(writer);

        System.out.println(writer.getBuffer());
    }

    @Test
    @DisplayName("Testing of 'setTarget' method")
    void testSetTarget(){

        ArrayList<String> path = new ArrayList<>() {{
            add("class");
            add("method");
            add("expression");
        }};
        SimpleCollector generator = new SimpleCollector();
        generator.setTarget(path);

        ArrayList<String> path1 = new ArrayList<>() {{
            add("class");
            add("method1");
            add("expression1");
        }};
        generator.setTarget(path1);

        System.out.println(generator);
    }
}
