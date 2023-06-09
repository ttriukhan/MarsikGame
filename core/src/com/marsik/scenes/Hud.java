package com.marsik.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import tools.MarsikGame;

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    private static Integer timer;
    private static float timeCount;
    private static Integer score;
    private static Integer health;

    private static Label countdownLabel;
    private static Label scoreLabel;
    private static Label bonusLabel;
    private static Label healthLabel;
    private Label healthL;
    private Label marsikLabel;
    private boolean fixed;

    public Hud(SpriteBatch sb) {
        timer = 0;
        timeCount = 0;
        score = 0;
        health = 100;

        viewport = new FitViewport(MarsikGame.V_WIDTH, MarsikGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%02d", timer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%01d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        bonusLabel = new Label("NO BONUS", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        healthLabel = new Label(String.format("%03d", health), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        healthL = new Label("HEALTH", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        marsikLabel = new Label("SAMPLES", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(marsikLabel).expandX().padTop(10);
        table.add(bonusLabel).expandX().padTop(10);
        table.add(healthL).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(countdownLabel).expandX();
        table.add(healthLabel).expandX();

        stage.addActor(table);
    }

    public void update(float dt) {
        if(!fixed) {
            timeCount += dt;
            if (timeCount >= 1 && timer > 0) {
                timer--;
                countdownLabel.setText(String.format("%02d", timer));
                timeCount = 0;
            }
            if (timer == 0)
                bonusLabel.setText("NO BONUS");
        }
    }

    public static void addBonus(String type, int time) {
        bonusLabel.setText(type);
        countdownLabel.setText(String.format("%02d", time));
        timer = time;
        timeCount = 0;
    }

    public  static void addScore(int value) {
        score += value;
        scoreLabel.setText(String.format("%01d", score));
    }

    public static void healthChange(int value) {
        health += value;
        if (health>100)
            health = 100;
        if (health<0)
            health = 0;
        healthLabel.setText(String.format("%03d", health));
    }

    public void win() {
        bonusLabel.setText("WIN");
        countdownLabel.setText(String.format("%02d", 0));
        fixed = true;
    }

    public void loose() {
        bonusLabel.setText("DEAD");
        countdownLabel.setText(String.format("%02d", 0));
        fixed = true;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
