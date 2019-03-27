package integration;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
public class UserControllerIntTests {

    @Test
    public void Registering_user_Should_return_user() throws IOException {
        // Arrange
        String url = "http://localhost:8080/user/register";
        String username = "tester1";//"user"+ System.currentTimeMillis();
        String json = String.format("{\"username\":\"%1s\",\"password\":\"%2s\"}", username, "testpassword");

        HttpClient client = HttpClientBuilder.create().useSystemProperties().build();
        StringEntity requestEntity = new StringEntity(json, ContentType.APPLICATION_JSON);

        HttpPost postMethod = new HttpPost(url);
        postMethod.setEntity(requestEntity);
        postMethod.setHeader("Content-Type", "application/json");


        // Act
        HttpResponse response = client.execute(postMethod);
        int statusCode = response.getStatusLine().getStatusCode();
        String responseString = EntityUtils.toString(response.getEntity());

        // Assert
        Assert.assertEquals(200, statusCode);
        Assert.assertTrue("Response string did not contain user name", responseString.contains(username));

    }
}
