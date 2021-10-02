package hello.core;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HelloLombok {

    private String name;
    private int age;

    public static void main(String[] args) {
        HelloLombok helloLombok = new HelloLombok();
        // Lombok 이 Annotation 에 있는 것들 알아서 해줌 (Getter, Setter,,,)
        helloLombok.setName("asdfas");

        System.out.println("name = " + helloLombok);

    }
}
