package ua.danit.photogramm.imgs.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Internal mapping class used for managing client subscriptions.
 *
 * @author Andrey Minov
 */
@Entity
@Table(name = "SUBSCRIPTIONS")
public class Subscription implements Serializable {
  @Id
  @Column(name = "SUBSCRIBER_USER_ID")
  private String subscriber;
  @Id
  @Column(name = "SUBSCRIPTION_USER_ID")
  private String subscription;

  public String getSubscriber() {
    return subscriber;
  }

  public void setSubscriber(String subscriber) {
    this.subscriber = subscriber;
  }

  public String getSubscription() {
    return subscription;
  }

  public void setSubscription(String subscription) {
    this.subscription = subscription;
  }
}
