package org.KasymbekovPN.Skeleton.custom.format.collector;

import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Collector Structure Entity Item class. Testing of:")
public class CollectorStructureEITest {

    private static final Logger log = LoggerFactory.getLogger(CollectorStructureEITest.class);
    private static final String SUFFIX = "EI";

    private Map<String, CollectorStructureEI.Entity> creationMethodNames;

    @BeforeEach
    void beforeEach(){
        creationMethodNames = new HashMap<>();
        for (CollectorStructureEI.Entity value : CollectorStructureEI.Entity.values()) {
            creationMethodNames.put(
                    value.toString().toLowerCase() + SUFFIX,
                    value
            );
        }
    }

    @Test
    @DisplayName("Entity Item Creation static Methods")
    void testEntityItemCreationMethods() throws InvocationTargetException, IllegalAccessException {

        Method[] declaredMethods = CollectorStructureEI.class.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            String methodName = declaredMethod.getName();
            int modifiers = declaredMethod.getModifiers();
            if (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers)){
                if (creationMethodNames.containsKey(methodName)){
                    EntityItem entityItem = (EntityItem) declaredMethod.invoke(CollectorStructureEI.classEI());
                    assertThat(entityItem).isEqualTo(new CollectorStructureEI(creationMethodNames.get(methodName)));
                    creationMethodNames.remove(methodName);
                    log.info("Method '{}' is checked.", methodName);
                }
            }
        }

        if (creationMethodNames.size() != 0){
            log.error("Not all creation method were implemented : {}", creationMethodNames);
        }
        assertThat(creationMethodNames.size()).isEqualTo(0);
    }
}
