package seek;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import util.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private int width, height;
    private String title;
    private long glfwWindow;
    
    public float r, g, b, a;
    private boolean fadeToBlack = false;
    
    private static Window window = null;
    
    private static Scene currentScene;
    
    private Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "Seek";
        r = 0;
        g = 0;
        b = 0;
        a = 1;
        
    }
    
    public static void changeScene(int newScene) {
        switch (newScene) {
            case 0:
                currentScene = new LevelEditorScene();
                // currentScene.init();
                break;
            case 1:
                currentScene = new LevelScene();
                break;
            default:
                assert false : "Unknown scene '" + newScene + "'";
                break;
        }
    }
    
    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }
        
        return Window.window;
    }
    
    public void run() {
        System.out.println("Hello, LWJGL " + Version.getVersion() + "!");
        
        init();
        loop();
        
    }
    public void init() {
        // Setup an error callback . . . 
        GLFWErrorCallback.createPrint(System.err).set();
        
        // Initialize GLFW . . . 
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }
        
        // Configure GLFW . . . 
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
        
        // Create the window . . . 
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW Window.");
        }
        
        
        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);
        // Make the OpenGL context . . . 
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-sync . . . 
        glfwSwapInterval(1);
        
        // Make the window visible . . . 
        glfwShowWindow(glfwWindow);

        GL.createCapabilities();
        
        Window.changeScene(0);
    }
    public void loop() {
        float beginTime = Time.getTime();
        float endTime;
        float dt = -1.0f;
        
        while (!glfwWindowShouldClose(glfwWindow)) {
            // Poll events . . . 
            glfwPollEvents();

            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);
            
            if (dt >= 0) 
                currentScene.update(dt);
            }
            
            glfwSwapBuffers(glfwWindow);
            
            endTime = Time.getTime();
            dt = endTime - beginTime;
            beginTime = endTime;
        }
    }
