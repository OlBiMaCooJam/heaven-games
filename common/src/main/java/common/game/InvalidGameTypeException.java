package common.game;

public class InvalidGameTypeException extends RuntimeException {
    private static final String MESSAGE = "잘못된 게임 타입입니다.";

    public InvalidGameTypeException() {
        super(MESSAGE);
    }
}
