import java.awt.*;
import java.util.concurrent.TimeUnit;

public class MouseMover {
    public static void main(String[] args) {
        try {
            Robot robot = new Robot();
            while (true) {
                // Get the current mouse position
                Point currentPos = MouseInfo.getPointerInfo().getLocation();
                
                // Move the mouse by one pixel and move it back
                robot.mouseMove(currentPos.x + 1, currentPos.y);
                TimeUnit.SECONDS.sleep(1);  // Wait for one second
                robot.mouseMove(currentPos.x, currentPos.y);
                
                // Wait 60 seconds before repeating (modify the time as desired)
                TimeUnit.SECONDS.sleep(60);
            }
        } catch (AWTException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
