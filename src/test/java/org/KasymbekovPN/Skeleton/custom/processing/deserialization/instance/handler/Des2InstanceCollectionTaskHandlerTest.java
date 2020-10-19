package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.handler;

import org.KasymbekovPN.Skeleton.custom.optionalConverter.ClassName2Instance;
import org.KasymbekovPN.Skeleton.custom.optionalConverter.ToInstanceOC;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.Des2InstanceCxt;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.state.Des2InstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.SKContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.result.SKSimpleResult;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.KasymbekovPN.Skeleton.util.USKClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.util.USKCollectorPath;
import org.KasymbekovPN.Skeleton.util.USKDes2Instance;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Des2InstanceCollectionTaskHandler. Testing of:")
public class Des2InstanceCollectionTaskHandlerTest {

    private static final String NOT_VALID = "not valid";
    private static final String NUMBER_OF_MEMBERS_ARE_ZERO = "Number of members are zero";

    @DisplayName("doIt method - check valid")
    @Test
    void testDoItMethodNotValid() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ContextStateCareTakerIsEmpty {
        TestedClassWrapper tested = new TestedClassWrapper("collection");

        HashMap<String, Class<?>> map = new HashMap<>();
        Des2InstanceCxt context = USKDes2Instance.createContext(
                USKDes2Instance.createContextIds(),
                new HashMap<>(),
                new ClassName2Instance(map),
                new ToInstanceOC(map, USKClassHeaderPartHandler.DEFAULT, USKCollectorPath.DEFAULT_CLASS_PART_PATH),
                USKDes2Instance.createProcessor(),
                new SKContextStateCareTaker<>()
        );

        context.getContextStateCareTaker().push(new NotValidMemento());

        tested.check(context);
        SimpleResult result = tested.getResult();
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getStatus()).isEqualTo(NOT_VALID);
    }

    @DisplayName("doIt method - get members")
    @Test
    void testDoItMethodGetMembers() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ContextStateCareTakerIsEmpty {
        TestedClassWrapper tested = new TestedClassWrapper("collection");

        HashMap<String, Class<?>> map = new HashMap<>();
        Des2InstanceCxt context = USKDes2Instance.createContext(
                USKDes2Instance.createContextIds(),
                new HashMap<>(),
                new ClassName2Instance(map),
                new ToInstanceOC(map, USKClassHeaderPartHandler.DEFAULT, USKCollectorPath.DEFAULT_CLASS_PART_PATH),
                USKDes2Instance.createProcessor(),
                new SKContextStateCareTaker<>()
        );

        context.getContextStateCareTaker().push(new ZeroMembersMemento());

        tested.check(context);
        SimpleResult result = tested.getResult();
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getStatus()).isEqualTo(NUMBER_OF_MEMBERS_ARE_ZERO);
    }

    private static class TestedClassWrapper extends Des2InstanceCollectionTaskHandler{
        public TestedClassWrapper(String id) {
            super(id);
            simpleResult = new SKSimpleResult();
        }

        public TestedClassWrapper(String id, SimpleResult simpleResult) {
            super(id, simpleResult);
        }

        @Override
        protected void check(Des2InstanceCxt context) throws ContextStateCareTakerIsEmpty {
            super.check(context);
        }

        @Override
        protected void doIt(Des2InstanceCxt context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
            super.doIt(context);
        }
    }

    private static class NotValidMemento implements Des2InstanceContextStateMemento{
        @Override
        public Object getInstance() {
            return null;
        }

        @Override
        public Set<Triple<Field, Node, ObjectNode>> getMembersData(String kind) {
            return null;
        }

        @Override
        public void validate() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        }

        @Override
        public SimpleResult getValidationResult() {
            SKSimpleResult result = new SKSimpleResult();
            result.setFailStatus(NOT_VALID);
            return result;
        }
    }

    private static class ZeroMembersMemento implements Des2InstanceContextStateMemento{
        @Override
        public Object getInstance() {
            return null;
        }

        @Override
        public Set<Triple<Field, Node, ObjectNode>> getMembersData(String kind) {
            return new HashSet<>();
        }

        @Override
        public void validate() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        }

        @Override
        public SimpleResult getValidationResult() {
            return new SKSimpleResult();
        }
    }
}
