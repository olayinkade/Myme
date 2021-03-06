package com.nitrogen.myme.tests.Business;

import com.nitrogen.myme.business.AccessMemes;
import com.nitrogen.myme.business.AccessTags;
import com.nitrogen.myme.business.Exceptions.InvalidMemeException;
import com.nitrogen.myme.business.MemeValidator;
import com.nitrogen.myme.business.SearchMemes;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.objects.Tag;
import com.nitrogen.myme.persistence.MemesPersistence;
import com.nitrogen.myme.persistence.TagsPersistence;
import com.nitrogen.myme.persistence.stubs.MemesPersistenceStub;
import com.nitrogen.myme.persistence.stubs.TagsPersistenceStub;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class MemeValidatorTest {

    private AccessTags accessTags;
    private AccessMemes accessMemes;
    private MemeValidator memeValidator;
    private Meme goodMeme;// nice
    private Meme badMeme;
    private List<Tag> goodTags;
    private List<Tag> badTagsNonExistent;
    private List<Tag> badTagsEmpty;
    private List<Tag> badTagsDuplicates;

    @Before
    public void setUp() {
        System.out.println("Starting tests for MemeValidator.\n");
        // stub database
        TagsPersistence tagsPersistenceStub = new TagsPersistenceStub();
        MemesPersistence memesPersistenceStub = new MemesPersistenceStub(tagsPersistenceStub);

        accessMemes = new AccessMemes(memesPersistenceStub);
        accessTags = new AccessTags(tagsPersistenceStub);
        assertNotNull(accessMemes);
        assertNotNull(accessTags);

        memeValidator = new MemeValidator(memesPersistenceStub, tagsPersistenceStub);
        assertNotNull(memeValidator);

        goodMeme = new Meme("good name");
        badMeme = new Meme("");

        badTagsNonExistent = new ArrayList<>();
        badTagsNonExistent.add(new Tag("???"));
        badTagsNonExistent.add(new Tag("^^^"));
        badTagsNonExistent.add(new Tag("~~~"));

        badTagsEmpty = new ArrayList<>();

        badTagsDuplicates = new ArrayList<>();
        badTagsDuplicates.add(new Tag("copy"));
        badTagsDuplicates.add(new Tag("copy"));

        goodTags = new ArrayList<>();
        goodTags.add(new Tag("dank"));
        goodTags.add(new Tag("wholesome"));
    }

    @Test
    public void testValidateName_validName() {
        System.out.println("Testing validateName() with a name which we know is valid");

        goodMeme.setName("good name");
        assert (memeValidator.validateName(goodMeme));
        goodMeme.setName(generateStringOfLen(memeValidator.MAX_NAME_LEN));// edge case
        assert (memeValidator.validateName(goodMeme));
        goodMeme.setName(generateStringOfLen(1));// edge case
        assert (memeValidator.validateName(goodMeme));
    }

    @Test(expected = InvalidMemeException.class)
    public void testValidateName_invalidNameLength0() {
        System.out.println("Testing validateName() with a name which we know is invalid, because name length is 0");

        // names of length 0 are invalid
        badMeme.setName("");

        // should throw expected exception
        memeValidator.validateName(badMeme);
    }

    @Test(expected = InvalidMemeException.class)
    public void testValidateName_invalidNameAboveMaxLength() {
        System.out.println("Testing validateName() with a name which we know is invalid, because name too long");

        // names longer than MAX_NAME_LEN are invalid
        badMeme.setName(generateStringOfLen(memeValidator.MAX_NAME_LEN +1));

        // should throw expected exception
        memeValidator.validateName(badMeme);
    }

    @Test(expected = InvalidMemeException.class)
    public void testValidateName_invalidNameAlreadyExists() {
        System.out.println("Testing validateName() with a name which we know is invalid, because name is not unique");

        badMeme.setName(accessMemes.getMemes().get(0).getName());

        // should throw expected exception
        memeValidator.validateName(badMeme);
    }

    // helper method
    private String generateStringOfLen (int len) {
        String result = "";

        for(int i = 0 ; i < len ; i++)
            result += " ";

        return  result;
    }

    @Test
    public void testValidateTags_validTags() {
        System.out.println("Testing validateTags() with tags we know are valid");

        goodMeme.setTags(goodTags);

        assert(memeValidator.validateTags(goodMeme));
    }

    @Test(expected = InvalidMemeException.class)
    public void testValidateTags_invalidTagsNonExistent() {
        System.out.println("Testing validateTags() with tags we know are invalid, tags not in app");

        badMeme.setTags(badTagsNonExistent);

        // should throw expected exception
        memeValidator.validateTags(badMeme);
    }

    @Test(expected = InvalidMemeException.class)
    public void testValidateTags_invalidTagsEmpty() {
        System.out.println("Testing validateTags() with tags we know are invalid, empty ArrayList");

        badMeme.setTags(badTagsEmpty);

        // should throw expected exception
        memeValidator.validateTags(badMeme);
    }

    @Test(expected = InvalidMemeException.class)
    public void testValidateTags_invalidTagsDuplicates() {
        System.out.println("Testing validateTags() with tags we know are invalid, duplicate tags");

        badMeme.setTags(badTagsDuplicates);

        // should throw expected exception
        memeValidator.validateTags(badMeme);
    }

    @After
    public void tearDown() {
        System.out.println("\nFinished tests.\n");
    }
}
