package com.nitrogen.myme.objects;

import android.net.Uri;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Meme {
    private String name;
    private String description;
    private Author author;
    private Date creationDate;
    private List<Tag> tags;
    private int memeID;
    private static int lastMemeID = 0;
    public abstract Uri getThumbnailPath();

    public Meme(final String name, final List<Tag> tags) {
        this.name = name;
        this.description = null;
        this.author = null;
        this.creationDate = new Date();
        this.tags = tags;
        this.memeID = lastMemeID++;
    }

    // accessors
    public String getName() { return name; }

    public int getMemeID() { return memeID; }

    public List<Tag> getTags() { return this.tags; }

    public List<String> getStringTags() {
        List<String> temp = new ArrayList<>();
        for(int i = 0; i<tags.size();i++) {
            temp.add(tags.get(i).getTagName());
        }
        return temp;
    }

    @Override
    public String toString() {
        return name;
    }
}
