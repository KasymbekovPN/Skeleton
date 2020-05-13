package org.KasymbekovPN.Skeleton.custom.format.collector;

import org.KasymbekovPN.Skeleton.lib.format.collector.CollectorStructure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@DisplayName("SkeletonCollectorStructure. Testing of:")
public class SkeletonCollectorStructureTest {

    private final static Logger log = LoggerFactory.getLogger(SkeletonCollectorStructureTest.class);

    private Map<CollectorStructureEI.Entity, String[]> paths = new HashMap<>();
    private Map<CollectorStructureEI.Entity, String[]> emptyPaths = new HashMap<>();
    private CollectorStructure completeCollectorStructure;

    @BeforeEach
    void beforeEach() throws Exception {
        paths.put(CollectorStructureEI.Entity.CLASS, new String[]{"class"});
        paths.put(CollectorStructureEI.Entity.MEMBERS, new String[]{"members"});
        paths.put(CollectorStructureEI.Entity.CONSTRUCTOR, new String[]{"constructor"});
        paths.put(CollectorStructureEI.Entity.ANNOTATION, new String[]{"annotation"});
        paths.put(CollectorStructureEI.Entity.METHOD, new String[]{"method"});
        paths.put(CollectorStructureEI.Entity.PROTOCOL, new String[]{"protocol"});

        emptyPaths.put(CollectorStructureEI.Entity.CLASS, new String[]{});
        emptyPaths.put(CollectorStructureEI.Entity.MEMBERS, new String[]{});
        emptyPaths.put(CollectorStructureEI.Entity.CONSTRUCTOR, new String[]{});
        emptyPaths.put(CollectorStructureEI.Entity.ANNOTATION, new String[]{});
        emptyPaths.put(CollectorStructureEI.Entity.METHOD, new String[]{});
        emptyPaths.put(CollectorStructureEI.Entity.PROTOCOL, new String[]{});

        completeCollectorStructure = new SkeletonCollectorStructure.Builder()
                .setClassPath(paths.get(CollectorStructureEI.Entity.CLASS))
                .setMembersPath(paths.get(CollectorStructureEI.Entity.MEMBERS))
                .setConstructorPath(paths.get(CollectorStructureEI.Entity.CONSTRUCTOR))
                .setAnnotationPath(paths.get(CollectorStructureEI.Entity.ANNOTATION))
                .setMethodPath(paths.get(CollectorStructureEI.Entity.METHOD))
                .setProtocolPath(paths.get(CollectorStructureEI.Entity.PROTOCOL))
                .build();
    }

    @Test
    @DisplayName("completely collector structure class path testing")
    void testCompletelyCollectorStructureClassPath(){
        assertThat(Arrays.asList(paths.get(CollectorStructureEI.Entity.CLASS)))
                .isEqualTo(completeCollectorStructure.getPath(CollectorStructureEI.classEI()));
    }

    @Test
    @DisplayName("completely collector structure members path testing")
    void testCompletelyCollectorStructureMembersPath(){
        assertThat(Arrays.asList(paths.get(CollectorStructureEI.Entity.MEMBERS)))
                .isEqualTo(completeCollectorStructure.getPath(CollectorStructureEI.membersEI()));
    }

    @Test
    @DisplayName("completely collector structure constructor path testing")
    void testCompletelyCollectorStructureConstructorPath(){
        assertThat(Arrays.asList(paths.get(CollectorStructureEI.Entity.CONSTRUCTOR)))
                .isEqualTo(completeCollectorStructure.getPath(CollectorStructureEI.constructorEI()));
    }

    @Test
    @DisplayName("completely collector structure annotation path testing")
    void testCompletelyCollectorStructureAnnotationPath(){
        assertThat(Arrays.asList(paths.get(CollectorStructureEI.Entity.ANNOTATION)))
                .isEqualTo(completeCollectorStructure.getPath(CollectorStructureEI.annotationEI()));
    }

    @Test
    @DisplayName("completely collector structure method path testing")
    void testCompletelyCollectorStructureMethodPath(){
        assertThat(Arrays.asList(paths.get(CollectorStructureEI.Entity.METHOD)))
                .isEqualTo(completeCollectorStructure.getPath(CollectorStructureEI.methodEI()));
    }

    @Test
    @DisplayName("completely collector structure protocol path testing")
    void testCompletelyCollectorStructureProtocolPath(){
        assertThat(Arrays.asList(paths.get(CollectorStructureEI.Entity.PROTOCOL)))
                .isEqualTo(completeCollectorStructure.getPath(CollectorStructureEI.protocolEI()));
    }

    @Test
    @DisplayName("Setting without class path")
    void testSettingWithoutClassPath(){
        assertThat(
                catchThrowable(() -> {
                    new SkeletonCollectorStructure.Builder()
                            .setMembersPath(paths.get(CollectorStructureEI.Entity.MEMBERS))
                            .setConstructorPath(paths.get(CollectorStructureEI.Entity.CONSTRUCTOR))
                            .setAnnotationPath(paths.get(CollectorStructureEI.Entity.ANNOTATION))
                            .setMethodPath(paths.get(CollectorStructureEI.Entity.METHOD))
                            .setProtocolPath(paths.get(CollectorStructureEI.Entity.PROTOCOL))
                            .build();
                })
        ).isInstanceOf(Exception.class)
                .hasMessageContaining("There were set no all item or two and more item have non-unique path");
    }

    @Test
    @DisplayName("Setting without members path")
    void testSettingWithoutMembersPath(){
        assertThat(
                catchThrowable(() -> {
                    new SkeletonCollectorStructure.Builder()
                            .setClassPath(paths.get(CollectorStructureEI.Entity.CLASS))
                            .setConstructorPath(paths.get(CollectorStructureEI.Entity.CONSTRUCTOR))
                            .setAnnotationPath(paths.get(CollectorStructureEI.Entity.ANNOTATION))
                            .setMethodPath(paths.get(CollectorStructureEI.Entity.METHOD))
                            .setProtocolPath(paths.get(CollectorStructureEI.Entity.PROTOCOL))
                            .build();
                })
        ).isInstanceOf(Exception.class)
                .hasMessageContaining("There were set no all item or two and more item have non-unique path");
    }

    @Test
    @DisplayName("Setting without constructor path")
    void testSettingWithoutConstructorPath(){
        assertThat(
                catchThrowable(() -> {
                    new SkeletonCollectorStructure.Builder()
                            .setClassPath(paths.get(CollectorStructureEI.Entity.CLASS))
                            .setMembersPath(paths.get(CollectorStructureEI.Entity.MEMBERS))
                            .setAnnotationPath(paths.get(CollectorStructureEI.Entity.ANNOTATION))
                            .setMethodPath(paths.get(CollectorStructureEI.Entity.METHOD))
                            .setProtocolPath(paths.get(CollectorStructureEI.Entity.PROTOCOL))
                            .build();
                })
        ).isInstanceOf(Exception.class)
                .hasMessageContaining("There were set no all item or two and more item have non-unique path");
    }

    @Test
    @DisplayName("Setting without annotation path")
    void testSettingWithoutAnnotationPath(){
        assertThat(
                catchThrowable(() -> {
                    new SkeletonCollectorStructure.Builder()
                            .setClassPath(paths.get(CollectorStructureEI.Entity.CLASS))
                            .setMembersPath(paths.get(CollectorStructureEI.Entity.MEMBERS))
                            .setConstructorPath(paths.get(CollectorStructureEI.Entity.CONSTRUCTOR))
                            .setMethodPath(paths.get(CollectorStructureEI.Entity.METHOD))
                            .setProtocolPath(paths.get(CollectorStructureEI.Entity.PROTOCOL))
                            .build();
                })
        ).isInstanceOf(Exception.class)
                .hasMessageContaining("There were set no all item or two and more item have non-unique path");
    }

    @Test
    @DisplayName("Setting without method path")
    void testSettingWithoutMethodPath(){
        assertThat(catchThrowable(() -> {
            new SkeletonCollectorStructure.Builder()
                    .setClassPath(paths.get(CollectorStructureEI.Entity.CLASS))
                    .setMembersPath(paths.get(CollectorStructureEI.Entity.MEMBERS))
                    .setConstructorPath(paths.get(CollectorStructureEI.Entity.CONSTRUCTOR))
                    .setAnnotationPath(paths.get(CollectorStructureEI.Entity.ANNOTATION))
                    .setProtocolPath(paths.get(CollectorStructureEI.Entity.PROTOCOL))
                    .build();
        })).isInstanceOf(Exception.class)
                .hasMessageContaining("There were set no all item or two and more item have non-unique path");
    }

    @Test
    @DisplayName("Setting without protocol path")
    void testSettingWithoutProtocolPath(){
        assertThat(
                catchThrowable(() -> {
                    new SkeletonCollectorStructure.Builder()
                            .setClassPath(paths.get(CollectorStructureEI.Entity.CLASS))
                            .setMembersPath(paths.get(CollectorStructureEI.Entity.MEMBERS))
                            .setConstructorPath(paths.get(CollectorStructureEI.Entity.CONSTRUCTOR))
                            .setAnnotationPath(paths.get(CollectorStructureEI.Entity.ANNOTATION))
                            .setMethodPath(paths.get(CollectorStructureEI.Entity.METHOD))
                            .build();
                })
        ).isInstanceOf(Exception.class)
                .hasMessageContaining("There were set no all item or two and more item have non-unique path");
    }

    @Test
    @DisplayName("setting with empty class path")
    void testSettingWithEmptyClassPath() {
        assertThat(
                catchThrowable(() -> {
                    new SkeletonCollectorStructure.Builder()
                            .setClassPath(emptyPaths.get(CollectorStructureEI.Entity.CLASS))
                            .setMembersPath(paths.get(CollectorStructureEI.Entity.MEMBERS))
                            .setConstructorPath(paths.get(CollectorStructureEI.Entity.CONSTRUCTOR))
                            .setAnnotationPath(paths.get(CollectorStructureEI.Entity.ANNOTATION))
                            .setMethodPath(paths.get(CollectorStructureEI.Entity.METHOD))
                            .setProtocolPath(paths.get(CollectorStructureEI.Entity.PROTOCOL))
                            .build();
                })
        ).isInstanceOf(Exception.class)
                .hasMessageContaining("Argument is null or empty");
    }

    @Test
    @DisplayName("setting with empty members path")
    void testSettingWithEmptyMembersPath() {
        assertThat(
                catchThrowable(() -> {
                    new SkeletonCollectorStructure.Builder()
                            .setClassPath(paths.get(CollectorStructureEI.Entity.CLASS))
                            .setMembersPath(emptyPaths.get(CollectorStructureEI.Entity.MEMBERS))
                            .setConstructorPath(paths.get(CollectorStructureEI.Entity.CONSTRUCTOR))
                            .setAnnotationPath(paths.get(CollectorStructureEI.Entity.ANNOTATION))
                            .setMethodPath(paths.get(CollectorStructureEI.Entity.METHOD))
                            .setProtocolPath(paths.get(CollectorStructureEI.Entity.PROTOCOL))
                            .build();
                })
        ).isInstanceOf(Exception.class)
                .hasMessageContaining("Argument is null or empty");
    }

    @Test
    @DisplayName("setting with empty constructor path")
    void testSettingWithEmptyConstructorPath() {
        assertThat(
                catchThrowable(() -> {
                    new SkeletonCollectorStructure.Builder()
                            .setClassPath(paths.get(CollectorStructureEI.Entity.CLASS))
                            .setMembersPath(paths.get(CollectorStructureEI.Entity.MEMBERS))
                            .setConstructorPath(emptyPaths.get(CollectorStructureEI.Entity.CONSTRUCTOR))
                            .setAnnotationPath(paths.get(CollectorStructureEI.Entity.ANNOTATION))
                            .setMethodPath(paths.get(CollectorStructureEI.Entity.METHOD))
                            .setProtocolPath(paths.get(CollectorStructureEI.Entity.PROTOCOL))
                            .build();
                })
        ).isInstanceOf(Exception.class)
                .hasMessageContaining("Argument is null or empty");
    }

    @Test
    @DisplayName("setting with empty annotation path")
    void testSettingWithEmptyAnnotationPath() {
        assertThat(
                catchThrowable(() -> {
                    new SkeletonCollectorStructure.Builder()
                            .setClassPath(paths.get(CollectorStructureEI.Entity.CLASS))
                            .setMembersPath(paths.get(CollectorStructureEI.Entity.MEMBERS))
                            .setConstructorPath(paths.get(CollectorStructureEI.Entity.CONSTRUCTOR))
                            .setAnnotationPath(emptyPaths.get(CollectorStructureEI.Entity.ANNOTATION))
                            .setMethodPath(paths.get(CollectorStructureEI.Entity.METHOD))
                            .setProtocolPath(paths.get(CollectorStructureEI.Entity.PROTOCOL))
                            .build();
                })
        ).isInstanceOf(Exception.class)
                .hasMessageContaining("Argument is null or empty");
    }

    @Test
    @DisplayName("setting with empty method path")
    void testSettingWithEmptyMethodPath() {
        assertThat(
                catchThrowable(() -> {
                    new SkeletonCollectorStructure.Builder()
                            .setClassPath(paths.get(CollectorStructureEI.Entity.CLASS))
                            .setMembersPath(paths.get(CollectorStructureEI.Entity.MEMBERS))
                            .setConstructorPath(paths.get(CollectorStructureEI.Entity.CONSTRUCTOR))
                            .setAnnotationPath(paths.get(CollectorStructureEI.Entity.ANNOTATION))
                            .setMethodPath(emptyPaths.get(CollectorStructureEI.Entity.METHOD))
                            .setProtocolPath(paths.get(CollectorStructureEI.Entity.PROTOCOL))
                            .build();
                })
        ).isInstanceOf(Exception.class)
                .hasMessageContaining("Argument is null or empty");
    }

    @Test
    @DisplayName("setting with empty protocol path")
    void testSettingWithEmptyProtocolPath() {
        assertThat(
                catchThrowable(() -> {
                    new SkeletonCollectorStructure.Builder()
                            .setClassPath(paths.get(CollectorStructureEI.Entity.CLASS))
                            .setMembersPath(paths.get(CollectorStructureEI.Entity.MEMBERS))
                            .setConstructorPath(paths.get(CollectorStructureEI.Entity.CONSTRUCTOR))
                            .setAnnotationPath(paths.get(CollectorStructureEI.Entity.ANNOTATION))
                            .setMethodPath(paths.get(CollectorStructureEI.Entity.METHOD))
                            .setProtocolPath(emptyPaths.get(CollectorStructureEI.Entity.PROTOCOL))
                            .build();
                })
        ).isInstanceOf(Exception.class)
                .hasMessageContaining("Argument is null or empty");
    }
}
