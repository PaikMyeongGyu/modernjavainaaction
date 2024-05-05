package org.src.chapter7;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class WordCount {

    public static final String SENTENCE =
            "Nel mezzo del cammin di nostra vita "
                    + "mi ritrovai in una selva oscura"
                    + " che la dritta via era smarrita ";

    public static void main(String[] args) {
        System.out.println("Found " + countWordsIteratively(SENTENCE) + " words");
        System.out.println("Found " + countWords(SENTENCE) + " words");
    }

    public static int countWordsIteratively(String s) {
        int counter = 0;
        boolean lastSpace = true;
        for (char c : s.toCharArray()) {
            if (Character.isWhitespace(c)) {
                lastSpace = true;
            }
            else {
                if (lastSpace) {
                    counter++;
                }
                lastSpace = false;
            }
        }
        return counter;
    }

    public static int countWords(String s) {
        // 스트림으로 리듀싱 연산을 통해 단어를 세는 연산
        //Stream<Character> stream = IntStream.range(0, s.length())
        //    .mapToObj(SENTENCE::charAt).parallel();
        Spliterator<Character> spliterator = new WordCounterSpliterator(s);
        Stream<Character> stream = StreamSupport.stream(spliterator, true);

        return countWords(stream);
    }

    private static int countWords(Stream<Character> stream) {
        WordCounter wordCounter = stream.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);
        return wordCounter.getCounter();
    }

    /**
     * 자바에서는 래퍼 객체 없이 character의 정렬 리스트를 표현할 수 있는 게 없어서 만든 래퍼 객체
     */
    private static class WordCounter {

        private final int counter;
        private final boolean lastSpace;

        public WordCounter(int counter, boolean lastSpace) {
            this.counter = counter;
            this.lastSpace = lastSpace;
        }

        public WordCounter accumulate(Character c) {
            if (Character.isWhitespace(c)) {
                return lastSpace ? this : new WordCounter(counter, true);
            }
            else {
                return lastSpace ? new WordCounter(counter + 1, false) : this;
            }
        }

        public WordCounter combine(WordCounter wordCounter) {
            return new WordCounter(counter + wordCounter.counter, wordCounter.lastSpace);
        }

        public int getCounter() {
            return counter;
        }

    }

    private static class WordCounterSpliterator implements Spliterator<Character> {

        private final String string;
        private int currentChar = 0;

        private WordCounterSpliterator(String string) {
            this.string = string;
        }

        @Override
        public boolean tryAdvance(Consumer<? super Character> action) {
            action.accept(string.charAt(currentChar++));
            return currentChar < string.length();
        }

        /**
         * 문자열을 임의로 나누게 되면 하나의 단어를 둘로 계산하는 상황이 spliterator에서 발생할 수 있다.
         * 이 문제를 해결하기 위해서는 직접 단어가 끝나는 위치에서만 분할할 수 있도록 직접 정해주어야 한다.
         */
        @Override
        public Spliterator<Character> trySplit() {
            int currentSize = string.length() - currentChar;
            if (currentSize < 10) {
                return null;
            }
            for (int splitPos = currentSize / 2 + currentChar; splitPos < string.length(); splitPos++) {
                if (Character.isWhitespace(string.charAt(splitPos))) { // 해당 splitPos 지점의 문자열이 빈문자열인 경우에만 나눌 수 있도록 설정
                    Spliterator<Character> spliterator = new WordCounterSpliterator(string.substring(currentChar, splitPos));
                    currentChar = splitPos;
                    return spliterator;
                }
            }
            return null;
        }

        @Override
        public long estimateSize() {
            return string.length() - currentChar;
        }

        @Override
        public int characteristics() {
            return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
        }

    }
}
