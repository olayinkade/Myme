package com.nitrogen.myme.business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.nitrogen.myme.application.Services;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.objects.Tag;
import com.nitrogen.myme.persistence.MemesPersistence;

public class AccessFavourites {
    private MemesPersistence memePersistence;
    private List<Meme> memes;
    private Meme meme;
    private int currentMeme;

    public AccessFavourites() {
        memePersistence = Services.getMemesPersistence();
        memes = null;
        meme = null;
        currentMeme = 0;
    }

    public List<Meme> getMemes() {
        memes = new ArrayList<Meme>();

        for (Meme meme : memePersistence.getMemeSequential()) {
            if (meme.getIsFavourite()) {
                memes.add(meme);
            }
        }

        return Collections.unmodifiableList(memes);
    }

}

