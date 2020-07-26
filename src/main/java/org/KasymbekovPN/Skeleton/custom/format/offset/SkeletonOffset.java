package org.KasymbekovPN.Skeleton.custom.format.offset;

import org.KasymbekovPN.Skeleton.lib.format.offset.Offset;

public class SkeletonOffset implements Offset {

    private static final String REPLACED_STRING = "%offset%";
    private static final String OFFSET_INIT_VALUE = "";
    private static final int COUNTER_INIT_VALUE = 0;

    private final String offsetStep;

    private String offset;
    private int counter;

    public SkeletonOffset(String offsetStep) {
        this.offsetStep = offsetStep;
        reset();
    }

    @Override
    public void reset() {
        offset = OFFSET_INIT_VALUE;
        counter = COUNTER_INIT_VALUE;
    }

    @Override
    public void inc() {
        offset = String.valueOf(offsetStep).repeat(Math.max(0, ++counter));
    }

    @Override
    public void dec() {
        if (counter > 0){
            counter--;
        }
        offset = String.valueOf(offsetStep).repeat(counter);
    }

    @Override
    public String get() {
        return offset;
    }

    @Override
    public String prepareTemplate(String template) {
        return template.replace(REPLACED_STRING, offset);
    }
}
