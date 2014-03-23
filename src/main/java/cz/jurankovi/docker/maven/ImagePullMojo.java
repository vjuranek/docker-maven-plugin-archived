package cz.jurankovi.docker.maven;

import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.kpelykh.docker.client.DockerClient;
import com.kpelykh.docker.client.DockerException;
import com.kpelykh.docker.client.model.Image;
import com.sun.jersey.api.client.ClientResponse;

@Mojo(name = "image-pull", defaultPhase = LifecyclePhase.PRE_INTEGRATION_TEST)
public class ImagePullMojo extends AbstractDockerMojo {

    @Parameter(required = true)
    private String imageId;
    @Parameter
    private String tag;
    @Parameter
    private String registry;
    
    public void doExecute() throws DockerException, MojoExecutionException {
        DockerClient client = getClient();
        ClientResponse resp = client.pull(imageId, tag, registry);
        if (Response.Status.Family.SUCCESSFUL.equals(resp.getStatusInfo().getFamily())) {
            //TODO log
        } else {
            throw new MojoExecutionException("Failed to pull image ID " + imageId);
        }

        // wait for the image to be downloaded
        while (!isImagePulled()) {
            try {
                Thread.currentThread().sleep(15 * 1000); // wait 15 sec
                //TOOD log progress
            } catch (InterruptedException e) {
                // TODO log
                throw new MojoExecutionException(e.getMessage());
            }
        }
    }
    
    private boolean isImagePulled() throws DockerException {
        List<Image> images = getClient().getImages(imageId, true);
        if (images.size() == 0) {
            return false;
        }
        // tag is not set, no need to compare repo tags
        if (tag == null || tag.isEmpty()) {
            return true;
        }

        String matchTag = imageId + ":" + tag;
        for (Image img : images) {
            for (String repoTag : img.getRepoTags()) {
                if (matchTag.equals(repoTag)) {
                    return true;
                }
            }
        }

        return false;
    }
    
}
