# Skeleton

## Общее
Библиотека предназначена для сериализации/дематериализации 
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

## todo : nodes

## todo : collector

## todo : common processing

## todo : class serialization

## todo : instance serialization

## todo : node writing 

## todo : node deserialization

## todo : instance deserialization

## todo : instance serialization

## todo : other

