package org.src.chapter20;

// 자바에서의 단점은, 객체를 사용하려면 생성자가 필요하고, 다른 객체의 내부 필드를 참조하려면 그게 필요함
// 근데 이걸 스칼라에서 자동으로 만들어준다고 하는데, 개인적으로 이게 장점인지는 잘 모르겠음.
// 간편하겠지만, 내부 필드 참조용 getter, setter를 기본으로 제공하면 나중에 관리하기 힘들텐데...
public class Student {

    private String name;
    private int id;

    public static void main(String[] args) {
        Student s = new Student("Raoul", 1);
        System.out.println(s.name);
        s.id = 1337;
        System.out.println(s.id);
    }

    public Student() {}

    public Student(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}

