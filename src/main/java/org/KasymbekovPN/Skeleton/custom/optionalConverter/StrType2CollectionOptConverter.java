package org.KasymbekovPN.Skeleton.custom.optionalConverter;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.optionalConverter.OptionalConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class StrType2CollectionOptConverter implements OptionalConverter<Collection<Object>, ObjectNode> {

    private static final Logger log = LoggerFactory.getLogger(StrType2CollectionOptConverter.class);

    private final ClassMembersPartHandler classMembersPartHandler;

    public StrType2CollectionOptConverter(ClassMembersPartHandler classMembersPartHandler) {
        this.classMembersPartHandler = classMembersPartHandler;
    }

    @Override
    public Optional<Collection<Object>> convert(ObjectNode object) {
        Optional<String> maybeClassName = classMembersPartHandler.getClassName(object);
        if (maybeClassName.isPresent()){
            String className = maybeClassName.get();
            switch (className){
                case "java.util.Set":
                    return Optional.of(new HashSet<>());
                case "java.util.Array":
                    return Optional.of(new ArrayList<>());
            }
        } else {
            log.warn("Class name doesn't exist");
        }

        return Optional.empty();
    }
}
