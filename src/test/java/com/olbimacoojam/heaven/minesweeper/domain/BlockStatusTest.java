package com.olbimacoojam.heaven.minesweeper.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class BlockStatusTest {
    @ParameterizedTest
    @MethodSource("provideBlockStatesForIsClick")
    void isClicked(BlockStatus input, boolean expected) {
        assertThat(input.isClicked()).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("provideBlockStatesForIsMine")
    void isMine(BlockStatus input, boolean expected) {
        assertThat(input.isMine()).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("provideBlockStatesForNextLeftClickStatus")
    void nextLeftClickStatus(BlockStatus status, ClickType clickType, boolean isMine, BlockStatus expected) {
        assertThat(status.nextStatus(clickType, isMine)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("provideBlockStatesForNextRightClickStatus")
    void nextRightClickStatus(BlockStatus status, ClickType clickType, boolean isMine, BlockStatus expected) {
        assertThat(status.nextStatus(clickType, isMine)).isEqualTo(expected);
    }

    private static Stream<Arguments> provideBlockStatesForIsClick() {
        return Stream.of(
                Arguments.of(BlockStatus.MINE, false),
                Arguments.of(BlockStatus.CLICKED, true),
                Arguments.of(BlockStatus.UNCLICKED, false),
                Arguments.of(BlockStatus.FLAG, false),
                Arguments.of(BlockStatus.QUESTION_MARK, false)
        );
    }

    private static Stream<Arguments> provideBlockStatesForIsMine() {
        return Stream.of(
                Arguments.of(BlockStatus.MINE, true),
                Arguments.of(BlockStatus.CLICKED, false),
                Arguments.of(BlockStatus.UNCLICKED, false),
                Arguments.of(BlockStatus.FLAG, false),
                Arguments.of(BlockStatus.QUESTION_MARK, false)
        );
    }

    private static Stream<Arguments> provideBlockStatesForNextLeftClickStatus() {
        return Stream.of(
                Arguments.of(BlockStatus.UNCLICKED, ClickType.LEFT, false, BlockStatus.CLICKED),
                Arguments.of(BlockStatus.FLAG, ClickType.LEFT, false, BlockStatus.CLICKED),
                Arguments.of(BlockStatus.QUESTION_MARK, ClickType.LEFT, false, BlockStatus.CLICKED),
                Arguments.of(BlockStatus.CLICKED, ClickType.LEFT, false, BlockStatus.CLICKED),
                Arguments.of(BlockStatus.UNCLICKED, ClickType.LEFT, true, BlockStatus.MINE),
                Arguments.of(BlockStatus.FLAG, ClickType.LEFT, true, BlockStatus.MINE),
                Arguments.of(BlockStatus.QUESTION_MARK, ClickType.LEFT, true, BlockStatus.MINE),
                Arguments.of(BlockStatus.CLICKED, ClickType.LEFT, true, BlockStatus.MINE),
                Arguments.of(BlockStatus.MINE, ClickType.LEFT, true, BlockStatus.MINE)
        );
    }

    private static Stream<Arguments> provideBlockStatesForNextRightClickStatus() {
        return Stream.of(
                Arguments.of(BlockStatus.UNCLICKED, ClickType.RIGHT, false, BlockStatus.FLAG),
                Arguments.of(BlockStatus.FLAG, ClickType.RIGHT, false, BlockStatus.QUESTION_MARK),
                Arguments.of(BlockStatus.QUESTION_MARK, ClickType.RIGHT, false, BlockStatus.UNCLICKED),
                Arguments.of(BlockStatus.CLICKED, ClickType.RIGHT, false, BlockStatus.CLICKED),
                Arguments.of(BlockStatus.MINE, ClickType.RIGHT, true, BlockStatus.MINE)
        );
    }
}