import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

import Display.Game;
import Display.Score;
import Gamestates.Gamestate;
import Gamestates.Playing;
import Helpers.Position;
import Helpers.AnimationConstants.EnemyConstants;
import Helpers.CollisionChecker;
import MoveableEntity.Enemy;
import MoveableEntity.MoveableEntity;
import MoveableEntity.Player;
import StaticEntity.TileManager;

public class EnemyTest {

    private Game game;
    private Playing playing;
    private TileManager tileManager;
    private CollisionChecker collisionChecker;
    private Score scoreObject;

    private Enemy enemy;
    private Player player;

    @BeforeEach
    public void setUp() {
        game = new Game();
        playing = new Playing(game);
        tileManager = new TileManager(playing);
        collisionChecker = new CollisionChecker(tileManager);
        scoreObject = new Score();
        Gamestate.state = Gamestate.PLAYING;

        enemy = new Enemy(new Position(2 * Game.tileSize, 3 * Game.tileSize), playing);
        player = new Player(new Position(2 * Game.tileSize, 3 * Game.tileSize), collisionChecker, playing, scoreObject);
    }

    @Test
    public void testCheckPlayerEnemyCollision() {
        Boolean result = collisionChecker.checkPlayerEnemyCollision(player, enemy);

        assertTrue(result);
    }

    @Test
    public void testEnemyMovesDownWhenPlayerBelow() {
        setPosition(enemy, 2, 3);
        setPosition(player, 2, 5);
        updateEnemy(enemy, player, 5);

        assertEquals(EnemyConstants.DOWN, enemy.getEnemyAction());
    }

    @Test
    public void testEnemyMovesUpWhenPlayerAbove() {
        setPosition(enemy, 2, 5);
        setPosition(player, 2, 3);
        updateEnemy(enemy, player, 20);

        assertEquals(EnemyConstants.UP, enemy.getEnemyAction());
    }

    @Test
    public void testEnemyMovesLeftWhenPlayerToTheLeft() {
        setPosition(enemy, 5, 3);
        setPosition(player, 1, 3);
        updateEnemy(enemy, player, 100);

        assertEquals(EnemyConstants.LEFT, enemy.getEnemyAction());
    }

    @Test
    public void testEnemyMovesRightWhenPlayerToTheRight() {
        setPosition(enemy, 2, 3);
        setPosition(player, 5, 3);
        updateEnemy(enemy, player, 20);

        assertEquals(EnemyConstants.RIGHT, enemy.getEnemyAction());
    }

    @Test
    public void testEnemyMovesAroundWalls() {
        setPosition(enemy, 21, 4);
        setPosition(player, 21, 8);
        updateEnemy(enemy, player, 750);

        assertEquals(EnemyConstants.RIGHT, enemy.getEnemyAction());
    }

    private void setPosition(MoveableEntity entity, int x, int y) {
        entity.getPosition().setX(x * Game.tileSize);
        entity.getPosition().setY(y * Game.tileSize);
    }

    private void updateEnemy(Enemy enemy, Player player, int maxNumUpdates) {
        int updates = 0;
        while (updates < maxNumUpdates) {
            enemy.update(player);
            updates++;
        }
    }
}
