package com.holyhero.blog.controllers;

import com.holyhero.blog.models.Post;
import com.holyhero.blog.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog")
    public String blog(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog";
    }

    @GetMapping("/blog/add")
    public String addBlog(Model model) {
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String addPostBlog(@RequestParam String title, @RequestParam String anons,
                              @RequestParam String full_text, Model model) {
        Post post = new Post(title, anons, full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String getBlogDetails(Model model, @PathVariable(value = "id") long id) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        Post post = postRepository.findById(id).orElseThrow();
        post.setViews(post.getViews() + 1);
        postRepository.save(post);
        Optional<Post> optional = postRepository.findById(id);
        ArrayList<Post> result = new ArrayList<>();
        optional.ifPresent(result::add);
        model.addAttribute("post", result);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String doBlogEdit(Model model, @PathVariable(value = "id") long id) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> result = new ArrayList<>();
        post.ifPresent(result::add);
        model.addAttribute("post", result);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String addPostBlog(@RequestParam String title, @RequestParam String anons,
                              @RequestParam String full_text,@PathVariable(value = "id") long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String deletePostBlog(@PathVariable(value = "id") long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/blog";
    }

}
