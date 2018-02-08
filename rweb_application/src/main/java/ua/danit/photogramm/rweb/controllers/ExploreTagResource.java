package ua.danit.photogramm.rweb.controllers;

import static ua.danit.photogramm.rweb.controllers.ExploreTagResource.RESOURCE_PATH;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for exploring tag page.
 *
 * @author Andrey Minovs
 */
@Controller
@RequestMapping(RESOURCE_PATH)
public class ExploreTagResource {

  /**
   * The constant for exploring tag resource.
   */
  public static final String RESOURCE_PATH = "/explore/tags";

  /**
   * Explore provided tag page..
   *
   * @param tag the tag to be explored.
   * @return the link to explore tag view.
   */
  @GetMapping("/{tags}")
  public String explore(@PathVariable("tags") String tag) {
    return "index";
  }
}
