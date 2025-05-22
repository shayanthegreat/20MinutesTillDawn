package com.tillDawn.View;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tillDawn.Controller.MainController;
import com.tillDawn.Controller.ProfileController;
import com.tillDawn.Main;
import com.tillDawn.Model.App;
import com.tillDawn.Model.GameAssetManager;
import com.tillDawn.Model.Result;

public class ProfileView implements Screen {
    private Stage stage;
    private final Table table;
    private final ProfileController controller;

    private final Label titleLabel;
    private final TextButton changeUsernameButton;
    private final TextButton changePasswordButton;
    private final TextButton deleteUserButton;
    private final TextButton changeAvatarButton;
    private final TextButton backButton;
    private Image avatarImage;
    private Texture avatarTexture;
    private Texture backgroundTexture;
    private Image backgroundImage;

    public ProfileView(ProfileController controller, Skin skin) {
        this.controller = controller;

        this.titleLabel = new Label("Profile Menu", skin);
        this.titleLabel.setFontScale(2f);

        this.changeUsernameButton = new TextButton("Change Username", skin);
        this.changePasswordButton = new TextButton("Change Password", skin);
        this.deleteUserButton = new TextButton("Delete Account", skin);
        this.changeAvatarButton = new TextButton("Change Avatar", skin);
        this.backButton = new TextButton("Main Menu", skin);

        this.table = new Table();
    }


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        String avatarPath = App.getInstance().getCurrentUser().getAvatarPath();
        String username = App.getInstance().getCurrentUser().getName();
        avatarTexture = new Texture(Gdx.files.internal(avatarPath));
        avatarImage = new Image(avatarTexture);
        avatarImage.setSize(64, 64);

        Label usernameLabel = new Label(username, GameAssetManager.getInstance().getSkin());
        usernameLabel.setFontScale(1.5f);
        usernameLabel.setColor(Color.WHITE);
        Table topLeftTable = new Table();
        topLeftTable.top().left().pad(30);
        topLeftTable.setFillParent(true);
        topLeftTable.add(avatarImage).size(64, 64).padRight(10);
        topLeftTable.add(usernameLabel).left().align(Align.left);

        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture(Gdx.files.internal("menuBackground.jpg"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);
        stage.addActor(topLeftTable);
        table.setFillParent(true);
        table.center();

        table.add(titleLabel).padBottom(30).colspan(2);
        table.row();

        addButton(changeUsernameButton);
        addButton(changePasswordButton);
        addButton(changeAvatarButton);
        addButton(deleteUserButton);
        addButton(backButton);

        stage.addActor(table);


        changeUsernameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                final TextField usernameField = new TextField("", GameAssetManager.getInstance().getSkin());
                usernameField.setMessageText("Enter new username");

                Dialog dialog = new Dialog("Change Username", GameAssetManager.getInstance().getSkin()) {
                    @Override
                    protected void result(Object object) {
                        if ((Boolean) object) {
                            String newUsername = usernameField.getText().trim();
                            if (!newUsername.isEmpty()) {
                                Result result = controller.changeUsername(newUsername);
                                Gdx.app.postRunnable(() -> showFeedbackDialog(result));
                            } else {
                                Gdx.app.postRunnable(() -> showFeedbackDialog(new Result("Username cannot be empty.", false)));
                            }
                        }
                    }
                };
                dialog.getContentTable().pad(20);
                dialog.setSize(400, 220);
                dialog.getTitleLabel().setFontScale(1.5f);

                dialog.getContentTable().add(new Label("New Username:", GameAssetManager.getInstance().getSkin())).padBottom(10);
                dialog.getContentTable().row();
                dialog.getContentTable().add(usernameField).width(300).padBottom(20);
                dialog.button("Confirm", true);
                dialog.button("Cancel", false);
                dialog.show(stage);
            }
        });

        changePasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                final Skin skin = GameAssetManager.getInstance().getSkin();

                final TextField passwordField = new TextField("", skin);
                passwordField.setMessageText("Enter new password");
                passwordField.setPasswordCharacter('*');
                passwordField.setPasswordMode(true);

                final TextField confirmPasswordField = new TextField("", skin);
                confirmPasswordField.setMessageText("Confirm new password");
                confirmPasswordField.setPasswordCharacter('*');
                confirmPasswordField.setPasswordMode(true);

                Dialog dialog = new Dialog("Change Password", skin) {
                    @Override
                    protected void result(Object object) {
                        if ((Boolean) object) {
                            String newPassword = passwordField.getText().trim();
                            String confirmPassword = confirmPasswordField.getText().trim();

                            Result result = controller.changePassword(newPassword, confirmPassword);
                            Gdx.app.postRunnable(() -> showFeedbackDialog(result));
                        }
                    }
                };

                dialog.getContentTable().pad(30);
                dialog.setSize(500, 350);
                dialog.getTitleLabel().setFontScale(1.5f);

                // Content layout
                dialog.getContentTable().add(new Label("New Password:", skin)).align(Align.left).padBottom(10).row();
                dialog.getContentTable().add(passwordField).width(400).padBottom(20).row();
                dialog.getContentTable().add(new Label("Confirm Password:", skin)).align(Align.left).padBottom(10).row();
                dialog.getContentTable().add(confirmPasswordField).width(400).padBottom(20).row();

                dialog.button("Confirm", true);
                dialog.button("Cancel", false);
                dialog.show(stage);
            }
        });
        deleteUserButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                final Skin skin = GameAssetManager.getInstance().getSkin();

                final TextField passwordField = new TextField("", skin);
                passwordField.setMessageText("Enter your password");
                passwordField.setPasswordMode(true);
                passwordField.setPasswordCharacter('*');

                Dialog dialog = new Dialog("Delete Account", skin) {
                    @Override
                    protected void result(Object object) {
                        if ((Boolean) object) {
                            String password = passwordField.getText().trim();
                            if (!password.isEmpty()) {
                                Result result = controller.deleteAccount(password);
                                Gdx.app.postRunnable(() -> {
                                    showFeedbackDialog(result);
                                    if (result.isSuccess()) {
                                        com.badlogic.gdx.utils.Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
                                            @Override
                                            public void run() {
                                                Main.getInstance().setScreen(new MainView(new MainController(), GameAssetManager.getInstance().getSkin()));
                                            }
                                        }, 1f); // 1 second delay
                                    }
                                });
                            } else {
                                Gdx.app.postRunnable(() ->
                                    showFeedbackDialog(new Result("Password cannot be empty.", false))
                                );
                            }
                        }
                    }

                };

                dialog.getContentTable().pad(30);
                dialog.setSize(500, 300);
                dialog.getTitleLabel().setFontScale(1.5f);
                dialog.getTitleLabel().setColor(Color.RED); // Indicate danger

                Label warningLabel = new Label("Are you sure you want to delete your account?", skin);
                warningLabel.setWrap(true);
                warningLabel.setColor(Color.RED);
                warningLabel.setAlignment(Align.center);

                dialog.getContentTable().add(warningLabel).width(450).padBottom(20).row();
                dialog.getContentTable().add(passwordField).width(400).padBottom(20).row();

                dialog.button("Delete", true).pad(10);
                dialog.button("Cancel", false).pad(10);
                dialog.show(stage);
            }
        });
        changeAvatarButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                Dialog dialog = new Dialog("Change Avatar", GameAssetManager.getInstance().getSkin());
                dialog.setSize(1000, 500);
                dialog.getTitleLabel().setFontScale(1.5f);
                dialog.getContentTable().pad(30);

                Skin skin = GameAssetManager.getInstance().getSkin();
                Table content = dialog.getContentTable();

                content.add(new Label("Select an Avatar:", skin)).colspan(2).padBottom(20);
                content.row();

                Table avatarTable = new Table();
                int count = 0;
                for (String avatarName : controller.getAvailableAvatars()) {
                    final String avatar = avatarName;
                    Image avatarImage = new Image(new Texture(Gdx.files.internal("avatar/" + avatarName)));
                    avatarImage.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            controller.setAvatar("avatar/" + avatar);
                            dialog.hide();
                            updateAvatarImage(App.getInstance().getCurrentUser().getAvatarPath());
                            showFeedbackDialog(new Result("Avatar changed successfully!", true));
                        }
                    });
                    avatarTable.add(avatarImage).size(48, 48).pad(10); // Set layout size here!
                    count++;
                    if (count % 4 == 0) {
                        avatarTable.row();
                    }
                }


                ScrollPane avatarScrollPane = new ScrollPane(avatarTable, skin);
                content.add(avatarScrollPane).width(900).height(300).colspan(2).padBottom(20);
                content.row();

                TextButton uploadButton = new TextButton("Upload New Avatar", skin);
                uploadButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        Main.getInstance().getFileChooser().chooseFile((fileHandle) -> {
                            if (fileHandle != null && fileHandle.exists()) {
                                String path = fileHandle.path().toLowerCase();
                                // Check for common image extensions
                                if (path.endsWith(".png") || path.endsWith(".jpg") || path.endsWith(".jpeg") || path.endsWith(".bmp") || path.endsWith(".gif")) {
                                    controller.setAvatar(fileHandle.path());
                                    updateAvatarImage(App.getInstance().getCurrentUser().getAvatarPath());
                                    showFeedbackDialog(new Result("Avatar uploaded successfully!", true));
                                } else {
                                    showFeedbackDialog(new Result("Selected file is not a supported image format.", false));
                                }
                            } else {
                                showFeedbackDialog(new Result("No image selected.", false));
                            }
                        });
                    }
                });

                content.add(uploadButton).colspan(2).padTop(10);
                dialog.button("Close", false);
                dialog.show(stage);
            }
        });

        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Main.getInstance().setScreen(new MainView(new MainController(), GameAssetManager.getInstance().getSkin()));
            }
        });


    }

    private void showFeedbackDialog(Result result) {
        Dialog feedbackDialog = new Dialog("", GameAssetManager.getInstance().getSkin());

        // Use a wider LabelStyle font if desired
        Label.LabelStyle labelStyle = new Label.LabelStyle(
            GameAssetManager.getInstance().getSkin().getFont("font"),
            result.isSuccess() ? Color.GREEN : Color.RED
        );

        Label messageLabel = new Label(result.getMessage(), labelStyle);
        messageLabel.setWrap(true); // Allow text to wrap
        messageLabel.setAlignment(Align.center);

        feedbackDialog.getContentTable().pad(30);
        feedbackDialog.getContentTable().add(messageLabel).width(450).padBottom(20).center();

        feedbackDialog.button("OK", true).pad(15);
        feedbackDialog.setSize(500, 300); // Bigger size
        feedbackDialog.getTitleLabel().setFontScale(1.5f);
        feedbackDialog.getTitleLabel().setColor(result.isSuccess() ? Color.GREEN : Color.RED);
        feedbackDialog.show(stage);
    }



    private void addButton(TextButton button) {
        button.setScale(1f);
        applyHoverEffect(button);
        table.row();
        table.add(button).width(500).pad(10);
    }

    private void applyHoverEffect(final TextButton button) {
        button.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                button.addAction(Actions.parallel(
                    Actions.scaleTo(1.05f, 1.05f, 0.1f),
                    Actions.color(new Color(1f, 1f, 0.7f, 1f), 0.1f)
                ));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                button.addAction(Actions.parallel(
                    Actions.scaleTo(1f, 1f, 0.1f),
                    Actions.color(Color.WHITE, 0.1f)
                ));
            }
        });
    }

    @Override
    public void render(float delta) {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
    }
    private void updateAvatarImage(String newPath) {
        if (avatarTexture != null) {
            avatarTexture.dispose();
        }
        avatarTexture = new Texture(Gdx.files.internal(newPath));
        avatarImage.setDrawable(new TextureRegionDrawable(avatarTexture));
        avatarImage.setSize(64, 64);
    }

}

