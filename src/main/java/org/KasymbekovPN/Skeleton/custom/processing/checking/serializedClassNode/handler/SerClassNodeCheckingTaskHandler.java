package org.KasymbekovPN.Skeleton.custom.processing.checking.serializedClassNode.handler;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.checking.serializedClassNode.context.SerClassNodeContext;
import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class SerClassNodeCheckingTaskHandler extends BaseContextTaskHandler {

    private static final Logger log = LoggerFactory.getLogger(SerClassNodeCheckingTaskHandler.class);

    private Set<String> withoutMembersPart = new HashSet<>();
    private Map<String, Set<String>> wrongTypeMembers = new HashMap<>();

    public SerClassNodeCheckingTaskHandler(Result result) {
        super(result);
    }

    @Override
    protected void check(Context context, Task<Context> task) {
        SerClassNodeContext cxt = (SerClassNodeContext) context;

        CollectorPath membersCollectorPath = cxt.getMembersCollectorPath();
        Map<String, ObjectNode> classNodes = cxt.getClassNodes();
        for (Map.Entry<String, ObjectNode> entry : classNodes.entrySet()) {
            String className = entry.getKey();
            ObjectNode classNode = entry.getValue();

            Optional<Node> maybeMembersPart = classNode.getChild(membersCollectorPath);
            if (maybeMembersPart.isPresent()){
                ObjectNode membersPart = (ObjectNode) maybeMembersPart.get();

                Map<String, Node> memberNodes = membersPart.getChildren();
                for (Map.Entry<String, Node> memberEntry : memberNodes.entrySet()) {
                    String memberName = memberEntry.getKey();
                    ObjectNode memberNode = (ObjectNode) memberEntry.getValue();

                    Optional<String> checkingResult = checkMember(memberNode, memberName, cxt);
                    checkingResult.ifPresent(s -> updateWrongTypeMembers(className, s));
                }
            } else {
                withoutMembersPart.add(className);
                log.error("Node {} doesn't contain members part", className);
            }
        }
    }

    @Override
    protected void doIt(Context context) {
        if (withoutMembersPart.size() != 0 || wrongTypeMembers.size() != 0){
            success = false;
        }
    }

    private Optional<String> checkMember(ObjectNode memberNode, String memberName, SerClassNodeContext cxt){
        ClassMembersPartHandler classMembersPartHandler = cxt.getClassMembersPartHandler();
        SimpleChecker<String> systemTypeChecker = cxt.getSystemTypeChecker();
        SimpleChecker<String> customTypeChecker = cxt.getCustomTypeChecker();

        String checkingResult = "";

        Optional<String> maybeClassName = classMembersPartHandler.getClassName(memberNode);
        if (maybeClassName.isPresent()){
            String className = maybeClassName.get();

            if (systemTypeChecker.check(className) || customTypeChecker.check(className)){
                return  Optional.empty();
            } else {
                checkingResult = memberName + "has wrong 'className'";
            }
        } else {
            checkingResult = memberName + " doesn't contain property 'className'";
        }

        log.error(checkingResult);
        return Optional.of(checkingResult);
    }

    private void updateWrongTypeMembers(String className, String checkingResult){
        if (wrongTypeMembers.containsKey(className)){
            wrongTypeMembers.get(className).add(checkingResult);
        } else {
            wrongTypeMembers.put(className, new HashSet<>(Collections.singleton(checkingResult)));
        }
    }
}
