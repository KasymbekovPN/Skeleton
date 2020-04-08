import org.KasymbekovPN.Skeleton.serialization.serializer.JsonSerializer;
import org.KasymbekovPN.Skeleton.serialization.serializer.Serializer;
import org.KasymbekovPN.Skeleton.serialization.visitor.JsonVisitor;
import org.KasymbekovPN.Skeleton.serialization.visitor.Visitor;
import org.KasymbekovPN.Skeleton.serialization.visitor.handler.JsonHeadVisitorHandler;
import org.KasymbekovPN.Skeleton.serialization.visitorElement.JsonHeaderVE;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testing of JsonSerializer")
public class JsonSerializerTest {

    @Test
    void test(){

        JsonHeaderVE headVE = new JsonHeaderVE();

        Visitor visitor = new JsonVisitor();
        visitor.addHandler(JsonHeaderVE.class, new JsonHeadVisitorHandler());

        Serializer ser = new JsonSerializer(visitor, headVE);
        ser.serialize(TestClass1.class);
    }
}
