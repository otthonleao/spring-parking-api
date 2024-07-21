package dev.otthon.parkingapi.enums;


import java.util.Random;

public enum Role {
    ROLE_ADMIN,
    ROLE_CLIENTE;

    public static Role getRandomRole() {
        Role[] roles = Role.values();
        return roles[new Random().nextInt(roles.length)];
    }
}


