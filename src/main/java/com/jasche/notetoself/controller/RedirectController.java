package com.jasche.notetoself.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for redirecting to root URI.
 */
@Controller
@Profile("prod")
public class RedirectController {

    /**
     * This mapping to /private for GET requests returns a redirect directive such that
     * the user can be redirected to the root page.
     * @return  redirect directive to root page
     */
    @GetMapping("/private")
    public String redirectToRoot() {
        return "redirect:/";
    }
}
