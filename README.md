# Console Component | By ReStudios

### About 
it is a Swing console component. here you can display text that the user cannot change (including text BEFORE the system text), you can also use spoilers - text with its own font and color, after clicking on which it is irrevocably replaced by another.

*somewhat similar to the Run output console in development environments by JetBrains*

### Usage
#### "Hello world"
```java
JFrame frame = new JFrame();
// setup frame

ConsoleComponent console = new ConsoleComponent();

frame.getContentPane().add(console, BorderLayout.CENTER);
```

#### Output system text
```java
ConsoleComponent console = new ConsoleComponent();

console.writeSystemText("some text");
```

#### Handling user input
```java
ConsoleComponent console=new ConsoleComponent();
console.setInputProcessor(new Handler());

class Handler implements InputProcessor {
    @Override
    public void accept(String userInput) {
        // output to console user input
        System.out.println("You typed: "+userInput);
    }
}
```

#### Creating spoilers
```java
ConsoleComponent console = new ConsoleComponent();

console.writeClickableSpoiler("click me", "woohoo, you clicked!");
```