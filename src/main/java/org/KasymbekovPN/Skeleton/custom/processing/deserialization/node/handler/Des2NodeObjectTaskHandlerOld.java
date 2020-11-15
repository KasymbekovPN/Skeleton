package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.handler;

import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.processing.handler.context.OldBaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeContextOld;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeModeOld;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.finder.FinderOld;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.itr.Des2NodeCharItrOld;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

import java.lang.reflect.InvocationTargetException;

public class Des2NodeObjectTaskHandlerOld extends OldBaseContextTaskHandler<Des2NodeContextOld> {

    public Des2NodeObjectTaskHandlerOld(String id) {
        super(id);
    }

    public Des2NodeObjectTaskHandlerOld(String id, SimpleResult simpleResult) {
        super(id, simpleResult);
    }

    @Override
    protected void check(Des2NodeContextOld context) {}

    @Override
    protected void doIt(Des2NodeContextOld context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {

        boolean done = false;
        StringBuilder name = new StringBuilder();
        State state = State.NAME_BEGIN_FINDING;
        Des2NodeCharItrOld iterator = context.iterator();
        FinderOld finderOld = context.getFinderOld();
        Node parent = context.getParent();

        ObjectNode objectNode = new ObjectNode(parent);
        context.setParent(objectNode);

        while (iterator.hasNext() && !done){
            Character next = iterator.next();

            switch (state){
                case NAME_BEGIN_FINDING:
                    if (finderOld.findPropertyNameBegin(next)){
                        state = State.NAME_END_FINDING;
                        name.setLength(0);
                    } else if (finderOld.findValueEnd(next, Des2NodeModeOld.OBJECT)){
                        done = true;
                    }
                    break;
                case NAME_END_FINDING:
                    if (finderOld.findPropertyNameEnd(next)){
                        state = State.SEPARATOR_FINDING;
                    } else {
                        name.append(next);
                    }
                    break;
                case SEPARATOR_FINDING:
                    if (finderOld.findNameValueSeparator(next)){
                        state = State.NAME_BEGIN_FINDING;
                        context.setMode(Des2NodeModeOld.INIT);
                        context.runProcessor();

                        Node node = context.getNode();
                        objectNode.addChild(name.toString(), node);
                    }
                    break;
            }
        }

        context.setParent(parent);
        context.setNode(objectNode);
    }

    private enum State{
        NAME_BEGIN_FINDING,
        NAME_END_FINDING,
        SEPARATOR_FINDING
    }
}
