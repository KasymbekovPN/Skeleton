package org.KasymbekovPN.Skeleton.lib.collector.handler;

import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.Utils;
import org.KasymbekovPN.Skeleton.lib.collector.CollectorCheckingResult;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.CollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("SkeletonCollectorCheckingHandler. Testing of:")
public class SkeletonCollectorCheckingHandlerTest {

    private static Object[][] getTestDataIsExisting(){
        return new Object[][]{
                {
                        NoneCollectorCheckingProcess.class,
                        new String[]{"process0", "process1", "process2"},
                        new String[]{"process0", "process1", "process2"},
                        true
                },
                {
                        NoneCollectorCheckingProcess.class,
                        new String[]{"process0", "process1", "process2"},
                        new String[]{"process0", "process11", "process2"},
                        false
                }
        };
    }

    @DisplayName("default add, isExist")
    @ParameterizedTest
    @MethodSource("getTestDataIsExisting")
    void testDefaultAddIsExistGerRemove(
            Class<? extends CollectorCheckingProcess> processClazz,
            String[] processNamesForCreation,
            String[] processNamesForChecking,
            boolean result
    ){
        SkeletonCollectorCheckingHandler collectorCheckingHandler = new SkeletonCollectorCheckingHandler(processClazz);

        for (String processName : processNamesForCreation) {
            collectorCheckingHandler.add(processName);
        }

        boolean isExist = true;
        for (String processName : processNamesForChecking) {
            isExist &= collectorCheckingHandler.isExisting(processName);
        }

        assertThat(isExist).isEqualTo(result);
    }

    private static Object[][] getTestDataAddGet(){
        return new Object[][]{
                {
                        new Class<?>[]{
                                NoneCollectorCheckingProcess.class,
                                IncludeCollectorCheckingProcess.class,
                                ExcludeCollectorCheckingProcess.class
                        },
                        new Class<?>[]{
                                NoneCollectorCheckingProcess.class,
                                IncludeCollectorCheckingProcess.class,
                                ExcludeCollectorCheckingProcess.class
                        },
                        new String[]{"process0", "process1", "process2"},
                        new String[]{"process0", "process1", "process2"},
                        true
                },
                {
                        new Class<?>[]{
                                NoneCollectorCheckingProcess.class,
                                IncludeCollectorCheckingProcess.class,
                                ExcludeCollectorCheckingProcess.class
                        },
                        new Class<?>[]{
                                NoneCollectorCheckingProcess.class,
                                NoneCollectorCheckingProcess.class,
                                ExcludeCollectorCheckingProcess.class
                        },
                        new String[]{"process0", "process1", "process2"},
                        new String[]{"process0", "process1", "process2"},
                        false
                },
                {
                        new Class<?>[]{
                                NoneCollectorCheckingProcess.class,
                                IncludeCollectorCheckingProcess.class,
                                ExcludeCollectorCheckingProcess.class
                        },
                        new Class<?>[]{
                                NoneCollectorCheckingProcess.class,
                                IncludeCollectorCheckingProcess.class,
                                ExcludeCollectorCheckingProcess.class
                        },
                        new String[]{"process0", "process1", "process2"},
                        new String[]{"process0", "process11", "process2"},
                        false
                }
        };
    }

    @DisplayName("add, get")
    @ParameterizedTest
    @MethodSource("getTestDataAddGet")
    void testAddGet(
            Class<?>[] classes,
            Class<?>[] checkingClasses,
            String[] processNamesForCreation,
            String[] processNamesForChecking,
            boolean result
    ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        SkeletonCollectorCheckingHandler cch = new SkeletonCollectorCheckingHandler(NoneCollectorCheckingProcess.class);
        for (int i = 0; i < classes.length; i++) {
            Constructor<?> constructor = classes[i].getConstructor();
            CollectorCheckingProcess process = (CollectorCheckingProcess) constructor.newInstance();
            cch.add(processNamesForCreation[i], process);
        }

        boolean check = true;
        for (int i = 0; i < processNamesForChecking.length; i++) {
            Optional<CollectorCheckingProcess> maybeProcess = cch.get(processNamesForChecking[i]);
            check &= maybeProcess.isPresent() && maybeProcess.get().getClass().equals(checkingClasses[i]);
        }

        assertThat(check).isEqualTo(result);
    }

    private static Object[][] getTestDataRemove() {
        return new Object[][] {
                {
                        NoneCollectorCheckingProcess.class,
                        new String[]{"process1", "process2"}
                }
        };
    }

    @DisplayName("remove")
    @ParameterizedTest
    @MethodSource("getTestDataRemove")
    void testRemove(
            Class<? extends CollectorCheckingProcess> clazz,
            String[] processNames
    ){
        SkeletonCollectorCheckingHandler cch = new SkeletonCollectorCheckingHandler(clazz);
        for (String processName : processNames) {
            cch.add(processName);
        }

        for (String processName : processNames) {
            Optional<CollectorCheckingProcess> maybeProcess = cch.get(processName);
            assertThat(maybeProcess).isPresent();
            cch.remove(processName);
            maybeProcess = cch.get(processName);
            assertThat(maybeProcess).isEmpty();
        }
    }

    private static Object[][] getTestDataHandle() {
        return new Object[][] {
                {
                        NoneCollectorCheckingProcess.class,
                        new HashMap<String, ProcessAndResult>(){{
                            put("none", new ProcessAndResult(
                                            NoneCollectorCheckingProcess.class,
                                            CollectorCheckingResult.NONE
                                    )
                            );
                            put("include", new ProcessAndResult(
                                            IncludeCollectorCheckingProcess.class,
                                            CollectorCheckingResult.INCLUDE
                                    )
                            );
                            put("exclude", new ProcessAndResult(
                                            ExcludeCollectorCheckingProcess.class,
                                            CollectorCheckingResult.EXCLUDE
                                    )
                            );
                        }},
                        true
                },
                {
                        NoneCollectorCheckingProcess.class,
                        new HashMap<String, ProcessAndResult>(){{
                            put("none", new ProcessAndResult(
                                            NoneCollectorCheckingProcess.class,
                                            CollectorCheckingResult.INCLUDE
                                    )
                            );
                            put("include", new ProcessAndResult(
                                            IncludeCollectorCheckingProcess.class,
                                            CollectorCheckingResult.INCLUDE
                                    )
                            );
                            put("exclude", new ProcessAndResult(
                                            ExcludeCollectorCheckingProcess.class,
                                            CollectorCheckingResult.EXCLUDE
                                    )
                            );
                        }},
                        false
                },
        };
    }

    @DisplayName("handle")
    @ParameterizedTest
    @MethodSource("getTestDataHandle")
    void testHandle(
            Class<? extends CollectorCheckingProcess> clazz,
            Map<String, ProcessAndResult> processData,
            boolean result
    ) throws Exception {
        SkeletonCollectorCheckingHandler cch = new SkeletonCollectorCheckingHandler(clazz);
        for (Map.Entry<String, ProcessAndResult> entry : processData.entrySet()) {
            cch.add(entry.getKey(), entry.getValue().createProcess());
        }

        boolean check = true;
        Map<String, CollectorCheckingResult> results = cch.handle(Utils.createCollector());
        for (Map.Entry<String, CollectorCheckingResult> entry : results.entrySet()) {
            if (processData.containsKey(entry.getKey())){
                if (!processData.get(entry.getKey()).checkResult(entry.getValue())){
                    check = false;
                    break;
                }
            } else {
                check = false;
                break;
            }
        }


        assertThat(check).isEqualTo(result);
    }

    private static class NoneCollectorCheckingProcess implements CollectorCheckingProcess {

        public NoneCollectorCheckingProcess() {
        }

        @Override
        public void setResult(Class<? extends Node> clazz, CollectorCheckingResult result) {}

        @Override
        public CollectorCheckingResult getResult() {
            return CollectorCheckingResult.NONE;
        }

        @Override
        public void handle(Node node) {}

        @Override
        public void addHandler(EntityItem handlerId, CollectorProcessHandler collectorProcessHandler) {

        }

        //<
//        @Override
//        public void addHandler(Class<? extends Node> clazz, CollectorProcessHandler collectorProcessHandler) {}
    }

    private static class IncludeCollectorCheckingProcess implements CollectorCheckingProcess{

        public IncludeCollectorCheckingProcess() {
        }

        @Override
        public void setResult(Class<? extends Node> clazz, CollectorCheckingResult result) {}

        @Override
        public CollectorCheckingResult getResult() {
            return CollectorCheckingResult.INCLUDE;
        }

        @Override
        public void handle(Node node) {}

        @Override
        public void addHandler(EntityItem handlerId, CollectorProcessHandler collectorProcessHandler) {

        }


        //<
//        @Override
//        public void addHandler(Class<? extends Node> clazz, CollectorProcessHandler collectorProcessHandler) {}
    }

    private static class ExcludeCollectorCheckingProcess implements CollectorCheckingProcess{

        public ExcludeCollectorCheckingProcess() {
        }

        @Override
        public void setResult(Class<? extends Node> clazz, CollectorCheckingResult result) {}

        @Override
        public CollectorCheckingResult getResult() {
            return CollectorCheckingResult.EXCLUDE;
        }

        @Override
        public void handle(Node node) {}

        @Override
        public void addHandler(EntityItem handlerId, CollectorProcessHandler collectorProcessHandler) {

        }

        //<
//        @Override
//        public void addHandler(Class<? extends Node> clazz, CollectorProcessHandler collectorProcessHandler) {}
    }

    private static class ProcessAndResult{
        private Class<? extends CollectorCheckingProcess> processClazz;
        private CollectorCheckingResult result;

        public ProcessAndResult(Class<? extends CollectorCheckingProcess> processClazz, CollectorCheckingResult result) {
            this.processClazz = processClazz;
            this.result = result;
        }

        CollectorCheckingProcess createProcess() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
            return (CollectorCheckingProcess) processClazz.getConstructor().newInstance();
        }

        boolean checkResult(CollectorCheckingResult result){
            return this.result.equals(result);
        }
    }
}
