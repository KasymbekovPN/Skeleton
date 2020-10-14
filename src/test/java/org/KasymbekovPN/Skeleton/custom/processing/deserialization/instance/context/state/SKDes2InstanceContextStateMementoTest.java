package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.state;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.SKClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.SKClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.collector.path.SKCollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.junit.jupiter.api.DisplayName;

import java.util.Collections;

@DisplayName("SKDes2InstanceContextStateMemento. Testing of:")
public class SKDes2InstanceContextStateMementoTest {

    private static final ClassHeaderPartHandler classHeaderPartHandler
            = new SKClassHeaderPartHandler("type","name","modifiers");

    private ClassMembersPartHandler classMembersPartHandler
            = new SKClassMembersPartHandler("kind","type","className","modifiers","arguments");

    private final static CollectorPath classPath = new SKCollectorPath(Collections.singletonList("class"),ObjectNode.ei());

    private final static CollectorPath membersPath = new SKCollectorPath(Collections.singletonList("members"),ObjectNode.ei());

    @DisplayName("test instance is null")
    void testInstanceIsNull(){

    }
}
