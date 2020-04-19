import org.KasymbekovPN.Skeleton.generator.Generator;
import org.KasymbekovPN.Skeleton.generator.SimpleGenerator;
import org.KasymbekovPN.Skeleton.generator.formatter.JsonFormatter;
import org.KasymbekovPN.Skeleton.generator.node.*;
import org.KasymbekovPN.Skeleton.generator.writeHandler.*;
import org.KasymbekovPN.Skeleton.generator.writer.SimpleWriter;
import org.KasymbekovPN.Skeleton.generator.writer.Writer;
import org.KasymbekovPN.Skeleton.serialization.handler.header.HeaderSEH;
import org.KasymbekovPN.Skeleton.serialization.handler.member.ExtendableMemberSEH;
import org.KasymbekovPN.Skeleton.serialization.handler.member.SimpleMemberSEH;
import org.KasymbekovPN.Skeleton.serialization.serializationElement.header.SimpleHSE;
import org.KasymbekovPN.Skeleton.serialization.serializationElement.member.MemberSE;
import org.KasymbekovPN.Skeleton.serialization.serializationElement.member.SimpleMSE;
import org.KasymbekovPN.Skeleton.serialization.serializer.Serializer;
import org.KasymbekovPN.Skeleton.serialization.serializer.SimpleSerializer;
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
        MemberSE memberVE = new SimpleMSE(new SimpleMemberSEH(String.class))
                .setNext(new SimpleMSE(new ExtendableMemberSEH(Number.class)))
                .setNext(new SimpleMSE(new SimpleMemberSEH(byte.class)))
                .setNext(new SimpleMSE(new SimpleMemberSEH(short.class)))
                .setNext(new SimpleMSE(new SimpleMemberSEH(int.class)))
                .setNext(new SimpleMSE(new SimpleMemberSEH(long.class)))
                .setNext(new SimpleMSE(new SimpleMemberSEH(float.class)))
                .setNext(new SimpleMSE(new SimpleMemberSEH(double.class)))
                .setNext(new SimpleMSE(new SimpleMemberSEH(char.class)))
                .setNext(new SimpleMSE(new SimpleMemberSEH(boolean.class)))
                .setNext(new SimpleMSE(new SimpleMemberSEH(Boolean.class)))
                .setNext(new SimpleMSE(new SimpleMemberSEH(Character.class)));

        Serializer serializer = new SimpleSerializer(headerVE, memberVE, generator);
        serializer.serialize(TestClass2.class);

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