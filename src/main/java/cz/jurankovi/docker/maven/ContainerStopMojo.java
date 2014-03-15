package cz.jurankovi.docker.maven;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.kpelykh.docker.client.DockerException;

@Mojo(name = "container-stop", defaultPhase = LifecyclePhase.POST_INTEGRATION_TEST)
public class ContainerStopMojo extends AbstractDockerMojo {

    @Parameter(required = true)
    private String[] containerIds;

    public void doExecute() throws DockerException {
        for (String id : containerIds) {
            getClient().stopContainer(id);
        }
    }
}