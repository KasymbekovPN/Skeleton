# Skeleton

## Общее
Библиотека предназначена для сериализации/десериализации 
данных экземпляра класса.

## Аннотации
Библиотека содержит две аннотации.

### **@SkeletonClass**
1. Данной аннотацией помечается класс, данные которого 
   мы хотим стерилизовать.
2. Аннотация имеет поле *name*, которое должно содержать 
   уникальное имя стерилизуемого класса.

### **@SkeletonMember**
1. Данной аннотацией помечаются члены класса, данные которых 
   должны быть сериализованны.
2. Аннотация содержит поле *name*, которое должно быть заполнено,
   если поле представляет собой пользовательский класс.
   
### Пример класса

```java
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;

@SkeletonClass(name = "InnerExampleClass")
class InnerExampleClass {

   @SkeletonMember
   private int intValue;
   
   /* some code */
}

@SkeletonClass(name="ExampleClass")
class ExampleClass{
    
    @SkeletonMember
    private Set<Integer> intSet;
    
    @SkeletonMember(name = "InnerExampleClass")
   private InnerExampleClass innerExampleClass;
    
    /* some code */
}
```

## Сериализация класса

### 1. Создание экземпляра с идентификаторами задачи (task) и обработчиков (handler).
```java
import org.KasymbekovPN.Skeleton.lib.processing.context.ids;

class Demo{
    void run(){
       ContextIds contextIds = new SKSimpleContextIds(
               "taskId",
               "signature",
               "specific",
               "custom",
               "collection",
               "map"
       );
    }
}
```

### 2. Создание путей для работы с коллектором
```java
import org.KasymbekovPN.Skeleton.lib.collector.path;

class Demo{
    void run(){
        CollectorPath classPath = new SKCollectorPath(
                Collections.singletonList("class"),
                ObjectNode.ei()
        );

        CollectorPath membersPath = new SKCollectorPath(
                Collections.singletonList("members"),
                ObjectNode.ei()
        );       
    }
}
```

### 3. Создание обработчиков частей
```java
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart;

class Demo{
    void run(){
        
        ClassHeaderPartHandler classHeaderPartHandler = new SKClassHeaderPartHandler(
                "type",
                "name",
                "modifiers"
        );


        ClassMembersPartHandler classMembersPartHandler = new SKClassMembersPartHandler(
                "kind",
                "type",
                "className",
                "modifiers",
                "arguments"
        );
    }
}
```

### 4. Создание контекста

```java
import org.KasymbekovPN.Skeleton.custom.functional.extractor.annotation.AnnotationExtractor;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.ClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.state.SKClassContextStateMemento;
import org.KasymbekovPN.Skeleton.lib.collector.SKCollector;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.SKContextStateCareTaker;

class Demo {
   void run() {
      ClassContext context = new SKClassContext(
              contextIds,
              new SKContextStateCareTaker<>(),
              classPath,
              membersPath,
              new SKCollector(),
              classHeaderPartHandler,
              classMembersPartHandler
      );
   }
}
```

### 5. Создание чекеров

```java
import org.KasymbekovPN.Skeleton.lib.functional.checker.SKSimpleChecker;

import java.util.function.Function;

class Demo {
   void run() {
      Function<Class<?>, Boolean> specificChecker = new SKSimpleChecker<>(int.class, float.class);
      
      Function<String, Boolean> customChecker = new SKSimpleChecker<>("SomeInnerClass");

      Function<Field, Boolean> collectionChecker = new CollectionTypeChecker.Builder()
              .addTypes(Set.class, List.class)
              .addArguments(Integer.class, String.class)
              .build();

      Function<Field, Boolean> mapChecker =  new MapTypeChecker.Builder()
              .addTypes(Map.class)
              .addKeyArgumentTypes(Integer.class)
              .addValueArgumentTypes(Integer.class)
              .build();
   }
}
```

### 6. Создание контекстного процессора.

```java
import org.KasymbekovPN.Skeleton.custom.functional.extractor.annotation.AnnotationExtractor;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.ClassContext;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.ContextProcessor;

class Demo {
   void run() {
      Task<ClassContext> task = new ContextTask<>("taskId");
      task
              .add(new ClassSignatureTaskHandler("signature"))
              .add(new ClassSpecificTaskHandler(specificChecker, "specific"))
              .add(new ClassCustomTaskHandler(customChecker, new AnnotationExtractor(), "custom"))
              .add(new ClassContainerTaskHandler(collectionChecker, "collection"))
              .add(new ClassContainerTaskHandler(mapChecker, "map"));

      ContextProcessor<ClassContext> processor = new ContextProcessor<>();
      processor.add(task);
   }
}
```

### 7. Сериализация

```java
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;

class Demo {
   void run() {

      context.getContextStateCareTaker().push(
              new SKClassContextStateMemento(
                      TestClass.class,
                      new AnnotationExtractor()
              )
      );
       
      processor.handle(context);

      /* node contains serialized data */
      Node node = context.getCollector().getNode();
   }

   @SkeletonClass(name = "TestClass")
   private static class TestClass {

      @SkeletonMember
      private int intValue = 123;
   }
}
```

## todo : instance serialization

## todo : node writing 

## todo : node deserialization

## todo : instance deserialization

## todo : instance serialization

## todo : other

