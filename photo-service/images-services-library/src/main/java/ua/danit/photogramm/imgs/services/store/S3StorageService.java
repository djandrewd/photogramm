package ua.danit.photogramm.imgs.services.store;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.danit.photogramm.imgs.services.configuration.AwsConfiguration;

/**
 * Storage service for storing image AWS simple storage service.
 *
 * @author Andrey Minov
 */
public class S3StorageService implements ImagesStorageService {

  private static final Logger LOGGER = LoggerFactory.getLogger(S3StorageService.class);
  //https://s3.eu-central-1.amazonaws.com/photogramm.images/
  private static final String URL_PATTERN = "https://s3.%s.amazonaws.com/%s/%s";

  private AwsConfiguration configuration;
  private AmazonS3 client;

  /**
   * Instantiates a storage service.
   *
   * @param configuration the configuration for storing.
   */
  public S3StorageService(AwsConfiguration configuration) {
    this.configuration = configuration;
    createClient();
  }

  private void createClient() {
    BasicAWSCredentials credentials =
        new BasicAWSCredentials(configuration.getAccessKey(), configuration.getSecretKey());
    ClientConfiguration clientConfiguration = new ClientConfiguration();
    client = AmazonS3ClientBuilder.standard()
                                  .withCredentials(new AWSStaticCredentialsProvider(credentials))
                                  .withRegion(Regions.fromName(configuration.getRegion()))
                                  .withClientConfiguration(clientConfiguration).build();
  }

  @Override
  public URL saveImage(String name, BufferedImage image) {
    try {
      byte[] encoded;
      try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
        ImageIO.write(image, configuration.getFormat(), os);
        encoded = os.toByteArray();
      }
      String imageName = name + "." + configuration.getFormat();
      LOGGER.info("Try to store image [{}] into remote AWS.", imageName);

      ObjectMetadata objectMetadata = new ObjectMetadata();
      objectMetadata.setContentType(configuration.getFormat());
      objectMetadata.setContentLength(encoded.length);

      PutObjectRequest request = new PutObjectRequest(configuration.getBucketName(), imageName,
          new ByteArrayInputStream(encoded), objectMetadata)
          .withCannedAcl(CannedAccessControlList.PublicRead);
      client.putObject(request);
      URL external = new URL(String
          .format(URL_PATTERN, configuration.getRegion(), configuration.getBucketName(),
              imageName));
      LOGGER.info("Image stored and will be accessed via : {}", external);
      return external;
    } catch (Exception e) {
      LOGGER
          .warn(String.format("Error occurred try to store image [%s] into remote AWS.", image), e);
      throw new RuntimeException(String.format("Unable to load file on S3 : %s", name), e);
    }
  }
}
