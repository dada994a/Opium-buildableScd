package we.devs.opium.event.impl;

public class EventPlayerJump {
    private boolean pre;

    public EventPlayerJump(boolean pre) {
        this.pre = pre;
    }

    public boolean isPre() {
        return pre;
    }
}
