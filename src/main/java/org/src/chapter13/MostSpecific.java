package org.src.chapter13;

public class MostSpecific {

    public static void main(String... args) {
        new C().hello();
        new E().hello();
        new G().hello();
    }

    /**
     * 아래의 A, B, C를 보면 A의 Hello를 B가 오버라이딩해 값을 바꿨다.
     * C에서 B, A를 상속 받아 아무런 구현이 없는데 이 때, A가 호출될까? B가 호출될까?
     * 이는 아래의 규칙을 따른다.
     * 1. 클래스 내부에서 상속된 것, 슈퍼클래스에서 정의된 게 디폴트 보다 우선순위이다.
     * 2. B는 A를 상속 받았다. 서브 인터페이스가 우선이다.
     * 3. 1,2로 우선순위가 나오지 않는다면, 상속 받는 메서드는 명시적으로 디폴트 메서드를 오버라이드해야 한다.
     */
    static interface A {

        public default void hello() {
            System.out.println("Hello from A");
        }

    }

    static interface B extends A {

        @Override
        public default void hello() {
            System.out.println("Hello from B");
        }

    }

    static class C implements B, A {}

    static class D implements A {}

    // 이 경우에 D에서 호출되는 hello는 A의 hello이므로
    // 더 구체적인 Default 메서드가 아닌 B가 호출
    static class E extends D implements B, A {}


    static class F implements B, A {

        @Override
        public void hello() {
            System.out.println("Hello from F");
        }

    }

    // 직접 상속 받아 오버라이드한 건, B와 F가 동일함.
    // 하지만 F는 상속, B는 인터페이스 상속이므로 슈퍼클래스인 F가 선택됨.
    static class G extends F implements B, A {}

}
