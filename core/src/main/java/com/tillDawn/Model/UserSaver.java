package com.tillDawn.Model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
public class UserSaver {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static ArrayList<User> loadUsers(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) return new ArrayList<>();
            return mapper.readValue(file, new TypeReference<ArrayList<User>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void saveUsers(ArrayList<User> users, String path) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(path), users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveOrUpdateUser(User user, String path) {
        ArrayList<User> users = loadUsers(path);
        boolean updated = false;

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getName().equals(user.getName())) {
                users.set(i, user);
                updated = true;
                break;
            }
        }

        if (!updated) {
            users.add(user);
        }

        saveUsers(users, path);
    }
}
