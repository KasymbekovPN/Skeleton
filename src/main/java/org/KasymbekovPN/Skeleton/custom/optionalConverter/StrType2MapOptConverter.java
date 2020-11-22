package org.KasymbekovPN.Skeleton.custom.optionalConverter;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.optionalConverter.OptionalConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

// TODO: 22.11.2020 test
public class StrType2MapOptConverter implements OptionalConverter<Map<Object, Object>, ObjectNode> {

    private static final Logger log = LoggerFactory.getLogger(StrType2MapOptConverter.class);

    private final ClassMembersPartHandler classMembersPartHandler;

    public StrType2MapOptConverter(ClassMembersPartHandler classMembersPartHandler) {
        this.classMembersPartHandler = classMembersPartHandler;
    }

    @Override
    public Optional<Map<Object, Object>> convert(ObjectNode object) {
        Optional<String> maybeClassName = classMembersPartHandler.getClassName(object);
        if (maybeClassName.isPresent()){
            String className = maybeClassName.get();
            if ("java.util.Map".equals(className)) {
                return Optional.of(new HashMap<>());
            } else {
                log.warn("Wrong type");
            }
        } else {
            log.warn("Class name doesn't exist");
        }

        return Optional.empty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StrType2MapOptConverter that = (StrType2MapOptConverter) o;
        return Objects.equals(classMembersPartHandler, that.classMembersPartHandler);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classMembersPartHandler);
    }
}
