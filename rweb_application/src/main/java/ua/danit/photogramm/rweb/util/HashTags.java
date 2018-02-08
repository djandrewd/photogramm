package ua.danit.photogramm.rweb.util;

import static ua.danit.photogramm.rweb.controllers.ExploreTagResource.RESOURCE_PATH;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ua.danit.photogramm.rweb.controllers.ExploreTagResource;
import ua.danit.photogramm.rweb.model.TextPart;

/**
 * Utility class for managing, searching and parsing hash tags.
 *
 * @author Andrey Minov
 */
public final class HashTags {

  private static final Pattern HASH_PATTERN = Pattern.compile("#\\w+");
  private static final String TAG_PATTERN = RESOURCE_PATH + "/%s";

  /**
   * Parse text of image name, search for list of hash tags and return them as partial text.
   *
   * @param text original image name text.
   * @return the list of text parts where text can be link to any other resource.
   */
  public static List<TextPart> parseIntoParts(String text) {
    if (Strings.isNullOrEmpty(text)) {
      return Collections.emptyList();
    }
    List<TextPart> result = new ArrayList<>();
    Matcher matcher = HASH_PATTERN.matcher(text);
    int searchPos = 0;
    int lastPos = 0;
    while (matcher.find(searchPos)) {
      if (matcher.start() > lastPos) {
        result.add(new TextPart(text.substring(lastPos, matcher.start()), false, null));
      }
      String tag = text.substring(matcher.start() + 1, matcher.end());
      result.add(new TextPart(text.substring(matcher.start(), matcher.end()), true,
          String.format(TAG_PATTERN, tag)));
      searchPos = matcher.end();
      lastPos = matcher.end();
    }
    if (lastPos < text.length()) {
      result.add(new TextPart(text.substring(lastPos), false, null));
    }
    return result;
  }
}
