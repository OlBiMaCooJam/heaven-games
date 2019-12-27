package com.olbimacoojam.heaven.domain;

public enum RoomState {
    READY, PLAYING;

    public boolean isPlaying() {
        return this.equals(PLAYING);
    }
}
