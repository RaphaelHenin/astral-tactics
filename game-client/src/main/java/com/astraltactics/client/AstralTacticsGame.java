package com.astraltactics.client;

import com.astraltactics.client.input.GridSelection;
import com.astraltactics.client.rendering.IsometricProjection;
import com.astraltactics.core.grid.Cell;
import com.astraltactics.core.grid.Grid;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import java.util.Optional;

public final class AstralTacticsGame extends ApplicationAdapter {
    private final Grid grid = new Grid(10, 10);
    private final IsometricProjection projection = new IsometricProjection(96, 48);
    private final GridSelection selection = new GridSelection();
    private final Vector2 pointer = new Vector2();
    private final Color normal = new Color(0.16f, 0.23f, 0.30f, 1);
    private final Color hovered = new Color(0.25f, 0.65f, 0.75f, 1);
    private final Color selected = new Color(0.95f, 0.65f, 0.20f, 1);
    private ShapeRenderer shapes;
    private FitViewport viewport;
    private String lastTitle = "";

    @Override public void create() {
        shapes = new ShapeRenderer();
        var camera = new OrthographicCamera();
        viewport = new FitViewport(1200, 800, camera);
        camera.position.set(0, -216, 0);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override public boolean touchDown(int x, int y, int pointerIndex, int button) {
                if (button != Input.Buttons.LEFT) return false;
                updateHover(x, y);
                selection.selectHovered();
                return true;
            }
        });
    }

    private void updateHover(int x, int y) {
        int bottomY = Gdx.graphics.getHeight() - 1 - y;
        if (x < viewport.getScreenX() || x >= viewport.getScreenX() + viewport.getScreenWidth()
                || bottomY < viewport.getScreenY()
                || bottomY >= viewport.getScreenY() + viewport.getScreenHeight()) {
            selection.hover(Optional.empty());
            return;
        }
        viewport.unproject(pointer.set(x, y));
        selection.hover(projection.cellAt(pointer.x, pointer.y, grid));
    }

    @Override public void render() {
        updateHover(Gdx.input.getX(), Gdx.input.getY());
        ScreenUtils.clear(0.055f, 0.075f, 0.11f, 1);
        viewport.apply();
        shapes.setProjectionMatrix(viewport.getCamera().combined);
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        for (int row = 0; row < grid.rows(); row++) {
            for (int column = 0; column < grid.columns(); column++) {
                var cell = new Cell(column, row);
                shapes.setColor(selection.selected().filter(cell::equals).isPresent() ? selected
                        : selection.hovered().filter(cell::equals).isPresent() ? hovered : normal);
                diamond(cell, true);
            }
        }
        shapes.end();
        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(0.40f, 0.48f, 0.57f, 1);
        for (int row = 0; row < grid.rows(); row++) {
            for (int column = 0; column < grid.columns(); column++) diamond(new Cell(column, row), false);
        }
        shapes.end();
        String title = "Astral Tactics | Survol : " + label(selection.hovered())
                + " | Selection : " + label(selection.selected());
        if (!title.equals(lastTitle)) { Gdx.graphics.setTitle(title); lastTitle = title; }
    }

    private static String label(Optional<Cell> cell) {
        return cell.map(c -> "(" + c.column() + ", " + c.row() + ")").orElse("aucune");
    }

    private void diamond(Cell cell, boolean filled) {
        var center = projection.center(cell);
        float x = (float) center.x(), y = (float) center.y();
        float w = (float) projection.tileWidth() / 2, h = (float) projection.tileHeight() / 2;
        if (filled) {
            shapes.triangle(x - w, y, x, y + h, x + w, y);
            shapes.triangle(x - w, y, x + w, y, x, y - h);
        } else {
            shapes.line(x - w, y, x, y + h);
            shapes.line(x, y + h, x + w, y);
            shapes.line(x + w, y, x, y - h);
            shapes.line(x, y - h, x - w, y);
        }
    }

    @Override public void resize(int width, int height) {
        if (width > 0 && height > 0) viewport.update(width, height, false);
    }

    @Override public void dispose() {
        Gdx.input.setInputProcessor(null);
        shapes.dispose();
    }
}
