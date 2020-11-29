package org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.KasymbekovPN.Skeleton.lib.node.NumberNode;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.node.StringNode;
import org.KasymbekovPN.Skeleton.util.USKClassMembersPartHandler;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@DisplayName("SKClassMembersPartHandler. Testing of:")
public class SKClassMembersPartHandlerTest {

    private ClassMembersPartHandler classMembersPartHandler;
    private FakeValuesService fakeValuesService;
    private Faker faker;

    @BeforeEach
    void init(){
        classMembersPartHandler = USKClassMembersPartHandler.DEFAULT;
        fakeValuesService = new FakeValuesService(new Locale("en-US"), new RandomService());
        faker = new Faker();
    }

    @DisplayName("set/getKind method")
    @RepeatedTest(10)
    void testSetGetKindMethod(){
        ObjectNode objectNode = new ObjectNode(null);
        Optional<String> maybeKind = classMembersPartHandler.getKind(objectNode);
        Assertions.assertThat(maybeKind).isEmpty();

        String kind = fakeValuesService.regexify("[a-z1-9]{10}");
        classMembersPartHandler.setKind(objectNode, kind);
        maybeKind = classMembersPartHandler.getKind(objectNode);
        Assertions.assertThat(maybeKind).isPresent();
        Assertions.assertThat(kind).isEqualTo(
                ((StringNode) objectNode.getChildren().get(USKClassMembersPartHandler.KIND)).getValue()
        );
    }

    @DisplayName("set/getType method")
    @RepeatedTest(10)
    void testSetGetTypeMethod(){
        ObjectNode objectNode = new ObjectNode(null);
        Optional<String> maybeType = classMembersPartHandler.getType(objectNode);
        Assertions.assertThat(maybeType).isEmpty();

        String type = fakeValuesService.regexify("[a-z1-9]{10}");
        classMembersPartHandler.setType(objectNode, type);
        maybeType = classMembersPartHandler.getType(objectNode);
        Assertions.assertThat(maybeType).isPresent();
        Assertions.assertThat(type).isEqualTo(
                ((StringNode) objectNode.getChildren().get(USKClassMembersPartHandler.TYPE)).getValue()
        );
    }

    @DisplayName("set/getClassName method")
    @RepeatedTest(10)
    void testSetGetClassNameMethod(){
        ObjectNode objectNode = new ObjectNode(null);
        Optional<String> maybeClassName = classMembersPartHandler.getClassName(objectNode);
        Assertions.assertThat(maybeClassName).isEmpty();

        String className = fakeValuesService.regexify("[a-z1-9]{10}");
        classMembersPartHandler.setClassName(objectNode, className);
        maybeClassName = classMembersPartHandler.getClassName(objectNode);
        Assertions.assertThat(maybeClassName).isPresent();
        Assertions.assertThat(className).isEqualTo(
                ((StringNode) objectNode.getChildren().get(USKClassMembersPartHandler.CLASS_NODE)).getValue()
        );
    }

    @DisplayName("set/getModifier method")
    @RepeatedTest(10)
    void testSetGetModifierMethod(){
        ObjectNode objectNode = new ObjectNode(null);
        Optional<Number> maybeModifier = classMembersPartHandler.getModifiers(objectNode);
        Assertions.assertThat(maybeModifier).isEmpty();

        int modifiers = faker.number().randomDigit();
        classMembersPartHandler.setModifiers(objectNode, modifiers);
        maybeModifier = classMembersPartHandler.getModifiers(objectNode);
        Assertions.assertThat(maybeModifier).isPresent();
        Assertions.assertThat(modifiers).isEqualTo(
                ((NumberNode) objectNode.getChildren().get(USKClassMembersPartHandler.MODIFIERS)).getValue()
        );
    }

    @DisplayName("set/getArguments method")
    @RepeatedTest(10)
    void testSetGetArgumentsMethod(){
        ObjectNode objectNode = new ObjectNode(null);
        Optional<List<String>> maybeArguments = classMembersPartHandler.getArguments(objectNode);
        Assertions.assertThat(maybeArguments).isEmpty();

        ArrayList<String> arguments = new ArrayList<>();
        int size = faker.number().numberBetween(10, 20);
        for (int i = 0; i < size; i++) {
            arguments.add(fakeValuesService.regexify("[a-z1-9]{10}"));
        }

        classMembersPartHandler.setArguments(objectNode, arguments);
        maybeArguments = classMembersPartHandler.getArguments(objectNode);
        Assertions.assertThat(maybeArguments).isPresent();
        Assertions.assertThat(arguments).isEqualTo(maybeArguments.get());
    }
}
