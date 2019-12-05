package common.game;

public class InvalidGameResultException extends RuntimeException {
    private static final String MESSAGE = "게임 결과가 잘못 되었습니다.";

    public InvalidGameResultException() {
        super(MESSAGE);
    }
}
