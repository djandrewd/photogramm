package ua.danit.photogramm.rweb.model;

import com.google.common.base.MoreObjects;

/**
 * Describe part of the text that can be either list text or hyperlink.
 *
 * @author Andrey Minov
 */
public class TextPart {

  private String payload;
  private boolean link;
  private String url;

  /**
   * Instantiates a new Text part.
   *
   * @param payload the payload
   * @param link    the link
   * @param url     the url
   */
  public TextPart(String payload, boolean link, String url) {
    this.payload = payload;
    this.link = link;
    this.url = url;
  }

  public String getPayload() {
    return payload;
  }

  public boolean isLink() {
    return link;
  }

  public String getUrl() {
    return url;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    TextPart textPart = (TextPart) o;

    if (link != textPart.link) {
      return false;
    }
    if (payload != null ? !payload.equals(textPart.payload) : textPart.payload != null) {
      return false;
    }
    return url != null ? url.equals(textPart.url) : textPart.url == null;
  }

  @Override
  public int hashCode() {
    int result = payload != null ? payload.hashCode() : 0;
    result = 31 * result + (link ? 1 : 0);
    result = 31 * result + (url != null ? url.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("payload", payload).add("link", link)
                      .add("url", url).toString();
  }
}
