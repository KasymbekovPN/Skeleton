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
      private int intValue;

      public TestClass(int intValue){
         this.intValue = intValue;
      }
   }
}
```
## Сериализация экземпляра
### 1. Создание экземпляра с идентификаторами задачи (task) и обработчиков (handler).
```java
import org.KasymbekovPN.Skeleton.lib.processing.context.ids;

class Demo{
    void run(){
       ContextIds contextIds = new SKSimpleContextIds(
               "taskId",
               "header",
               "specific",
               "custom",
               "collection",
               "map"
       );
    }
}
```
### 2. Создание контекстного процессора.
```java
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.InstanceContext;
import org.KasymbekovPN.Skeleton.lib.processing.task.context.ContextTask;

class Demo {
   void run() {
      ContextTask<InstanceContext> task = new ContextTask<>("taskId");
      task
              .add(new InstanceHeaderTaskHandler("header"))
              .add(new InstanceSpecificTaskHandler("specific"))
              .add(new InstanceCustomTaskHandler("custom"))
              .add(new InstanceCollectionTaskHandler("collection"))
              .add(new InstanceMapTaskHandler("map"));

      ContextProcessor<InstanceContext> processor = new ContextProcessor<InstanceContext>();
      processor.add(task);
   }
}
```
### 3. Создание путей для работы с коллектором
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
### 4. Создание обработчиков частей
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
### 5. Создание контекста

```java
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.SKInstanceContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.state.SKInstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.lib.collector.SKCollector;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.SKContextStateCareTaker;

import java.util.HashMap;

class Demo {
   void run() {

      HashMap<String, ObjectNode> classNodes = new HashMap<>();
      /* fill classNodes */

      SKInstanceContext context = new SKInstanceContext(
              contextIds,
              new SKContextStateCareTaker<>(),
              classNodes,
              new SKCollector(),
              processor,
              classPath,
              membersPath,
              classHeaderPartHandler,
              classMembersPartHandler,
              new AnnotationExtractor()
      );
   }
}
```
### 6. Сериализация

```java
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;

class Demo {
   void run() {

      TestClass testClass = new TestClass(123);
      context.getContextStateCareTaker().push(
              new SKInstanceContextStateMemento(
                      testClass,
                      classNodes.get("TestClass"),
                      context
              )
      );
       
      processor.handle(context);

      /* node contains serialized data */
      Node node = context.getCollector().getNode();
   }

   @SkeletonClass(name = "TestClass")
   private static class TestClass {

      @SkeletonMember
      private int intValue;

      public TestClass(int intValue){
          this.intValue = intValue;
      }
   }
}
```
## Запись узла
### 1. Создание экземпляра с идентификаторами задачи (task) и обработчиков (handler).

```java
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.SKMultiContextIds;

class Demo() {
   void run() {
      SKMultiContextIds<EntityItem> contextIds = new SKMultiContextIds.Builder<EntityItem>("taskId", "private")
              .add(ArrayNode.ei(), new SKSimpleContextIds("taskId", "array"))
              .add(ObjectNode.ei(), new SKSimpleContextIds("taskId", "object"))
              .build();
   }
}
```
### 2. Создание формат-обработчика записи

```java
import org.KasymbekovPN.Skeleton.custom.format.offset.SKOffset;
import org.KasymbekovPN.Skeleton.custom.format.writing.json.handler.JsonWritingFormatterHandler;

class Demo() {
   void run() {
      SKOffset offset = new SKOffset("    ");

      JsonWritingFormatterHandler writingFormatterHandler = new JsonWritingFormatterHandler.Builder(offset)
              .addFormatter(ObjectNode.ei(), new JsonObjectWritingFormatter(offset))
              .addFormatter(ArrayNode.ei(), new JsonArrayWritingFormatter(offset))
              .addFormatter(BooleanNode.ei(), new JsonBooleanWritingFormatter(offset))
              .addFormatter(CharacterNode.ei(), new JsonCharacterWritingFormatter(offset))
              .addFormatter(NumberNode.ei(), new JsonNumberWritingFormatter(offset))
              .addFormatter(StringNode.ei(), new JsonStringWritingFormatter(offset))
              .addFormatter(InvalidNode.ei(), new JsonInvalidWritingFormatter(offset))
              .build();
   }
}
```
### 3. Создание контекстного процессора.

```java
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.WritingContext;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.handler.WritingArrayTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.handler.WritingObjectTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.handler.WritingPrimitiveTaskHandler;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.ContextProcessor;
import org.KasymbekovPN.Skeleton.lib.processing.task.context.ContextTask;

class Demo {
   void run() {
      ContextTask<WritingContext> task = new ContextTask<>("taskId");
      task.add(new WritingArrayTaskHandler("array"))
              .add(new WritingObjectTaskHandler("object"))
              .add(new WritingPrimitiveTaskHandler("private"));

      ContextProcessor<WritingContext> processor = new ContextProcessor<WritingContext>();
      processor.add(task);
   }
}
```
### 4. Создание контекста

```java
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.SKWritingContext;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.state.SKWritingContextStateMemento;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.SKContextStateCareTaker;

class Demo {
   void test() {
      SKWritingContext context = new SKWritingContext(
              contextIds,
              writingFormatterHandler,
              processor,
              new SKContextStateCareTaker<>()
      );
   }
}
```
### 5. Запись
```java
class Demo{
    void run(){
       Node node;
       /* get node for writing*/
   
       context.getContextStateCareTaker().push(
               new SKWritingContextStateMemento(
                       node
               )
       );
       processor.handle(context);
   
       /* wrote string*/
       String string = writingFormatterHandler.getDecoder().getString();
    }
}
```

## Десериализация узла
### 1. Создание экземпляра с идентификаторами задачи (task) и обработчиков (handler).

```java
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.entity.node.NodeEI;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.SKMultiContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.SKSimpleContextIds;

class Demo {
   void run() {
      SKMultiContextIds<EntityItem> contextIds = new SKMultiContextIds.Builder<EntityItem>(new SKSimpleContextIds("taskId", "init"))
              .add(NodeEI.objectEI(), new SKSimpleContextIds("taskId", "object"))
              .add(NodeEI.arrayEI(), new SKSimpleContextIds("taskId", "array"))
              .add(NodeEI.booleanEI(), new SKSimpleContextIds("taskId", "boolean"))
              .add(NodeEI.characterEI(), new SKSimpleContextIds("taskId", "character"))
              .add(NodeEI.numberEI(), new SKSimpleContextIds("taskId", "number"))
              .add(NodeEI.stringEI(), new SKSimpleContextIds("taskId", "string"))
              .build();
   }
}
```
### 2. Создание контекстного процессора
```java
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.handler.*;
import org.KasymbekovPN.Skeleton.lib.processing.task.context.ContextTask;

class Demo {
   void run() {
      ContextTask<Des2NodeContext> task = new ContextTask<>("taskId");
      task.add(new Des2NodeInitTaskHandler("init"))
              .add(new Des2NodeObjectTaskHandler("object"))
              .add(new Des2NodeArrayTaskHandler("array"))
              .add(new Des2NodeBooleanTaskHandler("boolean"))
              .add(new Des2NodeCharacterTaskHandler("character"))
              .add(new Des2NodeNumberTaskHandler("number"))
              .add(new Des2NodeStringTaskHandler("string"));

      ContextTask<Des2NodeContext> processor = new ContextTask<Des2NodeContext>();
      processor.add(task);
   }
}
```
### 3. Создание чекеров
```java
import org.KasymbekovPN.Skeleton.custom.functional.checker.NumberCharacterChecker;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.entity.node.NodeEI;
import org.KasymbekovPN.Skeleton.lib.functional.checker.MultiChecker;
import org.KasymbekovPN.Skeleton.lib.functional.checker.SKMultiChecker;
import org.KasymbekovPN.Skeleton.lib.functional.checker.SKSimpleChecker;

class Demo {
   void run() {
      MultiChecker<EntityItem, Character> entityBeginChecker = new SKMultiChecker.Builder<EntityItem, Character>()
              .add(NodeEI.arrayEI(), new SKSimpleChecker<>('['))
              .add(NodeEI.booleanEI(), new SKSimpleChecker<>('T', 't', 'F', 'f'))
              .add(NodeEI.characterEI(), new SKSimpleChecker<>('\''))
              .add(NodeEI.objectEI(), new SKSimpleChecker<>('{'))
              .add(NodeEI.stringEI(), new SKSimpleChecker<>('"'))
              .add(NodeEI.numberEI(), new NumberCharacterChecker())
              .build();

      MultiChecker<EntityItem, Character> valueBeginChecker = new SKMultiChecker.Builder<EntityItem, Character>(new SKSimpleChecker<>())
              .add(NodeEI.arrayEI(), new SKSimpleChecker<>('[', ','))
              .add(NodeEI.characterEI(), new SKSimpleChecker<>('\''))
              .add(NodeEI.stringEI(), new SKSimpleChecker<>('"'))
              .build();

      MultiChecker<EntityItem, Character> valueEndChecker = new SKMultiChecker.Builder<EntityItem, Character>(new SKSimpleChecker<>())
              .add(NodeEI.numberEI(), new SKSimpleChecker<>(',', ']', '}'))
              .add(NodeEI.booleanEI(), new SKSimpleChecker<>(',', ']', '}'))
              .add(NodeEI.characterEI(), new SKSimpleChecker<>('\''))
              .add(NodeEI.stringEI(), new SKSimpleChecker<>('"'))
              .add(NodeEI.arrayEI(), new SKSimpleChecker<>(']'))
              .add(NodeEI.objectEI(), new SKSimpleChecker<>('}'))
              .build();

      SKSimpleChecker<Character> propertyNameBeginChecker = new SKSimpleChecker<>('"');
      SKSimpleChecker<Character> propertyNameEndChecker = new SKSimpleChecker<>('"');
      SKSimpleChecker<Character> valueNameSeparator = new SKSimpleChecker<>(':');
   }
}
```
### 4. Создание контекста

```java
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.SKDes2NodeContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.state.SKDes2NodeContextStateMemento;
import org.KasymbekovPN.Skeleton.lib.iterator.SKDecrementedCharIterator;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.SKContextStateCareTaker;

class Demo {
   void run() {

      String line;
      /* fill line*/

      SKDecrementedCharIterator iterator = new SKDecrementedCharIterator(line);
      SKDes2NodeContext context = new SKDes2NodeContext(
              contextIds,
              new SKContextStateCareTaker<>(),
              it,
              processor,
              entityBeginChecker,
              valueBeginChecker,
              valueEndChecker,
              propertyNameBeginChecker,
              propertyNameEndChecker,
              valueNameSeparator
      );
   }
}
```
### 5. Десериализация
```java
class Demo{
    void run(){
       context.getContextStateCareTaker().push(
               new SKDes2NodeContextStateMemento(null)
       );
       processor.handle(context);

       /* deserialized data*/
       Node node = context.getContextStateCareTaker().peek().getNode();
    }
}
```



## todo : instance deserialization


