package we.devs.opium.event;

public class Event {
    private boolean cancelled;

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancel() {
        cancelled = true;
    }
    public void setCancelled(Boolean bool) {
        cancelled = bool;
    }

}
