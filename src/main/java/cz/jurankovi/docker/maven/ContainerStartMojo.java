package cz.jurankovi.docker.maven;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.kpelykh.docker.client.DockerException;

@Mojo(name = "container-start", defaultPhase = LifecyclePhase.PRE_INTEGRATION_TEST)
public class ContainerStartMojo extends AbstractDockerMojo {

    @Parameter(required = true)
    private String[] containerIds;

    public void doExecute() throws DockerException {
        for (String id : containerIds) {
            getClient().startContainer(id);
        }
    }
}
