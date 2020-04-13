import org.KasymbekovPN.Skeleton.generator.Generator;
import org.KasymbekovPN.Skeleton.generator.SimpleGenerator;
import org.KasymbekovPN.Skeleton.serialization.serializer.Serializer;
import org.KasymbekovPN.Skeleton.serialization.serializer.SimpleSerializer;
import org.KasymbekovPN.Skeleton.serialization.visitor.SerializationVisitor;
import org.KasymbekovPN.Skeleton.serialization.visitor.SimpleSerializationVisitor;
import org.KasymbekovPN.Skeleton.serialization.visitor.handler.HeaderSVEH;
import org.KasymbekovPN.Skeleton.serialization.visitorElement.SimpleSHVE;
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
        Serializer serializer = new SimpleSerializer(visitor, new SimpleSHVE());

        serializer.serialize(TestClass1.class);

        log.info("Generator : {}", generator);
    }
}
