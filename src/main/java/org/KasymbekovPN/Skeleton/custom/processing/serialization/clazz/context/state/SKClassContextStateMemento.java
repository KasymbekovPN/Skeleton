package org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.state;

import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;
import org.KasymbekovPN.Skeleton.lib.functional.OptFunction;
import org.KasymbekovPN.Skeleton.lib.result.SKSimpleResult;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class SKClassContextStateMemento implements ClassContextStateMemento {

    private static final Logger log = LoggerFactory.getLogger(SKClassContextStateMemento.class);

    private static final Class<? extends SimpleResult> RESULT_CLASS = SKSimpleResult.class;

    private final OptFunction<Pair<Class<? extends Annotation>, Annotation[]>, Annotation> annotationExtractor;

    private SimpleResult result;
    private Class<?> clazz;
    private String className;
    private Set<Field> remainingFields;

    public SKClassContextStateMemento(Class<?> clazz,
                                      OptFunction<Pair<Class<? extends Annotation>, Annotation[]>, Annotation> annotationExtractor) {
        this.clazz = clazz;
        this.annotationExtractor = annotationExtractor;
    }

    @Override
    public void validate() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        result = createResultInstance();

        checkClazz();
        checkClazzAnnotation();
        getAndCheckRemainingFields();

        if (!result.isSuccess()){
            log.warn("{}", result.getStatus());
        }
    }

    @Override
    public SimpleResult getValidationResult() {
        return result;
    }

    @Override
    public Class<?> getClazz() {
        return clazz;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public Set<Field> getRemainingFields() {
        return remainingFields;
    }

    private SimpleResult createResultInstance() throws NoSuchMethodException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        Constructor<? extends SimpleResult> constructor = RESULT_CLASS.getConstructor();
        return constructor.newInstance();
    }

    private void checkClazz(){
        if (result.isSuccess() && clazz == null){
            result.setFailStatus("The clazz is null");
        }
    }

    private void checkClazzAnnotation(){
        if (result.isSuccess()){
            Optional<Annotation> maybeAnnotation
                    = annotationExtractor.apply(new MutablePair<>(SkeletonClass.class, clazz.getDeclaredAnnotations()));
            if (maybeAnnotation.isPresent()){
                className = ((SkeletonClass) maybeAnnotation.get()).name();
            } else {
                result.setFailStatus("The clazz hasn't annotation");
            }
        }
    }

    private void getAndCheckRemainingFields(){
        if (result.isSuccess()){
            remainingFields = new HashSet<>();
            for (Field field : clazz.getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers())){
                    Optional<Annotation> maybeAnnotation
                            = annotationExtractor.apply(new MutablePair<>(SkeletonMember.class, field.getDeclaredAnnotations()));
                    if (maybeAnnotation.isPresent()){
                        remainingFields.add(field);
                    }
                }
            }

            if (remainingFields.size() == 0){
                result.setFailStatus("The remaining fields is empty");
            }
        }
    }
}
