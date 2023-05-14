package ru.itmo.wp.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.itmo.wp.Utils;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.PostService;

@Controller
@RequestMapping("/post")
public class PostPage extends Page {
    private final PostService postService;

    public PostPage(PostService postService) {
        this.postService = postService;
    }

    @Guest
    @GetMapping(value = {"/{id}", ""})
    public String post(@PathVariable Optional<String> id, Model model, HttpServletResponse response) {
        String checkedIdString = id.filter(Utils.isParsableToLong).orElse(null);
        Post post = null;

        if (checkedIdString != null) {
            post = postService.findById(Long.parseLong(checkedIdString));
        }

        if (checkedIdString == null || post == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "PostPage";
        }

        model.addAttribute("post", post);
        model.addAttribute("comment", new Comment());

        return "PostPage";
    }

    @PostMapping("/{id}/addComment")
    public String addComment(@PathVariable Optional<String> id,
                             @Valid @ModelAttribute("comment") Comment comment,
                             BindingResult bindingResult,
                             HttpSession session,
                             Model model) {
        String checkedIdString = id.filter(Utils.isParsableToLong).orElse(null);
        Post post = null;

        if (checkedIdString != null) {
            post = postService.findById(Long.parseLong(checkedIdString));
        }

        // no such post
        if (checkedIdString == null || post == null) {
            return "redirect:/post/" + id.get();
        }

        // invalid comment
        if (bindingResult.hasErrors()) {
            model.addAttribute("post", post);
            return "PostPage";
        }

        postService.addComment(comment, post, getUser(session));
        putMessage(session, "Your comment was successfully added");

        return "redirect:/post/" + checkedIdString;
    }
}
