package cz.jurankovi.docker.maven;

import java.util.List;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.kpelykh.docker.client.DockerClient;
import com.kpelykh.docker.client.DockerException;
import com.kpelykh.docker.client.model.Container;

@Mojo(name = "image-stop", defaultPhase = LifecyclePhase.POST_INTEGRATION_TEST)
public class ContainerStopByImageMojo extends AbstractDockerMojo {

    @Parameter(required = true)
    private String imageId;

    public void doExecute() throws DockerException {
        DockerClient client = getClient();
        List<Container> containers = getClient().listContainers(false);
        for (Container c : containers) {
            if (imageId.equalsIgnoreCase(c.getImage())) {
                client.stopContainer(c.getId());
            }
        }
    }

}
