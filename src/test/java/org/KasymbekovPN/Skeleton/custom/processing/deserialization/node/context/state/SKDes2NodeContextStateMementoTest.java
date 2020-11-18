package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.state;

import org.KasymbekovPN.Skeleton.lib.node.InvalidNode;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

@DisplayName("SKDes2NodeContextStateMemento. Testing of:")
public class SKDes2NodeContextStateMementoTest {

    private static final String PARENT_NODE_HAS_WRONG_TYPE = "The parent node has wrong type";

    @DisplayName("validate method")
    @Test
    void testValidateMethod() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        SKDes2NodeContextStateMemento mem = new SKDes2NodeContextStateMemento(new InvalidNode(null, "", ""));
        mem.validate();

        SimpleResult result = mem.getValidationResult();
        Assertions.assertThat(result.isSuccess()).isFalse();
        Assertions.assertThat(result.getStatus()).isEqualTo(PARENT_NODE_HAS_WRONG_TYPE);
    }

    @DisplayName("getParentNode method")
    @Test
    void testGetParentNode(){

        ObjectNode parentNode = new ObjectNode(null);
        SKDes2NodeContextStateMemento mem = new SKDes2NodeContextStateMemento(parentNode);

        Assertions.assertThat(parentNode).isEqualTo(mem.getParentNode());
    }

    @DisplayName("set/getNode methods")
    @Test
    void testSetGetNodeMethods(){

        ObjectNode objectNode = new ObjectNode(null);
        SKDes2NodeContextStateMemento mem = new SKDes2NodeContextStateMemento(null);
        mem.setNode(objectNode);

        Assertions.assertThat(objectNode).isEqualTo(mem.getNode());
    }
}
