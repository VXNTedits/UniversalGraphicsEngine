import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Master {
    private int tickrate;   // how many times per second the scene is calculated
    private boolean running;
    private Window window;
    private Container c;
    private Renderer r;
    private Input input;
    private double scale;
    private double mSens;

    private int getPerTick; //used for getPerTick() method

    public Master(int tickrate, String title, int width, int height, double scale, double mSens) {
        getPerTick = 0;

        this.tickrate = tickrate;
        this.mSens = mSens;
        //TODO: Resources (data) should be initialized here
        window = new Window(title,width,height,scale);
        this.scale = scale;
        c = new Container();
        r = new Renderer(window, window.getHeight(), window.getHeight());
        input = new Input(window.getCanvas(),scale);
        run(tickrate);
    }

    public void run(int tickrate) {     // main reference loop. all timekeep is taken in reference to here

        // TODO: Account for unprocessed time

        running = true;

        int ticksElapsed = 0;
        double initTime = System.nanoTime() * 1.0e-9;
        double t0_sec = 0;
        double t1_sec = 1.0/tickrate;
        double dt_sec = 0.0;
        double dt_unpro = 0.0;
        boolean processing = false;

        while(running) {

            t0_sec = 0;
            t1_sec = 1.0/tickrate;
            dt_sec = 0.0;
            dt_unpro = 0.0;

            t0_sec = System.nanoTime() * 1.0e-9;

            while(dt_sec < t1_sec) {
                dt_sec = ( System.nanoTime() * 1.0e-9 ) - t0_sec;
            }

            // TODO: give render instructions here
            r.drawMesh(c.getMeshContainer().get(0),64250);
            window.update();
            r.clear();
            r.update(ticksElapsed);

            // Cube rotation based on WASD
            // TODO: Change rotation listening to mouse pos movement and implement translation on WASD + ctrl/space for Y

            if ( input.isKey(KeyEvent.VK_W) ) {
                r.getSc().setRotZ(r.getSc().getRotZ()+0.1);
            }
            if ( input.isKey(KeyEvent.VK_S) ) {
                r.getSc().setRotZ(r.getSc().getRotZ()-0.1);
            }
            if ( input.isKey(KeyEvent.VK_A) ) {
                r.getSc().setRotY(r.getSc().getRotY()+0.1);
            }
            if ( input.isKey(KeyEvent.VK_D) ) {
                r.getSc().setRotY(r.getSc().getRotY()-0.1);
            }

            r.getSc().setRotX(input.getMouseY()*mSens);
            //r.getSc().setRotY(input.getMouseX()*mSens);

            input.update();
            dt_sec = 0.0;
            ticksElapsed++;
            dt_unpro = ( System.nanoTime() * 1.0e-9 ) - t0_sec;

            if( getPerTick(tickrate/2) ) {
                System.out.println( "Tick " + ticksElapsed + "; Update rate = " + round( 1000.0 / (dt_unpro*1.0e+3), 1 )+ " FPS ");
            }
        }
    }
    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public boolean getPerTick(int rate) {
        getPerTick++;
        if( rate <= getPerTick ) {
            getPerTick = 0;
            return true;
        } else {
            return false;
        }
    }
}
