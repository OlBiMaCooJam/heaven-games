package common.game;

import lombok.Getter;

@Getter
public enum WinLose {
    WIN("WIN"), LOSE("LOSE");

    private String result;

    WinLose(String result) {
        this.result = result;
    }
}
