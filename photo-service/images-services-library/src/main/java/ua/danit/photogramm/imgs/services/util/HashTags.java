package ua.danit.photogramm.imgs.services.util;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for managing, searching and parsing hash tags.
 *
 * @author Andrey Minov
 */
public final class HashTags {

  private static final Pattern HASH_PATTERN = Pattern.compile("#\\w+");

  /**
   * Parse text of image name, search for list of hash tags and return them.
   *
   * @param text original image name text.
   * @return the list of name hash tags.
   */
  public static List<String> parseName(String text) {
    if (Strings.isNullOrEmpty(text)) {
      return Collections.emptyList();
    }
    List<String> result = new ArrayList<>();
    Matcher matcher = HASH_PATTERN.matcher(text);
    int searchPos = 0;
    while (matcher.find(searchPos)) {
      result.add(text.substring(matcher.start() + 1, matcher.end()));
      searchPos = matcher.end();
    }
    return result;
  }
}
