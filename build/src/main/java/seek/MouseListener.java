package seek;

import org.lwjgl.glfw.*;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {
    private static MouseListener instance;
    private double scrollX, scrollY;
    private double xpos, ypos, lastY, lastX;
    private boolean mouseButtonPressed[] = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
    private boolean isDragging;
    private double xPos;
    private double yPos;
    private Object ScrollX;
    private Object ScrollY;

    private MouseListener() {
        this.scrollX = 0.0;
        this.scrollY = 0.0;
        this.xpos = 0.0;
        this.ypos = 0.0;
        this.lastX = 0.0;
        this.lastY = 0.0;

    }

    public static MouseListener get() {
        if (MouseListener.instance == null) {
            MouseListener.instance = new MouseListener();

        }
        return MouseListener.instance;
    }

    public static void mousePosCallback(long window, double xpos, double ypos) {
        get().lastX = get().xPos;
        get().lastY = get().yPos;
        get().xPos = xpos;
        get().yPos = ypos;
        get().isDragging = get().mouseButtonPressed[0] || get().mouseButtonPressed[1] || get().mouseButtonPressed[2];
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        if (action == GLFW_PRESS) {
            if (button < get().mouseButtonPressed.length) {
                get().mouseButtonPressed[button] = true;
            }
        } else if (action == GLFW_RELEASE) {
            if (button < get().mouseButtonPressed.length) {
                get().mouseButtonPressed[button] = false;
                get().isDragging = false;
            }
        }

    }

    public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
        get().scrollX = xOffset;
        get().scrollY = yOffset;
    }

    public static void endFrame() {
        get().scrollX = 0;
        get().scrollY = 0;
        get().lastX = get().xPos;
        get().lastY = get().yPos;
    }

    public static float getX() {
        return (float) get().xPos;
    }

    public static float getY() {
        return (float) get().yPos;

    }

    public static float getDx() {
        return (float) (get().lastX - get().xPos);

    }

    public static float getDy() {
        return (float) (get().lastY - get().yPos);
    }

    public static float getScrollX() {
        return (float) get().ScrollX;
    }

    public static float getScrollY() {
        return (float) get().ScrollY;
    }

    public static boolean isDragging() {
        return get().isDragging;
    }

    public static boolean mouseButtonDown(int button) {
        if (button < get().mouseButtonPressed.length) {
        } else {
            return get().mouseButtonPressed[button];

        }
        return false;
    }
    
}
