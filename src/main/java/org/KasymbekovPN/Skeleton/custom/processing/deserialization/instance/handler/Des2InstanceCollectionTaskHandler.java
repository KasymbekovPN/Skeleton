package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.handler;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.Des2InstanceContext;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.optionalConverter.OptionalConverter;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public class Des2InstanceCollectionTaskHandler extends BaseContextTaskHandler<Des2InstanceContext> {

    private static final Logger log = LoggerFactory.getLogger(Des2InstanceCollectionTaskHandler.class);

    private final String kind;

    private Object instance;
    private Set<Triple<Field, Node, ObjectNode>> members;

    public Des2InstanceCollectionTaskHandler(SimpleResult simpleResult,
                                             String kind) {
        super(simpleResult);
        this.kind = kind;
    }

    @Override
    protected void check(Des2InstanceContext context, Task<Des2InstanceContext> task) {
        String status = "";
        if (context.isValid()){
            members = context.getMembers(kind);
            if (members.size() > 0){
                instance = context.getInstance();
            } else {
                status = "Number of member is zero";
            }
        } else {
            status = "Context isn't valid";
        }

        if (!status.isEmpty()){
            simpleResult.setSuccess(false);
            simpleResult.setStatus(status);

            log.warn(status);
        }
    }

    @Override
    protected void doIt(Des2InstanceContext context) {

        OptionalConverter<Collection<Object>, ObjectNode> strType2CollectionConverter
                = context.getStrType2CollectionConverter();

        for (Triple<Field, Node, ObjectNode> member : members) {
            Field field = member.getLeft();
            Node memberNode = member.getMiddle();
            ObjectNode classMember = member.getRight();

            Optional<Collection<Object>> maybeCollection = strType2CollectionConverter.convert(classMember);

            System.out.println(maybeCollection);

//            Collection<Object> collection = createEmptyCollection(field.getName(), classMember, classMembersPartHandler);

//            Set<Object> objects = new HashSet<Object>();
//
//            objects.add(111);
//            objects.add(222);
//            objects.add(333);
//
//            System.out.println(field.getGenericType().getTypeName());
//
//
//            //< !!!
//            System.out.println(field);
//            System.out.println(memberNode);
//            System.out.println(classMember);
//
////            Optional<Object> maybeValue = extractValue(memberNode);
////            if (maybeValue.isPresent()){
//                field.setAccessible(true);
//                try {
//                    field.set(instance, objects);
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } finally {
//                    field.setAccessible(false);
//                }
////            } else {
////                log.warn("{}: Member '{}' has wrong type", kind, field.getName());
////            }
        }
    }

//    private Collection<Object> createEmptyCollection(String name,
//                                                     ObjectNode classMember,
//                                                     ClassMembersPartHandler classMembersPartHandler){
//
//        Optional<String> maybeClassName = classMembersPartHandler.getClassName(classMember);
//        if (maybeClassName.isPresent()){
//            String className = maybeClassName.get();
//        } else {
//            log.warn("Field '{}': class member doesn't contain classname", name);
//        }
//    }
}
