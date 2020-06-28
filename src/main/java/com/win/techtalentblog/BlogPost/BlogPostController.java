package com.win.techtalentblog.BlogPost;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BlogPostController {

    @Autowired
    private BlogPostRepository blogPostRepository;

    private static List<BlogPost> posts = new ArrayList<>();

    @GetMapping(value = "/")
    public String index(BlogPost blogPost, Model model) {
        posts.removeAll(posts);
        for (BlogPost post : blogPostRepository.findAll()) {
            posts.add(post);
        }
        model.addAttribute("posts", posts);
        return "BlogPost/index";
    }

    @GetMapping(value = "/blogposts/all")
    public String allPosts(BlogPost blogPost, Model model) {
        posts.removeAll(posts);
        for (BlogPost post : blogPostRepository.findAll()) {
            posts.add(post);
        }
        model.addAttribute("posts", posts);
        return "BlogPost/posts";
    }

    @GetMapping(value = "/blogposts/new")
    public String newBlog(BlogPost blogPost) {
        return "BlogPost/new";
    }

    @PostMapping(value = "/blogposts")
    public String addNewBlogPost(BlogPost blogPost, Model model) {
        blogPostRepository.save(blogPost);

        model.addAttribute("blogPost", blogPost);

        return "BlogPost/result";
    }

    @RequestMapping(value = "/blogposts/{id}", method = RequestMethod.GET)

    public String editPostWithId(@PathVariable Long id, BlogPost blogPost, Model model) {

        Optional<BlogPost> post = blogPostRepository.findById(id);
        // Test if post actually has anything in it
        if (post.isPresent()) {
            // Unwrap the post from Optional shell
            BlogPost actualPost = post.get();
            model.addAttribute("blogPost", actualPost);
        }
        return "BlogPost/edit";
    }

    @RequestMapping(value = "/blogposts/update/{id}", method = RequestMethod.POST)
    public String updateExistingPost(@PathVariable Long id, BlogPost blogPost, Model model) {
        Optional<BlogPost> post = blogPostRepository.findById(id);
        if (post.isPresent()) {
            BlogPost actualPost = post.get();
            actualPost.setTitle(blogPost.getTitle());
            actualPost.setAuthor(blogPost.getAuthor());
            actualPost.setBlogEntry(blogPost.getBlogEntry());

            blogPostRepository.save(actualPost);
            model.addAttribute("blogPost", actualPost);
        }

        return "BlogPost/result";
    }

}