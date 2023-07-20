package com.example.homework27;

import com.example.homework27.exceptions.BadIndexException;
import com.example.homework27.exceptions.InvalidItemException;
import com.example.homework27.exceptions.StorageIsFullException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


public class StringListImplTest {

    private StringListImpl out;

    @BeforeEach
    void beforeEach() {
        out = new StringListImpl(5);
        out.add("privet");
        out.add("poka");
    }

    @Test
    void addTest() {
        String[] expected = {"hi", "ok"};

        assertThat(out.add("ok")).isIn(expected);
    }

    @Test
    void addNullElementTest() {
        assertThatExceptionOfType(InvalidItemException.class)
                .isThrownBy(() -> out.add(null));
    }

    @Test
    void addWhenStorageFullTest() {
        out.add("ok");
        out.add("ok");
        out.add("ok");
        assertThatExceptionOfType(StorageIsFullException.class)
                .isThrownBy(() -> out.add("hi"));
    }

    @Test
    void addWithIndexTest() {
        String[] expected = {"bye", "privet"};
        assertThat(out.add(0, "bye")).isIn(expected);
        assertThat(out.toArray()).isEqualTo(expected);
    }

    @Test
    void addInLastPositionTest() {
        out.add("ok");
        out.add("ok");
        String[] expected = {"privet", "poka", "ok", "bye"};
        assertThat(out.add(3, "bye")).isIn(expected);
        assertThat(out.toArray()).isEqualTo(expected);
    }

    public static Stream<Arguments> provideParamsForBadIndexExceptionTest() {
        return Stream.of(
                Arguments.of(-2, "hi"),
                Arguments.of(10, "hi")
        );
    }

    @ParameterizedTest
    @MethodSource("provideParamsForBadIndexExceptionTest")
    void addWithIndexNegativeTest(int index, String string) {
        assertThatExceptionOfType(BadIndexException.class)
                .isThrownBy(() -> out.add(index, string));
    }

    @Test
    void setTest() {
        String[] expected = {"privet", "bye"};
        assertThat(out.set(1, "bye")).isIn(expected);
    }

    @Test
    void removeByElementTest() {
        String[] expected = {"privet"};
        assertThat(out.remove("poka"))
                .isEqualTo("poka")
                .isNotIn(expected);
    }

    @Test
    void removeByElementNegativeTest() {
        assertThatExceptionOfType(InvalidItemException.class)
                .isThrownBy(() -> out.remove("ok"));
    }

    @Test
    void removeByIndexTest() {
        String[] expected = {"privet"};
        assertThat(out.remove(1))
                .isEqualTo("poka")
                .isNotIn(expected);
    }

    @Test
    void sizeTest() {
        assertThat(out.size())
                .isEqualTo(2);
    }

    @Test
    void containsTest() {
        assertThat(out.contains("poka"))
                .isTrue();
    }

    @Test
    void containsNegativeTest() {
        assertThat(out.contains("ok"))
                .isFalse();
    }

    @Test
    void indexOfTest() {
        assertThat(out.indexOf("poka"))
                .isEqualTo(1);
    }

    @Test
    void lastIndexOfTest() {
        assertThat(out.lastIndexOf("poka"))
                .isEqualTo(1);
        assertThat(out.lastIndexOf("ok")).isEqualTo(-1);
    }

    @Test
    void getTest() {
        assertThat(out.get(1))
                .isEqualTo("poka");
    }

    @Test
    void equalsTest() {
        StringListImpl test1 = new StringListImpl(5);
        test1.add("privet");
        test1.add("poka");

        StringListImpl test2 = new StringListImpl(5);
        test2.add("privet");
        test2.add("ok");

        assertThat(out.equals(test1)).isTrue();
        assertThat(out.equals(test2)).isFalse();
    }

    @Test
    void isEmptyTest() {
        StringListImpl testList = new StringListImpl();
        assertThat(out.isEmpty()).isFalse();
        assertThat(testList.isEmpty()).isTrue();
    }

    @Test
    void clearTest() {
        out.clear();
        assertThat(out.isEmpty()).isTrue();
    }
}