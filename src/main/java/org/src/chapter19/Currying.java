package org.src.chapter19;

import java.util.function.DoubleUnaryOperator;

// 커링은 여러 인수를 받는 함수를 하나의 인수 혹은 그보다 작은 인수를 가진 함수로 변환해주는 기법을 의미한다.
public class Currying {

    public static void main(String[] args) {
        DoubleUnaryOperator convertCtoF = curriedConverter(9.0 / 5, 32);
        DoubleUnaryOperator convertUSDtoGBP = curriedConverter(0.6, 0);
        DoubleUnaryOperator convertKmtoMi = curriedConverter(0.6214, 0);

        System.out.printf("24 °C = %.2f °F%n", convertCtoF.applyAsDouble(24));
        System.out.printf("US$100 = £%.2f%n", convertUSDtoGBP.applyAsDouble(100));
        System.out.printf("20 km = %.2f miles%n", convertKmtoMi.applyAsDouble(20));

        DoubleUnaryOperator convertFtoC = expandedCurriedConverter(-32, 5.0 / 9, 0);
        System.out.printf("98.6 °F = %.2f °C", convertFtoC.applyAsDouble(98.6));
    }

    // 섭씨를 화씨로 바꾸는 경우 아래와 가은 메서드로 변환 패턴을 표현할 수 있다.
    // 그런데 변수가 3개로 매번 인수에 변환 요소와 기준치를 넣는 건 불편하고 오타 발생확률이 높아진다.
    // 그렇다고 메서드를 새로 정의하면 해당 로직을 재활용하지 못한다. 그래서 변환에서 고정된 요소를
    // 미리 넣어놓은 변환 함수를 생성하는 역할을 하는 팩토리를 정의하는 방법이 아래와 같다.
    static double converter(double x, double y, double z) {
        return x * y + z;
    }

    static DoubleUnaryOperator curriedConverter(double y, double z) {
        return (double x) -> x * y + z;
    }

    static DoubleUnaryOperator expandedCurriedConverter(double w, double y, double z) {
        return (double x) -> (x + w) * y + z;
    }
}
