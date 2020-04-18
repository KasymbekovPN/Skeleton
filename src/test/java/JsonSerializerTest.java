import org.KasymbekovPN.Skeleton.generator.Generator;
import org.KasymbekovPN.Skeleton.generator.SimpleGenerator;
import org.KasymbekovPN.Skeleton.generator.formatter.JsonFormatter;
import org.KasymbekovPN.Skeleton.generator.node.*;
import org.KasymbekovPN.Skeleton.generator.writeHandler.*;
import org.KasymbekovPN.Skeleton.generator.writer.SimpleWriter;
import org.KasymbekovPN.Skeleton.generator.writer.Writer;
import org.KasymbekovPN.Skeleton.serialization.serializer.Serializer;
import org.KasymbekovPN.Skeleton.serialization.serializer.SimpleSerializer;
import org.KasymbekovPN.Skeleton.serialization.visitor.SerializationVisitor;
import org.KasymbekovPN.Skeleton.serialization.visitor.SimpleSerializationVisitor;
import org.KasymbekovPN.Skeleton.serialization.visitor.handler.header.HeaderSVEH;
import org.KasymbekovPN.Skeleton.serialization.visitor.handler.member.NumberSVEH;
import org.KasymbekovPN.Skeleton.serialization.visitor.handler.member.StringSVEH;
import org.KasymbekovPN.Skeleton.serialization.visitorElement.header.SimpleSHVE;
import org.KasymbekovPN.Skeleton.serialization.visitorElement.member.NumberSMVE;
import org.KasymbekovPN.Skeleton.serialization.visitorElement.member.SerializationMemberVE;
import org.KasymbekovPN.Skeleton.serialization.visitorElement.member.StringSMVE;
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
        SerializationVisitor visitor = new SimpleSerializationVisitor(generator);
        visitor.addHandler(SimpleSHVE.class, new HeaderSVEH());
        visitor.addHandler(StringSMVE.class, new StringSVEH());
        visitor.addHandler(NumberSMVE.class, new NumberSVEH());

//        StringSMVE memberVE = new StringSMVE(null);
        //<
        SerializationMemberVE memberVE = new StringSMVE()
                .setNext(new NumberSMVE());
        Serializer serializer = new SimpleSerializer(visitor, new SimpleSHVE(), memberVE);

        serializer.serialize(TestClass1.class);

//        log.info("Generator : {}", generator);

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
