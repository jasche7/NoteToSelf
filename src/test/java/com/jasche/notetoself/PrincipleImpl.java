package com.jasche.notetoself;

import java.security.Principal;

class PrincipleImpl implements Principal {
    @Override
    public String getName() {
        return "testUser";
    }
}
