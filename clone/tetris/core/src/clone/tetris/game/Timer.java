package clone.tetris.game;

public abstract class Timer implements Runnable{
    private int interval;
    private int delay;
    private boolean toContinue = true;
    @Override
    public void run() {
        try {
            Thread.sleep(delay);
            while(true) {
                execute();
                Thread.sleep(interval);
                if(!toContinue) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public abstract void execute();

    public void setInterval(int n) {
        interval = n;
    }
    public void setDelay(int n) {
        delay = n;
    }
    public void stop() {
        toContinue = false;
    }
    public void pause(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
