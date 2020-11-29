package org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.KasymbekovPN.Skeleton.lib.node.NumberNode;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.node.StringNode;
import org.KasymbekovPN.Skeleton.util.USKClassHeaderPartHandler;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Locale;
import java.util.Optional;

@DisplayName("SKClassHeaderPartHandler. Testing of:")
public class SKClassHeaderPartHandlerTest {

    private ClassHeaderPartHandler classHeaderPartHandler;
    private FakeValuesService fakeValuesService;

    @BeforeEach
    void init(){
        classHeaderPartHandler = USKClassHeaderPartHandler.DEFAULT;
        fakeValuesService = new FakeValuesService(new Locale("en-US"), new RandomService());
    }

    @DisplayName("setType method")
    @RepeatedTest(10)
    void testSetTypeMethod(){
        String line = fakeValuesService.regexify("[a-z1-9]{10}");
        ObjectNode objectNode = new ObjectNode(null);
        classHeaderPartHandler.setType(objectNode, line);

        Assertions.assertThat(objectNode.getChildren().containsKey(USKClassHeaderPartHandler.TYPE));
        Assertions.assertThat(line).isEqualTo(
                ((StringNode) objectNode.getChildren().get(USKClassHeaderPartHandler.TYPE)).getValue()
        );
    }

    @DisplayName("setName method")
    @RepeatedTest(10)
    void testSetNameMethod(){
        String line = fakeValuesService.regexify("[a-z1-9]{10}");
        ObjectNode objectNode = new ObjectNode(null);
        classHeaderPartHandler.setName(objectNode, line);

        Assertions.assertThat(objectNode.getChildren().containsKey(USKClassHeaderPartHandler.NAME));
        Assertions.assertThat(line).isEqualTo(
                ((StringNode) objectNode.getChildren().get(USKClassHeaderPartHandler.NAME)).getValue()
        );
    }

    @DisplayName("setModifiers method")
    @RepeatedTest(10)
    void testSetModifiersMethod(){
        int value = new Faker().number().randomDigit();
        ObjectNode objectNode = new ObjectNode(null);
        classHeaderPartHandler.setModifiers(
                objectNode,
                value
        );
        Assertions.assertThat(objectNode.getChildren().containsKey(USKClassHeaderPartHandler.MODIFIERS));
        Assertions.assertThat(value).isEqualTo(
                ((NumberNode) objectNode.getChildren().get(USKClassHeaderPartHandler.MODIFIERS)).getValue()
        );
    }

    @DisplayName("getType method")
    @RepeatedTest(10)
    void testGetTypeMethod(){
        ObjectNode objectNode = new ObjectNode(null);
        Optional<String> maybeType = classHeaderPartHandler.getType(objectNode);
        Assertions.assertThat(maybeType).isEmpty();

        String line = fakeValuesService.regexify("[a-z1-9]{10}");
        classHeaderPartHandler.setType(objectNode, line);
        maybeType = classHeaderPartHandler.getType(objectNode);
        Assertions.assertThat(maybeType).isPresent();
        Assertions.assertThat(maybeType.get()).isEqualTo(line);
    }

    @DisplayName("getName method")
    @RepeatedTest(10)
    void testGetNameMethod(){
        ObjectNode objectNode = new ObjectNode(null);
        Optional<String> maybeName = classHeaderPartHandler.getName(objectNode);
        Assertions.assertThat(maybeName).isEmpty();

        String line = fakeValuesService.regexify("[a-z1-9]{10}");
        classHeaderPartHandler.setName(objectNode, line);
        maybeName = classHeaderPartHandler.getName(objectNode);
        Assertions.assertThat(maybeName).isPresent();
        Assertions.assertThat(maybeName.get()).isEqualTo(line);
    }

    @DisplayName("getModifiers method")
    @RepeatedTest(10)
    void testGetModifiersMethod(){
        ObjectNode objectNode = new ObjectNode(null);
        Optional<Number> maybeModifiers = classHeaderPartHandler.getModifiers(objectNode);
        Assertions.assertThat(maybeModifiers).isEmpty();

        int value = new Faker().number().randomDigit();
        classHeaderPartHandler.setModifiers(objectNode, value);
        maybeModifiers = classHeaderPartHandler.getModifiers(objectNode);
        Assertions.assertThat(maybeModifiers).isPresent();
        Assertions.assertThat(maybeModifiers.get()).isEqualTo(value);
    }
}
