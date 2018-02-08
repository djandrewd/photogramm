package ua.danit.photogramm.web.model;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * This is sign-up user entity filled by sign-up form.
 *
 * @author Andrey Minov
 */
@Valid
public class SignupUser {
  @NotEmpty
  private String nickname;
  @NotEmpty
  private String email;
  @NotEmpty
  private String password;

  private String name;

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
