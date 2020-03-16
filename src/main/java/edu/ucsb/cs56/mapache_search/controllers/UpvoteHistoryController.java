package edu.ucsb.cs56.mapache_search.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import edu.ucsb.cs56.mapache_search.entities.AppUser;
import edu.ucsb.cs56.mapache_search.entities.UserVote;
import edu.ucsb.cs56.mapache_search.membership.AuthControllerAdvice;
import edu.ucsb.cs56.mapache_search.repositories.UserRepository;
import edu.ucsb.cs56.mapache_search.repositories.VoteRepository;

@Controller
public class UpvoteHistoryController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private AuthControllerAdvice controllerAdvice;

    @GetMapping("/user/upvotehistory")
    public String upvoteHist (Model model, OAuth2AuthenticationToken token){
        AppUser user = userRepository.findByUid(controllerAdvice.getUid(token)).get(0);        
        List<UserVote> byUser = voteRepository.findByUserAndUpvote(user, true);

        model.addAttribute("userVotes", byUser);
        return "user/upvotehistory";
    }

    @GetMapping("/user/upvoteascending")
    public String upvoteHistAscending (Model model, OAuth2AuthenticationToken token){
        AppUser user = userRepository.findByUid(controllerAdvice.getUid(token)).get(0);        
        List<UserVote> byUser = voteRepository.findByUserAndUpvoteOrderByTimestampAsc(user, true);

        model.addAttribute("userVotes", byUser);
        return "user/upvoteascending";
    }

    @GetMapping("/user/upvotedescending")
    public String upvoteHistDescending (Model model, OAuth2AuthenticationToken token){
        AppUser user = userRepository.findByUid(controllerAdvice.getUid(token)).get(0);        
        List<UserVote> byUser = voteRepository.findByUserAndUpvoteOrderByTimestampDesc(user, true);

        model.addAttribute("userVotes", byUser);
        return "user/upvotedescending";
    }
}

