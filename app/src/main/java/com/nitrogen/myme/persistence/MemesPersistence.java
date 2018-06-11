package com.nitrogen.myme.persistence;

import java.util.List;
import com.nitrogen.myme.objects.Meme;

public interface MemesPersistence {
    List<Meme> getMemeSequential();

    List<Meme> getMemeRandom(Meme currentMeme);

//    List<Meme> getMemesByTag(String tag);

    Meme insertMeme(Meme currentMeme);

    Meme updateMeme(Meme currentMeme);

    void deleteMeme(Meme currentMeme);
}
