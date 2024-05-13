package org.src.chapter13;

public interface Resizable extends Drawable {

    int getWidth();
    int getHeight();
    void setWidth(int width);
    void setHeight(int height);
    void setAbsoluteSize(int width, int height);
    /**
     * 아래의 건 이 인터페이스를 상속 받은 클래스의 구현을 개선해달라는 요청에,
     * 추가된 메서드 인터페이스로 이게 추가되면 기존에 이 코드를 상속 받은 모든 클래스에 영향을 받음.
     * 만약에 setRelativeSize가 없는 경우엔 바이너리 호환성으로 인해 동작 자체에는 문제가 없겠지만,
     * 미래의 누군가가 쓰는 순간부터 에러가 발생함.
     * 자바 8 버전 이하에서 공개된 API는 고치기 쉽지 않음
     * 하지만 Default 메서드를 이용하면 이 문제를 해결이 가능함!
     */
    //void setRelativeSize(int widthFactor, int heightFactor);
    default void setRelativeSize(int wFactor, int hFactor) {
        setAbsoluteSize(getWidth() / wFactor, getHeight() / hFactor);
    }
}
