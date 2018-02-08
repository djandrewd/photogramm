package ua.danit.photogramm.imgs.services.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ua.danit.photogramm.imgs.services.util.HashTags.parseName;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

/**
 * Test for hash tag comments.
 *
 * @author Andrey Minov
 */
public class HashTagsTest {

  @Test
  public void testNull() {
    assertTrue(parseName(null).isEmpty());
  }

  @Test
  public void testEmpty() {
    assertTrue(parseName("").isEmpty());
  }

  @Test
  public void testFound1() {
    assertEquals(Collections.singletonList("1"), parseName("we are #1 of all!"));
  }

  @Test
  public void testFound2() {
    assertEquals(Arrays.asList("1", "of"), parseName("we are #1 #of all!"));
  }

  @Test
  public void testNotFound() {
    assertEquals(Collections.emptyList(), parseName("we are 1 of all!"));
  }

  @Test
  public void testHashIncorrect() {
    assertEquals(Collections.emptyList(), parseName("we are ## of all!"));
  }

  @Test
  public void testHashStill1() {
    assertEquals(Collections.singletonList("1"), parseName("we are ##1 of all!"));
  }
}