package com.example.kalarilab.Models;

import java.io.Serializable;
import java.util.List;

public class AdminPanelModel implements Serializable {
    private List<String> usersFullNames;
    private List<String> challenges ;
    private List<String> uris;
    private List<String> urersIds;
    private List<String> levels;

    public AdminPanelModel(List<String> usersFullNames, List<String> challenges, List<String> levels , List<String> uris, List<String> urersIds) {
        this.usersFullNames = usersFullNames;
        this.challenges = challenges;
        this.levels = levels;
        this.uris = uris;
        this.urersIds = urersIds;


    }



    public List<String> getUrersIds() {
        return urersIds;
    }

    public List<String> getUsersFullNames() {
        return usersFullNames;
    }

    public List<String> getChallenges() {
        return challenges;
    }

    public List<String> getUris() {
        return uris;
    }

    public List<String> getLevels() {
        return levels;
    }
}
