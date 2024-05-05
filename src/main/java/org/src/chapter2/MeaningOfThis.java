package org.src.chapter2;

public class MeaningOfThis {

    public final int value = 4;

    // 해당 질문은 value 객체가 어디에 종속되는가와 this가 가리키는 게 어떤 객체인지를 묻는 것이다.
    // value = 6은 doIt 메서드에 종속되어있다, value = 5는 Runnable 객체에 종속되어있고
    // value = 10은 run 메서드 내부에 종속되어있다. 이 때 this가 가리키는 건,
    // 해당 호출을 하는 객체를 기리킨다. 따라서, this.value는 r 객체의 value이므로 5이다.
    public void doIt() {
        int value = 6;
        Runnable r = new Runnable() {
            public final int value = 5;
            @Override
            public void run() {
                int value = 10;
                System.out.println(this.value);
            }
        };
        r.run();
    }

    public static void main(String... args) {
        MeaningOfThis m = new MeaningOfThis();
        m.doIt(); // ???
    }

}
