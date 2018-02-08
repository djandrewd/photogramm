package ua.danit.photogramm.rweb.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ua.danit.photogramm.rweb.controllers.ExploreTagResource.RESOURCE_PATH;
import static ua.danit.photogramm.rweb.util.HashTags.parseIntoParts;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;
import ua.danit.photogramm.rweb.model.TextPart;

public class HashTagsTest {

  @Test
  public void testEmpty() {
    assertTrue(parseIntoParts("").isEmpty());
    assertTrue(parseIntoParts(null).isEmpty());
  }

  @Test
  public void testSingleTag() {
    assertEquals(Arrays.asList(new TextPart("You are ", false, null),
        new TextPart("#1", true, RESOURCE_PATH + "/1"), new TextPart(" here!", false, null)),
        parseIntoParts("You are #1 here!"));
  }

  @Test
  public void testNoTag() {
    assertEquals(Collections.singletonList(new TextPart("Here I am", false, null)),
        parseIntoParts("Here I am"));
  }

  @Test
  public void testFewTags() {
    assertEquals(Arrays.asList(new TextPart("#Here", true, RESOURCE_PATH + "/Here"),
        new TextPart("#I", true, RESOURCE_PATH + "/I"),
        new TextPart("#am", true, RESOURCE_PATH + "/am"), new TextPart("#", false, null)),
        parseIntoParts("#Here#I#am#"));
  }


}