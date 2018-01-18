package game.states;

import engine.render.game.Camera;
import engine.render.gui.GuiObj;
import game.main.Handler;
import game.managers.EntityManager;
import game.managers.GameLogic;
import game.managers.GuiManager;
import game.map.Map;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class GameState extends State {

    private Map map;
    private EntityManager entityManager;
    private GuiObj hpBar;
    private GuiManager guiManager;
    private GameLogic gameLogic;

    public GameState(Handler handler) {
        super(handler);
        map = new Map(handler, 11, 11);
        entityManager = new EntityManager(handler);
        guiManager = new GuiManager(handler);
        gameLogic = new GameLogic(handler);
    }

    public void create() {
        map.create();
        entityManager.create();
        guiManager.addGuiObj(handler.getAssetManager().hpbarEmpty);
        guiManager.addGuiObj(hpBar = handler.getAssetManager().hpbar);
    }

    public void update() {
        entityManager.update();

        guiManager.update();
        hpBar.setScale(Math.max(0, entityManager.getPlayer().getHealth()/100*0.9f), hpBar.getScale().y);

        Camera cam = handler.getRenderer().getCam();
        Vector3f camPos = new Vector3f(entityManager.getPlayer().getPos());
        camPos.y += 4f;
        cam.changePitch(-(Mouse.getDY() * 0.3f));
        cam.changeYaw(Mouse.getDX() * 0.3f);
        cam.setPos(camPos);

        gameLogic.update();
    }

    public void reset() {
        entityManager.getEntityList().clear();
        map.create();
        entityManager.create();
    }

    public Map getMap() {
        return map;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

}
