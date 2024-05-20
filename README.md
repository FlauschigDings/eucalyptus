# EventSystem

## How to Use

### Gradle (Kotlin DSL)
```gradle
repositories {
    mavenCentral()
}
dependencies {
    implementation("de.flauschig.eucalyptus:core:{version}")
}
```
### Gradle (Groovy DSL)
```gradle
repositories {
    mavenCentral()
}
dependencies {
    implementation 'de.flauschig.eucalyptus:core:{version}'
}
```
### Maven
```xml
<dependency>
  <groupId>de.flauschig.eucalyptus</groupId>
  <artifactId>core</artifactId>
  <version>{version}</version>
</dependency>
```
you find the version in the `Packages`.
## Usage
The Event System library allows you to create and manage events efficiently. Here’s how you can use it in both Kotlin and Java.
### Kotlin
#### Event
```kotlin
data class ExampleEvent(val text: String) : Event()
```
#### Listener
```kotlin
class ExampleListener : EventListener {
    @EventHandler
    fun exampleEvent(exampleEvent: ExampleEvent) {
        println(exampleEvent.text)
    }
}
```
#### Event Register, Dispatch and Subscribe / Unsubscribe
```kotlin
fun example() {
    val registry = Registry(arrayOf(
        ExampleEvent::class.java
    ))

    val listener = ExampleListener()

    registry.subscribe(listener)
    registry.addEvent(ExampleEvent("Rainbowdash"))
    registry.unsubscribe(listener)
}
```
### Java
#### Event
```java
public class ExampleEvent extends Event {
    private final String text;

    public ExampleEvent(final String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}
```
#### Listener
```java
public class ExampleListener implements EventListener {
    @EventHandler
    public void exampleEvent(ExampleEvent exampleEvent) {
        System.out.println(exampleEvent.getText());
    }
}
```
#### Event Register, Dispatch and Subscribe / Unsubscribe
```java
public class Example {
    public static void example() {
        Registry registry = new Registry(new Class<?>[]{
            ExampleEvent.class
        });

        ExampleListener listener = new ExampleListener();

        registry.subscribe(listener);
        registry.addEvent(new ExampleEvent("Rainbowdash"));
        registry.unsubscribe(listener);
    }
}
```

The Event System is a lightweight Kotlin (and Java-compatible) library designed to facilitate event-driven programming in your projects. With this library, you can easily implement event listeners, register event handlers, and dispatch events throughout your application.

## Contributing

Contributions are welcome! If you find any bugs or have suggestions for improvements, please open an issue or submit a pull request on the GitHub repository.

### Manual Publishing

Alternatively, you can manually publish the library to your GitHub Package Registry (GPR) using the provided Gradle wrapper.

 1. Make sure you have configured your GitHub credentials in your `~/.gradle/gradle.properties` file:
```properties
gpr.user=<your GitHub username>
gpr.password=<your GitHub personal access token>
```
2. Run the following command to publish the library to GPR:
```cmd
./gradlew publish
```
## License
This project is licensed under the MIT License - see the [LICENSE](https://github.com/FlauschigDings/eucalyptus/blob/master/LICENSE) file for details.
