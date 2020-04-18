import org.KasymbekovPN.Skeleton.generator.Generator;
import org.KasymbekovPN.Skeleton.generator.SimpleGenerator;
import org.KasymbekovPN.Skeleton.generator.formatter.JsonFormatter;
import org.KasymbekovPN.Skeleton.generator.node.*;
import org.KasymbekovPN.Skeleton.generator.writeHandler.*;
import org.KasymbekovPN.Skeleton.generator.writer.SimpleWriter;
import org.KasymbekovPN.Skeleton.generator.writer.Writer;
import org.KasymbekovPN.Skeleton.serialization.handler.member.ByteSEH;
import org.KasymbekovPN.Skeleton.serialization.serializer.Serializer;
import org.KasymbekovPN.Skeleton.serialization.serializer.SimpleSerializer;
import org.KasymbekovPN.Skeleton.serialization.handler.header.HeaderSEH;
import org.KasymbekovPN.Skeleton.serialization.handler.member.NumberSEH;
import org.KasymbekovPN.Skeleton.serialization.handler.member.StringSEH;
import org.KasymbekovPN.Skeleton.serialization.serializationElement.header.SimpleHSE;
import org.KasymbekovPN.Skeleton.serialization.serializationElement.member.MemberSE;
import org.KasymbekovPN.Skeleton.serialization.serializationElement.member.SimpleMSE;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisplayName("Testing of JsonSerializer")
public class JsonSerializerTest {

    private static final Logger log = LoggerFactory.getLogger(JsonSerializerTest.class);

    @Test
    void test(){

        Generator generator = new SimpleGenerator();
        SimpleHSE headerVE = new SimpleHSE(new HeaderSEH());
        MemberSE memberVE = new SimpleMSE(new StringSEH())
                .setNext(new SimpleMSE(new NumberSEH()))
                .setNext(new SimpleMSE(new ByteSEH()));

        Serializer serializer = new SimpleSerializer(headerVE, memberVE, generator);
        serializer.serialize(TestClass1.class);

        Writer writer = new SimpleWriter(new JsonFormatter());
        new ObjectWritingHandler(writer, ObjectNode.class);
        new ArrayWritingHandler(writer, ArrayNode.class);
        new StringWritingHandler(writer, StringNode.class);
        new CharacterWritingHandler(writer, CharacterNode.class);
        new BooleanWritingHandler(writer, BooleanNode.class);
        new NumberWritingHandler(writer, NumberNode.class);
        generator.write(writer);

        log.info("{}", writer.getBuffer());
    }
}
